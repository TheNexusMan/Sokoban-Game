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
	private Font fontGeneral = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	private Font fontTitle = new Font(Font.SANS_SERIF, Font.BOLD, 30);
	private Image imgWall = null;
	private Image imgPosition = null;
	private Image imgBox = null;
	private Image imgBoxInPosition = null;
	
	Panel(Game game){
		super();
		this.game = game;
		
		//Set game background
		this.setBackground(Color.LIGHT_GRAY); 
		
		//Loading textures
		try {
		    imgWall = ImageIO.read(new File("data\\texture\\wall.png"));
		    imgPosition = ImageIO.read(new File("data\\texture\\position.png"));
		    imgBox = ImageIO.read(new File("data\\texture\\box.jpg"));
		    imgBoxInPosition = ImageIO.read(new File("data\\texture\\boxInPosition.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//paintComponent procedure display the different elements on the panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(fontGeneral);
		
		double blockSize = 3; //Block size in window percentage
		double blockWidth = (blockSize*this.getWidth())/100; //Block width in pixel
		double blockHeight = (blockSize*this.getHeight())/100; //Block height in pixel
		int widthLevelStart = (int) ((this.getWidth() - (game.getLevel().getLevelWidth()*blockWidth))/2); //The width were we begin to display the level at the center
		int heightLevelStart = 150; //The height were we begin to display the level
		
		//Display walls and boxes goal positions
		for(int i = 0; i < Level.tabSize; i++) {
			for(int j = 0; j < Level.tabSize; j++) {
				
				switch (game.getLevel().getLevelCaseIJ(i, j)) {
				case 'X':
					g.drawImage(imgWall, (int) ((j*blockWidth) + widthLevelStart), (int) ((i*blockHeight) + heightLevelStart), (int) blockWidth, (int) blockHeight, this);
					break;
				
				case '.':
					g.drawImage(imgPosition, (int) ((j*blockWidth) + widthLevelStart), (int) ((i*blockHeight) + heightLevelStart), (int) blockWidth, (int) blockHeight, this);
					break;
					
				default:
					break;
				}
				
			}
		}
		
		//Display character
		g.drawImage(game.getLevel().getCharacter().getImgCharacter(), (int) ((game.getLevel().getCharacter().getPosX()*blockWidth) + widthLevelStart), (int) ((game.getLevel().getCharacter().getPosY()*blockHeight) + heightLevelStart), (int) blockWidth, (int) blockHeight, this);
		
		//Display boxes
		for(int i = 0; i < game.getLevel().getNbBoxes(); i++) {
			g.drawImage(game.getLevel().getBoxes()[i].IsInPosition() ? imgBoxInPosition : imgBox, (int) ((game.getLevel().getBoxes()[i].getPosX()*blockWidth) + widthLevelStart), (int) ((game.getLevel().getBoxes()[i].getPosY()*blockHeight) + heightLevelStart), (int) blockWidth, (int) blockHeight, this);
		}
		
		//Display level number
		g.setFont(fontTitle);
		g.setColor(Color.red);          
	    g.drawString("Niveau " + ((game.getCurrentLevelNum()+1) < 10 ? "0" : "") + (game.getCurrentLevelNum()+1), (this.getWidth()/2)-80, 40);
		
		//Display score
	    g.setFont(fontGeneral);
		g.setColor(Color.black);          
	    g.drawString("Score : " + game.getNbMoves(), (this.getWidth()/2)-50, 70);
		
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