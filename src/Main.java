import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

public class Main extends JFrame {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new Main();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public Main() {
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane();
		this.add(tabbedPane);
		
		tabbedPane.addTab("3 note", new Panel("C", "E", "Ab"));
		tabbedPane.addTab("whole tone", new Panel("C", "D", "E", "Gb", "Ab", "Bb"));
		tabbedPane.addTab("9 note", new Panel("C", "Db", "D", "E", "F", "Gb", "Ab","A", "Bb"));
		tabbedPane.addTab("chromatic", new Panel("C", "Db", "D", "Eb", "E", "F", "Gb", "G", "Ab","A", "Bb","B"));
		
		this.pack();
		this.setVisible(true);
	}

}
