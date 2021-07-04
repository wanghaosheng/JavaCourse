package com.whs.course002;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class HttpClientDemo {
    public static void main(String[] args) throws IOException {
        //打开浏览器
        HttpClient httpClient = HttpClients.createDefault();
        //声明访问路径
        //HttpGet httpGet = new HttpGet("http://www.baidu.com");
        HttpGet httpGet = new HttpGet("http://localhost:8801");
        //发送请求
        HttpResponse httpResponse =  httpClient.execute(httpGet);
        //处理返回信息
        if(httpResponse.getStatusLine().getStatusCode() == 200){
            HttpEntity entity = httpResponse.getEntity();
            String string = EntityUtils.toString(entity,"utf-8");
            System.out.println(string);
        }
    }
}
