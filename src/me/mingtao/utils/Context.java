package me.mingtao.utils;

import me.mingtao.request.Request;
import me.mingtao.response.Response;

import java.util.ArrayList;

/**
 * Created by mingtao on 7/31/16.
 */
public class Context {
    private Request request;
    private Response response;
    private ArrayList params = new ArrayList();

    public Context(Request request, Response response) {
        this.request = request;
        this.response = response;
    }

    public ArrayList getParams() {
        return params;
    }

    public void setParams(ArrayList params) {
        this.params = params;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }
}
