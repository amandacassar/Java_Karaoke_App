package karaokeapp;

public class BST<Key extends Comparable<Key>, Value>
{
    // root of BST
    private Node root;

    // inner class
    private class Node
    {
	private Key key;		// key
	private Value val;		// value
	private Node left, right;	// links to subtrees
		
	// constructor
	public Node (Key key, Value val)
	{
	    this.key = key;
	    this.val = val;
	}
    }

    // search method - using a recursive method 
    public Value get(Key key)
    {
	return get(root, key);
    }

    public Value get(Node x, Key key)
    {
	if (x == null) return null;

	int cmp = key.compareTo(x.key);		// compare key at Node x and the search key

	if (cmp < 0) return get(x.left, key);	// if the search key is less, go left

	if (cmp > 0) return get(x.right, key);	// if the search key is bigger, go right

	else return x.val;			// if they are equal, return value of Node x	    
    }


    // insert method – using a recursive method to return the link to the parent Node of the new Node
    public void put(Key key, Value val)
    {
	// invoke a recursive method, starting at the root – whatever link it returns will set that to root
	root = put(root, key, val);
    }

    // search for key; update value if found / grow table if new
    public Node put(Node x, Key key, Value val)
    {
	if (x == null) return new Node(key, val);		// if tree is empty

	int cmp = key.compareTo(x.key);				// compare the new key with current key

	if (cmp < 0) x.left = put(x.left, key, val);		// if new key is smaller, new root = key on the left

	else if (cmp > 0) x.right = put(x.right, key, val);	// if new key is bigger, new root = key on the right

	else x.val = val;					// if keys are the same, replace value of current key

	return x;						// return the current (new / updated) Node
    }


    // iterate method to traverse the BST in an ordered manner
    public Iterable<Key> keys()
    {
	Queue<Key> q = new Queue<Key>();
	ordered(root, q);
	return q;
    }

    // recursive method to put the items on the queue in an ordered manner
    public void ordered(Node x, Queue<Key> q)
    {
	if (x == null) return;
	ordered(x.left, q);
	q.enqueue(x.key);
	ordered(x.right, q);
    }
}
