package CiSlib;

public class Square {
	  // Represented as: x, y, h
	  
	static boolean containsParticle(double sx, double sy, double h, CNum p) {
		if(Math.abs(p.re - sx) > h || Math.abs(p.im - sy) > h) return false;
		return true;
	}
	static boolean containsParticle(double[]square, CNum p) {
		return containsParticle(square[0], square[1], square[2], p);
	}

	static boolean intersectsSquare(double x1, double y1, double h1, double x2, double y2, double h2) {
		if(Math.abs(x1 - x2) > h1 + h2 || Math.abs(y1 - y2) > h1 + h2) return false;
		return true;
	}
	static boolean intersectsSquare(double[]square1, double[]square2) {
		return intersectsSquare(square1[0], square1[1], square1[2], square2[0], square2[1], square2[2]);
	}
}