import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//SokoKeyListener class is used to perform actions according to the key pressed
public class SokoKeyListener implements KeyListener {

	public Panel panel;
	
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
					panel.game.menu.setMenuOn(1);
					panel.repaint();
					break;
			}
		}
		
		//Keys for end level pop-in
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
				case KeyEvent.VK_UP:
					panel.game.menu.menuChoice("up");
					panel.repaint();
					break;
				
				case KeyEvent.VK_DOWN:
					panel.game.menu.menuChoice("down");
					panel.repaint();
					break;
				
				case KeyEvent.VK_LEFT:
					panel.game.menu.menuChoice("left");
					panel.repaint();
					break;
				
				case KeyEvent.VK_RIGHT:
					panel.game.menu.menuChoice("right");
					panel.repaint();
					break;
				
				case KeyEvent.VK_ENTER:
					panel.game.menu.select();
					panel.repaint();
					break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
