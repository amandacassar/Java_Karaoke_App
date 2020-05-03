package karaokeapp;

import java.util.*;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.GridPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class PlayVideoView extends Stage
{
    // declaring the stage and pane that will be used - GridPane
    public Stage primaryStage;    
    public GridPane pane = new GridPane();
    
    // declaing the next views
    public HomeView homeView;
    
    // creating components to display
    Button btnPlay = new Button("►");
    Button btnPause = new Button("||");
    Button btnStop = new Button("■");
    Button btnNext = new Button(">>|");
    Button btnHome = new Button("BACK HOME");
    
    // string that will store the current video file name
    String mediaFile;
    
    // declaring the media elements
    Media videoFile;
    MediaPlayer mediaplayer;
    MediaView mediaView;
    
    // list that will store all the playlist's song titles and video file names
    List<String> playlistSongs = new ArrayList<>();
    
    // counter that increrments when a new song is played
    int counter = 1;
    
    // error message component
    static Text msgOne = new Text();
    static Text msgTwo = new Text();
    
    public Parent asParent()
    {
        return pane;
    }       
    
    // MAIN
    public PlayVideoView(Stage stage)
    {
        this.primaryStage = stage;
        
        // setting the text font of each component
        btnHome.setFont(new Font("Tahoma", 25));
        msgOne.setFont(new Font("Tahoma Bold", 20));
        msgOne.setFill(Color.ORANGE);
        msgTwo.setFont(new Font("Tahoma Bold", 20));
        msgTwo.setFill(Color.SLATEBLUE);
        btnPlay.setFont(new Font(35));
        btnPause.setFont(new Font("Tahoma Bold", 30));
        btnStop.setFont(new Font(35));
        btnNext.setFont(new Font("Tahoma Bold", 20));        
        
        // setting the video button's width and height so that they are all the same size
        btnPlay.setPrefWidth(80);
        btnPlay.setPrefHeight(80);
        btnPause.setPrefWidth(80);
        btnPause.setPrefHeight(80);
        btnStop.setPrefWidth(80);
        btnStop.setPrefHeight(80);
        btnNext.setPrefWidth(80);
        btnNext.setPrefHeight(80);
                
        // obtaining the list of video files names and song titles
        // song titles will be stored in even indices, while video files will be stored in odd indices
        playlistSongs = Controller.getPlaylistDetails();
        
        // displaying the song titles of the current song and of the next song
        msgOne.setText("Now Playing: " + playlistSongs.get(0));
        // checking if there is a second song, and display the corresponding message at the bottom
        // note - checking if size > 2 since when a song is added to the playlist file
	// a new empty line is also created after the newly added song
        if (playlistSongs.size() > 2)
	    {
		msgTwo.setText("Next: " + playlistSongs.get(2));
	    }
        else
	    {
		msgTwo.setText("No more songs in this playlist.");
	    }        

        mediaFile = playlistSongs.get(counter);
        
        // declaring the video file and from where to obtain the file - it has to be a url path
        videoFile = new Media("file:///~/Desktop/karaoke/src/" + mediaFile);
        
        // assigning the video file to the media player and setting the volume
        mediaplayer = new MediaPlayer(videoFile);
        mediaplayer.setVolume(0.5);
        
        // displaying the mediaplayer
        mediaView = new MediaView(mediaplayer);                
        
        // setting the size of the media player, to fit in the full view width
	// and adjust height to the maximum possible height
        mediaView.fitWidthProperty().bind(pane.widthProperty());
        mediaView.fitHeightProperty().bind(pane.heightProperty());
        
        // defining components' position in the grid pane
        GridPane.setConstraints(mediaView, 0, 0, 5, 1);
        GridPane.setConstraints(btnPlay, 0, 1);
        GridPane.setConstraints(btnPause, 1, 1);
        GridPane.setConstraints(btnStop, 2, 1);
        GridPane.setConstraints(btnNext, 3, 1);
        GridPane.setConstraints(btnHome, 6, 1);
        GridPane.setConstraints(msgOne, 0, 2, 6, 1);       
        GridPane.setConstraints(msgTwo, 0, 3, 6, 1);         
        
        // action upon clicking the play button - using Lambda Expression
        btnPlay.setOnAction(event ->
			    {
				// checking if mediaplayer was paused
				Status status = mediaplayer.getStatus();
				if (status == Status.PAUSED)
				    {
					mediaplayer.play();
				    }
            
				// if player was not paused - start the video from the beginning
				else
				    {
					// starting the stopwatch for timing records
					Stopwatch timer = new Stopwatch();
					
					// if the current song was not the first song in the playlist
					// obtain the video file name of the current song
					if (counter > 1)
					    {
						// video file names are stored in the odd indices of the playlistSongs list
						mediaFile = playlistSongs.get(counter + (counter-1));                
					    }

					// declaring the video file and from where to obtain the file - it has to be a url path
					videoFile = new Media("file:///~/Desktop/karaoke/src/" + mediaFile);
					// assigning the currentvideo file to the media player 
					mediaplayer = new MediaPlayer(videoFile);
					// displaying the mediaplayer
					mediaView.setMediaPlayer(mediaplayer);
					mediaplayer.play();

					// obtiaining the time elapsed and displaying it
					double time = timer.elapsedTime();        
					System.out.println("Play Song in Playlist - time taken in seconds = " + time);
				    }            
			    });     
        
        // action upon clicking the pause button - using Lambda Expression
        btnPause.setOnAction(event ->
			     {
				 mediaplayer.pause();
			     });            
            
        // action upon clicking the stop button - using Lambda Expression
        btnStop.setOnAction(event ->
			    {
				mediaplayer.stop();
			    });    
        
        // action upon clicking the next song button - using Lambda Expression
        btnNext.setOnAction(event ->
			    {
				// check if there is a next song
				if (counter < (playlistSongs.size() /2))
				    {
					mediaplayer.stop();                
					counter++;
                
					// updating the current + next songs displayed at the bottom
					msgOne.setText("Now Playing: " + playlistSongs.get(counter*2 -2));
					// checking if there is a next song after the new current song
					if (counter == (playlistSongs.size() /2))
					    {
						msgTwo.setText("No more songs in this playlist.");
					    }
					else
					    {
						msgTwo.setText("Next: " + playlistSongs.get(counter*2));
					    }
                
				    }
				// if there is not a next song, display message
				else
				    {
					msgTwo.setText("NO MORE SONGS IN THIS PLAYLIST!");
				    }
            
				try
				    {
					// removing the previous song from the playlist file
					String result = Controller.removeLastSong();
                
					if (!result.equals(""))
					    {
						msgOne.setText(result);
					    }
				    }
				catch(Exception ex)
				    {
					ex.printStackTrace();
				    }
			    });        
        
        // action upon clicking the Back button - using Lambda Expression
        btnHome.setOnAction(event ->
			    {
				// stopping the video in case the user did not stop it
				mediaplayer.stop();
            
				try
				    {
					// instantiating the next view and scene
					homeView = new HomeView(primaryStage);
					Scene homeScene = new Scene(homeView.asParent(), 1200, 800);
        
					// loading the next page to display results
					primaryStage.setScene(homeScene);
					primaryStage.show();             
				    }
				catch(Exception ex)
				    {
					ex.printStackTrace();
					msgOne.setText("Some error occurred or could not load new page.");
				    }               
			    });
                
        // setting the alignment of the display - starting top left
        pane.setAlignment(Pos.TOP_LEFT);

        // setting the padding and the vertical + horizontal gaps between components that will be added on the grid pane
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(20);
        pane.setHgap(15);

        // adding all the components in the grid - including the column , row where to add the component
        pane.getChildren().addAll(mediaView, btnPlay, btnPause, btnStop, btnNext, btnHome, msgOne, msgTwo);
    }
}
