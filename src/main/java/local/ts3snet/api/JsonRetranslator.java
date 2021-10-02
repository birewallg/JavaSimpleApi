package local.ts3snet.api;

import com.google.gson.Gson;

public class JsonRetranslator {
    public static String toJson(Object t) {
        return new Gson().toJson(t);
    }
}
