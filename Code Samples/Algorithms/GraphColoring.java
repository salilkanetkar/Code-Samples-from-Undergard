import java.io.*;
public class GraphColoring
{
	static int [][] G;
	static int [] x;
	static int n, m;
	static boolean found = false;

	public static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	public static void main(String[] args) throws IOException
	{
		System.out.println("\t\t\t\tGRAPH COLORING");
		System.out.print("\nEnter the number of the vertices: ");
		n = Integer.parseInt(br.readLine());
		G = new int[n+1][n+1];
		x = new int[n+1];
		System.out.print("\nIf edge between the following vertices enter 1 else 0:\n");
		for(int i=1;i<=n;i++)
			for(int j=1;j<=n;j++)
			{
				if((i!=j)&&(i<j))
				{
					System.out.print(i+" and "+j+": ");
					G[j][i]=G[i][j] = Integer.parseInt(br.readLine());
				}
				if(i==j)
					G[i][j]=0;
			}
		System.out.print("\nEnter the number of colors available: ");
		m = Integer.parseInt(br.readLine());
		System.out.println("\nSolution:");
		mColoring(1);
		if (found == false)
			System.out.println("No Solution possible!");
	}

	static void mColoring(int k)
	{
		while(true)
		{
			NextValue(k);
			if(x[k] == 0)
				return;
			if(k == n)
			{
				for(int i=1; i<=k;i++)
					System.out.print(x[i]+" ");
				System.out.println();
				found = true;
				return;
			}
			else
				mColoring(k+1);
		}
	}

	static void NextValue(int k)
	{
		int j;
		while(true)
		{
			x[k] = (x[k]+1)%(m+1);
			if(x[k]==0)
				return;
			for(j=1; j<=n; j++)
				if( (G[k][j] != 0) && (x[k] == x[j]) )
					break;
			if(j == n+1)
				return;
		}
	}
}