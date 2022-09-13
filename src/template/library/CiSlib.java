package CiSlib;

import processing.core.*;

public class CNum {
	protected float re, im, th, am;
	
	protected CNum(float[4] info) {
		this.re = info[0]; this.im = info[1];
		this.th = info[2]; this.am = info[4];
	}
	
	private void reCalcPol() { this.th = atan(this.im/this.re); this.am = sqrt(this.re*this.re+this.im*this.im); }
	
	private void reCalcCar() { this.re = this.am*cos(this.th); this.im = this.am*sin(this.th); }
	
	public void add(CNum b) {
		this.re += b.re; this.im += b.im;
		reCalcPol();
	}
	
	public void sub(CNum b) {
		this.re -= b.re; this.im -= b.im;
		reCalcPol();
	}
	
	public void mult(CNum b) {
		this.th += b.th; this.am *= b.am;
		reCalcCar();
	}
	
	public void div(CNum b) {
		this.th -= b.th; this.am /= b.am;
		reCalcCar();
	}
	
	public float[] get() { return new float[]{this.re, this.im, this.th, this.am}; }
	
	public void setP(float th, float am) {
		this.th = th; this.am = am;
		reCalcCar();
	}
	
	public void setC(float re, float im) {
		this.re = re; this.im = im;
		reCalcPol();
	}
}

public CNum fromPolar(float th, float am) {
	return new CNum(new float[]{am*cos(th), am*sin(th), th, am});
}

public CNum fromCart(float re, float im) {
	return new CNum(new float[]{re, im, atan(im/re), sqrt(re*re+im*im)});
}

package CiSlib.CiSMath;

public CNum add(CNum a, CNum b) {
	return fromCart(a.re + b.re, a.im + b.im);
}

public CNum sub(CNum a, CNum b) {
	return fromCart(a.re - b.re, a.im - b.im);
}

public CNum mult(CNum a, CNum b) {
	return fromPolar(a.th + b.th, a.am * b.am);
}

public CNum div(CNum a, CNum b) {
	return fromPolar(a.th - b.th, a.am / b.am);
}