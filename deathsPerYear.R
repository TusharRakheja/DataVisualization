deathsPerYear <- function(frame) {
     retFrame <- matrix(nrow = 0, ncol = 2)
     year <- 1950
     y <- 2
     sum <- 0
     while (year <= 2015 && y <= 4584) {
          if (year == frame[y, 1]) {
               sum <- sum + as.integer(frame[y, 2])
               y <- y + 1
          } 
          else {
               retFrame <- rbind(retFrame, c(year, sum))
               year <- frame[y, 1]
               sum <- 0
          }
     }
     retFrame
}