package com.highestpeak.dimlight.data.entity;

import lombok.Builder;
import lombok.Data;

/**
 * @author highestpeak
 */
@Data
@Builder
public class TopicInfoBean {
    private Integer id;
    private String name;
    private String desc;
}
