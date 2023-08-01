package BSA;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import static javafx.application.Application.launch;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

/**
 *
 * @author Group 8, Section 2
 */
public class GUIDriver extends Application {
    //All stages are initialized.
    LoginScreen logScreen = new LoginScreen();
    OwnerStartScreen OSScreen = new OwnerStartScreen();
    OwnerBooksScreen OBScreen = new OwnerBooksScreen();
    OwnerCustomersScreen OCScreen = new OwnerCustomersScreen();
    CustomerStartScreen CSScreen = new CustomerStartScreen(null, "0");
    GrabData gd = new GrabData();
    CustomerCostScreen CCScreen = new CustomerCostScreen(null, 0, null, false);
    
    // EFFECTS: Start of program. Window is opened.
    @Override
    public void start(Stage firstStage) {
        //Read from saved data
        gd.readBookFile("books.txt");
        gd.readCustFile("customers.txt");
        
        //Open GUI window
        logScreen = new LoginScreen();
        logScreen.start(firstStage);
        eventHandler(firstStage);
    }
    
    // EFFECTS: Required as a bridge between different stages.
    public void eventHandler(Stage currentStage){
        ///////////////////////////////////Login Screen/////////////////////////////////////
        logScreen.btLogin.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {   
                   String currUser = logScreen.userField.getText();
                   String currPass = logScreen.passField.getText();
                   
                   //Used to access Owner Start Screen.
                   if(currUser.equals("admin") && currPass.equals("admin")){
                       OSScreen = new OwnerStartScreen();
                       OSScreen.start(currentStage);
                       eventHandler(currentStage);
                    //If username/password does not exist, display a warning.
                   }else if (!(gd.ifCustExists(currUser, currPass))){
                       if(logScreen.switchLS){
                           logScreen.switchLS = false;
                           logScreen.LoginGrid.add(logScreen.switchLSText, 0, 4);
                       }
                    //Used to access Customer's Start Screen.
                   } else{
                       CSScreen = new CustomerStartScreen(currUser, GrabData.aPointsArray.get(GrabData.aUserArray.indexOf(currUser)));
                       CSScreen.start(currentStage);
                       eventHandler(currentStage);
                   }
                   logScreen.userField.clear();
                   logScreen.passField.clear();
                }
            }
        );
        ///////////////////////////////////Owner Start Screen/////////////////////////////////////
        //Open Owner Books Screen.
        OSScreen.btBooks.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                   OBScreen = new OwnerBooksScreen();
                   OBScreen.start(currentStage);
                   eventHandler(currentStage);
                 
                }
            }
        );
        //Open Owner Customers Screen.
        OSScreen.btCust.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                   OCScreen = new OwnerCustomersScreen();
                   OCScreen.start(currentStage);
                   eventHandler(currentStage);
                 
                }
            }
        );
        //Return into login Screen.
        OSScreen.btLogout.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                   logScreen = new LoginScreen();
                   logScreen.start(currentStage);
                   eventHandler(currentStage);
                }
            }
        );
        ///////////////////////////////////Owner Books Screen/////////////////////////////////////
        //Return to Owner Start Screen.
        OBScreen.btBack.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    OSScreen = new OwnerStartScreen();
                    OSScreen.start(currentStage);
                    eventHandler(currentStage);
                }
            }
        );
        ///////////////////////////////////Owner Customers Screen/////////////////////////////////////
        //Go back to Owner Start Screen.
        OCScreen.btBack.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    OSScreen = new OwnerStartScreen();
                    OSScreen.start(currentStage);
                    eventHandler(currentStage);
                }
            }
        );
        
        ///////////////////////////////////Customer Start Screen/////////////////////////////////////
        //When Buy/Redeem button is clicked, data from this page is transferred to Customer Cost Screen.
        CSScreen.btBuy.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    CSScreen.BuyAction();
                    if(CSScreen.booksChecked){
                        CCScreen = new CustomerCostScreen(CSScreen.custName, CSScreen.custPoints, CSScreen.deletedBooks, false);
                        CCScreen.start(currentStage);
                        eventHandler(currentStage);
                    }
                }
            }
        );
        
        CSScreen.btRedeem.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    CSScreen.BuyAction();
                    if(CSScreen.booksChecked){
                        CCScreen = new CustomerCostScreen(CSScreen.custName, CSScreen.custPoints, CSScreen.deletedBooks, true);
                        CCScreen.start(currentStage);
                        eventHandler(currentStage);
                    }
                }
            }
        );
        //Return to Login screen.
        CSScreen.btLogout.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    logScreen = new LoginScreen();
                    logScreen.start(currentStage);
                    eventHandler(currentStage);
                }
            }
        );
        ///////////////////////////////////Customer Cost Screen/////////////////////////////////////
        //Return to Login Screen.
        CCScreen.btLogout.setOnAction(
            new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
                    logScreen = new LoginScreen();
                    logScreen.start(currentStage);
                    eventHandler(currentStage);
                }
            }
        );
        
        ///////////////////////////////////Close GUI/////////////////////////////////////
        //Window is closed, and data is saved into books.txt and customers.txt.
        currentStage.setOnCloseRequest(
                    new EventHandler<WindowEvent>() {
                         @Override
                         public void handle(WindowEvent e) {
                             System.out.println("Window is closing. All data will be saved.");
                             
                             gd.writeBookFile("books.txt");
                             gd.writeCustFile("customers.txt");
                        }  
                    }
                );

    }

    public static void main(String[] args) {
        launch(args);
    }
}
