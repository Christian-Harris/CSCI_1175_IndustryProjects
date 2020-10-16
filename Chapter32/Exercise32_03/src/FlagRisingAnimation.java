/*
*	Modified By: Christian Harris.
*	Date: 16 October 2020.
*	Description: This appliction has been modified to implement the Runnable interface for running on a thread. The start method handles the setup of the application
*	while the run method begins the animation.
*/

import javafx.animation.PathTransition; 
import javafx.application.Application; 
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class FlagRisingAnimation extends Application implements Runnable {
	protected ImageView imageView;
	protected PathTransition pt;
	
	@Override // Override the start method in the Application class
	public void start(Stage primaryStage) {
		// Create a pane
		Pane pane = new Pane();
	
		// Add an image view and add it to pane
		imageView = new ImageView("image/us.gif");
		pane.getChildren().add(imageView);

		// Create a path transition
		pt = new PathTransition(Duration.millis(10000), new Line(100, 200, 100, 0), imageView); pt.setCycleCount(5);
		
		// Create a scene and place it in the stage
		Scene scene = new Scene(pane, 250, 200); 
		primaryStage.setTitle("FlagRisingAnimation"); // Set the stage title
		primaryStage.setScene(scene); // Place the scene in the stage
		primaryStage.show(); // Display the stage
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void run(){
		pt.play(); // Start animation
	}
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}