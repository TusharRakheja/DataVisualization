# AirCrashAnalysis

### Programming Assignment for Data Visualization by UIUC on Coursera. 

To reproduce the results:

* Data.java accesses an online database (http://www.planecrashinfo.com/database.htm) and obtains the needed data. Exports a .tsv file. 

* The file is then supposed to be cleaned up in Excel. Remove the extra columns and column names, leaving only the dates and the fatalities. 

* Be careful about the formatting. Date should be in a DD-MMM-YYYY format. I stored the new file in a tab-separated text file, because .tsv doesn't save the date's format.

* ProcessFrame.R then converts the date vs. deaths data to year-only vs. deaths data. 

* Finally, DeathsPerYear.R maps each year to the number of deaths in that year in a matrix, which is exported as a .csv file.

* Then we make the scatter plot - Historical Air-Safety Data.png. Either in R, or in Excel using the exported file.

## About the quality of the database

It's pretty good. The data seems to be accurate, and even somewhat comprehensive. The scatter plot does look quite a bit like this one - https://en.wikipedia.org/wiki/File:ACRO_fatalities_1918-2009.svg