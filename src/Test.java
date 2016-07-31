/**
 * Created by mingtao on 7/31/16.
 */
import me.mingtao.Server;
import me.mingtao.request.Request;
import me.mingtao.response.Response;
import me.mingtao.utils.Context;

import java.util.function.Consumer;

public class Test{
    public static void main(String [] args) {
        Server app = new Server();
        app.setPORT(8000);


        app.setRouter("/usr2", new Consumer() {
            @Override
            public void accept(Object o) {
                Context context = (Context) o;
                Request request = context.getRequest();
                Response response = context.getResponse();

                response.setResponsetBody(context.getRequest().getHttp_version());
            }
        });

        app.setRouter("/usr2/a", new Consumer() {
            @Override
            public void accept(Object o) {
                Context context = (Context) o;
                Request request = context.getRequest();
                Response response = context.getResponse();

                response.setResponsetBody("{a:2}\n");
                response.setContent_type("application/json");
            }
        });

        app.run();
    }
}
