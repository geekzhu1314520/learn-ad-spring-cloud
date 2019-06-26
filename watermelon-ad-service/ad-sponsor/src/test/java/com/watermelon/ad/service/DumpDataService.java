package com.watermelon.ad.service;

import com.alibaba.fastjson.JSON;
import com.watermelon.ad.Application;
import com.watermelon.ad.constant.CommonStatus;
import com.watermelon.ad.dao.AdCreativeRepository;
import com.watermelon.ad.dao.AdPlanRepository;
import com.watermelon.ad.dao.AdUnitRepository;
import com.watermelon.ad.dao.unit_condition.AdUnitDistrictRepository;
import com.watermelon.ad.dao.unit_condition.AdUnitItRepository;
import com.watermelon.ad.dao.unit_condition.AdUnitKeywordRepository;
import com.watermelon.ad.dao.unit_condition.CreativeUnitRepository;
import com.watermelon.ad.dump.DConstant;
import com.watermelon.ad.dump.table.*;
import com.watermelon.ad.entity.AdCreative;
import com.watermelon.ad.entity.AdPlan;
import com.watermelon.ad.entity.AdUnit;
import com.watermelon.ad.entity.unit_condition.AdUnitDistrict;
import com.watermelon.ad.entity.unit_condition.AdUnitIt;
import com.watermelon.ad.entity.unit_condition.AdUnitKeyword;
import com.watermelon.ad.entity.unit_condition.CreativeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class DumpDataService {

    @Autowired
    private AdPlanRepository planRepository;
    @Autowired
    private AdUnitRepository unitRepository;
    @Autowired
    private AdCreativeRepository creativeRepository;
    @Autowired
    private CreativeUnitRepository creativeUnitRepository;
    @Autowired
    private AdUnitDistrictRepository unitDistrictRepository;
    @Autowired
    private AdUnitItRepository unitItRepository;
    @Autowired
    private AdUnitKeywordRepository unitKeywordRepository;

    @Test
    public void dumpAdTableData() {
        dumpAdPlanTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN));
        dumpAdUnitTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT));
        dumpAdCreativeTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE));
        dumpAdCreativeUnitTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT));
        dumpAdUnitItTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_INTEREST));
        dumpAdUnitDistrictTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_DISTRICT));
        dumpAdUnitKeywordTable(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_KEYWORD));
    }

    private void dumpAdPlanTable(String fileName) {
        List<AdPlan> planList = planRepository.findAllByPlanStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(planList)) {
            return;
        }

        List<AdPlanTable> planTables = new ArrayList<>();
        planList.forEach(p -> planTables.add(
                new AdPlanTable(p.getId(), p.getUserId(), p.getPlanStatus(), p.getStartDate(), p.getEndDate())
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdPlanTable planTable : planTables) {
                writer.write(JSON.toJSONString(planTable));
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            log.error("dumpAdPlanTable error");
        }
    }

    private void dumpAdUnitTable(String fileName) {
        List<AdUnit> unitList = unitRepository.findAllByUnitStatus(CommonStatus.VALID.getStatus());
        if (CollectionUtils.isEmpty(unitList)) {
            return;
        }

        List<AdUnitTable> unitTables = new ArrayList<>();
        unitList.forEach(p -> unitTables.add(
                new AdUnitTable(p.getId(), p.getPlanId(), p.getUnitStatus(), p.getPositionType())
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitTable unitTable : unitTables) {
                writer.write(JSON.toJSONString(unitTable));
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            log.error("dumpAdUnitTable error");
        }
    }

    private void dumpAdCreativeTable(String fileName) {
        List<AdCreative> creativeList = creativeRepository.findAll();
        if (CollectionUtils.isEmpty(creativeList)) {
            return;
        }

        List<AdCreativeTable> creativeTables = new ArrayList<>();
        creativeList.forEach(p -> creativeTables.add(
                new AdCreativeTable(p.getId(), p.getName(), p.getType(), p.getMaterialType(), p.getHeight(), p.getWidth(), p.getAuditStatus(), p.getUrl())
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeTable creativeTable : creativeTables) {
                writer.write(JSON.toJSONString(creativeTable));
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            log.error("dumpAdCreativeTable error");
        }
    }

    private void dumpAdCreativeUnitTable(String fileName) {
        List<CreativeUnit> creativeUnitList = creativeUnitRepository.findAll();
        if (CollectionUtils.isEmpty(creativeUnitList)) {
            return;
        }

        List<AdCreativeUnitTable> creativeUnitTables = new ArrayList<>();
        creativeUnitList.forEach(p -> creativeUnitTables.add(
                new AdCreativeUnitTable(p.getCreativeId(), p.getUnitId())
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdCreativeUnitTable creativeUnitTable : creativeUnitTables) {
                writer.write(JSON.toJSONString(creativeUnitTable));
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            log.error("dumpAdCreativeUnitTable error");
        }
    }

    private void dumpAdUnitItTable(String fileName) {
        List<AdUnitIt> unitIts = unitItRepository.findAll();
        if (CollectionUtils.isEmpty(unitIts)) {
            return;
        }

        List<AdUnitItTable> unitItTables = new ArrayList<>();
        unitIts.forEach(p -> unitItTables.add(
                new AdUnitItTable(p.getUnitId(), p.getItTag())
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitItTable unitItTable : unitItTables) {
                writer.write(JSON.toJSONString(unitItTable));
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            log.error("dumpAdCreativeUnitTable error");
        }
    }

    private void dumpAdUnitDistrictTable(String fileName) {
        List<AdUnitDistrict> unitDistricts = unitDistrictRepository.findAll();
        if (CollectionUtils.isEmpty(unitDistricts)) {
            return;
        }

        List<AdUnitDistrictTable> unitDistrictTables = new ArrayList<>();
        unitDistricts.forEach(p -> unitDistrictTables.add(
                new AdUnitDistrictTable(p.getUnitId(), p.getProvince(), p.getCity())
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitDistrictTable unitDistrictTable : unitDistrictTables) {
                writer.write(JSON.toJSONString(unitDistrictTable));
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            log.error("dumpAdUnitDistrictTable error");
        }
    }

    private void dumpAdUnitKeywordTable(String fileName) {
        List<AdUnitKeyword> unitKeywords = unitKeywordRepository.findAll();
        if (CollectionUtils.isEmpty(unitKeywords)) {
            return;
        }

        List<AdUnitKeywordTable> unitKeywordTables = new ArrayList<>();
        unitKeywords.forEach(p -> unitKeywordTables.add(
                new AdUnitKeywordTable(p.getUnitId(), p.getKeyword())
        ));

        Path path = Paths.get(fileName);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            for (AdUnitKeywordTable unitKeywordTable : unitKeywordTables) {
                writer.write(JSON.toJSONString(unitKeywordTable));
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            log.error("dumpAdUnitDistrictTable error");
        }
    }

}
