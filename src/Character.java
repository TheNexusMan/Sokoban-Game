import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Character extends MovableElem {
	private Image imgCharacter = null; //Character's image
	
	Character(){
		try {
		    setImgCharacter(ImageIO.read(new File("data\\texture\\character.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Image getImgCharacter() {
		return imgCharacter;
	}

	public void setImgCharacter(Image imgCharacter) {
		this.imgCharacter = imgCharacter;
	}
}
