package datastructures;

import java.util.ArrayList;

import algorithm.Edge;
import algorithm.Endpoint;

public class StatusNode {

	public StatusNode left, right;
	public StatusNode parent;
	//int value ;
	//Endpoint eventPoint;
	Edge segment;
	int height = 0;
	private ArrayList<Edge> severalSegments;//If several segments share a single upper endpoint.
	//I insert: If node.severalSegments != null --> Create new nodes containing the new segments. 
	//Place them in status depending on currentXChoord.

	public StatusNode(Edge segment, StatusNode parent) {
		this.segment = segment;
		this.parent = parent;
	}
	
	public StatusNode(){
		left = null;
		right = null;
		parent = null;
		segment = null;
	}

	@Override
	public String toString() {
		return "Upper x: "+segment.getUpper().getX() + "Upper y: "+ segment.getUpper().getRealY()+"Lower x: "+segment.getLower().getX()+"Lower y: "+ segment.getLower().getRealY()+
				"height " + height + " parent " + (parent == null ?
				"NULL" : "Parent upper: ("+parent.segment.getUpper().getX()+", "+parent.segment.getUpper().getRealY()+") and parent lower: ("+parent.segment.getLower().getX()+", "+parent.segment.getLower().getRealY()+")") + " | Is this a left child?"+ isLeftChild()+"\n";
	}

	void setLeftChild(StatusNode child) {
		if (child != null) {
			child.parent = this;
		}

		this.left = child;
	}

	void setRightChild(StatusNode child) {
		if (child != null) {
			child.parent = this;
		}

		this.right = child;
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
	
	
	
	//Copies this nodes links and data to a new node.
	public StatusNode copyNode(StatusNode newNode){
		newNode = new StatusNode(segment, parent);
		newNode.left = left;
		newNode.right = right;
		//Height will not be copied since it has to do with position in the tree.
		return newNode;
	}
	
	public Edge getSegment(){
		return segment;
	}
	/*Flyttad till Edge
	public int currentXChoord(int y){
		//If a vertical segment, it's x-choordinate is always the same.
		if(segment.getUpper().getX()==segment.getLower().getX()){
			return segment.getLower().getX();//Any x will do. They are the same. This also skips dividing by zero in y = kx+m below.
		}
		else{
			double k = (segment.getUpper().getY()-segment.getLower().getY())/(segment.getUpper().getX()-segment.getLower().getX());
			int lower_y = segment.getLower().getY();
			double x = (y - lower_y)/k;
			return (int) x;
		}
	}
	
	//J�mf�r currentX f�r ett segment med upperX f�r en nod. B�ttre att ha current p� ett segment?
	public boolean isSmallerThan(Edge segment, int sweep_y){
		if(currentXChoord(segment.getUpper().getY()) < segment.getUpper().getX()){
			return true;
		}
		else return false;
	}*/
}




/*import polygontest.*;

	/*
 * En nod i det BST som �r EventQueue
 * @Params: Edge, noden h�ller en endpoint, 
 * @Params: isUpper, noden h�ller start eller endpoint beroende p� detta
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


		//Endpointen avg�r var tr�det noden ska ligga.
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


