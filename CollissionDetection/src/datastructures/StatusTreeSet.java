package datastructures;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.function.Consumer;

import algorithm.CollisionDetection;
import algorithm.Edge;
import algorithm.Endpoint;

public class StatusTreeSet extends TreeSet<Edge>{


	
	/**
	 * 2017-06-03 LIte tankar
	 * Updates the current x-coordinate of each segment in the status tree according to the current position of the sweep line.
	 * @param sweep_y
	 * 
	 * Former implementation only swapped after a crossing of two elements that are intersecting and the intersecting point is not a lower endpoint.
	 * We will still need o know the current y on each segment in the status. 
	 * The question is, by iterating this once for each new event point, do we infer bad time? Is it enough to only update this at the time when we
	 * are loking at each segment anyway?
	 *
	 * Algorithm after event points has been queued:
	 * 	1. Examine a new event point. If
	 * 			1.1 New upper
	 * 				1.1.1 If upper to many
	 * 					1.1.1.1 Insert one into the status
	 * 						1.1.1.1.1 For each check, update the currentXCoord of each segment before comparing!
	 * 				1.1.2 Else
	 * 					1.1.2.1 Handle one
	 * 
	 * 			1.2 Intersection
	 * 
	 * 			1.3 Lower
	 */
	/*public void updateAllCurrentXCoords(int sweep_y){
		Iterator<Edge> iterator = tree.iterator();
		while(iterator.hasNext()){
			Edge edge = iterator.next();
			edge.updateXCoord(sweep_y);
		}
	}*/
	
	
	public void traverseStatus(){
		System.out.println("========================================\nTRAVERSE STATUS\n========================================");
		Iterator<Edge> iterator = this.iterator();
		while(iterator.hasNext()){
			Edge edge = (Edge) iterator.next();
			System.out.println("Edge upper: (" + edge.getUpper().getX() + ", " + edge.getUpper().getRealY() + ")\t|\tEdge lower: (" + edge.getLower().getX() + ", " + edge.getLower().getRealY() + ")\t|\tCurrent X: " + edge.current_X + "\tid: " + edge.id);
		}
	}
}

