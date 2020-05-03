package karaokeapp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class KaraokeOne extends Application 
{    
    public Stage primaryStage;
    
    // declaring the home view
    public HomeView homeView;
    
    
    @Override
    public void start(Stage primaryStage) 
    {
        // instantiating the home view
        homeView = new HomeView(primaryStage);
        
        // Create a scene and place it in the stage
        Scene homeScene = new Scene(homeView.asParent(), 1200, 800);
        primaryStage.setTitle("Home Page"); // Set the stage title
        primaryStage.setScene(homeScene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    
    public static void main(String[] args) 
    {
        launch(args);
    }
    
}
