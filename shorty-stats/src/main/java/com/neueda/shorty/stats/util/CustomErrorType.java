package com.neueda.shorty.stats.util;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public class CustomErrorType {

    @NonNull private String errorMessage;
    @NonNull private String status;

}
