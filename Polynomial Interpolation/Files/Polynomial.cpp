/*
 * Implementations for methods in the Polynomial class.
 */

#include "Polynomial.h"
#include <cmath>
#include <iomanip>
#include <SDL.h>
#include <SDL_ttf.h>

#define withZeros {}							// Just for fun, hehe.
#define SC_HEIGHT 685
#define SC_WIDTH 1200
#define SC_XOFFSET 80
#define SC_YOFFSET 31

/*
 * Maps a pixel to its corresponding x value.
 * Helper function for Polynomial::Plot().
 */
double xAtPixel(int pixel, double hx, double lx) {	
	return (pixel*(hx - lx) + (SC_WIDTH*lx - 30 * hx)) / (SC_WIDTH - 30);
}


/*
 * Maps a y value to its corresponding pixel.
 * Helper function for Polynomial::Plot().
 */
int pixelAtY(double y, double hy, double ly) {
	return (SC_HEIGHT - 30)*(hy - y) / (hy - ly);
}

Polynomial::Polynomial(string filename) {
	fstream *file = new fstream(filename);
	int count = 0;
	string line;
	while (getline(*file, line, '\n')) count++;			// Count the number of points ..
	this->n = count;						// .. and assign that value to n.
	file->clear();							// Clear eof bit.
	file->seekp(0L, ios::beg);					// Go to the beginning of the file. We can start reading data again now.
	matrix = new Matrix[n];						// Make an array of rows.
	for (int i = 0; i < n; i++) {
		matrix[i].column = new float[n + 1] withZeros;		// Set up a row containing all zeros.
		float x_i, y_i;
		*file >> x_i >> y_i;
		//cout << x_i << " " << y_i << endl;
		if (i == 0) {
			hx = lx = x_i;
			hy = ly = y_i;
		}
		else {
			if (x_i > hx) hx = x_i; 
			if (x_i < lx) lx = x_i;
			if (y_i > hy) hy = y_i;
			if (y_i < ly) ly = y_i;
		}
		for (int j = 0; j < n; j++) {
			matrix[i][j] = pow(x_i, j);			// \sum{(a_j) * ((x_i) ^ j)}, from j = 0 to j = n-1.
		}
		matrix[i][n] = y_i;					// y_i. Now, this row of the augmented matrix is complete. 
	}
	file->close();
	delete file;							// Not deleting the file, just the fstream object.
	Gauss(); Jordan();       					// Call the functions to perform Gauss-Jordan elimination to get a_i.
	cout << *this;
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
			float multiplier = matrix[row][i];		// Since in row 'i' of the matrix, the only non-zero element left is 1.
			matrix[row][i] -= multiplier;
			matrix[row][n] -= multiplier*matrix[i][n];
		}
	}
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
	w = SDL_CreateWindow("Polynomial", SC_XOFFSET, SC_YOFFSET, SC_WIDTH, SC_HEIGHT, 0);	
	r = SDL_CreateRenderer(w, -1, SDL_RENDERER_ACCELERATED);
	SDL_SetRenderDrawColor(r, 255, 255, 255, SDL_ALPHA_OPAQUE);        	   // Make the screen white.
	SDL_RenderClear(r);						
	double xScale = (SC_WIDTH - 30) / (hx - lx);
	double yScale = (SC_HEIGHT - 30) / (hy - ly);
	SDL_SetRenderDrawColor(r, 0, 0, 0, 50);
	SDL_RenderDrawLine(r, 0, SC_HEIGHT-30, SC_WIDTH, SC_HEIGHT-30);	   	   // X axis.
	SDL_RenderDrawLine(r, 30, 0, 30, SC_HEIGHT);				   // Y-axis.
	SDL_SetRenderDrawColor(r, 150, 150, 150, SDL_ALPHA_OPAQUE);
	for (double i = 30 + 30; i < SC_WIDTH; i += 30) {			   // Set up the vertical grid-lines.
		SDL_RenderDrawLine(r, i, 0, i, SC_HEIGHT);
	}
	for (double j = 30 + 30; j < SC_HEIGHT; j += 30) {			   // Set up the horizontal grid-lines.
		SDL_RenderDrawLine(r, 0, SC_HEIGHT - j, SC_WIDTH, SC_HEIGHT - j);
	}
	SDL_SetRenderDrawColor(r, 255, 0, 0, SDL_ALPHA_OPAQUE);			   // The curve will be red in color.	
	SDL_Rect point;
	for (int pixel = 30; pixel <= SC_WIDTH; pixel++) {
		double x = xAtPixel(pixel, hx, lx);
		point.x = pixel;
		point.y = pixelAtY(this->of(x), hy, ly);
		point.w = point.h = 2;
		SDL_RenderFillRect(r, &point);					   // Color point.
	}
	SDL_RenderPresent(r);
	while (!quit) {
		while (SDL_PollEvent(&event)) {								
			if (event.type == SDL_QUIT) {
				quit = true;
			}
		}
	}
	SDL_DestroyRenderer(r);							  // Clean up allocated SDL resources.
	SDL_DestroyWindow(w);
	SDL_Quit();
}

int main(int argc, char **argv) {
	Polynomial p("Data.txt");
	return 0;
}