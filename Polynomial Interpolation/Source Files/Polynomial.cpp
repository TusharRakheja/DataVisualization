#include "Polynomial.h"
#include <cmath>
#include <iomanip>

#define withZeros {}								    // Just for fun, hehe.

Polynomial::Polynomial(string filename) {
	fstream *file = new fstream(filename);
	string line;
	int count = 0;
	while (getline(*file, line, '\n')) count++;				    // Count the number of points ..
	this->n = count;							    // .. and assign that value to n.
	file->clear();								    // Clear eol bit.
	file->seekp(0L, ios::beg);						    // Go to the beginning of the file. We can start reading data again now.
	matrix = new Matrix[n];							    // Make an array of rows.
	for (int i = 0; i < n; i++) {
		matrix[i].column = new float[n + 1] withZeros;			    // Set up a row containing all zeros.
		float x_i, y_i;
		*file >> x_i >> y_i;						    // Read one data point.
		for (int j = 0; j < n; j++) {
			matrix[i][j] = pow(x_i, j);				    // \sum{(a_j) * ((x_i) ^ j)}, from j = 0 to j = n-1.
		}
		matrix[i][n] = y_i;						    // y_i. Now, this row of the augmented matrix is complete. 
	}
	file->close();
	delete file;								    // Not deleting the file, just the fstream object.
	cout << *this << endl;
	Gauss();        							    // Call the functions to perform Gauss-Jordan elimination to get a_i.
}

void Polynomial::Gauss() {
	for (int i = 0; i < n; i++) {
		float divisor = matrix[i][i];
		for (int j = i; j <= n; j++) {						
			matrix[i][j] /= divisor;				    // Divide matrix(i,j) for j in {0,...,n} by matrix(i,0).
		}
		//cout << *this << endl;
		for (int row = i + 1; row < n; row++) {		                    // Refers to the subsequent rows.
			float multiplier = matrix[row][i];
			for (int j = 0; j <= n; j++) {				    // Refers to the columns (of each row).
				matrix[row][j] -= (matrix[i][j] * multiplier);	    // matrix(row) --> matrix(row) - matrix(i) * matrix(row, 0). 	
			}
			//cout << *this << endl;
		}
	}
	Jordan();
}

void Polynomial::Jordan() {						
	for (int i = n - 1; i >= 0; i--) {
		for (int row = i - 1; row >= 0; row--) {
			float multiplier = matrix[row][i];		 // Since in row 'i' of the matrix, the only non-zero element left is 1.
			matrix[row][i] -= multiplier;
			matrix[row][n] -= multiplier;
		}
	}
	cout << *this;
}

ostream& operator<<(ostream &out, const Polynomial &p) {
	for (int i = 0; i < p.n; i++) {
		for (int j = 0; j <= p.n; j++) {
			out << setw(8) << fixed << setprecision(3) << p.matrix[i][j] << " ";
		}
		out << endl;
	} 
	return out;
}

int main() {
	Polynomial p("Data.txt");
	return 0;
}