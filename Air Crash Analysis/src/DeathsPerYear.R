# Takes in a data frame mapping deaths in various accidents to the years of those
# respective accidents. 

# Returns a matrix mapping years to the number of deaths in those years.

deathsPerYear <- function(frame, startYear = 1908, endYear = 2015) {
     returnMatrix <- matrix(nrow = 0, ncol = 2)
     year <- startYear                           # Data starts.
     y <- 1
     sum <- 0
     while (year <= endYear && y <= nrow(frame)) {
          if (year == frame[y, 1]) {
               sum <- sum + as.integer(frame[y, 2])
               y <- y + 1
          } 
          else {
               returnMatrix <- rbind(returnMatrix, c(year, sum))
               year <- frame[y, 1]
               sum <- 0
          }
     }
     ## To make a data frame: retFrame <- as.data.frame(retFrame)       
     ## attributes(retFrame)$names <- c("Year", "Deaths")
     write.csv(returnMatrix, "Plot Data.csv")
     returnMatrix
}