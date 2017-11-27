import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.URLEncoder;
import java.util.regex.*;
import org.json.*;

class WithdrawPanel extends JPanel
{

	private JLabel intro;
	private JLabel amount20;
	private JLabel amount50;
	private JLabel amount100;
	private JLabel other;
	private JLabel insufficient;
	private Dispenser d = new Dispenser();

	public WithdrawPanel()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		intro = new JLabel("Kies het bedrag wat u wilt pinnen");
		amount20 = new JLabel("A: " + "\u20ac" + "20");
		amount50 = new JLabel("B: " + "\u20ac" + "50");
		amount100 = new JLabel("C: " + "\u20ac" + "100");
		other = new JLabel("D: " + "kies een bedrag");
		insufficient = new JLabel("onvoldoende saldo");

		intro.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		amount20.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		amount50.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		amount100.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		other.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		insufficient.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		insufficient.setForeground(Color.red);
		this.reset();

		panel.add(intro);
		panel.add(insufficient);
		panel.add(amount20);
		panel.add(amount50);
		panel.add(amount100);
		panel.add(other);

		this.add(panel, gbc);
	}

	public void handleData(String data, String token, Gui g)
	{
		if(data.equals("A"))
		{
			withdraw(20, token, g);
		}
		else if(data.equals("B"))
		{
			withdraw(50, token, g);
		}
		else if(data.equals("C"))
		{
			withdraw(100, token, g);
		}
		else if(data.equals("D"))
		{
			g.setPage(11);
		}
	}

	private void withdraw(int amount, String token, Gui g)
	{
		try
		{
			Connection connection = g.connection;
			String body = "amount=" + amount;
			String res;
			if(g.getCardNr().substring(0,4).equals("COPO"))
			{
				System.out.println("Header token: "+token);
				res = connection.post("/withdraw.php", body, token);
			}
			else
			{
				res = connection.post("/withdraw", body, token);
			}
			 
			JSONObject obj = new JSONObject(res);

			if(obj.get("success").toString().equals("{}"))
			{
				Object error = obj.get("error");
				int code = (int) ((JSONObject) error).get("code");
				if(code == 32)
				{
					insufficient.setVisible(true);
				}
			}
			else
			{
				JSONObject result = (JSONObject) obj.get("success");
//				if(result.get("balance") != null || result.get("balance") == null)
//				{
					g.setAmount(amount);
					//d.geefGeld(amount);
					this.reset();
					g.setPage(12);
//				}
//				else
//				{
//					// g.getCredit(true);
//					this.reset();
//					g.setPage(13);
//				}

				// TODO koppeling met dispenser.java maken
				
			}
		}
		catch(Exception e)
		{
			System.err.println(e.toString());
			e.printStackTrace();
		}
	}

	public void reset()
	{
		insufficient.setVisible(false);
	}
}
