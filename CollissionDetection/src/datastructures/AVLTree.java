package datastructures;

import java.io.Serializable;

import algorithm.Endpoint;



/**
 * Implementation of a self-balancing binary search tree
 * This class is used for the sorted queue of event points
 * The self-balancing ensures a worst case time for element retrieval operation of O(log n)
 * Code inspired from http://stackoverflow.com/questions/5771827/implementing-an-avl-tree-in-java?rq=1
 */


public class AVLTree implements Serializable{

	private static final long serialVersionUID = 1L;
    public Node root = null;

    public void insert(Endpoint eventpoint) {
        insert(root, eventpoint);
    }

    private int height(Node node) {
        return node == null ? -1 : node.height;
    }

    
    public boolean isEmpty(){
    	return root == null ? true : false;
    }
    
    private void insert(Node node, Endpoint eventpoint) {
        if (root == null) {
            root = new Node(eventpoint, null);
            return;
        }

        if (eventpoint.compareTo(node.eventPoint)<0) {//The eventpoint is smaller than the nodes eventpoint
            goLeft(node, eventpoint);
        } else if (eventpoint.compareTo(node.eventPoint)>0) {
            goRight(node, eventpoint);
        }
        //Case: Eventpoint already exists in a node.
        else if(eventpoint.compareTo(node.eventPoint)==0){//Two uppers coinside
        	
        	if(eventpoint.isUpper() && node.eventPoint.isLower()){
        		//Prioritize upper by going right!
        		goRight(node, eventpoint);
        		
        	}
        	else if(eventpoint.isLower() && node.eventPoint.isUpper()){
        		//Prioritize upper by going left
        		 goLeft(node, eventpoint);
        	}        	
        }
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

         if (height(node.left) - height(node.right) == 2) { //left heavier
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
        if (target == null) return false;
        target = deleteNode(target);
        
        if(root!=null && target != root){
        	balanceTree(target.parent);
        }
        if(target == root){
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
                target.parent.right = null;
            }
        } else if (target.left == null ^ target.right == null && target != root) { //exact 1 child
            Node nonNullChild = target.left == null ? target.right : target.left; 
            if (isLeftChild(target)) {
                target.parent.setLeftChild(nonNullChild);
                nonNullChild.setParent(target.parent);
            } else {
                target.parent.setRightChild(nonNullChild);
                nonNullChild.setParent(target.parent);
            }

        } else if(target.left!=null && target.right!=null && target != root){//2 children
            Node immediatePredInOrder = immediatePredInOrder(target);
            target.eventPoint = immediatePredInOrder.eventPoint;
            target = deleteNode(immediatePredInOrder);
        } 
        
        else if(target == root && root.right == null && root.left != null){
        	root = target.left;
        	root.parent = null;
        	return root;
        }
        
        else if (target == root && root.right == null && root.left == null){
        	root = null;
        	return root;
        }

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
        reHeight(node);
    }

    public Node search(Endpoint eventpoint) {
        return binarySearch(root, eventpoint);
    }

    private Node binarySearch(Node node, Endpoint eventpoint) {
    	if (node == null){
    		System.out.println("Noden �r null.");
    		return null;
    	}
        if (eventpoint == node.eventPoint) {
        	System.out.println("Ja");
            return node;
        }

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
    
    
    public Endpoint findLargest(Node root){
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
}
