import java.awt.*;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

//Panel class is used to display the different elements we want to show
public class Panel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	//We need to pass Game to Panel to access it
	public Game game;
	
	//The different fonts for the game
	private Font fontGeneral = new Font(Font.SANS_SERIF, Font.BOLD, 18);
	private Font fontTitle = new Font(Font.SANS_SERIF, Font.BOLD, 30);
	private Font fontBigTitle = new Font(Font.SANS_SERIF, Font.BOLD, 42);
	private Font fontSmall = new Font(Font.SANS_SERIF, Font.TRUETYPE_FONT, 18);
	
	//The different textures for the game
	private Image imgLogo = null;
	private Image imgPosition = null;
	private Image imgBox = null;
	private Image imgBoxInPosition = null;
	private Image imgLock = null;
	private Image imgLevelIcon [] = new Image [Game.nbLevels];
	private Image imgWalls [] = new Image [Game.nbSceneries];
	private Image characters [] = new Image [Game.nbCharacters];
	
	
	Panel(Game game){
		super();
		this.game = game;
		
		//Set game background
		this.setBackground(Color.white);
		
		//Loading images
		try {
			imgLogo = ImageIO.read(new File("data\\logo.png"));
		    imgPosition = ImageIO.read(new File("data\\texture\\position.png"));
		    imgBox = ImageIO.read(new File("data\\texture\\box.jpg"));
		    imgBoxInPosition = ImageIO.read(new File("data\\texture\\boxInPosition.jpg"));
		    imgLock = ImageIO.read(new File("data\\level-icons\\lock.jpg"));
		    
		    //Levels icons for the menu
		    for(int i = 0; i < Game.nbLevels; i++) {
		    	imgLevelIcon[i] = ImageIO.read(new File("data\\level-icons\\level-" + (i+1) + ".png"));
		    }
		    
		    //Walls textures
		    for(int i = 0; i < Game.nbSceneries; i++) {
		    	imgWalls[i] = ImageIO.read(new File("data\\texture\\wall_" + i + ".png"));
		    }
		    
		    //Characters images
		    for(int i = 0; i < Game.nbCharacters; i++) {
		    	characters[i] = ImageIO.read(new File("data\\texture\\character_" + i + ".png"));
		    }
		    
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//paintComponent procedure display the different elements on the panel
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		//Initialization of fonts metric sizes
		FontMetrics fontMetricsGeneral = g2d.getFontMetrics(fontGeneral);
		FontMetrics fontMetricsTitle = g2d.getFontMetrics(fontTitle);
		FontMetrics fontMetricsBigTitle = g2d.getFontMetrics(fontBigTitle);
		FontMetrics fontMetricsSmall = g2d.getFontMetrics(fontSmall);
		
		if(!game.inMenu) { //We display the game if we're not in the menu
			double blockSize = 30; //Block size in window percentage
			int widthLevelStart = (int) ((this.getWidth() - (game.getCurrentLevel().getLevelWidth()*blockSize))/2); //The width where we begin to display the level for it to be at the center
			int heightLevelStart = 150; //The height were we begin to display the level
			
			//Display level id
			g2d.setFont(fontTitle);
			g2d.setColor(Color.red);
		    String levelNb = "Niveau " + ((game.getCurrentLevelId()+1) < 10 ? "0" : "") + (game.getCurrentLevelId()+1);
		    g2d.drawString(levelNb, (int) (this.getWidth() - fontMetricsTitle.stringWidth(levelNb))/2, 40);
			
		    //Display player best score
		    g2d.setFont(fontGeneral);
			g2d.setColor(Color.black);
		    g2d.drawString("Votre meilleur score : " + (game.getCurrentPlayer().getLevelScore(game.getCurrentLevelId()) != -1 ? game.getCurrentPlayer().getLevelScore(game.getCurrentLevelId()) : "Aucun"), (this.getWidth()/2)-240, 80);
		    
		    //Display level best score
		    g2d.drawString("Meilleur score : " + (game.getBestScore(game.getCurrentLevelId()) != -1 ? game.getBestScore(game.getCurrentLevelId()) : "Aucun"), (this.getWidth()/2)+30, 80);
		    
		    //Display actual player score
		    String actualScore = "Score : " + game.getNbMoves();
		    g2d.drawString(actualScore, (int) (this.getWidth() - fontMetricsGeneral.stringWidth(actualScore))/2, 110);
			
			//Display walls and boxes goal positions
			for(int i = 0; i < Level.tabSize; i++) {
				for(int j = 0; j < Level.tabSize; j++) {
					
					switch (game.getCurrentLevel().getLevelCaseIJ(i, j)) {
					case 'X':
						g2d.drawImage(imgWalls[game.getCurrentPlayer().getSceneryChoice()], (int) ((j*blockSize) + widthLevelStart), (int) ((i*blockSize) + heightLevelStart), (int) blockSize, (int) blockSize, this);
						break;
					
					case '.':
						g2d.drawImage(imgPosition, (int) ((j*blockSize) + widthLevelStart), (int) ((i*blockSize) + heightLevelStart), (int) blockSize, (int) blockSize, this);
						break;
						
					default:
						break;
					}
					
				}
			}
			
			//Display character
			g2d.drawImage(characters[game.getCurrentPlayer().getCharacterChoice()], (int) ((game.getCurrentLevel().getCharacter().getPosX()*blockSize) + widthLevelStart), (int) ((game.getCurrentLevel().getCharacter().getPosY()*blockSize) + heightLevelStart), (int) blockSize, (int) blockSize, this);
			
			//Display boxes
			for(int i = 0; i < game.getCurrentLevel().getNbBoxes(); i++) {
				g2d.drawImage(game.getCurrentLevel().getBoxes()[i].IsInPosition() ? imgBoxInPosition : imgBox, (int) ((game.getCurrentLevel().getBoxes()[i].getPosX()*blockSize) + widthLevelStart), (int) ((game.getCurrentLevel().getBoxes()[i].getPosY()*blockSize) + heightLevelStart), (int) blockSize, (int) blockSize, this);
			}
		    
		    //Draw keys info
			g2d.setFont(fontSmall);
		    g2d.setColor(Color.GRAY);
			String inGameKeyInfos = "(Esc) Menu principal  (R) Recommencer le niveau";
		    g2d.drawString(inGameKeyInfos, (int) (this.getWidth() - fontMetricsSmall.stringWidth(inGameKeyInfos))/2, (int) (heightLevelStart + 50 + blockSize * game.getCurrentLevel().getLevelHeight()));
			
			//Display end level pop-in
			if(game.levelEnded) {
				g2d.setColor(Color.black);
				int popinSize = game.getCurrentLevelId() == Game.nbLevels-1 ? 520 : 400;
				int textXPosition = ((this.getWidth()-popinSize)/2)+90;
				
				//We draw the rectangle
				g2d.fillRoundRect(((this.getWidth()-popinSize)/2), (this.getHeight()/2)-100, popinSize, 200, 5, 5);
				
				//We draw the informations
			    g2d.setColor(Color.red);
			    int moveInfoYPosition = (this.getHeight()/2-50);
			    
			    //If it's the last level we display a congratulation message. If not, we display the key for the next level
			    if(game.getCurrentLevelId() == Game.nbLevels-1) {
			    	g2d.drawString("Félicitations ! Vous avez fini tous les niveaux du jeu !", textXPosition-40, (this.getHeight()/2-60));
			    	moveInfoYPosition = (this.getHeight()/2-10);
			    } else {
			    	g2d.setColor(Color.white);
				    g2d.drawString("Niveau suivant (Enter)", textXPosition, (this.getHeight()/2));
			    }
			    
			    g2d.setColor(Color.red);
			    g2d.drawString("Nombre de déplacements : " + game.getNbMoves(), textXPosition, moveInfoYPosition);
			    
			    g2d.setColor(Color.white);
			    g2d.drawString("Recommencer le niveau (R)", textXPosition, (this.getHeight()/2+30));
			    g2d.drawString("Menu principal (Esc)", textXPosition, (this.getHeight()/2+60));
			}
			
		} else { //We display the menu	
			
			switch(game.menu.getCurrentMenuId()) {
				case 1: //Draw main menu
					g2d.drawImage(imgLogo, (this.getWidth()/2)-100, 50, 200, 200, this);
					
					g2d.setFont(fontTitle);
					g2d.setColor(Color.DARK_GRAY);
					
					String loadedPlayerName = "Profil chargé : " + (game.getCurrentPlayerId() != -1 ? game.getCurrentPlayer().getPseudo() : "aucun");
				    g2d.drawString(loadedPlayerName, (int) (this.getWidth() - fontMetricsTitle.stringWidth(loadedPlayerName))/2, 320);
					
				    if(game.menu.getYMenuChoice() == 1) g2d.setColor(Color.red);
					else if(game.getCurrentPlayerId() != -1) g2d.setColor(Color.black);
					else g2d.setColor(Color.LIGHT_GRAY);
				    g2d.drawString("Reprendre", (this.getWidth()/2)-100, 400);

				    if(game.menu.getYMenuChoice() == 2) g2d.setColor(Color.red);
				    else if(game.getCurrentPlayerId() != -1) g2d.setColor(Color.black);
				    else g2d.setColor(Color.LIGHT_GRAY);
				    g2d.drawString("Choix du niveau", (this.getWidth()/2)-100, 450);

				    if(game.menu.getYMenuChoice() == 3) g2d.setColor(Color.red);
				    else g2d.setColor(Color.black);
				    g2d.drawString("Charger joueur", (this.getWidth()/2)-100, 500);
				    
				    if(game.menu.getYMenuChoice() == 4) g2d.setColor(Color.red);
				    else if(game.getCurrentPlayerId() != -1) g2d.setColor(Color.black);
				    else g2d.setColor(Color.LIGHT_GRAY);
				    g2d.drawString("Options", (this.getWidth()/2)-100, 550);

				    if(game.menu.getYMenuChoice() == 5) g2d.setColor(Color.red);
				    else g2d.setColor(Color.black);
				    g2d.drawString("Quitter", (this.getWidth()/2)-100, 600);
				    
					break;
				
				case 2: //Draw level choice menu
					int gap = 20;
					int iconSize = 75;
					int mosaicWidthStart = (this.getWidth() - ((7*iconSize) + (6*gap)))/2;
					int mosaicHeightStart = 150;
					int levelCounter = 0;
					
					//Display level number
				    g2d.setFont(fontTitle);
					g2d.setColor(Color.black);
					int levelId = (game.menu.getYMenuChoice()*7-(7-game.menu.getXMenuChoice()))-1;
					String levelNumber = "Niveau " + ((levelId+1) < 10 ? "0" : "") + (levelId+1);
				    g2d.drawString(levelNumber, (int) (this.getWidth() - fontMetricsTitle.stringWidth(levelNumber))/2, 40);
					
					//Display level best score
					g2d.setFont(fontGeneral);
				    g2d.drawString("Meilleur score : " + (game.getBestScore(levelId) != -1 ? game.getBestScore(levelId) : "Aucun"), (this.getWidth()/2)+50, 80);
				    
				    //Display player best score
				    g2d.drawString("Votre meilleur score : " + (game.getCurrentPlayer().getLevelScore(levelId) != -1 ? game.getCurrentPlayer().getLevelScore(levelId) : "Aucun"), (this.getWidth()/2)-230, 80);
					
					//We change the thickness of the graphic objects
					Stroke oldStroke = g2d.getStroke();
					g2d.setStroke(new BasicStroke(2));
					
					//We draw the level mosaic
					for(int i = 0; i < 7; i++) {
						for(int j = 0; j < 7; j++) {
							
							//If the level isn't already passed by the player, we show a lock image. If he passed it, we show the icon of the level.
							if(game.getCurrentPlayer().getNextLevelToPass() < levelCounter) g2d.drawImage(imgLock, mosaicWidthStart + (gap+iconSize)*j, mosaicHeightStart + (gap+iconSize)*i, iconSize, iconSize, this);
							else g2d.drawImage(imgLevelIcon[levelCounter], mosaicWidthStart + (gap+iconSize)*j, mosaicHeightStart + (gap+iconSize)*i, iconSize, iconSize, this);
							
							//We draw the square around the image
							if(game.menu.getXMenuChoice() == j+1 && game.menu.getYMenuChoice() == i+1) g2d.setColor(Color.red);
							else g2d.setColor(Color.black);
							g2d.drawRect(mosaicWidthStart + (gap+iconSize)*j, mosaicHeightStart + (gap+iconSize)*i, iconSize, iconSize);
							levelCounter++;
						}
					}
					
					g2d.setStroke(oldStroke); //We restore the old stroke
					
					//Draw key info
					g2d.setFont(fontSmall);
				    g2d.setColor(Color.GRAY);
					String keysInfo = "(Esc) Menu principal";
				    g2d.drawString(keysInfo, (int) (this.getWidth() - fontMetricsSmall.stringWidth(keysInfo))/2, 850);
					
					break;
				
				case 3: //Draw load player menu
					//Draw menu title
					g2d.setColor(Color.black);
					g2d.setFont(fontBigTitle);
					String loadMenuTitle = "Charger un joueur";
				    g2d.drawString(loadMenuTitle, (int) (this.getWidth() - fontMetricsBigTitle.stringWidth(loadMenuTitle))/2, 150);
					
					//Draw the player already load if there is one
					g2d.setFont(fontGeneral);
					g2d.setColor(Color.DARK_GRAY);
				    String playerLoadedName = "Joueur chargé : " + (game.getCurrentPlayerId() != -1 ? game.getCurrentPlayer().getPseudo() : "aucun");
				    g2d.drawString(playerLoadedName, (int) (this.getWidth() - fontMetricsGeneral.stringWidth(playerLoadedName))/2, 200);
					
					//Draw the players pseudo
				    g2d.setFont(fontTitle);
				    g2d.setColor(Color.black);
				    
					for(int i = 0; i < game.getNbPlayers(); i++) {
						if(game.menu.getYMenuChoice() == i+1) g2d.setColor(Color.red);
						else g2d.setColor(Color.black);
						g2d.drawString(game.getPlayer(i).getPseudo(), (this.getWidth()/2)-130, 270+(i*50));
					}
					
					//Draw the choice to add a player only if the number max of players isn't reached
					if(game.getNbPlayers() < Game.nbMaxPlayers) {
						if(game.menu.getYMenuChoice() == game.getNbPlayers()+1) g2d.setColor(Color.red);
						else g2d.setColor(Color.black);
						
						//If the player is writing a pseudo for a new player, we display the pseudo instead of "Add a player"
						if(game.creatingPlayer) {
							g2d.setColor(Color.DARK_GRAY);
							g2d.drawString(game.getNewPlayerPseudo().equals("") ? "Entrer un pseudo" : game.getNewPlayerPseudo(), (this.getWidth()/2)-130, 270+(game.getNbPlayers()*50));
						}
						else g2d.drawString("Ajouter un joueur", (this.getWidth()/2)-130, 270+(game.getNbPlayers()*50));
					}
					
					//Draw keys info
					g2d.setFont(fontSmall);
				    g2d.setColor(Color.GRAY);
					String ldMnKeyInfo = "(Esc) Menu principal  (Del) Supprimer le joueur";
				    g2d.drawString(ldMnKeyInfo, (int) (this.getWidth() - fontMetricsSmall.stringWidth(ldMnKeyInfo))/2, 320+(game.getNbPlayers()*50));
					
					break;
					
				case 4: //Draw options menu
					//Draw menu title
					g2d.setColor(Color.black);
					g2d.setFont(fontBigTitle);
					String optionsMenuTitle = "Options";
				    g2d.drawString(optionsMenuTitle, (int) (this.getWidth() - fontMetricsBigTitle.stringWidth(optionsMenuTitle))/2, 150);
				    
				    //Draw characters choice
					g2d.setColor(Color.black);
					g2d.setFont(fontTitle);
					String characterChoiceTitle = "Choisissez votre personnage :";
				    g2d.drawString(characterChoiceTitle, (int) (this.getWidth() - fontMetricsTitle.stringWidth(characterChoiceTitle))/2, 280);
				    
				    int choiceGap = 20;
					int choiceIconSize = 75;
					int choiceMosaicWidthStart = (this.getWidth() - ((Game.nbCharacters*choiceIconSize) + ((Game.nbCharacters-1)*choiceGap)))/2;
					int choiceMosaicHeightStart = 300;
					
					//We change the thickness of the graphic objects
					Stroke choiceOldStroke = g2d.getStroke();
					g2d.setStroke(new BasicStroke(2));
					
				    for(int i = 0; i < Game.nbCharacters; i++) {
				    	//We draw the characters
				    	if(game.getCurrentPlayer().getCharacterChoice() != i) g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f)); //We change the opacity to show which option is saved
						g2d.drawImage(characters[i], choiceMosaicWidthStart + (choiceGap+choiceIconSize)*i, choiceMosaicHeightStart, choiceIconSize, choiceIconSize, this);
						
						//We draw the square around the image
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); //We restore the opacity
						if(game.menu.getXMenuChoice() == i+1 && game.menu.getYMenuChoice() == 1) g2d.setColor(Color.red);
						else g2d.setColor(Color.black);
						g2d.drawRect(choiceMosaicWidthStart + (choiceGap+choiceIconSize)*i, choiceMosaicHeightStart, choiceIconSize, choiceIconSize);
					}
				    
				    //Draw scenery choice
					g2d.setColor(Color.black);
					g2d.setFont(fontTitle);
					String sceneryChoiceTitle = "Choisissez le décor :";
				    g2d.drawString(sceneryChoiceTitle, (int) (this.getWidth() - fontMetricsTitle.stringWidth(sceneryChoiceTitle))/2, 460);
				    choiceMosaicHeightStart = 480;
					
				    for(int i = 0; i < Game.nbCharacters; i++) {
				    	//We draw the sceneries
				    	if(game.getCurrentPlayer().getSceneryChoice() != i) g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f)); //We change the opacity to show which option is saved
						g2d.drawImage(imgWalls[i], choiceMosaicWidthStart + (choiceGap+choiceIconSize)*i, choiceMosaicHeightStart, choiceIconSize, choiceIconSize, this);
						
						//We draw the square around the image
						g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f)); //We restore the opacity
						if(game.menu.getXMenuChoice() == i+1 && game.menu.getYMenuChoice() == 2) g2d.setColor(Color.red);
						else g2d.setColor(Color.black);
						g2d.drawRect(choiceMosaicWidthStart + (choiceGap+choiceIconSize)*i, choiceMosaicHeightStart, choiceIconSize, choiceIconSize);
					}
				    
				    g2d.setStroke(choiceOldStroke); //We restore the old stroke
				    
				    //Draw key info
					g2d.setFont(fontSmall);
				    g2d.setColor(Color.GRAY);
					String choiceKeysInfo = "(Esc) Menu principal";
				    g2d.drawString(choiceKeysInfo, (int) (this.getWidth() - fontMetricsSmall.stringWidth(choiceKeysInfo))/2, 650);
				    
					break;
			}
			
		}
		
	}
}