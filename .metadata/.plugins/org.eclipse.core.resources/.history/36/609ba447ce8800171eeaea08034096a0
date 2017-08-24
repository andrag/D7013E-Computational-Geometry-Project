package algorithm;

import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

public class Endpoint implements Comparable<Endpoint>, Serializable{

	private int x, y;
	private int realY;
	public boolean isUpper, isLower= false;
	public boolean isIntersection = false; 
	private Edge segment = null;
	private ArrayList<Edge> intersectingEdges = null;
	public static final int NONE = 0, POLYGON1 = 1, POLYGON2 = 2;
	private int belongsTo;

	private Edge nextSegment = null;
	private Edge previousSegment = null;
	public ArrayList<Edge> isUpperTo;
	public ArrayList<Edge> isLowerTo;
	
	public Edge leftmost, rightmost = null;

	/**
	 * Constructor
	 * @param x
	 * @param y
	 */
	public Endpoint(int x, int y){
		this.x = x;
		this.y = y;
		realY = 400-y;
		isLowerTo = new ArrayList<Edge>();
		isUpperTo = new ArrayList<Edge>();
	}

	public void addUpperTo(Edge segment){
		isUpperTo.add(segment);
	}

	public void addLowerTo(Edge segment){
		isLowerTo.add(segment);
	}

	public ArrayList<Edge> getUpperTo(){
		return isUpperTo;
	}

	public ArrayList<Edge > getLowerTo(){
		return isLowerTo;
	}
	
	public void clearSegmentsInUpperTo(){
		isUpperTo.clear();
	}


	public int getX(){
		return x;
	}

	public int getY(){
		return y;
	}

	public int getRealY(){
		return 400-y;
	}

	public void setNextSeg(Edge segment){
		nextSegment = segment;
	}

	public void setPrevSeg(Edge segment){
		previousSegment = segment;
	}

	public Edge getNextSeg(){
		return nextSegment;
	}

	public Edge getPrevSeg(){
		return previousSegment;
	}

	public void paintPoint(Graphics g){
		g.fillRect(x, y, 3, 3);
	}

	public void setUpper(){
		isUpper = true;
		isLower = false;
	}

	public void setLower(){
		isLower = true;
		isUpper = false;

	}

	public boolean isUpper(){
		return isUpper;
	}
	public boolean isLower(){
		return isLower;
	}

	public void setCoords(int x, int y){
		this.x = x;
		this.y = y;
	}

	public Edge getSegment(){
		return segment;
	}

	public void setSegment(Edge segment){
		this.segment = segment;
	}

	public ArrayList<Edge> getIntersectingEdges(){
		return intersectingEdges;
	}

	public void setIntersectingEdges(ArrayList<Edge> edges){
		intersectingEdges = edges;
	}


	public boolean isIntersection(){
		return isIntersection;
	}

	public void setIntersection(boolean set){
		isIntersection = set;
	}

	/**
	 * Sets which polygon this endpoint belongs to
	 */
	public void setBelonging(int polygon){
		if(polygon == 0 || polygon == 1 || polygon == 2){
			belongsTo = polygon;
		}
	}

	public int getBelonging(){
		return belongsTo;
	}

	/**
	 * Compare the x and y coordinates of this endpoint to a provided endpoint o
	 */
	@Override
	public int compareTo(Endpoint o) {
		// TODO Auto-generated method stub
		if(realY<o.getRealY() || realY==o.getRealY() && x>o.getX()){//This is smaller than o
			return -1;
		}
		else if(realY==o.getRealY()&&x==o.getX()){//Equals
			return 0;
		}
		else return 1;
	}
	
	

	public boolean isEqualTo(Endpoint p) {
		if(x == p.getX() && y == p.getY()) return true;
		return false;
	}
	
	public boolean upperToArrayContains(Edge segment)
	{
		for(Edge e : isUpperTo)
		{
			if(segment.id == e.id)
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean lowerToArrayContains(Edge segment)
	{
		for(Edge e : isLowerTo)
		{
			if(segment.id == e.id)
			{
				return true;
			}
		}
		return false;
	}

	
	/**
	 * For finding out which side of a segment point lies
	 * Returns: 
	 * 	-1 for leftside
	 *  0 for on the line
	 *  1 for right side
	 */
	public int isToRightOrLeftOf(Edge segment){

		int det = (segment.getLower().getX()-segment.getUpper().getX())*(getRealY()-segment.getUpper().getRealY()) - (segment.getLower().getRealY()-segment.getUpper().getRealY())*(getX()-segment.getUpper().getX());
		if(det<0){
			return -1;
		}
		else if(det>0){
			return 1;
		}

		return 0;
	}


	
	/**
	 *  Find the leftmost segment that has this Enpoint as an upper point.
	 */
	public Edge findLeftmost(){
		Edge left = isUpperTo.get(0);
		for(Edge e : isUpperTo){
			if(e.getLower().isToRightOrLeftOf(left)<0){
				left = e;
			}
		}
		leftmost = left;
		return left;
	}

	/**
	 *  Find the rightmost segment that has this Enpoint as an upper point.
	 */
	public Edge findRightmost(){
		Edge right = isUpperTo.get(0);
		for(Edge e : isUpperTo){
			if(e.getLower().isToRightOrLeftOf(right)>0){
				right = e;
			}
		}
		rightmost = right;
		return right;
	}


}
