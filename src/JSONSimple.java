import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;

public class JSONSimple {
	
	@SuppressWarnings("unchecked")
	public static void savePlayers(Player[] players, int nbPlayer){
		
        JSONObject obj =  new JSONObject();
        JSONArray playerList = new JSONArray();
        JSONObject player;
        JSONArray scoreList;
        
        for(int i = 0; i < nbPlayer; i++) {
        	player = new JSONObject();
        	player.put("pseudo", players[i].getPseudo());
        	
        	scoreList = new JSONArray();
        	for(int j = 0; j < players[i].getNextLevelToPass(); j++) {
        		scoreList.add(players[i].getLevelScore(j));
        	}
        	player.put("scoreList", scoreList);
        	
        	player.put("nextLevelToPass", players[i].getNextLevelToPass());
        	
        	playerList.add(player);
        }
        
        obj.put("playerTab", playerList);
        
        try(FileWriter file = new FileWriter("save\\players.json")){
            file.write(obj.toString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
	
	public static void loadPlayers(Game game) {
        JSONParser parser = new JSONParser();
        Player players [] = new Player[Game.nbMaxPlayer];
        int nbPlayer = 0;

        try{
        	JSONObject obj = (JSONObject) parser.parse(new FileReader("save\\players.json"));
            
        	JSONArray JSONPlayers = (JSONArray) obj.get("playerTab");
        	
        	for(int i = 0; i < JSONPlayers.size(); i++) {
        		JSONObject player = (JSONObject) JSONPlayers.get(i);
        		players[i] = new Player();
        		players[i].setPseudo((String) player.get("pseudo"));
        		
        		
        		JSONArray JSONScore = (JSONArray) player.get("scoreList");
        		for(int j = 0; j < JSONScore.size(); j++) {
        			players[i].setLevelScore(j, ((Long) JSONScore.get(j)).intValue());
        		}
        		
        		players[i].setNextLevelToPass(((Long) player.get("nextLevelToPass")).intValue());
        		
        		nbPlayer++;
        	}
        }
        catch(FileNotFoundException e) {e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}
        catch(ParseException e){e.printStackTrace();}
        catch(Exception e){e.printStackTrace();}
        
        game.setPlayers(players);
        game.setNbPlayer(nbPlayer);
    }
}
