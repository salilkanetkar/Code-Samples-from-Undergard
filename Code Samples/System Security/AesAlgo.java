import java.io.*;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AesAlgo {
    static String IV = "AAAAAAAAAAAAAAAA";

    public static void main(String[] args) throws IOException {
        read();
    }

    static void read() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("\nEnter the plain text(multiple of 16 bytes ) : ");
        String plaintext = br.readLine();

        if (plaintext.length() % 16 != 0) {
            System.out.println("\nEnter the text again as it is not multiple of 16!");
            read();
        }

        System.out.print("\nEnter the encryption text(multiple of 16 bytes ) : ");
        String encryptionKey = br.readLine();

        if (encryptionKey.length() % 16 != 0) {
            System.out.println("\nEnter the text again as it is not multiple of 16!");
            read();
        }
        calculate(plaintext, encryptionKey);
    }

    static void calculate(String plaintext, String encryptionKey) {
        try {
            System.out.println("plain:   " + plaintext);

            byte[] cipher = encrypt(plaintext, encryptionKey);

            System.out.print("cipher:  ");
            for (int i = 0; i < cipher.length; i++)
                System.out.print(new Integer(cipher[i]) + " ");
            System.out.println("");

            String decrypted = decrypt(cipher, encryptionKey);

            System.out.println("decrypt: " + decrypted);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static byte[] encrypt(String plainText, String encryptionKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return cipher.doFinal(plainText.getBytes("UTF-8"));
    }

    public static String decrypt(byte[] cipherText, String encryptionKey) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding", "SunJCE");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(cipher.doFinal(cipherText), "UTF-8");
    }
}
