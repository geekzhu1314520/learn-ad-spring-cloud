package com.watermelon.ad.controller;

import com.watermelon.ad.exception.AdException;
import com.watermelon.ad.service.ICreativeService;
import com.watermelon.ad.vo.CreativeRequest;
import com.watermelon.ad.vo.CreativeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CreativeOPController {

    private ICreativeService creativeService;

    @Autowired
    public CreativeOPController(ICreativeService creativeService) {
        this.creativeService = creativeService;
    }

    @PostMapping("/create/adCreative")
    public CreativeResponse createAdCreative(CreativeRequest request) throws AdException{
        log.info("ad-sponsor:createAdCreative, request:{}", request);
        return creativeService.createAdCreative(request);
    }

}
