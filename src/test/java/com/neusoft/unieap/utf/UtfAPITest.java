package com.neusoft.unieap.utf;

import com.google.gson.Gson;
import edu.umass.cs.benchlab.har.HarEntry;
import edu.umass.cs.benchlab.har.HarLog;
import edu.umass.cs.benchlab.har.HarPostDataParam;
import edu.umass.cs.benchlab.har.tools.HarFileReader;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.neusoft.unieap.utf.UtfHttpRequest.get;
import static com.neusoft.unieap.utf.UtfHttpRequest.post;

/**
 * Created by jim on 2017/9/8.
 * UTF API 测试类
 */
@Test
public class UtfAPITest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UtfAPITest.class);
    private OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .connectTimeout(1200, TimeUnit.SECONDS)
            .writeTimeout(1200, TimeUnit.SECONDS)
            .readTimeout(1200, TimeUnit.SECONDS)
            .build();

    @BeforeMethod(enabled = true)
    public void testLoginAPI() throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add("username", "admin")
                .add("password", "1")
                .build();

        Request request = new Request.Builder()
                .url("http://10.4.45.46:9091/utf/user/login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        LOGGER.debug(String.valueOf(response.isRedirect()));
        LOGGER.debug(response.body().string());
    }

    @BeforeGroups(enabled = false)
    public void testAPI(){
        try {
            File file = new File("G:\\FiddlerAutoSave\\188.har");
            HarFileReader reader = new HarFileReader();
            HarLog log = reader.readHarFile(file);
            List<HarEntry> entries = log.getEntries().getEntries();
            for (HarEntry e : entries){
                String method = e.getRequest().getMethod().toUpperCase();
                List<HarPostDataParam> postDataParams = e.getRequest().getPostData().getParams().getPostDataParams();
                Map<String, Object> map = new HashMap();
                for (HarPostDataParam param: postDataParams){
                    map.put(param.getName(), param.getValue());
                }
                String json = new Gson().toJson(map);
                String url = e.getRequest().getUrl();

                switch (method){
                    case "GET":
                        get(url);
                        break;
                    case "POST":
                        post(url, json);
                        break;
                    case "DELETE":
                        break;
                    case "PUT":
                        break;

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test(enabled = true)
    public void testGetMenus() throws IOException {
        Request request = new Request.Builder()
                .url("http://10.4.45.46:9091/utf/menu/getMenus")
                .build();

        Response response = client.newCall(request).execute();
        LOGGER.debug(response.body().string());
    }

    @Test(enabled = true)
    public void testOnlineAPI() throws IOException {
        Request request = new Request.Builder()
                .url("http://10.4.45.46:9091/utf//admin/userManager/online")
                .build();

        Response response = client.newCall(request).execute();
        LOGGER.debug(response.body().string());
    }
}
