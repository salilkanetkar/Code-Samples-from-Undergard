import java.io.*;

class DSPA {
    public static void main(String args[]) throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader br = new BufferedReader(isr);
        int v, i, j, min, current, nv, source, dest, x, y, T, INF = 1000;
        System.out.println("Enter the number of vertices in graph: ");
        v = Integer.parseInt(br.readLine());
        int adj[][] = new int[v + 1][v + 1];
        boolean visited[] = new boolean[v + 1];
        int path[] = new int[v + 1];
        int distance[] = new int[v + 1];
        for (i = 1; i <= v; i++)
            for (j = 1; j <= v; j++) {
                System.out.println("Is vertex " + j + " adjacent to vertex " + i + " ?");
                System.out.println("Enter the weight of edge if YES and 0 if NO.");
                adj[i][j] = Integer.parseInt(br.readLine());
            }
        for (i = 1; i < v; i++) {
            visited[i] = false;
            path[i] = 0;
            distance[i] = INF;
        }
        System.out.println("Enter the source of vertex: ");
        source = Integer.parseInt(br.readLine());
        current = source;
        visited[current] = true;
        nv = 1;
        T = 0;
        while (nv != v) {
            for (i = 1; i < v; i++)
                if (adj[current][i] != 0)
                    if (visited[i] != true)
                        if (distance[i] > adj[current][i] + T) {
                            distance[i] = adj[current][i] + T;
                            path[i] = current;
                        }
            min = INF;
            for (i = 1; i < v; i++)
                if (visited[i] != true)
                    if (distance[i] < min) {
                        min = distance[i];
                        current = i;
                    }
            visited[current] = true;
            nv = nv + 1;
            T = distance[current];
        }
        System.out.println("Enter the destination vertex:");
        dest = Integer.parseInt(br.readLine());
        System.out.println("Shortest distance from source to vertex is " + distance[dest]);
        System.out.println("Shortest path between source and vertex is:");
        y = dest;
        do {
            x = path[y];
            System.out.println("Vertex " + y + " is connected to vertex " + x);
            y = x;
        } while (y != source);
    }
}