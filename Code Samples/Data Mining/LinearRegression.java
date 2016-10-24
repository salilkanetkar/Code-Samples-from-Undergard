import java.util.*;

class LinearRegression {
    public static void main(String args[]) {
        Scanner s = new Scanner(System.in);
        System.out.println("Linear Regression :\n");
        System.out.println("Enter no. of x & y values :");
        int n = s.nextInt();
        int x[] = new int[n];
        int y[] = new int[n];
        System.out.println("Enter all x & y values :");
        double xm = 0, ym = 0;
        for (int i = 0; i < n; i++) {
            x[i] = s.nextInt();
            xm += x[i];
            y[i] = s.nextInt();
            ym += y[i];
        }
        xm /= n;
        ym /= n;
        double t1 = 0, t2 = 0, w0 = 0, w1 = 0;
        for (int i = 0; i < n; i++) {
            t1 += (x[i] - xm) * (y[i] - ym);
            t2 += (x[i] - xm) * (x[i] - xm);
        }
        w1 = t1 / t2;
        w0 = ym - w1 * xm;
        System.out.println("Enter the values for which prediction is to be done :");
        int xx = s.nextInt();
        double yy = w0 + w1 * xx;
        System.out.println("Predicted value is :" + yy);

    }
}
