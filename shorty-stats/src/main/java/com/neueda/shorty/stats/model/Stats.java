package com.neueda.shorty.stats.model;

import lombok.Data;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * Created by gandreou on 16/05/2018.
 */
@Getter @RequiredArgsConstructor
public class Stats {

    @NonNull private final Integer id;
    @NonNull final String name;
    @NonNull final String value;
}
