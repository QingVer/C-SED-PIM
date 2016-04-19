import java.io.*;
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
    private byte encryptedPin[];
    private SecretKey key;
    private byte pinByte[];
    KeyStore store;

    public PinCode(int pin) throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException,
            IllegalBlockSizeException, BadPaddingException, KeyStoreException, CertificateException, IOException {
        if ((pin + "").length() != 4) {
            throw new IllegalArgumentException();
        }
        int pin1 = pin;
        this.encryptedPin = null;

        char pinChar[] = (pin1 + "").toCharArray();
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
        return new String(cipher.doFinal(encryptedPin));
    }

    public boolean allowAccess(String enteredPin)
            throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException,
            BadPaddingException, UnrecoverableEntryException, KeyStoreException, IOException {
        return (decryptPin().equals((enteredPin)));
    }

    private void saveKey() throws KeyStoreException, NoSuchAlgorithmException, CertificateException, IOException {
        File file = new File(DynOSor.rootDirectory + File.separator + "pin.keystore");

        String encoded = Base64.getEncoder().encodeToString(key.getEncoded());
        String encryptedP = Base64.getEncoder().encodeToString(encryptedPin);

        PrintWriter writer = new PrintWriter(file);
        writer.print(encoded);
        writer.println();
        writer.print(encryptedP);
        writer.close();
    }

    private void openKey()
            throws NoSuchAlgorithmException, UnrecoverableEntryException, KeyStoreException, IOException {
        BufferedReader reader = new BufferedReader(new FileReader(new File(DynOSor.rootDirectory + File.separator + "pin.keystore")));
        byte encoded[] = Base64.getDecoder().decode((reader.readLine()));
        encryptedPin = Base64.getDecoder().decode((reader.readLine()));
        reader.close();
        key = new SecretKeySpec(encoded, 0, encoded.length, "AES");
    }
}