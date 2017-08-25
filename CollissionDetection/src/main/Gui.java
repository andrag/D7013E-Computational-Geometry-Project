package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import algorithm.*;



public class Gui extends JPanel{
	
	private String loadFile = "";// Example: Polygons_2017_06_18_14-57-58.txt More examples exist in the src catalog.
	
	private ArrayList<Endpoint> intersections;
	
	private Endpoint startpoint1, startpoint2, latestpoint;
	
	private int polygonsFinished = 0;
	private boolean firstPoint = true;
	private boolean intersectionsCalculated = false;
	
	private JButton finishButton;
	private JButton clearButton;
	private JButton runButton;
	
	private static final long serialVersionUID = 1L;
	
		
	
		
	public Gui(){
		setBorder(BorderFactory.createLineBorder(Color.black));
		finishButton = new JButton("Finish polygon");
		clearButton = new JButton("Clear all");
		runButton = new JButton("Compute collision");
		runButton.setEnabled(false);
		finishButton.setEnabled(false);
		
		add(finishButton);
		add(clearButton);
		add(runButton);
		
		if(!loadFile.isEmpty())
		{
			runLoadedData();
		}
		
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				addGeometry(e.getX(), e.getY());
			}
		});
		
		
		finishButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				finishGeometry();
			}
		});
		
		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearPanel();
			}
		});
		
		runButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				runAlgorithm();
			}
		});	
	}
	
	private void runLoadedData()
	{	
		SaveAndLoad.loadPolygonsFromTxt(loadFile);
		ArrayList<Integer> polygon1List = SaveAndLoad.polygon1;
		ArrayList<Integer> polygon2List = SaveAndLoad.polygon2;
		for(int i = 0; i < polygon1List.size();i = i+2)
		{
			addGeometry(polygon1List.get(i), polygon1List.get(i+1));
		}
		finishGeometry();
		
		for(int i = 0; i < polygon2List.size();i = i+2)
		{
			addGeometry(polygon2List.get(i), polygon2List.get(i+1));
		}
		finishGeometry();
	}
	
	//Draw lines when the mouse is clicked
	private void addGeometry(int x, int y){
		if(polygonsFinished<2){//We can draw maximum 2 polygons
			finishButton.setEnabled(true);
			Endpoint point = new Endpoint(x, y);
			
			//If this is not the first line of the polygon
			if(!firstPoint){
				int id = CollisionDetection.incrementAndGetEdgeID();
				Edge newEdge = new Edge(latestpoint, point, id);
				latestpoint.setNextSeg(newEdge);
				point.setPrevSeg(newEdge);	
			}
			
			//If this is the first point of the first polygon
			else if(firstPoint && polygonsFinished == 0){
				startpoint1 = point;//To draw
				firstPoint = false;
			}
			
			//If this is the first point of the second polygon
			else if(firstPoint && polygonsFinished == 1){
				startpoint2 = point;//To draw. 
				firstPoint = false;
			}
			latestpoint = point;
			repaint();
			
			if(polygonsFinished == 0){
				point.setBelonging(Endpoint.POLYGON1);
			}
			else if(polygonsFinished == 1){
				point.setBelonging(Endpoint.POLYGON2);
			}
		}
	}
	
	//Finish drawing a polygon
	private void finishGeometry(){
		finishButton.setEnabled(false);
		polygonsFinished++;
		int id = CollisionDetection.incrementAndGetEdgeID();
		if(polygonsFinished==1){
			Edge lastEdge = new Edge(latestpoint, startpoint1, id);
			latestpoint.setNextSeg(lastEdge);
			startpoint1.setPrevSeg(lastEdge);	
		}
		
		else if(polygonsFinished==2){
			Edge lastEdge = new Edge(latestpoint, startpoint2, id);
			latestpoint.setNextSeg(lastEdge);
			startpoint2.setPrevSeg(lastEdge);
			
			runButton.setEnabled(true);
		}
		firstPoint = true;
		repaint();
	}
	
	
	//Clear the drawing area
	private void clearPanel(){
		startpoint1.setNextSeg(null);
		startpoint1.setPrevSeg(null);
		intersectionsCalculated = false;
		if(polygonsFinished>0){
			startpoint2.setNextSeg(null);
			startpoint2.setPrevSeg(null);
		}
		repaint();
		firstPoint = true;
		polygonsFinished=0;
	}
	
	private void runAlgorithm(){
		if(loadFile.isEmpty())
		{
			SaveAndLoad.savePolygonsAsText(startpoint1, startpoint2);
		}
		
		CollisionDetection detect = new CollisionDetection(startpoint1, startpoint2);
		intersections = new ArrayList<Endpoint>();
		intersections = detect.getIntersections();
		if(!intersections.isEmpty()){
			intersectionsCalculated = true;
		}
		else{
			System.out.println("Intersection array is empty.");
		}
		repaint();
	}
	
	
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}
		
		
		

	public void paintComponent(Graphics g) {	
		super.paintComponent(g);
		
		drawPolygon1(g, startpoint1);
		drawPolygon2(g, startpoint2);
		
		if(intersectionsCalculated){
			drawIntersections(g);
		}
	}
	
	
	
	private void drawPolygon1(Graphics g, Endpoint current){
		
		while(current!=null && current.getNextSeg()!=null && current.getNextSeg().getEnd()!=startpoint1 && current.getNextSeg().getEnd()!=null){
			g.setColor(Color.green);
			g.drawLine(current.getX(), current.getY(), current.getNextSeg().getEnd().getX(), current.getNextSeg().getEnd().getY());
			current = current.getNextSeg().getEnd();
		}
		if(current!=null && current.getNextSeg()!=null && current.getNextSeg().getEnd()!=null){
			g.drawLine(current.getX(), current.getY(), current.getNextSeg().getEnd().getX(), current.getNextSeg().getEnd().getY());
		}
	}
	
	
	
	
	private void drawPolygon2(Graphics g, Endpoint current){
		while(current!=null && current.getNextSeg()!=null && current.getNextSeg().getEnd()!=startpoint2 && current.getNextSeg().getEnd()!=null){
			g.setColor(Color.red);
			g.drawLine(current.getX(), current.getY(), current.getNextSeg().getEnd().getX(), current.getNextSeg().getEnd().getY());
			current = current.getNextSeg().getEnd();
		}
		
		if(current!=null && current.getNextSeg()!=null && current.getNextSeg().getEnd()!=null){
			g.drawLine(current.getX(), current.getY(), current.getNextSeg().getEnd().getX(), current.getNextSeg().getEnd().getY());
		}
	}
	
	
	
	
	private void drawIntersections(Graphics g){
		if(intersections!=null){
			for(Endpoint i : intersections){
				g.setColor(Color.BLACK);
				g.fillRect(i.getX(), i.getY(), 10, 10);
				
			}
		}
	}
}



