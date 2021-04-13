package com.highestpeak.dimlight.data.entity;

import lombok.Data;

/**
 * @author highestpeak
 */
@Data
public class RssSourceInfoBean {
    private Integer id;
    private String createTime;
    private String updateTime;
    private String url;
    private String titleUser;
    private String titleParse;
    private String descUser;
    private String descParse;
    private String link;
    private String image;
    private String generator;
    private String jsonOptionalExtraFields;
    private Boolean fetchAble;
}
