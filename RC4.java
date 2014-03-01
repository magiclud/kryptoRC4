import java.io.IOException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;

import javax.crypto.KeyGenerator;


public class RC4 {

	private char[] tabKlucz;
	private char[] trescWiadomosci;
	private int[] tabPermutacji = new int[256];
	

	public String zakoduj(String wiadomosc) {
		
		tworzenieKlucza();
		tworzenieKluczaRC4();
		
		trescWiadomosci = wiadomosc.toCharArray();

		return szyfrowanie();
	}

	private String szyfrowanie() {
		char[] trescZaszyfrowana = new char[trescWiadomosci.length];
		for(int i=0; i<trescWiadomosci.length; i++){
			int pom1 =0;
			int pom2 =0;
			pom1=(pom1+1)% 256;
			pom2= pom2+tabPermutacji[pom1];
			zamienMiejscami(pom1, pom2, tabPermutacji);
			int pom3= (tabPermutacji[pom1] + tabPermutacji[pom2])%256;
			trescZaszyfrowana[i]=(char) (pom3^(int)trescWiadomosci[i]);
		}
		return trescZaszyfrowana.toString();
		
	}

	private void tworzenieKlucza(){
		
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	
        // Create the public and private keys
        KeyGenerator generator;
		try {
			generator = KeyGenerator.getInstance("DES", "BC");
	
        Key key = generator.generateKey();
		
		KeyStore store = KeyStore.getInstance("PKCS12", "BC");
		String password = "hasloToUnlockTheKeyStore";
		char[] tabPass = password.toCharArray();
		store.load(null, tabPass);
		
	    String alias = "alias";
	    Certificate cert[] = new Certificate[1];
	    cert[0]=store.getCertificate(alias);
	    
		store.setKeyEntry("alias", key, tabPass, null);
		
		String klucz = key.toString();
		this.tabKlucz = klucz.toCharArray();
		
		
		} catch (NoSuchAlgorithmException | NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//drugi klucz
		//store.setKeyEntry("key2", (Key)keyPair.getPrivate(), tabPass, cert);
	}
	private void tworzenieKluczaRC4() {
		// uzupeniam tablice Permutacji kolejno wartosciami 0-255 (256 bajtow)
		for (int i = 0; i < 256; i++) {
			tabPermutacji[i] = i;
		}
		// tworzenie klucza w RC4
		int pom = 0; // zmienna pomocnicza

		for (int i = 0; i < 256; i++) {
			pom = (pom + tabPermutacji[i] + tabKlucz[i % tabKlucz.length]) % 256; 
			zamienMiejscami(i, pom, tabPermutacji);
		}
	}
	

	private void zamienMiejscami(int i, int pom, int[] tabPermutacji) {
		int tymczasowa = tabPermutacji[i];
		tabPermutacji[i] = pom;
		tabPermutacji[pom] = tymczasowa;
	}

}
