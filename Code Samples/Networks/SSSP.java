import java.io.*;

class SSSP {
    public static void main(String args[]) throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        System.out.print("Enter the no of vertices : ");
        int v = Integer.parseInt(br.readLine());
        int adj[][] = new int[v + 1][v + 1];
        int distance[] = new int[v + 1];
        int path[] = new int[v + 1];
        boolean visited[] = new boolean[v + 1];
        int current, min, INF = 1000, nv = 1, T = 0, source, dest, x, y, a, b, w, e;
        System.out.print("Enter number of edges : ");
        e = Integer.parseInt(br.readLine());
        for (int i = 1; i <= v; i++)
            for (int j = 1; j <= v; j++)
                adj[i][j] = 22222;
        for (int i = 1; i <= v; i++)
            adj[i][i] = 0;
        for (int i = 1; i <= e; i++) {
            System.out.println("Enter edge information:");
            a = Integer.parseInt(br.readLine());
            b = Integer.parseInt(br.readLine());
            System.out.println("Enter weight:");
            w = Integer.parseInt(br.readLine());
            adj[a][b] = w;
        }

        for (int i = 1; i <= v; i++) {
            visited[i] = false;
            distance[i] = INF;
            path[i] = 0;
        }
        System.out.print("Enter the source vertex : ");
        source = Integer.parseInt(br.readLine());
        current = source;

        while (nv != v) {
            for (int i = 1; i <= v; i++)
                if (adj[current][i] != 0)
                    if (visited[i] != true)
                        if (distance[i] > adj[current][i] + T) {
                            distance[i] = adj[current][i] + T;
                            path[i] = current;
                        }

            min = INF;
            for (int i = 1; i <= v; i++)
                if (visited[i] != true)
                    if (distance[i] < min) {
                        min = distance[i];
                        current = i;
                    }

            visited[current] = true;
            nv = nv + 1;
            T = distance[current];
        }

        System.out.print("\nEnter the destination : ");
        dest = Integer.parseInt(br.readLine());

        System.out.println("Shortest distance is : " + distance[dest]);

        System.out.println("Path is : ");
        y = dest;
        do {
            x = path[y];
            System.out.println("Vertex " + y + " is connected to vertex " + x + ".");
            y = x;
        } while (y != source);
    }
}	