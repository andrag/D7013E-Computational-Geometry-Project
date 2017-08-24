package algorithm;

/**
 * Project: Implementation of efficient Collision Detection Algorithm
 * 
 * Course: D7013E Computational Geometry
 * Author: Anders Ragnarsson
 * Email: andrag-0@student.ltu.se 
 * 
 * 1. Description of the algorithm
 * 
 * Input: the endpoints that make up the line segments of two polygons that may or may not overlap with eachother.
 * Output: A list of intersection points between the two polygons, if the polygons overlap.
 * Time constraints: as efficient as possible, no naive solution.
 * 
 * The algorithm is a line sweep solution where the sweep line moves down from the top along the y-axis. A status tree holds the line segments that the
 * sweep line crosses, but not the ones it has passed or not yet reached. In this way, only the segments within the status are compared. When the sweep line 
 * has passed the end of a segment, it is removed from the status tree and never again considered. 
 * The algorithm contains the following steps:
 * 
 * 		1.1. The endpoints of the polygons are sorted in a queue made up of a self-balancing binary search tree (BBST) in n(log n) time.
 * 
 * 		1.2. For each event point in the queue:
 * 
 * 			* If it is a lower event point:
 * 				Remove the segments from the status tree that ends in this lower point
 * 
 * 			* If it is an upper event point:
 * 				Insert all segments this point is an upper point to, into the status tree.
 * 				Check if the new segment(s) give raise to a future intersection. If yes - add it as a new event point to the queue
 * 
 *  		* If it is an intersection event point
 *  			Add the points coordinates as an intersection in the output list
 *  			Make sure that the segments that intersects switch places correctly in the status.
 *				Check if the switching places of segments give raise to a future intersection. If yes - add it as a new event point to the queue
 *		
 *		
 * 2. Description of the implemented solution
 * 		
 * 		2.1 Event flow
 * 		The user draws two overlapping polygons in the GUI. When executing the program these points are sent to the CollisionDetection class
 * 		that performs the algorithm through the following steps:
 * 		
 * 		1. The event points are sorted in a self-balancing binary search tree (BBST) in O(log(n)) time
 * 		2. The status tree is initialized, also a BBST
 * 		3. For each event point in the queue
 * 				- Call handleEventPoint(Endpoint p)
 * 				- See JavaDoc for the methods below for more details
 * 
 * 
 * 
 * 		2.2 System Architecture
 * 		The solution is divided into three packages; main, datastructures and algorithm. 
 * 		 
 *  	 The algorithm package holds the algorithm implementation and all the logic. Edges and Endpoint classes are what the polygons are made of. 
 *  	They hold references to each other, what polygon they belong to, as well as math functions for deciding on which side of a line a point lies and so on. 
 *  	Endpoints keep track of which Edges they are upper and lower points to.
 *  
 *		 The datastructures package contains the binary searh trees used for the event queue and the status tree and the main package contains the
 *		GUI implementation. The reason why there are two BBST implementations in the datastructures package is because the AVLTree implementation was not good enough
 *		for use as a status tree, so it was replaced by a Java TreeSet for the status.
 * 
 * 
 * 	3. Time complexity
 * 		Both the event queue and the status tree are self-balancing binary search trees that ensures O(log n) for each insert, retrieve and 
 * 		delete operation. Only edges that contain the current y-coordinate of the sweep line can exist together in the status tree. 
 * 		For each arriving event point, the status tree is searched a (small) constant number of times, giving k * log(n) time per event point.
 * 		Having n end points gives k*n*log(n) + I * log(n) time for the entire algorithm, where I is the number of intersections. 
 * 
 * 
 * 3. User manual
 * 		- Paint two polygons in the GUI by clicking with the mouse for each point in the polygons. 
 * 		- Do not cross a polygon with itself.
 * 		- To make the last line segment in a polygon, press "Finish polygon" button. 
 * 		- When two polygons are made, click "Compute collision" to run the algorithm.
 * 		- For each new run, a file is saved with the latest input data. To use it, set the variable String "loadFile" in GUI to the name of the
 * 		file you want to run. The files are written to the src directory. To draw your own polygons, leave "loadFile" an empty string ""
 * 
 */





import java.util.ArrayList;
import java.util.logging.Logger;
import datastructures.*;


public class CollisionDetection extends ArrayList<Endpoint>{

	private static final long serialVersionUID = 1L;
	private AVLTree eventQueue;
	private StatusTreeSet status;
	private ArrayList<Endpoint> allIntersections;

	private static int edgeID = 0;//For keeping track of all the edges
	private boolean intersecting = false;
	
	public static boolean isHandlingIntersection = false;

	public static int sweep_Y;
	
	
	
	/**
	 * Constructor: 1. Creates a sorted event queue from the two starting points of the polygons.
	 * 				2. Creates a status tree
	 * 				3. Runs handleEventPoint() and findNewEvent() for each event point in the queue
	 * 				4. Runs handleIntersection for each event point that is also an intersection between two segments
	 * 
	 * @param startpoint1
	 * @param startpoint2
	 */
	public CollisionDetection(Endpoint startpoint1, Endpoint startpoint2){
		//Make an eventqueue BBST
		eventQueue = new AVLTree();
		Endpoint current = startpoint1;
		allIntersections = new ArrayList<Endpoint>();

		while(current.getNextSeg().getEnd()!=startpoint1){
			eventQueue.insert(current);	 
			current = current.getNextSeg().getEnd();
		}
		eventQueue.insert(current);
		current = startpoint2;
		while(current.getNextSeg().getEnd()!=startpoint2){
			eventQueue.insert(current);
			current = current.getNextSeg().getEnd();
		}
		eventQueue.insert(current);


		status = new StatusTreeSet();
		while(eventQueue.root.left!=null || eventQueue.root.right!=null){//!eventQueue.isEmpty()
			Endpoint next = eventQueue.findLargest(eventQueue.root);
			eventQueue.traverseInOrder();
			eventQueue.delete(next);
			handleEventPoint(next);
		}
	}


	/**
	 * Handles each event point in the event queue in order.
	 * Creates new event points as intersections are found and places them at the correct position in the event queue.
	 * Fills an array list with all intersection points that are found.
	 * 
	 * Event points can be considered as:
	 * 		1. Lower point to a segment
	 * 		2. Upper point to a segment
	 * 		3. Intersection between two segments
	 * 		4. A combination of the above
	 *
	 * @param Endpoint p
	 */
	public void handleEventPoint(Endpoint p){

		//1. Endpoint p is a lower point to one or more segments
		if(p.isLower){
			System.out.println("===============================Handle a lower eventpoint=============================");

			//1.1 Upate the sweep line to be at the y coordinate of p
			sweep_Y = p.getRealY();
			//status.updateAll(sweep_Y); 	//<--------------Updating all segments here gives O(n^2) time since the entire status need to be traversed for each event point, messing up the log n benefit of the BBST
										//					Solution: Update only when comparing segments

			//1.2 Delete all segments in the status that has a lower point in point p
			for(Edge e : p.getLowerTo()){
				System.out.println("(1)Removing segment with id: " + e.id);
				status.remove(e);
				status.traverseStatus();
				e.getUpper().isUpperTo.remove(e);
			}
			p.getLowerTo().clear();
			status.traverseStatus();
		}

		//2. Endpoint p is an uppe point to one or more segments, but not an intersection.
		if(p.isUpper() && !p.isIntersection){
			System.out.println("/n================================Handle an upper event point===================================/n/n");
			
			//2.1 Update the sweep line
			sweep_Y = p.getRealY();
			//status.updateAll(sweep_Y);

			//2.2 Check if p is an upper point to two segments.
			if(p.getUpperTo().size()>1){
				
				// Add both to the status.
				for(Edge e : p.getUpperTo()){
					e.updateXandSweep(sweep_Y);
					status.add(e);

					/*if(p.getBelonging() != e.getLower().getBelonging()){ //Check if they belong to the same polygon. No need, already checked in intersection points.
						intersecting = true;
					}
					else System.out.println("No collisiion between this uppers segments. This case should only appear in the top point of a polygon if it is really simple :)");
					 */}

				// Find out which segment is leftmost resp rightmost
				Edge left = p.findLeftmost();//Replaced by leftmost and rightmost inside Endpoints class
				Edge right = p.findRightmost();

				//Find their neighbours in the status
				Edge leftNeighbour = status.lower(left);
				Edge rightNeighbour = status.higher(right);

				//Check if the newly inserted segments intersect with their neighbours
				findNewEvent(leftNeighbour, rightNeighbour, p);
			}
			
			//2.3 Case: Point p is an upper point to only one segment
			else {
				
				//Add the segment to the status tree
				p.getUpperTo().get(0).updateXandSweep(sweep_Y);
				status.add(p.getUpperTo().get(0));

				//Find left and right neighbours of the new segment
				Edge leftNeighbour = status.lower(p.getUpperTo().get(0));
				Edge rightNeighbour = status.higher(p.getUpperTo().get(0));

				//Check if the new segment intersect a neighbour in the status
				findNewEvent(leftNeighbour, rightNeighbour, p);
			}
		}
		
		// 3. Check if p is an intersection point between two segments (Can never be more since there are two polygons and none is allowed to cross itself)
		if(p.isIntersection){

			System.out.println("=====================Handling an intersection eventpoint====================\n");

			intersecting = true;
			handleIntersectingSegments(p);
			p.isUpper = true;

			Edge left = p.findLeftmost();
			Edge right = p.findRightmost();

			Edge leftNeighbour = status.lower(left);
			Edge rightNeighbour = status.higher(right);

			//Check if the switched locations of intersecting segments cause new intersections in the status
			findNewEvent(leftNeighbour, rightNeighbour, p);
		}


		if(intersecting == true){
			//Add intersection points to the algorithm output list
			allIntersections.add(p);
			intersecting = false;
		}
		System.out.println("\n====================================Slut p� handleEventPoint.==========================================");
	}




	public ArrayList<Endpoint> getIntersections(){
		return allIntersections;
	}

	
	/**
	 * Handles segments involved in an intersection.
	 * Performs cutting of the parts of the segments that lie above intersection point p.
	 * Deletes the old segments and insert the new shorter ones into the status. This reorders them in the status tree after the point where they cross.
	 * @param p An intersection point between two segments 
	 */
	private void handleIntersectingSegments(Endpoint p){
		//1. Get the segments that are involved in the intersection (Only two are possible in this implementation, otherwise they cross themselves = not ok)
		Edge tempSeg1 = p.getUpperTo().get(0);
		Edge tempSeg2 = p.getUpperTo().get(1);

		//2. Delete them from the status
		tempSeg1.updateXandSweep(tempSeg1.getUpper().getRealY());//There is a need to revert their sweep_Y and currentX to the positions they had when their upper point was last updated. Otherwise they won't be found.
		tempSeg2.updateXandSweep(tempSeg2.getUpper().getRealY());// Is there any case where this approach mess things up?
		
		System.out.println("(2) Removing segment with id: " + tempSeg1.id);
		isHandlingIntersection = true;
		status.remove(tempSeg1);
		status.traverseStatus();
		System.out.println("(3) Removing segment with id: " + tempSeg2.id);
		status.remove(tempSeg2);
		status.traverseStatus();
		isHandlingIntersection = false;
		

		//3. Remove their references at their former upper
		tempSeg1.getUpper().isUpperTo.remove(tempSeg1);
		tempSeg2.getUpper().isUpperTo.remove(tempSeg2);

		//4. Set the intersecting point as their new upper
		tempSeg1.changeUpper(p);
		tempSeg2.changeUpper(p);

		//5. Update the status such that an add will be just like adding a new upper point of two segments
		sweep_Y = p.getRealY();
		//status.updateAll(sweep_Y);

		//6. Update the sweep_y of the edges such that they will now be inserted in the new order
		tempSeg1.updateXandSweep(sweep_Y);
		tempSeg2.updateXandSweep(sweep_Y);

		//7. Insert the new modified segments into the status again
		status.add(tempSeg1);
		status.add(tempSeg2);
	}

	
	
	/**
	 * Takes an event point p that is an upper point of one or two segments (could be an intersection point or not).
	 * Checks if the segments originating from p intersects with the left and/or right neighbour(s) in the status tree.
	 * This method is called once for each new event point that is an upper point to one or two segments, and once for each intersection event point,
	 * which is always an upper point to two segments. The method only finds the closest intersection of a line and its neighbour in each run. When an 
	 * intersection point is handled and two segments switch places in the status tree, this method is called to find out if the switched segments 
	 * now cross their new neighbours.
	 * 
	 * @param leftNeighbour
	 * @param rightNeighbour
	 * @param p
	 */
	public void findNewEvent(Edge leftNeighbour, Edge rightNeighbour, Endpoint p){//Borde k�ra point h�r ist�llet s� kan man kolla o den ska leta upp leftmost och rightmost.
		Endpoint crossing;

		if(leftNeighbour==null)
			System.out.println("There is no left neighbour.");

		//Check if the segment cross its left neighbour
		else{
			if(p.getUpperTo().size()>1){

				//Check left segment againts left neighbour
				crossing = p.leftmost.doIntersect(leftNeighbour);
				if(crossing == null){
					System.out.println("The left segment doesn't intersect with its left neighbour.");
				}
				//Check that the crossing is not in the same lower point.
				else if(!(leftNeighbour.getLower().compareTo(p.leftmost.getLower())==0)){ 

					//The segments intersect, create a new eventpoint if they belong to different polygons
					if(leftNeighbour.getLower().getBelonging() != p.leftmost.getLower().getBelonging()){
						Endpoint intersection = new Endpoint(crossing.getX(), crossing.getY());
						intersection.isIntersection = true;
						intersection.setBelonging(p.getBelonging());

						//Send the involved segments with the intersection
						intersection.getUpperTo().add(leftNeighbour);
						intersection.getUpperTo().add(p.leftmost);
						eventQueue.insert(intersection);
					}		
				}
				else System.out.println("The left segment intersect with its left neighbour in both their lower points");
			}
			else if(p.getUpperTo().size()==1){
				//Check the only segment against its left neighbour
				crossing = p.getUpperTo().get(0).doIntersect(leftNeighbour);
				if(crossing == null){
					System.out.println("The segment doesn't intersect with its left neighbour.");
				}
				//Check that the crossing isn't a crossing between two lowers.
				else if(p.getUpperTo().get(0).getLower().compareTo(leftNeighbour.getLower())!=0 && p.getBelonging() != leftNeighbour.getLower().getBelonging()){//This should be ok. Only 1 element in isUpperTo --> this is not sprung from an intersection point
					//The segments intersect, create a new eventpoint
					Endpoint intersection = new Endpoint(crossing.getX(), crossing.getY());
					intersection.isIntersection = true;
					intersection.setBelonging(p.getBelonging());

					//Send the involved segments with the intersection
					intersection.getUpperTo().add(leftNeighbour);
					intersection.getUpperTo().add(p.getUpperTo().get(0));
					eventQueue.insert(intersection);
				}
			}
		}

		//Check if right neighbour is null
		if(rightNeighbour==null)
			System.out.println("There is no right neighbour.");

		//Check if the segment cross its right neighbour
		else{
			if(p.getUpperTo().size()>1){

				//Check right segment againts right neighbour
				crossing = p.rightmost.doIntersect(rightNeighbour);
				if(crossing == null){
					System.out.println("The right segment doesn't intersect with its right neighbour.");
				}
				//Check that crossing isn't a crossing between two lowers and that they belong to different polygons
				else if(p.rightmost.getLower().compareTo(rightNeighbour.getLower())!=0 && p.rightmost.getLower().getBelonging() != rightNeighbour.getLower().getBelonging()){
					//The segments intersect, create a new eventpoint
					Endpoint intersection = new Endpoint(crossing.getX(), crossing.getY());
					intersection.isIntersection = true;
					intersection.setBelonging(p.getBelonging());

					//Send the involved segments with the intersection
					intersection.getUpperTo().add(rightNeighbour);
					intersection.getUpperTo().add(p.rightmost);
					eventQueue.insert(intersection);
				}
				else System.out.println("The right segment intersect with its right neighbour in both their lower points.");
			}
			else if(p.getUpperTo().size()==1){
				//Check the only segment against its right neighbour
				crossing = p.getUpperTo().get(0).doIntersect(rightNeighbour);
				if(crossing == null){
					System.out.println("The segment doesn't intersect with its right neighbour.");
				}
				else if(p.getUpperTo().get(0).getLower().compareTo(rightNeighbour.getLower())!=0 && p.getBelonging() != rightNeighbour.getLower().getBelonging()){
					//The segments intersect, create a new eventpoint
					Endpoint intersection = new Endpoint(crossing.getX(), crossing.getY());
					intersection.isIntersection = true;
					intersection.setBelonging(p.getBelonging());

					//Send the involved segments with the intersection
					intersection.getUpperTo().add(rightNeighbour);
					intersection.getUpperTo().add(p.getUpperTo().get(0));
					eventQueue.insert(intersection);
				}
				else System.out.println("The segment intersect with its right neighbour in both their lower points.");
			}
		}
	}



	/*This function was used to handle the last event point.
	private void handleLastPoint(Endpoint p){
		boolean intersection = false;
		if(p.isUpper){
			System.out.println("Sista eventpointen �r en upper. N�got �r fel.");
		}
		if(p.isLower){
			System.out.println("Sista eventpointen �r en lower, precis som det ska vara.");
			ArrayList<Edge> segments = new ArrayList<Edge>();
			status.deleteWithLower(p, segments);
			if(segments.size()>1){
				for(int i = 1;i<segments.size();i++){
					if(segments.get(i).getUpper().getBelonging()!=segments.get(i-1).getUpper().getBelonging()){
						intersection = true;
					}
				}
			}
		}
	}*/


	public static int incrementAndGetEdgeID(){
		edgeID++;
		return edgeID;
	}
}
