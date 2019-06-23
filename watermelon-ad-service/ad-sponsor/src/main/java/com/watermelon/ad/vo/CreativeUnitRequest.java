package com.watermelon.ad.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreativeUnitRequest {

    private List<CreativeUnit> creativeUnits;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class CreativeUnit{
        private Long creativeId;
        private Long UnitId;
    }

}
