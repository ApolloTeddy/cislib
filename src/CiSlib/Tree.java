package CiSlib;

import java.util.ArrayList;

public class Tree {
  double[]boundingSquare;
  int capacity, elementCount;
  CNum[]elements;
  Tree[]subs = new Tree[4];
  
  
  public Tree(double[]rectBounds, int cap) {
	boundingSquare = rectBounds;
	elements = new CNum[cap];
	elementCount = 0;
	capacity = cap;
  }
  protected Tree(double x, double y, double h, int cap) {
    this(new double[] { x, y, h }, cap);
  }
  
  protected Tree buildTree(ArrayList<CNum> party) {
    reset();
    for(CNum member : party) insert(member);
    return this;
  }
  protected Tree buildTree(CNum[] party) {
    reset();
    for(CNum member : party) insert(member);
    return this;
  }
  
  protected void reset() {
    elements = new CNum[capacity];
    elementCount = 0;
    subs = new Tree[4];
  }
  
  protected CNum[] queryC(CNum pos, double r) {
    return queryC(pos.re, pos.im, r);
  }
  protected CNum[] queryC(double[]circle) {
    return queryC(circle[0], circle[1], circle[2]);
  }
  protected CNum[] queryC(double x, double y, double r) {
	boolean sub = subs[0] == null;
    if( (elementCount == 0 && sub) || !Circle.intersectsSquare(x, y, r, boundingSquare[0], boundingSquare[1], boundingSquare[2])) return null;
    ArrayList<CNum> out = new ArrayList<CNum>();
    
    if(sub) {
      for(int i = 0; i < elementCount; i++) 
        if(Circle.containsParticle(x, y, r, elements[i])) 
          out.add(elements[i]);
      return out.toArray(new CNum[out.size()]);
    }
    
    for(Tree s : subs) s.queryC(x, y, r, out);
    
    return out.toArray(new CNum[out.size()]);
  }
  private void queryC(double x, double y, double r, ArrayList<CNum> out) {
	  boolean sub = subs[0] == null;
	    if( (elementCount == 0 && sub) || !Circle.intersectsSquare(x, y, r, boundingSquare[0], boundingSquare[1], boundingSquare[2])) return;
	    
	    if(sub) {
	      for(int i = 0; i < elementCount; i++) 
	        if(Circle.containsParticle(x, y, r, elements[i])) 
	          out.add(elements[i]);
	      return;
	    }
	      
	    for(Tree s : subs) s.queryC(x, y, r, out);
  }
  
  protected CNum[] queryS(CNum pos, double h) {
    return queryS(pos.re, pos.im, h);
  }
  protected CNum[] queryS(double[]square) {
    return queryS(square[0], square[1], square[2]);
  }
  protected CNum[] queryS(double x, double y, double h) {
	boolean sub = subs[0] == null;
    if( (elementCount == 0 && sub) || !Square.intersectsSquare(x, y, h, boundingSquare[0], boundingSquare[1], boundingSquare[2]) ) return null;
    ArrayList<CNum> out = new ArrayList<CNum>();
    
    if(sub) {
      for(int i = 0; i < elementCount; i++) 
        if(Square.containsParticle(x, y, h, elements[i])) 
          out.add(elements[i]);
      return out.toArray(new CNum[out.size()]);
    }
    
    for(Tree s : subs) s.queryS(x, y, h, out);
    
    return out.toArray(new CNum[out.size()]);
  }
  private void queryS(double x, double y, double h, ArrayList<CNum> out) {
	boolean sub = subs[0] == null;
    if( (elementCount == 0 && sub) || !Square.intersectsSquare(x, y, h, boundingSquare[0], boundingSquare[1], boundingSquare[2])) return;
    
    if(sub) {
      for(int i = 0; i < elementCount; i++) 
        if(Square.containsParticle(x, y, h, elements[i])) 
          out.add(elements[i]);
      return;
    }
      
    for(Tree s : subs) s.queryS(x, y, h, out);
  }
  
  public boolean insert(CNum member) {
    if(!Square.containsParticle(boundingSquare, member)) return false;
    
    if(elementCount < capacity && subs[0] == null) {
      elements[elementCount] = member;
      elementCount++;
      return true;
    }
    
    if(subs[0] == null) subdivide();
    
    for(Tree sub : subs) 
      if(sub.insert(member)) return true;
    
    return false;
  }
  
  protected int size() {
	  return queryS(boundingSquare).length;
  }
  
  public CNum[] all() {
	  return queryS(boundingSquare);
  }
  
  protected void subdivide() {
    subs[0] = new Tree(boundingSquare[0] - boundingSquare[2]/2, boundingSquare[1] + boundingSquare[2]/2, boundingSquare[2]/2, capacity);
    subs[1] = new Tree(boundingSquare[0] + boundingSquare[2]/2, boundingSquare[1] + boundingSquare[2]/2, boundingSquare[2]/2, capacity);
    subs[2] = new Tree(boundingSquare[0] - boundingSquare[2]/2, boundingSquare[1] - boundingSquare[2]/2, boundingSquare[2]/2, capacity);
    subs[3] = new Tree(boundingSquare[0] + boundingSquare[2]/2, boundingSquare[1] - boundingSquare[2]/2, boundingSquare[2]/2, capacity);
    
    for(CNum ele : elements) {
      for(Tree sub : subs) {
        if(sub.insert(ele)) break;
      }
    }
    elements = null;
    elementCount = 0;
  }
}