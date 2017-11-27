import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class FooterPanel extends JPanel {
	private JLabel title;
	private JButton back;
	private JButton forward;
	final private Gui gui;
	private int location;
	
	public FooterPanel(Gui _gui) {
		this.gui = _gui;
		this.location = 1;
		title = new JLabel("All The Moneyzz ATM");
		title.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		title.setForeground(Color.white);
		this.setBackground( new Color(0x9933FF) );

//		back = new JButton("<< back");
//		forward = new JButton("forward >>");

//		add(back, BorderLayout.LINE_START);
		add(title, BorderLayout.CENTER);
//		add(forward, BorderLayout.LINE_END);

//		back.addActionListener(new ActionListener() {
//        	public void actionPerformed(ActionEvent e) {
//           	if(location>1){
//           		gui.setPage(--location);
//           	}
//        	}          
//     	});
//
//     	forward.addActionListener(new ActionListener() {
//        	public void actionPerformed(ActionEvent e) {
//        		if(location<13){
//           		gui.setPage(++location);
//           	}
//        	}          
//     	});
	}

	public void setLocation(int n){
		this.location = n;
		System.out.println("set location to: "+this.location);
	}
}