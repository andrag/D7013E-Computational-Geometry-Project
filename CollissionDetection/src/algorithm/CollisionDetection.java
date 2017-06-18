package algorithm;

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

	
	/**
	 * Constructor: Creates a sorted event queue from the two starting points of the polygons.
	 * 				Runs handleEventPoint() for each event point in order.
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
	 * @param Endpoint p
	 */
	public void handleEventPoint(Endpoint p){

		if(p.isLower){
			System.out.println("===============================Handle a lower eventpoint=============================");


			int sweep_Y = p.getRealY();
			status.updateAll(sweep_Y);

			//Delete all segments that has a lower point in p
			for(Edge e : p.getLowerTo()){
				status.remove(e);
				e.getUpper().isUpperTo.remove(e);
			}
			p.getLowerTo().clear();
			status.traverseStatus();
		}

		if(p.isUpper() && !p.isIntersection){
			System.out.println("/n================================Handle an upper event point===================================/n/n");

			int sweep_Y = p.getRealY();
			status.updateAll(sweep_Y);


			if(p.getUpperTo().size()>1){//Point p is an upper point to two segments. Add both.
				for(Edge e : p.getUpperTo()){
					e.updateXandSweep(sweep_Y);
					status.add(e);

					/*if(p.getBelonging() != e.getLower().getBelonging()){ //Check if they belong to the same polygon. No need, already checked in intersection points.
						intersecting = true;
					}
					else System.out.println("No collisiion between this uppers segments. This case should only appear in the top point of a polygon if it is really simple :)");
					 */}

				//Find out which segment is leftmost resp rightmost
				Edge left = p.findLeftmost();//Replaced by leftmost and rightmost inside Endpoints class
				Edge right = p.findRightmost();

				//Find their neighbours in the status
				Edge leftNeighbour = status.lower(left);
				Edge rightNeighbour = status.higher(right);

				//Check if the newly inserted segments intersect with their neighbours
				findNewEvent(leftNeighbour, rightNeighbour, p);
			}
			else {
				//This is an upper point to just one segment
				p.getUpperTo().get(0).updateXandSweep(sweep_Y);

				status.add(p.getUpperTo().get(0));

				//Find left and right neighbours of the new segment
				Edge leftNeighbour = status.lower(p.getUpperTo().get(0));
				Edge rightNeighbour = status.higher(p.getUpperTo().get(0));

				//Check if the new segment intersect a neighbour in the status
				findNewEvent(leftNeighbour, rightNeighbour, p);
			}
		}

		if(p.isIntersection){

			System.out.println("=====================Handling an intersection eventpoint====================\n");

			intersecting = true;
			handleIntersectingSegments(p);
			p.isUpper = true;

			Edge left = p.findLeftmost();
			Edge right = p.findRightmost();

			Edge leftNeighbour = status.lower(left);
			Edge rightNeighbour = status.higher(right);

			findNewEvent(leftNeighbour, rightNeighbour, p);
		}


		if(intersecting == true){
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
		tempSeg2.updateXandSweep(tempSeg2.getUpper().getRealY());
		status.remove(tempSeg1);
		status.remove(tempSeg2);

		//3. Remove their references at their former upper
		tempSeg1.getUpper().isUpperTo.remove(tempSeg1);
		tempSeg2.getUpper().isUpperTo.remove(tempSeg2);

		//4. Set the intersecting point as their new upper
		tempSeg1.changeUpper(p);
		tempSeg2.changeUpper(p);

		//5. Update the status such that an add will be just like adding a new upper point of two segments
		int sweep_Y = p.getRealY();
		status.updateAll(sweep_Y);

		tempSeg1.updateXandSweep(sweep_Y);
		tempSeg2.updateXandSweep(sweep_Y);

		//6. Insert the new modified segments into the status again
		status.add(tempSeg1);
		status.add(tempSeg2);
	}

	/**
	 * Takes an endpoint p that is an upper point of one or two segments.
	 * Checks if the segments originating from p intersects with the left and/or right neighbour in the status tree.
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
