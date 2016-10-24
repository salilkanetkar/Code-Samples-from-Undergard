import java.io.*;
import java.util.*;

class Framing {
    public static void main(String args[]) throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        System.out.print("Enter the string  : ");
        String s = br.readLine();
        int ch;
        String h = "110";
        String t = "01";
        String flag = "01111110";
        do {
            System.out.println("Menu:");
            System.out.println("1. Fixed");
            System.out.println("2. Variable");
            System.out.println("3. Exit");
            System.out.println("Enter your choice:");
            ch = Integer.parseInt(br.readLine());
            switch (ch) {
                case 1: {
                    int fixed = 8;
                    int l = s.length();
                    char x[] = new char[l];
                    x = s.toCharArray();
                    int nof = (l / fixed) + 1;
                    int n = 0;

                    do {
                        char temp[] = new char[fixed];
                        for (int i = 8 * n, j = 0; i < 8 * (n + 1); i++, j++)
                            temp[j] = x[i];
                        String temp1 = new String(temp);
                        System.out.println("Frame " + (n + 1));
                        System.out.println(h + " " + temp1 + " " + t);
                        n++;
                    } while (n < (nof - 1));

                    int j1 = 0;
                    char temp2[] = new char[l % fixed];
                    for (int k = 8 * n; k < l; k++) {
                        temp2[j1] = x[k];
                        j1++;
                    }
                    String temp3 = new String(temp2);
                    System.out.println("Frame " + (n + 1));
                    System.out.println(h + " " + temp3 + " " + t);
                    break;
                }
                case 2: {
                    Random random = new Random();
                    String stemp;
                    stemp = s.replaceAll("11111", "111110");
                    int f = stemp.length();
                    char qts[] = new char[f];
                    qts = stemp.toCharArray();
                    int rdm = random.nextInt(f);
                    System.out.println("Random Number : " + rdm);
                    System.out.println("Frame 1:\n");
                    System.out.print(flag + " " + h + " ");
                    for (int w = 0; w < rdm; w++)
                        System.out.print(qts[w]);
                    System.out.print(" " + t + " " + flag);
                    System.out.println();
                    System.out.println("\nFrame 2:\n");
                    System.out.print(flag + " " + h + " ");
                    for (int w = rdm; w < f; w++)
                        System.out.print(qts[w]);
                    System.out.print(" " + t + " " + flag);
                    System.out.println();
                    break;
                }
                case 3:
                    break;
            }
        } while (ch != 3);
    }
}


