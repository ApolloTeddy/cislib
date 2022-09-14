package CiSlib;

public class CNum {
	private final float TAU = (float)Math.PI*2;
	protected float re, im, th, am;
	
	protected CNum(float[] info) {
		this.re = info[0]; this.im = info[1];
		this.th = info[2]%TAU; this.am = info[3]*info[3];
	}
	
	private void reCalcPol() { this.th = (float)Math.atan2(this.im, this.re); this.am = this.re*this.re+this.im*this.im; }
	
	private void reCalcCar() { 
		float am = (float)Math.sqrt(this.am);
		this.re = am*(float)Math.cos(this.th); this.im = am*(float)Math.sin(this.th); 
	}
	
	public void add(CNum b) {
		this.re += b.re; this.im += b.im;
		reCalcPol();
	}
	
	public void sub(CNum b) {
		this.re -= b.re; this.im -= b.im;
		reCalcPol();
	}
	
	public void mult(CNum b) {
		this.th = (this.th+b.th)%TAU; this.am *= b.am;
		reCalcCar();
	}
	
	public void div(CNum b) {
		this.th = (this.th-b.th)%TAU; this.am /= b.am;
		reCalcCar();
	}
	
	public float[] get() { return new float[]{this.re, this.im, this.th, (float)Math.sqrt(this.am)}; }
	
	public void setP(float th, float am) {
		this.th = th%TAU; this.am = am*am;
		reCalcCar();
	}
	
	public void setC(float re, float im) {
		this.re = re; this.im = im;
		reCalcPol();
	}
}