import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class ParserEngine {
	
	private URL url;
	
	public WordLinkedList list;
	
	private HashSet<String> ignorewords;
	
	
	public ParserEngine(String siteURL) {
		try {
			url = new URL(siteURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		list = new WordLinkedList();
		ignorewords = new HashSet<String>();
		
		parseStopwords();
		parseKeywords();
	}
	
	/*
	 * Generates the set of stopwords used to find breaks between keywords
	 */
	private void parseStopwords() {
		Scanner sc = null;
		sc = new Scanner(getClass().getResourceAsStream("ignorewords.txt"));
		
		while (sc.hasNext())
			ignorewords.add(sc.next());
	}
	
	/*
	 * Returns true if a string is a stopword
	 */
	private boolean stopword(String word) {
		return word.endsWith(",") || ignorewords.contains(word); 
	}
	
	private boolean isUpper(String s)
	{
	    for (char c : s.toCharArray()) {
	        if(!Character.isUpperCase(c))
	            return false;
	    }
	    return true;
	}
	
		
	/*
	 * Connection is established with the webpage. 
	 */
	private void parseKeywords() {
		
		// Connect to site
		Document document = null;
		try {
			document = Jsoup.connect(url.toString()).timeout(500000000).userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.2 (KHTML, like Gecko) Chrome/15.0.874.120 Safari/535.2").get();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Scanner sc = new Scanner(document.text());
		
		System.out.println("checking Document in String Form:"+document.text());
		
		String word = "";
		
		//Stream of words before any such characters .,!
		String stream = "";
		
		// Iterate over the document
		while (sc.hasNext()) {

			word = sc.next();

			String text = word.trim();
			text = text.replace("'s", "");
			text = text.replace(":", "");
			text = text.replace("", "");
			text = text.replace("©", "");
			text = text.replace("\"", "");
			text = text.replace(",", "");
			text = text.replace("!", "");
			text = text.replace("?", "");
			
			if (text.startsWith("'") || text.endsWith("'"))
				text = text.replace("'", "");
			
			if (text.endsWith(".") && !isUpper(text.substring(text.length()-2, text.length()-1)))
				text = text.replace(".", "");

			/*
			 *  If the next word is the end of a stream then then that stream will be inserted 
			 *  in the WordLinkedList
			 */
			if ((text.endsWith(".") || text.endsWith(",") || text.endsWith("!") || text.endsWith("?") 
					|| text.endsWith("©")) || (stopword(text.toLowerCase()))) {
				//  If word ended with punctuation then remove all of it and add word to stream
				if ((text.endsWith(".") || text.endsWith(",") || text.endsWith("!") || text.endsWith("?") || text.endsWith("©"))) {
					stream += text + " ";
				}
				if (!stream.equals(" ") && stream.length() != 0) {
					String[] keywords = stream.trim().split(" ");
					list.add(keywords);
					stream = "";
				}
			}
			else {
				stream += text + " ";
			}
		}
	}

	
}
