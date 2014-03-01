
public class RC4main {

	public static void main(String args[]) throws Exception {
        String wiadomosc = "Nic sie nie dzieje";
        RC4 rc4 = new RC4();
        String kryptogram = rc4.zakoduj(wiadomosc); 
        System.out.println("Wiadomosc przed zaszyfrowaniem " + "\"" + wiadomosc +"\""  );
        System.out.println();
        System.out.println("Kryptogram "+ kryptogram);
        
        
        
        
        
	}


}
