package com.watermelon.ad.service.impl;

import com.watermelon.ad.constant.Constants;
import com.watermelon.ad.dao.AdCreativeRepository;
import com.watermelon.ad.entity.AdCreative;
import com.watermelon.ad.exception.AdException;
import com.watermelon.ad.service.ICreativeService;
import com.watermelon.ad.vo.CreativeRequest;
import com.watermelon.ad.vo.CreativeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreativeServiceImpl implements ICreativeService {

    private AdCreativeRepository creativeRepository;

    @Autowired
    public CreativeServiceImpl(AdCreativeRepository creativeRepository) {
        this.creativeRepository = creativeRepository;
    }

    @Override
    public CreativeResponse createAdCreative(CreativeRequest request) throws AdException {

        if(!request.volidate()){
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }
        AdCreative adCreative = creativeRepository.save(request.convertEntity());
        return new CreativeResponse(adCreative.getId(), adCreative.getName());
    }
}
