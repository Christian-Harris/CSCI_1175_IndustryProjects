/*
*	Modified By: Christian Harris.
*	Date: 16 October 2020.
*	Description: The server can communicate with multiple clients concurrently using the multiple threads. The server receives loan information from a client uses a Loan
*	object to compute the monthly payment and total payment and sends this information back to the client.
*/

import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.net.*;
import java.io.*;

public class Exercise33_01Server extends Application {
  // Text area for displaying contents
  private TextArea ta = new TextArea();

  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    ta.setWrapText(true);
   
    // Create a scene and place it in the stage
    Scene scene = new Scene(new ScrollPane(ta), 400, 200);
    primaryStage.setTitle("Exercise31_01Server"); // Set the stage title
    primaryStage.setScene(scene); // Place the scene in the stage
    primaryStage.show(); // Display the stage
	
	new Thread( () -> {
      try {
        // Create a server socket
        ServerSocket serverSocket = new ServerSocket(8000);
        Platform.runLater(() ->
          ta.appendText("Server started at " + new Date() + '\n'));
  
        // Listen for a connection request
        Socket socket = serverSocket.accept();
		Platform.runLater(() ->
			ta.appendText("Connected to a client at " + new Date() + "\n"));
  
        // Create data input and output streams
        DataInputStream inputFromClient = new DataInputStream(
          socket.getInputStream());
        DataOutputStream outputToClient = new DataOutputStream(
          socket.getOutputStream());
  
        while (true) {
          // Receive radius from the client
          double rate = inputFromClient.readDouble();
		  int years = inputFromClient.readInt();
		  double amount = inputFromClient.readDouble();
  
          //Create Loan
		  Loan loan = new Loan(rate, years, amount);
		  double monthlyPayment = loan.getMonthlyPayment();
		  double totalPayment = loan.getTotalPayment();
          // Send monthly and total payments back to client.
          outputToClient.writeDouble(monthlyPayment);
		  outputToClient.writeDouble(totalPayment);
  
          Platform.runLater(() -> {
            ta.appendText("Annual Interest Rate: " + rate + "\nNumber of Years: " + years + "\nLoan Amount: " + amount + "\nMonthly Payment:" + monthlyPayment + "\nTotal Payment: " + totalPayment);
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
