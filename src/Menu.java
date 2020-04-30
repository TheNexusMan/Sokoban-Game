
public class Menu {
	private int verticalMenuChoice; //The horizontal choice in the menu
	private int horizontalMenuChoice; //The horizontal choice in the menu
	private int menuOn; //The menu we are currently
	public Game game;
	
	Menu(Game game){
		this.game = game;
		verticalMenuChoice = 3;
		horizontalMenuChoice = 1;
		menuOn = 1;
	}

	public int getVerticalMenuChoice() {
		return verticalMenuChoice;
	}

	public void setVerticalMenuChoice(int menuChoice) {
		this.verticalMenuChoice = menuChoice;
	}
	
	public int getHorizontalMenuChoice() {
		return horizontalMenuChoice;
	}

	public void setHorizontalMenuChoice(int horizontalMenuChoice) {
		this.horizontalMenuChoice = horizontalMenuChoice;
	}

	public int getMenuOn() {
		return menuOn;
	}

	public void setMenuOn(int menuOn) {
		this.menuOn = menuOn;
	}
	
	public void menuChoice(String direction) {
		int minChoice = 0;
		int maxChoice = 0;
		
		switch(menuOn) {
			case 1:
				if(game.getCurrentPlayer() == -1) minChoice = 3;
				else minChoice = 1;
				maxChoice = 4;
				break;
			
			case 2:
				minChoice = 1;
				maxChoice = 7;
				break;
				
			case 3:
				minChoice = 1;
				maxChoice = game.getNbPlayer()+1;
				break;
		}
		
		switch(direction) {
			case "up":
				if(verticalMenuChoice == minChoice) verticalMenuChoice = maxChoice;
				else verticalMenuChoice -= 1;
				break;
				
			case "down":
				if(verticalMenuChoice == maxChoice) verticalMenuChoice = minChoice;
				else verticalMenuChoice += 1;
				break;
			
			case "left":
				if(horizontalMenuChoice == minChoice) horizontalMenuChoice = maxChoice;
				else horizontalMenuChoice -= 1;
				break;
				
			case "right":
				if(horizontalMenuChoice == maxChoice) horizontalMenuChoice = minChoice;
				else horizontalMenuChoice += 1;
				break;
		}
		
	}
	
	public void select() {
		switch(menuOn) {
			case 1:
				switch (verticalMenuChoice) {
					case 1 :
						game.inMenu = false;
						game.initGame(game.getPlayer().getNextLevelToPass());
						break;
						
					case 2 :
						verticalMenuChoice = 1;
						horizontalMenuChoice = 1;
						menuOn = 2;
						break;
					
					case 3 :
						verticalMenuChoice = 1;
						menuOn = 3;
						break;
						
					case 4 :
						game.window.dispose();
						break;
				}
				break;
				
			case 2:
				game.initGame((verticalMenuChoice-1)*7 + horizontalMenuChoice - 1);
				game.inMenu = false;
				verticalMenuChoice = 1;
				horizontalMenuChoice = 1;
				menuOn = 1;
				break;
				
			case 3:
				if(verticalMenuChoice <= game.getNbPlayer()) {
					game.setCurrentPlayer(verticalMenuChoice-1);
					verticalMenuChoice = 1;
					menuOn = 1;
				}
				break;
		}
	}
}
