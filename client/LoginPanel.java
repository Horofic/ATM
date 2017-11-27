import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class LoginPanel extends JPanel{

	private JLabel text;
	private JLabel dots;
	private JLabel wrong;
	private String pin;
	private Boolean pinCorrect;

	public LoginPanel() {
		pinCorrect = true;

		this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JPanel panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

		pin = "";
		text 	= new JLabel("<html>Voer uw pincode in<br># om door te gaan<br>* om te corrigeren<br>D om af te sluiten</html>");
		text.setFont(new Font("Sans-Serif", Font.PLAIN, 40));

		dots 	= new JLabel("_ _ _ _");
		dots.setFont(new Font("Sans-Serif", Font.PLAIN, 40));

		wrong	= new JLabel("Uw pincode was incorrect probeer het opnieuw");
		wrong.setFont(new Font("Sans-Serif", Font.PLAIN, 40));
		wrong.setForeground(Color.red);
		wrong.setVisible(!pinCorrect);

		panel.add(text);
		panel.add(dots);
		panel.add(wrong);

		this.add(panel, gbc);
	}

	public void handleData(String data, Gui g){
		if(pin.length()<4){
			try{
				int num = Integer.parseInt(data);
				pin += num;
				changeDots(pin.length());
			}
			catch(Exception e){
				System.err.println(e.toString());
			}
		}else if(data.equals("#")){
			g.login(pin);
			pin = "";
			int tries = 3-g.getTries();
			wrong.setText("Uw pincode is incorrect nog "+tries+" van de 3 pogingen");
			changeDots(pin.length());

		}
		else if(data.equals("D")){
			g.setPage(1);
			pin = "";
			changeDots(pin.length());
		}
		else if(data.equals("*")){
			pin = "";
			changeDots(pin.length());
			//g.setPage(1);
		}
	}

	private void changeDots(int n){
		String dotsContent = "";
		for(int i = 0; i<4; i++){
			if(i<n){
				dotsContent+="*";
			}else{
				dotsContent+="_";
			}
			if(i<3){
				dotsContent+=" ";
			}
		}
		dots.setText(dotsContent);
	}

	public void setPinCorrect(Boolean b){
		wrong.setVisible(!b);
	}
}
