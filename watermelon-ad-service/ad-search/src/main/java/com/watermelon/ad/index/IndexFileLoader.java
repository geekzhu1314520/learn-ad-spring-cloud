package com.watermelon.ad.index;


import com.alibaba.fastjson.JSON;
import com.watermelon.ad.dump.DConstant;
import com.watermelon.ad.dump.table.*;
import com.watermelon.ad.handler.AdLevelDataHandler;
import com.watermelon.ad.mysql.constant.OpType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    @PostConstruct
    public void init() {

        List<String> adPlanStrings = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_PLAN));
        adPlanStrings.forEach(p -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(p, AdPlanTable.class), OpType.ADD
        ));

        List<String> adCreativeStrings = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE));
        adCreativeStrings.forEach(p -> AdLevelDataHandler.handleLevel2(
                JSON.parseObject(p, AdCreativeTable.class), OpType.ADD
        ));

        List<String> adUnitStrings = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_UNIT));
        adUnitStrings.forEach(p -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(p, AdUnitTable.class), OpType.ADD
        ));

        List<String> adCreativeUnitStrings = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_CREATIVE_UNIT));
        adCreativeUnitStrings.forEach(p -> AdLevelDataHandler.handleLevel3(
                JSON.parseObject(p, AdCreativeUnitTable.class), OpType.ADD
        ));

        List<String> adDistrictStrings = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_DISTRICT));
        adDistrictStrings.forEach(p -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(p, AdUnitDistrictTable.class), OpType.ADD
        ));

        List<String> adItStrings = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_INTEREST));
        adItStrings.forEach(p -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(p, AdUnitItTable.class), OpType.ADD
        ));

        List<String> adKeywordStrings = loadDumpData(String.format("%s%s", DConstant.DATA_ROOT_DIR, DConstant.AD_KEYWORD));
        adKeywordStrings.forEach(p -> AdLevelDataHandler.handleLevel4(
                JSON.parseObject(p, AdUnitKeywordTable.class), OpType.ADD
        ));
    }

    private List<String> loadDumpData(String fileName) {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(fileName))) {
            return reader.lines().collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
