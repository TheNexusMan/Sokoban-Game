import javax.swing.JFrame;

//Window class is used to display a window
public class Window extends JFrame {

	private static final long serialVersionUID = 1L;
	public Panel panel;
	
	
	Window(Game game) {
		this.setTitle("Sokoban"); //Window title
		this.setSize(1000, 1000); //Window size (width wanted + 16 and height wanted + 39 because of borders)
		this.setLocationRelativeTo(null); //Window centering
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Window and processus close when click on red cross
		
		panel = new Panel(game);
		this.setContentPane(panel);
		panel.addKeyListener(new SokoKeyListener(panel));
		panel.setFocusable(true);
		this.setVisible(true);
	}
}
