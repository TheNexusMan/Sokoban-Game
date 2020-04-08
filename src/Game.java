import javax.swing.JPanel;

public class Game {
	public static int boxTabSize = 20;
	private int currentLevelNum;
	private Level currentLevel = new Level();
	private int nbMoves = 0; //Score (lower is best)
	private Character character = new Character();
	private int nbBoxes = 0;
	private Box boxes [] = new Box [boxTabSize];
	
	Game() {
		currentLevelNum = 1;
		initGame(currentLevelNum);
	}
	
	//Game and level initialisation
	private void initGame(int levelNum) {
		currentLevel.initLevel(levelNum);

		//Loading of the initial positions of the character and boxes
		for(int i = 0; i < Level.tabSize; i++) {
			for(int j = 0; j < Level.tabSize; j++) {
				
				switch (this.currentLevel.getLevelCaseIJ(i, j)) {
				case '@':
					character.setPosX(j);
					character.setPosY(i);
					break;
				
				case '*':
					boxes[nbBoxes] = new Box(i, j, false);
					nbBoxes++;
					break;
					
				default:
					break;
				}
			}
		}
	}
	
	public Level getLevel() {
		return currentLevel;
	}
	
	public Character getCharacter() {
		return character;
	}
	
	public int getNbBoxes() {
		return nbBoxes;
	}
	
	public Box [] getBoxes() {
		return boxes;
	}
	
	public void restartLevel() {
		nbMoves = 0;
		nbBoxes = 0;
		
		//Loading of the initial positions of the character and boxes
		for(int i = 0; i < Level.tabSize; i++) {
			for(int j = 0; j < Level.tabSize; j++) {
				
				switch (this.currentLevel.getLevelCaseIJ(i, j)) {
				case '@':
					character.setPosX(j);
					character.setPosY(i);
					break;
				
				case '*':
					boxes[nbBoxes].setPosX(j);
					boxes[nbBoxes].setPosY(i);
					boxes[nbBoxes].setIsInPosition(false);
					nbBoxes++;
					break;
				}
			}
		}
	}
	
	//Function that move character and boxes when it's possible. It return a boolean to say if the move has been done
	public boolean move(String direction) {
		
		boolean hasMoved = false;
		int casePosX = character.getPosX();
		int casePosY = character.getPosY();
		int secondCasePosX = character.getPosX();
		int secondCasePosY = character.getPosY();
		
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
		
		if(currentLevel.getLevelCaseXY(casePosX, casePosY) == 'X') return false; //If character in front of a wall = no move
		if(isThereABoxInXY(casePosX, casePosY) && isThereABoxInXY(secondCasePosX, secondCasePosY)) return false; //If character in front of two boxes = no move
		if(isThereABoxInXY(casePosX, casePosY) && currentLevel.getLevelCaseXY(secondCasePosX, secondCasePosY) == 'X') return false; //If character in front of a box followed by a wall = no move
		
		if(isThereABoxInXY(casePosX, casePosY)) { //If we pass the previous tests and the character is in front of a box = we move the box
			if(currentLevel.getLevelCaseXY(secondCasePosX, secondCasePosY) == '.') getBoxAtXY(casePosX, casePosY).setIsInPosition(true); //If the case behind the moving box is a box position, box's isInPosition become true
			else if(getBoxAtXY(casePosX, casePosY).IsInPosition()) getBoxAtXY(casePosX, casePosY).setIsInPosition(false); //If the box was in a position and is move on a non position case, box's IsInPosition become false
			getBoxAtXY(casePosX, casePosY).move(direction); //Then the box move
		}
		character.move(direction); //If we reach this point, the character can move
		nbMoves++; //We increase the number of moves, the "score"
		hasMoved = true;
		
		return hasMoved;
	}
	
	//Function to test if there is a box in position (X,Y)
	public boolean isThereABoxInXY(int X, int Y) {
		for(int i = 0; i < nbBoxes; i++) {
			if(boxes[i].getPosX() == X && boxes[i].getPosY() == Y) return true;
		}
		
		return false;
	}
	
	//Function to get the box in position (X,Y)
	public Box getBoxAtXY(int X, int Y) {
		for(int i = 0; i < nbBoxes; i++) {
			if(boxes[i].getPosX() == X && boxes[i].getPosY() == Y) return boxes[i];
		}
		
		return new Box(0, 0, false);
	}
	
	public void endLevel() {
		JPanel EndPanel = new JPanel();
		EndPanel.setFocusable(true);
		EndPanel.addKeyListener(new EndLevelKeyListener(this));
	}
	
	public void nextLevel() {
		System.out.println("Next level function to do.");
	}
}
