package CiSlib;

public class CNum {
	protected double re, im, th, am, fr;
	
	protected CNum(double[] info) {
		this.re = info[0]; this.im = info[1];
		this.th = info[2]; this.am = info[3];
	}
	
	private void reCalcPol() { this.th = Math.atan2(this.im, this.re); this.am = Math.sqrt(this.re*this.re+this.im*this.im); }
	
	private void reCalcCar() { this.re = this.am*Math.cos(this.th); this.im = this.am*Math.sin(this.th); }
	
	public void add(CNum b) {
		this.re += b.re; this.im += b.im;
		reCalcPol();
	}
	
	public void sub(CNum b) {
		this.re -= b.re; this.im -= b.im;
		reCalcPol();
	}
	
	public void mult(CNum b) {
		this.th = (this.th+b.th); this.am *= b.am;
		reCalcCar();
	}
	
	public void div(CNum b) {
		this.th = (this.th-b.th); this.am /= b.am;
		reCalcCar();
	}
	
	public double[] get() { return new double[]{this.re, this.im, this.th, this.am, this.fr}; }
	
	public void setP(double th, double am) {
		this.th = th; this.am = am;
		reCalcCar();
	}
	
	public void setC(double re, double im) {
		this.re = re; this.im = im;
		reCalcPol();
	}
	
	public void setF(double fr) { this.fr = fr; }
	
	public CNum conjugate() {
		return CiSMath.fromCart(this.re, -this.im);
	}
	
	public CNum clone() { return CiSMath.fromPolar(this.th, this.am); }
}