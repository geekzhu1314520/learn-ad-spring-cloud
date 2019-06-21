package com.watermelon.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdPlanRequest {

    private Long id;
    private Long userId;
    private String planName;
    private String startDate;
    private String endDate;

    public boolean createVolidate() {
        return userId != null && StringUtils.isNotBlank(planName)
                && StringUtils.isNotBlank(startDate) && StringUtils.isNotBlank(endDate);
    }

    public boolean updateVolidate() {
        return id != null && userId != null;
    }

    public boolean deleteVolidate(){
        return id != null && userId != null;
    }

}
