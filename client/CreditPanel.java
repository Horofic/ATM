import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import org.json.*;

class CreditPanel extends JPanel
{

	private JLabel greeting;
	private JLabel credit;
	private JLabel exit;

	public CreditPanel()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		greeting = new JLabel("Hallo, Meneer/Mevrouw");
		credit = new JLabel("Uw saldo is:");
		exit = new JLabel("Druk op een toets om af te sluiten");

		greeting.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		credit.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		exit.setFont(new Font("Sans-Serif", Font.PLAIN, 30));

		panel.add(greeting);
		panel.add(credit);
		panel.add(exit);

		this.add(panel, gbc);
	}

	
	public void otherBank()
	{
		credit.setText("Bedankt voor het pinnen.");
	}
	
	public void getCredit(String token, Gui g, Boolean b)
	{
		g.setPage(10);
		try
		{
			String res = g.connection.get("/balance", token);
			JSONObject obj = new JSONObject(res);
			JSONObject result = (JSONObject) obj.get("success");
			String amount = String.valueOf(result.get("balance"));
			String name = "Meneer/Mevrouw";
			
			
//			if(result.get("balance") != null)
//			{
//				
//				name = "Topkek";
//			}
//			else
//			{
//				 name = (String) result.get("customer");
//			}
//
//			System.out.println(res);

			greeting.setText("Hallo, " + name);
			if(b)
			{
				credit.setText("Uw nieuwe saldo is: " + "\u20ac" + amount);
			}
			else
			{
				credit.setText("Uw saldo is: " + "\u20ac" + amount);
			}
			g.setPage(5);
		}
		catch(Exception e)
		{
			System.out.println("Something went wrong: " + e.toString());
			e.printStackTrace();
		}
	}
}
