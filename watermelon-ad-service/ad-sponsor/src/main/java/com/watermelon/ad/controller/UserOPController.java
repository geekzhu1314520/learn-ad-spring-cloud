package com.watermelon.ad.controller;

import com.alibaba.fastjson.JSON;
import com.watermelon.ad.exception.AdException;
import com.watermelon.ad.service.IAdUserService;
import com.watermelon.ad.vo.CreateUserRequest;
import com.watermelon.ad.vo.CreateUserResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserOPController {

    private IAdUserService adUserService;

    @Autowired
    public UserOPController(IAdUserService adUserService) {
        this.adUserService = adUserService;
    }

    @PostMapping("/create/user")
    public CreateUserResponse createUser(@RequestBody CreateUserRequest request) throws AdException {
        log.info("ad-sponsor:createUser:{}", JSON.toJSONString(request));
        return adUserService.createUser(request);
    }
}
