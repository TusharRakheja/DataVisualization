/**
 * This file contains code for mining data from http://planecrashinfo.com/
 * No API found, so, URL and sample-HTML-code trickery employed.
 * 
 * I'll make my own API then.
 * Performance note: An implementation using ArrayList seems nearly twice as fast as that using LinkedList.
 * 
 * @author Tushar
 */

import java.net.URL;
import java.util.*;
import java.io.*;

public class Data {
    private static final String urlFeed = new String("http://planecrashinfo.com/");
    private Integer start, end;
    public Data() {
        start = 1920; 
        end = 2015;
    }
    /**
     * Set a starting and ending year for the dataset.
     * 
     * @param first
     * @param last
     */
    public void setInterval(int first, int last) {
        this.start = first;
        this.end = last;
    }
    /**
     * Start retrieving data. Default filename is "Output".
     * Extensions is .tsv and unchangeable.
     * 
     * @param filename  filename (optional)
     * @throws IOException 
     */
    public void retrieve(String... filename) throws IOException {        // Either specify a filename or go with the default.
        Integer year = start;
        File output;                                                     // Declare a new File variable.
        if (filename.length > 0) output = new File(filename[0] + ".tsv");          
        else output = new File("Output.tsv");                       
        if(output.exists()) {
            System.out.print("Overwriting: ");
        }
        else {
           output.createNewFile();                                       // Create Output.tsv (or <filename>.tsv)
        }
        FileWriter o = new FileWriter(output);                          
        PrintWriter out = new PrintWriter(o);
        out.print("Date\t" + "\tLocation\t" + "Operator\t" + "Fatalities\t" + "On-board\n");
        while (year <= end) {
            String tempAdd = new String();
            Scanner readData;
            tempAdd = year.toString() + "/" + year.toString() + ".htm";
            URL url = new URL(urlFeed + tempAdd);
            readData = new Scanner(url.openStream());
            ArrayList<String> data = new ArrayList<>();                  // Stores raw html doc.
            while (readData.hasNext()) {
                data.add(readData.nextLine());
            }            
            boolean readingLocOp = false;                                // Location and operator are read immediately after date.
            for (String str : data) {
                if (str.contains(".htm\">") && !readingLocOp) {          // Reading date (Found by going through sample data).
                    String[] date = str.split(">");
                    out.print(date[3].substring(0, 11) + "\t");
                    readingLocOp = true;
                }
                else if (readingLocOp) {
                    String[] str2 = str.split("<br>");                   // Most locations are broken up into two parts.
                    String operator = "";
                    boolean operatorExists = false;
                    if (str2.length != 1) {
                        operatorExists = true;
                        operator = operator.trim();
                        operator = str2[1];
                    }
                    str2 = str2[0].split(">");                           // We need the first part. Split into 3.
                    str2 = str2[2].split(",");                           // Get the 3rd. 
                    for (int i = 0; i < str2.length - 1; i++) {          // Combine all other pieces of data into one block.
                        str2[i] = str2[i].trim();
                        if (!str2[i].equals("")) {
                            out.print(str2[i]);
                        }                       
                        if (i != str2.length - 2) out.print(" , ");
                    }
                    out.print("\t" + str2[str2.length-1].trim() + "\t"); // Keep the most general location distinct.
                    out.print(operator.trim() + "\t");
                    readingLocOp = false;
                }
                else if (str.contains("width=\"1\"") && !readingLocOp) { // Distinguishing feature of fatalities.
                    String[] str2 = str.split("width=\"1\"");
                    str2 = str2[1].split(">");
                    str2 = str2[2].split("[(]");
                    String[] fatalities = str2[0].split("[/]");
                    out.print(fatalities[0] + "\t");
                    fatalities = fatalities[1].split("<");
                    out.print(fatalities[0] + "\n");                     // On-board passengers.                    
                }
            }
            year++;
        }
        out.close();
    }    
    public static void main(String[] args) throws IOException {          // Unit testing. 
        Integer start, end;
        String response;
        Scanner input = new Scanner(System.in);
        Data data = new Data();
        System.out.print("Enter the first year for the dataset: ");
        start = input.nextInt();
        System.out.print("Enter the last year for the dataset: ");
        end = input.nextInt();
        data.setInterval(start, end);
        System.out.print("Do you want to enter a filename? Enter 'Y' or 'N': ");
        response = input.nextLine(); 
        response = response.trim();
        while (!response.equals("Y") && !response.equals("N")) {
            System.out.print("Please enter either 'Y' or 'N' only: ");
            response = input.nextLine();
            response = response.trim();
        }
        if (response.equals("Y")) {
            String filename;
            System.out.print("Enter the filename (no whitespace): ");
            filename = input.nextLine(); 
            filename = filename.trim();
            System.out.print("Fetching data: ... ");
            data.retrieve(filename);
        }
        else {
            System.out.print("Fetching data: ... ");
            data.retrieve();
        }
    }
}
