package com.highestpeak.dimlight.data;

import android.util.Log;

import com.google.common.collect.Maps;
import com.highestpeak.dimlight.data.entity.RssSourceJsonBean;

import java.io.IOException;
import java.util.Map;

import lombok.NoArgsConstructor;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author highestpeak
 */
@NoArgsConstructor
public class RssSourceGetHelp {

    private final Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WebConfig.BASE_URL)
            .build();
    private final WebService webService = retrofit.create(WebService.class);

    public void getRssSource(WebService.SucceedCallback succeedCallback, WebService.FailCallback failCallback) {
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), "this_is_username");
        Map<String, String> requestDataMap = Maps.newHashMap();
        //requestDataMap.put("key", value);
        //Call<RssSourceInfoBean> call= webService.getRssSourceInfoList(WebHelp.generateRequestBody(requestDataMap));
        Call<RssSourceJsonBean> call= webService.getRssSourceInfoList();
        RssSourceJsonBean bean=null;
        try {
            bean = call.execute().body();
        } catch (IOException e) {
            Log.d("PoemGetHelp ","execute error");
            failCallback.invoke(null);
        }
        if (bean==null){
            failCallback.invoke(null);
        }
        succeedCallback.invoke(bean);
    }
}
