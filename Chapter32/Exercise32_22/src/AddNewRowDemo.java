/*
*	Modified By: Christian Harris.
*	Date: 15 October 2020.
*	Description: This application has been modified to include a "Delete Selected Row" button which deletes the current selected row from the table.
*/
import javafx.application.Application;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;

public class AddNewRowDemo extends Application {
  @Override // Override the start method in the Application class
  public void start(Stage primaryStage) {
    TableView<Country> tableView = new TableView<>();
    ObservableList<Country> data =
      FXCollections.observableArrayList(
        new Country("USA", "Washington DC", 280, true),
        new Country("Canada", "Ottawa", 32, true),
        new Country("United Kingdom", "London", 60, true),
        new Country("Germany", "Berlin", 83, true),
        new Country("France", "Paris", 60, true));
    tableView.setItems(data);
    
    TableColumn countryColumn = new TableColumn("Country");
    countryColumn.setMinWidth(100);
    countryColumn.setCellValueFactory(
      new PropertyValueFactory<Country, String>("country"));
    
    TableColumn capitalColumn = new TableColumn("Capital");
    capitalColumn.setMinWidth(100);
    capitalColumn.setCellValueFactory(
      new PropertyValueFactory<Country, String>("capital"));

    TableColumn populationColumn = 
      new TableColumn("Population (million)");
    populationColumn.setMinWidth(100);
    populationColumn.setCellValueFactory(
      new PropertyValueFactory<Country, Double>("population"));

    TableColumn democraticColumn = 
      new TableColumn("Is Democratic?");
    democraticColumn.setMinWidth(100);
    democraticColumn.setCellValueFactory(
      new PropertyValueFactory<Country, Boolean>("democratic"));

    tableView.getColumns().addAll(countryColumn, capitalColumn,
      populationColumn, democraticColumn);

    FlowPane flowPane = new FlowPane(3, 3);
	Button btDeleteSelectedRow = new Button("Delete Selected Row");
    TextField tfCountry = new TextField();
    TextField tfCapital = new TextField();
    TextField tfPopulation = new TextField();
    CheckBox chkDemocratic = new CheckBox("Is democratic?");
    Button btAddRow = new Button("Add new row");
    tfCountry.setPrefColumnCount(5);
    tfCapital.setPrefColumnCount(5);
    tfPopulation.setPrefColumnCount(5);
    flowPane.getChildren().addAll(new Label("Country: "),
      tfCountry, new Label("Capital"), tfCapital, 
      new Label("Population"), tfPopulation, chkDemocratic, 
      btAddRow,btDeleteSelectedRow);
	  
	btDeleteSelectedRow.setOnAction(e -> {
		int selectedRow = tableView.getSelectionModel().getSelectedIndex();
		if(selectedRow > -1){
			data.remove(selectedRow, selectedRow + 1);
		}
	});
    
    btAddRow.setOnAction(e -> {
      data.add(new Country(tfCountry.getText(), tfCapital.getText(), 
        Double.parseDouble(tfPopulation.getText()), 
        chkDemocratic.isSelected()));
      tfCountry.clear();
      tfCapital.clear();
      tfPopulation.clear();
    });
    
    BorderPane pane = new BorderPane();
    pane.setCenter(tableView);
    pane.setBottom(flowPane);
    
    Scene scene = new Scene(pane, 500, 250);  
    primaryStage.setTitle("AddNewRowDemo"); // Set the window title
    primaryStage.setScene(scene); // Place the scene in the window
    primaryStage.show(); // Display the window
  }

  public static class Country {
    private final SimpleStringProperty country;
    private final SimpleStringProperty capital;
    private final SimpleDoubleProperty population;
    private final SimpleBooleanProperty democratic;

    private Country(String country, String capital,
        double population, boolean democratic) {
      this.country = new SimpleStringProperty(country);
      this.capital = new SimpleStringProperty(capital);
      this.population = new SimpleDoubleProperty(population);
      this.democratic = new SimpleBooleanProperty(democratic);
    }

    public String getCountry() {
      return country.get();
    }

    public void setCountry(String country) {
      this.country.set(country);
    }

    public String getCapital() {
      return capital.get();
    }

    public void setCapital(String capital) {
      this.capital.set(capital);
    }

    public double getPopulation() {
      return population.get();
    }

    public void setPopulation(double population) {
      this.population.set(population);
    }

    public boolean isDemocratic() {
      return democratic.get();
    }

    public void setDemocratic(boolean democratic) {
      this.democratic.set(democratic);
    }
  }

  /**
   * The main method is only needed for the IDE with limited
   * JavaFX support. Not needed for running from the command line.
   * line.
   */
  public static void main(String[] args) {
    launch(args);
  }
}