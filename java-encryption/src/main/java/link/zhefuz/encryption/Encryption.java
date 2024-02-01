package link.zhefuz.encryption;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.Base64;

public class Encryption {
	public static class CaesarCipher {
		private final static String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789,.;:!?()-'";
		private final static int KEY = 4;

		public static String decrypt(String cipherText) {
			try {
				StringBuilder plainText = new StringBuilder();
				for (int i = 0; i < cipherText.length(); i++) {
					char plainCharacter = cipherText.charAt(i);
					int position = ALPHABET.indexOf(plainCharacter);
					int newPosition = (position - KEY + ALPHABET.length()) % ALPHABET.length();
					char cipherCharacter = ALPHABET.charAt(newPosition);
					plainText.append(cipherCharacter);

				}
				return plainText.toString();
			} catch (Exception e) {
				return "Invalid input";
			}
		}

		public static String encrypt(String plainText) {
			try {
				String cipherText = "";
				for (int i = 0; i < plainText.length(); i++) // loop through all characters
				{
					char plainCharacter = plainText.charAt(i);
					int position = ALPHABET.indexOf(plainCharacter); // get the psoition in the alphabet
					int newPosition = (position + KEY) % ALPHABET.length(); // position of the cipher character
					char cipherCharacter = ALPHABET.charAt(newPosition);
					cipherText += cipherCharacter; // appending this cipher character to the cipherText
				}
				return cipherText;
			} catch (Exception e) {
				return "Invalid input";
			}

		}
	}

	public static class DES extends Method {
		private static DES instance;

		private DES() {
			try {
				key = KeyGenerator.getInstance("DES").generateKey();
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
			method = "DES";
		}

		public static DES getInstance() {
			if (instance == null) {
				instance = new DES();
			}
			return instance;
		}
	}

	public static class AES extends Method {
		private static AES instance;

		private AES() {
			try {
				key = KeyGenerator.getInstance("AES").generateKey();
			} catch (NoSuchAlgorithmException e) {
				throw new RuntimeException(e);
			}
			method = "AES";
		}

		public static AES getInstance() {
			if (instance == null) {
				instance = new AES();
			}
			return instance;
		}

	}

	abstract static class Method {
		protected SecretKey key;
		protected String method;

		public String decrypt(String cipherText) {

			try {
				byte[] byteDataToDecrypt = Base64.getDecoder().decode(cipherText);
				Cipher cipher = Cipher.getInstance(method, "BC");
				cipher.init(Cipher.DECRYPT_MODE, key);
				byte[] plainText = new byte[cipher.getOutputSize(byteDataToDecrypt.length)];
				int ptLength = cipher.update(byteDataToDecrypt, 0, byteDataToDecrypt.length, plainText, 0);
				cipher.doFinal(plainText, ptLength);
				System.out.println("plain : " + new String(plainText));
				return new String(plainText).trim();
			} catch (Exception e) {
				return "Decryption failure";
			}
		}

		public String encrypt(String plainText) {

			try {
				Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
				byte[] byteDataToEncrypt = plainText.getBytes();
				Cipher cipher = Cipher.getInstance(method, "BC");
				cipher.init(Cipher.ENCRYPT_MODE, key);
				byte[] cipherText = new byte[cipher.getOutputSize(byteDataToEncrypt.length)];
				int ctLength = cipher.update(byteDataToEncrypt, 0, byteDataToEncrypt.length, cipherText, 0);
				cipher.doFinal(cipherText, ctLength);
				System.out.println("cipher: " + Base64.getEncoder().encodeToString(cipherText) + " bytes: ");
				return Base64.getEncoder().encodeToString(cipherText);
			} catch (Exception e) {
				System.out.println(e);
			}
			return "Encryption failure";
		}

	}

}
