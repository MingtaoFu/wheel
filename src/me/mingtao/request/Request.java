package me.mingtao.request;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mingtao on 7/20/16.
 */
public class Request {
    private String method;
    private String url;
    private String http_version;
    private HashMap<String, String> requestHeader = new HashMap<String, String>();
    private HashMap<String, String> requestBody = new HashMap<String, String>();

    private void parserHeader(String str) {
        String[] arr = str.split("(: |\\r\\n)");
        int len = arr.length;
        try {
            for (int i = 0; i < len; i += 2) {
                this.requestHeader.put(arr[i], arr[i + 1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void parserBody(String str) {
        String[] arr = str.split("(&|=)");
        int len = arr.length;
        if(str.equals("")) return;
        try {
            for (int i = 0; i < len; i += 2) {
                this.requestHeader.put(arr[i], arr[i + 1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMethod() {
        return method;
    }

    public String getUrl() {
        return url;
    }

    public String getHttp_version() {
        return http_version;
    }

    public String getHeader(String key) {
        return requestHeader.get(key);
    }

    public Request(InputStream inputStream) {
        int length;
        byte[] buffer = new byte[2048];
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

            while (true) {
                length = inputStream.read(buffer);
                byteArrayOutputStream.write(buffer, 0, length);
                if(length < 2048) break;
            }

            //inputStream.close();

            String str = new String(byteArrayOutputStream.toByteArray(), "UTF-8");

            String reg = "(?<method>TRACE|OPTIONS|HEAD|POST|GET|DELETE|PUT) (?<url>\\/[\\S]*) (?<version>HTTP\\/[0-9]+\\.[0-9]+)\\r\\n" +
                    "(?<header>((?!\\r\\n\\r\\n)[\\w\\W])*)\\r\\n\\r\\n(?<entity>[\\w\\W]*)";
            Pattern pattern = Pattern.compile(reg);
            Matcher matcher = pattern.matcher(str);

            if(matcher.find()) {
                this.method = matcher.group("method");
                this.url = matcher.group("url");
                this.http_version = matcher.group("version");
                this.parserBody(matcher.group("entity"));
                this.parserHeader(matcher.group("header"));
            } else {
                new Exception("报文错误").printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
