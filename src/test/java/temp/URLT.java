package temp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;

public class URLT {

    @Test
    public void t() throws IOException {
        URL url = new URL("https://jsonplaceholder.typicode.com/todos/1");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        BufferedReader r = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuffer temp = new StringBuffer();
        String t;
        
        while((t = r.readLine())!=null) {
            temp.append(t);
        }
        
        

        System.out.println(temp.toString());
        con.disconnect();
        r.close();

    }
}

class Todo{
    public String userId;
    public String id;
    public String title;
    public boolean completed;
}
