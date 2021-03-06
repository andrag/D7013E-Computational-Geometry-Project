

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.net.ssl.SSLEngineResult.Status;
import javax.swing.text.Segment;
import javax.swing.text.html.HTMLDocument.HTMLReader.ParagraphAction;

import algorithm.*;

public class StatusTree {
	/*
	 * Kod hittad p� http://stackoverflow.com/questions/5771827/implementing-an-avl-tree-in-java?rq=1
	 */
	public StatusNode root = null;
	private StatusNode tempNewRoot = null;
	public boolean isRoot = false;

	public void insert(Edge data) {
		insert(root, data);
		//Reheight root here?
		if (height(root.left) - height(root.right) == 2) { //left heavier. Detta kollas inte i roten?? Varf�r?
			//Which check to use here then?
			if (data.isToRightOrLeftOf(root.segment)<0) {//Test. Inte s�kert det funkar
				rotateRight(root);
			} else {
				rotateLeftThenRight(root);
			}
		}
		else if (height(root.right) - height(root.left) == 2) { //right heavier
			if (data.isToRightOrLeftOf(root.segment)>0)
				rotateLeft(root);
			else {
				rotateRightThenLeft(root);
			}
		}
		reHeight(root);
		
	}

	private int height(StatusNode node) {
		return node == null ? -1 : node.height;
	}

	public boolean isEmpty(){
		return root == null ? true : false;
	}

	//H�r sprang jag f�r snabbt.... G�r en updatemetod ist�llet som tar en sweep line f�r att omarrangera tr�det.
	//D� kan segment f� nya upper points kanske? Sl�ng allt gammalt? Om det inte tar f�r mycket tid.

	//Fixa till s� att alla noders x-v�rden uppdateras i varje insert.
	//Fixa s� att en nod med flera segment som delar samma upper f�r nya noder n�r de splittas.
	//Fixa in order traversal
	//Fixa swap positions
	private void insert(StatusNode node, Edge segment) {
		if (root == null) {
			root = new StatusNode(segment, null);
			return;
		}

		if (segment.getUpper().isToRightOrLeftOf(node.segment)<0) {//x is less or (the same and lower.getX() is less). OBS! Check this for exception handling a lower point. Answer: Lower points won't be handled by insert. They are deleted first in collision detection!
			if (node.left != null) {
				insert(node.left, segment);
			} else {
				node.left = new StatusNode(segment, node);
			}

			if (height(node.left) - height(node.right) == 2) { //left heavier. Detta kollas inte i roten?? Varf�r?
				if (segment.getUpper().isToRightOrLeftOf(node.segment)<0) {
					rotateRight(node);
				} else {
					rotateLeftThenRight(node);
				}
			}
		} else if (segment.getUpper().isToRightOrLeftOf(node.segment)>0) {
			if (node.right != null) {
				insert(node.right, segment);
			} else {
				node.right = new StatusNode(segment, node);
			}

			if (height(node.right) - height(node.left) == 2) { //right heavier
				if (segment.getUpper().isToRightOrLeftOf(node.segment)>0)
					rotateLeft(node);
				else {
					rotateRightThenLeft(node);
				}
			}
		}

		//Two segments have the same upper
		else if(segment.getUpper().isToRightOrLeftOf(node.segment)==0){
			//System.out.println("Inserting: The segments has same upper");
			if(segment.getLower().isToRightOrLeftOf(node.segment)<0){
				//System.out.println("But not same lower. This lies to the left.");
				goLeft(node, segment);
			}
			else if(segment.getLower().isToRightOrLeftOf(node.segment)>0){//Denna borde plocka upp segmentet och s�tta det r�tt!
				//System.out.println("But not same lower. This lies to the right.");
				goRight(node, segment);
			}
			else if(segment.getLower().isToRightOrLeftOf(node.segment)==0){
				//System.out.println("The segments lie on a line with eachother in the insert method. Nothing is done. But it could be two different polygons and an intersection.");
				
				//Height stuff here? Nothing changes so no.
			}
		}
		reHeight(node);
	}

	//Used by insert
	private void goLeft(StatusNode node, Edge segment){
		if (node.left != null) {
			insert(node.left, segment);
		} else {
			node.left = new StatusNode(segment, node);
		}

		if (height(node.left) - height(node.right) == 2) { //left heavier
			if (segment.getUpper().isToRightOrLeftOf(segment)<0) {
				rotateRight(node);
			} else {
				rotateLeftThenRight(node);
			}
		}
	}

	//Used by insert
	private void goRight(StatusNode node, Edge segment){
		if (node.right != null) {
			insert(node.right, segment);
		} else {
			node.right = new StatusNode(segment, node);
		}

		if (height(node.right) - height(node.left) == 2) { //right heavier
			//Lower might cause bugs later...Hope not.
			if (segment.getLower().isToRightOrLeftOf(node.segment)>0)//This might be the fault. Byter ut getUpper().isToRightOrLeft till getLower().isTo etc
				rotateLeft(node);
			else {
				rotateRightThenLeft(node);
			}
		}
	}


	//Traverse and update all nodes 
	//Inga noder byter plats f�rr�n inersectionpoint kommer. D� beh�ver tr�det uppdateras.
	//Vid insert(vanligt point) kommer den interna ordningen i x vara samma. Endast x-v�rdet beh�ver bytas.
	//Vid insert(intersection) k�rs f�rst en updatering av tr�det d�r alla current-x-v�rden j�mf�rs med varandra. 

	private void rotateRight(StatusNode pivot) {
		StatusNode parent = pivot.parent;
		StatusNode leftChild = pivot.left;
		StatusNode rightChildOfLeftChild = leftChild.right;
		pivot.setLeftChild(rightChildOfLeftChild);
		leftChild.setRightChild(pivot);
		if (parent == null) {
			this.root = leftChild;
			leftChild.parent = null;
			return;
		}

		if (parent.left == pivot) {
			parent.setLeftChild(leftChild);
		} else {
			parent.setRightChild(leftChild);
		}

		reHeight(pivot);
		reHeight(leftChild);
	}

	private void rotateLeft(StatusNode pivot) {
		StatusNode parent = pivot.parent;
		StatusNode rightChild = pivot.right;
		StatusNode leftChildOfRightChild = rightChild.left;
		pivot.setRightChild(leftChildOfRightChild);
		rightChild.setLeftChild(pivot);
		if (parent == null) {
			this.root = rightChild;
			rightChild.parent = null;
			return;
		}

		if (parent.left == pivot) {
			parent.setLeftChild(rightChild);
		} else {
			parent.setRightChild(rightChild);
		}

		reHeight(pivot);
		reHeight(rightChild);
	}

	private void reHeight(StatusNode node) {
		node.height = Math.max(height(node.left), height(node.right)) + 1;
	}

	private void rotateLeftThenRight(StatusNode node) {
		rotateLeft(node.left);
		rotateRight(node);
	}

	private void rotateRightThenLeft(StatusNode node) {
		rotateRight(node.right);
		rotateLeft(node);
	}

	//Detta kan bli komplext. Vad h�nder om vi har flera segment i en nod? Den ska inte kallas f�rr�n endpoint.
	//Om alla slutar i samma ska alla deletas. Men! Deleta utifr�n primary Edge!
	//Om vi n�r en lower point s� kommer tr�det omarrangeras s� att flera segment har samma x-v�rde. Alla segment
	//som d� har samma lower deletas.
	public boolean delete(Edge segment, int sweep_y) {//Kollar primary edge f�r en nod... Vad det nu �r.
		
		StatusNode target = newSearch(segment, sweep_y);
	
	
		//System.out.println("Delete says the segment to delete has upper: ("+ target.segment.getUpper().getX()+", "+target.segment.getUpper().getRealY()+"\n and lower: "+target.segment.getLower().getX()+", "+target.segment.getLower().getRealY()+")");
		
		if (target == null) return false;
		
		target = deleteNode(target, sweep_y);
		if(target!=root){
			balanceTree(target.parent);//Must balance root?
			isRoot = false;
		}
		isRoot = false;//Beleive this must be done for all caes.
		System.out.println("The status tree looks like this after deletion: ");
		traverseInOrder();
		
		return true;
	}

	
	//Funkar inte!
	private StatusNode deleteNode(StatusNode target, int sweep_y) {
		System.out.println("Inside deleteNode method. ");
		if (isLeaf(target)) { //leaf. Returns that root is leaf. Not good.
			//System.out.println("Yes, it is. Is it also a left child?");
			if (isLeftChild(target)) {//This is getting called allthough we are examining the root
				//System.out.println("Yes. Unhook it from its parents left-pointer..");
				target.parent.left = null;
			} else {
				//System.out.println("No. Unhook it from its parents right-pointer.");
				target.parent.right = null;
			}
		} else if ((target.left == null ^ target.right == null) && target!=root) { //exact 1 child (Laga tr�det, hantera root case!)
			StatusNode nonNullChild = target.left == null ? target.right : target.left; 
			//System.out.println("No. It has one child. Unhook it from its parent and attach its child instead.");
			if (isLeftChild(target)) {
				target.parent.setLeftChild(nonNullChild); 
			} else {
				target.parent.setRightChild(nonNullChild);
			}
		} else if(target!=root){//2 children
			//System.out.println("No. It has two children. Set its data to the immediatepred in orders data. Do this recursively in the whole tree.");
			StatusNode immediatePredInOrder = immediatePredInOrder(target);
			target.segment = immediatePredInOrder.segment;
			target = deleteNode(immediatePredInOrder, sweep_y);
		}
		
		
		//Fixa s� att statusen kan deleta rooten utan att allt kraschar! Har modifierat lite ovan. Kolla alla cases
		//f�r hur detta g�r till och koda nedan.
		
		/* G�r rotens barn till grannens barn. Behandla nya rotens gamla kontakter som att den deletas.
		 * Grannen kan vara leftChild p� ett enda s�tt! Den �r barn till roten.
		 * Annars �r grannen alltid rightChild
		 */
		if(target==root){
			isRoot = true;
			System.out.println("The target to delete is the root.");
			if(root.left == null && root.right == null){//Sista noden �r roten.
				//System.out.println("It's also the last element in the status tree.");
				root = null;
				//System.out.println("Sista noden har raderats fr�n tr�det.");
				return root;
			}
			//L�gg till options f�r n�r den bara har ett child
			else if(root.left!=null && root.right!=null){//Roten har tv� barn
				System.out.println("The root has two children.");
				if(height(root.left)>height(root.right)){
					//System.out.println("The left subtree is the biggest.");
					//Take the roots left neighbour as new root.
					Edge newRoot = findLeftNeighbour(root); //Find leftNeighbour fixad 2015-06-03
					if(newRoot == root.segment){
						System.out.println("Roten �r det minsta segmentet.");//This cannot happen since left subtree is bigger than right :P
					}
					System.out.println("Findlargest hittade segmentet som ska bli newRoot: ("+newRoot.getUpper().getX()+", "+newRoot.getUpper().getRealY()+"), ("+newRoot.getLower().getX()+", "+newRoot.getLower().getRealY()+")");
					
					System.out.println("Statusen ser nu ut s� h�r:");
					traverseInOrder();
					System.out.println("S�k efter newRoot nu");
					StatusNode newRootNode = newSearch(newRoot, sweep_y);//New root is not found
					System.out.println("New search �r klar)");
					
					
					//Try with tempNewRoot instead. This is set in findLargest called by findLeftNeighbour.
					if(tempNewRoot != null){
						if(isLeftChild(tempNewRoot)){//Case 1: The roots left child is the biggest in the left subtree.
							tempNewRoot.right = root.right;
							tempNewRoot.parent = null;
							root = tempNewRoot;
							
							if(root.right != null){//Added 2015-05-12
								root.right.parent = root; //Can be a nullPointer
							}
						}
						else if(isRightChild(tempNewRoot)){//There are nodes in between the root and its left neighbour
							//Detatch the left neighbour
							tempNewRoot.parent.right = tempNewRoot.left;
							if(tempNewRoot.left!=null){
								tempNewRoot.left.parent=tempNewRoot.parent;
							}
							//Move to root position
							tempNewRoot.left = root.left;
							tempNewRoot.right = root.right;
							tempNewRoot.parent = null;
							root = tempNewRoot;
							
							if(root.left != null){//Added 2015-05-12
								root.left.parent = root;
							}
							
							if(root.right != null){//Added 2015-05-12
								root.right.parent = root;
							}
						}
						
						//Take care of the former roots childrens parentpointer so it doesn't point towards the old root.
						
					}
					
				}
				else{
					//System.out.println("The right subtree is the biggest.");
					//Take the roots right neighbour as new root.
					Edge newRoot = findRightNeihbour(root);//Returns null!
					//System.out.println("Is newRoot null?");
					//System.out.println(newRoot==null);
					StatusNode newRootNode = newSearch(newRoot, sweep_y);
					if(isRightChild(newRootNode)){//Case 1: The roots right child is the smallest in the right subtree.
						newRootNode.left = root.left;
						newRootNode.parent = null;
						root = newRootNode;
						
						//2015-05-14
						if(root.left!=null){
							root.left.parent = root;
						}
					}
					else if(isLeftChild(newRootNode)){//There are nodes in between the root and its left neighbour
						//Detatch the left neighbour
						newRootNode.parent.left = newRootNode.left;
						if(newRootNode.right!=null){
							newRootNode.right.parent=newRootNode.parent;
						}
						//Move to root position
						newRootNode.left = root.left;
						newRootNode.right = root.right;
						newRootNode.parent = null;
						root = newRootNode;
						
						//2015-05-14
						if(root.left!=null){
							root.left.parent = root;
						}
						if(root.right != null){
							root.right.parent = root;
						}
						
					}
					
				}
				
			}
			
			else if((root.left == null ^ root.right == null)){//Exactly 1 child
				//System.out.println("The root had only one child. That's now the new root.");
				StatusNode nonNullChild = root.left == null ? root.right : root.left;
				nonNullChild.parent=null;
				root = nonNullChild;
				
				
				/*if(isLeftChild(nonNullChild)){
					nonNullChild.parent=null;
					root = nonNullChild;
				}
				else if(isRightChild(nonNullChild)){
					non
				}*/
			}
			reHeight(root);//Don't think you can run this too many times.
			balanceTree(root);
			target = root;
			/*
			if((target.left == null ^ target.right == null)){//Roten har exakt ett child
				StatusNode nonNullChild = target.left == null ? target.right : target.left; 
				//System.out.println("No. It has one child. Unhook it from its parent and attach its child instead.");
				
				//Ny root = findLeftNeighbour eller findRightNeighbour beroende p� heightskillnad.
				if (isLeftChild(target)) {
					target.parent.setLeftChild(nonNullChild); 
				} else {
					target.parent.setRightChild(nonNullChild);
				}
			}*/
		}
		System.out.println("Reheight the parent.");
		if(target != root){//Funkar ej. inte roten l�ngre.
			reHeight(target.parent);
			//isRoot = false;//G�rs i public deleteNode()
		}
		return target;
	}




	//Samma som neighbour??
	private StatusNode immediatePredInOrder(StatusNode node) {
		StatusNode current = node.left;
		while (current.right != null) {
			current = current.right;
		}

		return current;
	}

	private boolean isLeftChild(StatusNode child) {//Tries to perform this on the root -> Nullpointer
		//System.out.println("This node holds the following segment: ");
		//System.out.println("("+child.getSegment().getUpper().getX()+", "+child.getSegment().getUpper().getRealY()+"), ("+child.getSegment().getLower().getX()
			//	+", "+child.getSegment().getLower().getRealY()+")");
		return (child.parent.left == child);
	}

	private boolean isRightChild(StatusNode child) {
		return (child.parent.right == child);
	}

	private boolean isLeaf(StatusNode node) {
		return node.left == null && node.right == null && node.parent != null;//Added parent check for case:root 2016-03-02
	}

	private int calDifference(StatusNode node) {
		int rightHeight = height(node.right);
		int leftHeight = height(node.left);
		return rightHeight - leftHeight;
	}

	private void balanceTree(StatusNode node) {
		int difference = calDifference(node);
		StatusNode parent = node.parent;
		if (difference == -2) {
			if (height(node.left.left) >= height(node.left.right)) {
				rotateRight(node);
			} else {
				rotateLeftThenRight(node);
			}
		} else if (difference == 2) {
			if (height(node.right.right) >= height(node.right.left)) {
				rotateLeft(node);
			} else {
				rotateRightThenLeft(node);
			}
		}

		if (parent != null) {
			balanceTree(parent);
		}

		reHeight(node);
	}

	//Ska funka att s�ka efter segment som har samma upper nu
	
	//Funkar inte d� flera segment har samma upper och sweep_y == upper.getRealY!
	public StatusNode search(Edge segment) {
		return binarySearch(root, segment);
	}

	//Comparemetoden beh�ver fixas till i search.
	//Kolla vilken sida nodens segment det efters�kta segmentets upper �r p�.
	//Det ska st�mma, uppers byter n�mligen plats vid intersections osv.
	//Om det == 0 s� ligger uppern p� en linje -> Kolla mot lower???
	private StatusNode binarySearch(StatusNode node, Edge segment) {
		if (node == null){
			System.out.println("Search for segment with upper: ("+segment.getUpper().getX()+", "+segment.getUpper().getRealY()+") "+"in StatusTree returns null. ");
			return null;
		}
		if(segment.compareTo(node.segment)==0){
			System.out.println("Search har hittat r�tt segment i statusen.");
			return node;
		}

		else if (segment.getUpper().isToRightOrLeftOf(node.segment)==0) {//Funkar denna j�mf�relse eller m�ste vi testa endast koordinaterna mot varandra? Ska funka!
			//System.out.println("Search: Segmentens upper �r lika");
			if(segment.getLower().isToRightOrLeftOf(node.segment)<0){
				//System.out.println("Search: Segmentets lower �r mindre --> g� v�nster ");
				return binarySearch(node.left, segment);
			}
			else if(segment.getLower().isToRightOrLeftOf(node.segment)>0){
				//System.out.println("Search: Segmentets lower �r h�gre --> g� h�ger ");
				return binarySearch(node.right, segment);
			}
			/*
			else if(segment.getLower().compareTo(node.segment.getLower())==0){
				System.out.println("The segments lie on a line with eachother.");
				System.out.println("Binary search har hittat ett matchande segment i StatusTree!");
				return node;
			}*/
		}
		
		else if (segment.getLower().isToRightOrLeftOf(node.segment)==0){
			System.out.println("Det h�nder ingenting i denna else if.");
		}
		
		//G�r ett case som funkar d� den enas upper �r den andras lower. D� n�gon punkt ligger p� samma linje som segmentet.

		if (segment.getUpper().isToRightOrLeftOf(node.segment)<0 && node.left != null) {//G�r ej in h�r d� segmenten har samma upper
			//System.out.println("Search: segmentets upper �r mindre. G� v�nster.");
			return binarySearch(node.left, segment);
		}

		if (segment.getUpper().isToRightOrLeftOf(node.segment)>0 && node.right != null) {
			//System.out.println("Search: segmentets upper �r st�rre. G� h�ger.");
			return binarySearch(node.right, segment);
		}

		//Borde inte beh�va kolla intersections.... ?

		/*
		if(node.segment.currentXCoord(sweep_y)==segment.currentXCoord(sweep_y)){//Segmenten sammanstr�lar
			if(segment.getLower().getX()<node.segment.getLower().getX() && node.left != null){//G� v�nster om segments lower har l�gre x-v�rde
				return binarySearch(node.left, segment, sweep_y);
			}
			else if(segment.getLower().getX()<node.segment.getLower().getX() && node.right != null){
				return binarySearch(node.right, segment, sweep_y);
			}
		}*/

		System.out.println("Segmentet finns inte i StatusTree");
		//return node;//Var null innan. Fel som kan uppst�. Alla noder returneras eftersom det �r rekursivt. Fel nod returneras.
		return null;
	}
	
	
	//Ska funka att s�ka efter segment som har samma upper nu
		public StatusNode newSearch(Edge segment, int sweep_y) {
			return newBinarySearch(root, segment, sweep_y);
		}
	
		int count = 1;
		private StatusNode newBinarySearch(StatusNode node, Edge segment, int sweep_y) {
			System.out.println("========================\nInne i newBinarySearch omg�ng nr: "+count+".\n");
			count++;
			//System.out.println("newBinarySearch is searching for segment with upper: "+ segment.getUpper().getX()+", "+segment.getUpper().getRealY()+") , ("+segment.getLower().getX()+", "+segment.getLower().getRealY()+")");
			//System.out.println("�r nuvarande nod null?");
			if (node == null){
				//System.out.println("Search for segment with upper: ("+segment.getUpper().getX()+", "+segment.getUpper().getRealY()+") "+"in StatusTree returns null. ");
				return null;
			}
			/*
			System.out.println("�r segmenten samma?");
			System.out.println("�r noden null?");
			System.out.println(node==null);
			System.out.println("Har noden inget segment?");
			System.out.println(node.segment==null);
			System.out.println("Is the segment we search for null?");
			System.out.println(segment == null);
			System.out.println("The nodes segment is: ("+node.segment.getUpper().getX()+", "+node.segment.getUpper().getRealY()+"), ("+node.segment.getLower().getX()+", "+node.segment.getLower().getRealY()+")");
			*/
			
			if(segment.compareTo(node.segment)==0){//R�cker denna kontroll? Fungerar inte.
				System.out.println("Search har hittat r�tt segment i statusen.");
				return node;
			}
			

			
			//If the segments share the current y-value
			else if (segment.newCompareTo(node.segment, sweep_y)==0) {//Funkar denna j�mf�relse eller m�ste vi testa endast koordinaterna mot varandra? Ska funka!
				//System.out.println("Segmentet vi s�ker efter har samma current y-v�rde som noden som unders�ks nu.");
				//Check their lowers
				System.out.println("Vilket h�ll g�r vi?");
				//System.out.println("Search: Segmentens har samma currentX-value.");
				//Kan vara fel i dessa j�mf�relser. Missas n�got s� vi g�r till null h�r?
				
				//if-sats tillagd 2015-06-03 f�r att hantera s�kning i samband med besk�rning av segment vid intersections. Har tidigare s�kts i fel ordning pga lowers under intersection som anv�ndts f�r att navigera.
				//Specialfall f�r intersections. Om segmenten korsar mitt i funkar inte j�mf�relse med lowers innan de bytt plats i status tree, d�rav denna if.
				if(segment.getUpper().compareTo(node.segment.getUpper())!=0 && segment.getLower().compareTo(node.segment.getLower())!=0){
					//Det som ska g�ras h�r �r att g� motsatt h�ll mot vad som hade gjorts om de inte korsat varandra.
					if(segment.getLower().isToRightOrLeftOf(node.segment)<0){
						System.out.println("Search: Segmentets lower �r mindre men detta �r en intersection s�--> g� h�ger ");
						return newBinarySearch(node.right, segment, sweep_y);
					}
					else if(segment.getLower().isToRightOrLeftOf(node.segment)>0){
						System.out.println("Search: Segmentets lower �r h�gre men detta �r en intersection s� --> g� v�nster ");
						return newBinarySearch(node.left, segment, sweep_y);
					}
				}
				
				else{
					if(segment.getLower().isToRightOrLeftOf(node.segment)<0){
						System.out.println("Search: Segmentets lower �r mindre --> g� v�nster ");
						return newBinarySearch(node.left, segment, sweep_y);
					}
					else if(segment.getLower().isToRightOrLeftOf(node.segment)>0){
						System.out.println("Search: Segmentets lower �r h�gre --> g� h�ger ");
						return newBinarySearch(node.right, segment, sweep_y);
					}
					
					//Inserted 9/3 kl 15:22
					else if(segment.getLower().compareTo(node.segment.getLower())==0){
						//System.out.println("The segments share a lower point.");
						if(segment.getUpper().isToRightOrLeftOf(node.segment)<0){
							//System.out.println("But their uppers are different. Go left.");
							return newBinarySearch(node.left, segment, sweep_y);
						}
						else if(segment.getUpper().isToRightOrLeftOf(node.segment)>0){
							//System.out.println("But their uppers are different. Go right.");
							return newBinarySearch(node.right, segment, sweep_y);
						}
						
					}
					
				}
				
				
				
				
			}
			
			/*
			else if (segment.getLower().isToRightOrLeftOf(node.segment)==0){
				System.out.println("Det h�nder ingenting i denna else if.");
			}*/
			
			//G�r ett case som funkar d� den enas upper �r den andras lower. D� n�gon punkt ligger p� samma linje som segmentet.
			//Tror iofs inte att detta var problemet.
			
			if (segment.newCompareTo(node.segment, sweep_y) <0 && node.left != null) {//G�r ej in h�r d� segmenten har samma upper
				//System.out.println("newBinarySearch: segmentets upper �r mindre. G� v�nster.");
				return newBinarySearch(node.left, segment, sweep_y);
			}
			
			//Denna j�mf�relse funkar inte!
			if (segment.newCompareTo(node.segment, sweep_y) >0 && node.right != null) {
				//System.out.println("newBinarySearch: segmentets upper �r st�rre. G� h�ger.");
				return newBinarySearch(node.right, segment, sweep_y);
			}

			//Borde inte beh�va kolla intersections.... ?

			/*
			if(node.segment.currentXCoord(sweep_y)==segment.currentXCoord(sweep_y)){//Segmenten sammanstr�lar
				if(segment.getLower().getX()<node.segment.getLower().getX() && node.left != null){//G� v�nster om segments lower har l�gre x-v�rde
					return binarySearch(node.left, segment, sweep_y);
				}
				else if(segment.getLower().getX()<node.segment.getLower().getX() && node.right != null){
					return binarySearch(node.right, segment, sweep_y);
				}
			}*/

			System.out.println("Segmentet finns inte i StatusTree");
			//System.out.println("Efters�kta segmentet �r: ("+segment.getUpper().getX()+", "+segment.getUpper().getRealY()+"), ("+segment.getLower().getX()+", "+segment.getLower().getRealY()+")");
			//System.out.println("Traverse tree to see if it is there.");
			//this.traverseInOrder();
			//return node;//Var null innan. Fel som kan uppst�. Alla noder returneras eftersom det �r rekursivt. Fel nod returneras.
			return null;
		}


	public void traverseInOrder() {
		if(root!=null){
			System.out.println("ROOT " + root.toString());
			inorder(root);
			System.out.println();
		}
		else System.out.println("The Status Tree is empty.");
	}

	private void inorder(StatusNode node) {
		if (node != null) {
			inorder(node.left);
			System.out.print(node.toString());
			inorder(node.right);
		}
	}


	//Find all segments that contain p
	public ArrayList<Edge> findSegmentsWithPoint(Endpoint p){
		return findSegmentsWithPoint(root, p);
	}

	private ArrayList<Edge> findSegmentsWithPoint(StatusNode node, Endpoint p){
		ArrayList<Edge> segments = new ArrayList<Edge>();
		if (node != null) {
			inorder(node.left);
			inorder(node.right);
			//Do stuff depending on we're in a left or right child.
			if(containEndpoint(node.segment, p)){
				segments.add(node.segment);
			}
		}
		return segments;
	}

	//Check if a segment contains an endpoint. Funkar denna f�r om en upperpoint intersectas av en lower??
	//Dvs if(p.isUpper && intersectingpoint.isLower)
	private boolean containEndpoint(Edge segment, Endpoint p){
		Endpoint upper = segment.getUpper();
		Endpoint lower = segment.getLower();
		double k, m;

		if(segment.getUpper().getX()==segment.getLower().getX() && segment.getLower().getX()==p.getX()){//We have a vertical segment.  x = x avoid division by zero!
			return true;				//The point p lies on the vertical line. We assume p inside correct y-interval.	
		}
		else{
			k = (upper.getY()-lower.getY())/(upper.getX()-lower.getX());
			m = segment.getUpper().getY() - (k*segment.getUpper().getX());//Har varit fel h�r
			//Equation of straight line
			if(p.getY() == (k * p.getX() + m)){//Check if p is on the line y = kx + m
				return true;
				//Unnecessary check if p is within the interval of the line segment
				/*if(upper.getX()>lower.getX()){
						if(p.getX()<=upper.getX()&&p.getX()>=lower.getX()){
							return true;
						}
					}
					if(upper.getX()<lower.getX()){
						if(p.getX()>=upper.getX()&&p.getX()<=lower.getX()){
							return true;
						}
					}*/
			}
		}
		/*
			else if(upper.getX()==lower.getX()){//The segment is vertical
				if(p.getY()<upper.getY()&&p.getY()>lower.getY()){//p:s y-coordinate is within the segment
					return true;
				}
			}*/
		return false;
	}	

	//Calculate if two segments has an intersection point. 
	//Here, we cannot count with the sweep line telling us that two segments exists between eachother at the intersection.
	public Endpoint findIntersection(Edge segment1, Edge segment2){
		//Check if the segments belong to different polygons

		//if(segment1.getUpper().getBelonging()!=segment2.getUpper().getBelonging()&&(segment1.getUpper().getBelonging()!=0&&segment2.getUpper().getBelonging()!=0)){

		//Case 1: no vertical lines --> This avoids division by zero to get k
		if(segment1.getUpper().getX()!=segment1.getLower().getX()&&segment2.getUpper().getX()!=segment2.getLower().getX())
		{
			double k1 = (segment1.getUpper().getY()-segment1.getLower().getY())/(segment1.getUpper().getX()-segment1.getLower().getX());
			double m1 = segment1.getUpper().getY() - (k1*segment1.getUpper().getX()); 

			double k2 = (segment2.getUpper().getY()-segment2.getLower().getY())/(segment2.getUpper().getX()-segment2.getLower().getX());
			double m2 = segment2.getUpper().getY() - (k2*segment2.getUpper().getX());

			double x = (m1-m2)/(k2-k1);//Correct?
			double y1 = k1*x+m1;
			double y2 = k2*x+m2;
			if(y1==y2){//Varning f�r avrundningsfel kanske??? Hur har jag t�nkt h�r??
				//�ven varning f�r att vi kan befinna oss p� linjen men utanf�r segmentets �ndpunkter.

				//Check that the intersection occurrs within the interval of the segment
				if(y1<=segment1.getUpper().getY()&&y1>segment1.getLower().getY() && y2<=segment2.getUpper().getY()&&y2>segment2.getLower().getY()){
					//Tror att ovan check i y r�cker f�r att kolla om intersectionpunkten ligger i r�tt intervall.
					//Annars m�ste x-koordinaterna kollas ocks�. D� kan man anv�nda k-v�rdena f�r att se om upper_x �r st�rre eller mindre �n lower_x osv: Positive k-value --> upper x > lower x and vice versa

					//We have our intersection point!
					Endpoint intersection = new Endpoint((int)x, (int)y1);
					intersection.setIntersection(true);
					ArrayList<Edge> intersectingSegments = new ArrayList<Edge>();
					intersectingSegments.add(segment1);
					intersectingSegments.add(segment2);
					intersection.setIntersectingEdges(intersectingSegments);
					return intersection;
				}	
			}
		}


		//Case 2: Segment 1 is vertical and segment2 sloping
		else if(segment1.getUpper().getX() == segment1.getLower().getX() && segment2.getUpper().getX() != segment2.getLower().getX()){
			//Check if segment2 crosses segment1
			if(segment2.getUpper().getX()<=segment1.getUpper().getX() && segment2.getLower().getX()>=segment1.getUpper().getX() || segment2.getLower().getX()<=segment1.getUpper().getX() && segment2.getUpper().getX()>=segment1.getUpper().getX()){
				//Calculate intersection point
				double k = (segment2.getUpper().getY()-segment2.getLower().getY())/(segment2.getUpper().getX()-segment2.getLower().getX());
				double m = segment2.getUpper().getY()-(k*segment2.getUpper().getX());
				double y = k * segment1.getUpper().getX() + m;//The lines intersect in this point

				//Check if the crossing is in the correct y-interval.(Maybe double check but why not :P)
				if(y<=segment1.getUpper().getY()&&y>=segment1.getLower().getY() && y<=segment2.getUpper().getY()&&y>=segment2.getLower().getY()){
					//We have an intersection point!
					Endpoint intersection = new Endpoint(segment1.getUpper().getX(), (int)y);
					intersection.setIntersection(true);
					ArrayList<Edge> intersectingSegments = new ArrayList<Edge>();
					intersectingSegments.add(segment1);
					intersectingSegments.add(segment2);
					intersection.setIntersectingEdges(intersectingSegments);
					return intersection;
				}
			}
		}
		//Case 3: The other way around :) Endast kopierad kod fr�n ovan. Har bytt ut segmentens namn med varandra. Kan vara fel i upper och lower men tror inte det. Nu �r jag tl���tt.
		else if(segment2.getUpper().getX() == segment2.getLower().getX() && segment1.getUpper().getX() != segment1.getLower().getX()){
			if(segment1.getUpper().getX()<=segment2.getUpper().getX() && segment1.getLower().getX()>=segment2.getUpper().getX() || segment1.getLower().getX()<=segment2.getUpper().getX() && segment1.getUpper().getX()>=segment2.getUpper().getX()){
				//Calculate intersection point
				double k = (segment1.getUpper().getY()-segment1.getLower().getY())/(segment1.getUpper().getX()-segment1.getLower().getX());
				double m = segment1.getUpper().getY()-(k*segment1.getUpper().getX());
				double y = k * segment2.getUpper().getX() + m;

				//Check if the crossing is in the correct y-interval.
				if(y<=segment2.getUpper().getY()&&y>=segment2.getLower().getY() && y<=segment1.getUpper().getY()&&y>=segment1.getLower().getY()){
					//We have an intersection point!
					Endpoint intersection = new Endpoint(segment2.getUpper().getX(), (int)y);
					intersection.setIntersection(true);
					ArrayList<Edge> intersectingSegments = new ArrayList<Edge>();
					intersectingSegments.add(segment1);
					intersectingSegments.add(segment2);
					intersection.setIntersectingEdges(intersectingSegments);
					return intersection;
				}
			}
			System.out.println("Find intersection returns null");
			return null;
		}


		System.out.println("The checked segments belong to the same polygons or to none.");
		return null;
	}

	public void swapPosition(StatusNode node1, StatusNode node2){
		StatusNode temp = new StatusNode();
		temp = node1.copyNode(temp);
		node1 = node2.copyNode(node1);
		node2 = temp;
		int tempHeight = node1.height;
		node1.height = node2.height;
		node2.height = tempHeight;
		//Gl�m inte att beh�lla height som den var
	}

	public Edge findLargest(StatusNode root){
		//System.out.println("Inne i findLargest");
		//System.out.println("Is the current examined nodes null?");
		if(root!=null){
			//System.out.println("No, is the right child of the roots left child null?");
			if(root.right == null){
				//System.out.println("Yes! Return the current nodes segment.");
				return root.segment;
			}
			else {
				//System.out.println("No, run findLargest of roots childs right child.");
				return findLargest(root.right);//Null?
			}
		}
		else{
			//System.out.println("FindLeftNeighbour returns null.");
			return null;
		}
		
	}

	public Edge findSmallest(StatusNode root){
		if(root!=null){
			if(root.left==null){
				return root.segment;
			}
			else return findSmallest(root.left);
		}
		else return null;
	}
	public Edge findRightNeihbour(Edge segment, int sweep_y){
		StatusNode node = newSearch(segment, sweep_y);//Nullpointer here??
		System.out.println("Inside findRightNeighbour.");
		//System.out.println(node != null);
		return findRightNeihbour(node);
	}


	//Tillfixad kl 20:08 8/2
	private Edge findRightNeihbour(StatusNode n){
		if(n.right!=null){//Nullpointer here
			return findSmallest(n.right);
		}
		else if(n.parent!=null){
			if(isLeftChild(n)){
				return n.parent.segment;
			}
			else if(n.parent.parent==null){
				return null;
			}
			else if(n.parent == n.parent.parent.left){
				return n.parent.parent.segment;
			}
			else{
				//System.out.println("Noden �r ett leftChild men har inga leftchilds, �ven dess parent �r ett leftchild --> ingen v�nstergranne.");
				return null;
			}
		}
		else{//Ingen leftnode och ingen parent
			System.out.println("Segmentet har ingen v�nstergranne.");
			return null;
		}
		
		
		/*
		if(n.right == null && n==n.parent.left){
			return n.parent.segment;
		}
		else if(n.right == null && n == n.parent.right){
			if(n.parent.parent==null){
				return null;
			}
			else if(n.parent == n.parent.parent.left){
				return n.parent.parent.segment;//Ett case jag inte t�nkt p� f�rut.
			}
			else return null;
			
		}
		else if(n.right != null){
			return findSmallest(n.right);
		}
		else return null;*/
	}

	

	//Fel i denna. Returnerar null i vissa sammanhang. �ndra s� att den alltid returnerar ett segment. MEN
	//Om segmentet �r samma som det inmatade, eller noden �r samma s� visar det att den inte hittar.
	private Edge findLeftNeighbour(StatusNode n){//Skickas in en null h�r eller en som har parent==null.
		System.out.println("private findLeftNeighbour �r kallad.");
		//System.out.println(n);
		
		/* Cases:
		 * 1. Om left finns: findLargest d�r
		 * 2. Om left inte finns: returnera parent om vi �r ett h�gerbarn
		 * 3. Om left inte finns, om vi inte �r ett h�gerbarn MEN parent �r h�gerbarn: returnera grand parent.
		 * 4. Annars, returnera null(NEJ returnera detta segment!)
		 */
		
		if(n.left!=null){
			//System.out.println("Roten har ett left child. K�r findLargest p� det.");
			return findLargest(n.left);
		}
		if(n.left == null && n.parent!=null){
			if(isRightChild(n)){
				tempNewRoot = n.parent;
				return n.parent.segment;
			}
			
			else if(n.parent.parent==null){
				System.out.println("F�rs�ker hitta en leftNeighbour i statusen. Ingen finns.");
				return null;//Feeel!
			}
			else if(isLeftChild(n) && n.parent == n.parent.parent.right){
				tempNewRoot = n.parent.parent;
				return n.parent.parent.segment;
			}
			else{
				System.out.println("F�rs�ker hitta en leftNeighbour i statusen men hamnar i sista elsen i findLefNeighbour");
				return null;
			}
		}
		else{//Ingen leftnode och ingen parent
			System.out.println("Skulle tro att inputsegmentet till findLeftneighbour �r det minsta fr�n b�rjan.");
			return null;
		}
	}
	
	
	//Denna �r aktuell!
	public Edge findLeftNeighbour(Edge segment, int sweep_y){
		System.out.println("Public findLeftNeighbour �r kallad.");
		StatusNode node = newSearch(segment, sweep_y); //Returnerar null. Varf�r???
		System.out.println("�r s�kresultatet efter v�nstraste segmentet null?");
		System.out.println(node == null);
		return findLeftNeighbour(node);
		
		/*
		System.out.println("S�k efter noden som ska hittas grannen till. �r denna null?");
		System.out.println(node==null);
		/* 1. Om left finns: findLargest d�r
		 * 2. Om left inte finns --> returnera parent om vi �r ett h�gerbarn
		 * 3. Om left inte finns, om vi inte �r ett h�gerbarn MEN parent �r h�gerbarn --> returnera grandparent.
		 * 4. Annars, returnera null
		 */
	}

	
	/*
	private Edge newFindLeftNeighbour(StatusNode n, Edge segment){
		//Cases left:
		
		
		//Case 1: The left child is null
		if(n.left==null){
			//Case 1.1: No left child & no parent 																	(and the same as the input)
			if(n.parent==null){//Har inte hamnat h�r pga rekursion. n m�ste vara samma nod som originalinputen.		//) && n.segment.isToRightOrLeftOf(segment)==0){
				System.out.println("The segment has no left neighbour");
				return null;
			}

			//Case 1.2: No left but IS a right child
			else if(isRightChild(n)){//Detta �r fel! Ska endast vara en check preciiiis i b�rjan! F�r ej vara med i rekursionsmetoden.
				return n.parent.segment;
			}

			else return n.segment; 	//Is this correct? This should return the current nodes segment 
									//if it is a leftChild that has a parent but no left child of its own.
		}
		else if(n.right!=null){
			if(n.right.segment.isToRightOrLeftOf(segment)<0){
				newFindLeftNeighbour(n.right, segment);
			}
		}
	}*/



	public static void main(String[] args) {
		AVLTree avl = new AVLTree();

		/*
	        avl.insert(1);
	        avl.traverseInOrder();
	        avl.insert(2);
	        avl.traverseInOrder();
	        avl.insert(3);
	        avl.traverseInOrder();
	        avl.insert(4);
	        avl.traverseInOrder();
	        avl.delete(1);
	        avl.traverseInOrder();
	        avl.insert(5);
	        avl.traverseInOrder();
	        avl.insert(6);
	        avl.traverseInOrder();
	        avl.delete(3);
	        avl.traverseInOrder();
	        avl.delete(5);
	        avl.traverseInOrder();*/
	}

	public void findIntersectingSegments(Endpoint p, ArrayList<Edge> intersectionLower, ArrayList<Edge> intersectionInterior){
		findIntersectingSegments(p, root, intersectionLower, intersectionInterior);
	}



	/*
	 * Denna funktion tar en eventpoint och hittar om det finns intersections med befintliga segment i statusen.
	 */
	private void findIntersectingSegments(Endpoint p, StatusNode node, ArrayList<Edge> intersectionLower, ArrayList<Edge> intersectionInterior ){
		//Traverse the tree and find all segments that goes through p

		if(node != null){
			findIntersectingSegments(p, node.left, intersectionLower, intersectionInterior);
			//J�mf�r p med segmenten som finns i nodens segment h�r. P.getRealY() �r sweep_y
			if(calcIntersection(p, node.segment)=="lower"){
				//System.out.println("findIntersectingSegments has spotted a lower intersection point");
				intersectionLower.add(node.segment);//Ska jag lagra att det �r en intersection i sj�lva segmentet?
				//Ta bort segmentet??
			}
			else if(calcIntersection(p, node.segment)=="interior"){
				//System.out.println("findIntersectingSegments has spotted a lower intersection point");
				intersectionInterior.add(node.segment);
				//Ta bort segmentet och s�tta in ett nytt med p som upper point?
			}
			findIntersectingSegments(p, node.right, intersectionLower, intersectionInterior);
		}


	}

	private String calcIntersection(Endpoint p, Edge segment){
		if(p.testNewSideMethod(segment)==0){
			if(p.compareTo(segment.getLower())==0){
				//System.out.println("findIntersectingSegments has spotted a lower intersection point");
				return "lower";
			}
			else {
				//System.out.println("findIntersectingSegments has spotted an interior point");
				return "interior";
			}
		}
		else return "nointersect";
		
		//if(segment.getUpper().getX()==segment.getLower().getX()){//Avoid division by zero
			
			
			/*if(p.getX() == segment.getUpper().getX()){
				//We have an intersection
				if(p.compareTo(segment.getLower())==0){
					return "lower";
				}
				else return "interior";//Must check the interval! But otherwhise the segment wouldn't be in the status?
			}*/
		}
		
		/*
		else{

			double k = (segment.getUpper().getRealY()-segment.getLower().getRealY())/(segment.getUpper().getX()-segment.getLower().getX());
			double m = segment.getUpper().getRealY()-(k*segment.getUpper().getX());
			double y = k * p.getX() + m;
			if((int)y == p.getRealY()){
				//We have an intersection. Check if it is a lower point or in the interior. Should not be an upper. All upper should be stored in one p.
				if(segment.getLower().compareTo(p)==0){
					//We have a lower point
					return "lower";
				}else return "interior";

			}}

		return "nointersect";
	}*/



	//Denna funktion tar en eventpoint och hittar om det finns intersections med befintliga segment i statusen.
	public void deleteWithLower(Endpoint lower, ArrayList<Edge> segments){
		deleteWithLower(lower, root, segments);
	}

	private void deleteWithLower(Endpoint lower, StatusNode node, ArrayList<Edge> segments){
		//Traverse the tree and find all segments that has this lower

		if(node != null){
			deleteWithLower(lower, node.left, segments);
			//J�mf�r lower med segmenten som finns i nodens segment h�r.
			if(node.getSegment().getLower().compareTo(lower)==0){
				//Ta bort segmentet
				segments.add(node.segment);
			}
			deleteWithLower(lower, node.right, segments);

		}


	}


}


