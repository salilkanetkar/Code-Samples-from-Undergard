import java.io.*;
import java.util.*;

class BullyElection extends Thread {
    static BullyElection be[];
    boolean alive;
    int number;
    static int nodes;
    static int coordinator;
    final static String electionmsg = "  Election---->  ";
    final static String broadcastmsg = "  Coordinator====>  ";
    final static String okmsg = "  Ok/Alive---->  ";

    BullyElection(int number, boolean alive) {
        this.number = number;
        this.alive = alive;
    }

    BullyElection(BullyElection be[], int rand) {
        this.be = be;
        this.be[rand].start();
    }

    public void run() {
        boolean flag = true;
        int i = this.number;
        for (; flag; i++)
            flag = be[i].election();
        i--;
        if (!flag) {
            coordinator = i;
            be[i].broadcast();
        }
    }

    boolean election() {
        for (int i = this.number + 1; i < nodes; i++)
            System.out.println((this.number + 1) + electionmsg + (i + 1));
        if (response()) return true;
        return false;
    }

    boolean response() {
        boolean flag = false;
        for (int i = this.number + 1; i < nodes; i++) {
            if (be[i].alive) {
                System.out.println((i + 1) + okmsg + (this.number + 1));
                flag = true;
            }
        }
        return flag;
    }

    void broadcast() {
        for (int i = 0; i < nodes - 2; i++)
            if (be[i].alive)
                System.out.println((this.number + 1) + broadcastmsg + (i + 1));
    }
}

class BullyRun {
    static int nodes;

    public static void main(String args[]) throws IOException {
        BullyElection[] be = input();
        Random rn = new Random();
        int rand = rn.nextInt(BullyRun.nodes);
        BullyElection bully = new BullyElection(be, rand);
    }

    static BullyElection[] input() throws IOException {
        System.out.print("Enter number of processes : ");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BullyRun.nodes = Integer.parseInt(br.readLine());
        System.out.println("Process " + BullyRun.nodes + " is crashed\n");
        BullyElection.nodes = BullyRun.nodes;
        BullyElection be[] = new BullyElection[BullyRun.nodes];
        int i = 0;
        for (; i < nodes - 1; i++)
            be[i] = new BullyElection(i, true);
        be[i] = new BullyElection(i, false);
        return be;
    }
}
