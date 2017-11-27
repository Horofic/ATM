import java.awt.*;
import javax.swing.*;

public class App{

  public static void main(String[] args) {
  	Gui f = new Gui();
  	//f.setExtendedState(JFrame.MAXIMIZED_BOTH); 
    //f.setUndecorated(true);
    f.setVisible(true);
    SerialConnection main = new SerialConnection();
	main.initialize(f);
  }
}