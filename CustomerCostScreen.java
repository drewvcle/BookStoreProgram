package BSA;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import java.util.ArrayList;

/**
 *
 * @author Group 8, Section 2
 */
public class CustomerCostScreen extends Application {
    public static ArrayList<CustomerStartScreen.CustBook> selectBooks = new ArrayList<CustomerStartScreen.CustBook>();
    String custName;
    double custPoints;
    String status;
    boolean isRedeem;
    
    //Instances variables to create Stage
    GridPane CSSGrid = new GridPane();
    Label totalCost;
    Label pointsStatus;
    Button btLogout = new Button("Logout");
    
    
    // MODIFIES: this.custName, this.custPoints, and this.isRedeem
    // EFFECTS: Constructor inputs changes to instance variables
    public CustomerCostScreen(String user, double p, ArrayList<CustomerStartScreen.CustBook> sb, boolean isRedeem){
        CustomerCostScreen.selectBooks = sb;
        this.custName = user;
        this.custPoints = p;
        this.isRedeem = isRedeem;
    }
    

    // EFFECTS: Stage and various interactions are created within this method. Below, more detail is provided
    // for each section of the code.
    @Override
    public void start(Stage stage) {
        //Calculates total cost of selected books.
        double tc = 0;
        for(int i = 0; i < selectBooks.size(); i++)
            tc += Double.parseDouble(selectBooks.get(i).bPrice.substring(1));
        
        //If the "Buy" button is clicked, activate this if statement.
        if(!isRedeem){
            custPoints = custPoints + (tc * 10);
        }
        //If the "Redeem and Buy" button is clicked, activate this if statement.
        if(isRedeem){
            if((custPoints/100) >= tc){
                custPoints = (custPoints/100 - tc) * 100;
                tc = 0;
            } else{
                tc = tc - (custPoints/100);
                custPoints = tc * 10;
            }
        }
        //Requirements for silver/gold status.
        if(this.custPoints < 1000)
            this.status = "Silver";
        else
            this.status = "Gold";
        //Stage is created.
        totalCost = new Label("Total Cost: " + String.format("$%.2f", tc) + ".");
        totalCost.setFont(new Font("Arial", 20));
        totalCost.setAlignment(Pos.CENTER); 
        pointsStatus = new Label("Points: " + String.format("%.1f", custPoints) + ", Status: " + status + ".");
        pointsStatus.setFont(new Font("Arial", 16));
        pointsStatus.setAlignment(Pos.CENTER); 
        
        Scene scene = new Scene(createCSSPane(), 380, 150);
        stage.setTitle("Bookstore App");
        stage.setScene(scene);
        stage.show(); 
        //User's points is updated.
        GrabData.updateUser(custName, String.format("%.1f", custPoints));
    }
    
    // EFFECTS: Creation of a GridPane.
    public GridPane createCSSPane() {
        CSSGrid.setAlignment(Pos.CENTER); 
        
        Label empty1 = new Label(" ");
        Label empty2 = new Label(" ");
        
        CSSGrid.add(totalCost, 0, 0);
        CSSGrid.add(empty1, 0, 1);
        CSSGrid.add(pointsStatus, 0, 2);
        CSSGrid.add(empty2, 0, 3);
        CSSGrid.add(btLogout, 0, 4);
  
        return CSSGrid;
    }
        
}
    

