package com.watermelon.ad.service.impl;

import com.watermelon.ad.constant.Constants;
import com.watermelon.ad.dao.AdUserRepository;
import com.watermelon.ad.entity.AdUser;
import com.watermelon.ad.exception.AdException;
import com.watermelon.ad.service.IAdUserService;
import com.watermelon.ad.utils.CommonUtils;
import com.watermelon.ad.vo.CreateUserRequest;
import com.watermelon.ad.vo.CreateUserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdUserServiceImpl implements IAdUserService {

    private AdUserRepository userRepository;

    @Autowired
    public AdUserServiceImpl(AdUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public CreateUserResponse createUser(CreateUserRequest request) throws AdException {

        if(!request.volidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdUser oldUser = userRepository.findByUsername(request.getUsername());
        if(oldUser != null){
            throw new AdException(Constants.ErrorMsg.SAME_NAME_ERROR);
        }

        AdUser newUser = userRepository.save(
                new AdUser(request.getUsername(), CommonUtils.md5(request.getUsername())
        ));

        return new CreateUserResponse(
                newUser.getUsername(), newUser.getToken(), newUser.getCreateTime(), newUser.getUpdateTime()
        );
    }
}
