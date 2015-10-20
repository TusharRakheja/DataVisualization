# Polynomial Interpolation.

For given _n_ data points on a 2-D plane, the program computes an (_n_-_1)_-dimensional polynomial fitting the data set.

For a set of points S = { (x<sub>_i_</sub>, y<sub>_i_</sub>) | i _in_ {1, ... , n} }: 

* It assumes a polynomial **p**(x) = **a**<sub>_n-1_</sub>**x**<sup>_n-1_</sup> + **a**<sub>_n-2_</sub>**x**<sup>_n-2_</sup> + ... + **a**<sub>_1_</sub>**x** + **a**<sub>_0_</sub>.

* Generates _n_ equations using **p**(x<sub>_i_</sub>) = y<sub>_i_</sub>, in _n_ variables (The coefficients {**a**<sub>_j_</sub>}).

* Finally, it uses Gauss-Jordan elimination to determine the coefficients.

* Then it plots the computed polynomial in an SDL window. 

**Sample plot** for S = {(0,1), (1,2), (2,1), (3,0), (4,1), (5,2), (6,1), (7,0), (8,1), (9,2), (10,1), (11,0)} [Shifted sine curve].

<img src="https://github.com/TusharRakheja/DataVisualization/blob/master/Polynomial%20Interpolation/Images/Sample%20Plot.png" width="250">

**Interesting: ** For _n_ points on the sine curve, the polynomial computed should be a Taylor series of the curve upto _n-1_ degrees. 

##### To do:

* Label the gridlines with x and y-values. 

* Handle mouse-events. 

* An interface to enter data points in real time (needs a preset file for now).   

## Dependencies

Made in Visual Studio 2013, but should port quite easily. Compilation requires SDL2 to be set up.

