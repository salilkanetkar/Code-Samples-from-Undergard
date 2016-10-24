import java.io.*;
import java.net.*;

public class ChatClient {
    public static void main(String args[]) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        Socket s = new Socket("localhost", 5555);
        System.out.println("Send bye to end chat");
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        String a, b;
        PrintStream ps = new PrintStream(s.getOutputStream());
        do {
            System.out.print("Client Message:");
            a = input.readLine();
            ps.println(a);
            if (a.equalsIgnoreCase("bye"))
                break;
            b = br.readLine();
            System.out.println("Server Message:" + b);
            if (b.equalsIgnoreCase("bye"))
                break;
        }
        while (!a.equalsIgnoreCase("bye"));
    }
}