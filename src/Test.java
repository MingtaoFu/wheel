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


        app.setRouter("/usr2/<float>", new Consumer() {
            @Override
            public void accept(Object o) {
                Context context = (Context) o;
                Response response = context.getResponse();

                Float number = (Float) context.getParams().get(0);

                response.setResponsetBody("float: " + number + "\n");
            }
        });

        app.setRouter("/usr2/<int>", new Consumer() {
            @Override
            public void accept(Object o) {
                Context context = (Context) o;
                Response response = context.getResponse();

                Integer number = (Integer) context.getParams().get(0);
                response.setResponsetBody("int: " + number + "\n");
            }
        });

        app.setRouter("/usr2/<string>", new Consumer() {
            @Override
            public void accept(Object o) {
                Context context = (Context) o;
                Response response = context.getResponse();

                String number = (String) context.getParams().get(0);
                response.setResponsetBody("string: "+ number + "\n");
            }
        });

        app.setRouter("/<int>/<float>/<string>", new Consumer() {
            @Override
            public void accept(Object o) {
                Context context = (Context) o;
                Response response = context.getResponse();

                Integer number1 = (Integer) context.getParams().get(0);
                Float number2 = (Float) context.getParams().get(1);
                String number3 = (String) context.getParams().get(2);
                response.setResponsetBody(number1 + " " + number2 + " " +number3);
            }
        });

        app.run();
    }
}
