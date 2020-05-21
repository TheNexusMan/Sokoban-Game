import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

//Character class store character position and image
public class Character extends MovableElem {
	
	Character(int i, int j) {
		setPosX(j);
		setPosY(i);
		setInitPosX(j);
		setInitPosY(i);
	}
}
