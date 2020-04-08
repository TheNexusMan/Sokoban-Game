import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SokoKeyListener implements KeyListener {

	public Panel panel;
	
	SokoKeyListener(Panel panel){
		this.panel = panel;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	//The different actions do do when the player press a key
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
				
				case KeyEvent.VK_R:
					panel.game.restartLevel();
					panel.repaint();
					break;
			}
		}
		
		//Keys for end level pop-in
		if(panel.game.levelEnded) {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					panel.game.saveGame();
					panel.game.nextLevel();
					break;
				
				case KeyEvent.VK_R:
					panel.game.restartLevel();
					panel.repaint();
					break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
