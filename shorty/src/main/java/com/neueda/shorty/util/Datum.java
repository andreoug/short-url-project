package com.neueda.shorty.util;

import org.apache.commons.codec.binary.Hex;

import java.nio.charset.StandardCharsets;

/**
 * Created by gandreou on 17/05/2018.
 */
public class Datum {

    public static String toHex1(String s) {
        return Hex.encodeHexString(
                s.getBytes(StandardCharsets.UTF_8));
    }
}
