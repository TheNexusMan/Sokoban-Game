
//Class to to manage the different game menus
public class Menu {
	private int XMenuChoice; //The horizontal choice in the menu
	private int YMenuChoice; //The vertical choice in the menu
	private int currentMenuId; //The menu we are currently
	public Game game; //We need to pass the game to Menu to access it
	
	Menu(Game game){
		this.game = game;
		XMenuChoice = 1;
		if(game.getCurrentPlayerId() == -1) YMenuChoice = 3;
		else YMenuChoice = 1;
		currentMenuId = 1;
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

	public int getCurrentMenuId() {
		return currentMenuId;
	}

	public void setCurrentMenuId(int currentMenuId) {
		this.currentMenuId = currentMenuId;
	}
	
	//Function to navigate in the menus with keyboard's arrows
	public void menuChoice(String direction) {
		int minXChoice = 0;
		int maxXChoice = 0;
		int minYChoice = 0;
		int maxYChoice = 0;
		
		//The different menu maximum and minimum indexes
		switch(currentMenuId) {
			case 1: //Main menu : there's only 4 choices.
				if(game.getCurrentPlayerId() == -1) minYChoice = 3; //If no player are loaded, only 2 choices are visible "Quitter" and "Charger joueur"
				else minYChoice = 1;
				maxYChoice = 4;
				break;
			
			case 2: //Load level menu : player can only go to levels he has already unlocked
				minXChoice = 1;
				//If the player is on a line that is not full, the maxXChoice is set to the number of levels on this line
				if(YMenuChoice == (game.getCurrentPlayer().getNextLevelToPass()+1)/7+1 && (game.getCurrentPlayer().getNextLevelToPass()+1)%7 != 0) maxXChoice = (game.getCurrentPlayer().getNextLevelToPass()+1)%7;
				else maxXChoice = 7;
				
				minYChoice = 1;
				if((game.getCurrentPlayer().getNextLevelToPass()+1)%7 != 0) maxYChoice = (game.getCurrentPlayer().getNextLevelToPass()+1)/7+1;
				else maxYChoice = (game.getCurrentPlayer().getNextLevelToPass()+1)/7;
				break;
				
			case 3:
				minYChoice = 1;
				if(game.getNbPlayers() < Game.nbMaxPlayers) maxYChoice = game.getNbPlayers()+1; //If the number max of player saved is not reach, maxYChoice is the number of player saved + 1
				else maxYChoice = game.getNbPlayers(); //Else it's just the number of player saved
				break;
		}
		
		switch(direction) {
			case "up":
				if(game.creatingPlayer) { //If the player was writing a new pseudo, the creation is canceled
					game.resetNewPlayerPseudo();
					game.creatingPlayer = false;
				}
				if(YMenuChoice == minYChoice) YMenuChoice = maxYChoice; //If we reach the top of a row, we go to the end
				else YMenuChoice -= 1;
				break;
				
			case "down":
				if(game.creatingPlayer) { //If the player was writing a new pseudo, the creation is canceled
					game.resetNewPlayerPseudo();
					game.creatingPlayer = false;
				}
				if(YMenuChoice == maxYChoice) YMenuChoice = minYChoice; //If we reach the bottom of a row, we go back to the top
				else YMenuChoice += 1;
				break;
			
			case "left":
				if(XMenuChoice == minXChoice) XMenuChoice = maxXChoice; //If we reach the beginning of a line, we go to the end
				else XMenuChoice -= 1;
				break;
				
			case "right":
				if(XMenuChoice == maxXChoice) XMenuChoice = minXChoice; //If we reach the end of a line, we go back to the beginning
				else XMenuChoice += 1;
				break;
		}
		
	}
	
	//The different actions to execute when enter is pressed (depending of the menu)
	public void select() {
		switch(currentMenuId) {
			case 1: //Main menu
				switch (YMenuChoice) {
					case 1 : //If the player chose "Reprendre" we start the newt level the player has to pass
						game.inMenu = false;
						game.initLevel(game.getCurrentPlayer().getNextLevelToPass());
						break;
						
					case 2 : //If the player chose "Choix du niveau" we change the currentMenuId for this one and initialize the XY indexes
						YMenuChoice = 1;
						XMenuChoice = 1;
						currentMenuId = 2;
						break;
					
					case 3 : //If the player chose "Charger joueur" we change the currentMenuId for this one and initialize the Y index
						YMenuChoice = 1;
						currentMenuId = 3;
						break;
						
					case 4 : //If the player chose "Quitter" we quit the program
						System.exit(0);
						break;
				}
				break;
				
			case 2: //Choose level menu
				//Initialize the level chosen by the player
				game.initLevel((YMenuChoice-1)*7 + XMenuChoice - 1);
				
				//We reset all menu parameters
				game.inMenu = false;
				YMenuChoice = 1;
				XMenuChoice = 1;
				currentMenuId = 1;
				break;
				
			case 3: //Load player menu
				if(YMenuChoice <= game.getNbPlayers()) { //If the player press enter on a pseudo, we load the corresponding player
					game.setCurrentPlayerId(YMenuChoice-1);
					game.saveGameToFile();
					YMenuChoice = 1;
					currentMenuId = 1;
				}else if(game.creatingPlayer) { //If the player was creating a new player, pressing enter validate the creation and save the new player
					game.createNewPlayer();
					game.creatingPlayer = false;
				}else {
					game.creatingPlayer = true; //Else we start creating a new player
				}
				break;
		}
	}
	
	//Function to delete a player
	public void deletePlayer() {
		if(getYMenuChoice() != game.getNbPlayers()+1) { //If the key is pressed on a pseudo, the corresponding player is deleted
			game.deletePlayer(game.menu.getYMenuChoice()-1);
		}
	}
	
	//Function to go back to the main menu from a submenu
	public void goBack() {
		if(currentMenuId > 1) {
			if(game.creatingPlayer) { //If the player was writing a new pseudo, the creation is canceled
				game.resetNewPlayerPseudo();
				game.creatingPlayer = false;
			}
			XMenuChoice = 1;
			YMenuChoice = game.getCurrentPlayerId() == -1 ? 3 : currentMenuId; //If no player is loaded, there's only the two last choices in the main menu
			currentMenuId = 1;
		}
	}
}
