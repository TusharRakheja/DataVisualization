# Should be in the same directory as Output.txt and Output.csv 
# (or alternatively, the path should be pre-entered)

# Returns a data frame mapping deaths in various accidents to the years of those
# respective accidents.

processFrame <- function() { 
     frame <- read.table("Output.txt", sep = "\t")  
     frame <- frame[, !names(frame) == "V3"]         # Delete extra column.
     frame <- frame[!attributes(frame)$row.names == 1, ]
     attributes(frame)$names <- c("Flight dates", "Deaths")
     frame[, 1] <- as.Date(frame[, 1], "%d-%b-%Y")   # Convert to Date vs Deaths.
     frame[, 1] <- format(frame[, 1], "%Y")          # Convert to Year vs Deaths.
     frame2 <- read.csv("Output.csv", colClasses = "numeric")
     frame[, 2] <- frame2[,1]
     frame
}