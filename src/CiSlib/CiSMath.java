package CiSlib;

public class CiSMath {
	public static final CNum i = fromCart(0, 1);
	private static final double TAU = Math.PI * 2;
	
	public static CNum[] DFT(CNum[] x_n) {
		int N = x_n.length;
		CNum[] X = new CNum[N];
		
		for(int k = 0; k < N; k++) {
			CNum tmp = new CNum(new double[]{0, 0, 0, 0});
			
			for(int n = 0; n < N; n++) {
				tmp.add(mult(x_n[n], fromPolar(-(TAU * k * n)/N, 1)));
			}
			X[k] = tmp;
		}
		return X;
	}
	
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
		return fromPolar(TAU/root, 1);
	}
	
	public static CNum fromPolar(double th, double am) {
		return new CNum(new double[]{am*Math.cos(th), am*Math.sin(th), th, am});
	}

	public static CNum fromCart(double re, double im) {
		return new CNum(new double[]{re, im, Math.atan2(im, re), Math.sqrt(re*re+im*im)});
	}
}