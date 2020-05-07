
//Box class store a box position and it's in a box's place
public class Box extends MovableElem {
	private boolean isInPosition; //Is true if the box is in a box position
	
	Box(int i, int j, boolean isInPosition){
		setPosX(j);
		setPosY(i);
		setInitPosX(j);
		setInitPosY(i);
		setIsInPosition(isInPosition);
	}
	
	public void setIsInPosition(Boolean position) {
		this.isInPosition = position;
	}
	
	//Return true if the box is in position
	public Boolean IsInPosition() {
		return isInPosition;
	}
}
