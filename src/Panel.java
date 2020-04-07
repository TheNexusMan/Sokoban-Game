import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

//Panel class is use to display the different elements we want to show
public class Panel extends JPanel {
	
	Game game = new Game();
	
	//paintComponent procedure display the different elements on the panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setBackground(Color.LIGHT_GRAY); 
		
		//Loading textures
		Image imgPlayer = null;
		Image imgWall = null;
		Image imgGround = null;
		Image imgBox = null;
		Image imgPosition = null;

		try {
		    imgPlayer = ImageIO.read(new File("data\\texture\\player.png"));
		    imgWall = ImageIO.read(new File("data\\texture\\wall.png"));
		    imgBox = ImageIO.read(new File("data\\texture\\box.jpg"));
		    imgPosition = ImageIO.read(new File("data\\texture\\position.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		//Display walls and boxes goal positions
		for(int i = 0; i < Level.tabSize; i++) {
			for(int j = 0; j < Level.tabSize; j++) {
				
				switch (game.getLevel().getLevelCaseIJ(i, j)) {
				case 'X':
					g.drawImage(imgWall, (int) (j*((4*this.getWidth())/100)), (int) (i*((4*this.getHeight())/100)), (int) ((4*this.getWidth())/100), (int) ((4*this.getHeight())/100), this);
					break;
				
				case '.':
					g.drawImage(imgPosition, (int) (j*((4*this.getWidth())/100)), (int) (i*((4*this.getHeight())/100)), (int) ((4*this.getWidth())/100), (int) ((4*this.getHeight())/100), this);
					break;
					
				default:
					break;
				}
				
			}
		}
		
		//Display character
		g.drawImage(imgPlayer, (int) (game.getCharacter().getPosX()*((4*this.getWidth())/100)), (int) (game.getCharacter().getPosY()*((4*this.getHeight())/100)), (int) ((4*this.getWidth())/100), (int) ((4*this.getHeight())/100), this);
		
		//Display boxes
		for(int i = 0; i < game.getNbBoxes(); i++) {
			g.drawImage(imgBox, (int) (game.getBoxes()[i].getPosX()*((4*this.getWidth())/100)), (int) (game.getBoxes()[i].getPosY()*((4*this.getHeight())/100)), (int) ((4*this.getWidth())/100), (int) ((4*this.getHeight())/100), this);
		}
	}               
}