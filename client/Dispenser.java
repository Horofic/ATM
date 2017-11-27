import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;


public class Dispenser extends Thread  {
	
	public void geefGeld(int bedrag){
		int a10 = 0;
		int a20 = 0;
		int a50 = 0;
		
		NXTRegulatedMotor m10 = Motor.A;
		NXTRegulatedMotor m20 = Motor.B;
		NXTRegulatedMotor m50 = Motor.C;
		
		while(bedrag > 0){
			if(bedrag >= 50){
				a50 += 1;
				bedrag -= 50;
			}
			else if (bedrag >= 20&& bedrag < 50){
				a20 += 1;
				bedrag -= 20;
			}
			else{
				a10 += 1;
				bedrag -= 10;
			}		
		}

		dispense(m50,a50);
		dispense(m20,a20);
		dispense(m10,a10);
	}
	
	public static void dispense(NXTRegulatedMotor m, int aantal){	
		int tijd = 1100;
		m.backward(); // geld gaat voor uit
		try {
			sleep(tijd*aantal);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.stop();
		m.forward(); //geld gaat terug in automaat	
	}
}