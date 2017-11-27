import java.awt.*;
import javax.swing.*;

import org.json.*;

class WithdrawCustomPanel extends JPanel
{

	private String amount;
	private JLabel intro;
	private JLabel amountLabel;
	private JLabel insufficient;
	private Dispenser d = new Dispenser();

	public WithdrawCustomPanel()
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		amount = "";

		intro = new JLabel("<html>Kies het bedrag wat u wilt pinnen (meervoud van 10)<br># om door te gaan<br>C om te corrigeren<br>* om stap terug te gaan</html>");
		amountLabel = new JLabel("\u20ac" + "____");
		insufficient = new JLabel();

		intro.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		amountLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		insufficient.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		insufficient.setForeground(Color.red);
		this.reset();

		panel.add(intro);
		panel.add(insufficient);
		panel.add(amountLabel);

		this.add(panel, gbc);
	}

	public void handleData(String data, String token, Gui g)
	{
		if(data.equals("*"))
		{
			this.reset();
			g.setPage(4);
		}
		else if(data.equals("#"))
		{
			withdraw(Integer.parseInt(amount), token, g);
		}
		else if(data.equals("C"))
		{
			resetAmount();
		}
		else if(data.matches("[0-9]"))
		{
			amount += data;

			drawAmount();
		}
	}

	private void withdraw(int amount, String token, Gui g)
	{
		try
		{

			if(amount % 10 != 0)
			{
				insufficient.setText("Voer A.U.B een meervoud van 10 in");
				insufficient.setVisible(true);
				return;

			}
			else if(amount > 1000)
			{
				amount = 1000;
				insufficient.setText("U kunt maximaal maar " + "\u20ac" + "1000 opnemen");
				insufficient.setVisible(true);
				return;

			}
			else
			{
				// Connection connection = new Connection();
				Connection connection = g.connection;
				String body = "amount=" + amount;
				String res;

				if(g.getCardNr().substring(0, 4).equals("COPO"))
				{
					System.out.println("Header token: " + token);
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
						insufficient.setText("onvoldoende saldo");
						insufficient.setVisible(true);
					}
				}
				else
				{
					JSONObject result = (JSONObject) obj.get("success");
					// g.getCredit(true);
					g.setAmount(amount);
				//	d.geefGeld(amount);
					this.reset();
					g.setPage(12);

					// TODO koppeling met dispenser.java maken
				}
			}
		}
		catch(Exception e)
		{
			System.err.println(e.toString());
		}
	}

	public void reset()
	{
		insufficient.setVisible(false);
		this.resetAmount();
	}

	private void drawAmount()
	{
		String amountContent = amount;
		while(amountContent.length() < 4)
		{
			amountContent = "_" + amountContent;
		}
		amountLabel.setText(amountContent);
	}

	private void resetAmount()
	{
		amount = "";
		drawAmount();
	}
}
