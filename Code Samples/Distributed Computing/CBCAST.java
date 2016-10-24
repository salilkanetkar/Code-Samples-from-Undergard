import java.util.*;

class CBCAST {
    public static void main(String args[]) {
        int n, i, j, s, c;
        Scanner st = new Scanner(System.in);
        System.out.println("Enter the total number of processes");
        n = st.nextInt();
        int vector[][] = new int[n][n];

        for (i = 0; i < n; i++) {
            System.out.println("Enter the vector for process " + i);
            for (j = 0; j < n; j++) {
                vector[i][j] = st.nextInt();
            }
        }
        System.out.println("Enter the process id of sender");
        s = st.nextInt();
        vector[s][s] += 1;
        System.out.println("Process " + s + " is trying to send a message to all other process");

        for (i = 0; i < n; i++) {
            c = 0;
            if (i == s) {
                continue;
            }
            if (vector[s][s] == (vector[i][s] + 1)) {
                for (j = 0; j < n; j++) {
                    if (j != s) {
                        if (vector[s][j] <= vector[i][j]) {
                            c++;
                        }
                    }
                }

                if (c == n - 1) {
                    System.out.println("Message delivered to process " + i);
                } else {
                    System.out.println("Message delayed to process " + i + " because condition 2 of CBCAST protocol not satisfied");
                }
            } else {
                System.out.println("Message delayed to process " + i + " because condition 1 of CBCAST protocol not satisfied");
            }
        }
    }
}
