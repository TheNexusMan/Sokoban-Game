
public class Menu {
	private int XMenuChoice; //The horizontal choice in the menu
	private int YMenuChoice; //The vertical choice in the menu
	private int menuOn; //The menu we are currently
	public Game game;
	
	Menu(Game game){
		this.game = game;
		XMenuChoice = 1;
		YMenuChoice = 3;
		menuOn = 1;
	}
	
	public int getXMenuChoice() {
		return XMenuChoice;
	}

	public void setXMenuChoice(int XMenuChoice) {
		this.XMenuChoice = XMenuChoice;
	}
	
	public int getYMenuChoice() {
		return YMenuChoice;
	}

	public void setYMenuChoice(int menuChoice) {
		this.YMenuChoice = menuChoice;
	}

	public int getMenuOn() {
		return menuOn;
	}

	public void setMenuOn(int menuOn) {
		this.menuOn = menuOn;
	}
	
	//Function to navigate in the menus with keyboard's arrows
	public void menuChoice(String direction) {
		int minXChoice = 0;
		int maxXChoice = 0;
		int minYChoice = 0;
		int maxYChoice = 0;
		
		switch(menuOn) {
			case 1: //Main menu : there's only 4 choices. If no player are loaded, only 2 choices are visible "Quitter" and "Charger joueur"
				if(game.getCurrentPlayerNum() == -1) minYChoice = 3;
				else minYChoice = 1;
				maxYChoice = 4;
				break;
			
			case 2: //Load level menu : player can only go to levels he has already unlocked
				minXChoice = 1;
				if(YMenuChoice == (game.getCurrentPlayer().getNextLevelToPass()+1)/7+1 && (game.getCurrentPlayer().getNextLevelToPass()+1)%7 != 0) maxXChoice = (game.getCurrentPlayer().getNextLevelToPass()+1)%7;
				else maxXChoice = 7;
				
				minYChoice = 1;
				maxYChoice = (game.getCurrentPlayer().getNextLevelToPass()+1)/7+1;
				break;
				
			case 3:
				minYChoice = 1;
				maxYChoice = game.getNbPlayer()+1;
				break;
		}
		
		switch(direction) {
			case "up":
				if(YMenuChoice == minYChoice) YMenuChoice = maxYChoice;
				else YMenuChoice -= 1;
				break;
				
			case "down":
				if(YMenuChoice == maxYChoice) YMenuChoice = minYChoice;
				else YMenuChoice += 1;
				break;
			
			case "left":
				if(XMenuChoice == minXChoice) XMenuChoice = maxXChoice;
				else XMenuChoice -= 1;
				break;
				
			case "right":
				if(XMenuChoice == maxXChoice) XMenuChoice = minXChoice;
				else XMenuChoice += 1;
				break;
		}
		
	}
	
	public void select() {
		switch(menuOn) {
			case 1:
				switch (YMenuChoice) {
					case 1 :
						game.inMenu = false;
						game.initGame(game.getCurrentPlayer().getNextLevelToPass());
						break;
						
					case 2 :
						YMenuChoice = 1;
						XMenuChoice = 1;
						menuOn = 2;
						break;
					
					case 3 :
						YMenuChoice = 1;
						menuOn = 3;
						break;
						
					case 4 :
						game.window.dispose();
						break;
				}
				break;
				
			case 2:
				game.initGame((YMenuChoice-1)*7 + XMenuChoice - 1);
				game.inMenu = false;
				YMenuChoice = 1;
				XMenuChoice = 1;
				menuOn = 1;
				break;
				
			case 3:
				if(YMenuChoice <= game.getNbPlayer()) {
					game.setCurrentPlayerNum(YMenuChoice-1);
					YMenuChoice = 1;
					menuOn = 1;
				}
				break;
		}
	}
	
	public void goBack() {
		System.out.println(menuOn);
		if(menuOn > 1) {
			menuOn = 1;
			XMenuChoice = 1;
			YMenuChoice = game.getCurrentPlayerNum() == -1 ? 3 : 1;
		}
	}
}
