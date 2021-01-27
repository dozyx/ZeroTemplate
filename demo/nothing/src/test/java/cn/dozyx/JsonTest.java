package cn.dozyx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import static cn.dozyx.LogUtils.print;

public class JsonTest {

    @Test
    public void testFormat() throws JSONException, JSONException {
        String json = "{\n" +
                "    \"defaultQuality\": false,\n" +
                "    \"format\": \"mp4\",\n" +
                "    \"videoUrl\": \"https://www.baidu.com\",\n" +
                "    \"quality\": [],\n" +
                "    \"remote\": true\n" +
                "}";
        JSONObject jsonObject = new JSONObject(json);
        Object quality = jsonObject.get("quality");
        print(quality instanceof String);
        print(quality instanceof JSONArray);
    }
}
