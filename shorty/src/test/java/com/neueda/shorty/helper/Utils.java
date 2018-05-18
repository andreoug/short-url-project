package com.neueda.shorty.helper;

import org.apache.commons.lang3.RandomStringUtils;

/**
 * Created by gandreou on 17/05/2018.
 */
public class Utils {

    public static String generateRandomString(int length) {
        boolean useLetters = true;
        boolean useNumbers = false;
        return RandomStringUtils.random(length, useLetters, useNumbers);
    }
}
