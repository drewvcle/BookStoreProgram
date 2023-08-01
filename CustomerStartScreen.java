package BSA;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.text.Font;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.BooleanProperty;

/**
 *
 * @author Group 8, Section 2
 */
public class CustomerStartScreen extends Application{
    public static ArrayList<CustBook> selectBooks = new ArrayList<CustBook>();
    public ArrayList<CustBook> deletedBooks = new ArrayList<CustBook>();
    String custName;
    double custPoints;
    String status;
    
    //Instance variables to create Stage.
    boolean booksChecked = false;
    Button btBuy = new Button("Buy");
    Button btRedeem = new Button("Redeem points and Buy");
    Button btLogout = new Button("Logout");
    Label warning = new Label("Please select one or more books!");
    
    private TableView<CustBook> table = new TableView<CustBook>();
    private final ObservableList<CustBook> custData = FXCollections.observableArrayList();
    
    // MODIFIES: this.custName, this.custPoints, and this.status
    // EFFECTS: Constructor inputs changes to instance variables. Data is loaded into table.
    public CustomerStartScreen(String user, String p){
        selectBooks = new ArrayList<CustBook>();
        this.custName = user;
        this.custPoints = Double.parseDouble(p);
        if(this.custPoints < 1000)
            this.status = "Silver";
        else
            this.status = "Gold";
        
        for(int i = 0; i < GrabData.bNameArray.size(); i++){
            selectBooks.add(new CustBook(
                                GrabData.bNameArray.get(i),
                                GrabData.bPriceArray.get(i)));
            custData.add(new CustBook(
                                GrabData.bNameArray.get(i),
                                GrabData.bPriceArray.get(i)));
        }
    }
    // MODIFIES: this.custName, this.custPoints, and this.isRedeem
    // EFFECTS: Constructor inputs changes to instance variables
    @Override
    public void start(Stage stage) {
        stage.setTitle("Bookstore App");
        //Below is the creation of various elements for the Stage.
        /////////////////////////////Top Section: Title/////////////////////////////
        Label title = new Label("Welcome " + custName + ". You have " + custPoints + " points. Your status is " + status + ".");
        title.setFont(new Font("Arial", 20));
        table.setEditable(true);
        
        /////////////////////////////Middle Section: Table/////////////////////////////
        TableColumn bookNameCol = new TableColumn("Book Name");
        bookNameCol.setMinWidth(300);
        bookNameCol.setCellValueFactory(
            new PropertyValueFactory<CustBook, String>("bookName"));
        
        TableColumn bookPriceCol = new TableColumn("Book Price");
        bookPriceCol.setMinWidth(60);
        bookPriceCol.setCellValueFactory(
            new PropertyValueFactory<CustBook, String>("bookPrice"));

        TableColumn<CustBook, Boolean> select = new TableColumn("Select");
        select.setMinWidth(60);
        select.setCellFactory(CheckBoxTableCell.forTableColumn(select));
        select.setCellValueFactory(cd -> cd.getValue().selectProperty());
        
        table.setItems(custData);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getColumns().addAll(bookNameCol, bookPriceCol, select);
        
        ///////////////////////////////Bottom Section: Buy, Redeem, and Logout/////////////////////////////
        HBox botSec = new HBox();
        botSec.getChildren().addAll(btBuy, btRedeem, btLogout);
        botSec.setSpacing(3);
        
        ///////////////////////////////Creating Stage/////////////////////////////
        warning.setStyle("-fx-text-fill: transparent");
        
        GridPane combine = new GridPane();
        combine.setAlignment(Pos.CENTER);
        
        combine.add(title, 0, 0);
        combine.add(table, 0, 1);
        combine.add(botSec, 0, 2);
        combine.add(warning, 0, 3);
        
        final VBox wrapUp = new VBox();
        wrapUp.setSpacing(5);
        wrapUp.setPadding(new Insets(0, 0, 0, 0));
        wrapUp.getChildren().addAll(combine);
        
        Scene scene = new Scene(wrapUp, 650, 500);
        stage.setScene(scene);
        stage.show();
    }

    public static class CustBook {
        public String bName;
        public String bPrice;
        public BooleanProperty sel;
        public Boolean readableBool;
        
        // MODIFIES: this.bName, this.bPrice
        // EFFECTS: Constructor inputs changes to instance variables
        public CustBook(String bName, String bPrice) {
            this.sel = new SimpleBooleanProperty(false);
            this.bName = bName;
            this.bPrice = bPrice;
        }

        // EFFECTS: Whenever Checkmark Box updates, a BooleanProperty is returned. Instance variable "readableBool"
        // is also updated.
        public BooleanProperty selectProperty() {
            int o = selectBooks.indexOf(bName);
            for(int i = 0; i < selectBooks.size(); i++){
                if(bName.equals(selectBooks.get(i).bName))
                    o = i;
            }
            selectBooks.get(o).readableBool = sel.getValue();
            
            return sel;
        }
        // EFFECTS: returns book name.
        public String getBookName() {
            return bName;
        }
        // MODIFIES: bName
        // EFFECTS: sets book name.
        public void setBookName(String s) {
            bName = s;
        }
        // EFFECTS: returns book price.
        public String getBookPrice() {
            return bPrice;
        }
        // MODIFIES: bPrice
        // EFFECTS: sets book price.
        public void setBookPrice(String s) {
            bPrice = s;
        }
    }
    
    // EFFECTS: When this is initiated, table data and array data is updated.
    public void BuyAction(){
        //Checks if any Checkboxes have been checked. If not, display a warning.
        booksChecked = false;
        for(int i = 0; i < selectBooks.size(); i++){
            if(selectBooks.get(i).readableBool)
                booksChecked = true;
        }
        if(!booksChecked){
            warning.setStyle("-fx-text-fill: red");
        } else{
            //Removes items from table and arrays. Items are added into deletedBooks to be used in CustomerCostScreen.
            deletedBooks = new ArrayList<CustBook>();
            for(int i = selectBooks.size() - 1; i >= 0; i--){
               if(selectBooks.get(i).readableBool){
                   deletedBooks.add(selectBooks.get(i));
                   table.getItems().remove(i);

                   GrabData.bNameArray.remove(selectBooks.get(i).bName);
                   GrabData.bPriceArray.remove(selectBooks.get(i).bPrice);
                }
            }
        }
    }
    
    
    
    
    
}

