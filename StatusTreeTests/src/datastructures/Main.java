package datastructures;

import algorithm.Edge;
import algorithm.Endpoint;

public class Main {
	
	private static StatusTree testStatus;
	public static boolean intersecting = false;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		testStatus = new StatusTree();
		
		//generateInput();
		
		//2016-04-04
		//testPointSideOfLine();
		
		//test1();
		
		//2016-04-05
		//test2();//Not solved
		
		//2016-04-06
		//test3();//Maybe solved
		testDoIntersect();
	}
	
	
	private static void generateInput(){
		
		/* 1. Make all segments involved
		 * 2. Make all points too. Set everything right. No. Focus on the segments.
		 * 3. Insert them.
		 * 4. Traverse the tree and see that it is right.
		 * 5. Add the segment that makes it unbalanced.
		 * 6. Traverse
		 */
		
		/*
		 * 	Upper x: 53Upper y: 270Lower x: 107Lower y: 133height 0 parent Parent upper: (123, 277) and parent lower: (58, 190) | Is this a left child?true
			Upper x: 123Upper y: 277Lower x: 58Lower y: 190height 2 parent NULL | Is this a left child?false
			Upper x: 179Upper y: 224Lower x: 193Lower y: 192height 0 parent Parent upper: (253, 231) and parent lower: (240, 129) | Is this a left child?true
			Upper x: 253Upper y: 231Lower x: 240Lower y: 129height 1 parent Parent upper: (123, 277) and parent lower: (58, 190) | Is this a left child?false
			Upper x: 332Upper y: 292Lower x: 344Lower y: 141height 0 parent Parent upper: (253, 231) and parent lower: (240, 129) | Is this a left child?false
		 */
		
		//Test input 2016-03-31
		//Segment a
		/*Endpoint a1 = new Endpoint(123, 400-277);
		Endpoint a2 = new Endpoint(58, 400-190);
		Edge a = new Edge(a1, a2);
		
		//Segment b
		Endpoint b1 = new Endpoint(53, 400-270);
		Endpoint b2 = new Endpoint(107, 400-133);
		Edge b = new Edge(b1, b2);
		
		//Segment c
		Endpoint c1 = new Endpoint(253, 400-231);
		Endpoint c2 = new Endpoint(240, 400-129);
		Edge c = new Edge(c1, c2);
		
		//Segment d
		Endpoint d1 = new Endpoint(179, 400-224);
		Endpoint d2 = new Endpoint(193, 400-192);
		Edge d = new Edge(d1, d2);
		
		//Segment e
		Endpoint e1 = new Endpoint(332, 400-292);
		Endpoint e2 = new Endpoint(344, 400-141);
		Edge e = new Edge(e1, e2);
		
		//Segment f
		Endpoint f1 = new Endpoint(148, 400-220);
		Endpoint f2 = new Endpoint(172, 400-136);
		Edge f = new Edge(f1, f2);
		
		testStatus.insert(a);
		testStatus.insert(b);
		testStatus.insert(c);
		testStatus.insert(d);
		testStatus.insert(e);
		
		System.out.println("The status tree now looks like this: ");
		
		testStatus.traverseInOrder();
		
		System.out.println("Insert the last segment and print the tree: ");
		testStatus.insert(f);
		
		testStatus.traverseInOrder();*/
		
		
		//Test input 2016-04-01: Trying to get deletion of a vertical segment (Segment a) at the root to work
		//Segment a
				Endpoint a1 = new Endpoint(243, 400-193);
				Endpoint a2 = new Endpoint(243, 400-116);
				Edge a = new Edge(a1, a2);
				
				//Segment b
				Endpoint b1 = new Endpoint(157, 400-144);
				Endpoint b2 = new Endpoint(146, 400-66);
				Edge b = new Edge(b1, b2);
				
				//Segment c
				Endpoint c1 = new Endpoint(356, 400-279);
				Endpoint c2 = new Endpoint(340, 400-115);
				Edge c = new Edge(c1, c2);
				
				//Segment d
				Endpoint d1 = new Endpoint(62, 400-156);
				Endpoint d2 = new Endpoint(55, 400-84);
				Edge d = new Edge(d1, d2);
		
				testStatus.insert(a);
				testStatus.insert(b);
				testStatus.insert(c);
				testStatus.insert(d);
				
				testStatus.traverseInOrder();
				
				System.out.println("=========Now delete the root========");
				boolean hej = testStatus.delete(a, a2.getRealY());
				System.out.println("Did deletion return null?");
				System.out.println(hej == false);
				
				System.out.println("Traverse the tree again.");
				
				testStatus.traverseInOrder();
		
		
	}
	
	//This method returns 0 for the specified segments.
	public static void testPointSideOfLine(){
		Endpoint one = new Endpoint(148, 400 - 298);
		Endpoint two = new Endpoint(49, 400 - 255);
		Edge testSegment1 = new Edge(one, two);
		
		Endpoint three = new Endpoint(124, 400 - 214);
		Edge testSegment2 = new Edge(one, three);
		
		//Test common upper - 1 horizontal stretching left from upper == the upper is not the same for both segments. This case will not appear since the egments cannot be in the status at the same time.
		Endpoint four = new Endpoint(130, 400 - 298);//This points left from the "upper" -> therefore the upper is this segments lower.
		Edge horizontalLeft = new Edge(one, four);
		
		//Test 1 horizontal segment stretching right from common upper
		Endpoint five = new Endpoint(160, 400 - 298);
		Edge horizontalRight = new Edge(one, five);
		
		//System.out.println(testNewSideMethod(testSegment2, testSegment1.getLower()));//CHECK
		//System.out.println(testNewSideMethod(testSegment1, testSegment2.getLower()));//CHECK
		
		//System.out.println(isToRightOrLeftOf(testSegment2, testSegment1.getLower()));//CHECK
		//System.out.println(isToRightOrLeftOf(testSegment1, testSegment2.getLower()));//CHECK
		
		//Horizontal case
		//System.out.println(testNewSideMethod(testSegment1, horizontalRight.getLower()));//Should return 1 (CHECK)
		//System.out.println(isToRightOrLeftOf(testSegment1, horizontalRight.getLower()));//Should return 1 (CHECK)
		//System.out.println(testNewSideMethod(horizontalRight, testSegment1.getLower()));//Points underneath a horizontal line returns -1.
		//System.out.println(isToRightOrLeftOf(horizontalRight, testSegment1.getLower()));
		
		//Testing a point underneath a horizontal line and in the middle of its x-interval
		Endpoint testPoint1 = new Endpoint(155, 400 - 250);
		//System.out.println(testNewSideMethod(horizontalRight, testPoint1));//Returns -1
		//System.out.println(isToRightOrLeftOf(horizontalRight, testPoint1));//Returns -1
		
		//Testing a point underneath a horizontal line and in the above the max x of the segments interval
		Endpoint testPoint2 = new Endpoint(200, 400 - 250);
		System.out.println(testNewSideMethod(horizontalRight, testPoint2));//Returns -1
		System.out.println(isToRightOrLeftOf(horizontalRight, testPoint2));//Returns -1
		
		//Conclusion: A point underneath a line always return as if it is on the left, -1.
		
		/*int result = isToRightOrLeftOf(testSegment1, testSegment2);
		System.out.println(result);*/
	}
	
	//Test isToRightOrLeftOf for two segments with same upper
	public static int isToRightOrLeftOf(Edge segment, Endpoint p){
		//Determinant stuff
		//Might be bad to use int here. Resolves to 0.
		//Determinant stuff
				int det = (segment.getLower().getX()-segment.getUpper().getX())*(p.getRealY()-segment.getUpper().getRealY()) - (segment.getLower().getRealY()-segment.getUpper().getRealY())*(p.getX()-segment.getUpper().getX());
				if(det<0){
					return -1;
				}
				else if(det>0){
					return 1;
				}

				return 0;
	}
	
	//Returns 1 for right and -1 for left and 0 for point on line.
	/* Test special cases:
	 * 1. One segment is horizontal - common upper
	 * 2. One segment is horizontal and the other vertical - common upper
	 * 3. Both are horizontal - will never exist in the status at the same time (CHECK)
	 */
	public static int testNewSideMethod(Edge segment, Endpoint p){
		int Ax = segment.getUpper().getX();
		//System.out.println("Ax = "+Ax);
		int Ay = segment.getUpper().getRealY();
		//System.out.println("Ay = "+Ay);
		int Bx = segment.getLower().getX();
		//System.out.println("Bx = "+Bx);
		int By = segment.getLower().getRealY();
		//System.out.println("By = "+By);
		
		
		double doubleDet = ((double)Bx-(double)Ax)*((double)p.getRealY()-(double)Ay) - ((double)By-(double)Ay)*((double)p.getX()-(double)Ax);
		//System.out.println("doubleDet = "+doubleDet);
		int det = (Bx-Ax)*(p.getRealY()-Ay) - (By-Ay)*(p.getX()-Ax);
		
		//System.out.println("("+Bx + " - "+Ax+") * ("+p.getRealY() + " - "+Ay + ") - (" + By + " -" + Ay + ") * ("+ p.getX() + " - " + Ax);
		
		//System.out.println("det = " + det);
		
		
		
		if(det==0){
			return 0;
		}
		if(det<0){
			return -1;
		}
		else return 1;
	}
	
	/* 2016-04-04
	 * Test the exception caught 2016-04-01. 
	 * 
	 * Chain of events
	 * 1. The status tree was balanced.
	 * 2. Two segments with same upper was inserted.
	 * 3. Trying to find the leftNeighbour of the leftmost of the new segments caused a crash -> a.	sweep_y lies outside the segments endpoints.
	 * 4. Possible causes:
	 * 			1. The leftmost segment at the upper points getUpperTo-array is null
	 * 			2. The findLeftNeighbour method fails somewhere -> track back from the: a.	sweep_y lies outside the segments endpoints.
	 * 
	 * 5. Reason found! The root is a segment that was supposed to have been deleted but failed.
	 * This led to the finding of an unbalanced status tree before this happened. Test2 attempt to solve the balancing.
	 * 
	 * 
	 */
	
	public static void test1(){
		//Root segment
		Endpoint root1 = new Endpoint(177, 400-312);
		Endpoint root2 = new Endpoint(211, 400-254);
		Edge root = new Edge(root1, root2);
		
		//Root.left
		Endpoint b1 = new Endpoint(99, 400-303);
		Endpoint b2 = new Endpoint(94, 400-290);
		Edge b = new Edge(b1, b2);
		
		//Root.right
		Endpoint c1 = new Endpoint(207, 400-322);
		Endpoint c2 = new Endpoint(285, 400-222);
		Edge c = new Edge(c1, c2);
		
		//Root.left.left
		Endpoint d1 = new Endpoint(88, 400-324);
		Endpoint d2 = new Endpoint(22, 400-280);
		Edge d = new Edge(d1, d2);
		
		//Root.left.right
		Endpoint e1 = new Endpoint(153, 400-324);
		Endpoint e2 = new Endpoint(126, 400-251);
		Edge e = new Edge(e1, e2);
		
		//Root.right.left
		Endpoint f1 = new Endpoint(200, 400-304);
		Endpoint f2 = new Endpoint(191, 400-199);
		Edge f = new Edge(f1, f2);
		
		//Root.right.right
		Endpoint g1 = new Endpoint(230, 400-335);
		Endpoint g2 = new Endpoint(328, 400-257);
		Edge g = new Edge(g1, g2);
		
		//Root.left.left.right
		Endpoint h1 = new Endpoint(99, 400-303);
		Endpoint h2 = new Endpoint(57, 400-273);
		Edge h = new Edge(h1, h2);
		
		//Root.left.right.right
		Endpoint i1 = new Endpoint(177, 400-312);
		Endpoint i2 = new Endpoint(106, 400-307);
		Edge i = new Edge(i1, i2);
		
		
		testStatus.insert(root);
		testStatus.insert(b);
		testStatus.insert(c);
		testStatus.insert(d);
		testStatus.insert(e);
		testStatus.insert(f);
		testStatus.insert(g);
		testStatus.insert(h);
		testStatus.insert(i);
		
		testStatus.traverseInOrder();//Input is correct so far
		
		Endpoint upper = new Endpoint(148, 400-298);
		Endpoint lower1 = new Endpoint(49, 400-255);
		
		Edge segment1 = new Edge(upper, lower1);
		
		Endpoint lower2 = new Endpoint(124, 400-214);
		Edge segment2 = new Edge(upper, lower2);
		
		//Start testing
		
		//Insert the two segments (CHECK)
		testStatus.insert(segment1);
		testStatus.traverseInOrder();
		testStatus.insert(segment2);
		testStatus.traverseInOrder();
		
		testHandleAPoint(upper);
		
	}
	
	
	/* 2016-04-04
	 * Test: The error at test1 originated from a failed deletion of a 
	 * segment with upper: (177, 312) and lower: (106, 307)
	 * 
	 * This test builds the status tree as it is before the deletion attempt and then tracks the deletion process.
	 * 
	 * 2016-04-05
	 * Discovery: The tree as it was before was unbalanced. Failed to balance after insertion of (207, 322), (285, 222)
	 * Test: 
	 * 1. Track were it fails to balance the tree
	 * 2. Get it to balance at that point and any other of similar case
	 * 3. Make a test for the delete method of the root that failed in test1.
	 */
	public static void test2(){
		//Root segment
				Endpoint root1 = new Endpoint(153, 400-324);
				Endpoint root2 = new Endpoint(126, 400-251);
				Edge root = new Edge(root1, root2);
				
				//Root.left
				Endpoint b1 = new Endpoint(108, 400-324);
				Endpoint b2 = new Endpoint(94, 400-290);
				Edge b = new Edge(b1, b2);
				
				//Root.right
				Endpoint c1 = new Endpoint(230, 400-335);
				Endpoint c2 = new Endpoint(328, 400-257);
				Edge c = new Edge(c1, c2);
				
				//Root.left.left
				Endpoint d1 = new Endpoint(88, 400-324);
				Endpoint d2 = new Endpoint(22, 400-280);
				Edge d = new Edge(d1, d2);
				
				//Root.left.right = null
				
				//Root.right.left
				Endpoint e1 = new Endpoint(173, 400-324);
				Endpoint e2 = new Endpoint(200, 400-304);
				Edge e = new Edge(e1, e2);
				
				//Root.right.right = null
				
				//Root.right.left.right
				Endpoint f1 = new Endpoint(207, 400-322);
				Endpoint f2 = new Endpoint(285, 400-222);
				Edge f = new Edge(f1, f2);
				
				
				testStatus.insert(root);
				testStatus.insert(b);
				testStatus.insert(c);
				testStatus.insert(d);
				testStatus.insert(e);
				testStatus.traverseInOrder();
				
				testStatus.insert(f);
				testStatus.traverseInOrder();
				
	}
	/* 2016-04-06
	 * Test case: 
	 * 1. An intersection point is handled handled 
	 * 2. Two segments are to be cut. Both are deleted from the status.
	 * 3. The resulting status is balanced. We start here.
	 * 4. Insert the first cut segment and traverse the tree
	 * 5. Insert the second cut segment and traverse the tree
	 * 6. Debugg the insertion and find out why it is unbalanced
	 */
	public static void test3(){
		//Root segment
		Endpoint root1 = new Endpoint(153, 400-324);
		Endpoint root2 = new Endpoint(126, 400-251);
		Edge root = new Edge(root1, root2);
		
		//Root.left
		Endpoint b1 = new Endpoint(108, 400-324);
		Endpoint b2 = new Endpoint(94, 400-290);
		Edge b = new Edge(b1, b2);
		
		//Root.right
		Endpoint c1 = new Endpoint(230, 400-335);
		Endpoint c2 = new Endpoint(328, 400-257);
		Edge c = new Edge(c1, c2);
		
		//Root.left.left
		Endpoint d1 = new Endpoint(88, 400-324);
		Endpoint d2 = new Endpoint(22, 400-280);
		Edge d = new Edge(d1, d2);
		
		testStatus.insert(root);
		testStatus.insert(b);
		testStatus.insert(c);
		testStatus.insert(d);
		testStatus.traverseInOrder();
		
		System.out.println("Insert the first cut segment.");
		//Cut segment 1
		Endpoint e1 = new Endpoint(173, 400-324);
		Endpoint e2 = new Endpoint(200, 400-304);
		Edge e = new Edge(e1, e2);
		
		testStatus.insert(e);
		testStatus.traverseInOrder();
		
		System.out.println("Insert the second cut segment.");
		Endpoint f1 = new Endpoint(207, 400-322);
		Endpoint f2 = new Endpoint(285, 400-222);
		Edge f = new Edge(f1, f2);
		
		testStatus.insert(f);
		testStatus.traverseInOrder();
		
	}
	
	
	public static void testHandleAPoint(Endpoint p){
		if(p.isUpper()){//�ven en check mot intersections? Men inga intersections ska vara satta till upper.
			System.out.println("/n================================Upper inserted===================================/n/n");
			System.out.println("p: ("+p.getX()+","+p.getRealY()+") is an upper eventpoint. \nCheck if it intersects with any existing segment in the status.");
		
	
			
			System.out.println("Is p upper to many segments?");
			if(p.getUpperTo().size()>1){//p is upper to several segments. Check if it's an intersection. Double check in case of p being an intersection eventpoint
				//Check belongings of the polygons. Do we have an intersection?
				
				System.out.println("Yes it is. Check belongings for collision point.");
				System.out.println("isUpperTo size �r: "+p.getUpperTo().size());

				for(Edge e : p.getUpperTo()){//Should work
					System.out.println("Segment with same upper: ("+e.getUpper().getX()+", "+e.getUpper().getRealY()+"). Lower: ("+e.getLower().getX()+", "+e.getLower().getRealY()+")");
					System.out.println("Insert segment into status");
					testStatus.insert(e);//Dessa kan vara fr�n olika polygoner. Kan kolla tillh�righet genom p.getNext om Edge blir tilldelad en tillh�righet.
					if(p.getBelonging() != e.getLower().getBelonging()){
						intersecting = true;//This will report this upper point as an intersection point. Don't do that again in findNewEvent below.
						System.out.println("We have a collision point.");
					}
					else System.out.println("No collisiion between this uppers segments. This case should only appear in the top point of a polygon if it is really simple :)");
				}

				//Find the leftmost and rightmost segments among the intersecting ones
				System.out.println("Hitta det v�nstraste och h�graste segmentet som utg�r fr�n p.");
				
				//Findleft/rightmost must be run to set left/rightmost in the Endpoint
				Edge left = p.findLeftmost();//This is run to set leftmost of the Endpoint. Returns 0 for some.
				Edge right = p.findRightmost();//Verkar funka
				System.out.println("Leftmost is: ("+left.getLower().getX()+", "+left.getLower().getRealY()+")");
				System.out.println("Rightmost is: ("+right.getLower().getX()+", "+right.getLower().getRealY()+")");
				
				Edge leftNeighbour = testStatus.findLeftNeighbour(p.leftmost, p.getRealY());//Cannot find the segment
				
				//System.out.println("Search for rightmost. Is rightmost null?");
				//System.out.println(p.rightmost==null);
				Edge rightNeighbour = testStatus.findRightNeihbour(p.rightmost, p.getRealY());
				//System.out.println("Leftmost: ("+left.getLower().getX()+", "+left.getLower().getRealY()+")");
				
				//System.out.println("Rightmost:" + right.getLower().getX()+", "+right.getLower().getRealY()+")");

				//K�r findNewEvent p� dessa
				//System.out.println("K�r findNewEvent p� dessa.");
				
				
				
				
				//findNewEvent(leftNeighbour, rightNeighbour, p, false);
				
				
				
				//findNewEvent(rightNeighbour, right, p, false);
			}
			
			
			
	}
	
	
	

}
	
	/*2016-04-06: Test the intersection method for vertical case. Might be the cause of failing intersection points.
	 * 
	 */
	public static void testDoIntersect(){
		Endpoint a1 = new Endpoint(117, 400-292);
		Endpoint a2 = new Endpoint(19, 400-165);
		Edge a = new Edge(a1,  a2);
		
		Endpoint b1 = new Endpoint(93, 400 - 246);
		Endpoint b2 = new Endpoint(93, 400-213);
		Edge b = new Edge(b1, b2);
		
		Endpoint intersection = doIntersect(a, b);
		if(intersection != null){
			System.out.println("doIntersect calculates intersection to be at: ("+intersection.getX()+", "+intersection.getRealY()+")");
		}
		else System.out.println("doIntersects recognize that the intersection lies outside the segments interval. Null! :)");
		
	}
	
	
	public static Endpoint doIntersect(Edge segment1, Edge segment2) {
	    int x1 = segment1.getUpper().getX();
	    //int y1 = upper.getRealY();
	    int y1 = segment1.getUpper().getY();//M�ste anv�nda getY() och inte realY() h�r.
	    int x2 = segment1.getLower().getX();
	    //int y2 = lower.getRealY();
	    int y2 = segment1.getLower().getY();
	    
	    int x3 = segment2.getUpper().getX();
	    //int y3 = segment.getUpper().getRealY();
	    int y3 = segment2.getUpper().getY();
	    int x4 = segment2.getLower().getX();
	    //int y4 = segment.getLower().getRealY();
	    int y4 = segment2.getLower().getY();


		int d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4); //Denominator determinant for point calculation
	    if (d == 0) return null;//Lines are parallell
	    
	    //Point calculation
	    int xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
	    int yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;
	    
	    
	    //Check if any of the lines are vertical
        if(x1 == x2 || x3 == x4){
            //Check if the intersection point lies outside any of the segment in the y direction
            if(yi < Math.min(y1, y2) || yi > Math.max(y1, y2)) return null;
            if(yi < Math.min(y3, y4) || yi > Math.max(y3, y4)) return null;
        }
	    
	    //Check if intersection lies outside the segments endpoints
	    Endpoint p = new Endpoint(xi,yi);
	    if (xi < Math.min(x1,x2) || xi > Math.max(x1,x2)) return null;
	    if (xi < Math.min(x3,x4) || xi > Math.max(x3,x4)) return null;
	    
	    
	    
	    return p;
	  }
	
	}
