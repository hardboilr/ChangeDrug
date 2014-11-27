package Entities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class FileHandler
{
    
    public static List<String> loadPlayers(String filename)
    {
        Scanner file_scanner = null;
        List<String> playerList = new LinkedList<>();

        try {
            file_scanner = new Scanner(new File(filename));  //Connection to the file using the Scanner object
        } catch (FileNotFoundException ex) {
            System.out.println("Could not find the file to load from! Returning null.");
            ex.printStackTrace();
            return null;  //If something goes wrong the method returns null
        }
        
        while (file_scanner.hasNext()) {  //File found. Reading one line. 
            String linje = file_scanner.nextLine();
            Scanner sc = new Scanner(linje).useDelimiter(",");
            String name = sc.next();
            String credits  = sc.next();
            String player = new String(name + " " + credits);
            playerList.add(player);  //Reading in a single line and saving in the ArrayList
        }

        file_scanner.close();  //Closing the file
        return playerList;    //returning the arraylist
    }

       
    public static boolean savePlayers(List<String> playerList, String filename)
    {
        if( playerList == null ) { 
            return false;
        }  //Checking parameter for null.
        FileWriter output;  //Creating reference for filewriter.
        
        try {
                output = new FileWriter(new File(filename));  //Opening connection to file.
                for (String playerLine : playerList) {   //running through the ArrayList.                    
                    output.write(playerLine.toString() + "\n");  //Each String object is written as a line in file.
            }

            output.close();  //Closing the file
        } catch (IOException ex) {  //If something goes wrong everything is send to system out.
            System.out.println("Could not save to file!");
            System.out.println(ex.toString());
            ex.printStackTrace();
            return false;  //If something goes wrong false is returned!
        }

        return true;
    }

}//END CLASS
