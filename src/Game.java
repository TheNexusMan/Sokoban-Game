import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//Game is the main class
public class Game {
	public static int nbLevel = 49;
	public Window window;
	private Level levels [] = new Level[nbLevel];
	private int currentLevelNum;
	private int nbMoves = 0; //Score (lower is best)
	public boolean gameOn = true;
	public boolean levelEnded = false;
	
	Game() {
		setCurrentLevelNum(0);
		loadAllLevels();
		initGame(currentLevelNum);
		window = new Window(this);
	}
	
	//Game and level initialization
	private void initGame(int levelNum) {
		setNbMoves(0);
		setCurrentLevelNum(levelNum);
		gameOn = true;
		levelEnded = false;
	}
	
	//Load all levels from txt file
	private void loadAllLevels() {
		File file = new File("data\\levels\\levels.txt");
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String line;
		int i = 0;
		int l = -1; //level index in levels table
		
		while (sc.hasNextLine()) { //We go through the file as long as we have lines
			line = sc.nextLine();
			
			if(line.equals("break")) { //If we encounter a "break" line, it means we're reading a new level
				i = 0;
				l++;
				levels[l] = new Level();
				line = sc.nextLine(); //line equal next line so we don't save the word "break"
			}
			
			for(int j = 0; j < line.length(); j++) //We go through the line
			{
				if(line.charAt(j) == '*') levels[l].addABox(i, j); //We add a new box
				if(line.charAt(j) == '@') levels[l].createCharacter(i, j); //We save the character position
				levels[l].setLevelCaseIJ(line.charAt(j), i, j);
			}
			
			i++;
		}
		
		sc.close();
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
	
	public int getCurrentLevelNum() {
		return currentLevelNum;
	}

	public void setCurrentLevelNum(int currentLevelNum) {
		this.currentLevelNum = currentLevelNum;
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
		if(levels[currentLevelNum].isThereABoxInXY(casePosX, casePosY) && levels[currentLevelNum].isThereABoxInXY(secondCasePosX, secondCasePosY)) return false; //If character in front of two boxes = no move
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
}
