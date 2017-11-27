import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class StartPanel extends JPanel{

	private JLabel 	text;
	
	public StartPanel() {
		this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		text = new JLabel("Scan uw pas om uw transactie te beginnen");
		text.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		
		panel.add(text);
		this.add(panel, gbc);
	}
}