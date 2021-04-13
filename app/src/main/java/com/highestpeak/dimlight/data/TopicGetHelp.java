package com.highestpeak.dimlight.data;

import android.util.Log;

import com.highestpeak.dimlight.data.entity.TopicRssListBean;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author highestpeak
 */
public class TopicGetHelp {
    private final Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WebConfig.BASE_URL)
            .build();
    private final WebService webService = retrofit.create(WebService.class);

    public void getTopicRssList(WebService.SucceedCallback succeedCallback, WebService.FailCallback failCallback) {
        Call<TopicRssListBean> call= webService.getTopicList();
        TopicRssListBean bean=null;
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
