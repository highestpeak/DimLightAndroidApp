package com.highestpeak.dimlight.data.entity;

import java.util.List;
import java.util.Map;

import lombok.Data;

/**
 * @author highestpeak
 */
@Data
public class RssSourceJsonBean {
    private List<RssSourceInfoBean> sources;
    private Map<Integer, TagInfoBean> tags;
    private Map<Integer, TopicInfoBean> topics;
    private Map<Integer, List<Integer>> rssSourceTags;
    private Map<Integer, List<Integer>> rssTopics;
}
