
public class Game {
	public Level currentLevel = new Level();
	public int score = 0;
	public MovableElem character = new MovableElem();
	public MovableElem boxes [] = new MovableElem [20];
	public int nbBoxes = 0;
	
	Game() {
		currentLevel.initLevel();
		
		for(int i = 0; i < 25; i++) {
			for(int j = 0; j < 25; j++) {
				
				switch (this.currentLevel.tabLevel[i][j]) {
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
	
	public boolean move(String direction) {
		
		boolean hasMoved = false;
		
		switch(direction) {
			case "left":
				if(canMove(direction)) {
					hasMoved = true;
					character.moveLeft();
				}
				break;
			case "right":
				if(canMove(direction)) {
					hasMoved = true;
					character.moveRight();
				}
				break;
			case "up":
				if(canMove(direction)) {
					hasMoved = true;
					character.moveUp();
				}
				break;
			case "down":
				if(canMove(direction)) {
					hasMoved = true;
					character.moveDown();
				}
				break;
		}
		
		return hasMoved;
	}
	
	public boolean canMove(String direction) {
		switch(direction) {
			case "left":
				if(currentLevel.getLevelCase(character.getPosY(), character.getPosX()-1) == 'X') return false;
				break;
			case "right":
				if(currentLevel.getLevelCase(character.getPosY(), character.getPosX()+1) == 'X') return false;
				break;
			case "up":
				if(currentLevel.getLevelCase(character.getPosY()-1, character.getPosX()) == 'X') return false;
				break;
			case "down":
				if(currentLevel.getLevelCase(character.getPosY()+1, character.getPosX()) == 'X') return false;
				break;
		}
		
		return true;
	}
}
