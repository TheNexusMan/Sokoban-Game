
//Level class is used to store a level
public class Level {
	public static int tabSize = 30;
	public static int boxTabSize = 40;
	private char tabLevel[][] = new char[tabSize][tabSize]; //Where the level is loaded
	private Character character;
	private int nbBoxes = 0;
	private Box boxes [] = new Box [boxTabSize];
	
	public void createCharacter(int i, int j) {
		character = new Character(i, j);
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
	
	public void addABox(int i, int j) {
		boxes[nbBoxes] = new Box(i, j, false);
		nbBoxes++;
	}
	
	//Two functions to access to tabLevel to avoid confusion between XY axes and ij indexes
	public void setLevelCaseIJ(char c, int i, int j) {
		tabLevel[i][j] = c;
	}
	
	public char getLevelCaseIJ(int i, int j) {
		return tabLevel[i][j];
	}
	
	public void setLevelCaseXY(char c, int X, int Y) {
		tabLevel[Y][X] = c;
	}
	
	public char getLevelCaseXY(int X, int Y) {
		return tabLevel[Y][X];
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
	
	//Function to test if all the boxes are in position
	public boolean allBoxesInPosition() {
		for(int i = 0; i < nbBoxes; i++) {
			if(!boxes[i].IsInPosition()) return false;
		}
		return true;
	}
	
	
	//Function to replace every box and the character to their initials places
	public void resetLevel() {
		character.moveToInitialPos();
		for(int i = 0; i < nbBoxes; i++) {
			boxes[i].moveToInitialPos();
			boxes[i].setIsInPosition(false);
		}
	}
	
	//For debug only, show the level as it is loaded in the table
	public void showTable() {
		for(int i = 0; i < tabSize; i++) {
			for(int j = 0; j < tabSize; j++) {
				System.out.print(tabLevel[i][j]);
			}
			System.out.print("\r");
		}
	}
}
