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

	@Override
	public void keyPressed(KeyEvent e) {
		
		switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT:
				panel.game.character.moveLeft();
				panel.repaint();
				break;
			
			case KeyEvent.VK_RIGHT:
				panel.game.character.moveRight();
				panel.repaint();
				break;
			
			case KeyEvent.VK_UP:
				panel.game.character.moveUp();
				panel.repaint();
				break;
	
			case KeyEvent.VK_DOWN:
				panel.game.character.moveDown();
				panel.repaint();
				break;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

}
