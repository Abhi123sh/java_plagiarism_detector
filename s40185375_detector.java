import java.util.*;
import java.io.*;
import java.text.DecimalFormat;

public class s40185375_detector 
{
	static final DecimalFormat df = new DecimalFormat("0.00");
	static String file1Name;
	static String file2Name;
	static String file1Data[], file2Data[];
	static Set<String> file1DataSet = new HashSet<String>();
	static Set<String> file2DataSet = new HashSet<String>();
	static FileReader fileReader;
	static BufferedReader bufferedReader;
	static Set<String> stopWords = new HashSet<String>(Arrays.asList("en","a", "about", "above", "after", "again", "against", "all", "am", "an", "and", "any", "are", "aren't", "as", "at", "be", "because", "been", "before", "being", "below", "between", "both", "but", "by", "can't", "cannot", "could", "couldn't", "did", "didn't", "do", "does", "doesn't", "doing", "don't", "down", "during", "each", "few", "for", "from", "further", "had", "hadn't", "has", "hasn't", "have", "haven't", "having", "he", "he'd", "he'll", "he's", "her", "here", "here's", "hers", "herself", "him", "himself", "his", "how", "how's", "i", "i'd", "i'll", "i'm", "i've", "if", "in", "into", "is", "isn't", "it", "it's", "its", "itself", "let's", "me", "more", "most", "mustn't", "my", "myself", "no", "nor", "not", "of", "off", "on", "once", "only", "or", "other", "ought", "our", "ours", "ourselves", "out", "over", "own", "same", "shan't", "she", "she'd", "she'll", "she's", "should", "shouldn't", "so", "some", "such", "than", "that", "that's", "the", "their", "theirs", "them", "themselves", "then", "there", "there's", "these", "they", "they'd", "they'll", "they're", "they've", "this", "those", "through", "to", "too", "under", "until", "up", "very", "was", "wasn't", "we", "we'd", "we'll", "we're", "we've", "were", "weren't", "what", "what's", "when", "when's", "where", "where's", "which", "while", "who", "who's", "whom", "why", "why's", "with", "won't", "would", "wouldn't", "you", "you'd", "you'll", "you're", "you've", "your", "yours", "yourself", "yourselves"));
	static double codeThreshold = 22.50, textThreshold = 34.50;
	
	//method to calculate and return the minimum distance to convert the data of file1 to file2
	public static int getLevenshteinDistance(String file1Data, String file2Data)
	{
		int file1Size = file1Data.length();
		int file2Size = file2Data.length();

		int[][] T = new int[file1Size + 1][file2Size + 1];
		for (int i=1; i <= file1Size; i++) 
		{
			T[i][0] = i;
		}
		for (int j = 1; j <= file2Size; j++) 
		{
			T[0][j] = j;
		}

		int cost;
		for (int i = 1; i <= file1Size; i++) 
		{
			for (int j = 1; j <= file2Size; j++) 
			{
				cost = file1Data.charAt(i - 1) == file2Data.charAt(j - 1) ? 0: 1;
				T[i][j] = Integer.min(Integer.min(T[i - 1][j] + 1, T[i][j - 1] + 1),
						T[i - 1][j - 1] + cost);
			}
		}

		return T[file1Size][file2Size];
	}
	
	//the main algorithm to calculate the percentage of similarity between two source code files
	public static double findSimilarity(String file1Data, String file2Data) 
	{
		if (file1Data == null || file2Data == null) 
		{
			throw new IllegalArgumentException("Strings must not be null");
		}
		
		double maxLength = Double.max(file1Data.length(), file2Data.length());
		if (maxLength > 0) 
		{
			return (1-(getLevenshteinDistance(file1Data,file2Data)/maxLength));
		}
		return 1.0;
	}
	
	//method to calculate and return the number of common elements in two datasets 
	public static int intersectionsCount(Set file1DataSet, Set file2DataSet) 
	{
	    if (file2DataSet.size() < file1DataSet.size()) 
	    {
	    	return intersectionsCount(file2DataSet, file1DataSet);
	    }
	    int count = 0;
	    for (Object o : file1DataSet)
	        {
	    		if (file2DataSet.contains(o)) 
	    		{
	    			count++;
	    		}
	        }
	    return count;
	}

	//method that uses the jaccard algorithm to calculate the similarity between two text files  
	public static double getJaccardSimilarity(Set file1DataSet, Set file2DataSet) 
	{
	    int intersection = intersectionsCount(file1DataSet, file2DataSet);
	    int union = file1DataSet.size() + file2DataSet.size() - intersection;
	    return (double) intersection/ union;
	}
	
	//method that pre-processes source code files and displays whether the files are plagiarised or not
	public static void codeFileProcessing(String file1, String file2)
	{
			file1 = file1.replaceAll("\\s", "");
			file2 = file2.replaceAll("\\s", "");
			double similarity = Double.parseDouble(df.format(findSimilarity(file1,file2)*100));
			System.out.println(similarity<=codeThreshold?0:1);
	}
	
	//method that pre-processes text files and displays whether the files are plagiarised or not
	static void textFileProcessing(String file1Content, String file2Content)
	{
		file1Data = file1Content.replaceAll("\\W", " ").toLowerCase().split("\\s+");
		for(String str: file1Data)
		{
			if(!stopWords.contains(str) && !str.equals(""))
			{
				file1DataSet.add(str);
			}
		}
						
		file2Data = file2Content.replaceAll("\\W", " ").toLowerCase().split("\\s+");			
		for(String str: file2Data)
		{
			if(!stopWords.contains(str) && !str.equals(""))
			{
				file2DataSet.add(str);
			}
		}
		double result =Double.parseDouble(df.format(getJaccardSimilarity(file1DataSet,file2DataSet)*100));
		System.out.println(result<=textThreshold?0:1);
								
	}
	
	//method that reads the data from the files and checks whether the files are source code or text files
	static void processFilesForValidation(String file1Name, String file2Name) throws IOException
	{
		fileReader = new FileReader(file1Name);
		bufferedReader = new BufferedReader(fileReader);
		String file1Content="";
		String temp = null;
		while((temp=bufferedReader.readLine())!=null)
		{
			file1Content+=temp+"\n";
		}
		fileReader = new FileReader(file2Name);
		bufferedReader = new BufferedReader(fileReader);
		String file2Content="";
		temp = null;
		while((temp=bufferedReader.readLine())!=null)
		{
			file2Content+=temp+"\n";
		}
		
		if(file1Content.charAt(0)==('/') || file1Content.charAt(0)==('#') || (file1Content.contains(" ") ? file1Content.split(" ")[0] : file1Content).equals("import"))
		{
			if(file2Content.charAt(0)==('/') || file2Content.charAt(0)==('#') || (file2Content.contains(" ") ? file2Content.split(" ")[0] : file2Content).equals("import"))
			{
				codeFileProcessing(file1Content,file2Content);
			}
			else
			{
//				System.out.println("Both files must be source code!");
				System.exit(0);
			}
		}
		else
		{
			if(file2Content.charAt(0)==('/') || file2Content.charAt(0)==('#') || (file2Content.contains(" ") ? file2Content.split(" ")[0] : file2Content).equals("import"))
			{
//				System.out.println("Both files must be text!");
				System.exit(0);
			}
			textFileProcessing(file1Content,file2Content);
		}
		
	}
	
	//Driver code
	public static void main(String[] args) throws IOException 
	{
		file1Name = args[0];
		file2Name = args[1];
		processFilesForValidation(file1Name,file2Name);
		System.exit(0);
		
	}
}