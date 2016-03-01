package algorithm;

import java.util.ArrayList;

import javax.swing.text.Segment;

import datastructures.*;
import algorithm.*;

public class CollisionDetection extends ArrayList<Endpoint>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AVLTree eventQueue;
	private StatusTree status;
	private ArrayList<Endpoint> eventPoints;
	private ArrayList<Endpoint> allIntersections;

	//Papa's got a brand new bag
	private ArrayList<Edge> intersectionLower = new java.util.ArrayList<Edge>();
	private ArrayList<Edge> intersectionInterior = new ArrayList<Edge>();

	private boolean intersecting = false;

	public CollisionDetection(Endpoint startpoint1, Endpoint startpoint2){
		eventPoints = new ArrayList<Endpoint>();
		//Make an eventqueue BBST
		eventQueue = new AVLTree();
		//Build the eventqueue
		Endpoint current = startpoint1;
		allIntersections = new ArrayList<Endpoint>();
		//While next segment != startpoint.prev --> loop. This fails if two lines in a polygon are co-linear but they should not be.
		while(current.getNextSeg().getEnd()!=startpoint1){//
			//eventQueue.insert(current.getNextSeg().getUpper());
			//eventQueue.insert(current.getNextSeg().getLower());
			eventQueue.insert(current);	//Kommenterade ut ovan eftersom det ger dubbel insättning. 
			//Detta funkar endast om eventpoints kan vara både upper och lower samtidigt.
			current = current.getNextSeg().getEnd();
		}
		eventQueue.insert(current);
		//eventQueue.insert(current.getNextSeg().getUpper());
		//eventQueue.insert(current.getNextSeg().getLower());//Is this approach doubling the same points??? Yes. 
		//Som det ser ut nu bygger varje startpunkt i en edge på latestpoint och latestpoint är föregångarens
		//endpoint. Sätts start och end till annat då Edge skapas så att punkterna blir korrumperade?
		//Dvs... kan en punkt vara både upper och lower samtidigt utan att det gör något?

		current = startpoint2;
		while(current.getNextSeg().getEnd()!=startpoint2){//
			eventQueue.insert(current);
			//eventQueue.insert(current.getNextSeg().getLower());
			current = current.getNextSeg().getEnd();
		}
		eventQueue.insert(current);
		//eventQueue.insert(current.getNextSeg().getUpper());
		//eventQueue.insert(current.getNextSeg().getLower());

		/*
		int counter = 0;

		for(Endpoint e : eventPoints){
			eventQueue.insert(e);
			counter++;
		}*/

		System.out.println("is Root null?: "+ eventQueue.root==null);
		System.out.println("is Roots eventpoint null?: "+eventQueue.root==null);//Ger nullpointer. Noderna har ingen data.

		System.out.println("Now traverse the tree");
		eventQueue.traverseInOrder();
		System.out.println("Is the queue empty?");
		System.out.println(eventQueue.isEmpty());

		//Initialize the empty status structure
		status = new StatusTree();//Statusen ska ordnas på ett annat sätt. Ska ordnas efter hur segmenten ligger i x-riktningen där sweeplinjen befinner sig.
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
		handleLastPoint(eventQueue.root.getEventpoint());

	}

	
	public ArrayList<Endpoint> getIntersections(){
		return allIntersections;
	}


	// 1. Kolla om den insatta punkten sammanfaller med något element i statusen
	// 2. Spara alla lower och intersections, typ - dessa är det som ska hittas
	public void handleEventPoint(Endpoint p){ 

		//Ny check för lowers 2015-05-12. Sätt den innan uppers-checken istället. 
		//Ville att uppers skulle kollas först för att det ska upptäckas om en upper från en polygon korsar en lower från en annan.
		//Om två punkter i två polygoner sammanstrålar här så händer följande:
			/* Polygon 1:s lower upptäcks. Föregående segment tas bort.
			 * Polygon 1:s upper gör att ett nytt segment sätts in - nu ska det upptäckasatt denna upper sammanstrålar med polygon 2:s punkt.
			 * Görs detta test?
			 */
		if(p.isLower){
			System.out.println("===============================Handle a lower eventpoint=============================");
			
			int count = 0;
			//Count how many lowers we need to delete
			for(Edge e : p.getLowerTo()){
				count++;
			}
			System.out.println("p is lower to "+count+" segments.");
			
			for(Edge e : p.getLowerTo()){
				status.delete(e, p.getRealY()); //Delete all segments in a polygon that share p as their lower point
			}
			
			System.out.println(count + "Segments were deleted due to the lower endpoint of: ("+p.getX()+", "+ p.getRealY()+ ")");
		}
		
		
		//Kolla nya punktens intersections endast då nya segment ska in i statusen, p är en upper.
		if(p.isUpper()){//Även en check mot intersections? Men inga intersections ska vara satta till upper.
			System.out.println("/n================================Upper inserted===================================/n/n");
			System.out.println("p: ("+p.getX()+","+p.getRealY()+") is an upper eventpoint. \nCheck if it intersects with any existing segment in the status.");

			//Fel  denna metod - fångar inte upp att en intersection är en intersection
			//status.findIntersectingSegments(p, intersectionLower, intersectionInterior);//Denna måste köras innan segmentet sätts in i status tree. Annars kommer den upptäcka sig själv.

			//Följande if-sats är utkommenterad. Den ska hitta om p ligger på en lower-point. Redan omhändertaget ovan. 
			//Om lika-> rapportera p som intersection tillsammans med U(p), L(p) och C(p), dvs alla inblandade segment
			//Case: p is both lower and upper
			/*if(!intersectionLower.isEmpty()){
				//p intersects with a segments lower point
				System.out.println("It intersects with "+ intersectionLower.size()+" other segments lower point. Do they belong to different polygons?");
				for(Edge e : intersectionLower){//Borde kunna använda deleteWithLower här istället
					//Does p and e belong to different polygons?
					if(p.getBelonging()!=e.getLower().getBelonging()){
						//We have an intersection between the two polygons
						intersecting = true;
						System.out.println("Yes. This is a collision.");
					}
					else System.out.println("No collision.");
					//Delete the segments in L(p) from status
					System.out.println("Traversing the tree.");
					status.traverseInOrder();
					System.out.println("Deleting the lower segment with upper: ("+e.getUpper().getX()+", "+e.getUpper().getRealY()+") and lower: ("+e.getLower().getX()+", "+e.getLower().getRealY()+")");
					boolean delete = status.delete(e, p.getRealY());
					if(delete){
						System.out.println("The deletion of the segment with the lower endpoint was successful.");
					}
					else System.out.println("The deletion of the segment with the lower endpoint was NOT successful.");
					
					System.out.println("\n==========================\n==================Traverse the treee!!!\n=============\n");
					status.traverseInOrder();

				}
				intersectionLower.clear();//No need to report edges, erase this.
				
			}*/

		
			//Skit i att kolla om p ligger inuti ett befintligt segment. Kan kollas när resten funkar.
			//Fel här! En interior point misar denna check! Åtgärdat med att ha en egen check för interior
			//Denna check måste ändå finnas för att hitta intersections som uppstår då nya segment har en upper i ett annat segment interior.
			/*if(!intersectionInterior.isEmpty()){
				System.out.println("The point intersects "+intersectionInterior.size()+" other segments interior");
				//Beskär det intersectade segmentet genom att skapa nya, kortare varianter som läggs till statusen.
				for(Edge e : intersectionInterior){
					Edge newEdge;
					System.out.println("Is this a collision point?");
					//Check for intersections between polygons. Denna checken kan tas bort om det funkar nu. Detta tas hand om i caset med flera segment per upper under denna check.
					if(e.getLower().getBelonging()==p.getBelonging()){
						newEdge = new Edge(p, e.getLower());//p.isUpperTo får detta segment automatiskt
						System.out.println("No.");
					}
					else{//This segment shall have another belonging than p
						//Intersection between polygons occurr
						intersecting = true;
						//Endpoint upper = new Endpoint(p.getX(), p.getRealY());//Kan vara fel
						//upper.setBelonging(e.getLower().getBelonging());
						newEdge = new Edge(p, e.getLower());//Här tas ingen hänsyn till start och end.... Fuckas saker upp nu? Denna edge kommer aldrig sitta i polygonen.
						//Har bara att göra med statusen så start och end borde inte spela någon roll
						System.out.println("Yes, a new edge with p as upper and the old segments lower has been created.");

					}
					//p.getUpperTo().add(newEdge);//Detta får inte göras här. Görs autimatiskt i Edge då den skapas. Dubbelt.
					System.out.println("Delete the old segment with upper: ("+e.getUpper().getX()+", "+e.getUpper().getRealY()+") and lower: ("+e.getLower().getX()+", "+e.getLower().getRealY()+")");
					status.delete(e, p.getRealY());
					System.out.println("Insert the new, cropped one with upper: ("+newEdge.getUpper().getX()+", "+newEdge.getLower().getRealY()+") and lower: ("+newEdge.getLower().getX()+", "+newEdge.getLower().getRealY()+")");
					//status.insert(newEdge);//Dubbel insert här!! Dessa kommer sättas in i nästa steg då isUpperTo loopas igenom.
				}
				//intersectionInterior.clear();
			}*/
			
			System.out.println("Is p upper to many segments?");
			if(p.getUpperTo().size()>1){//p is upper to several segments. Check if it's an intersection. Double check in case of p being an intersection eventpoint
				//Check belongings of the polygons. Do we have an intersection?
				System.out.println("Yes it is. Check belongings for collision point.");
				System.out.println("isUpperTo size är: "+p.getUpperTo().size());

				for(Edge e : p.getUpperTo()){//Should work
					System.out.println("Segment with same upper: ("+e.getUpper().getX()+", "+e.getUpper().getRealY()+"). Lower: ("+e.getLower().getX()+", "+e.getLower().getRealY()+")");
					System.out.println("Insert segment into status");
					status.insert(e);//Dessa kan vara från olika polygoner. Kan kolla tillhörighet genom p.getNext om Edge blir tilldelad en tillhörighet.
					//intersectionInterior.add(e);//Varför detta?
					//How to report the edges involved? No need :)
					if(p.getBelonging() != e.getLower().getBelonging()){
						intersecting = true;//This will report an intersection event point as an intersection. Don't do that again in findNewEvent below.
						System.out.println("We have a collision point.");
					}
					else System.out.println("No collisiion between this uppers segment. This case should only appear in the top point of a polygon if it is really simple :)");
				}

				//Find the leftmost and rightmost segments among the intersecting ones
				System.out.println("Hitta det vänstraste och högraste segmentet som utgår från p.");
				Edge left = p.findLeftmost();
				Edge right = p.findRightmost();//Verkar funka
				//System.out.println("Leftmost is: ("+left.getLower().getX()+", "+left.getLower().getRealY()+")");
				//System.out.println("Rightmost is: ("+right.getLower().getX()+", "+right.getLower().getRealY()+")");
				//System.out.println("Now traverse the tree.");
				//status.traverseInOrder();

				//Find the left neighbour of the left segment
				//System.out.println("Hitta deras grannar.");
				Edge leftNeighbour = status.findLeftNeighbour(p.leftmost, p.getRealY());//If left neighbour exists. Check this. And the same for right.
				
				//System.out.println("Search for rightmost. Is rightmost null?");
				//System.out.println(p.rightmost==null);
				Edge rightNeighbour = status.findRightNeihbour(p.rightmost, p.getRealY());
				//System.out.println("Leftmost: ("+left.getLower().getX()+", "+left.getLower().getRealY()+")");
				
				//System.out.println("Rightmost:" + right.getLower().getX()+", "+right.getLower().getRealY()+")");

				//Kör findNewEvent på dessa
				//System.out.println("Kör findNewEvent på dessa.");
				findNewEvent(leftNeighbour, rightNeighbour, p, false);//Wrong.Should take the neighbours and point only!
				//findNewEvent(rightNeighbour, right, p, false);
			}
			else {
				System.out.println("No. Insert its only segment into the status.");
				status.insert(p.getUpperTo().get(0));//p is only upper to one segment
				
				System.out.println("\n=====================================Traverse the status=============================\n");
				status.traverseInOrder();

				//System.out.println("The eventpoint is not intersecting with any segment in the status. Den är false :)");
				//Find left and right neighbours of p
				System.out.println("Find the neighbours of p:s segment to see if they intersect.");
				Edge leftNeighbour = status.findLeftNeighbour(p.getUpperTo().get(0), p.getRealY());//Hittar ingen vänstergranne :(
				Edge rightNeighbour = status.findRightNeihbour(p.getUpperTo().get(0), p.getRealY());

				//Throw them as arguments togehter with p to findNewEvent
				findNewEvent(leftNeighbour, rightNeighbour, p, false);//Detta är fel! Ska kolla leftneighbour mot segmentet och rightneighbour mot segmentet.
				//findNewEvent(p.getUpperTo().get(0), rightNeighbour, p, false);//Fixat med ett till kall.
			}


			//Fix newEvent

		}

		//Case: p is only a lower. What if it's both? Can it be now?
		/*if(p.isLower()){//&&!p.isUpper){//Added a check so this is not performed for those who are both lower and uppers. That is taken care of in the first check above.
			System.out.println("==============================Handle a lower eventpoint=================================");
			//Just delete it's segment god dammit!
			
			
			
			
			ArrayList<Edge> segments = new ArrayList<Edge>();
			
			//DeleteWithLower might break the time boundary going throught the entire tree.
			System.out.println("Delete the lower. This might break the time.");
			status.deleteWithLower(p, segments);//The Edges are ordered left to right

			//If several segments share this lower point
			System.out.println("Is this lower to more than one segment?");
			if(segments.size()>1){
				
				/*
				 * Fixa här så att vi kan hitta leftmost och rightmost för de segment som har gemensam lower.
				 * Kanske blir lite uppochned att köra den vanliga varianten. Jämför den lowers kanske? IsToRightOrLeft...
				 * Den är gjord för att kolla segment med gemensam upper..
				 */
				
		/*		
				System.out.println("Yes. Find the leftmost and rightmost.");
				//Find the leftmost and rightmost of the segments
				Edge left = segments.get(0);//Does this work?
				System.out.println("The leftmost segment with lower in p is: ("+left.getUpper().getX()+", "+left.getUpper().getRealY()+"), ("+left.getLower().getX()+", "+left.getLower().getRealY()+")");
				Edge right = segments.get(segments.size()-1);//Right and left may be the same
				System.out.println("The rightmost segment with lower in p is: ("+right.getUpper().getX()+", "+right.getUpper().getRealY()+"), ("+right.getLower().getX()+", "+right.getLower().getRealY()+")");

				System.out.println("Find their left and right neighbours.");
				//Find their neighbours
				Edge leftNeighbour = status.findLeftNeighbour(left, p.getRealY());
				System.out.println("===================Traverse the tree again.");
				status.traverseInOrder();
				
				Edge rightNeighbour = status.findRightNeihbour(right, p.getRealY());

				System.out.println("Delete the segments that has their lower in point p.");
				//Delete the segments
				for(Edge e : segments){
					status.delete(e, p.getRealY());
				}
				if(!p.isUpper){//To prevent that neighbours are checked if p is both upper and lower. The uppers segment has already been checked against its neighbours.
					//Find if the new neighbours intersect
					findNewEvent(leftNeighbour, rightNeighbour, p, true);
					segments.clear();
				}
			}*/

			//If only one segment has this lower point
			/*else if(segments.size()==1){
				System.out.println("Vi har hittat ett segment som har en lower i punkt p. Denna punkt\nhar koordinaterna: "+p.getX()+", "+p.getRealY());
				System.out.println("Segmentets lower har koordinaterna: "+segments.get(0).getLower().getX()+", "+segments.get(0).getLower().getRealY());
				//status.search hittar ej segment(0)
				Edge leftNeighbour = status.findLeftNeighbour(segments.get(0), p.getRealY());//GetLowerTo finns inte i statusen. BORTTAGET OVAN?? Nope. Inte problemet. isLowerTo kanske inte sätts.
				//Edge rightNeighbour = status.findRightNeihbour(p.getLowerTo(), p.getRealY());
				Edge rightNeighbour = status.findRightNeihbour(segments.get(0), p.getRealY());

				//Delete the segment
				status.delete(p.getLowerTo(), p.getRealY());
				if(!p.isUpper){
					//Find if new neighbours intersect
					findNewEvent(leftNeighbour, rightNeighbour, p, true);	
				}
				segments.clear();
			}
			else{
				//No segments in the status has this as a lower point. Might already been deleted in a previous step.
				System.out.println("No segments in the status has this as a lower point. Might already been deleted in a previous step.");
			}



		}*/
		if(p.isIntersection){
			
			System.out.println("=====================This is an intersection eventpoint====================\n");
			//System.out.println("Traverse the tree first.");
			//status.traverseInOrder();
			//intersecting = true;
			//Handle this shit separatly. Hur blir det förresten med insertion i kön med dessa? Prioriteras före lower? Funkar ändå inte att kolla :P
			//Edge newEdge1, newEdge2;
			//System.out.println("Is this a collision point?");
			//Check for intersections between polygons. Denna checken kan tas bort om det funkar nu. Detta tas hand om i caset med flera segment per upper under denna check.
			/*if(p.getUpperTo().get(0).getLower().getBelonging()==p.getUpperTo().get(1).getLower().getBelonging()){//Denna jämförelse funkar ej!
				System.out.println("No.");
			}
			else{//This segment shall have another belonging than p
				//Intersection between polygons occurr
				intersecting = true;
				//Endpoint upper = new Endpoint(p.getX(), p.getRealY());//Kan vara fel
				//upper.setBelonging(e.getLower().getBelonging());
				//newEdge = new Edge(p, e.getLower());//Här tas ingen hänsyn till start och end.... Fuckas saker upp nu? Denna edge kommer aldrig sitta i polygonen.
				//Har bara att göra med statusen så start och end borde inte spela någon roll
				System.out.println("Yes, a new edge with p as upper and the old segments lower will be created.");

			}*/
			
			//Make a try were intersection points always are marked as intersection points. There belonings are already checked, otherwise
			//we wouldn't make them intersection points.
			intersecting = true;
			
			//newEdge1 = new Edge(p, p.getUpperTo().get(0).getLower());
			//newEdge2 = new Edge(p, p.getUpperTo().get(1).getLower());
			
			//p.getUpperTo().add(newEdge);//Detta får inte göras här. Görs autimatiskt i Edge då den skapas. Dubbelt.
			//System.out.println("Find the old segments and delete them. Inserting new ones will be lika a crop and switch their position in the status.");
			//status.delete(p.getUpperTo().get(0));
			//status.delete(p.getUpperTo().get(1));
			
			//Testa att manipulera dem istället om delete inte funkar så bra.
			//System.out.println("Nääää, testa att manipulera de befintliga istället. Problem kan bli när de ska ritas ut bara :P Fast nä. De behåller ju start och end points.");
			
			
			//Kastat runt lite skit. Testar att ändra segmentens upper iställer för att slänga dem.
			Edge tempSeg1 = p.getUpperTo().get(0);//Dessa ska redan finnas i statusen.
			Edge tempSeg2 = p.getUpperTo().get(1);
			
			//System.out.println("Delete the involved segments.");
			
			status.delete(tempSeg1, p.getRealY());
			status.delete(tempSeg2, p.getRealY());
			//System.out.println("Done.\nTraverse the tree.");
			//status.traverseInOrder();
			
			
			//Added 2015-06-03 to fix lower point double segments
			Endpoint tempLower = tempSeg1.getLower();
			//Erase tempSeg1 in isLowerTo in tempSeg1:s lower point
			if(tempLower.getLowerTo().get(0).compareTo(tempSeg1)==0){
				tempLower.getLowerTo().remove(tempSeg1);
			}
			if(tempLower.getLowerTo().size()>1){
				if(tempLower.getLowerTo().get(1).compareTo(tempSeg1)==0){
					tempLower.getLowerTo().remove(tempSeg1);
				}
			}
			
			tempLower = tempSeg2.getLower();
			//Erase tempSeg2 in isLowerTo in tempSeg1:s lower point
			if(tempLower.getLowerTo().get(0).compareTo(tempSeg2)==0){
				tempLower.getLowerTo().remove(tempSeg2);
			}
			if(tempLower.getLowerTo().size()>1){
				if(tempLower.getLowerTo().get(1).compareTo(tempSeg2)==0){
					tempLower.getLowerTo().remove(tempSeg2);
				}
			}
			
			tempSeg1.changeUpper(p);//Är detta allt som behövs? Att ändra upper?
			tempSeg2.changeUpper(p);
			
			//Added 2015-05-18 to put right segments with right uppers in the isUpperTo array of an intersection
			//Förmodligen onödigt eftersom vi redan pekat på segmenten och ändrat upper.
			p.clearSegmentsInUpperTo();
			p.addUpperTo(tempSeg1);
			p.addUpperTo(tempSeg2);
			
			//Add the shortened segments to the lower points isLowerTo-arrays
			//The old versions of these segments should be erased above. Now replace them with the shorter versions.
			tempSeg1.getLower().getLowerTo().add(tempSeg1);
			tempSeg2.getLower().getLowerTo().add(tempSeg2);
			
			
			
			//System.out.println("Insert the new cropped versions into the status tree.");
			status.insert(tempSeg1);
			status.insert(tempSeg2);
			//System.out.println("Traverse the tree.");
			//status.traverseInOrder();
			
			System.out.println("======Printar data om de nya beskurna segmenten=====");
			System.out.println("Beskuret 1: ("+tempSeg1.getUpper().getX()+", "+tempSeg1.getUpper().getRealY()+"), ("+tempSeg1.getLower().getX()+", "+tempSeg1.getLower().getRealY()+")");
			System.out.println("Beskuret 2: ("+tempSeg2.getUpper().getX()+", "+tempSeg2.getUpper().getRealY()+"), ("+tempSeg2.getLower().getX()+", "+tempSeg2.getLower().getRealY()+")");
			System.out.println("Gå igenom statusträdet.");
			status.traverseInOrder();
			
			
			
			
			//Om dessa ska ändra sig istället för att ersättas med nya måste de swappas i statusen med.
			//System.out.println("Changing old segment with upper: ("+p.getUpperTo().get(0).getUpper().getX()+", "+ p.getUpperTo().get(0).getUpper().getRealY()+")");
			//p.getUpperTo().remove(0);
			//System.out.println("Changing old segment with upper: ("+p.getUpperTo().get(0).getUpper().getX()+", "+ p.getUpperTo().get(0).getUpper().getRealY()+")");
			//p.getUpperTo().remove(0);
			//System.out.println("isUpperTo of intersection has cropped the old segments. The size of this array is now 2..?: Size: "+p.getUpperTo().size());
			p.isUpper = true;//Tvivelaktigt om deta behövs. Kan vara bra för insertion


			
			//System.out.println("Intersectionpunktens isUpperTo borde vara 2 stor. Annars är något fel.\nStorleken är: "+p.getUpperTo().size());
			//status.insert(newEdge1);
			//status.insert(newEdge2);
			//System.out.println("Traverse the tree now!\n");
			//status.traverseInOrder();



			//findNewEvent måste köras här för test mot segmentens nya grannar!
			Edge left = p.findLeftmost();
			Edge right = p.findRightmost();

			Edge leftNeighbour = status.findLeftNeighbour(p.leftmost, p.getRealY());
			Edge rightNeighbour = status.findRightNeihbour(p.rightmost, p.getRealY());//Istället för right... Gör nog ingen skillnad.

			findNewEvent(leftNeighbour, rightNeighbour, p, false);
			//Kolla även så att alla punkter får sina belongings! - Fixat!


		}
		if(intersecting == true){//Feltänk här. Måste bygga på antal element som upper är upper till.
			System.out.println("p är en intersection point, är nu upper till mer än ett segment. Lägg till p i intersection-arrayen.");
			allIntersections.add(p);
			intersecting = false;
		}

		
		System.out.println("\n====================================Slut på handleEventPoint.==========================================");
	}

	/*
	 * Egentligen ska denna ta en leftneighbour och en right neighbour samt p som jämför left och right med sitt segment.
	 * Om p är upper till massa segment --> gör check för leftmost och rightmst här?
	 */

	public void findNewEvent(Edge left, Edge right, Endpoint p, boolean lower){//Borde köra point här istället så kan man kolla o den ska leta upp leftmost och rightmost.
		Endpoint crossing;
		if(!lower){//Do a check here if we are handling a lower or an upper.... What if it's both? Good question. //This is never used

			//Check if left neighbour is null
			if(left==null)
				System.out.println("There is no left neighbour.");

			//Check if the segment cross its left neighbour
			else{
				if(p.getUpperTo().size()>1){

					//Check left segment againts left neighbour
					crossing = p.leftmost.doIntersect(left);
					if(crossing == null){
						System.out.println("The left segment doesn't intersect with its left neighbour.");
					}
					//Check so the crossing isn't a crossing between two lower points. This will prevent double reporting.
					else if(!(left.getLower().compareTo(p.leftmost.getLower())==0)){ //if(crossing.compareTo(left.getUpper())!=0 && crossing.compareTo(p.leftmost.getUpper())!=0){//To avoid a loop where the intersection will discover itself again and again.
						
						//The segments intersect, create a new eventpoint if they belong to different polygons
						if(left.getLower().getBelonging() != p.leftmost.getLower().getBelonging()){
							System.out.println("The left segment cross its left neighbour, create new event point.");
							System.out.println("They intersect at: ("+crossing.getX()+", "+crossing.getRealY()+")");
							Endpoint intersection = new Endpoint(crossing.getX(), crossing.getY());
							//intersection.isUpper = true;
							intersection.isIntersection = true;
							intersection.setBelonging(p.getBelonging());

							//Send the involved segments with the intersection
							intersection.getUpperTo().add(left);
							intersection.getUpperTo().add(p.leftmost);
							eventQueue.insert(intersection);
						}
								
					}
					else System.out.println("The left segment intersect with its left neighbour in both their lower points");
				}
				else if(p.getUpperTo().size()==1){
					//Check the only segment against its left neighbour
					crossing = p.getUpperTo().get(0).doIntersect(left);
					if(crossing == null){
						System.out.println("The segment doesn't intersect with its left neighbour.");
					}
					//Check that the crossing isn't a crossing between two lowers.
					else if(p.getUpperTo().get(0).getLower().compareTo(left.getLower())!=0 && p.getBelonging() != left.getLower().getBelonging()){//This should be ok. Only 1 element in isUpperTo --> this is not sprung from an intersection point
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
						intersection.getUpperTo().add(left);
						intersection.getUpperTo().add(p.getUpperTo().get(0));
						eventQueue.insert(intersection);
						//Also set p to isUpper?? Annars gör om alla checkar att kolla om UpperTo-arrayen är tom eller inte.
						
					}
					
					
					else if(p.getUpperTo().get(0).getLower().compareTo(left.getLower())!=0 && p.getBelonging() == left.getLower().getBelonging()){
						System.out.println("Jaja segmentet korsar sin vänstra granne men de tillhör samma polygon.");
						System.out.println("Den vänstra grannen är segmentet: ("+left.getUpper().getX()+", "+left.getUpper().getRealY()+"), ("+left.getLower().getX()+", "+left.getLower().getRealY()+")");
					}
					
					else System.out.println("The segment intersect with its left neighbour in both their lower points. Do something about this special case if needed.");

				}
			}

			//Check if right neighbour is null
			if(right==null)
				System.out.println("There is no right neighbour.");

			//Check if the segment cross its right neighbour
			else{
				if(p.getUpperTo().size()>1){

					//Check right segment againts right neighbour
					crossing = p.rightmost.doIntersect(right);
					if(crossing == null){
						System.out.println("The right segment doesn't intersect with its right neighbour.");
					}
					//Check that crossing isn't a crossing between two lowers and that they belong to different polygons
					else if(p.rightmost.getLower().compareTo(right.getLower())!=0 && p.rightmost.getLower().getBelonging() != right.getLower().getBelonging()){// if(crossing.compareTo(right.getUpper())==0 && crossing.compareTo(p.rightmost.getUpper())!=0){
						System.out.println("The right segment cross its right neighbour, create new event point.");
						System.out.println("They intersect at: ("+crossing.getX()+", "+crossing.getRealY()+")");
						//The segments intersect, create a new eventpoint
						Endpoint intersection = new Endpoint(crossing.getX(), crossing.getY());
						//intersection.isUpper = true;
						intersection.isIntersection = true;
						intersection.setBelonging(p.getBelonging());

						//Send the involved segments with the intersection
						intersection.getUpperTo().add(right);
						intersection.getUpperTo().add(p.rightmost);
						eventQueue.insert(intersection);
					}
					else System.out.println("The right segment intersect with its right neighbour in both their lower points.");
				}
				else if(p.getUpperTo().size()==1){
					//Check the only segment against its right neighbour
					crossing = p.getUpperTo().get(0).doIntersect(right);
					if(crossing == null){
						System.out.println("The segment doesn't intersect with its right neighbour.");
					}
					else if(p.getUpperTo().get(0).getLower().compareTo(right.getLower())!=0 && p.getBelonging() != right.getLower().getBelonging()){//Index out of bounds exception! Crossing har inget i sin array??
						System.out.println("The segment cross its right neighbour, create new event point.");
						//The segments intersect, create a new eventpoint
						Endpoint intersection = new Endpoint(crossing.getX(), crossing.getY());
						//intersection.isUpper = true;
						intersection.isIntersection = true;
						intersection.setBelonging(p.getBelonging());

						//Send the involved segments with the intersection
						intersection.getUpperTo().add(right);
						intersection.getUpperTo().add(p.getUpperTo().get(0));
						eventQueue.insert(intersection);
					}
					else System.out.println("The segment intersect with its right neighbour in both their lower points.");
				}
			}
		}
		if(lower){ //This code is never run I think as of 2015-05-12
			//This code shouldn't be run when we deal with closed polygons. There will always be an upper directly after the lower.
			
			
			if(left!=null && right !=null &&! p.isUpper){//Added a check against upper coinciding with lower. Then should not check the neighbours, already done against the uppers segment(s).
				//Check left and right neighbours against eachother
				crossing = left.doIntersect(right);
				if(crossing==null){
					System.out.println("A lower endpoint should now have been deleted. It's two neighbours doesn't intersect.");
				}
				else if(left.getLower().compareTo(right.getLower())!=0){//A check here against lower being the same as an intersection? No. Should be caught in the first findIntersectingSegments. I guess. This executes the lowers segment. I think.
					System.out.println("A lower endpoint has probably been deleted. It's neighbours intersect.");
					Endpoint intersection = new Endpoint(crossing.getX(), crossing.getY());
					//intersection.isUpper = true;
					intersection.isIntersection = true;
					intersection.setBelonging(p.getBelonging());

					//Send the involved segments with the intersection
					p.getUpperTo().add(left);
					p.getUpperTo().add(right);
					eventQueue.insert(intersection);
				}
				else System.out.println("The lowers neighbours cross and their lower points coincide. No need for a new intersection point.");
			}
			else{
				System.out.println("A lower endpoint has probably been deleted. At least one of its neighbours is null. No new intersections can be found here.");
			}
		}
	}
	/*
		crossing = segment.doIntersect(left);
		if(crossing == null){
			System.out.println("The segment doesn't cross with its left neighbour.");
		}
		else{
			System.out.println("The segment cross its left neighbour, create new event point.");
			//The segments intersect, create a new eventpoint
			Endpoint intersection = new Endpoint(crossing.getX(), crossing.getRealY());
			intersection.isUpper = true;
			intersection.setBelonging(left.getLower().getBelonging());
		}

		crossing = segment.doIntersect(right);
		if(crossing == null){
			System.out.println("The segment doesn't cross with its right neighbour.");
		}




		System.out.println("Inne i findNewEvent. Är grannen null?");
		if(left!=null&&right!=null){//Antar att ett av segmenten inte är null och det andra är en potentiell granne eller null.
			System.out.println("Nope, inget av de båda segmenten är null. Kolla dem för kollission.");
			//Calculate
			Endpoint crossing = left.doIntersect(right);
			System.out.println("Korsar segmenten?");
			if(crossing == null){
				//The egments doesn't intersect
				System.out.println("Nope. The neighbours doesn't intersect.");
			}
			else{
				System.out.println("Yes, det gör de! Skapa en ny eventpoint som heter intersection. Den blir en upper utan segment.\nDen kommer få segment sedan då den kommer in som upper och ligger på intersection-->beskär segmenten med sig själv som upper.");
				//The segments intersect, create a new eventpoint
				Endpoint intersection = new Endpoint(crossing.getX(), crossing.getRealY());
				intersection.isUpper = true;
				intersection.setBelonging(left.getLower().getBelonging());
				//Behöver inte lägga till segment i isUpperTo - det fixas när den behandlas som upper med interior intersection i handleEventPoint.
				//Denna hamnar i eventQueue som segmentlös upper
				//Sedan som upper i handleEventpoint
				//statusen söks igenom --> left och right hittas som interior intersections
				//Left och right skärs av och läggs till intersections egna array isUpperTo
				//intersection behöver en belonging. Bara välj en så jämförs de sedan.
				//Rapportera inte intersection här, då blir det dubbelt.
			}
		}
		else{
			System.out.println("There is no neighbour says findNewEvent");
		}

		//If the edges intersect below the sweep line, or on it and to the right of the current event point, add as an event in eventQueue
	}

	 */
	
	//Test för att se om allt funkar.
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
	}








}
