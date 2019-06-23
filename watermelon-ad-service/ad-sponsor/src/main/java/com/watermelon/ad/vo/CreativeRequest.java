package com.watermelon.ad.vo;

import com.watermelon.ad.constant.CommonStatus;
import com.watermelon.ad.entity.AdCreative;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeRequest {

    private String name;
    private Integer type;
    private Integer materialType;
    private Integer height;
    private Integer width;
    private Long size;
    private Integer duration;
    private Long userId;
    private String url;

    public AdCreative convertEntity(){
        AdCreative adCreative = new AdCreative();
        adCreative.setName(name);
        adCreative.setType(type);
        adCreative.setMaterialType(materialType);
        adCreative.setHeight(height);
        adCreative.setWidth(width);
        adCreative.setSize(size);
        adCreative.setDuration(duration);
        adCreative.setAuditStatus(CommonStatus.VALID.getStatus());
        adCreative.setUserId(userId);
        adCreative.setUrl(url);
        adCreative.setCreateTime(new Date());
        adCreative.setUpdateTime(adCreative.getCreateTime());
        return adCreative;
    }

    public boolean volidate(){
        return StringUtils.isNotBlank(name) && type != null && materialType != null && height != null && width != null
                && size != null && duration != null && userId != null && StringUtils.isNotBlank(url);
    }

}
