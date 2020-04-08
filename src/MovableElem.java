
public abstract class MovableElem {
	private int posX, posY;
	
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
