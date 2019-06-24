package com.watermelon.ad.controller;

import com.alibaba.fastjson.JSON;
import com.watermelon.ad.exception.AdException;
import com.watermelon.ad.service.IAdUnitService;
import com.watermelon.ad.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class AdUnitOPController {

    private IAdUnitService adUnitService;

    @Autowired
    public AdUnitOPController(IAdUnitService adUnitService) {
        this.adUnitService = adUnitService;
    }

    @PostMapping("/create/adUnit")
    public AdUnitResponse createAdUnit(AdUnitRequest request) throws AdException {
        log.info("ad-sponsor:createAdUnit, request:{}", JSON.toJSONString(request));
        return adUnitService.createAdUnit(request);
    }

    @PostMapping("/create/adUnitIt")
    public AdUnitItResponse createAdUnitIt(AdUnitItRequest request) throws AdException {
        log.info("ad-sponsor:createAdUnitIt, request:{}", JSON.toJSONString(request));
        return adUnitService.createAdUnitIt(request);
    }

    @PostMapping("/create/adUnitDistrict")
    public AdUnitDistrictResponse createAdUnitDistrict(AdUnitDistrictRequest request) throws AdException {
        log.info("ad-sponsor:createAdUnitDistrict, request:{}", JSON.toJSONString(request));
        return adUnitService.createAdUnitDistrict(request);
    }

    @PostMapping("/create/adUnitKeyword")
    public AdUnitKeywordResponse createAdUnitKeyword(AdUnitKeywordRequest request) throws AdException {
        log.info("ad-sponsor:createAdUnitKeyword, request:{}", JSON.toJSONString(request));
        return adUnitService.createAdUnitKeyword(request);
    }

    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {
        log.info("ad-sponsor:createCreativeUnit, request:{}", JSON.toJSONString(request));
        return adUnitService.createCreativeUnit(request);
    }

}
