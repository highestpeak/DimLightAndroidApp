package com.highestpeak.dimlight.data;

import com.highestpeak.dimlight.data.entity.FeedListBean;
import com.highestpeak.dimlight.data.entity.RssSourceJsonBean;
import com.highestpeak.dimlight.data.entity.TopicRssListBean;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author highestpeak
 */
public interface WebService {
    interface SucceedCallback {
        void invoke(Object info);
    }

    interface FailCallback {
        void invoke(Object info);
    }

    /**
     * 获得 rssSource 数据
     * @return data
     */
    @GET("android_rss_info")
    Call<RssSourceJsonBean> getRssSourceInfoList();

    @GET("android_feed_item")
    Call<FeedListBean> getFeedList();

    @GET("android_topic_rss_group")
    Call<TopicRssListBean> getTopicList();
}
