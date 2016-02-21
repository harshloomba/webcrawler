import java.util.HashMap;
import java.util.Set;

public class WordLinkedList {

	private HashMap<String, NextNode> listA;
	
	public WordLinkedList() {
		listA = new HashMap<String, NextNode>();
	}
	
	/*
	 * Adds the String in the Map. If key already exists then increments the node's size in the map
	 */
	public void addKeyWord(String keyword) {
		if (!listA.containsKey(keyword))
			listA.put(keyword, new NextNode(keyword));
		else {
			get(keyword).increment();
		}
	}
		
// Add String in the HashMap and use helper to add children nodes 
public void add(String[] keywords) {
		
		int ind = 1;
		for (String keyword : keywords) {
			addKeyWord(keyword);
			addH(keywords, ind, listA.get(keyword));
			ind++;
		}
	}
	
	//Get Node against the Key
	public NextNode get(String keyword) {
		return listA.get(keyword);
	}
	
	/*
	 * Recursive helper function to add all the children of the paret node
	 */
	private void addH(String[] keywords, int index, NextNode parent) {
		if (index != keywords.length) {
			parent.add(keywords[index]);
			addH(keywords, index + 1, parent.get(keywords[index]));
		}
	}

	//Get Set of Keys in the HashMap
	public Set<String> getKeys() {
		return listA.keySet();
	}
	
	 // Returns the entire HashMap contains all the keys and corresponding children
	public HashMap<String, Integer> getKeywords(String str) {
		return getKeywords(get(str));
	}
	
	/*
	 * Method for getKeywords to find child of child
	 */
	private String gKeywordsH(NextNode node, String keywords) {
		
		if (node.isChildren()) {
			HashMap<String, NextNode> ch = node.getChildren();
			for (String child : ch.keySet()) {
				gKeywordsH(ch.get(child), keywords + " " + child);
			}
		}
		
		map.put(keywords, node.getSize());
		
		return keywords.trim();
	}
	
	/*
	 * Helper function for the function getKeywords(String str)
	 */
	private HashMap<String, Integer> map = null;
	private HashMap<String, Integer> getKeywords(NextNode startNode) {
		
		map = new HashMap<String, Integer>();
		gKeywordsH(startNode, startNode.getValue());
		
		return map;
	}
		
}
