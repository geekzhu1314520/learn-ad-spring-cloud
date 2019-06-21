package com.watermelon.ad.service;

import com.watermelon.ad.exception.AdException;
import com.watermelon.ad.vo.CreateUserRequest;
import com.watermelon.ad.vo.CreateUserResponse;

public interface IAdUserService {

    /**
     * 创建用户
     * @param request
     * @return
     */
    CreateUserResponse createUser(CreateUserRequest request) throws AdException;

}
