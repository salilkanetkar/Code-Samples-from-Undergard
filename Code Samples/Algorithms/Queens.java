import java.io.*;

class Queens {
    public static void main(String args[]) throws IOException {

        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader stdin = new BufferedReader(isr);

        int n;

        System.out.println("Enter n: ");
        n = Integer.parseInt(stdin.readLine());

        int column[] = new int[n + 1]; // why n+1

        nqueens(1, n, column);  // place the first queen in row number 1.

    }

    public static void nqueens(int r, int n, int column[]) {
        int i;
        int c;

        for (c = 1; c <= n; c++)
            if (place(r, c, column)) {
                // placing queen r in row r and column c
                column[r] = c;

                if (r == n)   // all queens are placed
                {
                    for (i = 1; i <= n; i++)   // displaying solution
                        System.out.println("Queen no " + i + " is placed in row no " + i + " and column no " + column[i]);
                    System.exit(1);   // Dont write return
                } else
                    nqueens(r + 1, n, column);   // placing the next queen
            }
    }

    public static boolean place(int r, int c, int column[]) {
        int j;

        for (j = 1; j <= r - 1; j++)

            if ((column[j] == c) || ((Math.abs(column[j] - c)) == (Math.abs(j - r))))

                return false;

        return true;
    }
}
