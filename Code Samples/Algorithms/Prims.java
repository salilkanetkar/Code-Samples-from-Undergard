import java.io.*;
class Prims
{
public static void main(String args[])throws IOException
{
	InputStreamReader isr=new InputStreamReader(System.in);
	BufferedReader br=new BufferedReader(isr);
	System.out.println("\nEnter the no. of vertices: ");
	int v=Integer.parseInt(br.readLine());
	int adj[][]=new int [v+1][v+1];
	int distance[]=new int [v+1];
	boolean visited[]=new boolean [v+1];
	int path[]=new int [v+1];
	int INF=100;
	for(int j=1;j<=v;j++)
	{
		path[j]=0;
		visited[j]=false;
		distance[j]=5588577;
	}
	System.out.println("\nEnter weight if yes and 0 if no: ");
	for(int i=1;i<=v;i++)
		for(int j=1;j<=v;j++)
		{
			if(i==j)
				adj[i][j]=0;
			else
			{
				System.out.print("\nIs vertex "+j+" connected to "+i+": ");
				adj[i][j]=Integer.parseInt(br.readLine());
			}
		}
	System.out.println("\n\nSOLUTION: ");
	int nv=1;
	int current=1; visited[current]=true;
	int min=5588577;
	int index=0;
	while(nv!=v)
	{
		for(int i=1;i<=v;i++)
			if(adj[current][i]!=0)
 				if(!visited[i])
					if(distance[i]>adj[current][i])
  					 {
  						 path[i]=current;
  						 distance[i]=adj[current][i];
  					 }

		for(int i=1;i<=v;i++)
 			if(!visited[i])
 				if(min>distance[i])
  				{
  					min=distance[i];  
  					index=i;
 				 }

		current=index;
		nv++;
		min=INF;
		index=0;
	}
	int sum=0;
	for(int i=2;i<=v;i++)
		sum+=distance[i];
	for(int i=2;i<=v;i++)
		System.out.println("\nVertex "+i+" is connected to "+path[i]);
	System.out.println("\nMinCost: "+sum);
}
}
