# AirCrashAnalysis
Programming Assignment for Data Visualization by UIUC on Coursera.

* MiningData.java accesses an online database (http://www.planecrashinfo.com/database.htm) and obtains the needed data. Exports a .csv file. 

* The file is then supposed to be cleaned up in Excel. 

* ProcessFrame.R then converts the date vs. deaths data to year-only vs. deaths data. 

* Output.csv contains only the #deaths column from the original .csv file. 

* Output.txt is supposed to contain that as well as the dates corresponding to each incident, except that I couldn't get it to work properly in R when both these values are included. Therefore, I only used the dates from output.txt (from the original .csv file as well, of course). 

* Finally, DeathsPerYear.R maps each year to the number of deaths in that year in a matrix, which is exported as yet another .csv file.

* Then we make the scatter plot - Historical Air-Safety Data.png

## About the quality of the database

It's pretty good. The data seems to be accurate, and even somewhat comprehensive. The scatter plot does look quite a bit like this one - https://en.wikipedia.org/wiki/Aviation_accidents_and_incidents#/media/File:ACRO_fatalities_1918-2009.svg.