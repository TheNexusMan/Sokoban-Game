import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class EndLevelKeyListener implements KeyListener {

	public Game game;
	
	EndLevelKeyListener(Game game){
		this.game = game;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_ENTER) game.nextLevel();
	}

}
