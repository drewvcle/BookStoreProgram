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
public class OwnerBooksScreen extends Application {
    static private TableView<Book> table = new TableView<Book>();
    static public final ObservableList<Book> data = FXCollections.observableArrayList();
    
    Label warning = new Label("Price must be a number over 0.00!");
    //Buttons for Bottom Section
    Button btDelete = new Button("Delete");
    Button btBack = new Button("Back");

    // MODIFIES: table, data
    // EFFECTS: Constructor inputs changes to instance variables
    public OwnerBooksScreen(){
        table = new TableView<Book>();
        data.clear();
        
        for(int i = 0; i < GrabData.bNameArray.size(); i++){
            data.add(new Book(
                GrabData.bNameArray.get(i),
                GrabData.bPriceArray.get(i)));
        } 
        
    }
    
    // EFFECTS: Stage and various interactions are created within this method. Below, more detail is provided
    // for each section of the code.
    @Override
    public void start(Stage stage) {
        //Elements are created to be placed in stage.
        stage.setTitle("Bookstore App");
        warning.setStyle("-fx-text-fill: transparent");
        
        /////////////////////////////Top Section: Table/////////////////////////////
        Label title = new Label("Books Manager");
        title.setFont(new Font("Arial", 21));
        
        table.setEditable(true);
        
        TableColumn bookNameCol = new TableColumn("Book Name");
        bookNameCol.setMinWidth(300);
        bookNameCol.setCellValueFactory(
            new PropertyValueFactory<Book, String>("bookName"));
        //bookNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        bookNameCol.setOnEditCommit(
            new EventHandler<TableColumn.CellEditEvent<Book, String>>() {
                @Override
                public void handle(TableColumn.CellEditEvent<Book, String> t) {
                    ((Book) t.getTableView().getItems().get(
                            t.getTablePosition().getRow())
                            ).setBookName(t.getNewValue());
                }
            }
        );
        
        TableColumn bookPriceCol = new TableColumn("Book Price");
        bookPriceCol.setMinWidth(100);
        bookPriceCol.setCellValueFactory(
            new PropertyValueFactory<Book, String>("bookPrice"));
        //bookPriceCol.setCellFactory(TextFieldTableCell.forTableColumn());
        bookPriceCol.setOnEditCommit(
            new EventHandler<CellEditEvent<Book, String>>() {
                @Override
                public void handle(CellEditEvent<Book, String> t) {
                    ((Book) t.getTableView().getItems().get(
                        t.getTablePosition().getRow())
                        ).setBookPrice(t.getNewValue());
                }
            }      
        );
        
        table.setItems(data);
        table.getColumns().addAll(bookNameCol, bookPriceCol);
        
        ///////////////////////////////Middle Section: add Book Name and Price/////////////////////////////
        TextField bNameField = new TextField();
        bNameField.setPromptText("Book Name");
        bNameField.setMaxWidth(bookNameCol.getPrefWidth());
        TextField bPriceField = new TextField();
        bPriceField.setPromptText("Book Price");
        bPriceField.setMaxWidth(bookPriceCol.getPrefWidth());
        
        Button btAddBook = new Button("Add");
        btAddBook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                //If name and price field is empty, display a warning.
                if(bNameField.getText() == null || bPriceField.getText() == null || bNameField.getText().trim().isEmpty() || bPriceField.getText().trim().isEmpty()){
                    warning.setStyle("-fx-text-fill: red");
                    warning.setText("Enter a book name and price.");
                //If a duplicate name is typed, display a warning.
                } else if(GrabData.bNameArray.contains(bNameField.getText())){
                    warning.setStyle("-fx-text-fill: red");
                    warning.setText("This book already exists!");
                    System.out.println("Notes for TA: In the lab manual, it mentions that only one copy of each book exists.");
                } else {
                    warning.setText("Price must be a number over 0.00!");
                    String bPriceEdit = bPriceField.getText();
                    //Removes the $ at the start of the price.
                    if(bPriceEdit.charAt(0) == '$'){
                        bPriceEdit = bPriceEdit.substring(1);
                    }
                    //If price is not a double, or price is negative, display warning.
                    if((!checkIfDouble(bPriceEdit)) || bPriceEdit.contains("-")){
                        warning.setStyle("-fx-text-fill: red");
                        bPriceField.clear();
                    } else{
                        //Book is added into table and arrays.
                        warning.setStyle("-fx-text-fill: transparent");
                        bPriceEdit = "$" + bPriceEdit;
                        if(!bPriceEdit.contains("."))
                            bPriceEdit = bPriceEdit + ".00";

                        data.add(new Book(
                                bNameField.getText(),
                                bPriceEdit));

                        GrabData.bNameArray.add(bNameField.getText());
                        GrabData.bPriceArray.add(bPriceEdit);
                        
                        bNameField.clear();
                        bPriceField.clear();

                    }
                }
            }
        });
        
        HBox middleSec = new HBox();
        middleSec.getChildren().addAll(bNameField, bPriceField, btAddBook);
        middleSec.setSpacing(3);
        
            ///////////////////////////////Bottom: Section: Delete and Back/////////////////////////////
        HBox botSec = new HBox();
        botSec.getChildren().addAll(btDelete, btBack);
        botSec.setSpacing(3);
        
        //Removes selected books from table and arrays.
        btDelete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
                ObservableList<Book> selectedRows = table.getSelectionModel().getSelectedItems();
                ArrayList<Book> rows = new ArrayList<>(selectedRows);
                rows.forEach(row -> table.getItems().remove(row));
                
                rows.forEach(row -> GrabData.bPriceArray.remove(GrabData.bNameArray.indexOf(row.bName)));
                rows.forEach(row -> GrabData.bNameArray.remove(row.bName));

                
            }
        });
                
                
        ///////////////////////////////Creating Stage/////////////////////////////
        GridPane combine = new GridPane();
        combine.setAlignment(Pos.CENTER); 
        
        combine.add(title, 0, 0);
        combine.add(table, 0, 1);
        combine.add(middleSec, 0, 2);
        combine.add(warning, 0, 3);
        combine.add(botSec, 0, 4);
        
        final VBox wrapUp = new VBox();
        wrapUp.setSpacing(5);
        wrapUp.setPadding(new Insets(0, 0, 0, 0));
        wrapUp.getChildren().addAll(combine);
        
 
     
        Scene scene = new Scene(wrapUp, 500, 500);
        stage.setScene(scene);
        stage.show();
    }
    
    public static class Book {
        public String bName;
        public String bPrice;
        
        // MODIFIES: this.bName, this.bPrice
        // EFFECTS: Constructor inputs changes to instance variables
        public Book(String bName, String bPrice) {
            this.bName = bName;
            this.bPrice = bPrice;
        }
        // EFFECTS: Returns book name
        public String getBookName() {
            return bName;
        }
        // MODIFIES: bName
        // EFFECTS: sets book name
        public void setBookName(String s) {
            bName = s;
        }
        // EFFECTS: Returns book price
        public String getBookPrice() {
            return bPrice;
        }
        // MODIFIES: bPrice
        // EFFECTS: sets book price
        public void setBookPrice(String s) {
            bPrice = s;
        }
    }
    // EFFECTS: Checks if string can be converted into double; if it does, return true. Otherwise, return false.
    public boolean checkIfDouble(String s){
        try{
            Double.parseDouble(s);
            return true;
        }catch(NumberFormatException error){
            return false;
        }
    }
    
}
