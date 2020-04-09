import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

//Panel class is used to display the different elements we want to show
public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	//We need to pass the game to panel to access it
	public Game game;
	Panel(Game game){
		super();
		this.game = game;
	}
	
	Panel(){
		super();
	}
	
	//paintComponent procedure display the different elements on the panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//Set game font and background
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	    g.setFont(font);
		this.setBackground(Color.LIGHT_GRAY); 
		
		//Loading textures
		Image imgWall = null;
		Image imgPosition = null;
		Image imgBox = null;
		Image imgBoxInPosition = null;

		try {
		    imgWall = ImageIO.read(new File("data\\texture\\wall.png"));
		    imgPosition = ImageIO.read(new File("data\\texture\\position.png"));
		    imgBox = ImageIO.read(new File("data\\texture\\box.jpg"));
		    imgBoxInPosition = ImageIO.read(new File("data\\texture\\boxInPosition.jpg"));
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
		g.drawImage(game.getLevel().getCharacter().getImgCharacter(), (int) (game.getLevel().getCharacter().getPosX()*((4*this.getWidth())/100)), (int) (game.getLevel().getCharacter().getPosY()*((4*this.getHeight())/100)), (int) ((4*this.getWidth())/100), (int) ((4*this.getHeight())/100), this);
		
		//Display boxes
		for(int i = 0; i < game.getLevel().getNbBoxes(); i++) {
			g.drawImage(game.getLevel().getBoxes()[i].IsInPosition() ? imgBoxInPosition : imgBox, (int) (game.getLevel().getBoxes()[i].getPosX()*((4*this.getWidth())/100)), (int) (game.getLevel().getBoxes()[i].getPosY()*((4*this.getHeight())/100)), (int) ((4*this.getWidth())/100), (int) ((4*this.getHeight())/100), this);
		}
		
		//Display end level pop-in
		if(game.levelEnded) {
			g.fillRoundRect((this.getWidth()/2)-200, (this.getHeight()/2)-100, 400, 200, 5, 5);
		    g.setColor(Color.red);          
		    g.drawString("Nombre de déplacements : " + game.getNbMoves(), (this.getWidth()/2)-150, (this.getHeight()/2-50));
		    g.setColor(Color.white); 
		    g.drawString("Niveau suivant (Enter)", (this.getWidth()/2)-150, (this.getHeight()/2));
		    g.drawString("Recommencer le niveau (R)", (this.getWidth()/2)-150, (this.getHeight()/2+30));
		}
	}
}