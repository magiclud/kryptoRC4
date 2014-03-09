import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Security;
import java.security.UnrecoverableKeyException;
import java.security.KeyStore.Entry;
import java.security.KeyStore.ProtectionParameter;
import java.security.cert.CertificateException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class RC4wersjaWlasciwa {

	static String createKeyStore(String hasloDoKeystora, String aliasHasla) {

		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

		try {
			KeyStore keyStore = KeyStore.getInstance("UBER", "BC");
			InputStream inputStream = null;

			keyStore.load(inputStream, hasloDoKeystora.toCharArray());
			dodajKlucz(keyStore, hasloDoKeystora, aliasHasla);

			return zapiszKeyStore(keyStore, hasloDoKeystora);

		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static void dodajKlucz(KeyStore keyStore, String hasloDoKeystora,
			String aliasHasla) {
		try {
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

			KeyGenerator keyGen = KeyGenerator.getInstance("ARC4", "BC");
			Key secretKey = keyGen.generateKey();
			keyStore.setKeyEntry(aliasHasla, secretKey,
					hasloDoKeystora.toCharArray(), null);
			// ProtectionParameter protParam = new KeyStore.PasswordProtection(
			// hasloDoKeystora.toCharArray());
			// keyStore.setEntry(aliasHasla, entry, protParam);
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String zapiszKeyStore(KeyStore keyStore,
			String hasloDoKeystora) {
		try {
			File keyStoreFile = new File("keyStore.ks");
			FileOutputStream fos = new FileOutputStream(keyStoreFile);
			keyStore.store(fos, hasloDoKeystora.toCharArray());
			fos.close();
			System.out.println("sceizka do keystore"
					+ keyStoreFile.getAbsolutePath());
			return keyStoreFile.getAbsolutePath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static byte[] dekoduj(byte[] kryptogram, Key klucz) {

		try {
			Cipher cipher = Cipher.getInstance("RC4");
			cipher.init(Cipher.DECRYPT_MODE, klucz, cipher.getParameters());
			return cipher.doFinal(kryptogram);
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return kryptogram;
	}

	static Key pobierzKlucz(String sciezkaDoKeyStore, String aliasHasla,
			String hasloDoKeystora) {

		try {
			KeyStore ks = KeyStore.getInstance("UBER", "BC");
			InputStream inputStream = new FileInputStream(sciezkaDoKeyStore);
			ks.load(inputStream, hasloDoKeystora.toCharArray());
			return ks.getKey(aliasHasla, hasloDoKeystora.toCharArray());
		} catch (UnrecoverableKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeyStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CertificateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	static byte[] zakoduj(String wiadomosc, Key key) {
		try {
			Cipher aesCipher = Cipher.getInstance("RC4");
			aesCipher.init(Cipher.ENCRYPT_MODE, key);
			return aesCipher.doFinal(wiadomosc.getBytes());
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadPaddingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static void zapiszZakodowanaWiadomoscDoPilku(byte[] kryptogram) {

		File kryptogramFile = new File("kryptogramZad1.txt");
		FileOutputStream stream;
		try {
			stream = new FileOutputStream(kryptogramFile);

			stream.write(kryptogram);
			stream.close();
			System.out.println("sceizka do kryptogramu"
					+ kryptogramFile.getAbsolutePath());

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String wykonajXor(String wiadomosc, String wiadomosc2) {

		// XOR - DRUGI SPOSÓB
		// int max = Math.max(wiadomosc.length(), wiadomosc2.length());
		// StringBuilder builder = new StringBuilder();
		// for(int i=0; i<max; i++){
		// if(wiadomosc.charAt(i)!=wiadomosc2.charAt(i)){
		// builder.append("1");
		// }else{
		// builder.append("0");
		// }
		// }
		// return builder.toString();

		byte wiad1[] = wiadomosc.getBytes();
		byte wiad2[] = wiadomosc2.getBytes();
		byte wynik[] = new byte[wiad1.length];
		int min = Math.min(wiadomosc.length(), wiadomosc2.length());
		for (int i = 0; i < min; i++) {
			wynik[i] = (byte) (wiad1[i] ^ wiad2[i]);
		}
		for (int i = 0; i < min; i++) {
			System.out.print(wynik[i] + " ");
		}
		System.out.println("");
		return "";
	}

}
