import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class SokoKeyListener implements KeyListener {

	Panel panel = new Panel();
	
	SokoKeyListener(Panel panel){
		this.panel = panel;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	//The different actions do do when the player press a key
	@Override
	public void keyPressed(KeyEvent e) {
		
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
				panel.game.restartLevel(); //Press R to restart the level
				panel.repaint();
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
