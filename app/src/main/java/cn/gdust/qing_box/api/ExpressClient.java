package cn.gdust.qing_box.api;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import baseokhttp3.FormBody;
import baseokhttp3.OkHttpClient;
import baseokhttp3.Request;
import baseokhttp3.RequestBody;
import baseokhttp3.Response;
import cn.gdust.qing_box.bean.Express;

public class ExpressClient {

    private static final String TAG = "ExpressClient";

    private final OkHttpClient client;
    private final Gson gson;
    private static final String BASE_URL = "https://api.oioweb.cn/api/common/delivery";


    public ExpressClient() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
    }

    public List<Express> requestApi(String expressId) throws Exception{
        //post请求体
        RequestBody requestBody = new FormBody.Builder()
                .add("nu", expressId)
                .build();
        //设置请求头
        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(requestBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .build();
        //发送
        Response response = client.newCall(request).execute();

        String responseBody = response.body().string(); //返回的JSON
        //response处理
        JsonElement jsonElement = JsonParser.parseString(responseBody);
        JsonObject jsonObject = jsonElement.getAsJsonObject().get("result").getAsJsonObject();
        JsonArray dataArray = jsonObject.getAsJsonArray("data");
        Type type = new TypeToken<List<Express>>(){}.getType();
        List<Express> expressList = gson.fromJson(dataArray.toString(), type);

        return expressList;
    }
}
