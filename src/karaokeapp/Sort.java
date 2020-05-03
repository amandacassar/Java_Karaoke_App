package karaokeapp;

public class Sort
{
    public static Song[] mergeSort(Song[] list)
    {
        if (list.length > 1)
	    {
		// creating an array of Songs for the first half from all the songs
		Song[] firstHalf = new Song[list.length / 2];
		System.arraycopy(list, 0, firstHalf, 0, list.length / 2);
		mergeSort(firstHalf);

		// creating an array of Songs for the first half from all the songs
		int secondHalfLength = list.length - list.length / 2;
		Song[] secondHalf = new Song[secondHalfLength];
		System.arraycopy(list, list.length / 2, secondHalf, 0, secondHalfLength);	    
		mergeSort(secondHalf);

		merge(firstHalf, secondHalf, list);
	    }

	return list;
    }

    // method that compares two songs within the two halves and swaps as required
    public static void merge(Song[] list1, Song[] list2, Song[] destination)
    {
	// counters for the lists of songs - firstHalf, secondHalf, list
        int cl1, cl2, cdest;
        cl1 = cl2 = cdest = 0;

        while (cl1 < list1.length && cl2 < list2.length)
	    {
		// comparing two songs
		int cmp = Song.compareSongs(list1[cl1], list2[cl2]);

		// if first song is less than second song
		if (cmp < 0)
		    destination[cdest++] = list1[cl1++];
		// if second song is less than first song
		else
		    destination[cdest++] = list2[cl2++];

		// assuming that no two songs can have the same title
	    }

	// swapping songs as required
        while (cl1 < list1.length)
            destination[cdest++] = list1[cl1++];

        while (cl2 < list2.length)
            destination[cdest++] = list2[cl2++];
    }
}
