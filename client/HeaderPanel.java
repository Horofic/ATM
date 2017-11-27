import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class HeaderPanel extends JPanel{
	private JLabel title;
	
	public HeaderPanel() {
		title = new JLabel("All The Moneyzz ATM");
		title.setFont(new Font("Sans-Serif", Font.PLAIN, 60));
		title.setForeground(Color.white);
		this.setBackground( new Color(0x9933FF) );

		add(title);
	}
}