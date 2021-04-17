package com.wty.intermediate.design14;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Map;

public class Main {

    public static void main(String[] args) throws UnsupportedEncodingException {
        long start = System.currentTimeMillis();
        FutureClient client = new FutureClient();
        FutureData futureData = client.queryProjectInfo("123");
        Map projectInfo = futureData.getProjectInfo();
        System.out.println(projectInfo);
        long l = System.currentTimeMillis() - start;
        System.out.println(l/1000 + "秒");

        String encode = URLEncoder.encode(projectInfo.toString(), "UTF-8");
        System.out.println(encode);
        String decode = URLDecoder.decode(encode, "UTF-8");
        System.out.println(decode);

    }

}
