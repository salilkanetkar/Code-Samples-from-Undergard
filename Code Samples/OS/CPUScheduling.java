import java.io.*;
import java.util.*;

class CPUScheduling {
    int bt, at, pr, tat, wt, copy_of_bt;
    int no;
    int btm = 0;
    int wtm = 0;
    int tt = 0;

    public static void main(String args[]) throws IOException {
        roundRobin();
        fcfs();
        sjf();
    }

    public static void fcfs() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        int n;
        System.out.println("\n----------FCFS----------");
        System.out.println("Enter the no. of processes:");
        n = Integer.parseInt(br.readLine());
        int btime[] = new int[n];
        for (int i = 0; i < n; i++) {
            System.out.println("Enter the burst time for process " + (i + 1) + ":");
            btime[i] = Integer.parseInt(br.readLine());
        }
        System.out.println("Process\tBurst Time");
        for (int i = 0; i < n; i++)
            System.out.println("  P" + i + "\t    " + btime[i]);
        int x[] = new int[n + 1];
        x[0] = 0;
        for (int i = 1; i < n + 1; i++) {
            x[i] = x[i - 1] + btime[i - 1];
        }
        System.out.println("\n\tGantt Chart\n");
        System.out.print("|");
        for (int i = 0; i < n; i++) {
            System.out.print("  P" + i + "  |");
        }
        System.out.println();
        for (int i = 0; i < n + 1; i++)
            System.out.print(x[i] + "      ");
        System.out.println();
        int wtime[] = new int[n];
        int tatime[] = new int[n];
        float wt = 0;
        for (int i = 0; i < n; i++) {
            wt = wt + x[i];
        }
        float awt = wt / n;
        float tat = 0;
        for (int i = 1; i < n + 1; i++) {
            tat = tat + x[i];
        }
        float atat = tat / n;
        int WT[] = new int[n];
        System.out.println("\nThe avreage waiting turn around time is " + awt);
        System.out.println("The average turn around time is " + atat);
    }

    public static void sjf() {
        Scanner sc = new Scanner(System.in);
        System.out.println("\n\n----------SJF----------");
        System.out.println("Enter no. of processes : ");
        int n = sc.nextInt();
        CPUScheduling op[] = new CPUScheduling[n + 1];    // creatng array of objects
        for (int i = 0; i <= n; i++)
            op[i] = new CPUScheduling();
        int sum = 0;
        for (int m = 1; m <= n; m++) {
            op[m].pr = m;
            System.out.println("Enter the Burst Time and Arrival Time of process " + m + ":");
            op[m].bt = sc.nextInt();
            op[m].at = sc.nextInt();
            sum += op[m].bt;
            op[m].copy_of_bt = op[m].bt;
        }
        System.out.println("Process\tBurst Time\tArrival Time");
        for (int m = 1; m <= n; m++)
            System.out.println("  P" + m + "\t    " + op[m].bt + "\t          " + op[m].at);
        for (int t = 1; t <= sum; t++) {
            int min = 9999, index = 0;
            for (int j = 1; j <= n; j++) {

                if (op[j].at < t && op[j].bt < min && op[j].bt > 0) {

                    min = op[j].bt;
                    index = j;

                }
            }

            op[index].bt -= 1;

            if (op[index].bt == 0) {

                op[index].tat = t - op[index].at;
                op[index].wt = op[index].tat - op[index].copy_of_bt;

            }

        }

        int tot_turn = 0, tot_wait = 0;
        for (int i = 1; i <= n; i++) {

            tot_turn += op[i].tat;
            tot_wait += op[i].wt;

        }
        float avg_turn = (float) tot_turn / n;
        float avg_wait = (float) tot_wait / n;
        for (int m = 1; m <= n; m++) {

            System.out.println("\nProcess " + op[m].pr);
            System.out.print("Turn around time : " + op[m].tat);
            System.out.print("     Waiting time : " + op[m].wt);

        }
        System.out.println("\nTotal turn around time : " + tot_turn);
        System.out.println("Total waiting time : " + tot_wait);
        System.out.println("Avg turn around time : " + avg_turn);
        System.out.println("Avg waiting time : " + avg_wait);
    }

    public static void roundRobin() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        int i, n, j, tq, k, max = 0, m;
        float sum = 0, twt = 0;
        System.out.println("Enter the no. of processes:");
        n = Integer.parseInt(br.readLine());
        int b[] = new int[n];
        int Rrobin[][] = new int[n][n];
        int count[] = new int[n];
        for (i = 1; i <= n; i++) {
            System.out.println("\nEnter the Burst time for process P" + i + ":");
            b[i] = Integer.parseInt(br.readLine());
            if (max < b[i])
                max = b[i];
            wt[i] = 0;
        }
        System.out.println("\nEnter the Time Quantum:");
        tq = Integer.parseInt(br.readLine());
//To find the dimension of the Round robin array
        m = max / tq + 1;
//initializing Round robin array
        for (i = 1; i <= n; i++) {
            for (j = 1; j <= m; j++) {
                Rrobin[i][j] = 0;
            }
        }
//placing value in the Rrobin array
        i = 1;
        while (i <= n) {
            j = 1;
            while (b[i] > 0) {
                if (b[i] >= tq) {
                    b[i] = b[i] - tq;
                    Rrobin[i][j] = tq;
                    j++;
                } else {
                    Rrobin[i][j] = b[i];
                    b[i] = 0;
                    j++;
                }
            }
            count[i] = j - 1;
            i++;
        }
        System.out.println("Display");
        for (i = 1; i <= n; i++) {
            for (j = 1; j <= m; j++) {
                System.out.println("\nRr[" + i + "," + j + "]=" + Rrobin[i][j]);
                System.out.println("\t");
            }
            System.out.println("\nCount:" + count[i]);
            ;
        }
        for (j = 1; j <= n; j++) {
            for (i = 1; i <= count[j]; i++) {
                if (i == count[j]) {
                    for (k = 1; k < j; k++) {
                        if (k != j)
                            wt[j] += Rrobin[k][i];
                    }
                } else
                    for (k = 1; k <= n; k++) {
                        if (k != j)
                            wt[j] += Rrobin[k][i];
                    }
            }
        }
        for (i = 1; i <= n; i++)
            System.out.println("\nWaiting Time for process P" + i + ":" + wt[i]);
//calculating Average Weighting Time
        for (i = 1; i <= n; i++) {
            twt = twt + wt[i];
            tat[i] = b[i] + wt[i];
            sum += tat[i];
        }
        awt = twt / n;
        sum = sum / n;
        System.out.println("\nTotal Waiting Time:" + twt);
        System.out.println("\nAverage Waiting Time:" + awt);
        System.out.println("\nAverage turnaround time:" + sum);
    }
}
    