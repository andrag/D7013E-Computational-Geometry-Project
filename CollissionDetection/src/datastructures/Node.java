package datastructures;

import java.io.Serializable;

import algorithm.Edge;
import algorithm.Endpoint;

/**
 * Node class for the AVLTree
 */

public class Node{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Node left, right;
	Node parent;
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
