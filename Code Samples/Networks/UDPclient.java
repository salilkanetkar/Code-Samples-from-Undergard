import java.io.*;
import java.net.*;

class UDPclient {
    public static void main(String args[]) throws Exception {
        DatagramSocket ds = new DatagramSocket();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        InetAddress ip = InetAddress.getByName("localhost");
        System.out.print("Enter a string : ");
        String input = br.readLine();
        byte[] datatosend = new byte[1024];
        byte[] datatorec = new byte[1024];
        datatosend = input.getBytes();
        DatagramPacket sendpkt = new DatagramPacket(datatosend, datatosend.length, ip, 2500);
        ds.send(sendpkt);
        DatagramPacket recpkt = new DatagramPacket(datatorec, datatorec.length);
        ds.receive(recpkt);
        String output = new String(recpkt.getData());
        System.out.println("Reverse of the entered string is : " + output);
        ds.close();
    }
}