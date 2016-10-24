import java.io.*;
import java.net.*;
import java.util.*;

class Slave {
    public static void main(String args[]) throws IOException {
        DatagramSocket clientsocket = new DatagramSocket();
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        InetAddress ip = InetAddress.getByName("10.3.7.23");
        String input = "connectionRequest";
        sendData = input.getBytes();
        DatagramPacket sp = new DatagramPacket(sendData, sendData.length, ip, 2500);
        clientsocket.send(sp);
        while (true) {
            DatagramPacket rp = new DatagramPacket(receiveData, receiveData.length);
            clientsocket.receive(rp);
            String t = new String(rp.getData(), 0, rp.getLength());
            System.out.println("processing " + t);
            Random rn = new Random();
            int rnumber = rn.nextInt(10000);
            try {
                Thread.sleep(rnumber);                 //1000 milliseconds is one second.
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
            System.out.println(t + " is completed");
            input = "completed";
            sendData = input.getBytes();
            sp = new DatagramPacket(sendData, sendData.length, ip, 2500);
            clientsocket.send(sp);
        }
    }
}
