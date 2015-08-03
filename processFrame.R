processFrame <- function() {
     frame <- read.table("output.txt", sep = "\t")
     frame <- frame[, !names(frame) == "V3"]
     frame <- frame[!attributes(frame)$row.names == 1, ]
     attributes(frame)$names <- c("Flight dates", "Deaths")
     frame[, 1] <- as.Date(frame[, 1], "%d-%b-%Y")
     frame[, 1] <- format(frame[, 1], "%Y")
     frame2 <- read.csv("output.csv", colClasses = "numeric")
     frame[, 2] <- frame2[,1]
     frame
}