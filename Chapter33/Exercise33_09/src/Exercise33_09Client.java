/*
*	Modified By: Christian Harris.
*	Date: 16 October 2020.
*	Description: This application has been modified to open a socket to connect to a server. Once connected the users on each side can communicate via a text field.
*/

import javafx.application.Application;
import javafx.application.Platform;
import static javafx.application.Application.launch;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.input.*;
import javafx.event.*;

import java.io.*;
import java.net.*;
import java.util.*;

public class Exercise33_09Client extends Application {
  // IO streams
  DataOutputStream toServer = null;
  DataInputStream fromServer = null;
  
  private TextArea taServer = new TextArea();
  private TextArea taClient = new TextArea();
 
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    taServer.setWrapText(true);
	taServer.setEditable(false);
    taClient.setWrapText(true);
    //taServer.setDisable(true);

    BorderPane pane1 = new BorderPane();
    pane1.setTop(new Label("History"));
    pane1.setCenter(new ScrollPane(taServer));
    BorderPane pane2 = new BorderPane();
    pane2.setTop(new Label("New Message"));
    pane2.setCenter(new ScrollPane(taClient));
    
    VBox vBox = new VBox(5);
    vBox.getChildren().addAll(pane1, pane2);

    // Create a scene and place it in the stage
    Scene scene = new Scene(vBox, 200, 200);
    primaryStage.setTitle("Exercise31_09Client"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
	
	taClient.setOnKeyPressed(new EventHandler<KeyEvent>(){
		public void handle(KeyEvent e){
			if(e.getCode() == KeyCode.ENTER){
				try{
					String message = taClient.getText().trim();
					toServer.writeUTF(message);
					toServer.flush();
					taClient.setText("");	
					taServer.appendText("Client: " + message + "\n");
				}
				catch(IOException ex){
					System.err.println(ex);
				}
			}
		}
	});
	
	new Thread( () -> {
      try {
		// Create a socket to connect to the server
		Socket socket = new Socket("localhost", 8000);
		Platform.runLater(() ->
			taServer.appendText("Connected to a server at " + new Date() + "\n"));

		// Create an input stream to receive data from the server
		fromServer = new DataInputStream(socket.getInputStream());

		// Create an output stream to send data to the server
		toServer = new DataOutputStream(socket.getOutputStream());
  
        while (true) {
          String message = fromServer.readUTF();
          Platform.runLater(() -> {
            taServer.appendText("Server: " + message + "\n");
          });
        }
      }
      catch(IOException ex) {
        ex.printStackTrace();
      }
    }).start();
	
	
 }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}
