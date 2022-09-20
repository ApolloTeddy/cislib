package CiSlib;

public class Circle {
	  // Represented as: x, y, r
	  
	  static boolean containsParticle(double cx, double cy, double r, CNum p) {
	    return CiSMath.validVector(cx - p.re, cy - p.im, r);
	  }
	  static boolean containsParticle(double[]circle, CNum p) {
	    return containsParticle(circle[0], circle[1], circle[2], p);
	  }
	  
	  static boolean intersectsCircle(double x1, double y1, double r1, double x2, double y2, double r2) {
	    return CiSMath.validVector(x1 - x2, y1 - y2, r1 + r2);
	  }
	  static boolean intersectsCircle(double[]circle1, double[]circle2) {
	    return intersectsCircle(circle1[0], circle1[1], circle1[2], circle2[0], circle2[1], circle2[2]);
	  }
	  
	  static boolean intersectsSquare(double x1, double y1, double r, double x2, double y2, double h) {
	    double dx = Math.abs(x2 - x1), dy = Math.abs(y2 - y1);
	    return CiSMath.validVector(Math.max(dx - h, 0), Math.max(dy - h, 0), r);
	  }
	  static boolean intersectsSquare(double[]circle, double[]square) {
	    return intersectsSquare(circle[0], circle[1], circle[2], square[0], square[1], square[2]);
	  }
	}
