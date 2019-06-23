package com.watermelon.ad.service;

import com.watermelon.ad.exception.AdException;
import com.watermelon.ad.vo.*;

public interface IAdUnitService {

    /**
     * 创建广告单元
     * @param request
     * @return
     */
    AdUnitResponse createAdUnit(AdUnitRequest request) throws AdException;

    /**
     * 推广单元兴趣Feature
     * @param request
     * @return
     */
    AdUnitItResponse createAdUnitIt(AdUnitItRequest request) throws AdException;

    /**
     * 推广单元地域Feature
     * @param request
     * @return
     */
    AdUnitDistrictResponse createAdUnitDistrict(AdUnitDistrictRequest request) throws AdException;

    /**
     * 推广单元关键字Feature
     * @param request
     * @return
     */
    AdUnitKeywordResponse createAdUnitKeyword(AdUnitKeywordRequest request) throws AdException;

    /**
     * 创建创意与单元的关联
     * @param request
     * @return
     */
    CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException;
}
