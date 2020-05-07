
//MovableElem is a parent class for Box and Character that are moving objects in the game
public abstract class MovableElem {
	private int posX, posY; //Actual position of the object
	private int initPosX, initPosY; //Initial position of the object
	
	public void setPosX(int x) {
		posX = x;
	}
	
	public int getPosX() {
		return posX;
	}
	
	public void setPosY(int y) {
		posY = y;
	}
	
	public int getPosY() {
		return posY;
	}

	public int getInitPosX() {
		return initPosX;
	}

	public void setInitPosX(int initPosX) {
		this.initPosX = initPosX;
	}

	public int getInitPosY() {
		return initPosY;
	}

	public void setInitPosY(int initPosY) {
		this.initPosY = initPosY;
	}
	
	//Replace the object at its initial position
	public void moveToInitialPos() {
		posX = initPosX;
		posY = initPosY;
	}

	//Move the object according to the direction
	public void move(String direction) {
		switch(direction) {
			case "left":
				posX--;
				break;
				
			case "right":
				posX++;
				break;
				
			case "up":
				posY--;
				break;
				
			case "down":
				posY++;
				break;
		}
	}
}
