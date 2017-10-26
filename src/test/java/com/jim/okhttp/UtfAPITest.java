package com.jim.okhttp;

import com.jim.mybatis.utils.MyOKHttpClient;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

/**
 * Created by jim on 2017/9/8.
 * UTF API 测试类
 */
@Test
public class UtfAPITest {
    private static final Logger LOGGER = LoggerFactory.getLogger(UtfAPITest.class);

    /**
     * 登录utf
     *
     * @throws IOException
     */
    @Test(enabled = false)
    public void testLoginAPI() throws IOException {
        RequestBody requestBody = new FormBody.Builder()
                .add("username", "admin")
                .add("password", "*****")
                .build();

        Request request = new Request.Builder()
                .url("http://xxx.xxx.xxx/utf/user/login")
                .post(requestBody)
                .build();

        OkHttpClient client = MyOKHttpClient.getInstance();

        Response response = client.newCall(request).execute();
        LOGGER.debug(response.body().string());
    }

    /**
     * 取得登录用户对应的菜单
     *
     * @throws IOException
     */
    @Test(enabled = false, dependsOnMethods = {"testLoginAPI"})
    public void testGetMenus() throws IOException {
        Request request = new Request.Builder()
                .url("http://xxx.xxx.xxx/utf/menu/getMenus")
                .build();
        OkHttpClient client = MyOKHttpClient.getInstance();
        Response response = client.newCall(request).execute();
        LOGGER.debug(response.body().string());
    }

    private JSONArray users;

    /**
     * 取得在线用户
     *
     * @throws IOException
     */
    @Test(enabled = false, dependsOnMethods = {"testLoginAPI"})
    public void testOnlineAPI() throws IOException {
        Request request = new Request.Builder()
                .url("http://xxx.xxx.xxx/utf//admin/userManager/online")
                .get()
                .build();

        OkHttpClient client = MyOKHttpClient.getInstance();
        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());

        users = (JSONArray) jsonObject.get("result");
    }

    /**
     * 在线用户强制下线
     *
     * @throws IOException
     */
    @Test(enabled = false, dependsOnMethods = {"testOnlineAPI"})
    public void testOnlineKickOffAPI() throws IOException {
        OkHttpClient client = MyOKHttpClient.getInstance();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody requestBody = RequestBody.create(JSON, users.toString());
        Request request = new Request.Builder()
                .url("http://xxx.xxx.xxx/utf//admin/userManager/kickoff")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();
        LOGGER.debug(response.body().string());
    }

    @Test(enabled = false, dependsOnMethods = {"testLoginAPI"})
    public void uploadFile() throws IOException {
        MediaType MEDIA_TYPE_IMAGE = MediaType.parse("image/jpg");
        Random random = new Random();
        int MAX = 5;
        int fileName = random.nextInt(MAX) + 1;
        File file = new File("D:\\utf-mobile\\utf-mobile-web\\src\\main\\webapp\\static\\assets\\img\\SM A8000 screen wallpaper\\" + fileName + ".jpg");
        LOGGER.debug(file.getAbsolutePath());
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("file", "1.jpg", RequestBody.create(MEDIA_TYPE_IMAGE, file))
                .addFormDataPart("description", "change mobile screen")
                .build();

        Request request = new Request.Builder()
                .url("http://xxx.xxx.xxx/utf/admin/equipmentManage/upload/8a84b5b05e5a32f8015e6ea92afb0066/LENOVO%20Lenovo%20X3c70")
                .post(requestBody)
                .build();

        OkHttpClient client = MyOKHttpClient.getInstance();
        Response response = client.newCall(request).execute();

        LOGGER.debug(response.body().string());
    }

    @Test(enabled = false, dependsOnMethods = {"testLoginAPI"})
    public void downloadFile() throws IOException {
        Request request = new Request.Builder()
                .url("http://xxx.xxx.xxx/utf/projectApplicationManager/apps/download/8a84b5b05e5a32f8015e5f3a66a40054")
                .addHeader("Content-Type", "application/json")
                .get()
                .build();

        OkHttpClient client = MyOKHttpClient.getInstance();
        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        String downloadLink = jsonObject.getString("result");

        String baseUrl = "http://xxx.xxx.xxx/utf";
        String fileUrl = baseUrl + downloadLink;
        request = new Request.Builder()
                .url(fileUrl)
                .build();

        response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            LOGGER.debug(response.message());
        }

        String temp[] = downloadLink.split("/");
        String originFileName = temp[temp.length - 1];

        FileOutputStream fos = new FileOutputStream("./" + originFileName);
        fos.write(response.body().bytes());
        fos.close();

    }

    @Test(enabled = true)
    public void test12306() throws IOException, KeyManagementException, NoSuchAlgorithmException {
        Request request = new Request.Builder()
                .url("https://cn.bing.com/search?q=12306&qs=n&form=QBLH&sp=-1&pq=12306&sc=8-5&sk=&cvid=8AFB07ED6EAF449C81F41D05C17734C2")
                .get()
                .build();

        OkHttpClient client = MyOKHttpClient.getSSLInstance();
        Response response = client.newCall(request).execute();
        LOGGER.debug(response.body().string());
    }
}
