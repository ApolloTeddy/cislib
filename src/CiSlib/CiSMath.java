package CiSlib;

public class CiSMath {
	public static final CNum i = fromCart(0, 1);
	
	public static CNum add(CNum a, CNum b) {
		return fromCart(a.re + b.re, a.im + b.im);
	}

	public static CNum sub(CNum a, CNum b) {
		return fromCart(a.re - b.re, a.im - b.im);
	}

	public static CNum mult(CNum a, CNum b) {
		return fromPolar(a.th + b.th, a.am * b.am);
	}

	public static CNum div(CNum a, CNum b) {
		return fromPolar(a.th - b.th, a.am / b.am);
	}
	
	public static CNum rootOfUnity(int root) {
		return fromPolar((2*(float)Math.PI)/root, 1);
	}
	
	public static CNum fromPolar(float th, float am) {
		return new CNum(new float[]{am*(float)Math.cos(th), am*(float)Math.sin(th), th, am});
	}

	public static CNum fromCart(float re, float im) {
		return new CNum(new float[]{re, im, (float)Math.atan(im/re), (float)Math.sqrt(re*re+im*im)});
	}
}