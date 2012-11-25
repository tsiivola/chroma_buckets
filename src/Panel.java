import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfugue.Pattern;
import org.jfugue.StreamingPlayer;


public class Panel extends JPanel {
	
	public static StreamingPlayer player = new StreamingPlayer();
	public static Random random = new Random();
	
	public String currentNote;
	public String currentOctave;
	public String currentTimbre;
	
	public Vector<ButtonAndCheckbox> buttonList = new Vector<ButtonAndCheckbox>();
	
	public Panel(final String... notes) {
		JPanel top, middle, bottom;
		top = new JPanel();
		middle = new JPanel();
		middle.setPreferredSize(new Dimension(200, 150));
		
		bottom = new JPanel();
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(top);
		this.add(middle);
		this.add(bottom);
		
		// setup window
		JButton playButton = (JButton) top.add(new JButton("Play tone"));
		final JLabel correctLabel = (JLabel) top.add(new JLabel(""));
		
		JButton nextButton = (JButton) bottom.add(new JButton("Next"));
		JButton checkButton = (JButton) bottom.add(new JButton("Check"));
		
		nextButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				generateNextNote();
				for(ButtonAndCheckbox b : buttonList) {
					b.checkbox.setSelected(false);
				}
				correctLabel.setText("");
				playCurrentNote();
			}
		});
		
		playButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				playCurrentNote();
			}
		});
		
		checkButton.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				boolean correct=false;
				for(ButtonAndCheckbox b : buttonList) {
					if(b.checkbox.isSelected() && b.label.equals(currentNote)) {
						correct=true;
					}
					if(b.checkbox.isSelected() && !b.label.equals(currentNote)) {
						correct=false;
						break;
					}
				}
				correctLabel.setText(correct ? "Correct" : "Incorrect");
			}
		});
		

		for(final String label : notes) {
			final ButtonAndCheckbox b = new ButtonAndCheckbox(label);
			middle.add(b);
			buttonList.add(b);
			b.button.addActionListener(new ActionListener() {
				@Override public void actionPerformed(ActionEvent arg0) {
					playNoteWithRandomTimbre(label);
				}
			});
		}
		
		generateNextNote();
		this.setPreferredSize(new Dimension(350, 200));
		this.setVisible(true);
		middle.validate();
	}
	
	
	public void playNoteWithRandomTimbre(String note) {
		// select tone bank between 0-95
		String soundBank = "I" + String.valueOf(random.nextInt(95));
		// select octave between 2-7
		String octave = String.valueOf(random.nextInt(6)+2);
		player.stream(new Pattern(
				soundBank + " " + note + octave));
	}
	
	public void playCurrentNote() {
		player.stream(new Pattern(currentTimbre + " " + currentNote + currentOctave));
	}
	
	public void generateNextNote() {
		currentTimbre = "I" + String.valueOf(random.nextInt(95));
		currentOctave = String.valueOf(random.nextInt(4)+3);
		currentNote = buttonList.get(random.nextInt(buttonList.size())).label;
	}
	
	class ButtonAndCheckbox extends JPanel {
		public JButton button;
		public JCheckBox checkbox;
		public final String label;
		public ButtonAndCheckbox(String label) {
			this.label = label;
			button = new JButton(label);
			button.setAlignmentX(0.5f);
			checkbox = new JCheckBox();
			checkbox.setAlignmentX(0.5f);
			this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
			this.add(button);
			this.add(checkbox);
		}
	}

}
