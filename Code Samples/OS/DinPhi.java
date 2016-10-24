class DinPhi
{static int i=1;
	static int fork[]=new int[5];
	public static void main(String args[])
	{
		philosopher(i);
	}

	static void philosopher(int i)
	{
		while(i<6)
		{
			think(i);
			wait(i-1);
			wait(i%5);
			eat(i);
			signal(i%5);
			signal(i-1);
			i++;
		}

	}

	static void think(int i)
	{
		System.out.println("philosopher thinking: "+i);
	}
	
	static void wait(int i)
	{
		if(fork[i]==0)
		{
			fork[i]=1;
			System.out.println("philosopher waiting for fork :"+(i+1));
		}
		else
			System.out.println("fork not available"+(i+1));
	}
	
	static void eat(int i)
	{
		System.out.println("philosopher eating: "+i);	
	}

	static void signal(int i)
	{
	fork[i]=0;
		System.out.println("fork returned"+(i+1));
	}
}