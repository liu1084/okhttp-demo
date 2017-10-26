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
 * psdmis API 测试类
 * <p>
 * psdmis没有get请求方式，所有请求都会post方式，并把数据以DataCenter的方式放在requestBody中
 */
@Test
public class PsdmisAPITest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PsdmisAPITest.class);

    @DataProvider(name = "usernamePassword")
    private static Object[][] usernamePasswordProvider() {
        return new Object[][]{{"username", "******"}};
    }

    /**
     * psdmis登录
     *
     * @throws IOException
     */
    @Test(enabled = true)
    public void testLoginAPI() throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add("j_username", "jun.liu.neu")
                .add("j_password", "1qaz@WSX")
                .build();

        Request request = new Request.Builder()
                .url("http://xxx.xxx.xx/j_spring_security_check")
                .post(requestBody)
                .build();

        OkHttpClient client = MyOKHttpClient.getInstance();
        Response response = client.newCall(request).execute();
        LOGGER.debug(response.body().string());
    }

    /**
     * 研发管理 - sprint信息管理
     *
     * @throws IOException
     */
    @Test(enabled = true, dependsOnMethods = "testLoginAPI")
    public void testSprintInfo() throws IOException {
        String dc = "{header:{\"code\":0,\"message\":{\"title\":\"\",\"detail\":\"\"}},body:{dataStores:{},parameters:{\"dcID\":\"development\",\"nom\":\"getSprintsByModuleId1\",\"moduleId\":\"8a84adf83cf77c82013cfa57decc0060\",\"pageNumber\":1,\"pageSize\":20,\"_parameters\":\"moduleId,pageNumber,pageSize\",\"_parameterTypes\":\"string,string,string\",\"_boId\":\"development_sprintBO_bo\",\"_methodName\":\"getSprintsByModuleId\",\"_methodParameterTypes\":\"String,int,int\"}}}";

        MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=UTF-8");
        Request request = new Request.Builder()
                .url("http://xxx.xxx.xxx/techcomp/ria/commonProcessor!commonMethod.action")
                .post(RequestBody.create(MEDIA_TYPE_JSON, dc.getBytes("UTF8")))
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("ajaxRequest", "true")
                .addHeader("Content-Type", "application/json")
                .build();
        OkHttpClient client = MyOKHttpClient.getInstance();
        Response response = client.newCall(request).execute();
        LOGGER.debug(response.body().string());
    }


    /**
     * psdmis实施管理 -- 费用管理
     *
     * @throws IOException
     */
    @Test(enabled = true, dependsOnMethods = "testLoginAPI")
    public void testCaseInfo() throws IOException {
        String dc = "{header:{\"code\":0,\"message\":{\"title\":\"\",\"detail\":\"\"}},body:{dataStores:{},parameters:{\"dcID\":\"management\",\"nom\":\"getCaseList\",\"moduleName\":\"manager\",\"Number\":1,\"size\":20,\"_parameters\":\"moduleName,Number,size\",\"_parameterTypes\":\"string,string,string\",\"_boId\":\"management_caseManageBO_bo\",\"_methodName\":\"GetCaseList\",\"_methodParameterTypes\":\"String,int,int\"}}}";

        MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=UTF-8");
        Request request = new Request.Builder()
                .url("http://xxx.xxx.xxx/techcomp/ria/commonProcessor!commonMethod.action?t=1505361749806")
                .post(RequestBody.create(MEDIA_TYPE_JSON, dc.getBytes("UTF8")))
                .addHeader("X-Requested-With", "XMLHttpRequest")
                .addHeader("ajaxRequest", "true")
                .addHeader("Content-Type", "application/json")
                .build();
        OkHttpClient client = MyOKHttpClient.getInstance();
        Response response = client.newCall(request).execute();
        LOGGER.debug(response.body().string());
    }
}
