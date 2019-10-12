package cn.dozyx.core.utli.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;

/**
 * Create by dozyx on 2019/7/5
 **/
public class IntDefaultZeroAdapter implements JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        System.out.println("json = [" + json + "], typeOfT = [" + typeOfT + "], context = [" + context + "]");
        if (json == null) {
            return 0;
        }
        if (json.getAsString().equals("")) {
            return 0;
        }
        try {
            return json.getAsInt();
        } catch (NumberFormatException e) {
            return 0;
        }
    }
}
