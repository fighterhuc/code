package com.huc.util;

import java.util.HashMap;
import java.util.Map;
public class ResultUtil {
    public static Map<String, Object> success() {
        return o2e(true, (Object)null);
    }

    public static Map<String, Object> success(Object o) {
        return o2e(true, o);
    }

    public static Map<String, Object> failure() {
        return o2e(false, (Object)null);
    }

    public static Map<String, Object> failure(Object o) {
        return o2e(false, o);
    }

    private static Map<String, Object> o2e(boolean success, Object o) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", success);
        if (o != null) {
            result.put("data", o);
        }

        return result;
    }
}
