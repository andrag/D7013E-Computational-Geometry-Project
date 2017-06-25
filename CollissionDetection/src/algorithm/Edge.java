package algorithm;

import java.awt.Graphics;
import java.io.Serializable;


public class Edge implements Comparable<Edge>, Serializable{

	private Endpoint start, end;
	private Endpoint upper, lower;

	public int id;

	//Use these for comparisons in the Status tree
	public int current_X; 
	public int sweep_Y;

	public Edge(Endpoint start, Endpoint end, int id){
		this.start = start;
		this.end = end;
		setUpperAndLower();

		current_X = upper.getX();
		this.id = id;
	}

	public void updateXandSweep(int sweep_Y)
	{
		this.sweep_Y = sweep_Y;
		current_X = currentXCoord(sweep_Y);
	}

	public Endpoint getStart(){
		return start;
	}

	public Endpoint getEnd(){
		return end;
	}

	public void paintEdge(Graphics g){
		g.drawLine(start.getX(), start.getY(), end.getX(), end.getY());
	}

	/**
	 * Sets which point is upper and lower in this segment AND add references to this segment in the upper and lower points
	 */
	private void setUpperAndLower(){
		if(start.getRealY()>end.getRealY() || start.getRealY() == end.getRealY() && start.getX()<end.getX()){
			upper = start;
			lower = end;
			start.addUpperTo(this);

			start.isUpper = true;

			end.isLower = true;
			end.addLowerTo(this);
		}
		else{
			upper = end;
			lower = start;
			end.addUpperTo(this);

			end.isUpper = true;

			start.isLower = true;
			start.addLowerTo(this);
		}
		current_X = upper.getX();
	}

	public Endpoint getUpper(){
		return upper;
	}

	public Endpoint getLower(){
		return lower;
	}

	public void changeUpper(Endpoint newUpper){
		upper = newUpper;
	}


	public int currentXCoord(int sweep_y){
		//If a vertical segment, it's x-choordinate is always the same.
		if(this.getUpper().getX()==this.getLower().getX() && (sweep_y <= upper.getRealY() && sweep_y >= lower.getRealY())){
			return this.getLower().getX();
		}
		//If a horizontal segment: Set current x to upper.getX()
		else if(upper.getRealY() == lower.getRealY() && sweep_y == upper.getRealY()){//(sweep_y <= upper.getRealY() && sweep_y >= lower.getRealY())){
			return upper.getX();
		}

		else if(sweep_y <= upper.getRealY() && sweep_y >= lower.getRealY()){//We have a slope
			int deltaY = this.getUpper().getRealY()-this.getLower().getRealY();
			int deltaX = this.getUpper().getX()-this.getLower().getX();
			double k = (double)deltaY/(double)deltaX;

			//y = kx + m --> m = y - kx
			double m = this.getUpper().getRealY()-(k*this.getUpper().getX());
			double x = (sweep_y - m)/k;
			x = Math.round(x);
			return (int) x;
		}

		else{
			System.out.println("sweep_y lies outside the segments endpoints."); //For fail checking
			return 2000;
		}
	}


	/**
	 * Compares the x-coordinate of this Edge to the one provided, at the current location of the sweep-line that sweeps along the y-axis.
	 * @param: The Edge to compare this Edge to.
	 * @return: -1 ,0 or 1 if this Edge has a smaller x-coordinate value, equal or larger than the provided Edges' current x-coordinate.
	 * 
	 * NB! current_X and sweep_Y must be updated in the Edge object that is passed to this method beforehand!!
	 */				
	@Override
	public int compareTo(Edge o) {
		//The absolutely most simple check first. Added 2017-06-18.
		if(o.current_X < current_X)
		{
			return 1;
		}
		else if (o.current_X > current_X)
		{
			return -1;
		}

		if(comparePointToEdge(o.current_X, o.sweep_Y) < 0){
			//System.out.println("Segment o is to the left(?), return -1");
			return -1;
		}
		else if(comparePointToEdge(o.current_X, o.sweep_Y) > 0) {
			//System.out.println("Segment o is to the right(?), return 1");
			return 1;
		}
		else
		{
			if((lower.isEqualTo(o.lower)) && (upper.isEqualTo(o.getUpper())))
			{
				return 0;
			}

			else if(upper.upperToArrayContains(o))
			{
				//Compare their lower endpoints
				if(comparePointToEdge(o.getLower().getX(), o.getLower().getRealY()) < 0) {
					//System.out.println("Segment o is to the left(?), return -1");
					return -1;
				}
				else if(comparePointToEdge(o.getLower().getX(), o.getLower().getRealY()) > 0) {
					//System.out.println("Segment o is to the right(?), return 1");
					return 1;
				}
				System.out.println("Edge.compareTo() failed (1)");
				return 0;
			}
			//They share the same lower but are different segments
			else if(lower.lowerToArrayContains(o))
			{
				//System.out.println("Segment o has the same lower as another segment");

				// -> compare their upper endpoints
				if(comparePointToEdge(o.getUpper().getX(), o.getUpper().getRealY()) < 0) {
					//System.out.println("o:s upper is to the left of the other segment, return -1");
					return -1;
				}
				else if(comparePointToEdge(o.getUpper().getX(), o.getUpper().getRealY()) > 0) {
					//System.out.println("o:s upper is to the right of the other segment, return 1");
					return 1;
				}
				System.out.println("Edge.compareTo() failed (2)");
				return 0;
			}
			else
			{
				//The current point of segment o is on the line of THIS segment
				//It is not the same segment
				//It is not the upper to THIS segment
				//It is the LOWER to this segment

				//Should update current x and sweep y of THIS segment here. Still just a test so don't need it. (Should be updated outside anyway.)
				int comparison = o.comparePointToEdge(current_X, sweep_Y);
				if(comparison > 0) return -1;
				else if(comparison < 0) return 1;
				System.out.println("Edge.compareTo() FAILED REALLY BAAAAAD (3)");
				return 0;
			}
		}
	}

	/**
	 * Purpose: Compare a point to a segment to see which side it lies on
	 * Needs: unit test
	 * @param p
	 * @return
	 */
	private int comparePointToEdge(int p_x, int p_y) {
		int x1 = upper.getX();
		int y1 = upper.getRealY();

		int x2 = lower.getX();
		int y2 = lower.getRealY();

		double d = (p_x - x1) * (y2 - y1) - (p_y - y1) * (x2 - x1);

		if(d < 0) return -1;
		else if(d > 0) return 1;
		else return 0;		
	}


	/**
	 * Checks if two segments cross eachother
	 * Inspired by http://www.ahristov.com/tutorial/geometry-games/intersection-segments.html
	 */
	public Endpoint doIntersect(Edge segment) {
		int x1 = upper.getX();
		int y1 = upper.getY();
		int x2 = lower.getX();
		int y2 = lower.getY();

		int x3 = segment.getUpper().getX();
		int y3 = segment.getUpper().getY();
		int x4 = segment.getLower().getX();
		int y4 = segment.getLower().getY();

		int d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4); //Denominator determinant for point calculation
		if (d == 0) return null;//Lines are parallell

		//Point calculation
		int xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
		int yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;

		//Check if any of the lines are vertical
		if(x1 == x2 || x3 == x4){
			//Check if the intersection point lies outside any of the segment in the y direction
			if(yi < Math.min(y1, y2) || yi > Math.max(y1, y2)) return null;
			if(yi < Math.min(y3, y4) || yi > Math.max(y3, y4)) return null;
		}

		//Check if intersection lies outside the segments endpoints
		Endpoint p = new Endpoint(xi,yi);
		if (xi < Math.min(x1,x2) || xi > Math.max(x1,x2)) return null;
		if (xi < Math.min(x3,x4) || xi > Math.max(x3,x4)) return null;
		return p;
	}
}
