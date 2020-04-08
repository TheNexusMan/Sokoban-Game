import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Box extends MovableElem {
	private Boolean isInPosition;
	
	Box(int i, int j, boolean isInPosition){
		setPosX(j);
		setPosY(i);
		setIsInPosition(isInPosition);
	}
	
	public void setIsInPosition(Boolean position) {
		this.isInPosition = position;
	}
	
	public Boolean IsInPosition() {
		return isInPosition;
	}
}
