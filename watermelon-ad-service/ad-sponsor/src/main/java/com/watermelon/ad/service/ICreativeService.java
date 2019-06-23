package com.watermelon.ad.service;

import com.watermelon.ad.exception.AdException;
import com.watermelon.ad.vo.CreativeRequest;
import com.watermelon.ad.vo.CreativeResponse;

public interface ICreativeService {

    /**
     * 创建创意
     * @param request
     * @return
     */
    CreativeResponse createAdCreative(CreativeRequest request) throws AdException;

}
