package com.highestpeak.dimlight.data.entity;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @author highestpeak
 */
@Data
public class TopicRssListBean {
    private Map<Integer, TopicInfoBean> topics;
    private Map<Integer, List<Integer>> topicRss;
    private Map<Integer, RssSourceCountBean> rss;
}
