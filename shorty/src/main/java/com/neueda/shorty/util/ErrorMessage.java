package com.neueda.shorty.util;

/**
 * Created by gandreou on 15/05/2018.
 */
public enum ErrorMessage {

    BAD_REQUEST(            "400 - BAD REQUEST"),
    BAD_REQUEST_EMPTY_URL(  "400 - BAD REQUEST - The requested URL is emtpy" ),
    UNAUTHORIZE(            "401 - UNAUTHORIZED - You are not authorized to use this service"),
    RESOURCE_NOT_FOUND(     "404 - RESOURCE NOT FOUND - Unable to find URL to redirect to. " +
            "Please check that the URL entered is correct."),
    UNSUPPORTED_TYPE(       "415 - UNSUPPORTED TYPE - The representation not supported for the resource"),
    SERVER_ERROR(           "500 - SERVER ERROR - We are sorry for this. You can come back later");

    private String message;

    ErrorMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public static ErrorMessage fromString(String text) {
        if (text != null) {
            for (ErrorMessage b : ErrorMessage.values()) {
                if (text.equalsIgnoreCase(b.message)) {
                    return b;
                }
            }
        }
        return null;
    }
}

