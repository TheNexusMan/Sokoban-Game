import javax.swing.JFrame;

//Window class is used to display a window
public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	public Panel panel;
	
	Window(Game game) {
		this.setTitle("Sokoban"); //Window title
		this.setSize(1000, 1000);
		this.setLocationRelativeTo(null); //Window centering
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Window and processus close when click on red cross
		
		panel = new Panel(game); //Unique panel where we draw the game
		this.setContentPane(panel);
		panel.addKeyListener(new SokoKeyListener(panel)); //We associate the keylistener to the panel
		panel.setFocusable(true);
		this.setVisible(true);
	}
}
