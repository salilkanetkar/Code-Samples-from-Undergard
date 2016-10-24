import java.io.*;
import java.net.*;

class ChatServer {
    public static void main(String args[]) throws IOException {
        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        ServerSocket ss = new ServerSocket(5555);
        Socket s = ss.accept();
        System.out.println("Handshake Complete");
        BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintStream ps = new PrintStream(s.getOutputStream());
        String a, b;
        do {
            a = br.readLine();
            System.out.println("Client Message:" + a);
            if (a.equalsIgnoreCase("bye"))
                break;
            System.out.print("Server Message:");
            b = input.readLine();
            ps.println(b);
            if (b.equalsIgnoreCase("bye"))
                break;
        }
        while (!a.equalsIgnoreCase("bye"));
        s.close();
    }
}