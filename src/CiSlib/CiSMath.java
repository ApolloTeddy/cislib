package CiSlib;

import java.util.ArrayList;
import java.util.Arrays;

public class CiSMath {
	public static final CNum i = fromCart(0, 1);
	private static final double TAU = Math.PI * 2;
	
	public CNum[] GSP(CNum[] points, int iterations) {
		Tree tree = new Tree(boundingSq(points), 10);
		System.out.println("Setup Complete.");
		ArrayList<CNum[]> groups = makeGroups(tree.buildTree(points), iterations);
		System.out.println("Step 1 Complete.");
		shiftArrays(groups, tree);
		System.out.println("Step 2 Complete.");
		return connectionFunction(groups, tree);
	}
	
	private static boolean contains(CNum[] arr, CNum c) {
		for(CNum v : arr) if(v.re == c.re && v.im == c.im) return true;
		return false;
	}
	private static boolean contains(ArrayList<CNum> arr, CNum c) {
		for(CNum v : arr) if(v.re == c.re && v.im == c.im) return true;
		return false;
	}
	
	private static CNum[] connectionFunction(ArrayList<CNum[]> groups, Tree tree) {
		CNum[] path = new CNum[len(groups)];
		CNum[] all = tree.all();
		
		for(int i = 0; i < groups.get(0).length; i++) path[i] = groups.get(0)[i];
		groups.remove(0);
		
		for(int i = 0; i < groups.size(); i++) {
			CNum closestPartToPath = closestInOtherGroup(path, groups.get(i), tree), closestPartToAll = closestInOtherGroup(all, groups.get(i), tree);
			if(closestPartToPath == closestPartToAll) {
				insert(path, groups.get(i), getInd(path, closestPartToAll));
				groups.remove(i);
				i--;
			}
		}
		System.out.println("Step 3 Complete.");
		return path;
	}
	
	private static void insert(CNum[] arrA, CNum[] arrB, int ind) {
		for(int i = ind; i < arrA.length; i++) arrA[i+arrB.length] = arrA[i].clone();
		for(int i = 0; i < arrB.length; i++) arrA[i+ind] = arrB[i];
	}
	
	private static int getInd(CNum[] arr, CNum el) {
		for(int i = 0; i < arr.length; i++) if(el.re == arr[i].re && el.im == arr[i].im) return i;
		return -1;
	}
	private static int getInd(ArrayList<CNum> arr, CNum el) {
		for(int i = 0; i < arr.size(); i++) if(el.re == arr.get(i).re && el.im == arr.get(i).im) return i;
		return -1;
	}
	
	public static int len(ArrayList<CNum[]> groups) {
		int length = 0;
		for(int i = 0; i < groups.size(); i++) for(int j = 0; j < groups.get(i).length; j++) length++;
		return length;
	}
	
	private static CNum closestInOtherGroup(CNum[] groupA, CNum[] groupB, Tree tree) {
		double recordD = Double.MAX_VALUE;
		CNum closest = fromCart(0, 0);
		
		for(int i = 0; i < groupA.length; i++) {
			double d = 0.01;
			while(true) {
				CNum[] neighbors = tree.queryC(groupA[i], d);
				for(CNum nei : neighbors) if(contains(groupB, nei)) if(d < recordD) { d = recordD; closest = nei; break; } else break;
				d += 0.01;
			}
		}
		
		return closest;
	}
	
	private static void shiftArrays(ArrayList<CNum[]> groups, Tree tree) {
		for(int n = 1; n < groups.size(); n++) { // n = 1 because the first group in the array will be the largest, which we don't want to shuffle.
			CNum curGroup[] = groups.get(n);
			int closest = 0;
			double recordD = Double.MAX_VALUE;
			for(int i = 0; i < curGroup.length; i++) {
				double d = 0.01;
				while(true) {
					CNum[] neighbors = tree.queryC(curGroup[i], d);
					for(CNum nei : neighbors) if(!contains(curGroup, nei)) if(d < recordD) { d = recordD; closest = i; break; } else break;
					d += 0.01;
				}
			}
			offsetArray(curGroup, closest);
		}
	}
	
	private static void offsetArray(CNum[] group, int amount) {
		for(int i = 0; i < group.length; i++) swapArrEle(group, i, (i + amount)%group.length);
	}
	
	private static void swapArrEle(ArrayList<CNum[]> arr, int indA, int indB) {
		CNum[] tmp = arr.get(indA).clone();
		arr.set(indA, arr.get(indB).clone());
		arr.set(indB, tmp);
	}
	private static void swapArrEle(CNum[] arr, int indA, int indB) {
		CNum tmp = arr[indA].clone();
		arr[indA] = arr[indB].clone();
		arr[indB] = tmp;
	}
	
	
	private static double[] boundingSq(CNum[] points) {
		double[] scores = new double[4];
		
		for(CNum e : points) {
			if(e.re < scores[0]) e.re = scores[0];
			if(e.im < scores[1]) e.im = scores[1];
			if(e.re > scores[2]) e.re = scores[2];
			if(e.im > scores[3]) e.im = scores[3];
		}
		
		CNum mid = CiSMath.div(CiSMath.fromCart(scores[0]+scores[2], scores[1]+scores[3]), 2);
		double dW = scores[2] - mid.re, dH = scores[3] - mid.im;
		
		return new double[] { mid.re, mid.im, dW > dH ? dW : dH };
	}
	
	public static int[] range(int start, int end) {
		int[]out = new int[end-start];
		
		for(int i = 0; i < out.length; i++) out[i] = i + start;
		
		return out;
	}
	
	public static CNum[] FSCDFT(CNum[] x_n) {
		int N = x_n.length;
		CNum[] X = new CNum[N];
		
		for(int k = 0; k < N; k++) {
			CNum tmp = new CNum(new double[]{0, 0, 0, 0});
			
			for(int n = 0; n < N; n++) {
				tmp.add(mult(x_n[n], fromPolar(-(TAU * k * n)/N, 1)));
			}
			tmp.div(N);
			
			tmp.setF(k);
			X[k] = tmp;
		}
		return X;
	}
	
	public static CNum[] DFT(CNum[] x_n) {
		int N = x_n.length;
		CNum[] X = new CNum[N];
		
		for(int k = 0; k < N; k++) {
			CNum tmp = new CNum(new double[]{0, 0, 0, 0});
			
			for(int n = 0; n < N; n++) {
				tmp.add(mult(x_n[n], fromPolar(-(TAU * k * n)/N, 1)));
			}
			
			tmp.setF(k);
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
	
	public static CNum mult(CNum a, double c) {
		return fromPolar(a.th, a.am * c);
	}

	public static CNum div(CNum a, CNum b) {
		return fromPolar(a.th - b.th, a.am / b.am);
	}
	
	public static CNum div(CNum a, double c) {
		return fromPolar(a.th, a.am / c);
	}
	
	public static CNum rootOfUnity(int root) {
		return fromPolar(TAU/root, 1);
	}
	
	public static CNum complexConjugate(CNum x) {
		return fromCart(x.re, -x.im);
	}
	
	public static CNum limitMag(CNum a, double mag) {
		double[] inf = a.get();
		return inf[3] <= mag ? a : fromPolar(inf[2], mag);
	}
	
	public static CNum setMag(CNum a, double mag) {
		return fromPolar(a.get()[2], mag);
	}
	
	public static CNum fromPolar(double th, double am) {
		return new CNum(new double[]{am*Math.cos(th), am*Math.sin(th), th, am});
	}
	
	public static boolean validVector(CNum c, double check) {
		return c.re*c.re + c.im*c.im <= check*check;
	}
	public static boolean validVector(double x, double y, double check) {
		return x*x + y*y <= check*check;
	}

	public static CNum fromCart(double re, double im) {
		return new CNum(new double[]{re, im, Math.atan2(im, re), Math.sqrt(re*re+im*im)});
	}
}