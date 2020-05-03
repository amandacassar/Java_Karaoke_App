package karaokeapp;

public class Stopwatch 
{
    // variable to store the start time
    private final long start;
    
    public Stopwatch()
    { 
        // obtain current time in milliseconds - the starting time
        start = System.currentTimeMillis(); 
    }
    
    public double elapsedTime()
    {
        // obtaining the current time in miliseconds - the finishing time
        long now = System.currentTimeMillis();
        
        // calculating the time elapsed in seconds, and returning this value
        return (now - start) / 1000.0;
    }
}


