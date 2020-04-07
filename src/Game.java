
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
				if(currentLevel.getLevelCaseXY(character.getPosX()-1, character.getPosY()) == 'X') return false;
				if(isThereABoxInXY(character.getPosX()-1, character.getPosY()) && isThereABoxInXY(character.getPosX()-2, character.getPosY())) return false;
				if(isThereABoxInXY(character.getPosX()-1, character.getPosY()) && currentLevel.getLevelCaseXY(character.getPosX()-2, character.getPosY()) == 'X') return false;
				if(isThereABoxInXY(character.getPosX()-1, character.getPosY())) {
					getBoxAtXY(character.getPosX()-1, character.getPosY()).moveLeft();
				}
				character.moveLeft();
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
				hasMoved = true;
				break;
		}
		
		return hasMoved;
	}
	
	public boolean isThereABoxInXY(int X, int Y) {
		for(int i = 0; i < nbBoxes; i++) {
			if(boxes[i].getPosX() == X && boxes[i].getPosY() == Y) return true;
		}
		
		return false;
	}
	
	public MovableElem getBoxAtXY(int X, int Y) {
		for(int i = 0; i < nbBoxes; i++) {
			if(boxes[i].getPosX() == X && boxes[i].getPosY() == Y) return boxes[i];
		}
		
		return new MovableElem();
	}
}
