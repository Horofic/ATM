import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import java.awt.print.*;
import java.util.Date;
import java.text.SimpleDateFormat;

class ReceiptPanel extends JPanel implements Printable/* , ActionListener */
{
	private JLabel title;
	private JLabel account;
	private JLabel amount;
	private JLabel date;
	private JPanel panel;
	private JLabel card;
	private JLabel spacer;
	// private JButton printButton;
	private JLabel yesLabel;

	public ReceiptPanel(int amountWithdrawn)
	{
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		panel.setBackground(Color.WHITE);

		title = new JLabel("All  The  Moneyzz  ATM");
		title.setFont(new Font("Sans-Serif", Font.PLAIN, 12));
		
		spacer = new JLabel("     ");

		amount = new JLabel("EUR " + amountWithdrawn);
		amount.setFont(new Font("Sans-Serif", Font.PLAIN, 12));

		date = new JLabel(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
		date.setFont(new Font("Sans-Serif", Font.PLAIN, 12));

		card = new JLabel("XXXXXXX397    ");
		card.setFont(new Font("Sans-Serif", Font.PLAIN, 12));


		yesLabel = new JLabel("<html>&nbsp &nbsp Voor de bon druk op #<br>&nbsp &nbsp Als u geen bon wilt druk op *</html>");
		yesLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 30));

		panel.add(title);
		panel.add(spacer);
		panel.add(date);
		panel.add(card);
		panel.add(amount);

		this.add(panel, gbc);

		JPanel p = new JPanel();
		p.add(yesLabel);
		// p.add(printButton);
		this.add(p, gbc);

		// printButton.addActionListener(this);
	}

	public void handleData(String data, Gui g)
	{
		if(data.equals("#"))
		{
			this.startPrint();
			g.setPage(13);
		}
		else if(data.equals("*"))
		{
			g.setPage(13);
		}
	}

	public int print(Graphics g, PageFormat pf, int page) throws PrinterException
	{

		// We have only one page, and 'page'
		// is zero-based
		if(page > 0)
		{
			return NO_SUCH_PAGE;
		}

		// User (0,0) is typically outside the
		// imageable area, so we must translate
		// by the X and Y values in the PageFormat
		// to avoid clipping.
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(30, 100);

		// Now we perform our rendering
		this.panel.printAll(g);

		// tell the caller that this page is part
		// of the printed document
		return PAGE_EXISTS;
	}

	public void startPrint()
	{
		PrinterJob job = PrinterJob.getPrinterJob();
		
		job.setPrintable(this, new PageFormat());
		try
		{
			job.print();
		}
		catch(PrinterException ex)
		{
			ex.printStackTrace();
		}
	}

	public void setAmount(int a)
	{
		amount.setText("EUR " + a);
	}
	
	public void getCard()
	{
	
	}
}
