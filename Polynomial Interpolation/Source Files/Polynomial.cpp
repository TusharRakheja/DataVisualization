/*
 * Implementations for methods in the Polynomial class.
 */

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
	Gauss(); Jordan();       					            // Call the functions to perform Gauss-Jordan elimination to get a_i.
	Plot();
}

void Polynomial::Gauss() {
	for (int i = 0; i < n; i++) {
		float divisor = matrix[i][i];
		for (int j = i; j <= n; j++) {						
			matrix[i][j] /= divisor;				    // Divide matrix(i,j) for j in {0,...,n} by matrix(i,0).
		}
		for (int row = i + 1; row < n; row++) {		                    // Refers to the subsequent rows.
			float multiplier = matrix[row][i];
			for (int j = 0; j <= n; j++) {				    // Refers to the columns (of each row).
				matrix[row][j] -= (matrix[i][j] * multiplier);	    // matrix(row) --> matrix(row) - matrix(i) * matrix(row, 0). 	
			}
		}
	}
}

void Polynomial::Jordan() {						
	for (int i = n - 1; i >= 0; i--) {
		for (int row = i - 1; row >= 0; row--) {
			float multiplier = matrix[row][i];		 	    // Since in row 'i' of the matrix, the only non-zero element left is 1.
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

void Polynomial::Plot() {
	bool quit = false;
	SDL_Event event;
	SDL_Window *w = nullptr;
	SDL_Renderer *r = nullptr;
	SDL_Init(SDL_INIT_VIDEO);
	SDL_SetHint(SDL_HINT_RENDER_DRIVER, "opengl");
	w = SDL_CreateWindow("Polynomial", 80, 31, 1200, 685, 0);			// Half screen plot.
	r = SDL_CreateRenderer(w, -1, SDL_RENDERER_ACCELERATED);
	SDL_SetRenderDrawColor(r, 255, 255, 255, SDL_ALPHA_OPAQUE);        // Clear screen to white color.
	SDL_RenderClear(r);
	SDL_SetRenderDrawColor(r, 0, 0, 0, 50);
	SDL_RenderDrawLine(r, 0, 685-30, 1190, 685-30);
	SDL_RenderDrawLine(r, 30, 0, 30, 685);
	SDL_RenderPresent(r);
	float increment = 0.001f;											// The curve's resolution.
	while (!quit) {
		while (SDL_PollEvent(&event)) {								
			if (event.type == SDL_QUIT) {
				quit = true;
			}
		}
	}
}

int main(int argc, char **argv) {
	Polynomial p("Data.txt");
	return 0;
}