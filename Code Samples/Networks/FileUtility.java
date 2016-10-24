import java.io.*;
import java.util.*;

class FileUtility {
    public static void main(String args[]) throws IOException {
        int ch;
        char x[] = new char[100];
        int count = 0, i = 0, n, j;
        String word, word2, s1, s2, s3;
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);

        FileReader f = new FileReader(args[0]);
        while ((ch = f.read()) != -1) {
            x[i] = (char) ch;
            i++;
        }
        f.close();
        String line = new String(x);
        System.out.println("The line is:\n" + line);
        System.out.println("Enter the word to be searched:");
        word = br.readLine();

        s1 = line.toLowerCase();
        s2 = word.toLowerCase();

        System.out.println(s1);
        System.out.println(s2);

        n = s2.length();

        while (true) {
            i = s1.indexOf(s2);
            if (i != -1) {
                count++;
                s1 = s1.substring(i + n);
            } else
                break;
        }
        System.out.println("The word " + word + " occurs " + count + " times");

        System.out.println("Enter the word to be replaced:");
        word2 = br.readLine();

        s1 = line.toLowerCase();
        System.out.println("Enter the new word");
        s3 = br.readLine();
        s1 = s1.replaceAll(word2, s3);
        System.out.println(s1);

        FileWriter w = new FileWriter("a.txt");
        w.write(s1);
        w.close();


    }
}