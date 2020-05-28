import org.json.simple.*;
import org.json.simple.parser.*;

import java.io.*;

public class JSONSimple {
	
	@SuppressWarnings("unchecked")
	//Function to save all the game data in a JSON file
	public static void saveGameToJSON(Player[] players, int nbPlayers, int currentPlayerId){
		
        JSONObject obj =  new JSONObject();
        JSONArray playersList = new JSONArray();
        JSONObject player;
        JSONArray scoreList;
        
        //Save the last player selected
        obj.put("currentPlayerId", currentPlayerId);
        
        //Save all the players
        for(int i = 0; i < nbPlayers; i++) {
        	player = new JSONObject();
        	
        	//Save the player's pseudo
        	player.put("pseudo", players[i].getPseudo());
        	
        	//Save the player scores
        	scoreList = new JSONArray();
        	int maxScore;
        	if(players[i].getNextLevelToPass() == Game.nbLevels-1) maxScore = players[i].getNextLevelToPass()+1;
        	else maxScore = players[i].getNextLevelToPass();
        	
        	for(int j = 0; j < maxScore; j++) {
        		scoreList.add(players[i].getLevelScore(j));
        	}
        	player.put("scoreList", scoreList);
        	
        	//Save the last level unlocked by the player
        	player.put("nextLevelToPass", players[i].getNextLevelToPass());
        	
        	//Save the player's character choice
        	player.put("characterChoice", players[i].getCharacterChoice());
        	
        	//Save the player's scenery choice
        	player.put("sceneryChoice", players[i].getSceneryChoice());
        	
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
        	game.setCurrentPlayerId(((Long) obj.get("currentPlayerId")).intValue());
            
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
        		
        		//Load the player's character choice
        		players[i].setCharacterChoice(((Long) player.get("characterChoice")).intValue());
        		
        		//Load the player's scenery choice
        		players[i].setSceneryChoice(((Long) player.get("sceneryChoice")).intValue());
        		
        		nbPlayers++;
        	}
        }
        catch(FileNotFoundException e) {System.out.println("Aucun fichier de sauvegarde trouvé.");}
        catch(IOException e){e.printStackTrace();}
        catch(ParseException e){e.printStackTrace();}
        catch(Exception e){e.printStackTrace();}
        
        game.setPlayers(players);
        game.setNbPlayers(nbPlayers);
    }
}
