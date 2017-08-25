package algorithm;

/**
 * Project: Implementation of Efficient Collision Detection Algorithm
 * 
 * Course: D7013E Computational Geometry
 * Author: Anders Ragnarsson
 * Email: andrag-0@student.ltu.se 
 * 
 * 1. Description of the algorithm
 * 
 * Input: endpoints that make up the line segments of two polygons that may or may not overlap with each other.
 * Output: A list of intersection points between the two polygons, if the polygons overlap.
 * Time constraints: as efficient as possible, no naive solution.
 * 
 * The algorithm is a line sweep solution where the sweep line moves down from the top along the y-axis. A self balancing binary search tree called
 * the "status" holds the line segments that the sweep line crosses, but not the ones it has passed or not yet reached. In this way, only the segments 
 * within the status are compared at any given time. When the sweep line has passed the end of a segment, the segment is removed from the status tree 
 * and never considered again. The algorithm contains the following steps:
 * 
 * 		1.1. The endpoints of the polygons are sorted in a self-balancing binary search tree (BBST) calld the "queue"
 * 
 * 		1.2. For each event point in the queue*:
 * 
 * 			- If it is a lower event point:
 * 				Remove the segments from the status tree that ends in this lower point
 * 
 * 			- If it is an upper event point, but not an intersection point:
 * 				Insert all segments this point is an upper point to, into the status tree.
 * 				Check if the new segment(s) give raise to a future intersections with lines in the status tree. If yes - add it as a new event point to the queue.
 * 
 *  		- If it is an intersection event point
 *  			Add the points coordinates as an intersection in the algorithm output list
 *  			Make sure that the segments that intersects switch places correctly in the status.
 *				Check if the switching places of segments give raise to a future intersections in the status tree. If yes - add it as a new event point to the queue.
 *			
 *			* An event point can be a segments lower point, upper point, crossing between two segments, or a combination of these three types.
 *		
 * 2. Description of the implemented solution
 * 		
 * 		2.1 Event flow
 * 		The user draws two overlapping polygons in the GUI. When executing the program, these points are sent to the CollisionDetection class
 * 		that performs the algorithm through the following steps:
 * 		
 * 		1. The event points are sorted in a self-balancing binary search tree (BBST) in O(log(n)) time
 * 		2. The status tree is initialized, also as a BBST
 * 		3. For each event point in the queue
 * 				- Update the sweep line to hold the y value of point p
 * 				- Call handleEventPoint(Endpoint p)
 * 				- If p is an upper point:
 * 					- Call handleNewEvent() to find out if the newly inserted segments cross their neighbouring segments in the status tree.
 * 				- If p is an intersection:
 * 					- Call handleIntersectingSegments()
 * 					- Call handleNewEvent() to find new intersections
 * 				- See JavaDoc of the methods below for more details
 * 
 * 
 * 
 * 		2.2 System Architecture
 * 		The solution is divided into three packages; main, datastructures and algorithm. 
 * 		 
 *  	 The algorithm package holds the algorithm implementation and all the logic. Edges and Endpoint objects are what the polygons are made of. 
 *  	They hold references to each other, what polygon they belong to, as well as math functions for deciding on which side of a line a point lies and so on. 
 *  	Endpoints objects keep track of which Edges they are upper and lower points to. Edges objects has a compareTo method that is used by the TreeSet (the data structure 
 *  	for the status tree) to keep track of where a segments should lie in the status tree. Based on the current value of the sweep line, compareTo 
 *  	calculates the current value of a segments x-coordinate as the sweep line changes. The TreeSet sorts the segments from low to high value of 
 *  	current x-coordinates of the segments. To avoid having to update the entire tree when it's not needed, which would destroy the time complexity, 
 *  	the current x-coordinate of each segment is only updated when something new happens to it, like an intersection is detected
 *  	involving that segment or the segment is compared with a new segment by TreeSets insert-method.
 *  
 *		 The datastructures package contains the binary search trees used for the event queue and the status tree while the main package contains the
 *		GUI implementation. The reason why there are two BBST implementations in the datastructures package is because the AVLTree implementation was not good enough
 *		for use as a status tree, so it was replaced by a Java TreeSet for the status.
 * 
 * 
 * 	3. Time complexity
 * 		Both the event queue and the status tree are self-balancing binary search trees that ensures O(log n) for insert, retrieve and 
 * 		delete operations. Only edges that contain the current y-coordinate of the sweep line can exist together in the status tree at any instant in time. 
 * 		For each arriving event point the status tree is searched a (small) constant number of times, giving k * log(n) time per event point.
 * 		Having n end points and I intersections gives k*n*log(n) + I * log(n) time for the entire algorithm, or O(n log(n) + I log(n)) since k is small. 
 * 
 * 
 * 3. User manual
 * 		- Paint two polygons in the GUI drawing area by clicking with the mouse for each point in the polygons. 
 * 		- Do not cross a polygon with itself.
 * 		- To make the last line segment in a polygon, press "Finish polygon" button. 
 * 		- When two polygons are made, click "Compute collision" to run the algorithm.
 * 		- For each new run, a file is saved with the latest input data. To use it, hard code the variable String "loadFile" in the GUI class to be the name of the
 * 		  file you want to run. The files are written to the src directory. To draw and save your own polygons, leave "loadFile" an empty string ""
 * 
 * 
 * 4. References
 * 		1. Code for AVL tree inspired from http://stackoverflow.com/questions/5771827/implementing-an-avl-tree-in-java?rq=1
 * 		2. The sweep line algorithm inspired from the book Computational Geometry - Algorithms and Applications, 3d edition, by Mark de Berg et al. 
 * 		3. Segment intersection math: http://www.ahristov.com/tutorial/geometry-games/intersection-segments.html
 */





import java.util.ArrayList;
import datastructures.*;


public class CollisionDetection extends ArrayList<Endpoint>{

	private static final long serialVersionUID = 1L;
	private AVLTree eventQueue;
	private StatusTreeSet status;
	private ArrayList<Endpoint> allIntersections;

	private static int edgeID = 0;//For keeping track of all the edges
	private static boolean intersecting = false;

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

		//Initialize the status tree
		status = new StatusTreeSet();
		
		//Run the algorithm for each event point
		while(eventQueue.root.left!=null || eventQueue.root.right!=null){
			Endpoint next = eventQueue.findLargest(eventQueue.root);
			eventQueue.traverseInOrder();
			eventQueue.delete(next);
			handleEventPoint(next);
		}
	}


	/**
	 * handleEventPoint(Endpoint p)
	 * 
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
			
			//1.1 Update the sweep line to be at the y coordinate of p
			sweep_Y = p.getRealY();
			
			//1.2 Delete all segments in the status that has a lower point in point p
			for(Edge e : p.getLowerTo()){
				status.remove(e);
				status.traverseStatus();
				e.getUpper().isUpperTo.remove(e);
			}
			p.getLowerTo().clear();
			status.traverseStatus();
		}

		//2. Endpoint p is an uppe point to one or more segments, but not an intersection.
		if(p.isUpper() && !p.isIntersection){
			
			//2.0 Check if p is on a line of another polygon -> intersection - Fix this in compareTo to not break the time
			
			
			//2.1 Update the sweep line
			sweep_Y = p.getRealY();

			//2.2 Check if p is an upper point to two segments.
			if(p.getUpperTo().size()>1){
				
				// Add both to the status.
				for(Edge e : p.getUpperTo()){
					e.updateXandSweep(sweep_Y);
					status.add(e);
					}

				// Find out which segment is leftmost resp rightmost
				Edge left = p.findLeftmost();
				Edge right = p.findRightmost();

				// Find their neighbours in the status
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
		
		// 3. Check if p is an intersection point between two segments 
		// (Can never be more since there are two polygons and none is allowed to cross itself)
		if(p.isIntersection){

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
	}




	public ArrayList<Endpoint> getIntersections(){
		return allIntersections;
	}

	
	/**
	 * Handles segments involved in an intersection.
	 * Performs cutting of the parts of the segments that lie above intersection point p.
	 * Deletes the old segments and insert the new shorter ones into the status. This reorders them in the status tree.
	 * @param p An intersection point between two segments 
	 */
	private void handleIntersectingSegments(Endpoint p){
		//1. Get the segments that are involved in the intersection (Only two are possible in this implementation, otherwise they cross themselves = not ok)
		Edge tempSeg1 = p.getUpperTo().get(0);
		Edge tempSeg2 = p.getUpperTo().get(1);

		//2. Delete them from the status
		status.remove(tempSeg1);
		status.remove(tempSeg2);

		//3. Remove the references at their former upper point
		tempSeg1.getUpper().isUpperTo.remove(tempSeg1);
		tempSeg2.getUpper().isUpperTo.remove(tempSeg2);

		//4. Set the intersecting point as their new upper point
		tempSeg1.changeUpper(p);
		tempSeg2.changeUpper(p);

		//5. Update the sweep line such that an add to the status will be just like adding a new upper point of two segments
		sweep_Y = p.getRealY();

		//6. Update the sweep_y of the edges such that they will now be inserted in the new order
		tempSeg1.updateXandSweep(sweep_Y);
		tempSeg2.updateXandSweep(sweep_Y);

		//7. Insert the new modified segments into the status again
		status.add(tempSeg1);
		status.add(tempSeg2);
	}

	
	
	/**
	 * findNewEvent(Edge leftNeighbour, Edge rightNeighbour, Endpoint p)
	 * 
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
	public void findNewEvent(Edge leftNeighbour, Edge rightNeighbour, Endpoint p){
		Endpoint crossing;

		if(leftNeighbour==null){
			System.out.println("There is no left neighbour.");
		}
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

	public static int incrementAndGetEdgeID(){
		edgeID++;
		return edgeID;
	}
	
	public static void intersectionFound(){
		intersecting = true;
	}
}
