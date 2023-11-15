package com.everton.pilltime.api;

import com.google.gson.Gson;

public class ApiErrorParser {
    private static final Gson gson = new Gson();

    public static ApiError parseError(String json) {
        return gson.fromJson(json, ApiError.class)  ;
    }
}
