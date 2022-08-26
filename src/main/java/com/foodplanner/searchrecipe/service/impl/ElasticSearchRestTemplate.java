package com.foodplanner.searchrecipe.service.impl;

import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.handler.timeout.ReadTimeoutException;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public class ElasticSearchRestTemplate {

    public static void main(String[] args) {
        RestTemplate restTemplate = new RestTemplate();
        String fooResourceUrl = "http://localhost:9200/reviews/_doc";
        ResponseEntity<String> response = restTemplate
                .getForEntity(fooResourceUrl + "/1", String.class);
        System.out.println(response);

        Mono<ResponseEntity<String>> mono = Mono.just(
                restTemplate.getForEntity(fooResourceUrl + "/1", String.class))
                .timeout(Duration.ofMillis(0));
        mono.subscribe(System.out::println);

        StopWatch sw = StopWatch.createStarted();

        ResponseEntity<String> responseEntity = Mono.just(
                restTemplate.getForEntity(fooResourceUrl + "/1", String.class))
                .timeout(Duration.ofNanos(5))
                .block();
        sw.stop();
        System.out.println(responseEntity);
        System.out.println(sw.getTime(TimeUnit.MILLISECONDS));
        System.out.println(sw.getNanoTime());

        testingTimeout();
    }

    private static void testingTimeout() {
        HttpClient client = HttpClient.create()
                .responseTimeout(Duration.ofNanos(1));

        WebClient webClient = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(client))
                .baseUrl("http://localhost:9200/reviews/_doc/1")
                .build();

        HttpClient httpClient = HttpClient.create()
                .doOnConnected(conn -> conn
                        .addHandler(
                                new ReadTimeoutHandler(10,
                                        TimeUnit.MICROSECONDS))
                        .addHandler(new WriteTimeoutHandler(10)));

        WebClient webClientTimeout = WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://localhost:9200/reviews/_doc/1")
                .build();

        // System.out.println("--------"
        // + webClient.get()
        // .retrieve()
        // .bodyToMono(String.class)
        // .block());
        System.out.println("--------"
                + webClientTimeout.get()
                        .retrieve()
                        .bodyToMono(String.class)
                        .doOnError(s -> System.out.println("mierda"))
                        .onErrorMap(ReadTimeoutException.class,
                                ex -> new HttpTimeoutException("ReadTimeout"))
                        .block());
    }
}
