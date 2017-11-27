import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
 
public class Connection extends FunctionPanel{
 
	private final String USER_AGENT = "Mozilla/5.0";
	private Gui gui;
	public Connection(Gui g){
		this.gui = g;
	}

	private String getIp(){
		String res = "";
		System.out.println(gui.getCardNr());
		switch(gui.getCardNr().substring(0,4)){
			case "ATMB":
				res = "http://145.24.222.217:8080";
			break;
			case "PROH":
				res = "http://www.projectheist.tk:8000";
			break;
			case "ILMG":
				res = "http://145.24.222.103:8080";
			break;
			case "SKER":
				res = "http://145.24.222.112/api";
			break;
			case "MLBI":
				res = "http://145.24.222.177";
			break;
			case "COPO":
				res = "http://145.24.222.150";
			break;
		}
		System.out.println("Target IP is "+res);
		return res;
	}
 
	// HTTP GET request
	public String get(String url, String token) throws Exception {
 
		URL obj = new URL(this.getIp()+url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		// optional default is GET
		con.setRequestMethod("GET");
 
		//add request header
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("token", token);
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'GET' request to URL : " + url);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream())
		);
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());

		return response.toString();
 
	}
 
	// HTTP POST request
	public String post(String url, String params, String token) throws Exception {
 
		URL obj = new URL(this.getIp()+url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
 
		//add reuqest header
		con.setRequestMethod("POST");
		con.setRequestProperty("User-Agent", USER_AGENT);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("token", token);
 
		// Send post request
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(params);
		wr.flush();
		wr.close();
 
		int responseCode = con.getResponseCode();
		System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + params);
		System.out.println("Response Code : " + responseCode);
 
		BufferedReader in = new BufferedReader(
	        new InputStreamReader(con.getInputStream())
		);
		String inputLine;
		StringBuffer response = new StringBuffer();
 
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();
 
		//print result
		System.out.println(response.toString());

		return response.toString();
 
	}
 
}