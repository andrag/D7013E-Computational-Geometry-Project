package tests;

import algorithm.Edge;

/**
 * This class is for testing the methods that order segments in the StatusTree
 * @author Anders
 *
 */

public class CompareMethods {
	
	
	/**
	 * This method compares a point to a segment to find out if it should be to the 
	 * left or right of the segment in the StatusTree. 
	 * 
	 * Belongs to: Endpoint class, might as well be somewhere else.
	 * 
	 * @param segment
	 * @return -1, 0 or 1
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

}
