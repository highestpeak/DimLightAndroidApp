package com.highestpeak.dimlight.data.entity;

import lombok.Data;

/**
 * @author highestpeak
 */
@Data
public class FeedItemBean {
    private Integer id;
    private String createTime;
    private String updateTime;
    private String titleParse;
    private String descParse;
    private String link;
    private String guid;
    private String pubDate;
    private String author;
    private String jsonOptionalExtraFields;
    private Integer rssId;
    private String sourceName;
}
