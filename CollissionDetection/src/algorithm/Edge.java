package algorithm;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;
import java.util.ArrayList;

//import datastructures.Edge;

public class Edge implements Comparable<Edge>, Serializable{//Last one only needed for storing?

	private Endpoint start, end;
	private Endpoint upper, lower;
	//Added 2017-04-09
	public int id; //Use this to find the correct edge in the status tree
	public int current_X; //Use this for comparisons in the Status tree
	public int sweep_Y;
	public boolean isHorizontal;
	
	
	public Edge(Endpoint start, Endpoint end, int id){ //L�gg till id h�r
		this.start = start;
		this.end = end;
		setUpperAndLower();
		
		current_X = upper.getX();
		this.id = id;
		
		if(start.getY() == end.getY()) isHorizontal = true;
	}
	
	public void updateXandSweep(int sweep_Y)
	{
		this.sweep_Y = sweep_Y;
		current_X = currentXCoord(sweep_Y);//<--------------------------------------Might be wrong
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
	
	//Decide which is upper and lower in this segment AND add this segment as data to the upper and lower points
	private void setUpperAndLower(){
		if(start.getRealY()>end.getRealY() || start.getRealY() == end.getRealY() && start.getX()<end.getX()){
			/*start.setUpper();
			end.setLower();
			start.setSegment(this);
			end.setSegment(this);*/
			
			//start.setPointUpperTo(this);
			//end.setPointIsLowerTo(this);
			upper = start;
			lower = end;
			start.addUpperTo(this);
			
			
			start.isUpper = true;
			
			end.isLower = true;
			end.addLowerTo(this);
		}
		else{
			/*start.setLower();
			end.setUpper();
			end.setSegment(this);
			start.setSegment(this);*/
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
	
	
	//Denna returnerar fel s� in i bomben!! Anv�nds ej mer. <--------------------------------------------TA REDA P� VILKEN SOM SKA ANV�NDAS!
	//Ska ha korrekt matte! B�r jag l�gga till constraints f�r endpointsen i segmenten???
	
	
	/* Input (124, 325), (22, 277) ger 0 f�r sweep 124.
	 * Input (124, 325), (206, 108) ger 124 f�r sweep 124.
	 */
	
	/*Test with returning double
	 * 
	 */
	public int currentXCoord(int sweep_y){
		//System.out.println("Inne i currentXCoord");
		//If a vertical segment, it's x-choordinate is always the same.
		if(this.getUpper().getX()==this.getLower().getX() && (sweep_y <= upper.getRealY() && sweep_y >= lower.getRealY())){
			//System.out.println("Segmentet �r vertikalt.");
			//L�gg till en if y == this.getUpper().getY()){ //F�r att h�lla inom ramarna. Om det beh�vs. Samma i else nedan. Beh�vs inte f�r sweep line.
			return this.getLower().getX();//Any x will do. They are the same. This also skips dividing by zero in y = kx+m below.
		}
		//If a horizontal segment: Try setting current x to upper.getX()
		else if(upper.getRealY() == lower.getRealY() && (sweep_y <= upper.getRealY() && sweep_y >= lower.getRealY())){
			//System.out.println("Segmentet �r horisontellt.");
			return upper.getX();
		}
		
		else if(sweep_y <= upper.getRealY() && sweep_y >= lower.getRealY()){//We have a slope
			/*System.out.println("Segmentet lutar.");
			System.out.println("Upper: ("+this.getUpper().getX()+","+this.getUpper().getRealY()+")");
			System.out.println("Lower: ("+this.getLower().getX()+", "+this.getLower().getRealY()+")");
			System.out.println("DeltaY = "+ (this.getUpper().getRealY() - this.getLower().getRealY()));
			System.out.println("DeltaX = "+ (this.getUpper().getX()-this.getLower().getX()));*/
			int deltaY = this.getUpper().getRealY()-this.getLower().getRealY();
			int deltaX = this.getUpper().getX()-this.getLower().getX();
			double k = (double)deltaY/(double)deltaX;
			//System.out.println("k = "+k);
			
			//y = kx + m --> m = y - kx
			double m = this.getUpper().getRealY()-(k*this.getUpper().getX());//Anv�nd k�nda v�rden p� x och y.
			//System.out.println("m = "+m);
			
			//y = kx + m --> x = (y-m)/k
			double x = (sweep_y - m)/k;
			//System.out.println("x = "+x);
			x = Math.round(x);
			return (int) x;
		}
		
		else{
			System.out.println("sweep_y lies outside the segments endpoints.");
			return 2000;
		}
	}
	//Anv�nds ej mer.
	//Kollar endast om ett segment har l�gre x-v�rde �n ett annat d� y = sweep_y
	//Har de samma x-v�rde ska en annan funktion rapportera intersection
	public boolean isSmallerThan(Edge segment, int sweep_y){
		if(currentXCoord(sweep_y) < segment.currentXCoord(sweep_y)){//If they are the same, they will be prioritized according to direction.
			return true;
		}
		else return false;
	}

	/**
	 * Compares the x-coordinate of this Edge to the one provided, at the current location of the sweep-line over that sweeps over the y-axis.
	 * @param: The Edge to compare this Edge to.
	 * @return: -1 ,0 or 1 if this Edge has a smaller x-coordinate value, equal or larger than the provided Edges' current x-coordinate.
	 * 
	 * NB! current_X and sweep_Y must be updated in the Edge object that is passed to this method beforehand!!
	 * 
	 * There are many cases, where the intersecting ones are most complex
	 * 		
	 * 		1. Adding a new segment, could be upper to one or many or be an intersection (intersection = upper to many when they arrive as new points)
	 * 		if(currentX < o.current_X) return -1
	 * 		else if(current_X > o.current_X) return 1
	 * 		else if(current_X == o.currentX)
	 * 			if(The segments are the same. Id or just overlapping + belonging?) return 0
	 * 			else if(this to left of o) return -1
	 * 			else if(this to right of o) return 1
	 * 			else do some trace
	 * 
	 * 
	 * 		2. Searching for a segment - same as above
	 * 
	 * 		3. Deleting a segment - same as above
	 * 			NB! A segment gets deleted due to a lower eventpoint is encountered or an intersection is handled. 
	 * 			
	 * 		Problems with current_X
	 * 			1. Using doubles in calculations might lead to rounding errors and misses. Should probably use linear algebra here. Compare one current x with an entire edge.
	 * 			2. The best way would be to use the current x only in the segment that is input to the tree, not in the segments that lies within the tree. This way we avoid having to update the entire tree.
	 * 			
	 * 			Example:
	 * 				1. Adding a new segment:
	 * 					newSeg.updateX-coord();
	 * 					status.add(newSeg); <----------Compare to now compares like this: in an edge e: if(this isToRightOf(newSeg.current_X())) return -1. Easier to find a segment then too!
	 * 	
	 * 		
	 * 		
	 * 
	 * 
	 * 
	 * Case 1: x1 < x2 -> return -1
	 * 		Case 2: x1 > x2 -> return 1
	 * 		Case 3: x1 == x2
	 * 					3.1 if they are uppers to many
	 * 					or intersection -> compare their lowers and return -1, 0 or 1
	 * 					3.2 else if they are lower point 
	 * 							-> This should not happen because all edges should have been erased before
	 * 							-> p might be a lower and an upper but this is an else if, meaning that the upper-if above will take it instead
	 * 
	 * 
	 * 		All cases ()
	 * 				- uppers to many
	 * 				- upper to many and an intersection
	 * 				- upper to one and an intersection
	 * 				- etc... skip these for now
	 * 
	 *  NB! current_X of this edge and the one we compare to need to be updated before comparing.
	 *  How to avoid unnecessary updates?
	 */				
	@Override
	public int compareTo(Edge o) {
		//1. Compare o.current_X to this.segment
		//if(o.x is to left) return -1
		//if(o.x is to right) return 1
		//if(o.x is on the line)
			//if(same element (parallell)) (check endpoints, ids, belonings, whatever) return 0
			//else(we have a case where two segments intersect but are not the same. Might be of different lengths.) return -1 as default and print a trace_info
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
			/*The point is on the line. We have the following possible scenarios:
			 * 1. The segments are the same -> return 0
			 * 2. We insert a new segment, this has same upper as a present segment (or they might be intersections. Complex scenario, skip for now. I beleive that scenario is already caught in CollisionDetection) -> compare their lowers to know where to go next
			 * 3. We search for a segment to delete and have found a segment that has the same lower as the one we want to delete. -> compare their uppers to know what to return.
			 * */
			//1.
			if((lower.isEqualTo(o.lower)) && (upper.isEqualTo(o.getUpper())))// && (upper.getBelonging() == o.upper.getBelonging())) //Need more checks? Id?`IDs must then follow along each segments wherever they may reside.
			{
				//The segments are the same
				//System.out.println("compareTo found a segment that matches o in the treeSet");
				return 0;
			}
			//2. They share the same upper but are different segments
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
			//3. They share the same lower but are different segments
			else if(lower.lowerToArrayContains(o))//<------ Does not work!
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
				//It is the LOWER to this segment (spelar ingen roll, skulle kunna vara korsat.) -> l�sning...?
				//L�sning: Testa current point of THIS segment against the entire o segment
				
				//Should update current x and sweep y of THIS segment here. Still just a test so don't need it. (Should be updated outside anyway.)
				int comparison = o.comparePointToEdge(current_X, sweep_Y);
				if(comparison > 0) return -1;
				else if(comparison < 0) return 1;
				System.out.println("Edge.compareTo() FAILED REALLY BAAAAAD (3)");
				return 0;//Problem with return statements in this method. Cases that should not happen return 0. That is bad.
			}
			/*else
			{
				System.out.println("Edge.compareTo() FAILED REALLY BAAAAAD (2)");
				return 0;//Problem with return statements in this method. Cases that should not happen return 0. That is bad.
			}*/
		}
	}
	
	/**
	 * Created 2017-06-03
	 * Purpose: Compare a point to a segment to see which side it lies on
	 * Needs: unit test
	 * @param p
	 * @return
	 */
	private int comparePointToEdge(int p_x, int p_y) { //Risk with current x: it has to be calculated using doubles and then cast to int. Might miss intersections... but, intersections might already be caught??
		int x1 = upper.getX();
		int y1 = upper.getRealY();
		
		int x2 = lower.getX();
		int y2 = lower.getRealY();
		
		double d = (p_x - x1) * (y2 - y1) - (p_y - y1) * (x2 - x1);
		
		if(d < 0) return -1;
		else if(d > 0) return 1;
		else return 0;		
	}
	
	private boolean upperToSegment(Edge segment){
		if(upper.isUpperTo.contains(segment)) //Does this thing work with custom objects?
		{
			return true;
		}
		return false;
	}
	
	private boolean lowerToSegment(Edge segment){
		if(lower.isLowerTo.contains(segment))
		{
			return true;
		}
		return false;
	}
	
	
	
	/* Metod f�r att j�mf�ra ett efters�kt segment med ett befintligt i en nod.
	 * This �r efters�kta segmentet
	 * @params: segment �r befintliga segmentet i noden
	 * @params: sweep_y �r det y-v�rde sveplinjen har d� metoden kallas
	 * @output: 
	 * -1 om this ligger till v�nster
	 * 0 om samma
	 * 1 om this ligger till h�ger
	 * 
	 * Denna kanske endast ska kolla x-v�rde mot varandra
	 */
	public int newCompareTo(Edge segment, int sweep_y){
		//Ber�kna x-v�rdet hos this f�r current y.
		
		/*
		int x = currentXCoord(sweep_y);
		Endpoint temp = new Endpoint(x, sweep_y);
		if(temp.isToRightOrLeftOf(segment) < 0){
			return -1;
		}
		else if(temp.isToRightOrLeftOf(segment)>0){
			return 1;
		}
		else return 0;*/
		//Kolla med typ isToLeftOrRight med currentX som argument.
		//Testa currentX-metoden och ta reda p� varf�r den returnerar fel.
		
		double x1 = currentXCoord(sweep_y);
		//System.out.println("Current x coord for ("+this.getUpper().getX()+", "+this.getUpper().getRealY()+"), ("+this.getLower().getX()+", "+this.getLower().getRealY()+") is "+x1);
		
		double x2 = segment.currentXCoord(sweep_y);
		//System.out.println("Current x coord for ("+segment.getUpper().getX()+", "+segment.getUpper().getRealY()+"), ("+segment.getLower().getX()+", "+segment.getLower().getRealY()+") is "+x2);
		if(x1>x2) return 1;
		else if(x2>x1) return -1;
		//else if(x1 == x2){
			
			
			//Ovan metod borde inte funka! M�ste l�gga till att de kan �verlappa varandra. Kolla d� deras lower med en determinantmetod?
		//}
		else return 0;
		
		
	}
	
	
	/*Anv�nds ej mer.
	 * Returnerar 	-1 om denna edge pekar �t v�nster i f�rh�llande till Edge e.
	 * 				+1 om denna edge pekar �t h�ger
	 * 				0 om de �r samma
	 */
	public int specialCompareTo(Edge e, int sweep_y){
		if(currentXCoord(sweep_y) < e.currentXCoord(sweep_y) || currentXCoord(sweep_y) == e.currentXCoord(sweep_y) && compareSides(sweep_y-1, e)==-1){//Check om detta redan �r en lower???
			//Kolla riktning direkt under sweepline!!! Hur g�rs deta b�st?
			//Anv�nd upper och lower som line. Kolla vilken sida this.lower ligger p�. typ.
			return -1;
		}
		else if(currentXCoord(sweep_y)>e.currentXCoord(sweep_y) || currentXCoord(sweep_y)==e.currentXCoord(sweep_y) && compareSides(sweep_y-1, e)==1){
			return 1;
		}
		else if(currentXCoord(sweep_y)==e.currentXCoord(sweep_y)&&compareSides(sweep_y, e)==0){//Problematiskt att anv�nda lower h�r. Blir fel vid korsade linjer.
			return 0;
		}
		System.out.println("Lacking if-statement in specialCompareTo in Edge.");
		return 0;
	}
	
	//Anv�nds bara d� tv� segment har punkter i samma x-coord
	//Nu ber�knar denna vilka om ett segment ligger till v�nster om ett annat precis under sveplinjen
	public int compareSides(int sweep_y, Edge e){//Tidigare tog denna en endpoint som var e.getLower()
		double x1 = currentXCoord(sweep_y);
		double x2 = e.currentXCoord(sweep_y);
		
		
		//Full�sning. Kan funka eftersom inget segment som har lowerpoint i nuvarande p ska s�kas igenom. F�rhoppningsvis redan borttaget.
		if(x1==x2){
			return 0;
		}
		else if(x1 < x2){
			return -1;
		}
		
		else if(x1>x2){
			return 1;
		}
		
		//Determinant stuff
		/*int det = (lower.getX()-upper.getX())*(p.getRealY()-upper.getRealY()) - (lower.getRealY()-upper.getRealY())*(p.getX()-upper.getX());
		if(det<0){
			return 1;
		}
		else if(det>0){
			return -1;
		}
		
		return 0;*/
		System.out.println("Compare sides i Edge returnerar fel.");
		return 0;
	}
	
	//�ndra i search: Om current x �r lika men uppers och lowers inte �r det. D� har vi detta specialfall!
	//Denna anv�nds f�r att avg�ra vilken sida av ett annat segment ett segment ska s�ttas in i i statusen.
		public int isToRightOrLeftOf(Edge segment){
			//Determinant stuff
			int det1 = (segment.getLower().getX()-segment.getUpper().getX())*(getUpper().getRealY()-segment.getUpper().getRealY()) - (segment.getLower().getRealY()-segment.getUpper().getRealY())*(getUpper().getX()-segment.getUpper().getX());
			int det2 = (segment.getLower().getX()-segment.getUpper().getX())*(getLower().getRealY()-segment.getUpper().getRealY()) - (segment.getLower().getRealY()-segment.getUpper().getRealY())*(getLower().getX()-segment.getUpper().getX());
			if(det1<0 || det1==0 && det2<0){//Be careful with the grouping here. This mght be wrong.
				return -1;
			}
			else if(det1>0 || det1 == 0 && det2>0){//Kolla b�de upper och lower om upper skulle vara samma f�r b�da
				return 1;
			}
					
			else return 0;//They are on a line(Might not work if there is to different segments on a line.)
		}
		
		
		//Denna metod funkade 27/1
		//Returnerar null om intersection ligger utanf�r segmentens endpoints
		//Testa med vertikala och horisontella linjer
		public Endpoint isCrossingWith(Edge segment){
			System.out.println("Inne i metoden.");
			if(upper.getX()!=lower.getX() && segment.getUpper().getX()!=segment.getLower().getX()){//Avoid division by zero. Test this case!
				
				System.out.println("Linjerna lutar!");//Fail!!! Det g�r de inte alls! Beh�ver ett test mot horisontell linje
				
				double k1 = (upper.getRealY()-lower.getRealY())/(upper.getX()-lower.getX());
				double k2 = (segment.getUpper().getRealY()-segment.getLower().getRealY())/(segment.getUpper().getX()-segment.getLower().getX());
				
				double m1 = upper.getRealY()-(k1*upper.getX());//Anv�nd k�nda v�rden p� x och y.
				double m2 = segment.getUpper().getRealY()-(k2*segment.getUpper().getX());//Anv�nd k�nda v�rden p� x och y.
				
				double x = (m1-m2)/(k2-k1);//Correct? Om det �r en intersection. Oops. Division med 0 h�r om det �r parallella linjer! Fixit!
				double y1 = k1*x+m1;
				double y2 = k2*x+m2;
				
				//Hittar inte intersection d� ena linjen �r horisontell
				if(y1==y2 && y1<upper.getRealY() && y1>lower.getRealY() && y1<segment.getUpper().getRealY() && y1>segment.getLower().getRealY()){
					System.out.println("The method is working. We have an intersection at:" + x +", "+ y1);
					Endpoint intersection = new Endpoint((int)x, (int)y1);
					System.out.println("Hej och h�!");
					return intersection;
				}
				else{
					System.out.println("G�r den in i else?");
					
				}
			}
			else System.out.println("Division by zero in method isCrossingWith() in Edge."); return null;
		}
		
		
		 /**
		0002   * Computes the intersection between two segments. 
		0003   * @param x1 Starting point of Segment 1
		0004   * @param y1 Starting point of Segment 1
		0005   * @param x2 Ending point of Segment 1
		0006   * @param y2 Ending point of Segment 1
		0007   * @param x3 Starting point of Segment 2
		0008   * @param y3 Starting point of Segment 2
		0009   * @param x4 Ending point of Segment 2
		0010   * @param y4 Ending point of Segment 2
		0011   * @return Point where the segments intersect, or null if they don't
		0012   */
		
		
		//http://www.ahristov.com/tutorial/geometry-games/intersection-segments.html
		//This method is for spotting an intersection between two segments. This intersection
		//shall later be inserted as a new eventPoint in the queue
		
		//2016-04-06: This seems to be in use but not handling vertical segments like it should.
		//Test performed:
		public Endpoint doIntersect(Edge segment) {
				    int x1 = upper.getX();
				    //int y1 = upper.getRealY();
				    int y1 = upper.getY();//M�ste anv�nda getY() och inte realY() h�r.
				    int x2 = lower.getX();
				    //int y2 = lower.getRealY();
				    int y2 = lower.getY();
				    
				    int x3 = segment.getUpper().getX();
				    //int y3 = segment.getUpper().getRealY();
				    int y3 = segment.getUpper().getY();
				    int x4 = segment.getLower().getX();
				    //int y4 = segment.getLower().getRealY();
				    int y4 = segment.getLower().getY();
			
			
					int d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4); //Denominator determinant for point calculation
				    if (d == 0) return null;//Lines are parallell
				    
				    //Point calculation
				    int xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
				    int yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
				    
				  //Added 2016-04-06: Check if any of the lines are vertical
			        if(x1 == x2 || x3 == x4){
			            //2016-04-06: Check if the intersection point lies outside any of the segment in the y direction
			            if(yi < Math.min(y1, y2) || yi > Math.max(y1, y2)) return null;
			            if(yi < Math.min(y3, y4) || yi > Math.max(y3, y4)) return null;
			        }
				    
				    //Check if intersection lies outside the segments endpoints
				    Endpoint p = new Endpoint(xi,yi);
				    if (xi < Math.min(x1,x2) || xi > Math.max(x1,x2)) return null;
				    if (xi < Math.min(x3,x4) || xi > Math.max(x3,x4)) return null;
				    return p;
				  }
		
		
		//http://www.ahristov.com/tutorial/geometry-games/intersection-segments.html
		//This method is for spotting an intersection between two segments. This intersection
		//shall later be inserted as a new eventPoint in the queue
		public Endpoint intersectionOf(int x1,int y1,int x2,int y2, int x3, int y3, int x4,int y4) {
			int d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4); //Denominator determinant for point calculation
			if (d == 0) return null;//Lines are parallell

			//Point calculation
			int xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
			int yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;

			//Check if intersection lies outside the segments endpoints
			Endpoint p = new Endpoint(xi,yi);
			if (xi < Math.min(x1,x2) || xi > Math.max(x1,x2)) return null;
			if (xi < Math.min(x3,x4) || xi > Math.max(x3,x4)) return null;
			return p;
		}
		
		

}
