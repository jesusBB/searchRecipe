package com.foodplanner.searchrecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SearchRecipeApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void testExecutorService() throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<String>> tasks = new ArrayList();
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);

        // executorService.awaitTermination(2, TimeUnit.SECONDS);
        executorService.invokeAll(tasks);
    }

    Callable<String> callableTask = () -> {
        TimeUnit.MILLISECONDS.sleep(300);
        return "Task's execution";
    };
}
