import java.io.*;
class DistanceVectorRouting
{
	public static void main(String args[])throws IOException
	{
		InputStreamReader isr=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(isr);
		int i,j,k,n,src,s,d,ch;
		int a[][]=new int[10][10];
		int b[][]=new int[10][10];
		System.out.print("Enter the number of nodes : ");
		n=Integer.parseInt(br.readLine());
		for(i=1;i<=n;i++)
			for(j=1;j<=n;j++)
				if(i==j)
					a[i][j]=0;
				else
				{
					System.out.println("Enter the distance between host "+i+" and "+j+" : ");
					a[i][j]=Integer.parseInt(br.readLine());
				}
		for(i=1;i<=n;i++)
		{
			for(j=1;j<=n;j++)
				System.out.println(a[i][j]);
			System.out.println();
		}
		do
		{
			System.out.println("Enter the node to display the routing table : ");
			src=Integer.parseInt(br.readLine());
			for(j=1;j<=n;j++)
			{
				if(src!=j)
				{
					if(a[src][j]!=0)
						System.out.println("The shortest path from "+src+" to "+j+" = "+a[src][j]+".");
					else
						System.out.println("There is no path from "+src+" to "+j+".");
				}
			}
			System.out.print("Do you want to continue(1/0) : ");
			ch=Integer.parseInt(br.readLine());
		}while(ch!='0');
		for(k=1;k<=n;k++)
			for(i=1;i<=n;i++)
				for(j=1;j<=n;j++)
					if(a[i][j]>a[i][k]+a[k][j])
						a[i][j]=a[i][k]+a[k][j];
		for(i=1;i<=n;i++)
		{
			for(j=1;j<=n;j++)
				System.out.println(a[i][j]+"  ");
			System.out.println();
		}
	}
}