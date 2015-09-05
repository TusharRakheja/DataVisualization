#ifndef POLYNOMIAL_H
#define POLYNOMIAL_H

#include <fstream>
#include <iostream>
#include <string>

using namespace std;

class Polynomial {
private:
	struct Matrix {								// Used to store the augmented matrix for the data set.
		float *column;
	};
	Matrix *row;								// The augmented matrix.
	int n;									// The number of data points (= degree of polynomial).
	void Gauss(Matrix *);							// Performs Gaussian elimination on the augmented matrix.
	void Jordan(Matrix *);						// Converts the REF Matrix obtained from Gauss() to RREF.
public:
	Polynomial(string);							// Gets the data set from the filename (string) and generates the augmented matrix.
};

#endif