import java.util.*;
import java.lang.*;
import java.io.*;

/* Name of the class has to be "Main" only if the class is public. */
class permutations
{
	static int cnt = 0;
	public static void main (String[] args) throws java.lang.Exception
	{
		permuteFrequentItemSetToGetAssociationRules(new String[]{"1", "2", "3", "4"}, 0);
	}
	static void permuteFrequentItemSetToGetAssociationRules(String[] arr, int pos)
	{
		if(arr.length- 1 == pos)
			System.out.println((++cnt)+". Adding "+Arrays.deepToString(arr));
		else
			for(int i = pos; i < arr.length; i++)
			{
				//System.out.println("pos is " +pos+" and i is "+i);
				swap(arr, pos, i, true);
				permuteFrequentItemSetToGetAssociationRules(arr, pos+1);
				swap(arr, pos, i, false);
			}
	}
	public static void swap(String[] arr, int pos1, int pos2, boolean isSwap1)
	{
		//System.out.println((isSwap1?"Swap1: ":"Swap2: ")+"Swapping arr["+pos1+"]  and arr["+pos2+"] i.e "+arr[pos1]+" and "+arr[pos2]);
		String s = arr[pos1];
		arr[pos1] = arr[pos2];
		arr[pos2] = s;
	}
}