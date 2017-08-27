package com.yinhao.googleplay.http.protocol;


import android.util.Log;

import com.yinhao.googleplay.util.IOUtils;
import com.yinhao.googleplay.util.StringUtils;
import com.yinhao.googleplay.util.UIUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by yinhao on 2017/8/25.
 * 访问网络的基类
 */

public abstract class BaseProtocol<T> {
    private static final String TAG = "BaseProtocol";

    public static final String BASE_URL = "http://127.0.0.1:8090/";

    public T getData(int index) {
        //先判断是否有缓存，有的话就加载缓存
        String cache = getCache(index);
        if (StringUtils.isEmpty(cache)) {//如果没有缓存或者缓存失效
            //请求服务器
            cache = getDataFromServer(index);
        }

        //开启解析
        if (cache != null) {
            T data = parseData(cache);
            return data;
        }
        return null;
    }

    /**
     * 写缓存
     * url为key,json为value
     */
    public void setCache(int index, String json) {
        //以url为文件名，以json为文件内容，保存在本地
        File cacheDir = UIUtils.getContext().getCacheDir();
        //生成缓存文件
        File file = new File(cacheDir, getKey() + "?index=" + index + getParams());//文件夹，文件名
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            long deadline = System.currentTimeMillis() + 30 * 60 * 1000;//半个小事有效期，截止时间
            //写入json
            writer.write(String.valueOf(deadline) + "\n");//在第一行写入缓存时间
            writer.write(json);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.close(writer);
        }
    }

    /**
     * 读缓存
     */
    public String getCache(int index) {
        //以url为文件名，以json为文件内容，保存在本地
        File cacheDir = UIUtils.getContext().getCacheDir();
        //生成缓存文件
        File file = new File(cacheDir, getKey() + "?index=" + index + getParams());//文件夹，文件名
        if (file.exists()) {
            //还有判断日期是否有效
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(new FileReader(cacheDir));
                String deadline = reader.readLine();
                long deadtime = Long.parseLong(deadline);
                if (System.currentTimeMillis() < deadtime) {//当前时间小于截止时间，说明有效
                    String line;
                    StringBuffer sb = new StringBuffer();
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    return sb.toString();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                IOUtils.close(reader);
            }
        }
        return null;
    }

    /**
     * @param index 从哪个位置开始返回数据，用于加载分页浏览
     */
    private String getDataFromServer(int index) {
        OkHttpClient okHttpClient = new OkHttpClient();
        final Request request = new Request.Builder().url(BASE_URL + getKey() + "?index=" + index + getParams()).build();
        if (request != null) {

            Call call = okHttpClient.newCall(request);
            try {
                Response response = call.execute();
                String result = response.body().string();
                Log.e(TAG, "getDataFromServer: 获取结果" + result );
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    public abstract T parseData(String cache);

    public abstract String getParams();

    public abstract String getKey();
}
