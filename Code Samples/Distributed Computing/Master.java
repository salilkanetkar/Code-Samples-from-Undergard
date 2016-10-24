import java.io.*;
import java.net.*;

class Master {
    static String machines[][];

    public static void main(String args[]) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket serversocket = new DatagramSocket(2500);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        int i = 0, m, busynodes = 0;
        InetAddress ip;
        int port;

        System.out.println("Enter the number of nodes");
        m = Integer.parseInt(br.readLine());
        machines = new String[m][3];
        while (i < m) {
            DatagramPacket rp = new DatagramPacket(receiveData, receiveData.length);
            serversocket.receive(rp);
            String input = new String(rp.getData(), 0, rp.getLength());
            ip = rp.getAddress();
            port = rp.getPort();
            System.out.println("node " + ip + " is connected");
            machines[i][0] = ip.toString();
            machines[i][0] = machines[i][0].substring(1);
            machines[i][1] = Integer.toString(port);
            machines[i][2] = "free";
            i++;
        }
        MasterThread mt = new MasterThread(machines, serversocket);
        mt.start();
        while (true) {
            System.out.println("Enter number of task");
            int t = Integer.parseInt(br.readLine());
            i = 1;
            while (i <= t) {
                int j = 0;
                for (j = 0; j < m; j++) {
                    if (machines[j][2].equals("free")) {
                        machines[j][2] = "busy";
                        busynodes++;
                        ip = InetAddress.getByName(machines[j][0]);
                        port = Integer.parseInt(machines[j][1]);
                        String input = "task-" + i;
                        sendData = input.getBytes();
                        DatagramPacket sp = new DatagramPacket(sendData, sendData.length, ip, port);
                        serversocket.send(sp);
                        System.out.println("task-" + i + " is assigned to " + "node " + ip);
                        i++;
                        break;
                    }
                }
            }
            int j = 0;
            while (j != m) {
                for (j = 0; j < m; j++)
                    if (machines[j][2].equals("busy")) {
                        break;
                    }
            }

            System.out.println("All tasks are completed");
        }
    }
}

class MasterThread extends Thread {
    DatagramSocket serversocket;
    String machines[][];

    MasterThread(String machines[][], DatagramSocket serversocket) {
        this.machines = machines;
        this.serversocket = serversocket;
    }

    public void run() {
        while (true) {
            try {
                byte[] receiveData = new byte[1024];
                DatagramPacket rp = new DatagramPacket(receiveData, receiveData.length);
                serversocket.receive(rp);
                InetAddress ip = rp.getAddress();
                String temp = ip.toString();
                for (int j = 0; j < machines.length; j++)
                    if (machines[j][0].equals(temp.substring(1)))
                        machines[j][2] = "free";
            } catch (Exception e) {
            }
        }
    }
}
