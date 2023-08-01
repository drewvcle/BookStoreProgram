package BSA;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;

/**
 *
 * @author Group 8, Section 2
 */
public class OwnerStartScreen extends Application {
    Button btBooks = new Button("Books");
    Button btCust = new Button("Customers");
    Button btLogout = new Button("Logout");
    
    // EFFECTS: Stage and various interactions are created within this method.
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(createOSSBox(), 200, 150);
        stage.setTitle("Bookstore App"); 
        stage.setScene(scene);
        stage.show(); 

    }
    // EFFECTS: Creation of a GridPane.
    public GridPane createOSSBox() {
        Label empty = new Label(" ");
        Label empty2 = new Label(" ");

        GridPane OSSGrid = new GridPane();
        OSSGrid.setAlignment(Pos.CENTER); 
        
        OSSGrid.add(btBooks, 0, 0);
        OSSGrid.add(empty, 0, 1);
        OSSGrid.add(btCust, 0, 2);
        OSSGrid.add(empty2, 0, 3);
        OSSGrid.add(btLogout, 0, 4);
 
        return OSSGrid;
    }
    
    
}