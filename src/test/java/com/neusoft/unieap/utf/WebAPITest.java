package com.neusoft.unieap.utf;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * Created by jim on 2017/9/8.
 * psdmis API 测试类
 */
@Test
public class WebAPITest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebAPITest.class);
    private OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .connectTimeout(1200, TimeUnit.SECONDS)
            .writeTimeout(1200, TimeUnit.SECONDS)
            .readTimeout(1200, TimeUnit.SECONDS)
            .build();

    @DataProvider(name = "usernamePassword")
    private static Object[][] usernamePasswordProvider(){
        return new Object[][]{{"jun.liu.neu", "1qaz@WSX"}};
    }

    /**
     * psdmis登录
     * @param name
     * @param passwd
     * @throws IOException
     */
    @Test(enabled = true, dataProvider = "usernamePassword")
    public void testLoginAPI(String name, String passwd) throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add("j_username", name)
                .add("j_password", passwd)
                .build();

        Request request = new Request.Builder()
                .url("http://web.neusoft.com/dealwithcenter.jsp?action=login")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        LOGGER.debug(response.body().string());
    }

    @Test(enabled = false)
    public void testAddressList() throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add("type", "sn")
                .add("keywords", "殷坤")
                .add("pagesize", "50")
                .add("Submit22", "")
                .build();
        Request request = new Request.Builder()
                .url("http://web.neusoft.com/addresslist.jsp")
                .build();
        Response response = client.newCall(request).execute();
        LOGGER.debug(response.body().string());
    }
}
