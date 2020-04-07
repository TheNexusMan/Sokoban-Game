import javax.swing.JFrame;

//Window class is use to display a window
public class Window extends JFrame {
	public Window() {
		this.setTitle("Sokoban"); //Window title
		this.setSize(800, 800); //Window size
		this.setLocationRelativeTo(null); //Window centering
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Window and processus close when click on red cross
		
		Panel panel = new Panel();
		this.setContentPane(panel);      
		panel.addKeyListener(new SokoKeyListener(panel));
		panel.setFocusable(true);
		this.setVisible(true);
	}
}
