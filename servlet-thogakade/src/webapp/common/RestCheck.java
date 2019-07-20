package webapp.common;

import com.google.gson.Gson;

import java.io.PrintWriter;

public class RestCheck {

    public static void checkResult(Gson gson, PrintWriter writer, int rst) {
        if (rst==1){
            String s = gson.toJson(true);
            writer.write(s);
        }else {
            String s = gson.toJson(false);
            writer.write(s);
        }
    }
}
