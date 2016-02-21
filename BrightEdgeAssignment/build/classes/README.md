									Author: Harsh Loomba

SUMMARY OF ALGORITHM

Overall the key point of the algorithm are as follows:

1. Documents (Webpages) are parsed, which are specified by URL.

2. Now parsing starts on the document and strings being created, by appending the words fed from the URL into the string, while parsing and every time when punctuation arrives, while scanning the document, string breaks at that point of time and pushed into the HashMap of kind <Node, Size, NextNodes (could be multiple)>.
Important point to mention, I have created the ignorewords.txt file having those words (such as among, yours, you, with) which not need to be processed. Otherwise, if scanned word is present in the ignored file then it ignores that word and continue appending the string.  

3. During Hashing process, if key is already present in the HashMap then size of that keyword is incremented otherwise 
It’s pushed into the HashMap along with the children, if any. From Children I mean to say if there is a following structure of sentence.

Sentence – "REI Blog", "REI HOME"

So my algorithm will feed “REI” with its incremental size and a child “Blog”. And there would be another entry having “Blog” is parent and its corresponding size and its children. By doing this we are able to find how many times any combination (“REI Blog”, “REI Home”) are present on our webpage.

4. Now once HashMap is built, next process is to sort the HashMap by overriding comparator and print out the most frequent words.

-------------------------------------------------
Algorithm is divided into 4 Java Classes
-------------------------------------------------

1: HTMLParser.java - 
Contains Main method which calls ParserEngine.java Class with URL and in return we get the intended data of Type NextNode class having the following structure

Value - the object needs to be evaluated
Size - frequency of the object on the targeted URL 
Children - words which are connected to the object as I mentioned above

Overall, it’s like a LinkedList having multiple next nodes which are connected to their parent node and it keeps growing like a tree.

I have opted this structure to find out the occurrence of words in a combination.

Once we received the Parsed data, we iterated over the data and created the final sortedHashMap (sorted in descending order of the number of times words occur). To sort, I have overridden the comparator and used CASEINSENSITIVE HashMap.

2. Parse Engine.java -
 
Foremost, I have created one text file (named -ignorewords.txt) having all those words which no need to be evaluated. 
I have applied a logic where words are continuously read and appended into the string until any sort of punctuation (such as , . !) arrives. At that point of time, the string which has been built so far moves to be processed to get entered into the HashMap. In the HashMap the string gets inserted in the below fashion

Example:
---------
Adding these four keywords

{"Cuisinart", "Cuisinart CPT-122", "Cuisinart CPT-122 2-Slice"} 

Would look like this:
	  
list.add({"Cuisinart"});		// single-word keyword
list.add({"Cuisinart", "CPT-122"});	// 2-word keyword
list.add({"Cuisinart", "CPT-122",2-Slice}); // 3-word keyword

And the tree looks like this:

	  Cuisinart [Value] : 34 
	  CPT-122 [Value] : 9
	  2-Slice [Value] : 8
	  Cuisinart CPT-122 [Value] : 8
	  Cuisinart CPT-122 2-Slice [Value] : 7
	   
3. WordLinkedList.java - It helps in adding the String into HashMap and recursively added the children into the nextnode of the parent node.

4. NextNode.java - Here I defined the data structure having Object, size of the object and children of the object. 
	   
Aforementioned signifies that "Cuisinart" is the parent of all other children nodes and its data structure look like 



Example of Structure setup - 

List - (Parent,size,children)

(Cuisinart [Value] : 34)  -->  CPT-122 [Value] : 8  -->  2-Slice  [Value] : 7

CPT-122 [Value] : 9  --> 2-Slice [Value] : 3 

2-Slice [Value] : 7

 Just like how each node in a linked list points to the next one, each NODE here points to each of its children using the map. The key-value pairing for the map is as follows:

(child object, child NODE)

The 'child object' is the key used to reference the child, and the 'child NODE' is another NODE that represents the child. Since the child is also a NODE, then it too can have it's own children.

LinkedList is a collection of NODEs stored in a HashMap. The map contains key-value pairings of an Object and its respective NODE (Object, NODE).

When adding to the TREE you can either add a single Object, or a collection of Objects where each successive Object is a "child" of the previous one.

-------------------------------------
Keyword Extraction Example:
-------------------------------------
If the sentence parsed is: 

"Amazon Toast might never be considered as good product in United States again"

The parsing would be as follows:

"Amazon"
"Toast"
STOPWORD: "might" -> list.add({"Amazon", "Toast"})
STOPWORD: "never"
STOPWORD: "be"
STOPWORD: "considered"
STOPWORD: "as"
"good"
"product"
STOPWORD: "in" -> list.add({"good", “product”})
STOPWORD: "the"
"United"
"States"
STOPWORD:  "again" -> tree.add({"United", "states"})


And the tree would look like this:

--------------------------------
("Amazon", 1) --> ("Toast", 1)

("Toast", 1)

(“good”,1) --> (“product” ,1)

("product", 1)

("United", 1) --> (“States”,1)

(“States”,1)

The top level of the List contains the keywords 

{"Amazon", "Toast",  "good", “product", "United" } 

Subsequently, in the tree their children who represent the keywords 

{"Amazon Toast"} 

{“good product”}

{“United” “States”}

Finally, we sorted the keywords according to their frequency of occurrence and fetch the most used keywords. And in my case I have assumed that top 40 words are the most frequent words. It’s an assumption.
