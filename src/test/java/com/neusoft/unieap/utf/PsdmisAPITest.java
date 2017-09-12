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
public class PsdmisAPITest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PsdmisAPITest.class);
    private OkHttpClient client = new OkHttpClient()
            .newBuilder()
            .connectTimeout(1200, TimeUnit.SECONDS)
            .writeTimeout(1200, TimeUnit.SECONDS)
            .readTimeout(1200, TimeUnit.SECONDS)
            .build();

    @DataProvider(name = "usernamePassword")
    private static Object[][] usernamePasswordProvider(){
        return new Object[][]{{"jun.liu.neu", "12345"}};
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
                .url("http://10.4.44.97:9999/psdmis/j_spring_security_check")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        LOGGER.debug(response.body().string());
    }

    /**
     * 打印迭代信息
     * @throws IOException
     */
    @Test
    public void testSprintInfo() throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add("header", "{\"code\":0,\"message\":{\"title\":\"\",\"detail\":\"\"}")
                .add("body", "{dataStores:{},parameters:{\"dcID\":\"development\",\"nom\":\"getSprintsByModuleId1\",\"moduleId\":\"8a84adf83cf77c82013cfa57decc0060\",\"pageNumber\":1,\"pageSize\":20,\"_parameters\":\"moduleId,pageNumber,pageSize\",\"_parameterTypes\":\"string,string,string\",\"_boId\":\"development_sprintBO_bo\",\"_methodName\":\"getSprintsByModuleId\",\"_methodParameterTypes\":\"String,int,int\"}}")
                .build();
        Request request = new Request.Builder()
                .url("http://10.4.44.97:9999/psdmis/techcomp/ria/commonProcessor!commonMethod.action")
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("ajaxRequest", "true")
                .build();
        Response response = client.newCall(request).execute();
        LOGGER.debug(response.body().string());
    }
}
