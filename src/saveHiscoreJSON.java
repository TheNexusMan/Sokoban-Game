import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class saveHiscoreJSON {
    @SuppressWarnings("unchecked")
    public static void main(String[] args){
        JSONObject obj =  new JSONObject();
        obj.put("Highscore", "1,200");

        try(FileWriter file = new FileWriter("highscore.json")){
            file.write(obj.toString());
            file.flush();
        }

        catch(IOException e){
            e.printStackTrace();
        }
        System.out.println(obj);
    }
}
