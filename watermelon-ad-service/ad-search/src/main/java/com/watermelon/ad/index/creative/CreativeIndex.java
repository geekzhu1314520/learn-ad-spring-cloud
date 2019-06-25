package com.watermelon.ad.index.creative;

import com.watermelon.ad.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class CreativeIndex implements IndexAware<Long, CreativeObject> {

    private static Map<Long, CreativeObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    public List<CreativeObject> fetch(List<Long> adIds) {

        if (CollectionUtils.isEmpty(adIds)) {
            return Collections.emptyList();
        }

        List<CreativeObject> result = new ArrayList<>();
        adIds.forEach(k -> {
            CreativeObject creativeObject = objectMap.get(k);
            if (null == creativeObject) {
                log.error("creativeObject is null:{}", k);
                return;
            }
            result.add(creativeObject);
        });
        return result;
    }

    @Override
    public CreativeObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, CreativeObject value) {
        objectMap.put(key, value);
    }

    @Override
    public void update(Long key, CreativeObject value) {
        CreativeObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }
    }

    @Override
    public void delete(Long key, CreativeObject value) {
        objectMap.remove(key);
    }
}
