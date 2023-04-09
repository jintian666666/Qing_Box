package cn.gdust.qing_box.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import baseokhttp3.MediaType;
import baseokhttp3.OkHttpClient;
import baseokhttp3.Request;
import baseokhttp3.RequestBody;
import baseokhttp3.Response;

public class OcrClient {

    private static final String API_KEY = "xICEb2auBouFVTFGGPGIReCU";
    private static final String SECRET_KEY = "a6K6vtcc4wpSQEm75e6yg4rMRgvN4VGT";

    private static OkHttpClient HTTP_CLIENT;
    private final Gson gson;


    public OcrClient() {
        this.HTTP_CLIENT = new OkHttpClient().newBuilder().build();
        this.gson = new Gson();
    }

    public String requestApi(String urlEncode) throws Exception{
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "image=" + urlEncode);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/rest/2.0/ocr/v1/general_basic?access_token=" + getAccessToken())
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        JsonElement jsonElement = JsonParser.parseString(response.body().string());
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        JsonArray wordsArray = jsonObject.getAsJsonArray("words_result");

        StringBuilder sb = new StringBuilder();
        for (JsonElement element : wordsArray) {
            JsonObject wordsObject = element.getAsJsonObject();
            String words = wordsObject.get("words").getAsString();
            sb.append(words);
        }
        String wordsString = sb.toString();

        return wordsString;
    }



    /**
     * 从用户的AK，SK生成鉴权签名（Access Token）
     *
     * @return 鉴权签名（Access Token）
     * @throws IOException IO异常
     */
    static String getAccessToken() throws IOException, JSONException {
        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded");
        RequestBody body = RequestBody.create(mediaType, "grant_type=client_credentials&client_id=" + API_KEY
                + "&client_secret=" + SECRET_KEY);
        Request request = new Request.Builder()
                .url("https://aip.baidubce.com/oauth/2.0/token")
                .method("POST", body)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        Response response = HTTP_CLIENT.newCall(request).execute();
        return new JSONObject(response.body().string()).getString("access_token");
    }

}
