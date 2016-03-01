package datastructures;

import algorithm.Edge;
import algorithm.Endpoint;

//import avl_tree.Node;


//import avl_tree.AVLTree.Node;

public class Node {
	public Node left, right;
	Node parent;
	//int value ;
	Endpoint eventPoint = new Endpoint(1, 1);
	Edge segment;
	public int height = 0;

	public Node(Endpoint eventpoint, Node parent) {
		this.eventPoint = eventpoint;
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "X: "+ eventPoint.getX()+", realY: "+eventPoint.getRealY() + " height " + height + " parent " + (parent == null ?
				"NULL" : "X: "+parent.eventPoint.getX()+"realY: "+parent.eventPoint.getRealY()) + " | Is this a left child?"+ isLeftChild()+
				" | Is this an upper? "+ eventPoint.isUpper+ "| "+ "Is this a lower? "+ eventPoint.isLower+"\n";
	}

	public void setLeftChild(Node child) {
		if (child != null) {
			child.parent = this;
		}

		this.left = child;
	}

	public void setRightChild(Node child) {
		if (child != null) {
			child.parent = this;
		}

		this.right = child;
	}
	
	public void setParent(Node parent){
		this.parent = parent;
	}
	
	public Edge getSegment(){
		return segment;
	}
	
	public Endpoint getEventpoint(){
		return eventPoint;
	}
	
	private boolean isLeftChild() {
		if(parent!=null){
			if(parent.left!=null){
				return parent.left == this;
			}
			else return false;
		}
		else return false;
	}
	
	
}




/*import polygontest.*;

/*
 * En nod i det BST som är EventQueue
 * @Params: Edge, noden håller en endpoint, 
 * @Params: isUpper, noden håller start eller endpoint beroende på detta
 */

/*public class Node {

	private Endpoint point;
	private Edge segment;


	public Node leftChild = null;
	public Node rightChild = null;

	public int height;

	public Node(Endpoint point){
		this.point = point;
		segment = null;
	}


	//Endpointen avgör var trädet noden ska ligga.
	public Endpoint getEndpoint(){
		return point;
	}

	public Edge getSegment(){
		return segment;//Can be null
	}

	public void setSegment(Edge segment){
		this.segment = segment;
	}

	public int getHeight(){
		return height;
	}


	public Node getLeftChild(){
		return leftChild;
	}

	public Node getRightChild(){
		return rightChild;
	}




}*/
