package datastructures;

import java.io.Serializable;

import algorithm.Endpoint;

//import avl_tree.AVLTree;
//import avl_tree.Node;


/*
 * Kod hittad p� http://stackoverflow.com/questions/5771827/implementing-an-avl-tree-in-java?rq=1
 */


public class AVLTree implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int counter = 0;
    public Node root = null;

    public void insert(Endpoint eventpoint) {
        insert(root, eventpoint);
        //2016-03-31: In the StatusTree, balanceTree(root) is now called here to fix that the tree fails to balance at root level.
    }

    private int height(Node node) {
        return node == null ? -1 : node.height;
    }

    
    public boolean isEmpty(){
    	return root == null ? true : false;
    }
    
    
    //Kan beh�vas en �ndring d�r upper points prioriteras f�re lower points om de intersectar.
    //Annars raderas lower innan upper s�tts in i statusen.
    private void insert(Node node, Endpoint eventpoint) {
        if (root == null) {//Stod root h�r innan. B�r vara node!!!! Nope. Det ska vara root :)
            root = new Node(eventpoint, null);//Ett fel h�r! Kanske ska vara root �nd�. F�rs�ker fixa med parent.
            return;
        }

        if (eventpoint.compareTo(node.eventPoint)<0) {//The eventpoint is smaller than the nodes eventpoint
            goLeft(node, eventpoint);
        	/*if (node.left != null) {
                insert(node.left, eventpoint);
            } else {
                node.left = new Node(eventpoint, node);
            }

            if (height(node.left) - height(node.right) == 2) { //left heavier. Kollar inte detta �nda upp i root.
                if (eventpoint.compareTo(node.left.eventPoint)<0) {
                    rotateRight(node);
                } else {
                    rotateLeftThenRight(node);
                }
            }*/
        } else if (eventpoint.compareTo(node.eventPoint)>0) {
            goRight(node, eventpoint);
        	/*if (node.right != null) {
                insert(node.right, eventpoint);
            } else {
                node.right = new Node(eventpoint, node);
            }

            if (height(node.right) - height(node.left) == 2) { //right heavier
                if (eventpoint.compareTo(node.right.eventPoint)>0)
                    rotateLeft(node);
                else {
                    rotateRightThenLeft(node);
                }
            }*/
        }
        //Case: Eventpoint already exists in a node.
        else if(eventpoint.compareTo(node.eventPoint)==0){//Two uppers coinside
        	System.out.println("The eventpoints coinside. Are both uppers?");
        	if(eventpoint.isUpper && node.eventPoint.isUpper){
        		System.out.println("Yes");
        		System.out.println("Two upper eventpoints coincide here. Are one of them also a lower? Could that cause problems?");
        		System.out.println(eventpoint.isLower);
        		System.out.println(node.eventPoint.isLower);
        		//Duplicate upper eventpoint. Store all segments with same uppers in one upper point.
            	//node.eventPoint.addUpperTo(eventpoint.getUpperTo().get(0));//Should not be needed. This is all set att creation of edge.
        	}
        	else if(eventpoint.isUpper() && node.eventPoint.isLower()){
        		System.out.println("No");
        		//Prioritize upper by going right!
        		goRight(node, eventpoint);
        		
        	}
        	else if(eventpoint.isLower() && node.eventPoint.isUpper()){
        		//Prioritize upper by going left
        		System.out.println("No");
        		 goLeft(node, eventpoint);
        	}
        	else{
        		System.out.println("It's nothing!!!!");
        		//Both are lower. Duplicates here doesn't matter.
        	}
        	
        	
        }
        
        //Finns inget case d�r tv� lower sammanstr�lar(Hur �r det med lower och upper?). 
        //L�sning 1: L�gg till alla segment i punktens lowerTo. Dessa raderas i collisiondetection
        //d� 

        reHeight(node);
    }
    
    private void goRight(Node node, Endpoint eventpoint){
    	 if (node.right != null) {
             insert(node.right, eventpoint);
         } else {
             node.right = new Node(eventpoint, node);
         }

         if (height(node.right) - height(node.left) == 2) { //right heavier
             if (eventpoint.compareTo(node.right.eventPoint)>0)
                 rotateLeft(node);
             else {
                 rotateRightThenLeft(node);
             }
         }
    }
    
    private void goLeft(Node node, Endpoint eventpoint){
    	 if (node.left != null) {
             insert(node.left, eventpoint);
         } else {
             node.left = new Node(eventpoint, node);
         }

         if (height(node.left) - height(node.right) == 2) { //left heavier. Kollar inte detta �nda upp i root.
             if (eventpoint.compareTo(node.left.eventPoint)<0) {
                 rotateRight(node);
             } else {
                 rotateLeftThenRight(node);
             }
         }
    }

    private void rotateRight(Node pivot) {
        Node parent = pivot.parent;
        Node leftChild = pivot.left;
        Node rightChildOfLeftChild = leftChild.right;
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

    private void rotateLeft(Node pivot) {
        Node parent = pivot.parent;
        Node rightChild = pivot.right;
        Node leftChildOfRightChild = rightChild.left;
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

    private void reHeight(Node node) {
        node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    private void rotateLeftThenRight(Node node) {
        rotateLeft(node.left);
        rotateRight(node);
    }

    private void rotateRightThenLeft(Node node) {
        rotateRight(node.right);
        rotateLeft(node);
    }

    public boolean delete(Endpoint eventpoint) {
        
    	Node target = search(eventpoint);
        System.out.println("Target f�r deletion �r hittad.");
        if (target == null) return false;
        System.out.println("Nu ska target deletas.");
        target = deleteNode(target);
        System.out.println("K�r balanceTree.");
        
        
        if(root!=null && target != root){// If added 2015-05-13
        	balanceTree(target.parent);
        }
        if(target == root){//Added 2015-05-13
        	balanceTree(target);
        }
        return true;
    }

    private Node deleteNode(Node target) {
    	System.out.println("Inne i deleteNode");
        if (isLeaf(target)) { //leaf
        	System.out.println("Target is leaf.");
            if (isLeftChild(target)) {
                target.parent.left = null;
            } else {
            	System.out.println("Dessutom ett right");
                target.parent.right = null;//H�r kanske h�nsyn m�ste tas till ett eventuellt left subtr�d.
            }
        } else if (target.left == null ^ target.right == null && target != root) { //exact 1 child
            Node nonNullChild = target.left == null ? target.right : target.left; 
            if (isLeftChild(target)) {
                target.parent.setLeftChild(nonNullChild);
                nonNullChild.setParent(target.parent);//Nullpointer d� vi f�rs�ker ta bort roten
            } else {
                target.parent.setRightChild(nonNullChild);
                nonNullChild.setParent(target.parent);
            }
        
            //Might have to add a check here so that we don't mess with deleting root here. Might not work as it should.
        } else if(target.left!=null && target.right!=null && target != root){//2 children
            Node immediatePredInOrder = immediatePredInOrder(target);
            target.eventPoint = immediatePredInOrder.eventPoint;
            target = deleteNode(immediatePredInOrder);
        } 
        
        
        //Two else-if added 2015-05-13. The eventQueue root couldn't be deleted. Try to solve.
        else if(target == root && root.right == null && root.left != null){
        	root = target.left;
        	root.parent = null;
        	return root;
        }
        
        else if (target == root && root.right == null && root.left == null){
        	root = null;
        	return root;
        }
       
        // Tried to handle root deletion. Added 2015-05-12
        /*else if(target == root){
        	
        	//Case 1: The root has only one child
        	if (target.left == null ^ target.right == null && target != root) { //exact 1 child
                Node nonNullChild = target.left == null ? target.right : target.left; 
                
                //No need for more changes than this since it's a self-balancing tree.
                nonNullChild.parent = null;
                root = nonNullChild;
        	}
        	
        	//Might have to do a check here for deletion of root with two children.
        }*/
        System.out.println("K�r reheight. Slut p� deleteNode.");
        reHeight(target.parent);
        return target;
    }

    private Node immediatePredInOrder(Node node) {
        Node current = node.left;
        while (current.right != null) {
            current = current.right;
        }
        return current;
    }

    private boolean isLeftChild(Node child) {
        return (child.parent.left == child);
    }

    private boolean isLeaf(Node node) {
        return node.left == null && node.right == null;
    }

    private int calDifference(Node node) {
        int rightHeight = height(node.right);
        int leftHeight = height(node.left);
        return rightHeight - leftHeight;
    }

    private void balanceTree(Node node) {
        int difference = calDifference(node);
        Node parent = node.parent;
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
        System.out.println("Balansering k�rd. Nu reHeight igen.");
        reHeight(node);
    }

    public Node search(Endpoint eventpoint) {
        return binarySearch(root, eventpoint);
    }

    private Node binarySearch(Node node, Endpoint eventpoint) {
        counter++;
    	System.out.println("Binary search run for the: "+counter+" time.");
    	if (node == null){
    		System.out.println("Noden �r null.");
    		return null;
    	}
    	System.out.println("Ska kolla om eventpoint == node.eventPoint.");
        if (eventpoint == node.eventPoint) {
        	System.out.println("Ja");
            return node;
        }
        System.out.println("Nej.");
        System.out.println("The node is: "+node==null);
        System.out.println("The nodes left child: "+node.left==null);
        System.out.println("The nodes eventpoint is: "+node.eventPoint==null);//Detta funkar!
        System.out.println(eventpoint==null);
        if (eventpoint.compareTo(node.eventPoint)<0 && node.left != null) {//Nullpointer
            return binarySearch(node.left, eventpoint);
        }

        if (eventpoint.compareTo(node.eventPoint)>0 && node.right != null) {
            return binarySearch(node.right, eventpoint);
        }

        return null;
    }

    public void traverseInOrder() {
        System.out.println("ROOT " + root.toString());
        inorder(root);
        System.out.println();
    }

    private void inorder(Node node) {
        if (node != null) {
            inorder(node.left);
            System.out.print(node.toString());
            inorder(node.right);
        }
    }
    
    
    //Returns a null. Has no endpoint.
    //Denna funkar inte. Returnerar alltid null!
    public Endpoint findLargest(Node root){
    	/*System.out.println("Going through node of height:" + root.height+" on the way to the largest eventpoint.");
    	//if(root!=null){
    		if(root.right == null){
    			System.out.println("Returning an event point");
    			return root.eventPoint;
    		}
    		else findLargest(root.right);
    	//}
    	System.out.println("Returning null");
    	return null;*/
    	if(root==null){
    		return null;
    	}
    	else if(root.right==null){
    		return root.getEventpoint();
    	}
    	
    	Node current = root;
    	while(current.right!=null){
    		current=current.right;
    	}
    	return current.getEventpoint();
    }
    
    public Endpoint findSmallest(Node root){
    	if(root!=null){
    		if(root.left==null){
    			return root.eventPoint;
    		}
    		else findSmallest(root.left);
    	}
    	return null;
    }
    
    public Endpoint findRightNeihbour(Node n){
    	if(n.right==null&&n==n.parent.left){
    		return n.parent.eventPoint;
    	}
    	else if(n.right!=null){
    		return findSmallest(n.right);
    	}
    	else return null;
    }
    
    public Endpoint findLeftNeihbour(Node n){
    	if(n.left==null&&n==n.parent.right){
    		return n.parent.eventPoint;
    	}
    	else if(n.left!=null){
    		return findLargest(n.left);
    	}
    	else return null;
    }

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

}
