/**
 * This file contains code for mining data from http://planecrashinfo.com/
 * No API found, so, URL and sample-HTML-code trickery employed.
 * 
 * Mined data classes for each accident:
 * - Date (String)
 * - Location (String)
 * - Passengers on board and Fatalities (both int)
 *  
 * Data is then exported to a .csv file.
 * 
 * @author Tushar
 */

import java.net.URL;
import java.util.*;
import java.io.*;

public class MiningData {
    public static void main(String[] args) throws IOException {
        String urlFeed = new String("http://planecrashinfo.com/");  // Base.
        Integer year = 1920;  // Dataset begins.
        File output = new File("output.csv");                                // Declare a new File variable.
        if(output.exists()) {
            System.out.print("The File already exists.");
        }
        else {
           output.createNewFile();                                           // Create output.txt
        }
        FileWriter o = new FileWriter(output);                          
        PrintWriter out = new PrintWriter(o);
        output.createNewFile();
        while (year <= 2015) {
            String tempAdd = new String();
            Scanner readData;
            tempAdd = year.toString() + "/" + year.toString() + ".htm";
            URL url = new URL(urlFeed + tempAdd);
            readData = new Scanner(url.openStream());
            ArrayList<String> data = new ArrayList<>();  // Stores raw html doc.
            while (readData.hasNext()) {
                data.add(readData.nextLine());
            }            
            //boolean readLocation = false;  // Location immediately follows date.
            for (String str : data) {
                /*if (readLocation && str.contains("<br>")) {  // Location contains two parts sep. by <br> tag. Get first.
                    String[] str2 = str.split("<br>"); 
                    str2 = str2[0].split(">");  // Skip the tags for alignment and font.
                    str2 = str2[2].split(",");
                    out.print(str2[1]+ ", ");
                    readLocation = false;
                }*/
                if (str.contains(".htm\">")) {  // Reading date (Found by reading sample data).
                    String[] str2 = str.split(">");
                    out.print(str2[3].substring(0, 11) + ", ");
                    //readLocation = true;
                }
                else if (str.contains("width=\"1\"")) { // Distinguishing feature of fatalities.
                    String[] str2 = str.split("width=\"1\"");
                    str2 = str2[1].split(">");
                    str2 = str2[2].split("[(]");
                    str2 = str2[0].split("[/]");
                    out.print(str2[0] + ", " + str2[1] + "\n");
                }
            }
            year++;
        }
        out.close();
    }    
}
