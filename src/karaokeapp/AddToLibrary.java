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

import javafx.scene.paint.Color;

import javafx.stage.Stage;



public class AddToLibrary extends Stage

{

    // declaring the stage and pane that will be used - GridPane

    public Stage primaryStage;    

    public GridPane pane = new GridPane();

    

    // declaing the next views

    public HomeView homeView;

    

    // creating components to display

    Label title = new Label("ADD NEW SONG TO LIBRARY");

    Label lblSongTitle = new Label("Song Title:");

    Label lblArtist = new Label("Artist:");

    Label lblLength = new Label("Length (sec):");

    Label lblFileName = new Label("Video File Name:");

    TextField txtSongTitle = new TextField();

    TextField txtArtist = new TextField();

    TextField txtLength = new TextField();

    TextField txtFileName = new TextField();

    Button btnAddSong = new Button("ADD SONG");

    Button btnBack = new Button("BACK");

    

    // error message component

    static Text msg = new Text();

    

    public Parent asParent()

    {

        return pane;

    }   

    

    // MAIN

    public AddToLibrary(Stage stage)

    {

        this.primaryStage = stage;

        

        // setting the text font of each component

        title.setFont(new Font("Tahoma Bold", 40));

        lblSongTitle.setFont(new Font("Tahoma", 25));

        lblArtist.setFont(new Font("Tahoma", 25));

        lblLength.setFont(new Font("Tahoma", 25));

        lblFileName.setFont(new Font("Tahoma", 25));

        btnAddSong.setFont(new Font("Tahoma Bold", 25));

        btnBack.setFont(new Font("Tahoma", 25));

        msg.setFont(new Font("Tahoma Bold", 20));

        msg.setFill(Color.ORANGE);

        

        // setting textfields size and prompt text

        txtSongTitle.setPrefSize(600, 30);

        txtSongTitle.setPromptText("enter song title...");

        txtArtist.setPrefSize(600, 30);

        txtArtist.setPromptText("enter artist name...");

        txtLength.setPrefSize(600, 30);

        txtLength.setPromptText("enter length of song in seconds...");

        txtFileName.setPrefSize(600, 30);

        txtFileName.setPromptText("enter name of video file (ex: videoexample.mp4) ...");

        

        // defining components' position in the grid pane

        GridPane.setConstraints(title, 0, 0, 3, 1);

        GridPane.setConstraints(lblSongTitle, 0, 1);

        GridPane.setConstraints(txtSongTitle, 1, 1);

        GridPane.setConstraints(lblArtist, 0, 2);

        GridPane.setConstraints(txtArtist, 1, 2);

        GridPane.setConstraints(lblLength, 0, 3);

        GridPane.setConstraints(txtLength, 1, 3);

        GridPane.setConstraints(lblFileName, 0, 4);

        GridPane.setConstraints(txtFileName, 1, 4);

        GridPane.setConstraints(btnAddSong, 0, 5);

        GridPane.setConstraints(btnBack, 0, 6);

        GridPane.setConstraints(msg, 0, 7, 4, 1);

        

        // action upon clicking the Add Song button - using Lambda Expression

        btnAddSong.setOnAction(event ->

			       {

				   // clear any previous error message

				   msg.setText("");

            

				   // obtaining the song details entered by the user

				   String title = txtSongTitle.getText();

				   String artist = txtArtist.getText();

				   String length = txtLength.getText();

				   String vidFileName = txtFileName.getText();

            

				   // if any of the textboxes was left empty, throw an error message

				   if (title.isEmpty() || artist.isEmpty() || length.isEmpty() || vidFileName.isEmpty())

				       {

					   msg.setText("All fields are required - enter all details.");

				       }



				   // checking that only numeric characters are entered for the song's length (seconds)

				   else if (!length.matches("^[0-9]*$"))

				       {

					   msg.setText("Please enter a valid numeric value for the song's length");

				       }

            

				   // checking that the video file name is of the correct format - ending in ".mp4"

				   else if (vidFileName.indexOf(".mp4") != (vidFileName.length() - 4))

				       {

					   msg.setText("The video file format should be .mp4");

				       }

     

				   // if all textboxes are filled

				   else

				       {

					   try

					       {

						   // converting the length to integer (seconds)

						   int seconds = Integer.parseInt(length);



						   // starting the stopwatch for timing records

						   Stopwatch timer = new Stopwatch();

			

						   // passing parameters to the function in the Controller to add a new song in the library

						   // and obtaining the result

						   String result = Controller.addToLibrary(title, artist, seconds, vidFileName);



						   // obtiaining the time elapsed and displaying it

						   double time = timer.elapsedTime();        

						   System.out.println("Adding Song To Library - time taken in seconds = " + time);

                    

						   msg.setText(result);

					       }

					   catch(Exception ex)

					       {

						   ex.printStackTrace();

						   msg.setText("Some error occurred");

					       }               

				       }            

			       });        

        

        // action upon clicking the Back button - using Lambda Expression

        btnBack.setOnAction(event ->

			    {            

				// clear any previous error message

				msg.setText("");

                

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

        pane.getChildren().addAll(title, lblSongTitle, txtSongTitle, lblArtist, txtArtist, lblLength, txtLength, lblFileName, txtFileName, btnAddSong, btnBack, msg);

    }

}