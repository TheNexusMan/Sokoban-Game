import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Game is the main class
public class Game {
	public static int nbLevels = 49; //Number maximum of levels
	public static int nbMaxPlayers = 10; //Number maximum of players
	public static int nbCharacters = 4; //Number of characters
	public static int nbSceneries = 4; //Number of wall textures
	public Window window;
	public Menu menu;
	private Level levels [] = new Level[nbLevels]; //Levels list
	private Player players []; //Players list
	private int nbPlayers = 0; //Number of players in the list
	private int currentLevelId = -1; //Id of the current level
	private int currentPlayerId = -1; //Id of the current player
	private int nbMoves = 0; //Score (lower is best)
	private String newPlayerPseudo = ""; //New player pseudo
	public boolean inMenu = true; //Boolean to know if the player is in a menu
	public boolean gameOn = false; //Boolean to know if the player is playing
	public boolean levelEnded = false; //Boolean to know if the the level is finished (to display the pop-in)
	public boolean creatingPlayer = false; //Boolean to know if a new player is being created
	
	Game() {
		setCurrentLevelId(0);
		loadAllLevels(); //Load all the levels from the text file
		loadGameFromFile(); //Load all game parameters and players from the JSON file
		menu = new Menu(this);
		window = new Window(this);
	}
	
	public int getNbMoves() {
		return nbMoves;
	}

	public void setNbMoves(int nbMoves) {
		this.nbMoves = nbMoves;
	}
	
	public void increaseNbMoves() {
		this.nbMoves++;
	}

	public Level getCurrentLevel() {
		return levels[currentLevelId];
	}
	
	public Level getLevel(int i) {
		return levels[i];
	}
	
	public Player[] getPlayers() {
		return players;
	}
	
	public void setPlayers(Player [] players) {
		this.players = players;
	}
	
	public Player getCurrentPlayer() {
		return players[currentPlayerId];
	}
	
	public Player getPlayer(int i) {
		return players[i];
	}
	
	public int getNbPlayers() {
		return nbPlayers;
	}

	public void setNbPlayers(int nbPlayers) {
		this.nbPlayers = nbPlayers;
	}
	
	public void increaseNbPlayers() {
		nbPlayers++;
	}
	
	public void decreaseNbPlayers() {
		nbPlayers--;
	}

	public int getCurrentLevelId() {
		return currentLevelId;
	}

	public void setCurrentLevelId(int currentLevelId) {
		this.currentLevelId = currentLevelId;
	}
	
	public int getCurrentPlayerId() {
		return currentPlayerId;
	}

	public void setCurrentPlayerId(int currentPlayerId) {
		this.currentPlayerId = currentPlayerId;
	}
	
	public String getNewPlayerPseudo() {
		return newPlayerPseudo;
	}
	
	public void setNewPlayerPseudo(String newPlayerPseudo) {
		this.newPlayerPseudo = newPlayerPseudo;
	}
	
	public void resetNewPlayerPseudo() {
		newPlayerPseudo = "";
	}
	
	//Game and level initialization
	public void initLevel(int levelId) {
		setNbMoves(0);
		setCurrentLevelId(levelId);
		getCurrentLevel().resetLevel();
		gameOn = true;
		levelEnded = false;
	}
	
	//Load all levels from text file
	private void loadAllLevels() {
		File file = new File("data\\levels\\levels.txt");
//		File file = new File("data\\levels\\levels-easy.txt"); //For development only
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		int i = 0;
		int l = -1; //level index in levels table
		int width = 0; //Level width
		
		while (sc.hasNextLine()) { //We go through the file as long as we have lines
			line = sc.nextLine();
			
			if(line.equals("next")) { //If we encounter a "next" line, it means we've started to read a level
				i = 0;
				l++;
				width = 0;
				levels[l] = new Level();
			} else if(line.equals("break")) { //If we encounter a "break" line, it means we've finished to read a level
				
				levels[l].setLevelWidth(width);
				levels[l].setLevelHeight(i);
				
			} else {
				
				for(int j = 0; j < line.length(); j++) { //We go through the line
					if(line.charAt(j) == '*') levels[l].addABox(i, j); //We add a new box
					if(line.charAt(j) == '@') levels[l].createCharacter(i, j); //We save the character position
					levels[l].setLevelCaseIJ(line.charAt(j), i, j); //We save the character in the table
				}
				
				if(line.length() > width) width = line.length(); //We calculate the length of the level
				i++;
				
			}
			
		}
		
		sc.close();
	}
	
	//Function to restart the current level
	public void restartLevel() {
		gameOn = true;
		levelEnded = false;
		setNbMoves(0);
		levels[currentLevelId].resetLevel();
	}
	
	//Function that move character and boxes when it's possible. It return a boolean to say if the move has been done
	public boolean move(String direction) {
		int casePosX = levels[currentLevelId].getCharacter().getPosX();
		int casePosY = levels[currentLevelId].getCharacter().getPosY();
		int secondCasePosX = casePosX;
		int secondCasePosY = casePosY;
		
		//The first and second case coordinates to test according to the move direction
		switch(direction) {
			case "left":
				casePosX--;
				secondCasePosX -= 2;
				break;
				
			case "right":
				casePosX++;
				secondCasePosX += 2;
				break;
				
			case "up":
				casePosY--;
				secondCasePosY -= 2;
				break;
				
			case "down":
				casePosY++;
				secondCasePosY += 2;
				break;
		}
		
		if(levels[currentLevelId].getLevelCaseXY(casePosX, casePosY) == 'X') return false; //If character in front of a wall = no move
		if(levels[currentLevelId].isThereABoxInXY(casePosX, casePosY) && levels[currentLevelId].isThereABoxInXY(secondCasePosX, secondCasePosY)) return false; //If character is in front of two boxes = no move
		if(levels[currentLevelId].isThereABoxInXY(casePosX, casePosY) && levels[currentLevelId].getLevelCaseXY(secondCasePosX, secondCasePosY) == 'X') return false; //If character in front of a box followed by a wall = no move
		
		if(levels[currentLevelId].isThereABoxInXY(casePosX, casePosY)) { //If we pass the previous tests and the character is in front of a box , we can move the box
			if(levels[currentLevelId].getLevelCaseXY(secondCasePosX, secondCasePosY) == '.') {
				levels[currentLevelId].getBoxAtXY(casePosX, casePosY).setIsInPosition(true); //If the case behind the moving box is a box position, box's isInPosition become true
			} else if(levels[currentLevelId].getBoxAtXY(casePosX, casePosY).IsInPosition()) levels[currentLevelId].getBoxAtXY(casePosX, casePosY).setIsInPosition(false); //If the box was in a position and is moved on a non-position case, box's IsInPosition become false
			levels[currentLevelId].getBoxAtXY(casePosX, casePosY).move(direction); //Then the box move
			increaseNbMoves(); //We increase the number of box moves, the "score"
			if(levels[currentLevelId].allBoxesInPosition()) endLevel(); //We test if all the boxes are in position to end level
		}
		levels[currentLevelId].getCharacter().move(direction); //If we reach this point, the character can move
		
		return true;
	}
	
	//Function to end the current level
	public void endLevel() {
		saveScore();
		gameOn = false;
		levelEnded = true;
	}
	
	//Function to pass to the next level
	public void nextLevel() {
		levels[currentLevelId].resetLevel();
		if(currentLevelId+1 != nbLevels) initLevel(currentLevelId+1); //We pass to the next level if it's not the last one
	}
	
	//Function to save the game to a JSON file
	public void saveGameToFile() {
		JSONSimple.saveGameToJSON(players, nbPlayers, currentPlayerId);
	}
	
	//Function to load the game from a JSON file
	public void loadGameFromFile() {
		JSONSimple.loadGameFromJSON(this);
	}
	
	//Save the score if it's better or if it's the first time the player do the level.
	public void saveScore() {
		int playerActualScore = getCurrentPlayer().getLevelScore(currentLevelId);
		
		if(playerActualScore == -1 || nbMoves < playerActualScore) {
			if(playerActualScore == -1 && currentLevelId != nbLevels-1) getCurrentPlayer().setNextLevelToPass(currentLevelId+1);
			getCurrentPlayer().setLevelScore(currentLevelId, nbMoves);
			saveGameToFile();
		}
	}
	
	//Function to return the best score of a level
	public int getBestScore(int levelId) {
		int bestScore = -1;
		int playerScore;
		
		for(int i = 0; i < nbPlayers; i++) {
			playerScore = players[i].getLevelScore(levelId);
			if(playerScore != -1 && (bestScore == -1 || playerScore < bestScore)) bestScore = playerScore; //If player score exist (not -1) and is lower than all the previous score, we save it as the bestScore
		}
		
		return bestScore;
	}
	
	//Function to construct the new player nickname
	public void createNewPlayerPseudo(KeyEvent newKey) {
		if(newKey.getKeyCode() == KeyEvent.VK_BACK_SPACE && !newPlayerPseudo.equals("")) newPlayerPseudo = (new StringBuilder(newPlayerPseudo)).deleteCharAt(newPlayerPseudo.length()-1).toString(); //Delete a char if we press delete key
		else {
			char newChar = newKey.getKeyChar();
			if(isAGoodChar(newChar)) newPlayerPseudo += newChar; //Add a new char if it's acceptable
		}
	}
	
	//Function to test if a char is acceptable in a pseudo
	public boolean isAGoodChar(char newChar) {
		if(newChar > 31 && newChar < 127) return true;
		return false;
	}
	
	//Function to add a new player
	public void createNewPlayer() {
		players[nbPlayers] = new Player(newPlayerPseudo);
		nbPlayers++;
		resetNewPlayerPseudo();
		saveGameToFile();
	}
	
	//Function to delete a player
	public void deletePlayer(int idPlayer) {
		for(int i = idPlayer; i < nbPlayers-1; i++) { //To delete a player, we just erase it with the next one in the list
			players[i] = players[i+1];
		}
		players[nbPlayers-1] = null; //The last one need to become null so we don't have two time the same player
		nbPlayers--;
		
		if(currentPlayerId == idPlayer) setCurrentPlayerId(-1); //If we delete the current player, there's no more player loaded.
		else if(currentPlayerId > idPlayer) currentPlayerId--; //If we delete a player with an id lower than the current player, we decrease current player id
		
		saveGameToFile();
	}
}
