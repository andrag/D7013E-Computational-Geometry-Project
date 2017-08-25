package main;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import algorithm.*;
import datastructures.*;

import org.junit.Assert;


public class Main {
	
	private static boolean loadPolygons = false;

	public static void main(String[] args) {
		AVLTree eventQueueTest = new AVLTree();
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createGUI();
			}
		});
	}

	private static void createGUI(){
		JFrame frame = new JFrame("Computational geometry project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Painting panel
		final Gui paintArea = new Gui(); 
		frame.add(paintArea);

		//Controls
		JPanel controlPanel = new JPanel();
		frame.add(controlPanel, BorderLayout.SOUTH);
		
		frame.pack();
		frame.setVisible(true);
	}
}



