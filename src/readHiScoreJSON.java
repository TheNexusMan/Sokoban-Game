import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class readHiScoreJSON {
    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        JSONParser parser = new JSONParser();

        try{
            Object obj = parser.parse(new FileReader("highscore.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String hiscore = (String) jsonObject.get("Highscore");
            System.out.println("Current highscore is: " + hiscore);
        }
        catch(FileNotFoundException e) {e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}
        catch(ParseException e){e.printStackTrace();}
        catch(Exception e){e.printStackTrace();}
    }
}
