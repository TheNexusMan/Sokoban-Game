import javax.swing.JFrame;

public class Window extends JFrame {
	public Window() {
		this.setTitle("Sokoban");
		this.setSize(800, 800);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Panel panel = new Panel();
		this.setContentPane(panel);      
		panel.addKeyListener(new SokoKeyListener(panel));
		panel.setFocusable(true);
		this.setVisible(true);
	}
}
