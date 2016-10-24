import java.io.*;
class BitStuffing
{
	public static void main(String args[])throws IOException
	{
		InputStreamReader isr = new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(isr);
		int ch;
		do
		{
			String h="110";
			String t="01";
			System.out.println("Menu:");
			System.out.println("1. Fixed");
			System.out.println("2. Variable");
			System.out.println("3. Exit");
			System.out.print("Enter your choice:");
			ch=Integer.parseInt(br.readLine());
			switch(ch)
			{
				case 1:
				{
					System.out.print("Enter the string : ");
					String s=br.readLine();
					int fixed=8;
					int l=s.length();
					char x[]=new char[l];
					x=s.toCharArray();
					int nof=(l/fixed)+1;
					int n=0;

					do
					{
						char temp[]=new char[fixed];
						for(int i=fixed*n,j=0;i<fixed*(n+1);i++,j++)
						{
							temp[j]=x[i];
							
						}
						String temp1=new String(temp);
						System.out.println("Frame "+(n+1)+"-");
						System.out.println(h+" "+temp1+" "+t);
						n++;
					}while(n<(nof-1));

					int j1=0;
					char temp2[]=new char[l%fixed];
					for(int k=fixed*n;k<l;k++)
					{
						temp2[j1]=x[k];
						j1++;
					}
					String temp3=new String(temp2);
					System.out.println("Frame "+(n+1)+"-");
					System.out.println(h+" "+temp3+" "+t);
					break;
				}
				case 2:
				{
					System.out.print("Enter the string : ");
					String s=br.readLine();
					s=s.replaceAll("11111","111110");
					System.out.println("Frame -");
					System.out.println(h+" "+s+" "+t);
					break;
				}	

			}
		}while(ch!=3);	
	}
}