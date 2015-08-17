# file is the path of the file containing the dolphin association data.

plotDistribution <- function(file) {
     frame <- read.table(file, sep = "\t", colClasses = "numeric")
     attributes(frame)$names <- c("Network Size (#Associations)", "Frequency")
     plot(frame)
}