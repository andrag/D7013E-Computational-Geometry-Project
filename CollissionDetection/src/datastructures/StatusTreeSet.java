package datastructures;

import java.util.Iterator;
import java.util.TreeSet;

import algorithm.CollisionDetection;
import algorithm.Edge;
import algorithm.Endpoint;

public class StatusTreeSet extends TreeSet<Edge>{

	public TreeSet<Edge> tree;
	

	public void statusTreeSet(){
		tree = new TreeSet<Edge>();
	}
	
	/**
	 * Updates the current x-coordinate of each segment in the status tree according to the current position of the sweep line.
	 * @param sweep_y
	 */
	public void updateAllCurrentXCoords(int sweep_y){
		Iterator<Edge> iterator = tree.iterator();
		while(iterator.hasNext()){
			Edge edge = iterator.next();
			edge.updateXCoord(sweep_y);
		}
	}
	
	
	public void traverseStatus(){
		System.out.println("========================================\nTRAVERSE STATUS\n========================================");
		Iterator<Edge> iterator = tree.iterator();
		while(iterator.hasNext()){
			Edge edge = iterator.next();
			System.out.println("Edge upper: (" + edge.getUpper().getX() + ", " + edge.getUpper().getRealY() + ")\t|\tEdge lower: (" + edge.getLower().getX() + ", " + edge.getLower().getRealY() + ")\t|\tCurrent X: " + edge.current_X);
		}
	}
}

