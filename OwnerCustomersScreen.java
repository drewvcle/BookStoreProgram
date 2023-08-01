package BSA;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.event.EventHandler;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;

/**
 *
 * @author Group 8, Section 2
 */
public class OwnerCustomersScreen extends Application {
    static private TableView<Account> table = new TableView<Account>();
    static public final ObservableList<Account> data = FXCollections.observableArrayList();
    
    //Buttons for Bottom Section
    Button btDelete = new Button("Delete");
    Button btBack = new Button("Back");
    Label warning = new Label("Enter a username and password.");
    
    
    // MODIFIES: table, data
    // EFFECTS: Constructor inputs changes to instance variables
    public OwnerCustomersScreen(){
        table = new TableView<Account>();
        data.clear();
        
        for(int i = 0; i < GrabData.aUserArray.size(); i++){
            data.add(new Account(
                GrabData.aUserArray.get(i),
                GrabData.aPassArray.get(i),
                GrabData.aPointsArray.get(i)));
        } 
    }
    // EFFECTS: Stage and various interactions are created within this method. Below, more detail is provided
    // for each section of the code.
    @Override
    public void start(Stage stage) {
        //Various elements are created to be set on stage.
        stage.setTitle("Bookstore App");
        
        /////////////////////////////Top Section: Table/////////////////////////////
        Label title = new Label("Customers Manager");
        title.setFont(new Font("Arial", 21));
        
        table.setEditable(true);
        
        TableColumn accUserCol = new TableColumn("Username");
        accUserCol.setMinWidth(150);
        accUserCol.setCellValueFactory(
            new PropertyValueFactory<Account, String>("accUser"));
        accUserCol.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<Account, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Account, String> t) {
                    ((Account) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setAccUser(t.getNewValue());
                }
            }
        );
        
        TableColumn accPassCol = new TableColumn("Password");
        accPassCol.setMinWidth(150);
        accPassCol.setCellValueFactory(
            new PropertyValueFactory<Account, String>("accPass"));
        accPassCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Account, String>>() {
                @Override
                public void handle(CellEditEvent<Account, String> t) {
                    ((Account) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setAccPass(t.getNewValue());
                }
            }      
        );
        
        TableColumn accPointsCol = new TableColumn("Points");
        accPointsCol.setMinWidth(100);
        accPointsCol.setCellValueFactory(
            new PropertyValueFactory<Account, String>("accPoints"));
        accPointsCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Account, String>>() {
                @Override
                public void handle(CellEditEvent<Account, String> t) {
                    ((Account) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setAccPoints(t.getNewValue());
                }
            }      
        );
        
        table.setItems(data);
        table.getColumns().addAll(accUserCol, accPassCol, accPointsCol);
        
        ///////////////////////////////Middle Section: add User and Pass/////////////////////////////
        TextField aUserField = new TextField();
        aUserField.setPromptText("Username");
        aUserField.setMaxWidth(accUserCol.getPrefWidth());
        TextField aPassField = new TextField();
        aPassField.setPromptText("Password");
        aPassField.setMaxWidth(accPassCol.getPrefWidth());
        
        Button btAddAcc = new Button("Add");
        btAddAcc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //If username/password fields are empty, display a warning.
                if(aUserField.getText() == null || aPassField.getText() == null || aUserField.getText().trim().isEmpty() || aPassField.getText().trim().isEmpty()){
                    warning.setText("Enter a username and password.");
                    warning.setStyle("-fx-text-fill: red");
                //If username/password fields contains "&&&!!!", display a warning. This is required so customer.txt file does not break when updating.
                } else if(aUserField.getText().contains("&&&!!!") || aPassField.getText().contains("&&&!!!")){
                    warning.setText("Username nor password can contain \"&&&!!!\".");
                    warning.setStyle("-fx-text-fill: red");
                //If a duplicate name is typed, display a warning.
                } else if(GrabData.aUserArray.contains(aUserField.getText())){
                    warning.setText("This username already exists!");
                    warning.setStyle("-fx-text-fill: red");
                //Add username and password into table and arrays.
                }else{
                    warning.setText("Enter a username and password.");
                    warning.setStyle("-fx-text-fill: transparent");
                    data.add(new Account(
                            aUserField.getText(),
                            aPassField.getText(),
                            "0"));
                    
                    GrabData.aUserArray.add(aUserField.getText());
                    GrabData.aPassArray.add(aPassField.getText());
                    GrabData.aPointsArray.add("0");
                    aUserField.clear();
                    aPassField.clear();
                }
            }
        });
        
        HBox middleSec = new HBox();
        middleSec.getChildren().addAll(aUserField, aPassField, btAddAcc);
        middleSec.setSpacing(3);

            ///////////////////////////////Bottom: Section: Delete and Back/////////////////////////////
        HBox botSec = new HBox();
        botSec.getChildren().addAll(btDelete, btBack);
        botSec.setSpacing(3);
        
        //When specific rows are selected and the delete button is clicked, remove row from table and arrays.
        btDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                ObservableList<Account> selectedRows = table.getSelectionModel().getSelectedItems();
                ArrayList<Account> rows = new ArrayList<>(selectedRows);
                rows.forEach(row -> table.getItems().remove(row));
                
                rows.forEach(row -> GrabData.aPassArray.remove(GrabData.aUserArray.indexOf(row.aUser)));
                rows.forEach(row -> GrabData.aPointsArray.remove(GrabData.aUserArray.indexOf(row.aUser)));
                rows.forEach(row -> GrabData.aUserArray.remove(row.aUser));

                
            }
        });
                     
        GridPane combine = new GridPane();
        combine.setAlignment(Pos.CENTER); 
        
        warning.setStyle("-fx-text-fill: transparent");
        combine.add(title, 0, 0);
        combine.add(table, 0, 1);
        combine.add(middleSec, 0, 2);
        combine.add(warning, 0, 3);
        combine.add(botSec, 0, 4);
        
        final VBox wrapUp = new VBox();
        wrapUp.setSpacing(5);
        wrapUp.setPadding(new Insets(0, 0, 0, 0));
        wrapUp.getChildren().addAll(combine);
        
 
     
        Scene scene = new Scene(wrapUp, 450, 500);
        stage.setScene(scene);
        stage.show();
    }
    
    
    
    
    
    
    
    
    
        public static class Account {
        protected String aUser;
        protected String aPass;
        protected String aPoints;
        
 
        private Account(String aUser, String aPass, String aPoints) {
            this.aUser = aUser;
            this.aPass = aPass;
            this.aPoints = aPoints;
        }
 
        public String getAccUser() {
            return aUser;
        }
 
        public void setAccUser(String s) {
            aUser = s;
        }
 
        public String getAccPass() {
            return aPass;
        }
 
        public void setAccPass(String s) {
            aPass = s;
        }
        
        public String getAccPoints() {
            return aPoints;
        }
 
        public void setAccPoints(String s) {
            aPoints = s;
        }
    }
    
    
}
