/*
*	Author: Christian Harris.
*	Date: 15 October 2020.
*	Description: This application implements a simple investment calculator to compute the future value of an initial investemnt given the time and interest rate.
*/
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import javafx.geometry.*;
import javafx.scene.paint.*;
import javafx.event.*;

import java.util.function.UnaryOperator;

public class Exercise31_17 extends Application{
	@Override
	public void start(Stage primaryStage){
		int LabelWidth = 128;
		int LabelHeight = 24;
		GridPane pane = new GridPane();
		
		Label amountLabel = new Label("Investment Amount:");
		
		TextField amountField = new TextField();
		
		Label amountLabelError = new Label("");
		amountLabelError.setStyle("-fx-text-fill: red");
		
		Label yearsLabel = new Label("Number of Years:");
		
		TextField yearsField = new TextField();
		
		Label yearsLabelError = new Label("");
		yearsLabelError.setStyle("-fx-text-fill: red");
		
		Label rateLabel = new Label("Annual Interest Rate:");
		
		TextField rateField = new TextField();
		
		Label rateLabelError = new Label("");
		rateLabelError.setStyle("-fx-text-fill: red");
		
		Label valueLabel = new Label("Future Value:");
		
		TextField valueField = new TextField();
		
		Button calculateButton = new Button("Calculate");
		calculateButton.setOnAction(new EventHandler<ActionEvent>(){
				public void handle(ActionEvent e){
					amountLabelError.setText("");
					yearsLabelError.setText("");
					rateLabelError.setText("");
					double amount = 0;
					int years = 0;
					double rate = 0;
					
					try{
						amount = Double.parseDouble(amountField.getText());
					}
					catch(NullPointerException ex){
						amountLabelError.setText("No value entered.");
					}
					catch(NumberFormatException ex){
						amountLabelError.setText("Not a number.");
					}
					
					try{
						years = Integer.parseInt(yearsField.getText());
					}
					catch(NullPointerException ex){
						yearsLabelError.setText("No value entered.");
					}
					catch(NumberFormatException ex){
						yearsLabelError.setText("Not an integer.");
					}
					
					try{
						rate = Double.parseDouble(rateField.getText());
					}
					catch(NullPointerException ex){
						rateLabelError.setText("No value entered.");
					}
					catch(NumberFormatException ex){
						rateLabelError.setText("Not a number.");
					}
					
					double value = amount * Math.pow(1 + (rate / 1200), years * 12);
					String strValue = String.format("%.2f", value);
					valueField.setText("$" + strValue);
				}
			});
			
		MenuBar menuBar = new MenuBar();
		Menu operation = new Menu("Operation");
		MenuItem close = new MenuItem("Close");
		close.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				primaryStage.close();
			}
		});
		MenuItem calculateItem = new MenuItem("Calculate");
		calculateItem.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				amountLabelError.setText("");
				yearsLabelError.setText("");
				rateLabelError.setText("");
				double amount = 0;
				int years = 0;
				double rate = 0;
				
				try{
					amount = Double.parseDouble(amountField.getText());
				}
				catch(NullPointerException ex){
					amountLabelError.setText("No value entered.");
				}
				catch(NumberFormatException ex){
					amountLabelError.setText("Not a number.");
				}
				
				try{
					years = Integer.parseInt(yearsField.getText());
				}
				catch(NullPointerException ex){
					yearsLabelError.setText("No value entered.");
				}
				catch(NumberFormatException ex){
					yearsLabelError.setText("Not an integer.");
				}
				
				try{
					rate = Double.parseDouble(rateField.getText());
				}
				catch(NullPointerException ex){
					rateLabelError.setText("No value entered.");
				}
				catch(NumberFormatException ex){
					rateLabelError.setText("Not a number.");
				}
				
				double value = amount * Math.pow(1 + (rate / 1200), years * 12);
				String strValue = String.format("%.2f", value);
				valueField.setText("$" + strValue);	
			}
		});
		operation.getItems().addAll(close, calculateItem);
		menuBar.getMenus().addAll(operation);
		
		pane.add(menuBar, 0, 0, 2, 1);
		pane.add(amountLabel, 0, 1, 1, 1);
		pane.add(amountField, 1, 1, 1, 1);
		pane.add(amountLabelError, 2, 1, 1, 1);
		pane.add(yearsLabel, 0, 2, 1, 1);
		pane.add(yearsField, 1, 2, 1, 1);
		pane.add(yearsLabelError, 2, 2, 1, 1);
		pane.add(rateLabel, 0, 3, 1, 1);
		pane.add(rateField, 1, 3, 1, 1);
		pane.add(rateLabelError, 2, 3, 1, 1);
		pane.add(valueLabel, 0, 4, 1, 1);
		pane.add(valueField, 1, 4, 1, 1);
		pane.add(calculateButton, 0, 5, 2, 1);
		
		Scene scene = new Scene(pane, 800, 800);
		
		primaryStage.setTitle("Exercise31_17");
		primaryStage.setMaxWidth(400);
		primaryStage.setMaxHeight(200);
		primaryStage.setMinWidth(400);
		primaryStage.setMinHeight(200);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}