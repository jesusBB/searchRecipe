package com.foodplanner.searchrecipe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UsingExecutorService {

    public static void main(String[] args)
            throws InterruptedException, JSONException {
        System.out.println("Starting");
        Callable<String> callableTask = () -> {
            TimeUnit.MILLISECONDS.sleep(2000);
            System.out.println("running");
            return "Task's execution";
        };
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        List<Callable<String>> tasks = new ArrayList();
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);
        tasks.add(callableTask);

        executorService.awaitTermination(1, TimeUnit.MICROSECONDS);
        List<Future<String>> result = executorService.invokeAll(tasks);
        executorService.shutdown();
        System.out
                .println(
                        executorService.awaitTermination(3,
                                TimeUnit.MICROSECONDS));
        System.out.println(result.get(0).isCancelled());
        result.forEach(future -> {
            try {
                System.out.println(future.get());
            } catch (InterruptedException | ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });

        testJsonObject();

    }

    private static void testJsonObject() throws JSONException {
        JSONObject propertyJsonObject = new JSONObject();
        String[] abicodes = new String[] { "1111111", "2222222" };
        // propertyJsonObject.put("abiCode", abicodes);
        // System.out.println(propertyJsonObject.has("abiCode"));
        // System.out.println(propertyJsonObject.get("abiCode").toString());

        String jsonValue = "[\"11111111\", \"222222222222\"]";

        JSONArray jsonArray = new JSONArray(jsonValue);
        System.out.println(jsonValue);
        System.out.println(jsonArray);
        propertyJsonObject.put("abiCodes", jsonArray);
        System.out.println(propertyJsonObject);

        JSONObject jsonObject = new JSONObject(propertyJsonObject);
        jsonArray = jsonObject.toJSONArray(jsonArray);
        System.out.println(jsonArray);

    }

}
