package org.woodwhales.generator.core.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @projectName: woodwhales-code-generator
 * @author: woodwhales
 * @date: 20.9.8 22:51
 * @description:
 */
public class GsonUtil {

    public static <T> T fromJson(String json) {
        return new Gson().fromJson(json, new TypeToken<T> (){}.getType());
    }

    public static String toJson(Object object) {
        return new Gson().toJson(object);
    }
}
