package algorithm;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

public class Endpoint implements Comparable<Endpoint>, Serializable{

	private int x, y;
	private int realY;
	public boolean isUpper, isLower= false;
	public boolean isIntersection = false; 
	private Edge segment = null;//Kanske on�dig. Kan r�cka med att ha en lista.
	//private Edge isLowerTo = null;//Endast upper b�r h�lla segments. Blir kr�ngligt annars.
	private ArrayList<Edge> intersectingEdges = null;
	public static final int NONE = 0, POLYGON1 = 1, POLYGON2 = 2;
	private int belongsTo;

	//Papa's got a brand new bag!
	private Edge nextSegment = null;
	private Edge previousSegment = null;
	private ArrayList<Edge> isUpperTo = new ArrayList<Edge>();//B�r vara en ArrayList
	private ArrayList<Edge> isLowerTo;//Ocks� ArrayList?
	
	
	public Edge leftmost, rightmost = null;






	public Endpoint(int x, int y){
		this.x = x;
		this.y = y;
		realY = 400-y;//Dubbel grej
		isLowerTo = new ArrayList<Edge>();
	}

	public void addUpperTo(Edge segment){
		isUpperTo.add(segment);
	}

	public void setLowerTo(Edge segment){
		//If this point is lower to two segments
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

	//Make points
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
	/*
	public void setEdge(Edge segment){
		if(this.isUpper){
			this.segment = segment;
		}
		else if(this.isLower){
			this.isLowerTo = segment;
		}
	}*/

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


	//Denna kanske ger nullPointerException
	public boolean isSmallerThan(Endpoint e){
		//Case 1: Check if this point is smaller than e in y or greater in x.
		if(y < e.getY() || y == e.getY() && x >e.getX()){
			return true;
		}
		//Case 2: Check if they lie on the same point

		if(y == e.getY()&&x == e.getX()){
			//Case 2.1: Prioritize upper before lower. Otherwhise lower may be deleted from status before tested for intersection with upper.
			if(this.isLower && e.isUpper){
				return true;
			}
			else if(this.isUpper&&e.isLower){
				return false;
			}

			//Case 2.2: If both are upper and lie on the same point. Sort after their lower endpoints.
			else if(this.isUpper&&!e.isUpper){//If the points are totally equal. Compare their segments lower endpoints
				if(this.segment.getLower().getY()<e.getSegment().getLower().getY()){//N�gonstans checkas x-v�rdet p� lower men y �r viktigare. �ven i statusen?
					return true;
				}
				else if(this.segment.getLower().getY() == e.getSegment().getLower().getY() && this.segment.getLower().getX()>e.getSegment().getLower().getX()){
					return true;
				}
			}
		}
		return false;
	}




	/*
	//Returns all segments this endpoint is upper to that are not duplicates of existing ones.
	public ArrayList<Edge> getUniqueSegments(Edge segment){//Borde kanske j�mf�ra punktens alla upperTo med this.upperTo. O(n^2)??? Ajajaj :/
		ArrayList<Edge> newSegments = new ArrayList<Edge>();
		for(Edge e : isUpperTo){
			if(!segmentDuplicate(e, segment)){
				newSegments.add(e);
			}	
		}
		return newSegments;
	}*/

	private boolean segmentDuplicate(Edge segment1, Edge segment2){

		//Check if the edges are the same if they have the same upper and lower.
		if(segment1.getUpper().getX()==segment2.getUpper().getX()&&segment1.getUpper().getY()==segment2.getUpper().getY()&&segment1.getLower().getX()==segment2.getLower().getX()&&segment1.getLower().getY()==segment2.getLower().getY()){
			return true;
		}
		//Check if the edges are the same if they have the different upper and lower.(Total overlap but different directions)
		//Must compare only the coordinates and not the whole segments
		else if(segment1.getUpper().getX()==segment2.getLower().getX()&&segment1.getUpper().getY()==segment2.getLower().getY()&&segment1.getLower().getX()==segment2.getUpper().getX()&&segment1.getLower().getY()==segment2.getUpper().getY()){
			return true;
		}
		return false;
	}

	public boolean isIntersection(){
		return isIntersection;
	}

	public void setIntersection(boolean set){
		isIntersection = set;
	}

	public void setBelonging(int polygon){
		if(polygon == 0 || polygon == 1 || polygon == 2){
			belongsTo = polygon;
		}
	}

	public int getBelonging(){
		return belongsTo;
	}

	//Compare this to another endpoint in Y and X
	@Override
	public int compareTo(Endpoint o) {
		// TODO Auto-generated method stub
		if(realY<o.getRealY()||realY==o.getRealY()&&x>o.getX()){//This is smaller than o
			return -1;
		}
		else if(realY==o.getRealY()&&x==o.getX()){//Equals
			return 0;
		}
		else return 1;
	}


	//Fel i nedanst�ende metod.
	/*Denna metod funkar endast f�r segment som inte korsar varandra.
	 * Anv�nds f�r insert och search i statusen. Segment som korsar varandra ska ge upphov till nya uppers innan denna beh�ver anv�ndas.
	 * Den funkar �ven f�r segment som har en gemensam upper.
	 */
	public int isToRightOrLeftOf(Edge segment){
		//Determinant stuff
		int det = (segment.getLower().getX()-segment.getUpper().getX())*(getRealY()-segment.getUpper().getRealY()) - (segment.getLower().getRealY()-segment.getUpper().getRealY())*(getX()-segment.getUpper().getX());
		if(det<0){
			return -1;
		}
		else if(det>0){
			return 1;
		}

		return 0;
	}

	//Solution from http://stackoverflow.com/questions/1560492/how-to-tell-whether-a-point-is-to-the-right-or-left-side-of-a-line
	//position = sign( (Bx-Ax)*(Y-Ay) - (By-Ay)*(X-Ax) )

	public int testNewSideMethod(Edge segment){
		int Ax = segment.getUpper().getX();
		int Ay = segment.getUpper().getRealY();
		int Bx = segment.getLower().getX();
		int By = segment.getLower().getRealY();

		int det = (Bx-Ax)*(getRealY()-Ay) - (By-Ay)*(x-Ax);
		if(det==0){
			return 0;
		}
		if(det<0){
			return -1;
		}
		else return 1;
	}

	//These are for finding the leftmost and rightmost segments of isUpperTo
	//findLeftmost and findRightmost are not tested
	public Edge findLeftmost(){
		Edge left = isUpperTo.get(0);
		for(Edge e : isUpperTo){
			if(e.getLower().isToRightOrLeftOf(left)<0){//May throw exception. May only be used with two uppers at the same place... Is this always the case?
				left = e;
			}
		}
		leftmost = left;
		return left;
	}

	public Edge findRightmost(){
		Edge right = isUpperTo.get(0);
		for(Edge e : isUpperTo){
			if(e.getLower().isToRightOrLeftOf(right)>0){//If e is to the right of right -> update right
				right = e;
			}
		}
		rightmost = right;
		return right;
	}


}
