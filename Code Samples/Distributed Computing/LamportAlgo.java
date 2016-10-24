import java.io.*;
import java.util.*;

class LamportAlgo extends Thread {
    int time;
    int no;
    static String message;
    static LamportAlgo la[];
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static boolean flag = true;
    static int count;

    LamportAlgo(int no) {
        this.no = no;
        Random r = new Random();
        this.time = r.nextInt((400 - 1) + 1) + 1;
        this.start();
    }

    LamportAlgo() throws IOException {
        System.out.print("Enter the Number of Processes : ");
        int pno = Integer.parseInt(br.readLine());
        System.out.println();
        la = new LamportAlgo[pno + 1];
        for (int i = 0; i <= pno; i++)
            la[i] = new LamportAlgo(i);
    }

    public synchronized void run() {
        while (true) {
            if (this.no != la.length - 1) {
                System.out.println("Process " + this.no + " :: " + this.time);
                count++;
            }
            if (count == la.length - 1) {
                count = 0;
            }
            try {
                if (this.no == la.length - 1) {
                    msgPass();
                } else wait();
            } catch (Exception ioe) {
                System.out.println(ioe.toString());
            }
        }
    }

    static void msgPass() throws IOException {
        System.out.println("\nEnter the message along with sender and destination processes");
        st = new StringTokenizer(br.readLine());
        System.out.println();
        int sender = 0, receiver = 0;
        int i = 0;
        while (st.hasMoreTokens()) {
            if (i == 0) message = st.nextToken();
            if (i == 1) sender = Integer.parseInt(st.nextToken());
            if (i == 2) receiver = Integer.parseInt(st.nextToken());
            i++;
        }
        if (message.equals("exit")) System.exit(0);
        System.out.println("Process " + la[sender].no + ": -------> Process " + la[receiver].no + "\n");
        clockSynch(la[receiver], la[sender].time);
        la[sender].time++;
        display();
        msgPassRep(message, receiver, sender, receiver, la[sender].time - 1);
    }

    static void clockSynch(LamportAlgo lac, int time) {
        if (lac.time <= time) {
            lac.time = time + 1;
        }
    }

    static void msgPassRep(String message, int sender, int source, int rec, int t) {
        int receiver = 0;
        for (int i = 0; i < la.length; i++) {
            receiver = i;
            if (receiver != source && rec != receiver) {
                System.out.println("Process " + la[sender].no + ": -------> Process “+la[receiver].no+"\n");
                        clockSynch(la[receiver], t);
                sender = receiver;
                display();
            }
        }
    }

    static void display() {
        for (int i = 0; i < la.length; i++)
            System.out.println("Process " + la[i].no + " : " + la[i].time);
        System.out.println();
    }
}

class LamportMain {
    public static void main(String args[]) throws IOException {
        LamportAlgo la = new LamportAlgo();
    }
}
