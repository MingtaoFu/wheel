package me.mingtao.WebFramework;

import me.mingtao.response.Response;
import me.mingtao.utils.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Created by mingtao on 7/30/16.
 */
public class RouterHandle {
    private HashMap<String, Consumer> router = new HashMap<>();
    public void setRouter(String pattern, Consumer callback) {
        this.router.put(pattern, callback);
    }

    public void handle(String url, Context context) {
        Set<String> patterns = this.router.keySet();
        ArrayList params;
        boolean success = false;
        for (String pattern: patterns) {
            if((params = validate(url, pattern)) != null) {
                context.setParams(params);
                try {
                    router.get(pattern).accept(context);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                success = true;
                break;
            }
        }

        if(!success) {
            Response response = context.getResponse();
            response.setStatus_code(404);
            response.setReason_phrase("Not Found");
            response.setResponsetBody("Page not found");
        }
    }

    public static ArrayList validate(String url, String pattern) {
        //remove "/"
        String url2 = url.substring(1);
        String pattern2 = pattern.substring(1);

        String[] url_arr = url2.split("/");
        String[] pattern_arr = pattern2.split("/");

        int len = url_arr.length;
        int len2 = pattern_arr.length;

        if (len != len2) return null;

        for(int i = 0; i < len; i++) {
            if(! url_arr[i].equals(pattern_arr[i])) {
                return null;
            }
        }
        return new ArrayList();
    }
}
