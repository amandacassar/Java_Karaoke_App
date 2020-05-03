package karaokeapp;

import java.util.Iterator;

public class Queue<T> implements Iterable<T>
{    
    Node front = null;
    Node back = null;

    // inner class
    private class Node
    {
	T item;
	Node next;

	// constructor
	public Node(T item, Node next)
	{
	    this.item = item;
	    this.next = next;
	}
    }


    // method to insert new item at the end
    public void enqueue(T item)
    {	
	Node temp = back;
	back = new Node(item, null);
	
	if(isEmpty())
	    front = back;
	else
	    temp.next = back;
    }


    // method to remove the first item
    public T dequeue()
    {
	T item = front.item;
	front = front.next;
	return item;
    }


    // method to check if Queue is empty
    public boolean isEmpty()
    {
	return front == null;
    }


    // iterator method to traverse the Queue collection
    public Iterator<T> iterator()
    {
	return new QueueIterator();
    }

    // inner class implementing the hasNext() and next() methods to iterate through the collection
    private class QueueIterator implements Iterator<T>
    {
	private Node current = front;

	public boolean hasNext()
	{
	    return current != null;
	}

	public T next()
	{
	    T item = current.item;
	    current = current.next;
	    return item;
	}
    }
}

