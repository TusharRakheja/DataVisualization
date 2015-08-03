/**
 * This file contains code for mining data from http://planecrashinfo.com/
 * No API found, so, URL and sample-HTML-code trickery employed.
 * 
 * Mined data classes for each accident:
 * - Date (String)
 * - Fatalities (int)
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
        Integer year = 1950;  // Dataset begins.
        File output = new File("Output.csv");                                // Declare a new File variable.
        if(output.exists()) {
            System.out.print("The file already exists.");
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
            for (String str : data) {
                if (str.contains(".htm\">")) {  // Reading date (Found by reading sample data).
                    String[] str2 = str.split(">");
                    out.print(str2[3].substring(0, 11) + ", ");
                }
                else if (str.contains("width=\"1\"")) { // Distinguishing feature of fatalities.
                    String[] str2 = str.split("width=\"1\"");
                    str2 = str2[1].split(">");
                    str2 = str2[2].split("[(]");
                    str2 = str2[0].split("[/]");
                    out.print(str2[0] + "\n");
                }
            }
            year++;
        }
        out.close();
    }    
}
