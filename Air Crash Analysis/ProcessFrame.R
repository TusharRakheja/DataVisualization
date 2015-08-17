# Should be in the same directory as Output.txt and Output.csv 
# (or alternatively, the path should be pre-entered)

# Returns a data frame mapping deaths in various accidents to the years of those
# respective accidents.

processFrame <- function(file) { 
     frame <- read.table(file, sep = "\t")
     attributes(frame)$names <- c("Flight dates", "Deaths")
     frame[, 1] <- as.Date(frame[, 1], "%d-%b-%Y")   # Convert to Date vs Deaths.
     frame[, 1] <- format(frame[, 1], "%Y")          # Convert to Year vs Deaths.
     frame[, 2] <- as.character(frame[, 2])
     frame[, 2] <- as.numeric(frame[,2])
     good <- !is.na(frame[, 2])                      # Remove data with NA values.
     frame <- frame[good, ]
     frame
}