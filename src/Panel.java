import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Stroke;
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
	private Font fontBigTitle = new Font(Font.SANS_SERIF, Font.BOLD, 42);
	private Image imgLogo = null;
	private Image imgWall = null;
	private Image imgPosition = null;
	private Image imgBox = null;
	private Image imgBoxInPosition = null;
	private Image imgLevelIcon [] = new Image [Game.nbLevel];
	private Image imgLock = null;
	
	Panel(Game game){
		super();
		this.game = game;
		
		//Set game background
		this.setBackground(Color.white);
		
		//Loading textures
		try {
			imgLogo = ImageIO.read(new File("data\\logo.png"));
		    imgWall = ImageIO.read(new File("data\\texture\\wall.png"));
		    imgPosition = ImageIO.read(new File("data\\texture\\position.png"));
		    imgBox = ImageIO.read(new File("data\\texture\\box.jpg"));
		    imgBoxInPosition = ImageIO.read(new File("data\\texture\\boxInPosition.jpg"));
		    imgLock = ImageIO.read(new File("data\\level-icons\\lock.jpg"));
		    
		    for(int i = 0; i < Game.nbLevel; i++) {
		    	imgLevelIcon[i] = ImageIO.read(new File("data\\level-icons\\level-" + (i+1) + ".png"));
		    }
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//paintComponent procedure display the different elements on the panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setFont(fontGeneral);
		
		if(!game.inMenu) { //We display the game if we're not int the menu
			double blockSize = 30; //Block size in window percentage
			int widthLevelStart = (int) ((this.getWidth() - (game.getLevel().getLevelWidth()*blockSize))/2); //The width were we begin to display the level at the center
			int heightLevelStart = 150; //The height were we begin to display the level
			
			//Display walls and boxes goal positions
			for(int i = 0; i < Level.tabSize; i++) {
				for(int j = 0; j < Level.tabSize; j++) {
					
					switch (game.getLevel().getLevelCaseIJ(i, j)) {
					case 'X':
						g.drawImage(imgWall, (int) ((j*blockSize) + widthLevelStart), (int) ((i*blockSize) + heightLevelStart), (int) blockSize, (int) blockSize, this);
						break;
					
					case '.':
						g.drawImage(imgPosition, (int) ((j*blockSize) + widthLevelStart), (int) ((i*blockSize) + heightLevelStart), (int) blockSize, (int) blockSize, this);
						break;
						
					default:
						break;
					}
					
				}
			}
			
			//Display character
			g.drawImage(game.getLevel().getCharacter().getImgCharacter(), (int) ((game.getLevel().getCharacter().getPosX()*blockSize) + widthLevelStart), (int) ((game.getLevel().getCharacter().getPosY()*blockSize) + heightLevelStart), (int) blockSize, (int) blockSize, this);
			
			//Display boxes
			for(int i = 0; i < game.getLevel().getNbBoxes(); i++) {
				g.drawImage(game.getLevel().getBoxes()[i].IsInPosition() ? imgBoxInPosition : imgBox, (int) ((game.getLevel().getBoxes()[i].getPosX()*blockSize) + widthLevelStart), (int) ((game.getLevel().getBoxes()[i].getPosY()*blockSize) + heightLevelStart), (int) blockSize, (int) blockSize, this);
			}
			
			//Display level number
			g.setFont(fontTitle);
			g.setColor(Color.red);
		    g.drawString("Niveau " + ((game.getCurrentLevelNum()+1) < 10 ? "0" : "") + (game.getCurrentLevelNum()+1), (this.getWidth()/2)-80, 40);
			
			
		    
		    //Display player best score
		    g.setFont(fontGeneral);
			g.setColor(Color.black);
		    g.drawString("Votre meilleur score : " + (game.getPlayer().getLevelScore(game.getCurrentLevelNum()) != -1 ? game.getPlayer().getLevelScore(game.getCurrentLevelNum()) : "Aucun"), (this.getWidth()/2)-250, 80);
		    
		    //Display level best score
		    g.drawString("Meilleur score : " + (game.getBestScore(game.getCurrentLevelNum()) != -1 ? game.getBestScore(game.getCurrentLevelNum()) : "Aucun"), (this.getWidth()/2)+30, 80);
		    
		    //Display actual player score
		    g.drawString("Score : " + game.getNbMoves(), (this.getWidth()/2)-50, 110);
			
			//Display end level pop-in
			if(game.levelEnded) {
				g.fillRoundRect((this.getWidth()/2)-200, (this.getHeight()/2)-100, 400, 200, 5, 5);
			    g.setColor(Color.red);
			    g.drawString("Nombre de déplacements : " + game.getNbMoves(), (this.getWidth()/2)-150, (this.getHeight()/2-50));
			    g.setColor(Color.white);
			    g.drawString("Niveau suivant (Enter)", (this.getWidth()/2)-150, (this.getHeight()/2));
			    g.drawString("Recommencer le niveau (R)", (this.getWidth()/2)-150, (this.getHeight()/2+30));
			}
		} else { //We display the menu	
			
			switch(game.menu.getMenuOn()) {
				case 1: //Draw main menu
					g.drawImage(imgLogo, (this.getWidth()/2)-100, 50, 200, 200, this);
					
					g.setFont(fontTitle);
					
					if(game.getCurrentPlayerNum() != -1) {
						g.setColor(Color.DARK_GRAY);
					    g.drawString("Joueur : " + game.getPlayer().getPseudo(), (this.getWidth()/2)-100, 320);
						
						if(game.menu.getYMenuChoice() == 1) g.setColor(Color.red);
						else g.setColor(Color.black);
					    g.drawString("Reprendre", (this.getWidth()/2)-100, 400);
					    
					    if(game.menu.getYMenuChoice() == 2) g.setColor(Color.red);
					    else g.setColor(Color.black);
					    g.drawString("Choix du niveau", (this.getWidth()/2)-100, 450);
					}
				    
				    if(game.menu.getYMenuChoice() == 3) g.setColor(Color.red);
				    else g.setColor(Color.black);
				    g.drawString("Charger joueur", (this.getWidth()/2)-100, 500);
				    
				    if(game.menu.getYMenuChoice() == 4) g.setColor(Color.red);
				    else g.setColor(Color.black);
				    g.drawString("Quitter", (this.getWidth()/2)-100, 550);
					break;
				
				case 2: //Draw level choice menu
					int gap = 20;
					int iconSize = 75;
					int mosaicWidthStart = (this.getWidth() - ((7*iconSize) + (7*gap)))/2;
					int mosaicHeightStart = 150;
					int levelCounter = 0;
					
					//We change the thickness of the graphic objects
					Graphics2D g2 = (Graphics2D) g;
					float thickness = 2;
					Stroke oldStroke = g2.getStroke();
					g2.setStroke(new BasicStroke(thickness));
					
					//We draw the level mosaic
					for(int i = 0; i < 7; i++) {
						for(int j = 0; j < 7; j++) {
							if(game.getPlayer().getNextLevelToPass() < levelCounter) g.drawImage(imgLock, mosaicWidthStart + (gap+iconSize)*j, mosaicHeightStart + (gap+iconSize)*i, iconSize, iconSize, this);
							else g.drawImage(imgLevelIcon[levelCounter], mosaicWidthStart + (gap+iconSize)*j, mosaicHeightStart + (gap+iconSize)*i, iconSize, iconSize, this);
							
							if(game.menu.getXMenuChoice() == j+1 && game.menu.getYMenuChoice() == i+1) g.setColor(Color.red);
							else g.setColor(Color.black);
							g.drawRect(mosaicWidthStart + (gap+iconSize)*j, mosaicHeightStart + (gap+iconSize)*i, iconSize, iconSize);
							levelCounter++;
						}
					}
					
					//Display player best score
				    g.setFont(fontGeneral);
					g.setColor(Color.black);
					int numLevel = (game.menu.getYMenuChoice()*7-(7-game.menu.getXMenuChoice()))-1;
					g.drawString("Niveau " + ((numLevel+1) < 10 ? "0" : "") + (numLevel+1), (this.getWidth()/2)-80, 40);
					
					//Display level best score
				    g.drawString("Meilleur score : " + (game.getBestScore(numLevel) != -1 ? game.getBestScore(numLevel) : "Aucun"), (this.getWidth()/2)+30, 80);
				    
				    //Display player best score
				    g.drawString("Votre meilleur score : " + (game.getPlayer().getLevelScore(numLevel) != -1 ? game.getPlayer().getLevelScore(numLevel) : "Aucun"), (this.getWidth()/2)-250, 80);
					break;
				
				case 3: //Draw load player menu
					g.setColor(Color.black);
					g.setFont(fontBigTitle);
					g.drawString("Charger un joueur :", (this.getWidth()/2)-200, 150);
					
					g.setFont(fontTitle);
					
					for(int i = 0; i < game.getNbPlayer(); i++) {
						if(game.menu.getYMenuChoice() == i+1) g.setColor(Color.red);
						else g.setColor(Color.black);
						g.drawString(game.getPlayer(i).getPseudo(), (this.getWidth()/2)-100, 250+(i*50));
					}
					if(game.menu.getYMenuChoice() == game.getNbPlayer()+1) g.setColor(Color.red);
					else g.setColor(Color.black);
					g.drawString("Ajouter joueur", (this.getWidth()/2)-100, 250+(game.getNbPlayer()*50));
					break;
			}
			
		}
		
	}
}