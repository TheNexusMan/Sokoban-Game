import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
 
public class Panel extends JPanel {
	
	Game game = new Game();
	
	Panel(){
		
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		this.setBackground(Color.LIGHT_GRAY); 
		
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
		
		for(int i = 0; i < 25; i++) {
			for(int j = 0; j < 25; j++) {
				
				switch (game.currentLevel.tabLevel[i][j]) {
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
		
		g.drawImage(imgPlayer, (int) (game.character.getPosX()*((4*this.getWidth())/100)), (int) (game.character.getPosY()*((4*this.getHeight())/100)), (int) ((4*this.getWidth())/100), (int) ((4*this.getHeight())/100), this);
		
		for(int i = 0; i < game.nbBoxes; i++) {
			g.drawImage(imgBox, (int) (game.boxes[i].getPosX()*((4*this.getWidth())/100)), (int) (game.boxes[i].getPosY()*((4*this.getHeight())/100)), (int) ((4*this.getWidth())/100), (int) ((4*this.getHeight())/100), this);
		}
	}               
}