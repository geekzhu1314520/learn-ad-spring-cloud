package com.watermelon.ad.service;

import com.watermelon.ad.entity.AdPlan;
import com.watermelon.ad.exception.AdException;
import com.watermelon.ad.vo.AdPlanGetRequest;
import com.watermelon.ad.vo.AdPlanRequest;
import com.watermelon.ad.vo.AdPlanResponse;

import java.util.List;

public interface IAdPlanService {

    /**
     * 创建广告计划
     *
     * @param request
     * @return
     */
    AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException;

    /**
     * 获取广告计划
     *
     * @param request
     * @return
     */
    List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException;

    /**
     * 更新广告计划
     *
     * @param request
     * @return
     */
    AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException;


    /**
     * 删除推广计划
     *
     * @param request
     */
    void deleteAdPlan(AdPlanRequest request) throws AdException;

}
