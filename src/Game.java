
public class Game {
	public Level currentLevel = new Level();
	public MovableElem character = new MovableElem();
	public MovableElem boxes [] = new MovableElem [20];
	public int nbBoxes = 0;
	
	Game() {
		currentLevel.initLevel();
		
		for(int i = 0; i < 15; i++) {
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
}
