import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;

public class JSONSimple {
	
	@SuppressWarnings("unchecked")
	//Function to save all the game data in a JSON file
	public static void saveGameToJSON(Player[] players, int nbPlayers, int currentPlayerNum){
		
        JSONObject obj =  new JSONObject();
        JSONArray playersList = new JSONArray();
        JSONObject player;
        JSONArray scoreList;
        
        //Save the last player selected
        obj.put("currentPlayerNum", currentPlayerNum);
        
        //Save all the players
        for(int i = 0; i < nbPlayers; i++) {
        	player = new JSONObject();
        	
        	//Save the player's pseudo
        	player.put("pseudo", players[i].getPseudo());
        	
        	//Save the player scores
        	scoreList = new JSONArray();
        	for(int j = 0; j < players[i].getNextLevelToPass(); j++) {
        		scoreList.add(players[i].getLevelScore(j));
        	}
        	player.put("scoreList", scoreList);
        	
        	//Save the last level unlocked by the player
        	player.put("nextLevelToPass", players[i].getNextLevelToPass());
        	
        	playersList.add(player);
        }
        
        obj.put("playersTab", playersList);
        
        //Write the JSON object in the JSON file
        try(FileWriter file = new FileWriter("data\\saveFiles\\save.json")){
            file.write(obj.toString());
            file.flush();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
	
	//Function to load all the game data from a JSON file
	public static void loadGameFromJSON(Game game) {
        JSONParser parser = new JSONParser();
        Player players [] = new Player[Game.nbMaxPlayers];
        int nbPlayers = 0;

        try{
        	JSONObject obj = (JSONObject) parser.parse(new FileReader("data\\saveFiles\\save.json"));
        	
        	//Load the last player selected
        	game.setCurrentPlayerId(((Long) obj.get("currentPlayerNum")).intValue());
            
        	//Load all the players
        	JSONArray JSONPlayers = (JSONArray) obj.get("playersTab");
        	
        	for(int i = 0; i < JSONPlayers.size(); i++) {
        		JSONObject player = (JSONObject) JSONPlayers.get(i);
        		players[i] = new Player();
        		
        		//Load the player's pseudo
        		players[i].setPseudo((String) player.get("pseudo"));
        		
        		//Load the player scores
        		JSONArray JSONScore = (JSONArray) player.get("scoreList");
        		for(int j = 0; j < JSONScore.size(); j++) {
        			players[i].setLevelScore(j, ((Long) JSONScore.get(j)).intValue());
        		}
        		
        		//Load the last level unlocked by the player
        		players[i].setNextLevelToPass(((Long) player.get("nextLevelToPass")).intValue());
        		
        		nbPlayers++;
        	}
        }
        catch(FileNotFoundException e) {e.printStackTrace();}
        catch(IOException e){e.printStackTrace();}
        catch(ParseException e){e.printStackTrace();}
        catch(Exception e){e.printStackTrace();}
        
        game.setPlayers(players);
        game.setNbPlayers(nbPlayers);
    }
}
