package com.watermelon.ad.utils;

import com.watermelon.ad.exception.AdException;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.util.Date;

public class CommonUtils {

    private static String[] parsePatterns = {
        "yyyy-MM-dd", "yyyy/MM/dd", "yyyy.MM.dd"
    };

    public static String md5(String value){
        return DigestUtils.md5Hex(value).toUpperCase();
    }

    public static Date parseStringDate(String value) throws AdException {
        try {
            return DateUtils.parseDate(value, parsePatterns);
        } catch (ParseException e) {
            throw new AdException(e.getMessage());
        }
    }

}
