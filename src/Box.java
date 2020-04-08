import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Box extends MovableElem {
	private Boolean isInPosition;
	private Image imgBox = null;
	private Image imgBoxInPosition = null;
	
	Box(int i, int j, boolean isInPosition){
		
		setPosX(j);
		setPosY(i);
		setIsInPosition(isInPosition);
		
		try {
		    setImgBox(ImageIO.read(new File("data\\texture\\box.jpg")));
		    setImgBoxInPosition(ImageIO.read(new File("data\\texture\\boxInPosition.jpg")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Image getImgBox() {
		return imgBox;
	}

	public void setImgBox(Image imgBox) {
		this.imgBox = imgBox;
	}
	
	public Image getImgBoxInPosition() {
		return imgBoxInPosition;
	}

	public void setImgBoxInPosition(Image imgBoxInPosition) {
		this.imgBoxInPosition = imgBoxInPosition;
	}
	
	public void setIsInPosition(Boolean position) {
		this.isInPosition = position;
	}
	
	public Boolean IsInPosition() {
		return isInPosition;
	}
}
