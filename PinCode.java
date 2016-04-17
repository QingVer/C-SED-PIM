import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;;

public class PinCode {
	private int pin;
	private byte encryptedPin[];
	private SecretKey key;
	private byte pinByte[];
	KeyStore store;

	public PinCode(int pin) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, BadPaddingException, KeyStoreException, CertificateException, IOException {
		if ((pin + "").length() != 4) {
			throw new IllegalArgumentException();
		}
		this.pin = pin;
		this.encryptedPin = null;

		char pinChar[] = new String(this.pin + "").toCharArray();
		ByteBuffer buffer = Charset.forName("UTF-8").encode(CharBuffer.wrap(pinChar));
		pinByte = new byte[buffer.remaining()];
		buffer.get(pinByte);
		KeyGenerator keyG = KeyGenerator.getInstance("AES");
		keyG.init(128);
		this.key = new SecretKeySpec(keyG.generateKey().getEncoded(), "AES");

		encryptPin();
	}

	public PinCode() throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, IOException {
		openKey();
	}

	private void encryptPin() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException, KeyStoreException, CertificateException, IOException {

		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		encryptedPin = cipher.doFinal(pinByte);
		saveKey();
	}

	private String decryptPin()
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnrecoverableEntryException, KeyStoreException, IOException {
		openKey();
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		String s = new String(cipher.doFinal(encryptedPin));
		return s;
	}

	public boolean allowAccess(String enteredPin)
			throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException, UnrecoverableEntryException, KeyStoreException, IOException {
		return (decryptPin().equals((enteredPin)));
	}

	private void saveKey() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
		File file = new File("pin.keystore");

		String encoded = Base64.getEncoder().encodeToString(key.getEncoded());
		String encryptedP = Base64.getEncoder().encodeToString(encryptedPin);

		BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write(encoded);
		writer.newLine();
		writer.write(encryptedP);
		writer.close();
	}

	private void openKey()
			throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, IOException {
		BufferedReader reader = new BufferedReader(new FileReader(new File("pin.keystore")));
		byte encoded[] = Base64.getDecoder().decode((reader.readLine()));
		encryptedPin = Base64.getDecoder().decode((reader.readLine()));
		reader.close();
		key = new SecretKeySpec(encoded, 0, encoded.length, "AES");
	}
}
