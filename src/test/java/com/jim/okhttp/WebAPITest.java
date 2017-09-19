package com.jim.okhttp;

import com.jim.mybatis.utils.MyOKHttpClient;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * Created by jim on 2017/9/8.
 * web.neusoft.com API 测试类
 */
@Test
public class WebAPITest {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebAPITest.class);

    @DataProvider(name = "usernamePassword")
    private static Object[][] usernamePasswordProvider() {
        return new Object[][]{{"jun.liu.neu", "1qaz@WSX"}};
    }

    /**
     * web.neusoft.com登录
     *
     * @param name
     * @param passwd
     * @throws IOException
     */
    @Test(enabled = true, dataProvider = "usernamePassword")
    public void testLoginAPI(String name, String passwd) throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add("inUserName", name)
                .add("inPwd", passwd)
                .build();

        requestBody.contentType();

        Request request = new Request.Builder()
                .url("http://web.neusoft.com/dealwithcenter.jsp?action=login")
                .post(requestBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Upgrade-Insecure-Requests", "1")
                .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.79 Safari/537.36")
                .addHeader("Referer", "http://web.neusoft.com/login.html")
                .build();


        OkHttpClient client = MyOKHttpClient.getInstance();
        Response response = client.newCall(request).execute();
        response.header("Content-Type", "text/html;charset=gb2312");
        //LOGGER.debug(response.body().string());
    }

    /**
     * 根据邮箱名字查询用户信息
     *
     * @throws IOException
     */
    @Test(enabled = true, dependsOnMethods = "testLoginAPI")
    public void testAddressList() throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add("type", "mail")
                .add("keywords", "yinkun")
                .add("pagesize", "50")
                .add("Submit22", "")
                .build();
        Request request = new Request.Builder()
                .url("http://web.neusoft.com/addresslist.jsp")
                .post(requestBody)
                .build();
        OkHttpClient client = MyOKHttpClient.getInstance();
        Response response = client.newCall(request).execute();
        LOGGER.debug(response.body().string().replaceAll("gb2312", "utf-8"));
    }
}
