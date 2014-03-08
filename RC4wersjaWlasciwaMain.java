import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RC4wersjaWlasciwaMain {

	public static void main(String[] args) throws InvalidKeyException,
			NoSuchAlgorithmException, KeyStoreException, CertificateException,
			IOException, UnrecoverableKeyException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException,
			InvalidAlgorithmParameterException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
		
		String hasloDoKeystora = "ala ma kota";
		String aliasHasla = "mojAlias";
		String sciezkaDoKeyStore = RC4wersjaWlasciwa.createKeyStore(hasloDoKeystora, aliasHasla);

		String wiadomosc = "adopcja ";
		byte[] kryptogram = RC4wersjaWlasciwa.zakoduj(wiadomosc,
				RC4wersjaWlasciwa.pobierzKlucz(sciezkaDoKeyStore, aliasHasla, hasloDoKeystora));
		RC4wersjaWlasciwa.zapiszZakodowanaWiadomoscDoPilku(kryptogram);
		
		String wiadomosc2 = "pajacyk ";
		byte[] kryptogram2 = RC4wersjaWlasciwa.zakoduj(wiadomosc2,
				RC4wersjaWlasciwa.pobierzKlucz(sciezkaDoKeyStore, aliasHasla, hasloDoKeystora));
		RC4wersjaWlasciwa.zapiszZakodowanaWiadomoscDoPilku(kryptogram2);
		
		byte[] zdekodowanyTekst = RC4wersjaWlasciwa.dekoduj(kryptogram,
				RC4wersjaWlasciwa.pobierzKlucz(sciezkaDoKeyStore, aliasHasla, hasloDoKeystora));
		
		byte[] zdekodowanyTekst2 = RC4wersjaWlasciwa.dekoduj(kryptogram2,
				RC4wersjaWlasciwa.pobierzKlucz(sciezkaDoKeyStore, aliasHasla, hasloDoKeystora));

		
		System.out.println(wiadomosc);
		System.out.println(new String(kryptogram));
		System.out.println(new String(zdekodowanyTekst));
		System.out.println(new String(kryptogram2));
		System.out.println(new String(zdekodowanyTekst2));
		System.out.println("");
		
		System.out.println("Xor tekstow jawnych: "+ RC4wersjaWlasciwa.wykonajXor(wiadomosc, wiadomosc2));
		
		System.out.println("Xor kryptogramow:    "+ RC4wersjaWlasciwa.wykonajXor(kryptogram.toString(), kryptogram2.toString()));
	
System.out.println("Xor2 tekstow jawnych: "+ RC4wersjaWlasciwa.wykonajXor2(wiadomosc, wiadomosc2));
		
		System.out.println("Xor2 kryptogramow:    "+ RC4wersjaWlasciwa.wykonajXor2(kryptogram.toString(), kryptogram2.toString()));
	
	}
}
