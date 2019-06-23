package com.watermelon.ad.service.impl;

import com.watermelon.ad.constant.Constants;
import com.watermelon.ad.dao.AdCreativeRepository;
import com.watermelon.ad.dao.AdPlanRepository;
import com.watermelon.ad.dao.AdUnitRepository;
import com.watermelon.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.watermelon.ad.dao.unit_condition.AdUnitItRepository;
import com.watermelon.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.watermelon.ad.dao.unit_condition.CreativeUnitRepository;
import com.watermelon.ad.entity.AdPlan;
import com.watermelon.ad.entity.AdUnit;
import com.watermelon.ad.entity.unit_condition.AdUnitDistrict;
import com.watermelon.ad.entity.unit_condition.AdUnitIt;
import com.watermelon.ad.entity.unit_condition.AdUnitKeyword;
import com.watermelon.ad.entity.unit_condition.CreativeUnit;
import com.watermelon.ad.exception.AdException;
import com.watermelon.ad.service.IAdUnitService;
import com.watermelon.ad.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AdUnitServiceImpl implements IAdUnitService {

    private AdUnitRepository unitRepository;
    private AdUnitItRepository unitItRepository;
    private AdUnitKeywordRepository unitKeywordRepository;
    private AdUnitDistrictRepository unitDistrictRepository;
    private AdPlanRepository planRepository;
    private AdCreativeRepository creativeRepository;
    private CreativeUnitRepository creativeUnitRepository;

    @Autowired
    public AdUnitServiceImpl(AdUnitRepository unitRepository, AdUnitItRepository unitItRepository
            , AdUnitKeywordRepository unitKeywordRepository, AdUnitDistrictRepository unitDistrictRepository
            , AdPlanRepository planRepository, AdCreativeRepository creativeRepository
            , CreativeUnitRepository creativeUnitRepository) {
        this.unitRepository = unitRepository;
        this.unitItRepository = unitItRepository;
        this.unitKeywordRepository = unitKeywordRepository;
        this.unitDistrictRepository = unitDistrictRepository;
        this.planRepository = planRepository;
        this.creativeRepository = creativeRepository;
        this.creativeUnitRepository = creativeUnitRepository;
    }

    @Override
    public AdUnitResponse createAdUnit(AdUnitRequest request) throws AdException {
        if (!request.volidate()) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        Optional<AdPlan> plan = planRepository.findById(request.getPlanId());
        if (!plan.isPresent()) {
            throw new AdException(Constants.ErrorMsg.CAN_NOT_FIND_RECORD);
        }

        AdUnit oldUnit = unitRepository.findByPlanIdAndUnitName(request.getPlanId(), request.getUnitName());
        if (oldUnit != null) {
            throw new AdException(Constants.ErrorMsg.SAME_UNIT_ERROR);
        }

        AdUnit newUnit = unitRepository.save(
                new AdUnit(request.getPlanId(), request.getUnitName()
                        , request.getPositionType(), request.getBudget()
                ));
        return new AdUnitResponse(newUnit.getId(), newUnit.getUnitName());
    }

    @Override
    public AdUnitItResponse createAdUnitIt(AdUnitItRequest request) throws AdException {
        List<Long> unitIds = request.getUnitIts().stream().map(AdUnitItRequest.UnitIt::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitIt> unitIts = new ArrayList<>();
        request.getUnitIts().forEach(i -> unitIts.add(
                new AdUnitIt(i.getUnitId(), i.getItTag())
        ));
        List<Long> ids = unitItRepository.saveAll(unitIts).stream().map(AdUnitIt::getId).collect(Collectors.toList());
        return new AdUnitItResponse(ids);
    }

    @Override
    public AdUnitDistrictResponse createAdUnitDistrict(AdUnitDistrictRequest request) throws AdException {

        List<Long> unitIds = request.getUnitDistricts().stream()
                .map(AdUnitDistrictRequest.UnitDistrict::getUnitId).collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitDistrict> unitDistricts = new ArrayList<>();
        request.getUnitDistricts().forEach(i -> unitDistricts.add(
                new AdUnitDistrict(i.getUnitId(), i.getProvince(), i.getCity())
        ));

        List<Long> ids = unitDistrictRepository.saveAll(unitDistricts).stream().map(AdUnitDistrict::getId)
                .collect(Collectors.toList());
        return new AdUnitDistrictResponse(ids);
    }

    @Override
    public AdUnitKeywordResponse createAdUnitKeyword(AdUnitKeywordRequest request) throws AdException {

        List<Long> unitIds = request.getUnitKeywords().stream().map(AdUnitKeywordRequest.UnitKeyword::getUnitId)
                .collect(Collectors.toList());
        if (!isRelatedUnitExist(unitIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<AdUnitKeyword> unitKeywords = new ArrayList<>();
        request.getUnitKeywords().forEach(i -> unitKeywords.add(
                new AdUnitKeyword(i.getUnitId(), i.getKeyword())
        ));

        List<Long> ids = unitKeywordRepository.saveAll(unitKeywords).stream().map(AdUnitKeyword::getId)
                .collect(Collectors.toList());
        return new AdUnitKeywordResponse(ids);
    }

    @Override
    public CreativeUnitResponse createCreativeUnit(CreativeUnitRequest request) throws AdException {

        List<Long> creativeIds = request.getCreativeUnits().stream().map(CreativeUnitRequest.CreativeUnit::getCreativeId)
                .collect(Collectors.toList());
        List<Long> unitIds = request.getCreativeUnits().stream().map(CreativeUnitRequest.CreativeUnit::getUnitId)
                .collect(Collectors.toList());

        if (!isRelatedUnitExist(unitIds) || !isRelatedCreativeExist(creativeIds)) {
            throw new AdException(Constants.ErrorMsg.REQUEST_PARAM_ERROR);
        }

        List<CreativeUnit> creativeUnits = new ArrayList<>();
        request.getCreativeUnits().forEach(i -> creativeUnits.add(
                new CreativeUnit(i.getCreativeId(), i.getUnitId())
        ));

        List<Long> ids = creativeUnitRepository.saveAll(creativeUnits).stream().map(CreativeUnit::getId)
                .collect(Collectors.toList());
        return new CreativeUnitResponse(ids);
    }

    private boolean isRelatedUnitExist(List<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return false;
        }
        return unitRepository.findAllById(unitIds).size()
                == new HashSet<>(unitIds).size();
    }

    private boolean isRelatedCreativeExist(List<Long> creativeIds) {
        if (CollectionUtils.isEmpty(creativeIds)) {
            return false;
        }
        return creativeRepository.findAllById(creativeIds).size()
                == new HashSet<>(creativeIds).size();
    }
}
