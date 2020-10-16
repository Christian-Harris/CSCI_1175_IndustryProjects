/*
*	Modified By: Christian Harris.
*	Date: 16 October 2020.
*	Description: This application has been modified to open a socket for connection to a client. Once connected the users on each side can communicate via a text field.
*/

import javafx.application.Application;
import javafx.application.Platform;
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

public class Exercise33_09Server extends Application {
  // IO streams
  DataOutputStream toClient = null;
  DataInputStream fromClient = null;
  
  private TextArea taServer = new TextArea();
  private TextArea taClient = new TextArea();
 
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    taServer.setWrapText(true);
	taServer.setEditable(false);
    taClient.setWrapText(true);
    //taClient.setDisable(true);

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
    primaryStage.setTitle("Exercise31_09Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
	
	taClient.setOnKeyPressed(new EventHandler<KeyEvent>(){
		public void handle(KeyEvent e){
			if(e.getCode() == KeyCode.ENTER){
				try{
					String message = taClient.getText().trim();
					toClient.writeUTF(message);
					toClient.flush();
					taClient.setText("");	
					taServer.appendText("Server: " + message + "\n");
				}
				catch(IOException ex){
					System.err.println(ex);
				}
			}
		}
	});
	
	new Thread( () -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        Platform.runLater(() ->
          taServer.appendText("Server started at " + new Date() + '\n'));
  
        // Listen for a connection request
        Socket socket = serverSocket.accept();
		Platform.runLater(() ->
			taServer.appendText("Connected to a client at " + new Date() + "\n"));
  
        // Create data input and output streams
        fromClient = new DataInputStream(socket.getInputStream());
        toClient = new DataOutputStream(socket.getOutputStream());
  
        while (true) {
          String message = fromClient.readUTF();
          Platform.runLater(() -> {
            taServer.appendText("Client: " + message + "\n");
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
