package com.watermelon.ad.controller;

import com.alibaba.fastjson.JSON;
import com.watermelon.ad.entity.AdPlan;
import com.watermelon.ad.exception.AdException;
import com.watermelon.ad.service.IAdPlanService;
import com.watermelon.ad.vo.AdPlanGetRequest;
import com.watermelon.ad.vo.AdPlanRequest;
import com.watermelon.ad.vo.AdPlanResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class AdPlanOPController {

    private IAdPlanService adPlanService;

    @Autowired
    public AdPlanOPController(IAdPlanService adPlanService) {
        this.adPlanService = adPlanService;
    }

    @PostMapping("/create/adPlan")
    public AdPlanResponse createAdPlan(@RequestBody AdPlanRequest request) throws AdException {
        log.info("ad-sponsor:createAdPlan, request:{}", JSON.toJSONString(request));
        return adPlanService.createAdPlan(request);
    }

    @PostMapping("/get/adPlan")
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException{
        log.info("ad-sponsor:getAdPlanByIds, request:{}", JSON.toJSONString(request));
        return adPlanService.getAdPlanByIds(request);
    }

    @PutMapping("/put/adPlan")
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException{
        log.info("ad-sponsor:updateAdPlan, request:{}", JSON.toJSONString(request));
        return adPlanService.updateAdPlan(request);
    }

    @DeleteMapping("/delete/adPlan")
    public void deleteAdPlan(AdPlanRequest request) throws AdException{
        log.info("ad-sponsor:deleteAdPlan, request:{}", JSON.toJSONString(request));
        adPlanService.deleteAdPlan(request);
    }

}
