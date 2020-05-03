package karaokeapp;

import java.util.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.text.*;
import javafx.scene.layout.GridPane;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HomeView extends Stage
{
    // declaring the stage and pane that will be used - GridPane
    public Stage primaryStage;    
    public GridPane pane = new GridPane();
    
    // declaing the next views
    public SearchResultsView searchResultsView;
    public PlaylistView playlistView;
    public AddToLibrary addToLibraryView;
    public ListAllView listAllView;
    
    // creating components to display
    Label title = new Label("WELCOME TO KARAOKE ONE  ♫ ♪ ♫ ♪");
    Label lblSearch = new Label("Search for a Song Title:");
    TextField txtSearch = new TextField();
    Button btnSearch = new Button("SEARCH");
    Text txtOr = new Text("   OR   ");
    Button btnListAll = new Button("LIST ALL SONGS");
    Button btnViewPlayList = new Button("VIEW CURRENT PLAY-LIST");
    Button btnAddToLibrary = new Button("ADD NEW SONG TO LIBRARY");
    
    // error message component
    static Text msg = new Text();
    
    public Parent asParent()
    {
        return pane;
    }   
    
    // MAIN
    public HomeView(Stage stage)
    {
        this.primaryStage = stage;
        
        // setting the text font of each component
        title.setFont(new Font("Tahoma Bold", 50));
        lblSearch.setFont(new Font("Tahoma", 25));
        btnSearch.setFont(new Font("Tahoma", 20));
        txtOr.setFont(new Font("Tahoma Bold", 20));
        btnListAll.setFont(new Font("Tahoma", 20));
        btnViewPlayList.setFont(new Font("Tahoma Bold", 25));
        btnAddToLibrary.setFont(new Font("Tahoma Bold", 25));
        msg.setFont(new Font("Tahoma Bold", 20));
        msg.setFill(Color.ORANGE);
        
        // setting textfield size and prompt text
        txtSearch.setPrefSize(400, 30);
        txtSearch.setPromptText("enter song title...");
        
        // defining components' position in the grid pane
        GridPane.setConstraints(title, 0, 0, 6, 1);
        GridPane.setConstraints(lblSearch, 0, 1);
        GridPane.setConstraints(txtSearch, 1, 1);
        GridPane.setConstraints(btnSearch, 2, 1);
        GridPane.setConstraints(txtOr, 3, 1);
        GridPane.setConstraints(btnListAll, 4, 1);
        GridPane.setConstraints(btnViewPlayList, 0, 2, 2, 1);
        GridPane.setConstraints(btnAddToLibrary, 0, 3, 2, 1);
        GridPane.setConstraints(msg, 0, 4, 4, 1);
        
        
        // action upon clicking the Search button - using Lambda Expression
        btnSearch.setOnAction(event ->
			      {
				  // clear any previous error message
				  msg.setText("");
            
				  // obtaining the search criteria entered by the user
				  String criteria = txtSearch.getText();
            
				  // if the user did not enter something in the search textbox, throw an error message
				  if (criteria.isEmpty())
				      {
					  msg.setText("Please enter your search criteria in the text box.");
				      }
				  // if the user entered some text in the search textbox
				  else
				      {
					  try
					      {
						  // changing all letters to lower case and removing any leading/trailing spaces
						  criteria  = criteria.toLowerCase().trim();
						  						  
						  // declaring BST that will store the result obtained from the Controller
						  BST<String, Song> bst = new BST<String, Song>();

						  // starting the stopwatch
						  Stopwatch timer = new Stopwatch();
			
						  // obtaining the list of matching songs
						  bst = Controller.searchSongs(criteria);

						  // obtiaining the time elapsed and displaying it - also displaying input size
						  double time = timer.elapsedTime();        
						  System.out.println("Song Search by Title - time taken in seconds = " + time);
						  
						  // passing the result of matching songs to the method inside the next view
						  SearchResultsView.setTable(bst);

						  // instantiating the next view and scene
						  searchResultsView = new SearchResultsView(primaryStage);
						  Scene searchResultsScene = new Scene(searchResultsView.asParent(), 1200, 800);

						  // loading the next page to display results
						  primaryStage.setScene(searchResultsScene);
						  primaryStage.show();   
						  
					      }
					  catch(Exception ex)
					      {
						  ex.printStackTrace();
						  msg.setText("Some error occurred or could not load new page.");
					      }               
				      }           
			      });
                
        // action upon clicking the List All Songs button - using Lambda Expression
        btnListAll.setOnAction(event ->
			       {
				   // clear any previous error message
				   msg.setText("");
            
				   try
				       {   
					   // declaring BST that will store the result obtained from the Controller
					   BST<String, Song> bst = new BST<String, Song>();

					   // starting the stopwatch
					   Stopwatch timer = new Stopwatch();
					   
					   // obtaining the list of songs from the text file
					   bst = Controller.listAllSongs();					   

					   // obtiaining the time elapsed and displaying it
					   double time = timer.elapsedTime();
					   System.out.println("List All Songs Sorted - time taken in seconds = " + time);
                   
					   // passing the allSongs list to the method inside the next view
					   ListAllView.setSongsTable(bst);
                
					   // instantiating the next view and scene
					   listAllView = new ListAllView(primaryStage);
					   Scene listAllScene = new Scene(listAllView.asParent(), 1200, 800);
        
					   // loading the next page to display results
					   primaryStage.setScene(listAllScene);
					   primaryStage.show();    
				       }
				   catch(Exception ex)
				       {
					   ex.printStackTrace();
					   msg.setText("Some error occurred or could not load new page.");
				       }               
			       });
           
	// action upon clicking the View Current Play-list button - using Lambda Expression
        btnViewPlayList.setOnAction(event ->
				    {
					// clear any previous error message
					msg.setText("");
            
					try
					    {                
						// declaring list of type Song, to pass to the PlaylistView to be displayed in the table
						List<Song> allSongs = new ArrayList<>();
                
						// obtaining the list of songs from playlist
						allSongs = Controller.listPlaylistSongs();
                   
						// checking if there is a playlist file
						if (allSongs.isEmpty())
						    {
							msg.setText("There is no playlist yet.");
						    }
						else
						    {
							// passing the allSongs list to the method inside the next view
							PlaylistView.setTable(allSongs);

							// instantiating the next views and scenes
							playlistView = new PlaylistView(primaryStage);
							Scene playlistScene = new Scene(playlistView.asParent(), 1200, 800);

							// loading the next page to display results
							primaryStage.setScene(playlistScene);
							primaryStage.show();  
						    }                       
					    }
					catch(Exception ex)
					    {
						ex.printStackTrace();
						msg.setText("Some error occurred or could not load new page.");
					    }               
				    });                
        
        // action upon clicking the View Current Play-list button - using Lambda Expression
        btnAddToLibrary.setOnAction(event ->
				    {
					// clear any previous error message
					msg.setText("");
            
					try
					    {
						// instantiating the next views and scenes
						addToLibraryView = new AddToLibrary(primaryStage);
						Scene addToLibraryScene = new Scene(addToLibraryView.asParent(), 1200, 800);
        
						// loading the next page to display results
						primaryStage.setScene(addToLibraryScene);
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
        pane.setVgap(80);
        pane.setHgap(15);

        // adding all the components in the grid - including the column , row where to add the component
        pane.getChildren().addAll(title, lblSearch, txtSearch, btnSearch, txtOr, btnListAll, btnViewPlayList, btnAddToLibrary, msg);
    }
}
