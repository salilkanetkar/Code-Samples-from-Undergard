import java.util.*;

class KMeansClustering {
    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        System.out.println("Clustering :\n");
        System.out.println("Enter no. of values :");
        int n = s.nextInt();
        int c[] = new int[n];
        int c1[] = new int[n];
        int c2[] = new int[n];
        System.out.println("Enter the values :");
        for (int i = 0; i < n; i++)
            c[i] = s.nextInt();
        double m1 = c[0];
        double m2 = c[1];
        double t1 = 0, t2 = 0;
        c1[0] = c[0];
        c2[0] = c[1];
        System.out.println("Means :" + m1 + " , " + m2);
        for (int i = 2; i < n; i++) {
            t1 = Math.abs(m1 - c[i]);
            t2 = Math.abs(m2 - c[i]);
            if (t1 <= t2) {
                for (int j = 0; j < n; j++)
                    if (c1[j] == 0) {
                        c1[j] = c[i];
                        break;
                    }
            } else {
                for (int j = 0; j < n; j++)
                    if (c2[j] == 0) {
                        c2[j] = c[i];
                        break;
                    }
            }
        }
        System.out.println("\nIteration 1 :");
        System.out.println("Cluster 1 :");
        for (int i = 0; i < n; i++)
            if (c1[i] != 0)
                System.out.print(c1[i] + " ");
        System.out.println();
        System.out.println("Cluster 2 :");
        for (int i = 0; i < n; i++)
            if (c2[i] != 0)
                System.out.print(c2[i] + " ");
        System.out.println();
        m1 = 0;
        m2 = 0;
        int a = 0;
        for (a = 0; c1[a] != 0; a++)
            m1 += c1[a];
        m1 /= a;
        for (a = 0; c2[a] != 0; a++)
            m2 += c2[a];
        m2 /= a;
        System.out.println("Means :" + m1 + " , " + m2);
        for (int i = 0; i < n; i++) {
            t1 = Math.abs(m1 - c1[i]);
            t2 = Math.abs(m2 - c1[i]);
            if (t2 < t1) {
                for (int j = 0; j < n; j++)
                    if (c2[j] == 0) {
                        c2[j] = c1[i];
                        c1[i] = 0;
                        break;
                    }
            }
        }
        for (int i = 0; i < n; i++) {
            t1 = Math.abs(m1 - c2[i]);
            t2 = Math.abs(m2 - c2[i]);
            if (t1 <= t2) {
                for (int j = 0; j < n; j++)
                    if (c1[j] == 0) {
                        c1[j] = c2[i];
                        c2[i] = 0;
                        break;
                    }
            }
        }
        System.out.println("\nIteration 2 :");
        System.out.println("Cluster 1 :");
        for (int i = 0; i < n; i++)
            if (c1[i] != 0)
                System.out.print(c1[i] + " ");
        System.out.println();
        System.out.println("Cluster 2 :");
        for (int i = 0; i < n; i++)
            if (c2[i] != 0)
                System.out.print(c2[i] + " ");
        System.out.println();
        m1 = 0;
        m2 = 0;
        int n1 = 0;
        int n2 = 0;
        for (a = 0; a < n; a++)
            if (c1[a] != 0) {
                m1 += c1[a];
                n1++;
            }
        m1 /= n1;
        for (a = 0; a < n; a++)
            if (c2[a] != 0) {
                m2 += c2[a];
                n2++;
            }
        m2 /= n2;
        System.out.println("Means :" + m1 + " , " + m2);
        for (int i = 0; i < n; i++) {
            t1 = Math.abs(m1 - c1[i]);
            t2 = Math.abs(m2 - c1[i]);
            if (t2 < t1) {
                for (int j = 0; j < n; j++)
                    if (c2[j] == 0) {
                        c2[j] = c1[i];
                        c1[i] = 0;
                        break;
                    }
            }
        }
        for (int i = 0; i < n; i++) {
            t1 = Math.abs(m1 - c2[i]);
            t2 = Math.abs(m2 - c2[i]);
            if (t1 <= t2) {
                for (int j = 0; j < n; j++)
                    if (c1[j] == 0) {
                        c1[j] = c2[i];
                        c2[i] = 0;
                        break;
                    }
            }
        }
        System.out.println("\nIteration 3 :");
        System.out.println("Cluster 1 :");
        for (int i = 0; i < n; i++)
            if (c1[i] != 0)
                System.out.print(c1[i] + " ");
        System.out.println();
        System.out.println("Cluster 2 :");
        for (int i = 0; i < n; i++)
            if (c2[i] != 0)
                System.out.print(c2[i] + " ");
        System.out.println();
        m1 = 0;
        m2 = 0;
        n1 = 0;
        n2 = 0;
        for (a = 0; a < n; a++)
            if (c1[a] != 0) {
                m1 += c1[a];
                n1++;
            }
        m1 /= n1;
        for (a = 0; a < n; a++)
            if (c2[a] != 0) {
                m2 += c2[a];
                n2++;
            }
        m2 /= n2;
        System.out.println("Means :" + m1 + " , " + m2);
    }
}
