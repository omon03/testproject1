package ru.apache_maven;

import org.json.simple.JSONObject;

public class JsonUtils {

    public static String buildJson(Object mark, Object obj) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(mark, obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObject.toJSONString();
    }
}
