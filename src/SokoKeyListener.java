import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//SokoKeyListener class is used to perform actions according to the key pressed
public class SokoKeyListener implements KeyListener {

	public Panel panel; //We need to pass Panel to SokoKeyListener to access it
	
	SokoKeyListener(Panel panel){
		this.panel = panel;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	//The different actions to do when the player press a key
	@Override
	public void keyPressed(KeyEvent e) {
		
		//Keys for playing
		if(panel.game.gameOn) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					if(panel.game.move("left")) panel.repaint(); //if the character can move, we repaint the panel
					break;
				
				case KeyEvent.VK_RIGHT:
					if(panel.game.move("right")) panel.repaint();
					break;
				
				case KeyEvent.VK_UP:
					if(panel.game.move("up")) panel.repaint();
					break;
		
				case KeyEvent.VK_DOWN:
					if(panel.game.move("down")) panel.repaint();
					break;
			}
		}
		
		//Keys that work in game and at the end level pop-in
		if(!panel.game.inMenu) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_R:
					panel.game.restartLevel();
					panel.repaint();
					break;
					
				case KeyEvent.VK_ESCAPE :
					panel.game.getLevel().resetLevel();
					panel.game.inMenu = true;
					panel.game.gameOn = false;
					panel.game.menu.setCurrentMenuId(1);
					panel.repaint();
					break;
			}
		}
		
		//Key for end level pop-in
		if(panel.game.levelEnded) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					panel.game.nextLevel();
					panel.repaint();
					break;
			}
		}
		
		//Keys for the menu
		if(panel.game.inMenu) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_UP: //Go up in a menu
					panel.game.menu.menuChoice("up");
					panel.repaint();
					break;
				
				case KeyEvent.VK_DOWN: //Go down in a menu
					panel.game.menu.menuChoice("down");
					panel.repaint();
					break;
				
				case KeyEvent.VK_LEFT: //Go left in a menu
					panel.game.menu.menuChoice("left");
					panel.repaint();
					break;
				
				case KeyEvent.VK_RIGHT: //Go right in a menu
					panel.game.menu.menuChoice("right");
					panel.repaint();
					break;
				
				case KeyEvent.VK_ENTER: //Select a choice in a menu
					panel.game.menu.select();
					panel.repaint();
					break;
					
				case KeyEvent.VK_ESCAPE: //Go back to the main menu
					panel.game.menu.goBack();
					panel.repaint();
					break;
				
				case KeyEvent.VK_DELETE: //Delete a character when writing a new player pseudo
					if(panel.game.menu.getCurrentMenuId() == 3) {
						panel.game.menu.deletePlayer();
						panel.repaint();
					}
					break;
				
				default: //We use every others entries if the player is writing a new player pseudo
					if(panel.game.creatingPlayer) {
						panel.game.createNewPlayerPseudo(e);
						panel.repaint();
					}
					break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
