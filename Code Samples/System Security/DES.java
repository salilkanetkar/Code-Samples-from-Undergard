import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class DES {
    private static Cipher encryptCipher;
    private static Cipher decryptCipher;

    public static void main(String[] args) {
        try {
            KeyGenerator keygenerator = KeyGenerator.getInstance("DES");
            SecretKey secretKey = keygenerator.generateKey();

            encryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            encryptCipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedData = encryptData("D17A C-Batch People!");

            decryptCipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
            decryptCipher.init(Cipher.DECRYPT_MODE, secretKey);
            decryptData(encryptedData);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
    }

    private static byte[] encryptData(String data)
            throws IllegalBlockSizeException, BadPaddingException {
        System.out.println("Data Before Encryption :" + data);
        byte[] dataToEncrypt = data.getBytes();
        byte[] encryptedData = encryptCipher.doFinal(dataToEncrypt);
        System.out.println("Encryted Data: " + encryptedData);

        return encryptedData;
    }

    private static void decryptData(byte[] data)
            throws IllegalBlockSizeException, BadPaddingException {
        byte[] textDecrypted = decryptCipher.doFinal(data);
        System.out.println("Decryted Data: " + new String(textDecrypted));
    }
}
