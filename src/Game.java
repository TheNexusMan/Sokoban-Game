
public class Game {
	private int currentLevelNum;
	private Level currentLevel = new Level();
	private int nbMoves = 0; //Score (lower is best)
	private MovableElem character = new MovableElem();
	private int nbBoxes = 0;
	private MovableElem boxes [] = new MovableElem [20];
	
	Game() {
		currentLevelNum = 1;
		initGame(currentLevelNum);
	}
	
	//Game and level initialisation
	private void initGame(int levelNum) {
		currentLevel.initLevel(levelNum);

		//Loading of the initial positions of the character and boxes
		for(int i = 0; i < 25; i++) {
			for(int j = 0; j < 25; j++) {
				
				switch (this.currentLevel.getLevelCaseIJ(i, j)) {
				case '@':
					character.setPosX(j);
					character.setPosY(i);
					character.setType("character");
					break;
				
				case '*':
					boxes[nbBoxes] = new MovableElem();
					boxes[nbBoxes].setPosX(j);
					boxes[nbBoxes].setPosY(i);
					boxes[nbBoxes].setIsInPosition(false);
					boxes[nbBoxes].setType("box");
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
	
	public MovableElem getCharacter() {
		return character;
	}
	
	public int getNbBoxes() {
		return nbBoxes;
	}
	
	public MovableElem [] getBoxes() {
		return boxes;
	}
	
	public void restartLevel() {
		nbMoves = 0;
		nbBoxes = 0;
		
		//Loading of the initial positions of the character and boxes
		for(int i = 0; i < 25; i++) {
			for(int j = 0; j < 25; j++) {
				
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
		
		switch(direction) {
			case "left":
				if(currentLevel.getLevelCaseXY(character.getPosX()-1, character.getPosY()) == 'X') return false; //If character in front of a wall = no move
				if(isThereABoxInXY(character.getPosX()-1, character.getPosY()) && isThereABoxInXY(character.getPosX()-2, character.getPosY())) return false; //If character in front of two boxes = no move
				if(isThereABoxInXY(character.getPosX()-1, character.getPosY()) && currentLevel.getLevelCaseXY(character.getPosX()-2, character.getPosY()) == 'X') return false; //If character in front of a box followed by a wall = no move
				if(isThereABoxInXY(character.getPosX()-1, character.getPosY())) { //If we pass the previous tests and the character is in front of a box = we move the box
					getBoxAtXY(character.getPosX()-1, character.getPosY()).moveLeft();
				}
				character.moveLeft(); //If we reach this point, the character can move
				nbMoves++; //We increase the number of moves, the "score"
				hasMoved = true;
				break;
				
			case "right":
				if(currentLevel.getLevelCaseXY(character.getPosX()+1, character.getPosY()) == 'X') return false;
				if(isThereABoxInXY(character.getPosX()+1, character.getPosY()) && isThereABoxInXY(character.getPosX()+2, character.getPosY())) return false;
				if(isThereABoxInXY(character.getPosX()+1, character.getPosY()) && currentLevel.getLevelCaseXY(character.getPosX()+2, character.getPosY()) == 'X') return false;
				if(isThereABoxInXY(character.getPosX()+1, character.getPosY())) {
					getBoxAtXY(character.getPosX()+1, character.getPosY()).moveRight();
				}
				character.moveRight();
				nbMoves++;
				hasMoved = true;
				break;
				
			case "up":
				if(currentLevel.getLevelCaseXY(character.getPosX(), character.getPosY()-1) == 'X') return false;
				if(isThereABoxInXY(character.getPosX(), character.getPosY()-1) && isThereABoxInXY(character.getPosX(), character.getPosY()-2)) return false;
				if(isThereABoxInXY(character.getPosX(), character.getPosY()-1) && currentLevel.getLevelCaseXY(character.getPosX(), character.getPosY()-2) == 'X') return false;
				
				if(isThereABoxInXY(character.getPosX(), character.getPosY()-1)) {
					getBoxAtXY(character.getPosX(), character.getPosY()-1).moveUp();
				}
				character.moveUp();
				nbMoves++;
				hasMoved = true;
				break;
				
			case "down":
				if(currentLevel.getLevelCaseXY(character.getPosX(), character.getPosY()+1) == 'X') return false;
				if(isThereABoxInXY(character.getPosX(), character.getPosY()+1) && isThereABoxInXY(character.getPosX(), character.getPosY()+2)) return false;
				if(isThereABoxInXY(character.getPosX(), character.getPosY()+1) && currentLevel.getLevelCaseXY(character.getPosX(), character.getPosY()+2) == 'X') return false;
				
				if(isThereABoxInXY(character.getPosX(), character.getPosY()+1)) {
					getBoxAtXY(character.getPosX(), character.getPosY()+1).moveDown();
				}
				character.moveDown();
				nbMoves++;
				hasMoved = true;
				break;
		}
		
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
	public MovableElem getBoxAtXY(int X, int Y) {
		for(int i = 0; i < nbBoxes; i++) {
			if(boxes[i].getPosX() == X && boxes[i].getPosY() == Y) return boxes[i];
		}
		
		return new MovableElem();
	}
}
