# Takes in a data frame mapping deaths in various accidents to the years of those
# respective accidents. 

# Returns a matrix mapping years to the number of deaths in those years.

deathsPerYear <- function(frame) {
     retFrame <- matrix(nrow = 0, ncol = 2)
     year <- 1950                            # Data starts from year 1950.
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
     retFrame       # Actually a matrix. 
}