import java.util.HashMap;

public class NextNode implements Comparable<Object> {

	private String value;
	private int size;
	private HashMap<String, NextNode> children;
	
	public NextNode(String val) {
		value = val;
		size = 1;
		children = new HashMap<String, NextNode>();
	}
	
	
	 // Returns the specified child of this WordNode
	
	public NextNode get(String child) {
		return children.get(child);
	}
	
	
	// Adds new child in case it doesn't exist otherwise increments the node size.
	public NextNode add(String child) {
		if (!children.containsKey(child))
			children.put(child, new NextNode(child));
		else
			children.get(child).increment();
		
		return children.get(child);
	}
	
	
	public String getValue() {
		return value;
	}
	
	public int getSize() {
		return size;
	}
	
	//add node size
	public void increment() {
		size=size+1;
	}
	
	@Override
	public int compareTo(Object arg0) {
		NextNode a = this;
		NextNode b = (NextNode) arg0;
		
		if (a.getSize() > b.getSize())
			return -1;
		else if (a.getSize() < b.getSize())
			return 1;
		else
			return 0;
	}
	
	// Returns a HashMap of containing all children and respective linked node
	public HashMap<String, NextNode> getChildren() {
		return children;
	}
	
	 //Check if Node has children or not
	public boolean isChildren() {
		return children.size() != 0;
	}
	
}
