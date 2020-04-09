import javax.swing.JFrame;

//Window class is used to display a window
public class Window extends JFrame {

	private static final long serialVersionUID = 1L;

	public Window(Game game) {
		this.setTitle("Sokoban"); //Window title
		this.setSize(800, 800); //Window size
		this.setLocationRelativeTo(null); //Window centering
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Window and processus close when click on red cross
		
		Panel panel = new Panel(game);
		this.setContentPane(panel);
		panel.addKeyListener(new SokoKeyListener(panel));
		panel.setFocusable(true);
		this.setVisible(true);
	}
}
