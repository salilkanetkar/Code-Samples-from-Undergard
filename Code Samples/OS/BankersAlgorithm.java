import java.io.*;
import java.util.*;

class BankersAlgorithm {
    public static void main(String args[]) throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        int clm[][] = new int[7][5];
        int req[][] = new int[7][5];
        int alloc[][] = new int[7][5];
        int rsrc[] = new int[5];
        int avai[] = new int[5];
        int comp[] = new int[7];
        int first, p, r, i, j, prc, count = 0, t;
        for (i = 1; i < 7; i++)
            comp[i] = 0;
        System.out.print("Enter the no. of processes : ");
        p = Integer.parseInt(br.readLine());
        System.out.print("\nEnter the no. of resources  : ");
        r = Integer.parseInt(br.readLine());
        System.out.println("\nEnter the claim for each process : ");
        for (i = 1; i <= p; i++) {
            System.out.println("\nFor process " + i + " : ");
            for (j = 1; j <= r; j++) {
                clm[i][j] = Integer.parseInt(br.readLine());
            }
        }
        System.out.println("\nEnter the allocation for each process  : ");
        for (i = 1; i <= p; i++) {
            System.out.println("\nFor process " + i + " : ");
            for (j = 1; j <= r; j++) {
                alloc[i][j] = Integer.parseInt(br.readLine());
            }
        }
        System.out.println("\nEnter the instances of each resource : ");
        for (j = 1; j < r; j++)
            rsrc[j] = Integer.parseInt(br.readLine());
        for (j = 1; j < r; j++) {
            avai[j] = 0;
            int total = 0;
            for (i = 1; i <= p; i++) {
                total += alloc[i][j];
            }
            avai[j] = rsrc[j] - total;
        }
        do {
            for (i = 1; i <= p; i++) {
                for (j = 1; j < r; j++) {
                    req[i][j] = clm[i][j] - alloc[i][j];
                }
            }
            System.out.println("\n\nAvailable resorces are : ");
            for (j = 1; j < r; j++) {
                System.out.println(" " + avai[j]);
            }
            System.out.println("\nClaim Matrix:\t\tAllocation Matrix:\n");
            for (i = 1; i <= p; i++) {
                for (j = 1; j < r; j++) {
                    System.out.print(clm[i][j] + " ");
                }
                System.out.print("\t\t\t");
                for (j = 1; j < r; j++) {
                    System.out.print(alloc[i][j] + " ");
                }
                System.out.println("\n");
            }
            prc = 0;
            for (i = 1; i <= p; i++) {
                if (comp[i] == 0) {
                    prc = i;
                    for (j = 1; j <= r; j++) {
                        if (avai[j] < req[i][j]) {
                            prc = 0;
                            break;
                        }
                    }
                }
                if (prc != 0)
                    break;
            }
            if (prc != 0) {
                System.out.println("Process  " + prc + " runs to completion.");
                count++;
                for (j = 1; j <= r; j++) {
                    avai[j] += alloc[prc][j];
                    alloc[prc][j] = 0;
                    clm[prc][j] = 0;
                    comp[prc] = 1;
                }
            }
        }
        while (count != p && prc != 0);
        if (count == p)
            System.out.println("\nThe system is in a safe state.");
        else
            System.out.println("\nThe system is in a unsafe state.");
    }
}