import java.util.*;
class Process
{
	int cpu;
	int arr;
	//int rem;
	int strt;
	int pid;
}

class ProSch2
{	static Process p[]=new Process[100];
	static int n;
	public static void main(String args[])
	{
		int i;
		Scanner s=new Scanner(System.in);
		System.out.println("enter no of processes:");
		n=s.nextInt();
	
		for(i=0;i<n;i++)
		{	p[i]=new Process();
			System.out.println("enter details for process "+(i+1));
			System.out.println("enter pid");
			p[i].pid=s.nextInt();
			System.out.println("enter burst time:");
			p[i].cpu=s.nextInt();
			System.out.println("enter arrival time:");
			p[i].arr=s.nextInt();
		}
	
		sjf();
		display();
	}
	

	public static void sjf()
	{

		
		int i,j;
		Process t;
		for(i=0;i<n;i++)
			for(j=0;j<n-1;j++)
				if(p[j].arr>p[j+1].arr)
				{
					
					t=p[j];
					p[j]=p[j+1];
					p[j+1]=t;
				}

		for(i=0;i<n;i++)
		{
			if(i==0)
				p[i].strt=0;
			else
				p[i].strt=p[i-1].cpu+p[i-1].strt;
		}
	}


	static void display()
	{
		System.out.println("pid		burst_time	arrival time	start_time");
		int i;
		for(i=0;i<n;i++)
			System.out.println(p[i].pid+"		"+p[i].cpu+"		"+p[i].arr+"		"+p[i].strt);
	}
}

/*
o/p

D:\java>javac ProSch2.java

D:\java>java ProSch2
enter no of processes:
3
enter details for process 1
enter pid
1
enter burst time:
20
enter arrival time:
0
enter details for process 2
enter pid
2
enter burst time:
6
enter arrival time:
1
enter details for process 3
enter pid
3
enter burst time:
4
enter arrival time:
2
pid             burst_time      arrival time    start_time
1               20              0               0
2               6               1               20
3               4               2               26

*/
