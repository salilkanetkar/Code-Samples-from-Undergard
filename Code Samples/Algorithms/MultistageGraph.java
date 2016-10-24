import java.io.*;

class MultistageGraph {
    public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static int[][] G;
    static int n;
    static int k; //stages

    public static void main(String[] args) throws IOException {
        System.out.println("\t\t\t\tMultistage Graph");

        System.out.print("\nEnter the number of the vertices: ");
        n = Integer.parseInt(br.readLine());
        G = new int[n + 1][n + 1];

        System.out.print("\nEnter the total number of stages in your graph: ");
        k = Integer.parseInt(br.readLine());

        System.out.print("\nIf there is a edge between the following vertices enter its weight else 0:\n");
        for (int i = 1; i <= n; i++)
            for (int j = 1; j <= n; j++) {
                G[i][j] = 0;
                if ((i != j) && (i < j)) {
                    System.out.print(i + " and " + j + ": ");
                    G[i][j] = Integer.parseInt(br.readLine());
                }
            }
        FGraph();
    }

    static void FGraph() {
        int[] cost = new int[n + 1];
        int[] d = new int[n + 1];
        int[] p = new int[k + 1];
        for (int i = 1; i <= n; i++)
            cost[i] = 0;

        for (int j = n - 1; j >= 1; j--) {
            int r = findR(j + 1);
            cost[j] = G[j][r] + cost[r];
            d[j] = r;
        }

        p[1] = 1;
        p[k] = n;
        for (int j = 2; j < k; j++)
            p[j] = d[p[j - 1]];

        System.out.print(d[1] + "-");
        for (int j = 2; j <= n; j++) {
            if ((d[j] == d[j - 1]) || (d[j] == 0))
                continue;

            System.out.print(d[j] + "-");
        }
        System.out.print(n);
    }

    static int findR(int cu) {
        int r1 = n + 1;
        for (int h = 1; h <= n; h++) {
            if ((G[h][cu] != 0) && (r1 == n + 1)) {
                r1 = h;
                continue;
            }
            if (G[h][cu] != 0) {
                if (G[h][cu] < G[r1][cu])
                    r1 = h;
            }
        }
        return r1;
    }
}