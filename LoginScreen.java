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
public class LoginScreen extends Application {
    Label welcome = new Label("Welcome to the BookStore App!");
    Label userText = new Label("Username:");
    Label passText = new Label("Password:");
    TextField userField = new TextField();
    TextField passField = new TextField();
    Button btLogin = new Button("Login");
    
    GridPane LoginGrid = new GridPane();
    boolean switchLS = true;
    Label switchLSText = new Label("Invalid username or password!");
    
    // EFFECTS: Stage and various interactions are created within this method.
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(createLoginGridPane(), 380, 150);
        stage.setTitle("Bookstore App"); 
        stage.setScene(scene);
        stage.show(); 
        
    }
    
    // EFFECTS: Creation of a GridPane.
    public GridPane createLoginGridPane() {
        switchLSText.setStyle("-fx-text-fill: red");
        
        LoginGrid.setAlignment(Pos.CENTER); 
        
        LoginGrid.add(welcome, 0, 0);
        LoginGrid.add(userText, 0, 1);
        LoginGrid.add(userField, 1, 1);
        LoginGrid.add(passText, 0, 2);
        LoginGrid.add(passField, 1, 2);
        LoginGrid.add(btLogin, 1, 3);
        
        return LoginGrid;
    }
    

}




