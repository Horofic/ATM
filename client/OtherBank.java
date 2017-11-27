import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.json.*;

class OtherBank extends JPanel
{

	private JLabel greeting;
	private JLabel credit;
	private JLabel exit;
	public static int amount;

	public OtherBank()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		greeting = new JLabel("Hallo, Meneer/Mevrouw");
		credit = new JLabel();
		exit = new JLabel("Druk op een toets om af te sluiten");

		greeting.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		credit.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		exit.setFont(new Font("Sans-Serif", Font.PLAIN, 30));

		panel.add(greeting);
		panel.add(credit);
		panel.add(exit);

		this.add(panel, gbc);
	}
	
	public void setAmount(int a)
	{
		credit.setText("Bedankt voor het pinnen. U heeft "+a+" opgenomen");
	}
}
