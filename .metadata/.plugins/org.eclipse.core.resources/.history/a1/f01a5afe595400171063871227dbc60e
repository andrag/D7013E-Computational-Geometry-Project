package algorithm;

import java.util.ArrayList;
import java.util.logging.Logger;

import javax.swing.text.Segment;

import datastructures.*;
import algorithm.*;

public class CollisionDetection extends ArrayList<Endpoint>{

	private Logger log;
	private static final long serialVersionUID = 1L;
	private AVLTree eventQueue;
	//private StatusTree status;
	private StatusTreeSet status;
	private ArrayList<Endpoint> eventPoints;
	private ArrayList<Endpoint> allIntersections;

	private static int edgeID = 0;//For keeping track of all the edges

	//Papa's got a brand new bag
	//Guess these are replaced by arrays within the Endpoints themselves
	private ArrayList<Edge> intersectionLower = new java.util.ArrayList<Edge>();
	private ArrayList<Edge> intersectionInterior = new ArrayList<Edge>();

	private boolean intersecting = false;
	
	private int debugCounter = 0;

	//private static int sweep_y; //Idea. Use this to get hold of sweep_y from inside Edge and/or Endpoint. Important. Should not be modified from anywhere other than this class.

	public CollisionDetection(Endpoint startpoint1, Endpoint startpoint2){
		eventPoints = new ArrayList<Endpoint>();
		//Make an eventqueue BBST
		eventQueue = new AVLTree();
		//Build the eventqueue
		Endpoint current = startpoint1;
		allIntersections = new ArrayList<Endpoint>();

		//While next segment != startpoint.prev --> loop. This fails if two lines in a polygon are co-linear but they should not be.
		while(current.getNextSeg().getEnd()!=startpoint1){//
			eventQueue.insert(current);	//Kommenterade ut ovan eftersom det ger dubbel insättning. 
			//Detta funkar endast om eventpoints kan vara både upper och lower samtidigt.
			current = current.getNextSeg().getEnd();
		}
		eventQueue.insert(current);
		current = startpoint2;
		while(current.getNextSeg().getEnd()!=startpoint2){//
			eventQueue.insert(current);
			current = current.getNextSeg().getEnd();
		}
		eventQueue.insert(current);

		//For debugging
		System.out.println("is Root null?: "+ eventQueue.root==null);
		System.out.println("is Roots eventpoint null?: "+eventQueue.root==null);//Ger nullpointer. Noderna har ingen data.
		System.out.println("Now traverse the tree");
		eventQueue.traverseInOrder();
		System.out.println("Is the queue empty?");
		System.out.println(eventQueue.isEmpty());

		//SaveAndLoad.saveEventQueue(eventQueue);
		String savedQueue = "Log_2017_04_02_20-24-51.ser";
		//eventQueue = SaveAndLoad.EventQueue(savedQueue);

		//Initialize the empty status structure
		//status = new StatusTree();//Statusen ska ordnas på ett annat sätt. Ska ordnas efter hur segmenten ligger i x-riktningen där sweeplinjen befinner sig.
		status = new StatusTreeSet();


		//Gör en insertfunktion i StatusTree som tar ett y-värde som parameter. Jämför de olika nodernas x-värden i detta y-värde. Typ räta linjen?
		while(eventQueue.root.left!=null || eventQueue.root.right!=null){//!eventQueue.isEmpty()
			System.out.println("==================================================\n\nWhile loop entered.");

			Endpoint next = eventQueue.findLargest(eventQueue.root);

			System.out.println("Deleting eventpoint p from queue. p: "+"("+next.getX()+", "+next.getRealY()+")");

			System.out.println("Traverse the event-tree");
			eventQueue.traverseInOrder();

			//Varning! Denna datastruktur är felkodad. Klarar inte att deleta roten. Nullpointer i sista steget. 
			eventQueue.delete(next);//Next är en endpoint som är null. Noden med största värde har null data av någon anledning.
			System.out.println("Calling handleEventPoint(p)");

			handleEventPoint(next);
		}
		System.out.println("There is only one event point left in the queue. Handle it now!");
		//pInOtherSegments(eventQueue.root.getEventpoint());
		//handleLastPoint(eventQueue.root.getEventpoint());

	}

	/* TODO
	 * 1. Enforce the rules in compareTo test in main
	 * 		- Make an update x and sweepY in Edge (CHECK)
	 * 		- Initialize current_X in the edges constructors to their upper (CHECK)
	 * 		- initialize sweep_y to null such that they only have value when they are in the status (No. Skip init of this.)
	 * 		- Check that update is used before each operation on the status (CHECK)
	 * 		- Make an updateAll method in the status (CHECK)(will this break the time??? The status is less than n at least. For each iteration we willl iterate twice = factor of 2. Ok!)
	 * 		- Check that the segments are removed from each isUpperTo/isLowrTo array when removed permanently (CHECK)
	 * 		- Fix switching places of segments in the status (CHECK)
	 * 		- Check that status.lower(leftmost) return null or something (CHECK)
	 * 
	 *  	- Fix unit tests of
	 *  				* Handling intersections
	 *  				* Handling new event points
	 * 
	 * 
	 * Old todo
	 * 1. Make sure that status.remove(e) removes the correct edge. Does compareTo() in edge work as it should?
	 * 2. Check that status.add(e) adds a new segment in the correct place
	 * 3. Check that findLftmost and rightmost works as intended
	 * 4. Check that status.higher(e) and lower works as intended with edge.compareTo()
	 * 5. Find out if currentXCoord is correct
	 * 
	 */


	//2017-04-09
	public void handleEventPoint(Endpoint p){
		//Om två punkter i två polygoner sammanstrålar här så händer följande:
		/* Polygon 1:s lower upptäcks. Föregående segment tas bort.
		 * Polygon 1:s upper gör att ett nytt segment sätts in - nu ska det upptäckasatt denna upper sammanstrålar med polygon 2:s punkt.
		 * Görs detta test?
		 */
		
		debugCounter++;
		if(debugCounter == 15)
		{
			System.out.println("Time to debug :)");
		}
		
		if(p.isLower){
			System.out.println("===============================Handle a lower eventpoint=============================");
			
			
			int sweep_Y = p.getRealY();
			status.updateAll(sweep_Y);

			int count = 0;
			//Count how many lowers we need to delete
			for(Edge e : p.getLowerTo()){
				count++;
			}
			System.out.println("p is lower to "+count+" segments.");
			System.out.println("Traverse the status before deleting.");
			status.traverseStatus();
			//At some point we try to delete a lower point with an upper that has never existed.
			for(Edge e : p.getLowerTo()){//Tries to delete all segments the lower point refers to but there is only one segment left in the tree
				System.out.println("Delete the segment: " + e.id);
				status.remove(e);
				e.getUpper().isUpperTo.remove(e);//2017-06-04 Ooops. Forgot about this :P Added.
			}
			p.getLowerTo().clear();
			
			System.out.println("Handled a lower. Deletd shit. Traversing the fucking tree.");
			status.traverseStatus();
			
			/*int sweep_Y = p.getRealY(); <--- Should these be after remove to avoid "sweep_y lies outside blabla" ?
			status.updateAll(sweep_Y);*/
		}


		//Kolla nya punktens intersections endast då nya segment ska in i statusen, p är en upper.
		if(p.isUpper() && !p.isIntersection){
			System.out.println("/n================================Upper inserted===================================/n/n");

			int sweep_Y = p.getRealY();
			status.updateAll(sweep_Y);// <------- SWEEP_Y lies outside blablabla! 2017-06-18 Obegripligt! Ska inte finnas några, ska ha tagits bort ovan.
			
			//Used to be an out commented code part here for checking if a new upper is in the interior of an existing segment.
			//That should be spotted by find new event and doIntersect...Or is it?
			if(p.getUpperTo().size()>1){//p is upper to several segments. Check if it's an intersection. Double check in case of p being an intersection eventpoint
				//Check belongings of the polygons. Do we have an intersection?

				System.out.println("This upper point is upper to segments: ");
				for(Edge e : p.getUpperTo()){//Should work
					System.out.println(e.id);
					e.updateXandSweep(sweep_Y);
					
					
					status.add(e);//sjdh

					//How to report the edges involved? No need :)
					if(p.getBelonging() != e.getLower().getBelonging()){
						intersecting = true;//This will report this upper point as an intersection point. Don't do that again in findNewEvent below.
						System.out.println("Collision point found under isUpper() check.");
					}
					else System.out.println("No collisiion between this uppers segments. This case should only appear in the top point of a polygon if it is really simple :)");
				}

				//Findleft/rightmost must be run to set left/rightmost in the Endpoint
				Edge left = p.findLeftmost();//Replaced by leftmost and rightmost inside Endpoints class
				Edge right = p.findRightmost();

				//Find the left neighbour of the left segment
				Edge leftNeighbour = status.lower(p.leftmost);//Bug?
				Edge rightNeighbour = status.higher(p.rightmost);//Bug!!!
				
				status.traverseStatus();

				//Kör findNewEvent på dessa
				findNewEvent(leftNeighbour, rightNeighbour, p, false);
			}
			else {
				//This is an upper point to just one segment
				System.out.println("Upper to segment: " + p.isUpperTo.get(0));
				p.getUpperTo().get(0).updateXandSweep(sweep_Y);
				
				status.add(p.getUpperTo().get(0));//p is only upper to one segment

				//Find left and right neighbours of p
				Edge leftNeighbour = status.lower(p.getUpperTo().get(0));
				Edge rightNeighbour = status.higher(p.getUpperTo().get(0));//Returns itself if there is no neighbour. Bad!

				//Throw them as arguments togehter with p to findNewEvent
				findNewEvent(leftNeighbour, rightNeighbour, p, false);//Detta är fel! Ska kolla leftneighbour mot segmentet och rightneighbour mot segmentet.
			}
			//Fix newEvent..... eh what? 
		}

		if(p.isIntersection){ //Could it be upper and intersection at once?? If it could then the x-coord might already have been updated and deletions of the segments for rearranging might not work.

			System.out.println("=====================Handling an intersection eventpoint====================\n");

			intersecting = true;
			handleIntersectingSegments(p);

			//Don't think this is needed. Is it bad??
			p.isUpper = true;//Tvivelaktigt om deta behövs. Kan vara bra för insertion

			//findNewEvent must be run to set the leftmost and rightmost within the Edge object
			Edge left = p.findLeftmost();
			Edge right = p.findRightmost();

			Edge leftNeighbour = status.lower(p.leftmost);
			Edge rightNeighbour = status.higher(p.rightmost);//Istället för right... Gör nog ingen skillnad.

			findNewEvent(leftNeighbour, rightNeighbour, p, false);
		}


		if(intersecting == true){//Feltänk här. Måste bygga på antal element som upper är upper till.  <------------Fortfarande??
			System.out.println("p är en intersection point, är nu upper till mer än ett segment. Lägg till p i intersection-arrayen.");
			allIntersections.add(p);//This might already be added in findNewEvent!!!
			intersecting = false;
		}
		System.out.println("\n====================================Slut på handleEventPoint.==========================================");
	}




	public ArrayList<Endpoint> getIntersections(){
		return allIntersections;
	}


	//Är det ens nödvändigt att beskära elementen??

	/**
	 * Performs a cutting of the parts of the segments that lie above p
	 * Deletes the old segments and insert the new shorter ones into the status. This reorders them in the status tree.
	 * @param p An intersection point between two(?) segments 
	 */
	private void handleIntersectingSegments(Endpoint p){
		//1. Get the segments that are involved in the intersection (Only two are possible in this implementation, otherwise they cross themselves = not ok)
		Edge tempSeg1 = p.getUpperTo().get(0);
		int id1 = tempSeg1.id;
		
		Edge tempSeg2 = p.getUpperTo().get(1);
		int id2 = tempSeg2.id;
		
		//2. Delete them from the status.... How to do this when there is an intersection??? The point will lie on the line but they will be reoredered under the point so the search will not find it.
		//Solution - temporarily set the current_x of the segments to their FORMER value.Wait with updating til after this deleting is done!
		System.out.println("handleIntersection removes uncropped segments: " + tempSeg1.id + " and " + tempSeg2.id);
		System.out.println("Status before removal");
		status.traverseStatus();
		
		tempSeg1.updateXandSweep(tempSeg1.getUpper().getRealY());
		tempSeg2.updateXandSweep(tempSeg2.getUpper().getRealY());
		
		status.remove(tempSeg1);
		status.remove(tempSeg2); //<----------------------------------------THIS ONE FAILS TO REMOVE SEGMENT 5 (2017-06-18)
		
		System.out.println("Status after removal");
		status.traverseStatus();
		
		//3. Remove their references at their former upper
		tempSeg1.getUpper().isUpperTo.remove(tempSeg1);
		tempSeg2.getUpper().isUpperTo.remove(tempSeg2);
		
		//4. Set the intersecting point as being their new upper
		tempSeg1.changeUpper(p);
		tempSeg2.changeUpper(p);
		
		

		//3. Remove the references to them at their lower points - This should not be needed if we change the old segments instead of making new ones.
		/*Endpoint tempLower = tempSeg1.getLower();
		int index = -1;
		for(Edge segment : tempLower.getLowerTo()){
			if(segment.compareTo(tempSeg1)==0){
				index = tempLower.getLowerTo().indexOf(segment);
			}
		}
		if(index != -1){
			tempLower.getLowerTo().remove(index);
		}
		tempLower = tempSeg2.getLower();
		index = -1;
		for(Edge segment : tempLower.getLowerTo()){
			if(segment.compareTo(tempSeg2)==0){
				index = tempLower.getLowerTo().indexOf(segment);
			}
		}
		if(index != -1){
			tempLower.getLowerTo().remove(index);
		}*/

		//4. Make the Edge objects point their upper to point p, the intersection
		

		//5. Point p to be upper to the new shorter segments. Do we need to do something with start and end points in the Edge object?
		/*p.clearSegmentsInUpperTo();
		p.addUpperTo(tempSeg1);//This should be done in the changeUpper of the segment
		p.addUpperTo(tempSeg2);*/

		//6. Add the shortened segments to the lower points isLowerTo-arrays
		/*tempSeg1.getLower().addLowerTo(tempSeg1);
		tempSeg2.getLower().addLowerTo(tempSeg2);*/
		
		//5. Update the status such that an add will be just like adding a new upper point of two segments
		int sweep_Y = p.getRealY();
		status.updateAll(sweep_Y); //<------------- Update sweep_y of tempSeg1 and tempSeg2  as well! Forgot!
		
		tempSeg1.updateXandSweep(sweep_Y);
		tempSeg2.updateXandSweep(sweep_Y);

		//6. Insert the new modified segments into the status again
		status.add(tempSeg1);
		status.add(tempSeg2);

		//Print the changes to the intersecting segments
		System.out.println("===========================================\nAn intersection as been handled. Traverse the status tree.\n______________________________________________");
		status.traverseStatus();
	}

	/*
	 * Egentligen ska denna ta en leftneighbour och en right neighbour samt p som jämför left och right med sitt segment.
	 * Om p är upper till massa segment --> gör check för leftmost och rightmst här?
	 */

	public void findNewEvent(Edge leftNeighbour, Edge rightNeighbour, Endpoint p, boolean lower){//Borde köra point här istället så kan man kolla o den ska leta upp leftmost och rightmost.
		Endpoint crossing;
		if(!lower){//Do a check here if we are handling a lower or an upper.... What if it's both? Good question. //This is never used

			//Check if left neighbour is null
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
					//Check so the crossing is not in the same lower.
					else if(!(leftNeighbour.getLower().compareTo(p.leftmost.getLower())==0)){ //if(crossing.compareTo(left.getUpper())!=0 && crossing.compareTo(p.leftmost.getUpper())!=0){//To avoid a loop where the intersection will discover itself again and again.

						//The segments intersect, create a new eventpoint if they belong to different polygons
						if(leftNeighbour.getLower().getBelonging() != p.leftmost.getLower().getBelonging()){
							System.out.println("The left segment cross its left neighbour, create new event point.");
							System.out.println("They intersect at: ("+crossing.getX()+", "+crossing.getRealY()+")");
							Endpoint intersection = new Endpoint(crossing.getX(), crossing.getY());
							intersection.isIntersection = true;
							intersection.setBelonging(p.getBelonging());

							//Send the involved segments with the intersection
							intersection.getUpperTo().add(leftNeighbour);
							intersection.getUpperTo().add(p.leftmost);
							eventQueue.insert(intersection);
							//allIntersections.add(intersection);//Maybe not needed here. This is doubled in the last statement in handleEventPoitn. Doesn't matter.
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
						System.out.println("The segment cross its left neighbour, create new event point.");
						//The segments intersect, create a new eventpoint

						//Check if the line ends on its neighbour or totally cross it. If it ends on the neighbour, just create
						//a new upper would suffice, instead of an intersection. No swapping necessary.
						if(p.getUpperTo().get(0).getLower() == crossing){
							//Create a new upper. Set isUpperTo till en mindre version av det gamla. Borde funka.

							//Har inte gjort något med denna. Bara speciella fall. Kanske inte ens behövs.
						}

						Endpoint intersection = new Endpoint(crossing.getX(), crossing.getY());
						//intersection.isUpper = true;
						intersection.isIntersection = true;
						intersection.setBelonging(p.getBelonging());//Kanske är detta fel?

						//Send the involved segments with the intersection
						intersection.getUpperTo().add(leftNeighbour);
						intersection.getUpperTo().add(p.getUpperTo().get(0));
						eventQueue.insert(intersection);
						//Also set p to isUpper?? Annars gör om alla checkar att kolla om UpperTo-arrayen är tom eller inte.

					}


					else if(p.getUpperTo().get(0).getLower().compareTo(leftNeighbour.getLower())!=0 && p.getBelonging() == leftNeighbour.getLower().getBelonging()){
						System.out.println("Jaja segmentet korsar sin vänstra granne men de tillhör samma polygon.");
						System.out.println("Den vänstra grannen är segmentet: ("+leftNeighbour.getUpper().getX()+", "+leftNeighbour.getUpper().getRealY()+"), ("+leftNeighbour.getLower().getX()+", "+leftNeighbour.getLower().getRealY()+")");
					}

					else System.out.println("The segment intersect with its left neighbour in both their lower points. Do something about this special case if needed.");

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
					else if(p.rightmost.getLower().compareTo(rightNeighbour.getLower())!=0 && p.rightmost.getLower().getBelonging() != rightNeighbour.getLower().getBelonging()){// if(crossing.compareTo(right.getUpper())==0 && crossing.compareTo(p.rightmost.getUpper())!=0){
						System.out.println("The right segment cross its right neighbour, create new event point.");
						System.out.println("They intersect at: ("+crossing.getX()+", "+crossing.getRealY()+")");
						//The segments intersect, create a new eventpoint
						Endpoint intersection = new Endpoint(crossing.getX(), crossing.getY());
						//intersection.isUpper = true;
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
					else if(p.getUpperTo().get(0).getLower().compareTo(rightNeighbour.getLower())!=0 && p.getBelonging() != rightNeighbour.getLower().getBelonging()){//Index out of bounds exception! Crossing har inget i sin array??
						System.out.println("The segment cross its right neighbour, create new event point.");
						//The segments intersect, create a new eventpoint
						Endpoint intersection = new Endpoint(crossing.getX(), crossing.getY());
						//intersection.isUpper = true;
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
		if(lower){ //This code is never run I think as of 2015-05-12
			//This code shouldn't be run when we deal with closed polygons. There will always be an upper directly after the lower.


			if(leftNeighbour!=null && rightNeighbour !=null &&! p.isUpper){//Added a check against upper coinciding with lower. Then should not check the neighbours, already done against the uppers segment(s).
				//Check left and right neighbours against eachother
				crossing = leftNeighbour.doIntersect(rightNeighbour);
				if(crossing==null){
					System.out.println("A lower endpoint should now have been deleted. It's two neighbours doesn't intersect.");
				}
				else if(leftNeighbour.getLower().compareTo(rightNeighbour.getLower())!=0){//A check here against lower being the same as an intersection? No. Should be caught in the first findIntersectingSegments. I guess. This executes the lowers segment. I think.
					System.out.println("A lower endpoint has probably been deleted. It's neighbours intersect.");
					Endpoint intersection = new Endpoint(crossing.getX(), crossing.getY());
					//intersection.isUpper = true;
					intersection.isIntersection = true;
					intersection.setBelonging(p.getBelonging());

					//Send the involved segments with the intersection
					p.getUpperTo().add(leftNeighbour);
					p.getUpperTo().add(rightNeighbour);
					eventQueue.insert(intersection);
				}
				else System.out.println("The lowers neighbours cross and their lower points coincide. No need for a new intersection point.");
			}
			else{
				System.out.println("A lower endpoint has probably been deleted. At least one of its neighbours is null. No new intersections can be found here.");
			}
		}
	}

	//Test för att se om allt funkar.

	/*This function was used to handle the last event point. Wait with this for now (2017-06-03)
	private void handleLastPoint(Endpoint p){
		boolean intersection = false;
		if(p.isUpper){
			System.out.println("Sista eventpointen är en upper. Något är fel.");
		}
		if(p.isLower){
			System.out.println("Sista eventpointen är en lower, precis som det ska vara.");
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


	/*public int getSweepY(){
		return sweep_y;
	}*/

	public static int incrementAndGetEdgeID(){
		edgeID++;
		return edgeID;
	}
}
