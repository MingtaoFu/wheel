package me.mingtao.response;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by mingtao on 7/31/16.
 */
public class Response {
    private int status_code;
    private String reason_phrase;
    private String content_type;
    private HashMap<String, String> responseHeader = new HashMap<String, String>();
    private String responseBody;
    private OutputStream outputStream;


    private static final int BUFFER_SIZE = 1024;

    public Response(OutputStream outputStream) {
        this.status_code = 200;
        this.reason_phrase = "success";
        this.outputStream = outputStream;
        this.content_type = "text/html";
    }

    public void send() {
        String errorMessage = "HTTP/1.1 " + status_code + " " + reason_phrase + "\r\n" +
                "Content-Type: " + this.content_type + "\r\n" +
                "Content-Length: " + this.responseBody.length() + "\r\n" +
                "\r\n" +
                this.responseBody;
        try {
            outputStream.write(errorMessage.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getContent_type() {
        return content_type;
    }

    public void setContent_type(String content_type) {
        this.content_type = content_type;
    }

    private String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getReason_phrase() {
        return reason_phrase;
    }

    public void setReason_phrase(String reason_phrase) {
        this.reason_phrase = reason_phrase;
    }

    public HashMap<String, String> getResponseHeader() {
        return responseHeader;
    }

    public void setResponseHeader(HashMap<String, String> responseHeader) {
        this.responseHeader = responseHeader;
    }

    public String getResponsetBody() {
        return responseBody;
    }

    public void setResponsetBody(String responsetBody) {
        this.responseBody = responsetBody;
    }


}
