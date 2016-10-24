import java.util.*;

class DiskScheduling {
    static int pos[], n, vis[], tracks = 0, head, max;

    static void read() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Number of positions to be visited : ");
        n = sc.nextInt();
        pos = new int[n + 1];
        System.out.print("Enter positions : ");
        for (int i = 0; i < n; i++)
            pos[i] = sc.nextInt();
        vis = new int[n + 2];
        System.out.print("Enter head position : ");
        head = sc.nextInt();
        int opt = 0;
        System.out.print("Enter maximum position : ");
        max = sc.nextInt();
        do {
            System.out.print("Menu\n1.FCFS\n2.Scan\n3.Look\n4.C-Scan\n5.Exit\n");
            System.out.println("Enter your option : ");
            opt = sc.nextInt();
            switch (opt) {
                case 1:
                    fcfs();
                    break;
                case 2:
                    scan();
                    break;
                case 3:
                    look();
                    break;
                case 4:
                    c_scan();
                    break;
            }
        } while (opt != 5);
    }

    static void fcfs() {
        for (int i = 0; i < n; i++)
            vis[i] = pos[i];
        System.out.println("The positions are visited as follows :");
        for (int i = 0; i < n; i++)
            System.out.print(vis[i] + "    ");
        System.out.println();
        for (int i = 0; i < n - 1; i++) {
            int temp = vis[i] - vis[i + 1];
            if (temp < 0)
                temp = -temp;
            tracks = tracks + temp;
        }
        tracks = tracks + 100 - vis[0];
        System.out.println("Total tracks Visited = " + tracks);
    }

    static void scan() {
        int cp = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = 1; j < n - i; j++) {
                if (pos[j - 1] > pos[j]) {
                    int temp = pos[j];
                    pos[j] = pos[j - 1];
                    pos[j - 1] = temp;
                }
            }
        }
        int i = 0;
        while (pos[i] < head)
            cp = i++;
        for (i = cp; i >= 0; i--)
            vis[cp - i] = pos[i];
        for (i = 0; i <= cp; i++)
            System.out.print(vis[i] + "    ");
        System.out.print("0\t");
        for (i = cp + 1; i < n; i++)
            vis[i] = pos[i];
        for (i = cp + 1; i < n; i++)
            System.out.print(vis[i] + "    ");
        System.out.println();
        for (i = 0; i < cp; i++) {
            int temp = vis[i] - vis[i + 1];
            if (temp < 0)
                temp = -temp;
            tracks = tracks + temp;
        }
        for (i = cp + 1; i < n - 1; i++) {
            int temp = vis[i] - vis[i + 1];
            if (temp < 0)
                temp = -temp;
            tracks = tracks + temp;
        }
        tracks = tracks + 100 - vis[0] + vis[cp] + vis[cp + 1];
        System.out.println("Total tracks Visited = " + tracks);
    }

    static void look() {
        int cp = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = 1; j < n - i; j++) {
                if (pos[j - 1] > pos[j]) {
                    int temp = pos[j];
                    pos[j] = pos[j - 1];
                    pos[j - 1] = temp;
                }
            }
        }
        int i = 0;
        while (pos[i] < head)
            cp = i++;
        for (i = cp; i >= 0; i--)
            vis[cp - i] = pos[i];
        for (i = 0; i <= cp; i++)
            System.out.print(vis[i] + "    ");
        for (i = cp + 1; i < n; i++)
            vis[i] = pos[i];
        for (i = cp + 1; i < n; i++)
            System.out.print(vis[i] + "    ");
        System.out.println();
        for (i = 0; i < n - 1; i++) {
            int temp = vis[i] - vis[i + 1];
            if (temp < 0)
                temp = -temp;
            tracks = tracks + temp;
        }
        tracks = tracks + 100 - vis[0];
        System.out.println("Total tracks Visited = " + tracks);
    }

    static void c_scan() {
        int cp = 0;
        for (int i = 0; i <= n; i++) {
            for (int j = 1; j < n - i; j++) {
                if (pos[j - 1] > pos[j]) {
                    int temp = pos[j];
                    pos[j] = pos[j - 1];
                    pos[j - 1] = temp;
                }
            }
        }
        int i = 0;
        while (pos[i] < head)
            cp = i++;
        for (i = cp; i >= 0; i--)
            vis[cp - i] = pos[i];
        for (i = 0; i <= cp; i++)
            System.out.print(vis[i] + "    ");
        System.out.print("0\t");
        if (pos[n] != max)
            System.out.print(max + "\t");
        for (i = n; i > cp; i--)
            vis[cp + n - i] = pos[i];
        for (i = n - 1; i > cp; i--)
            System.out.print(vis[i] + "    ");
        System.out.println();
        for (i = 0; i < cp; i++) {
            int temp = vis[i] - vis[i + 1];
            if (temp < 0)
                temp = -temp;
            tracks = tracks + temp;
        }
        tracks = tracks + vis[cp] + max + max - vis[cp + 1];
        for (i = cp + 1; i < n - 1; i++) {
            int temp = vis[i] - vis[i + 1];
            if (temp < 0)
                temp = -temp;
            tracks = tracks + temp;
        }
        if (vis[0] <= 100)
            tracks = tracks + 100 - vis[0];
        else
            tracks = tracks - 100 + vis[0];
        System.out.println("Total tracks Visited = " + tracks);
    }

    public static void main(String args[]) {
        read();
    }
}
