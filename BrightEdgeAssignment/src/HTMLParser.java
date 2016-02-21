import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.TreeMap;

public class HTMLParser {

	public static void main(String[] args) {
		
	//  String url = "http://www.amazon.com/Cuisinart-CPT-122-Compact-2-Slice-Toaster/dp/B009GQ034C/ref=sr_1_1?s=kitchen&ie=UTF8&qid=1431620315&sr=1-1&keywords=toaster";
	//	String url = "http://blog.rei.com/camp/how-to-introduce-your-indoorsy-friend-to-the-outdoors/";
	//	String url = "http://www.cnn.com/2013/06/10/politics/edward-snowden-profile/";
		String url = args[0];
		
		ParserEngine parser = null;
		try {
			parser = new ParserEngine(url);
		}
		catch (Exception e) {
			System.out.println("\nCONNECTION ERROR. Retry to build the connection.\n");
			return;
		}
		
		Scanner sc = new Scanner(System.in);
		
		Map<String,Integer> s=new TreeMap<String,Integer>(String.CASE_INSENSITIVE_ORDER);
		
		
				// Get keyset of single-word keywords and all keywords stemming from each key in the keyset
				for (String str : parser.list.getKeys()) {
					
					HashMap<String, Integer> keywords = parser.list.getKeywords(str);
					s.putAll(keywords);
						}
				
				Map<String,Integer> d=sortByComparator(s);
				printMap(d);
		
	}
	
	private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap) {

		// Convert Map to List
		List<Map.Entry<String, Integer>> list = 
			new LinkedList<Map.Entry<String, Integer>>(unsortMap.entrySet());

		// Sort list with comparator, to compare the Map values
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1,
                                           Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});

		// Convert sorted map back to a Map
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Iterator<Map.Entry<String, Integer>> it = list.iterator(); it.hasNext();) {
			Map.Entry<String, Integer> entry = it.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	public static void printMap(Map<String, Integer> map) {
		int count=0;
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			int flag=0;
			if(count>70)
				break;
			char[] x=entry.getKey().toCharArray();
			if(x.length==0)
				continue;
			for(int i=0;i<x.length;i++)
			{
				
			if(!Character.isLetter(x[i]))
				flag=1;
			break;
			}
			if (flag==1)
				continue;
	
			System.out.println("[Key] : " + entry.getKey() 
                                      + " [Value] : " + entry.getValue());
			count++;
		}
	}

}

