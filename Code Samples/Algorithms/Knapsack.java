import java.io.*;
class Knapsack
{
    public static void main(String args[]) throws IOException
    {
        InputStreamReader isr=new InputStreamReader(System.in);
        BufferedReader stdin=new BufferedReader(isr);

        int i,j,n; double  m, profit, capacity, fraction, t1, t2;

        System.out.println("Enter the number of objects: ");
        n=Integer.parseInt(stdin.readLine());

        double  p[]=new  double[n]; double  w[]=new  double[n];
        double  x[]=new  double[n];

        System.out.println("Enter the Knapsack capacity: ");
        m=Integer.parseInt(stdin.readLine());

        System.out.println("Enter the profit and weight of each object:");
        for(i=0;i<n;i++)
        {
            System.out.println("Profit of Object No:"+i);
            p[i]=Integer.parseInt(stdin.readLine());
            System.out.println("Weight of Object No:"+i);
            w[i]=Integer.parseInt(stdin.readLine());
        }

        //Arranging p and w in decreasing order of p/w
        for(i=0;i<x.length-1;i++)
            for(j=0;j<x.length-i-1;j++)
                if(p[j]/w[j] < p[j+1]/w[j+1])
                {
                    t1=p[j];       p[j]=p[j+1];       p[j+1]=t1;
                    t2=w[j];      w[j]=w[j+1];     w[j+1]=t2;
                }

        // Initializing the solution array
        for(i=0;i<n;i++)
            x[i]=0;
        profit=0;	 capacity=m;

        // Filling the knapsack with entire objects
        for(i=0;i<n;i++)
            if(capacity-w[i]>=0)  // true if object can be inserted
            {
                capacity=capacity-w[i];
                profit=profit+p[i];
                x[i]=1;		// Entire Object is selected
            }
            else
                break;
        // Knapsack may or may not be full at this stage
        // Inserting fraction of last object if required
        if(capacity!=0) // true if knapsack not entirely full
        {
            fraction=capacity/w[i];
            capacity=capacity-w[i]*fraction;  // not required
            profit=profit+p[i]*fraction;
            x[i]=fraction;
        }
        // Displaying  Solution
        System.out.println("Selection of objects in their decreasing order of p/w ratio is as shown:");
        System.out.print("(");
        for(i=0;i<n;i++)
            System.out.print(x[i]+"  ");
        System.out.println(")");
        System.out.println("Total profit is "+profit);
    }
}
