package karaokeapp;

import javafx.scene.control.Button;

public class Song
{
    public String title;
    public String artist;
    public int seconds;
    public String vidFileName;
    public Button button;
    
    // constructor
    public Song()
    {
        this.title = "no title";
        this.artist = "no artist";
        this.seconds = 0;
        this.vidFileName = "no file name";
        this.button = new Button();
    }
    
    // constructor overload
    public Song(String _title, String _artist, int _seconds, String _vidFileName)
    {
        this.setTitle(_title);
        this.setArtist(_artist);
        this.setSeconds(_seconds);
        this.setVidFileName(_vidFileName);
    }

    // constructor overload with button
    public Song(String _title, String _artist, int _seconds, String _vidFileName, Button _button)
    {
        this.setTitle(_title);
        this.setArtist(_artist);
        this.setSeconds(_seconds);
        this.setVidFileName(_vidFileName);
	this.setButton(_button);
    }
    
    
    // setters
    public void setTitle(String _title)
    {
        this.title = _title;
    }
    
    public void setArtist(String _artist)
    {
        this.artist = _artist;
    }
    
    public void setSeconds(int _seconds)
    {
        this.seconds = _seconds;
    }
    
    public void setVidFileName(String _vidFileName)
    {
        this.vidFileName = _vidFileName;
    }
    
    public void setButton(Button _button)
    {
        this.button = _button;
    }
    
    
    // getters
    public String getTitle()
    {
        return this.title;
    }
    
    public String getArtist()
    {
        return this.artist;
    }
    
    public int getSeconds()
    {
        return this.seconds;
    }
    
    public String getVidFileName()
    {
        return this.vidFileName;
    }
    
    public Button getButton()
    {
        return this.button;
    }
    

    // method to compare the title of two songs
    public static int compareSongs(Song s1, Song s2)
    {
	String title1 = s1.getTitle();
	String title2 = s2.getTitle();

	int cmp = title1.compareTo(title2);

	return cmp;
    }
}

