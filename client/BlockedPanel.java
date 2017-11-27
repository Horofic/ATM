import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class BlockedPanel extends JPanel{

	private JLabel 	text;
	
	public BlockedPanel() {
		this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panel = new JPanel();

		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		text = new JLabel("Uw pas is geblokkeerd. Neem contact op met uw bank.");
		text.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		
		panel.add(text);
		this.add(panel, gbc);
	}
}