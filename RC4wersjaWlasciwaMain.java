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

		String wiadomosc = "Polish PM Donald Tusk met with Yulia Tymoshenko in Dublin, Thursday night, following EU leaders declaring a Crimea referendum on leaving Ukraine would violate international law. - See more at: http://thenews.pl/1/10/Artykul/164396,Tymoshenko-meets-Tusk-in-Dublin";
		byte[] kryptogram = RC4wersjaWlasciwa.zakoduj(wiadomosc,
				RC4wersjaWlasciwa.pobierzKlucz(sciezkaDoKeyStore, aliasHasla, hasloDoKeystora));
		RC4wersjaWlasciwa.zapiszZakodowanaWiadomoscDoPilku(kryptogram);
		
		String wiadomosc2 = "\"Tymoshenko thanked Poland for its attempts, especially in recent week, to solve the crisis in Ukraine,\" Polish deputy foreign minister Piotr Serafin said. - See more at: http://thenews.pl/1/10/Artykul/164396,Tymoshenko-meets-Tusk-in-Dublin#sthash.36yECgKW.dpuf";
		byte[] kryptogram2 = RC4wersjaWlasciwa.zakoduj(wiadomosc2,
				RC4wersjaWlasciwa.pobierzKlucz(sciezkaDoKeyStore, aliasHasla, hasloDoKeystora));
		RC4wersjaWlasciwa.zapiszZakodowanaWiadomoscDoPilku(kryptogram2);
		
		byte[] zdekodowanyTekst = RC4wersjaWlasciwa.dekoduj(kryptogram,
				RC4wersjaWlasciwa.pobierzKlucz(sciezkaDoKeyStore, aliasHasla, hasloDoKeystora));

		System.out.println(wiadomosc);
		System.out.println(new String(kryptogram));
		System.out.println(new String(zdekodowanyTekst));
		System.out.println(new String(kryptogram2));
		
	}
}
