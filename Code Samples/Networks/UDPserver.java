import java.io.*;
import java.net.*;

class UDPserver {
    public static void main(String args[]) throws Exception {
        DatagramSocket ds = new DatagramSocket(2500);
        byte[] datatorec = new byte[1024];
        byte[] datatosend = new byte[1024];
        DatagramPacket recpkt = new DatagramPacket(datatorec, datatorec.length);
        ds.receive(recpkt);
        String input = new String(recpkt.getData());
        InetAddress ip = recpkt.getAddress();
        int port = recpkt.getPort();
        StringBuffer op = new StringBuffer(input);
        op = op.reverse();
        String output = new String(op);
        datatosend = output.getBytes();
        DatagramPacket sendpkt = new DatagramPacket(datatosend, datatosend.length, ip, port);
        ds.send(sendpkt);
        ds.close();
    }
}