package datastructures;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.function.Consumer;

import algorithm.CollisionDetection;
import algorithm.Edge;
import algorithm.Endpoint;

public class StatusTreeSet extends TreeSet<Edge>{
	
	public void traverseStatus()
	{
		System.out.println("========================================\nTRAVERSE STATUS\n========================================");
		Iterator<Edge> iterator = this.iterator();
		while(iterator.hasNext()){
			Edge edge = (Edge) iterator.next();
			System.out.println("Edge upper: (" + edge.getUpper().getX() + ", " + edge.getUpper().getRealY() + ")\t|\tEdge lower: (" + edge.getLower().getX() + ", " + edge.getLower().getRealY() + ")\t|\tCurrent X: " + edge.current_X + "\tid: " + edge.id);
		}
	}
}

