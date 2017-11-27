import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class LoadingPanel extends JPanel{

	private JLabel 	text;
	
	public LoadingPanel() {

		this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		text = new JLabel("Even geduld A.U.B.");
		text.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		
		panel.add(text);

		this.add(panel, gbc);

	}
}