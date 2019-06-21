package com.watermelon.ad.service.impl;

import com.watermelon.ad.constant.CommonStatus;
import com.watermelon.ad.constant.Constants;
import com.watermelon.ad.dao.AdPlanRepository;
import com.watermelon.ad.dao.AdUserRepository;
import com.watermelon.ad.entity.AdPlan;
import com.watermelon.ad.entity.AdUser;
import com.watermelon.ad.exception.AdException;
import com.watermelon.ad.service.IAdPlanService;
import com.watermelon.ad.utils.CommonUtils;
import com.watermelon.ad.vo.AdPlanGetRequest;
import com.watermelon.ad.vo.AdPlanRequest;
import com.watermelon.ad.vo.AdPlanResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AdPlanServiceImpl implements IAdPlanService {

    private AdPlanRepository planRepository;
    private AdUserRepository userRepository;

    @Autowired
    public AdPlanServiceImpl(AdPlanRepository planRepository, AdUserRepository userRepository) {
        this.planRepository = planRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public AdPlanResponse createAdPlan(AdPlanRequest request) throws AdException {

        if (!request.createVolidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdUser> adUser = userRepository.findById(request.getUserId());
        if (!adUser.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdPlan oldAdPlan = planRepository.findByUserIdAndPlanName(request.getUserId(), request.getPlanName());
        if (oldAdPlan != null) {
            throw new AdException(Constants.ErrorMsg.SAME_PLAN_ERROR);
        }

        AdPlan adPlan = planRepository.save(
                new AdPlan(request.getUserId(), request.getPlanName(), CommonUtils.parseStringDate(request.getStartDate())
                        , CommonUtils.parseStringDate(request.getEndDate()))
        );
        return new AdPlanResponse(adPlan.getId(), adPlan.getPlanName());
    }

    @Override
    public List<AdPlan> getAdPlanByIds(AdPlanGetRequest request) throws AdException {

        if (!request.volidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        return planRepository.findAllByIdInAndUserId(request.getIds(), request.getUserId());

    }

    @Override
    @Transactional
    public AdPlanResponse updateAdPlan(AdPlanRequest request) throws AdException {

        if (!request.updateVolidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        if (StringUtils.isNotBlank(request.getPlanName())) {
            plan.setPlanName(request.getPlanName());
        }

        if (StringUtils.isNotBlank(request.getStartDate())) {
            plan.setStartDate(CommonUtils.parseStringDate(request.getStartDate()));
        }

        if (StringUtils.isNotBlank(request.getEndDate())) {
            plan.setEndDate(CommonUtils.parseStringDate(request.getEndDate()));
        }
        plan.setUpdateTime(new Date());

        plan = planRepository.save(plan);
        return new AdPlanResponse(plan.getId(), plan.getPlanName());
    }

    @Override
    @Transactional
    public void deleteAdPlan(AdPlanRequest request) throws AdException {
        if (!request.deleteVolidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        AdPlan plan = planRepository.findByIdAndUserId(request.getId(), request.getUserId());
        if (plan == null) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        plan.setUpdateTime(new Date());
        plan.setPlanStatus(CommonStatus.INVALID.getStatus());
        planRepository.save(plan);
    }
}
