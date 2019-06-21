package com.watermelon.ad.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;
import org.bouncycastle.crypto.digests.MD5Digest;

public class CommonUtils {

    public static String md5(String value){
        return DigestUtils.md5Hex(value).toUpperCase();
    }

}
