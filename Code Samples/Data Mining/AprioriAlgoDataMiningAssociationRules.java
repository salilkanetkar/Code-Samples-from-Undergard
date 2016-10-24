import java.io.*;
import java.util.*;

class AprioriAlgoDataMiningAssociationRules
{
	static File inputFile;
	static LinkedHashMap<Integer, ArrayList<String>> transactionList = new LinkedHashMap<Integer, ArrayList<String>>();
	static ArrayList<String> uniqueItems = new ArrayList<String>();
	static float minConfidence = 0f, minSupport = 0f;
	static boolean minSupAsCnt = true, minConfidenceAsPercentage = true, kickStartCalculation = true;
	static LinkedHashMap<ArrayList<String>, Integer> perCombinationSupportOdd = new LinkedHashMap<ArrayList<String>, Integer>(), perCombinationSupportEven = new LinkedHashMap<ArrayList<String>, Integer>(), perCombinationFinalForAssociationRulesCalculation = new LinkedHashMap<ArrayList<String>, Integer>();
	//similar to double + inter buffering
	static LinkedHashMap<Integer, LinkedHashMap<ArrayList<String>, Integer>> allPerCombinationSupportFrequentItemSetLTables = new LinkedHashMap<Integer, LinkedHashMap<ArrayList<String>, Integer>>();
	//all L tables, key: cnter => value: table
	static int cnter = 0, lastValidCnter = 0;		//lastValidCnter stores L <lastValidCnter> i.e valid and latest(last) for association rules calculations
	static ArrayList<ArrayList<String>> permutationsAssociationRules = new ArrayList<ArrayList<String>>();
	
	public static void main(String args[])throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Note 1: 1 Transaction record per line as: numID, Item1, Item2 .... ItemN");
		System.out.println("Note 2:  for each line: >1 commas would be clubbed together, trimmed, and all in lower-case");
		while(true)
		{
			System.out.print("Enter csv file name: ");
			inputFile = new File(br.readLine());
			if(inputFile.exists())
				break;
			else
			{
				System.out.println("File doesn't exist! ");
				continue;
			}
		}
		System.out.print("Enter min support as count/percentage(int/float%): ");
		minSupport = getFloatValue(true, false, true);
		try
		{
			System.out.print("Enter min confidence as count/percentage(int/float%): ");
			minConfidence = getFloatValue(true, false, false);
			
			parseAndGetAllItems();
			Collections.sort(uniqueItems);
			System.out.println("\tTID\tItems");
			for(Map.Entry<Integer, ArrayList<String>> entry:transactionList.entrySet())
				System.out.println("\t"+entry.getKey()+"\t"+entry.getValue().toString().substring(1, entry.getValue().toString().length()-1).trim());
			System.out.println("\n----------->Unique Items found: "+uniqueItems);
			
			while(perCombinationSupportOdd.size()!=perCombinationSupportEven.size() || kickStartCalculation)
			{
				if(kickStartCalculation)
					kickStartCalculation = false;
				formNextCandidatesItemsets();
				formCurrentCandidatesSupportAndFilter();
			}
			for(Map.Entry<ArrayList<String>, Integer> entry:perCombinationFinalForAssociationRulesCalculation.entrySet())
			{
				String arr[] = new String[entry.getKey().size()];
				for(int i=0;i<entry.getKey().size();i++)
					arr[i] = entry.getKey().get(i);
				//System.out.println("For "+Arrays.deepToString(arr));
				permuteFrequentItemSetToGetAssociationRules(arr, 0);
			}
			System.out.println("\n\n*********** LHS of rule -> RHS of rule => c = support(allRuleItems)/support(LHS) = allItemsSupport/LHSItemOnlySupport = confidence => confidence*100f => Strong/Weak\n");
			for(ArrayList<String> allRuleItems:permutationsAssociationRules)
			{
				//Association Rule LHS -> RHS
				ArrayList<String> LHS = new ArrayList<String>();
				ArrayList<String> RHS = new ArrayList<String>();
				for(int i=0;i<allRuleItems.size()-1;i++)
				{
					for(int j=0;j<=i;j++)
						LHS.add(allRuleItems.get(j));
					for(int j=i+1;j<allRuleItems.size();j++)
						RHS.add(allRuleItems.get(j));
					float allItemsSupport = (float)getSupport(allRuleItems), LHSItemOnlySupport = (float)getSupport(LHS);
					float confidence = allItemsSupport/LHSItemOnlySupport;
					System.out.println((minConfidenceAsPercentage?((confidence*100f>=minConfidence)?"#! ":""):((confidence>=minConfidence)?"#! ":""))+LHS+" -> "+RHS+" => c = support("+allRuleItems+")/support("+LHS+") = "+allItemsSupport+"/"+LHSItemOnlySupport+" = "+confidence+" => "+(confidence*100f)+" => "+(minConfidenceAsPercentage?((confidence*100f>=minConfidence)?"Strong":"Weak"):((confidence>=minConfidence)?"Strong":"Weak"))+"\n");
					LHS.clear();
					RHS.clear();
				}
			}
		}catch(Exception e)
		{
			System.out.println("Parsing error .. check input data validity!");
			e.printStackTrace();
			System.exit(1);
		}
	}
	static void permuteFrequentItemSetToGetAssociationRules(String[] arr, int pos)
	{
		if(arr.length- 1 == pos)
		{
			ArrayList<String> arrList = new ArrayList<String>();
			for(String s:arr)
				arrList.add(s);
			if(!permutationsAssociationRules.contains(arrList))
				permutationsAssociationRules.add(arrList);
			//System.out.println("Adding "+Arrays.deepToString(arr));
		}else
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

	static void formCurrentCandidatesSupportAndFilter()			//current candidate = cnter
	{
		LinkedHashMap<ArrayList<String>, Integer> tempCombinationsToRemove = new LinkedHashMap<ArrayList<String>, Integer>();
		
		if(cnter%2==1)
			perCombinationSupportEven.clear();
		else
			perCombinationSupportOdd.clear();
			
		if((cnter%2==1?perCombinationSupportOdd:perCombinationSupportEven).size()!=0)
		{
			System.out.println("\n-----------> Candidate "+cnter+":");
			System.out.println("\tFormat: Itemset => Support => support/|T| => %");
			for(Map.Entry<ArrayList<String>, Integer> entry:(cnter%2==1?perCombinationSupportOdd.entrySet():perCombinationSupportEven.entrySet()))
			{
				int sup;
				ArrayList<String> itemset = entry.getKey();
				System.out.println("\tEntry: "+itemset.toString().substring(1, itemset.toString().length()-1).trim()+" => "+(sup = getSupport(itemset))+" => "+sup+"/"+transactionList.size()+" => "+((float)sup/(float)transactionList.size()*100f));
				if(minSupAsCnt?(sup>=minSupport):(((float)sup/(float)transactionList.size()*100f)>=minSupport))
				{
					//update support
					if(cnter%2==1)
						perCombinationSupportOdd.put(itemset, sup);
					else
						perCombinationSupportEven.put(itemset, sup);
				}else
					tempCombinationsToRemove.put(itemset, sup);
			}
			for(Map.Entry<ArrayList<String>, Integer> entry:tempCombinationsToRemove.entrySet())
				(cnter%2==1?perCombinationSupportOdd:perCombinationSupportEven).remove(entry.getKey());
			System.out.println("\n-----------> Frequent ItemSet L "+cnter+":");
			System.out.println("\tItemset => Support");
			if((cnter%2==1?perCombinationSupportOdd:perCombinationSupportEven).size()==0)
			{
				System.out.println("\tEMPTY");
				System.out.println("\n\n*********** Finished Frequent ItemSet Calculations! => Last valid Frequent ItemSet for Association Rule Calculations: L "+lastValidCnter+" which is as follows: \n\n");
				displayFreqItemsetLTableToBeUsedForAssociationRules();
			}else
			{
				lastValidCnter = cnter;
				LinkedHashMap<ArrayList<String>, Integer> perCombinationSupportTemp = new LinkedHashMap<ArrayList<String>, Integer>();
				for(Map.Entry<ArrayList<String>, Integer> entry:(cnter%2==1?perCombinationSupportOdd.entrySet():perCombinationSupportEven.entrySet()))
				{
					System.out.println("\t"+entry.getKey().toString().substring(1, entry.getKey().toString().length()-1).trim()+" => "+entry.getValue());
					perCombinationSupportTemp.put(entry.getKey(), entry.getValue());
				}
				allPerCombinationSupportFrequentItemSetLTables.put(cnter, perCombinationSupportTemp);
			}
		}else
		{
			System.out.println("\n\n*********** Finished Calculations! => Last valid Frequent ItemSet i.e to be used for Association Rule Formation: L "+lastValidCnter+" which is as follows: \n");
			displayFreqItemsetLTableToBeUsedForAssociationRules();
		}
	}
	static void displayFreqItemsetLTableToBeUsedForAssociationRules()
	{
		System.out.println("\tItemset => Support");
		for(Map.Entry<ArrayList<String>, Integer> entry:allPerCombinationSupportFrequentItemSetLTables.get(lastValidCnter).entrySet())
		{
			System.out.println("\t"+entry.getKey().toString().substring(1, entry.getKey().toString().length()-1).trim()+" => "+entry.getValue());
			perCombinationFinalForAssociationRulesCalculation.put(entry.getKey(), entry.getValue());
		}
	}
	static void formNextCandidatesItemsets()
	{
		if(cnter==0)
		{	
			//initial load all items in perCombinationSupportOdd as C1 is 1st, so loading not from previous data(perCombinationSupportEven) but from uniqueItems(derived Input DB)
			for(String item:uniqueItems)
			{
				ArrayList<String> temp = new ArrayList<String>();
				temp.add(item);
				perCombinationSupportOdd.put(temp, null);
			}
		}else
		{
			String s = "";
			ArrayList<ArrayList<String>> temp = new ArrayList<ArrayList<String>>();
			double num;
			if(cnter%2==1)									//for Odd cnter => performing next load of perCombinationSupportEven with combinations from perCombinationSupportOdd
			{
				perCombinationSupportEven.clear();
				for(Map.Entry<ArrayList<String>, Integer> entry:perCombinationSupportOdd.entrySet())
				{
					Collections.sort(entry.getKey());
					temp.add(entry.getKey());
				}
				num = Math.pow(2, perCombinationSupportOdd.size());
				for(int i=0;i<(int)num;i++)
					if((s=makeOfArgSize(perCombinationSupportOdd.size(), Integer.toBinaryString(i))).matches("^0*10*10*$"))
					{
						ArrayList<String> combined = new ArrayList<String>();
						for(int index=s.indexOf("1");index>=0;index=s.indexOf("1", index+1))
							combined.addAll(temp.get(index));
						perCombinationSupportEven.put(new ArrayList<String>(new HashSet<String>(combined)), null);
					}
			}else											//for Even cnter => performing next load of perCombinationSupportOdd with combinations from perCombinationSupportEven
			{
				perCombinationSupportOdd.clear();
				for(Map.Entry<ArrayList<String>, Integer> entry:perCombinationSupportEven.entrySet())
				{
					Collections.sort(entry.getKey());
					temp.add(entry.getKey());
				}
				num = Math.pow(2, perCombinationSupportEven.size());
				for(int i=0;i<(int)num;i++)
					if((s=makeOfArgSize(perCombinationSupportEven.size(), Integer.toBinaryString(i))).matches("^0*10*10*$"))
					{
						ArrayList<String> combined = new ArrayList<String>();
						for(int index=s.indexOf("1");index>=0;index=s.indexOf("1", index+1))
							combined.addAll(temp.get(index));
						perCombinationSupportOdd.put(new ArrayList<String>(new HashSet<String>(combined)), null);
					}
			}
		}
		cnter++;
	}
	static String makeOfArgSize(int toMakeOfLength, String s)
	{
		if(s.length()==toMakeOfLength)
            return s;
		while((s="0"+s).length()!=toMakeOfLength);
		return s;
	}
	static float getFloatValue(boolean needOnlyPositiveValue, boolean zeroAllowed, boolean forSupport)
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		do
		{
			try
			{
				String s = br.readLine().replaceAll("\\s+", "").trim();
				if(forSupport)
					minSupAsCnt = !s.endsWith("%");
				else
					minConfidenceAsPercentage = s.endsWith("%");
				s = s.endsWith("%")?s.substring(0, s.length()-1):s;
				float temp = Float.parseFloat(s);
				if(needOnlyPositiveValue && zeroAllowed && temp<0)
					throw new Exception();
				else if(!needOnlyPositiveValue && zeroAllowed && temp>0)
					throw new Exception();
				else if(needOnlyPositiveValue && !zeroAllowed && temp<=0)
					throw new Exception();
				else if(!needOnlyPositiveValue && !zeroAllowed && temp>=0)
					throw new Exception();
				return temp;
			}catch(Exception e)
			{
				System.out.print("An error occurred, please re-enter: ");
			}
		}while(true);
	}
	static void parseAndGetAllItems()throws Exception
	{
		System.out.println("\n----------->Input DB: ");
		BufferedReader br = new BufferedReader(new FileReader(inputFile));
		String line = "";
		ArrayList<String> tempArrList;
		while((line=br.readLine())!=null)
		{
			line = line.replaceAll(",\\s+,", ",,").replaceAll(",+", ",").trim().toLowerCase();
			if(line.length()>0)
			{
				String arr[] = line.split(",");
				tempArrList = new ArrayList<String>();
				for(int i=0;i<arr.length;i++)
					if(i>0)
					{
						tempArrList.add(arr[i] = arr[i].trim());
						if(!uniqueItems.contains(arr[i]))
							uniqueItems.add(arr[i]);
					}else
						arr[i] = arr[i].trim();
				for(int i=1;i<arr.length;i++)
				transactionList.put(Integer.parseInt(arr[0]), tempArrList);
			}
		}
	}
	static int getSupport(ArrayList<String> itemset)
	{
		int support = 0;
		boolean containsAll = true;
		for(Map.Entry<Integer, ArrayList<String>> entry:transactionList.entrySet())
		{
			containsAll = true;
			for(int i=0;i<itemset.size();i++)
				if(!(entry.getValue().contains(itemset.get(i))))
				{
					containsAll = false;
					break;
				}
			if(containsAll)
				support++;
		}
		return support;
	}
}