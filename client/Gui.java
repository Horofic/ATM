import java.awt.event.*;
import java.awt.*;

import javax.swing.*;

import java.net.URLEncoder;

import org.json.*;

class Gui extends JFrame{

    final private StartPanel home;
    final private LoginPanel login;
    final private FunctionPanel functions;
    final private WithdrawPanel withdraw;
    final private CreditPanel credit;
    final private LoadingPanel loading;
    final private BlockedPanel blocked;
    final private WithdrawCustomPanel custom;
    final private ReceiptPanel receipt;
    final private HeaderPanel header;
    final private FooterPanel footer;
    final private OtherBank otherbank;
    final private JPanel cards;
    final public CardLayout cardLayout;
    public Connection connection;
    private int location;
    private String token;
    private String pin;
    private String cardnr;
    private static int amount;
    private int tries;

    public Gui() {
    	setTitle("ATM ATM");
    	setSize(1000,1000);
    	setLocationRelativeTo(null);
        getContentPane().setBackground( new Color(0xBBDDEE) );

        connection = new Connection(this);

        cardLayout = new CardLayout();

        cards = new JPanel(cardLayout);

        header = new HeaderPanel();
        footer = new FooterPanel(this);

        home = new StartPanel();
        login = new LoginPanel();
        functions = new FunctionPanel();
        withdraw = new WithdrawPanel();
        credit = new CreditPanel();
        loading = new LoadingPanel();
        blocked = new BlockedPanel();
        custom = new WithdrawCustomPanel();
        receipt = new ReceiptPanel(10);
        otherbank = new OtherBank();

        cards.add(home, "1");
        cards.add(login, "2");
        cards.add(functions, "3");
        cards.add(withdraw, "4");
        cards.add(credit, "5");
        cards.add(blocked, "9");
        cards.add(loading,"10");
        cards.add(custom,"11");
        cards.add(receipt,"12");
        cards.add(otherbank,"13");

        add(cards, BorderLayout.CENTER);
        this.add(header, BorderLayout.PAGE_START);
        this.add(footer, BorderLayout.PAGE_END);
        setPage(1);

    	addWindowListener(new WindowAdapter() {
    	  	public void windowClosing(WindowEvent e) {
    		    System.exit(0);
    	  	}
    	} );
    }

    public void setPage(int n){
        this.location = n;
        footer.setLocation(n);
        switch(n){
            case 1:
                cardLayout.show(cards, "1");
                break;
            case 2:
                cardLayout.show(cards, "2");
                break;
            case 3:
                cardLayout.show(cards, "3");
                break;
            case 4:
                cardLayout.show(cards, "4");
                break;
            case 5:
                cardLayout.show(cards, "5");
                break;
            case 9:
                cardLayout.show(cards, "9");
                break;
            case 10:
                cardLayout.show(cards, "10");
                break;
            case 11:
                cardLayout.show(cards, "11");
                break;
            case 12:
                cardLayout.show(cards, "12");
                break;
            case 13:
                cardLayout.show(cards, "13");
                break;
        }
        repaint();
    }

    public void handleData(String data){
        switch(this.location){
            case 1:
                if(data.length()>2){
                    cardnr = data;
                    login.setPinCorrect(true);
                    this.setPage(2);
                }
                break;
            case 2:
                if(data.length()<2){
                    login.handleData(data, this);
                }
                break;
            case 3:
                if(data.length()<2){
                    functions.handleData(data, this);
                }
                break;
            case 4:
                if(data.length()<2){
                    withdraw.handleData(data, token, this);
                }
                break;
            case 10:

                break;
            case 11:
                if(data.length()<2){
                    custom.handleData(data, token, this);
                }
                break;
            case 12:
                if(data.length()<2){
                    receipt.handleData(data, this);
                }
                break;
            default:
                this.logout();
                this.setPage(1);
                break;
        }
    }

    public void login(String pin)
    {
        this.setPage(10);
        try
        {
        	String res;
            String message = "cardId="+URLEncoder.encode(this.cardnr, "UTF-8")+"&"+
                             "pin="+URLEncoder.encode(pin, "UTF-8");
            if(cardnr.substring(0,4).equals("COPO"))
			{
            	res = connection.post("/login.php", message, "");
			}
            else
            {
            	 res = connection.post("/login", message, "");
            }
			
			JSONObject obj = new JSONObject(res);

			if(obj.get("success").toString().equals("{}"))
			{
				Object error = obj.get("error");
				int code = (int) ((JSONObject) error).get("code");

				if(code == 16)
				{
					System.out.println("pas is geblokkeerdddddd");
					this.setPage(9);
				}

				if(code == 15)
				{
					Object test = obj.get("error");
					tries = (int) ((JSONObject) test).get("failedAttempts");
					System.out.println(tries);
					System.out.println("pincode incorrectttttt");
					login.setPinCorrect(false);
					this.setPage(2);
				}
			}
			else
			{
				JSONObject result = (JSONObject) obj.get("success");
				token = (String) result.get("token");

				// cardnr = "";

				login.setPinCorrect(true);
                if(cardnr.substring(0,4).equals("ATMB")){
				    this.setPage(3);
                }else{
                    this.setPage(4);
                }
			}
		}
		catch(Exception e)
		{
			// Niet zeker wat ik hier moet doen misschien kan jij hier naar
			// kijken david :)
			System.out.println("it didn't work");
			login.setPinCorrect(false);
			this.setPage(2);
		}
	}

	public void logout()
	{
		try
		{
			if(cardnr.substring(0,4).equals("COPO"))
			{
				connection.post("/logout.php", "", token);
			}
			else
			{
				connection.post("/logout", "", token);
			}
		}
		catch(Exception e)
		{
			System.out.println("you did not log out");
		}
		token = "";
	}

	public void getCredit(Boolean b)
	{
		credit.getCredit(token, this, b);
	}

	public int getTries()
	{
		return tries;
	}

	public String getCardNr()
	{
		return this.cardnr;
	}

    public void setAmount(int a){
        this.receipt.setAmount(a);
       this.otherbank.setAmount(a);
    }
    
    public static int getAmount()
    {
		return amount;
    	
    }

}
