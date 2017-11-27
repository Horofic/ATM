import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class FunctionPanel extends JPanel{

	public 	JLabel 	withdraw;
	public	JLabel 	credit;
	
	public FunctionPanel() {
		this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

	    withdraw	= new JLabel("# opnemen");
		credit		= new JLabel("* saldo controleren");
		
		withdraw.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		credit.setFont(new Font("Sans-Serif", Font.PLAIN, 40));

	    panel.add(withdraw);

	    panel.add(credit);

	    this.add(panel, gbc);
	}

	public void handleData(String data, Gui g){
		if(data.equals("#")){
			g.setPage(4);
		}
		else if(data.equals("*")){
			g.setPage(5);
			g.getCredit(false);
		}else if(data.equals("D")){
			g.logout();
			g.setPage(1);
		}
	}
}