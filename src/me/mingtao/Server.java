package me.mingtao;

import me.mingtao.WebFramework.RouterHandle;
import me.mingtao.request.Request;
import me.mingtao.response.Response;
import me.mingtao.utils.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.function.Consumer;

/**
 * Created by mingtao on 7/20/16.
 */
public class Server {
    private static int PORT = 8080;
    private static int BACKLOG = 1;
    private static ServerSocket serverSocket;
    private static boolean shutdown = false;
    private RouterHandle routerHandle = new RouterHandle();

    public void setRouter(String pattern, Consumer callback) {
        this.routerHandle.setRouter(pattern, callback);
    }

    public void setPORT(int PORT) {
        Server.PORT = PORT;
    }

    private static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        //创建一个监听
        try {
            serverSocket = new ServerSocket(PORT, BACKLOG, InetAddress.getLocalHost());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (!shutdown) {
            //并非一直响应
            //sleep(100);

            Socket socket;
            InputStream inputStream;
            OutputStream outputStream;
            try {
                socket = serverSocket.accept();
                inputStream = socket.getInputStream();
                outputStream = socket.getOutputStream();

                //包装一个请求对象
                Request request = new Request(inputStream);
                Response response = new Response(outputStream);
                //将request, response 加入上下文
                Context context = new Context(request, response);
                routerHandle.handle(request.getUrl(), context);

                response.send();
                socket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
