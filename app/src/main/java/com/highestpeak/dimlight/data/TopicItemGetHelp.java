package com.highestpeak.dimlight.data;

import android.util.Log;

import com.highestpeak.dimlight.data.entity.FeedListBean;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author highestpeak
 */
public class TopicItemGetHelp {
    private final Retrofit retrofit = new Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(WebConfig.BASE_URL)
            .build();
    private final WebService webService = retrofit.create(WebService.class);

    public void getTopicItemList(WebService.SucceedCallback succeedCallback, WebService.FailCallback failCallback) {
        Call<FeedListBean> call= webService.getFeedList();
        FeedListBean bean=null;
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
