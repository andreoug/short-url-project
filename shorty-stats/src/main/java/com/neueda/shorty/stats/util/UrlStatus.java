package com.neueda.shorty.stats.util;

/**
 * Created by gandreou on 16/04/2018.
 */

public enum UrlStatus {

    CREATED("CREATED"),
    UPDATED("UPDATED"),
    DELETED("DELETED");

    private String status;

    private UrlStatus(String status) {
        this.status = status;
    }

    public static UrlStatus fromString(String type) {
        if (type != null) {
            for (UrlStatus b : UrlStatus.values()) {
                if (type.equalsIgnoreCase(b.status)) {
                    return b;
                }
            }
        }
        return null;
    }

    public String getStatus() {
        return status;
    }
}