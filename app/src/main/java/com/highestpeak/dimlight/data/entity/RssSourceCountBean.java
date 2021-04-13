package com.highestpeak.dimlight.data.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author highestpeak
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class RssSourceCountBean extends RssSourceInfoBean{
    private int count;
}
