package local.ts3snet.api;

import com.google.gson.Gson;

import java.lang.reflect.Type;

public class JsonTranslate {
    public static String toJson(Object t) {
        return new Gson().toJson(t);
    }
    public static Object fromJson(String value, Object obj) {
        return new Gson().fromJson(value, (Type) obj);
    }
}
