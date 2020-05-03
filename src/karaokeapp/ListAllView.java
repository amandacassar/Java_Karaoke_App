package karaokeapp;

import java.util.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.GridPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ListAllView extends Stage
{
    // declaring the stage and pane that will be used - GridPane
    public Stage primaryStage;    
    public GridPane pane = new GridPane();
    
    // declaing the next views
    public HomeView homeView;
    
    // creating components to display
    Label title = new Label("ALL SONGS");
    public static TableView<Song> table = new TableView<>();  
    Button btnBack = new Button("BACK");
    
    // error message component
    static Text msg = new Text();
    
    public Parent asParent()
    {
        return pane;
    }   
    
    // MAIN
    public ListAllView(Stage stage)
    {
        this.primaryStage = stage;
        
        // setting the table's columns titles
        TableColumn<Song, String> titleCol = new TableColumn<>("title");
        TableColumn<Song, String> artistCol = new TableColumn<>("Artist");
        TableColumn<Song, Integer> secondsCol = new TableColumn<>("Length (sec)");
        TableColumn<Song, String> videoCol = new TableColumn<>("Video File Name");
        TableColumn <Song, Button> addCol = new TableColumn<>("");
        
        // defining the table's columns minimum width
        titleCol.setMinWidth(400);
        artistCol.setMinWidth(300);
        secondsCol.setMinWidth(100);
        videoCol.setMinWidth(150);
        addCol.setMinWidth(200);
        
        // declaring the variable to display in each field of the table
        titleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
        artistCol.setCellValueFactory(new PropertyValueFactory<>("artist"));
        secondsCol.setCellValueFactory(new PropertyValueFactory<>("seconds"));
        videoCol.setCellValueFactory(new PropertyValueFactory<>("vidFileName"));
        addCol.setCellValueFactory(new PropertyValueFactory<>("button"));
        
        // setting the text font of each component
        title.setFont(new Font("Tahoma Bold", 40));
        btnBack.setFont(new Font("Tahoma", 25));
        msg.setFont(new Font("Tahoma Bold", 20));
        msg.setFill(Color.SLATEBLUE);
        
        // defining components' position in the grid pane
        GridPane.setConstraints(title, 0, 0, 3, 1);
        GridPane.setConstraints(table,0,1, 10, 1);
        GridPane.setConstraints(btnBack, 0, 2);
        GridPane.setConstraints(msg, 0, 4, 4, 1);   
        
        // adding the columns of the table to display them
        table.getColumns().addAll(titleCol, artistCol, secondsCol, videoCol, addCol);        
        
        // action upon clicking the Back button - using Lambda Expression
        btnBack.setOnAction(event ->
			    {
				try
				    {
					// clear the contents of the table - so that they won't display in the next run
					table.getColumns().clear();
                
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
					msg.setText("Some error occurred or could not load new page.");
				    }               
			    });
        
        // setting the alignment of the display - starting top left
        pane.setAlignment(Pos.TOP_LEFT);

        // setting the padding and the vertical + horizontal gaps between components that will be added on the grid pane
        pane.setPadding(new Insets(10, 10, 10, 10));
        pane.setVgap(30);
        pane.setHgap(15);

        // adding all the components in the grid - including the column , row where to add the component
        pane.getChildren().addAll(title, table, btnBack, msg);
    }    
    
    // method to obtain the list of songs and display them in the table
    // also setting the action upon clicking a button "Add to Playlist" for any song selected from the table
    public static void setSongsTable(BST<String, Song> bst)
    {
	// clearing any previous messages
	msg.setText("");

        ObservableList<Song> allSongs = FXCollections.observableArrayList();

	List<Song> songs = new ArrayList<>();
	
	// getting each song from the BST and putting it in the array list, to facilitate adding items to table
	for (String x : bst.keys())
	    songs.add(new Song(x, bst.get(x).getArtist(), bst.get(x).getSeconds(), bst.get(x).getVidFileName()));
        
        // for each song, add this to the ObservableList "allSongs"
        for (int i = 0; i < songs.size(); i++)
	    {
		String songTitle = songs.get(i).getTitle();
		String artist = songs.get(i).getArtist();
		int seconds = songs.get(i).getSeconds();
		String videoFile = songs.get(i).getVidFileName();
		Button btnAddSong = new Button("Add to Playlist");
            
		// add the current song data in the Observable List
		allSongs.add(new Song(songTitle, artist, seconds, videoFile, btnAddSong));            
            
		// action upon clicking the button - using Lambda Expression
		// this will obtain the song details
		btnAddSong.setOnAction(event ->
				       {
					   try
					       {
						   // starting stopwatch - for timing records
						   Stopwatch timer = new Stopwatch();
						   
						   String result = Controller.addToPlaylist(songTitle, artist, seconds, videoFile);

						   // obtaining and outputting the time taken in seconds
						   double time = timer.elapsedTime();        
						   System.out.println("Add Song to Playlist - time taken in seconds = " + time);
						   
						   msg.setText(result);
					       }
					   catch(Exception ex)
					       {
						   ex.printStackTrace();
						   msg.setText("Some error occurred or could not load new page.");
					       }               
				       });            
	    }       
        
        // add the list of bookings into the table to be displayed
        table.setItems(allSongs);
    }
}
