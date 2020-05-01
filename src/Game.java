import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Game is the main class
public class Game {
	public static int nbLevel = 49;
	public static int nbMaxPlayer = 10;
	public Window window;
	public Menu menu = new Menu(this);
	private Level levels [] = new Level[nbLevel];
	private Player players [] = new Player[nbMaxPlayer];
	private int nbPlayer = 0;
	private int currentLevelNum = -1;
	private int currentPlayerNum = -1;
	private int nbMoves = 0; //Score (lower is best)
	public boolean inMenu = true;
	public boolean gameOn = false;
	public boolean levelEnded = false;
	
	Game() {
		setCurrentLevelNum(0);
		loadAllLevels();
		loadAllPlayers();
		window = new Window(this);
	}
	
	//Game and level initialization
	public void initGame(int levelNum) {
		setNbMoves(0);
		setCurrentLevelNum(levelNum);
		getLevel().resetLevel();
		gameOn = true;
		levelEnded = false;
	}
	
	//Load all levels from txt file
	private void loadAllLevels() {
		File file = new File("data\\levels\\levels.txt");
		//File file = new File("data\\levels\\levels-easy.txt"); //For development only
		//File file = new File("data\\levels\\levels-fullwall.txt"); //For development only
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
				
				levels[l].setLevelWidth(width+1);
				levels[l].setLevelHeight(i);
				
			} else {
				
				for(int j = 0; j < line.length(); j++) //We go through the line
				{
					if(line.charAt(j) == '*') levels[l].addABox(i, j); //We add a new box
					if(line.charAt(j) == '@') levels[l].createCharacter(i, j); //We save the character position
					levels[l].setLevelCaseIJ(line.charAt(j), i, j); //We save the character in the table
					if(width < j) width = j;
				}
				i++;
				
			}
			
		}
		
		sc.close();
	}
	
	public void loadAllPlayers() {
		int tab1 [] = {150, 199, 70};
		players[0] = new Player("Arnaud", tab1, 3);
		increaseNbPlayer();
		
		players[1] = new Player("Pierre");
		increaseNbPlayer();
		
		int tab2 [] = {135, 141};
		players[2] = new Player("Claire", tab2, 2);
		increaseNbPlayer();
		
		int tab3 [] = {78, 250, 68, 89};
		players[3] = new Player("Morgane", tab3, 4);
		increaseNbPlayer();
		
		int tab4 [] = {256, 232, 98, 121, 560, 785};
		players[4] = new Player("Louis", tab4, 6);
		increaseNbPlayer();
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

	public Level getLevel() {
		return levels[currentLevelNum];
	}
	
	public Level getLevel(int i) {
		return levels[i];
	}
	
	public Player getPlayer() {
		return players[currentPlayerNum];
	}
	
	public Player getPlayer(int i) {
		return players[i];
	}
	
	public int getNbPlayer() {
		return nbPlayer;
	}

	public void setNbPlayer(int nbPlayer) {
		this.nbPlayer = nbPlayer;
	}
	
	public void increaseNbPlayer() {
		nbPlayer++;
	}
	
	public void decreaseNbPlayer() {
		nbPlayer--;
	}

	public int getCurrentLevelNum() {
		return currentLevelNum;
	}

	public void setCurrentLevelNum(int currentLevelNum) {
		this.currentLevelNum = currentLevelNum;
	}
	
	public int getCurrentPlayerNum() {
		return currentPlayerNum;
	}

	public void setCurrentPlayerNum(int currentPlayerNum) {
		this.currentPlayerNum = currentPlayerNum;
	}
	
	public void restartLevel() {
		gameOn = true;
		levelEnded = false;
		setNbMoves(0);
		levels[currentLevelNum].resetLevel();
	}
	
	//Function that move character and boxes when it's possible. It return a boolean to say if the move has been done
	public boolean move(String direction) {
		int casePosX = levels[currentLevelNum].getCharacter().getPosX();
		int casePosY = levels[currentLevelNum].getCharacter().getPosY();
		int secondCasePosX = casePosX;
		int secondCasePosY = casePosY;
		
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
		
		if(levels[currentLevelNum].getLevelCaseXY(casePosX, casePosY) == 'X') return false; //If character in front of a wall = no move
		if(levels[currentLevelNum].isThereABoxInXY(casePosX, casePosY) && levels[currentLevelNum].isThereABoxInXY(secondCasePosX, secondCasePosY)) return false; //If character is in front of two boxes = no move
		if(levels[currentLevelNum].isThereABoxInXY(casePosX, casePosY) && levels[currentLevelNum].getLevelCaseXY(secondCasePosX, secondCasePosY) == 'X') return false; //If character in front of a box followed by a wall = no move
		
		if(levels[currentLevelNum].isThereABoxInXY(casePosX, casePosY)) { //If we pass the previous tests and the character is in front of a box , we can move the box
			if(levels[currentLevelNum].getLevelCaseXY(secondCasePosX, secondCasePosY) == '.') {
				levels[currentLevelNum].getBoxAtXY(casePosX, casePosY).setIsInPosition(true); //If the case behind the moving box is a box position, box's isInPosition become true
				endLevel(); //We test if all the boxes are in position to end level
			} else if(levels[currentLevelNum].getBoxAtXY(casePosX, casePosY).IsInPosition()) levels[currentLevelNum].getBoxAtXY(casePosX, casePosY).setIsInPosition(false); //If the box was in a position and is move on a non-position case, box's IsInPosition become false
			levels[currentLevelNum].getBoxAtXY(casePosX, casePosY).move(direction); //Then the box move
			increaseNbMoves(); //We increase the number of box moves, the "score"
		}
		levels[currentLevelNum].getCharacter().move(direction); //If we reach this point, the character can move
		
		return true;
	}
	
	//If all the boxes are in position, we stop the game and end level
	public void endLevel() {
		if(levels[currentLevelNum].allBoxesInPosition()) {
			gameOn = false;
			levelEnded = true;
		}
	}
	
	//Function to pass to the next level
	public void nextLevel() {
		saveGame();
		levels[currentLevelNum].resetLevel();
		if(currentLevelNum+1 != nbLevel) initGame(currentLevelNum+1);
	}
	
	//Function to save current level informations
	public void saveGame() {
		System.out.println("Save function to do.");
	}
	
	public int getBestScore( int levelNum) {
		int bestScore = -1;
		int playerScore;
		
		for(int i = 0; i < nbPlayer; i++) {
			playerScore = players[i].getLevelScore(levelNum);
			if(playerScore != -1 && bestScore == -1) bestScore = playerScore;
			if(playerScore != -1 && playerScore < bestScore) bestScore = playerScore;
		}
		
		return bestScore;
	}
}
