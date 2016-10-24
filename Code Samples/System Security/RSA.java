import java.io.*;

class RSA {
    public static void main(String args[]) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        int p, q, n, e, d = 0, ct, pt, on;
        System.out.println("Enter the value of p:");
        p = Integer.parseInt(input.readLine());
        System.out.println("Enter the value of q:");
        q = Integer.parseInt(input.readLine());
        n = p * q;
        on = (p - 1) * (q - 1);
        System.out.println("Enter the value of e:");
        e = Integer.parseInt(input.readLine());
        while (true) {
            d++;
            if ((d * e) % on == 1)
                break;
        }
        System.out.println("d =" + d);
        System.out.println("Enter plain text");
        pt = Integer.parseInt(input.readLine());
        ct = (int) Math.pow(pt, e) % n;
        System.out.println("Cipher text " + ct);
        int pt1 = (int) Math.pow(ct, d) % n;
        System.out.println("Plain text " + pt1);
    }
}
