#include "Polynomial.h"
#include <cmath>

Polynomial::Polynomial(string filename) {
	fstream *file = new fstream(filename);
	string line;
	int count = 0;
	while (getline(*file, line, '\n')) count++;		// Count the number of points ..
	this->n = count;							// .. and assign that value to n.
	file->clear();							// Clear eol bit. Ready to start reading data.
	file->seekp(0L, ios::beg);					// Go to the beginning of the file.
	row = new Matrix[n];						// Make an array of rows.
	for (int i = 0; i < n; i++) {
		row[i].column = new float[n + 1] {};		// Set up a row containing all zeros.
		float x_i, y_i;
		*file >> x_i >> y_i;					// Read one data point.
		for (int j = 0; j < n; j++) {
			row[i].column[j] = pow(x_i, j);		// \sum{(a_j) * ((x_i) ^ j)}, from j = 0 to j = n-1.
		}
		row[i].column[n] = y_i;				// y_i. Now, this row of the augmented matrix is complete. 
	}
	file->close();
	delete file;							// Not deleting the file, just the fstream object.
}

int main() {
	Polynomial p("Data.txt");
	return 0;
}