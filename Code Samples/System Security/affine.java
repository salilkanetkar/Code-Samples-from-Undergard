import java.io.*;
import java.math.*;

class affine {
    static char[] encryption(String s, int akey, int mulkey) {
        String temp = s.toLowerCase();
        int cipher[] = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            cipher[i] = c;
            cipher[i] = cipher[i] - 97;

            cipher[i] = cipher[i] * mulkey;
            cipher[i] = cipher[i] % 26;
            cipher[i] = cipher[i] + akey;
            cipher[i] = cipher[i] % 26;

        }
        char a[] = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            a[i] = (char) (cipher[i] + 97);

        }
        return a;
    }

    static int inverse(int mulkey) {
        int inv = 99;
        for (int i = 0; i < 26; i++) {
            if ((i * mulkey) % 26 == 1)
                inv = i;
        }
        return inv;
    }

    static char[] decryption(char s[], int akey, int mulkey) {
        int dcipher[] = new int[s.length];
        int inv = inverse(mulkey);
        System.out.println("the inverse is: " + inv);
        for (int i = 0; i < s.length; i++) {
            char c = s[i];
            dcipher[i] = c;
            dcipher[i] = dcipher[i] - 97;

            if (dcipher[i] - akey < 0)
                dcipher[i] = 26 - Math.abs(dcipher[i] - akey);
            else

                dcipher[i] = dcipher[i] - akey;
            dcipher[i] = dcipher[i] % 26;
            dcipher[i] = dcipher[i] * inv;
            dcipher[i] = dcipher[i] % 26;
        }
        char a[] = new char[s.length];
        for (int i = 0; i < s.length; i++) {
            a[i] = (char) (dcipher[i] + 97);

        }
        return a;
    }


    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("enter the message");
        String msg = br.readLine();
        System.out.println("enter the additive key");
        int akey = Integer.parseInt(br.readLine());
        System.out.println("enter the multiplicative key");
        int mulkey = Integer.parseInt(br.readLine());
        if (inverse(mulkey) == 99) {
            System.out.println("enter proper mulkey");
            System.exit(0);
        }

        char cipher[] = new char[msg.length()];
        cipher = encryption(msg, akey, mulkey);
        System.out.println(cipher);

        char dcipher[] = new char[msg.length()];
        dcipher = decryption(cipher, akey, mulkey);
        System.out.println(dcipher);
    }
}
