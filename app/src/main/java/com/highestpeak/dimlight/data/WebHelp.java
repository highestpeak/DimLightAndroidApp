package com.highestpeak.dimlight.data;

import com.google.common.collect.Maps;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author highestpeak
 */
public class WebHelp {
    public static Map<String, RequestBody> generateRequestBody(Map<String, String> requestDataMap) {
        if (requestDataMap == null) {
            return Maps.newHashMap();
        }
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        requestDataMap.entrySet().forEach(entry -> {
            RequestBody requestBody = RequestBody.create(
                    MediaType.parse("multipart/form-data"),
                    Optional.ofNullable(entry.getValue()).orElse("")
            );
            requestBodyMap.put(entry.getKey(), requestBody);
        });
        return requestBodyMap;
    }

    private static final ThreadPoolExecutor NETWORK_POOL = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors()*2,
            30, TimeUnit.SECONDS, new LinkedBlockingQueue<>()
    );
    public static void asyncDataGet(Runnable runnable){
        NETWORK_POOL.execute(runnable);
    }

}
