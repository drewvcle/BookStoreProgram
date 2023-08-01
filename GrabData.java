package BSA;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.io.File; 
import java.io.FileWriter;
import java.util.Scanner;

/**
 *
 * @author Group 8, Section 2
 */
public class GrabData {
    //Book Arrays
    public static ArrayList<String> bNameArray = new ArrayList<String>();
    public static ArrayList<String> bPriceArray = new ArrayList<String>();
    //Account Arrays
    public static ArrayList<String> aUserArray = new ArrayList<String>();
    public static ArrayList<String> aPassArray = new ArrayList<String>();
    public static ArrayList<String> aPointsArray = new ArrayList<String>();
    
    //Read and Write instance variables
    public File myBookFile;
    public File myCustFile;
    
    // MODIFIES: aUserArray, aPassArray, aPointsArray
    // EFFECTS: updates Account arrays.
    public static void setAccArrays(ObservableList<OwnerCustomersScreen.Account> data){
        aUserArray.clear();
        aPassArray.clear();
        aPointsArray.clear();
        for(int i = 0; i < data.size(); i++){
            aUserArray.add(data.get(i).aUser);
            aPassArray.add(data.get(i).aPass);
            aPointsArray.add(data.get(i).aPoints);
        }
    }

    // EFFECTS: returns username array.
    public static ArrayList<String> getAUserArray(){
        return aUserArray;
    }
    // MODIFIES: aUserArray
    // EFFECTS: sets username array.
    public static void setAUserArray(ArrayList<String> uArr){
        aUserArray = uArr;
    }
    // EFFECTS: returns password array.
    public static ArrayList<String> getAPassArray(){
        return aPassArray;
    }
    // MODIFIES: aPassArray
    // EFFECTS: sets password array.
    public static void setAPassArray(ArrayList<String> pArr){
        aPassArray = pArr;
    }
    // EFFECTS: returns points array.
    public static ArrayList<String> getAPointsArray(){
        return aPointsArray;
    }
    // MODIFIES: aPointsArray
    // EFFECTS: sets points array.
    public static void setAPointsArray(ArrayList<String> poArr){
        aPointsArray = poArr;
    }
    // MODIFIES: bNameArray, bPriceArray
    // EFFECTS: Updates Book arrays.
    public static void setBookArrays(ObservableList<OwnerBooksScreen.Book> data){
        bNameArray.clear();
        bPriceArray.clear();
        for(int i = 0; i < data.size(); i++){
            bNameArray.add(data.get(i).bName);
            bPriceArray.add(data.get(i).bPrice);
        }
    }
    // EFFECTS: returns name array.
    public static ArrayList<String> getBNameArray(){
        return bNameArray;
    }
    // MODIFIES: bNameArray
    // EFFECTS: sets name array.
    public static void setBNameArray(ArrayList<String> nArr){
        bNameArray = nArr;
    }
    // EFFECTS: returns price array.
    public static ArrayList<String> getBPriceArray(){
        return bPriceArray;
    }
    // MODIFIES: bPriceArray
    // EFFECTS: sets price array.
    public static void setBPriceArray(ArrayList<String> bArr){
        bPriceArray = bArr;
    }
    // EFFECTS: Checks if the customer name exist in the username array; if it does, return true. Otherwise, return false.
    public static boolean ifCustExists(String user, String pass){
        if(aUserArray.contains(user) && aPassArray.contains(pass))
            return true;
        return false;
    }
    // MODIFIES: aPointsArray
    // EFFECTS: updates a specific user's points.
    public static void updateUser(String user, String points){
        aPointsArray.set(aUserArray.indexOf(user), points);
    }
    // MODIFIES: bNameArray, bPriceArray, aUserArray, aPassArray, aPointsArray
    // EFFECTS: resets all arrays.
    private void clearArrays(){
        bNameArray.clear();
        bPriceArray.clear();
        aUserArray.clear();
        aPassArray.clear();
        aPointsArray.clear();
    }
    // MODIFIES: bNameArray, bPriceArray
    // EFFECTS: Reads from book file and stores data.
    public void readBookFile(String s){
        bNameArray.clear();
        bPriceArray.clear();
        try{
            myBookFile = new File(s);
            myBookFile.createNewFile();
        } catch (IOException e){
            System.out.println("An error occurred, unable to create file.");
            e.printStackTrace();
        }
        try{
            Scanner reader = new Scanner(myBookFile);
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                String[] words = line.split("\\$");
                
                bNameArray.add(words[0]);
                bPriceArray.add("$" + words[1]);
            }
            reader.close();
        }catch(IOException e){
            System.out.println("An error occurred, unable to read file.");
            e.printStackTrace();
            System.exit(1);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Incorrect formatting of file. Program is exiting.");
            System.exit(1);
        }
    }
    // EFFECTS: Writes data into book file.
    public void writeBookFile(String s){
        try{
            myBookFile = new File(s);
            myBookFile.createNewFile();
        } catch (IOException e){
            System.out.println("An error occurred, unable to create file.");
            e.printStackTrace();
        }  
        try {
            FileWriter w = new FileWriter(s);
            for(int i = 0; i < bNameArray.size(); i++){
                w.write(bNameArray.get(i) + bPriceArray.get(i));
                if(i != (bNameArray.size() - 1))
                    w.write("\n");
            }
            
            w.close();
        } catch (IOException e) {
            System.out.println("An error occurred, unable to write to file.");
            e.printStackTrace();
        }
    }
    // MODIFIES: aUserArray, aPassArray, aPointsArray
    // EFFECTS: Reads from customer file and stores data.
    public void readCustFile(String s){
        aUserArray.clear();
        aPassArray.clear();
        aPointsArray.clear();
        try{
            myCustFile = new File(s);
            myCustFile.createNewFile();
        } catch (IOException e){
            System.out.println("An error occurred, unable to create file.");
            e.printStackTrace();
        }
        try{
            Scanner reader = new Scanner(myCustFile);
            while(reader.hasNextLine()){
                String line = reader.nextLine();
                String[] words = line.split("&&&!!!");
                
                aUserArray.add(words[0]);
                aPassArray.add(words[1]);
                aPointsArray.add(words[2]);
            }
            reader.close();
        }catch(IOException e){
            System.out.println("An error occurred, unable to read file.");
            e.printStackTrace();
            System.exit(1);
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Incorrect formatting of file. Program is exiting.");
            System.exit(1);
        }
    }
    // EFFECTS: Writes data into customer file.
    public void writeCustFile(String s){
        try{
            myCustFile = new File(s);
            myCustFile.createNewFile();
        } catch (IOException e){
            System.out.println("An error occurred, unable to create file.");
            e.printStackTrace();
        }  
        try {
            FileWriter w = new FileWriter(s);
            for(int i = 0; i < aUserArray.size(); i++){
                w.write(aUserArray.get(i) + "&&&!!!" + aPassArray.get(i) + "&&&!!!" + aPointsArray.get(i));
                if(i != (aUserArray.size() - 1))
                    w.write("\n");
            }
            w.close();
        } catch (IOException e) {
            System.out.println("An error occurred, unable to write to file.");
            e.printStackTrace();
        }
    }
}
