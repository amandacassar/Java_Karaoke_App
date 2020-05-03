package karaokeapp;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Controller 
{
    // method to search for specific song title
    public static BST<String, Song> searchSongs(String _criteria) throws FileNotFoundException
    {
	BST<String, Song> bst = new BST<String, Song>();
        
        // declaring the file that contains the data about songs
        File songsFile = new File("src/sample_song_data.txt");

	// variable to count the input size - for time complexity analysis
        int inputSize = 0;
	
        try
	    {
		// declaring the reader
		FileReader fr = new FileReader(songsFile);
		BufferedReader br = new BufferedReader(fr);
            
		String currentLine;		
            
		// while there is a line to read, check if the line index is the same as the button index
		// if yes, do not write this line to the temporary file
		while ((currentLine = br.readLine()) != null) 
		    {
			// split the line where there is a tab and store each element inside array songData
			String[] songData = currentLine.split("\t");  
                
			// obtain the title of the current song
			String title = songData[0];
                    
			// changing all letters to lower case and removing any leading/trailing spaces
			title  = title.toLowerCase().trim();
		
			if (title.contains(_criteria)) 
			    {
				// creating a new variable of type Song, which will store the data of the current song being obtained
				Song currentSong = new Song();                  
				currentSong.setTitle(songData[0]);
				currentSong.setArtist(songData[1]);
				currentSong.setSeconds(Integer.parseInt(songData[2]));
				currentSong.setVidFileName(songData[3]);

				// adding the current song's data to the BST   
				bst.put(title, currentSong); 
			    }                        
						
			inputSize++;
		    }
		br.close();
          
	    }
        catch(Exception ex)
	    {
		ex.printStackTrace();
	    }   

	// displaying the input size - for time complexity analysis
	System.out.println("Song Search by Title - input size = " + inputSize);
	
        return bst;
    }

    
    // method to obtain full list of songs
    public static BST<String, Song> listAllSongs() throws FileNotFoundException
    {        
        // declaring the file that contains the data about songs
        File songsFile = new File("src/sample_song_data.txt");
        
        // declaring the variable of type Song that will store the data about the list of songs extracted
        BST<String, Song> bst = new BST<String, Song>();
	BST<String, Song> sortedBST = new BST<String, Song>();

	// variable to count the input size - for time complexity analysis
        int inputSize = 0;
        
        try
	    {
		// declaring the reader 
		FileReader fr = new FileReader(songsFile);
		BufferedReader br = new BufferedReader(fr);
            
		String currentLine;		
            
		// while there is a line to read, add this song
		while ((currentLine = br.readLine()) != null) 
		    {
			String[] songData = currentLine.split("\t");

			String songTitle = songData[0];
                
			// creating a new variable of type Song, which will store the data of the current song being obtained
			Song currentSong = new Song();                  
			currentSong.setTitle(songData[0]);
			currentSong.setArtist(songData[1]);
			currentSong.setSeconds(Integer.parseInt(songData[2]));
			currentSong.setVidFileName(songData[3]);

			// adding the current song's data to the BST
			bst.put(songTitle, currentSong);

			inputSize++;
		    }

		// iterating through the BST to obtain a new sorted BST
		for (String x : bst.keys())
		    sortedBST.put(x, bst.get(x));
	 
	    }
        catch(Exception ex)
	    {
		ex.printStackTrace();
	    }               
        
	// displaying the input size - for time complexity analysis
	System.out.println("List All Songs Sorted - input size = " + inputSize);

        // return the list of songs in the file
        return sortedBST;
    }
       
    
    // method to add a new song at the end of the playlist file
    public static String addToPlaylist(String _title, String _artist, int _seconds, String _vidFileName) throws FileNotFoundException
    {
        // declaring the playlist text file
        File playlistFile = new File("src/playlist.txt");
    
        String result = "Failed - No song";
        
        try
	    {
		// add a new line in the playlist textfile
		// FileWriter allows to append text - true is flag for append mode
		FileWriter fw = new FileWriter(playlistFile, true);                                                 
		BufferedWriter bw = new BufferedWriter(fw);            
            
		// adding the new song details - each element is seperated with a tab - moving to the next line
		bw.write(_title + "\t" + _artist + "\t" + _seconds + "\t" + _vidFileName + "\n");      
		bw.close();
            
		result = _title  + " has been added to the playlist";
	    }
        catch(Exception ex)
	    {
		ex.printStackTrace();
	    }   
        
        return result;
    }
    
    
    // method to list all songs in the current playlist
    public static List<Song> listPlaylistSongs() throws FileNotFoundException
    {        
        // declaring the playlist text file
        File playlistFile = new File("src/playlist.txt");
        
        // declaring the variable of type Song that will store the data about the list of songs extracted
        List<Song> songsList = new ArrayList<>();
        
        // if the playlist file exists, get the songs - else if it does not exis, return message
        if (playlistFile.exists())
	    {
		try
		    {
			// declaring the reader 
			FileReader fr = new FileReader(playlistFile);
			BufferedReader br = new BufferedReader(fr);

			String currentLine;

			// while there is a line to read, check if the line index is the same as the button index
			// if yes, do not write this line to the temporary file
			while ((currentLine = br.readLine()) != null) 
			    {
				String[] songData = currentLine.split("\t");

				// creating a new variable of type Song, which will store the data of the current song being obtained
				Song currentSong = new Song();                  
				currentSong.setTitle(songData[0]);
				currentSong.setArtist(songData[1]);
				currentSong.setSeconds(Integer.parseInt(songData[2]));
				currentSong.setVidFileName(songData[3]);

				// adding the current song's data to the arraylist songsList
				// to pass this tot he ListAllView to be displayed in the table
				songsList.add(currentSong);  
			    }

			br.close();
		    }
		catch(Exception ex)
		    {
			ex.printStackTrace();
		    }               
	    }
        else
	    {
		System.out.println("there is not playlist file");
	    }      
        
        // return the list of songs in the file
        return songsList;
    }
    
    
    // method to remove a song from the playlist file
    public static String removeFromPlaylist(int _index) throws FileNotFoundException
    {        
	// declaring the playlist text file and the temporary file where the updated playlist will be temporarily stored
        File playlistFile = new File("src/playlist.txt");
        File tempFile = new File("src/tempFile.txt");
        
        String result = "Failed - No song removed";
        
        try
	    {
		// obtaining the song details that resides at the line index # that is like
		// the index # of the "Remove from Playlist" button that was selected
		String songToRemove = Files.readAllLines(Paths.get("src/playlist.txt")).get(_index);
		String[] songArray = songToRemove.split("\t");
           
            
		// declaring the reader and writer
		FileReader fr = new FileReader(playlistFile);
		FileWriter fw = new FileWriter(tempFile, true);
		BufferedReader br = new BufferedReader(fr);
		BufferedWriter bw = new BufferedWriter(fw);
            
		String currentLine;
            
		// setting counter to -1 since the increment will be at the beginning of the loop
		// to avoid skipping an number due to the 'continue' statement
		int counter = -1;           
            
		// while there is a line to read, check if the line index is the same as the button index
		// if yes, do not write this line to the temporary file
		while ((currentLine = br.readLine()) != null) 
		    {
			counter++;
                
			// if the current line index is not equivalent to the index of the button that was selected
			// add this song to the temporary text file
			if (counter == _index) 
			    {
				// if index is reached, skip this iteration (do not write this line to the temporary file)
				continue;                                                                                   
			    }
			// "line.seperator" returns OS dependent line separator
			bw.write(currentLine + System.getProperty("line.separator"));   
		    }
            
		bw.close();
		br.close();
            
		// removing the current "playlist" file
		// and renaming to "playlist" the temporary file created for this action 
		playlistFile.delete();
		tempFile.renameTo(playlistFile);

		result = songArray[0];
	    }
        catch(Exception ex)
	    {
		ex.printStackTrace();
	    }   
        
        return result;
    }
      
    
    // method to add a new song to the library
    public static String addToLibrary(String _title, String _artist, int _seconds, String _vidFileName) throws FileNotFoundException
    {
        // declaring the playlist text file
        File songsFile = new File("src/sample_song_data.txt");
    
        String result = "Failed - No song was added";
        
        try
	    {
		// add a new line in the songs textfile
		// FileWriter allows to append text - true is flag for append mode
		FileWriter fw = new FileWriter(songsFile, true);                                                 
		BufferedWriter bw = new BufferedWriter(fw);            
            
		// adding the new song details - each element is seperated with a tab - moving to the next line
		bw.write(_title + "\t" + _artist + "\t" + _seconds + "\t" + _vidFileName + "\n");   
		bw.close();
            
		result = _title + " has been added to the songs library.";
	    }
        catch(Exception ex)
	    {
		ex.printStackTrace();
	    }   
        
        return result;
    }
    
    
    // method to delete the current playlist file
    public static String deletePlaylist() throws FileNotFoundException
    {        
	// declaring the playlist text file
        File playlistFile = new File("src/playlist.txt");
        
        String result = "Failed - Could not delete playlist.";
        
        try
	    {   
		// removing the current "playlist" file
		playlistFile.delete();
            
		result = "Playlist has now been deleted.";
	    }
        catch(Exception ex)
	    {
		ex.printStackTrace();
	    }   
        
        return result;
    }
    
       
    
    // method to obtain the song titles and respective video file name from the playlist
    public static List<String> getPlaylistDetails()
    {        
        List<String> result = new ArrayList<>();
        
        // declaring the playlist text file
        File playlistFile = new File("src/playlist.txt");

        if (!playlistFile.exists())
	    {
		return result;
	    }
        else	
	    {
		try
		    {   
			// declaring the reader
			FileReader fr = new FileReader(playlistFile);
			BufferedReader br = new BufferedReader(fr);
            
			String currentLine;
            
			// while there is a line to read, check if the line index is the same as the button index
			// if yes, do not write this line to the temporary file
			while ((currentLine = br.readLine()) != null) 
			    {
				// split the line where there is a tab and store each element inside array songData
				String[] songData = currentLine.split("\t");  
                
				// add the title + video file name of the current song to the array to be returned to the view class
				result.add(songData[0]);
				result.add(songData[3]);
                
			    }
			br.close();
		    }
		catch(Exception ex)
		    {
			ex.printStackTrace();
		    }   
	    }
        return result;
    }
    
    
    // method to remove the first song from the playlist file - i.e. the last song that was played
    public static String removeLastSong() throws FileNotFoundException
    {        
	// declaring the playlist text file and the temporary file where the updated playlist will be temporarily stored
        File playlistFile = new File("src/playlist.txt");
        File tempFile = new File("src/tempFile.txt");
        
        String result = "";
        
        try
	    {
		// declaring the reader and writer
		FileReader fr = new FileReader(playlistFile);
		FileWriter fw = new FileWriter(tempFile, true);
		BufferedReader br = new BufferedReader(fr);
		BufferedWriter bw = new BufferedWriter(fw);
            
		String currentLine;
            
		// setting counter to -1 since the increment will be at the beginning of the loop
		// to avoid skipping an number due to the 'continue' statement
		int counter = -1;           
            
		// while there is a line to read, check if the line index is the same as the button index
		// if yes, do not write this line to the temporary file
		while ((currentLine = br.readLine()) != null) 
		    {
			counter++;
                
			// if the current line index is not equivalent to the index of the button that was selected
			// add this song to the temporary text file
			if (counter == 0) 
			    {
				// if index is reached, skip this iteration (do not write this line to the temporary file)
				continue;                                                                                   
			    }
			// "line.seperator" returns OS dependent line separator
			bw.write(currentLine + System.getProperty("line.separator"));   
		    }
            
		bw.close();
		br.close();
            
		// removing the current "playlist" file, and renaming to "playlist" the temporary file created for this action 
		playlistFile.delete();
		tempFile.renameTo(playlistFile);
	    }
        catch(Exception ex)
	    {
		ex.printStackTrace();
		result = ex.getMessage();
	    }   
        return result;
    }
}

