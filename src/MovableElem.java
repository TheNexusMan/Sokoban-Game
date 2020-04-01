
public class MovableElem {
	private int posX, posY;
	private String type;
	private Boolean isInPosition;
	
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
	
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
	
	public void setIsInPosition(Boolean position) {
		this.isInPosition = position;
	}
	
	public Boolean getIsInPosition() {
		return isInPosition;
	}
	
	public void moveUp() {
		posY--;
	}
	
	public void moveDown() {
		posY++;
	}
	
	public void moveLeft() {
		posX--;
	}
	
	public void moveRight() {
		posX++;
	}
	
}
