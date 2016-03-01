package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIDefaults.LazyValue;
import algorithm.*;

import algorithm.CollisionDetection;



public class Gui extends JPanel{
	
	private ArrayList<Endpoint> tempPolygon;
	private ArrayList<Endpoint> polygon1;
	private ArrayList<Endpoint> polygon2;
	private ArrayList<Endpoint> intersections;
	
	ArrayList<Edge> superTempPolygon;
	private ArrayList<Edge> superPolygon1;
	private ArrayList<Edge> superPolygon2;
	
	Endpoint startpoint1, startpoint2, latestpoint;
	Edge finishEdge;
	
	private int polygonFinished = 0;
	private int init = 0;
	private boolean calculated = false;
	
	//private ArrayList<Vertex> polygon1;
	//private ArrayList<Vertex> polygon2;
	
	private JButton finishPolygon;
	private JButton clear;
	private JButton collision;
	
	private static final long serialVersionUID = 1L;
		
	
		
	public Gui(){
		setBorder(BorderFactory.createLineBorder(Color.black));//Sets a black border around the panel
		finishPolygon = new JButton("Finish polygon");
		clear = new JButton("Clear all");
		collision = new JButton("Compute collision");
		collision.setEnabled(false);
		finishPolygon.setEnabled(false);
		tempPolygon = new ArrayList<Endpoint>();
		polygon1 = new ArrayList<Endpoint>();
		polygon2 = new ArrayList<Endpoint>();
		
		superTempPolygon = new ArrayList<Edge>();
		superPolygon1 = new ArrayList<Edge>();
		superPolygon2 = new ArrayList<Edge>();
		
		add(finishPolygon);
		add(clear);
		add(collision);
		
		
		//Create edgelist here?
		finishPolygon.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					finishPolygon.setEnabled(false);
					polygonFinished++;
					if(polygonFinished==1){
						
						/*
						for(Edge x : superTempPolygon){
							superPolygon1.add(x);
						}*/
						
						Edge lastEdge = new Edge(latestpoint, startpoint1);
						latestpoint.setNextSeg(lastEdge);
						startpoint1.setPrevSeg(lastEdge);
						
						/*
						finishEdge = new Edge(latestpoint, startpoint1);//Latest point is not updated before this correctly
						superPolygon1.add(finishEdge);*/
						
					}
					else if(polygonFinished==2){
						for(Edge x : superTempPolygon){
							superPolygon2.add(x);
						}
						
						
						/*
						finishEdge = new Edge(latestpoint, startpoint2);//Denna kant vägrar adderas. 
						superPolygon2.add(finishEdge);
						System.out.println("Printa alla startpunkter i polygon 2: ");
						for(Edge x : superPolygon2){
							System.out.println(x.getStart().getX()+", "+ x.getStart().getY());
						}*/
						
						Edge lastEdge = new Edge(latestpoint, startpoint2);
						latestpoint.setNextSeg(lastEdge);
						startpoint1.setPrevSeg(lastEdge);
						
						collision.setEnabled(true);
					}
					//superTempPolygon.clear();//Fullösning. Detta funkar bara för att polygonFinished bara är 1 en gång.
					init = 0;
					//superTempPolygon.add(finishEdge);//Funkar inte utan denna men det makes no sense.
					System.out.println("Printa alla punkter i temp(Borde inte finnas några utom en finish edge.");
					
					/*
					for(Edge x : superTempPolygon){
						System.out.println(x.getStart().getX()+", "+ x.getStart().getY());
					}*/
					repaint();
				
			}
		});
		
		clear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				/*superTempPolygon.clear();
				superPolygon1.clear();
				superPolygon2.clear();
				polygonFinished = 0;
				init = 0;
				repaint();*/
				startpoint1.setNextSeg(null);
				startpoint1.setPrevSeg(null);
				calculated = false;
				if(polygonFinished>0){
					startpoint2.setNextSeg(null);
					startpoint2.setPrevSeg(null);
				}
				
				repaint();
				init = 0;
				polygonFinished=0;
			}
		});
		
		collision.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CollisionDetection detect = new CollisionDetection(startpoint1, startpoint2);
				intersections = new ArrayList<Endpoint>();
				intersections = detect.getIntersections();
				if(intersections != null){
					for(Endpoint i : intersections){
						//Print the fackers!
						calculated = true;
					System.out.println(i.getX()+", " + i.getY());}
				}
				else{
					System.out.println("Intersection array is empty.");
				}
				//calculated = true;
				repaint();
			}
		});
		
		
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				//addPoint(e.getX(), e.getY());
				//addEdge(e.getX(), e.getY());
				addGeometry(e.getX(), e.getY());
			}
		});
	}
	
	private void addGeometry(int x, int y){
		if(polygonFinished<2){
			finishPolygon.setEnabled(true);
			Endpoint point = new Endpoint(x, y);
			
			if(init>0){
				System.out.println("Värde av läjtest pojnt."+latestpoint.getX()+", "+latestpoint.getY());
				Edge newEdge = new Edge(latestpoint, point);//KANSKE MÅSTE GÖRA EN NY ENDPOINT FÖR VARJE EDGE PGA ATT DE SKA SÄTTAS IN SOM OLIKA I KÖN, MEN DÅ FALLERAR START/END-BASERADE LOOPAR.
				latestpoint.setNextSeg(newEdge);
				point.setPrevSeg(newEdge);
				//superTempPolygon.add(newEdge);
				latestpoint = point;
				repaint();
				System.out.println("Jag har varit inne i ELSE i addEdge");
			}
			
			else if(init==0 && polygonFinished == 0){//Första punkten i en polygon.
				latestpoint = point;
				startpoint1 = point;//To draw
				init++;
				repaint();
				System.out.println("Jag har varit inne i en if i addEdge");
			}
			
			else if(init==0 && polygonFinished == 1){//Första punkten i polygon 2.
				latestpoint = point;
				startpoint2 = point;//To draw. 
				init++;
				repaint();
				System.out.println("Jag har varit inne i en if i addEdge");
			}
			
		}
	}
	
	private void addEdge(int x, int y){
		if(polygonFinished<2){
			finishPolygon.setEnabled(true);
			Endpoint point = new Endpoint(x, y);//Sätt belonging redan här?
			
			if(init>0){
				System.out.println("Värde av läjtest pojnt."+latestpoint.getX()+", "+latestpoint.getY());
				Edge newEdge = new Edge(latestpoint, point);//Har satt fel håll tror jag. Ändrar.
				superTempPolygon.add(newEdge);
				latestpoint = point;
				repaint();
				System.out.println("Jag har varit inne i ELSE i addEdge");
			}
			
			else if(init==0 && polygonFinished == 0){//Första punkten i en polygon.
				latestpoint = point;
				startpoint1 = point;//To draw
				init++;
				repaint();
				System.out.println("Jag har varit inne i en if i addEdge");
			}
			
			//Fullösning för att få olika startpoints. Borde egentligen lösas med att alla segments
			//har start och slutpunkt istället för upper och lower som hör till event points.
			else if(init==0 && polygonFinished == 1){//Första punkten i polygon 2.
				latestpoint = point;
				startpoint2 = point;//To draw. 
				init++;
				repaint();
				System.out.println("Jag har varit inne i en if i addEdge");
			}
			
			else{
				
			}		
		}
		
		
	}
	
	private void addPoint(int x, int y) {
		if(polygonFinished<2){//Begränsar till två polygoner.
			finishPolygon.setEnabled(true);//Kallas onödigt många gånger.
			Endpoint point = new Endpoint(x, y);
			tempPolygon.add(point);
			repaint();
		}	
	}
	
	//Constructs an edge with upper and lower endpoints
	//Bättre att skapa med start -och slutpunkt. Sedan initieras upper och lower i CollisionDetection
	public Edge constructEdge(Endpoint point, Endpoint latestPoint){
		Edge edge;
		if(point.getY()>latestPoint.getY() || (point.getY()==latestPoint.getY())&&(point.getX()<=latestPoint.getX())){//Kollar om y är större eller x mindre eller LIKA MED.
			edge = new Edge(point, latestPoint);
		}
		else{
			edge = new Edge(latestPoint, point);
		}
		return edge;
	}
	
	public Dimension getPreferredSize() {
		return new Dimension(400, 400);
	}
		
		
		
	//Here we do custom painting
	public void paintComponent(Graphics g) {//Graphics object provides methods for drawing 2D shapes. and obtain info about environment		
		super.paintComponent(g);
		//Loopa igenom alla polygoner och rita upp dem.
		
		/*if(!tempPolygon.isEmpty()){
			for(int i = 0;i<tempPolygon.size();i++){
				tempPolygon.get(i).paintPoint(g);
				if(i>0){
					g.drawLine(tempPolygon.get(i).getX(), tempPolygon.get(i).getY(), tempPolygon.get(i-1).getX(), tempPolygon.get(i-1).getY());
				}
			}*/
			
			/*
			if(polygonFinished){
				g.drawLine(tempPolygon.get(0).getX(), tempPolygon.get(0).getY(), tempPolygon.get(tempPolygon.size()-1).getX(), tempPolygon.get(tempPolygon.size()-1).getY());
				tempPolygon.clear();
				polygonFinished = false;
			}*/
		
		/*
		if(!polygon1.isEmpty()){
			g.setColor(Color.green);
			g.drawLine(polygon1.get(0).getX(), polygon1.get(0).getY(), polygon1.get(polygon1.size()-1).getX(), polygon1.get(polygon1.size()-1).getY());
			for(int i = 1;i<polygon1.size();i++){
				g.drawLine(polygon1.get(i).getX(), polygon1.get(i).getY(), polygon1.get(i-1).getX(), polygon1.get(i-1).getY());
			}
		}
		
		if(!polygon2.isEmpty()){
			g.setColor(Color.blue);
			g.drawLine(polygon2.get(0).getX(), polygon2.get(0).getY(), polygon2.get(polygon2.size()-1).getX(), polygon2.get(polygon2.size()-1).getY());
			for(int i = 1;i<polygon2.size();i++){
				g.drawLine(polygon2.get(i).getX(), polygon2.get(i).getY(), polygon2.get(i-1).getX(), polygon2.get(i-1).getY());
			}
		}*/
		
		/*
		if(init == 0){
			g.setColor(Color.BLUE);
			g.fillRect(latestpoint.getX(), latestpoint.getY(), 3, 3);
			init++;
		}*/
		
		/*
		if(!superTempPolygon.isEmpty()){
			for(int i = 0;i<superTempPolygon.size();i++){
				superTempPolygon.get(i).paintEdge(g);
				
			}
		}
		
		if(!superPolygon1.isEmpty()){
			g.setColor(Color.green);
			for(int i = 0;i<superPolygon1.size();i++){
				superPolygon1.get(i).getUpper().setBelonging(Endpoint.POLYGON1);
				superPolygon1.get(i).getLower().setBelonging(Endpoint.POLYGON1);
				superPolygon1.get(i).paintEdge(g);
			}
			//superPolygon1.get(superPolygon1.size()-1).end.setBelonging(Endpoint.POLYGON1);
		}
		
		if(!superPolygon2.isEmpty()){
			g.setColor(Color.red);
			for(int i = 0;i<superPolygon2.size();i++){
				superPolygon2.get(i).getUpper().setBelonging(Endpoint.POLYGON2);//Var sätts tillhörigheten till index 0?
				superPolygon2.get(i).getLower().setBelonging(Endpoint.POLYGON2);
				superPolygon2.get(i).paintEdge(g);
			}
			//superPolygon2.get(superPolygon2.size()-1).end.setBelonging(Endpoint.POLYGON2);
		}
		
		if(calculated){
			g.setColor(Color.BLUE);
			for(Endpoint e : intersections){
				g.fillRect(e.getX(), e.getY(), 4, 4);
			}
		}*/
		
		Endpoint current = startpoint1;
		//current.setBelonging(Endpoint.POLYGON1);
		if(startpoint1!=null){
			current.setBelonging(Endpoint.POLYGON1);
		}
		while(current!=null && current.getNextSeg()!=null && current.getNextSeg().getEnd()!=startpoint1 && current.getNextSeg().getEnd()!=null){
			g.setColor(Color.green);
			g.drawLine(current.getX(), current.getY(), current.getNextSeg().getEnd().getX(), current.getNextSeg().getEnd().getY());
			System.out.println("This point is startpoint1: ");
			System.out.println(current==startpoint1);
			System.out.println("Koordinater: "+current.getX()+", "+current.getY());
			
			current = current.getNextSeg().getEnd();//Knepigt?
			current.setBelonging(Endpoint.POLYGON1);
			
		}
		
		//current.setBelonging(Endpoint.POLYGON1);
		if(current!=null && current.getNextSeg()!=null && current.getNextSeg().getEnd()!=null){
			g.drawLine(current.getX(), current.getY(), current.getNextSeg().getEnd().getX(), current.getNextSeg().getEnd().getY());
		}
		
		
		current = startpoint2;
		//current.setBelonging(Endpoint.POLYGON2);
		if(startpoint2!=null){
			current.setBelonging(Endpoint.POLYGON2);
		}
		while(current!=null && current.getNextSeg()!=null && current.getNextSeg().getEnd()!=startpoint2 && current.getNextSeg().getEnd()!=null){
			g.setColor(Color.red);
			g.drawLine(current.getX(), current.getY(), current.getNextSeg().getEnd().getX(), current.getNextSeg().getEnd().getY());
			current.setBelonging(Endpoint.POLYGON2);
			current = current.getNextSeg().getEnd();//Knepigt?
			current.setBelonging(Endpoint.POLYGON2);
		}
		//current.setBelonging(Endpoint.POLYGON2);
		if(current!=null && current.getNextSeg()!=null && current.getNextSeg().getEnd()!=null){
			g.drawLine(current.getX(), current.getY(), current.getNextSeg().getEnd().getX(), current.getNextSeg().getEnd().getY());
		}
		
		if(calculated){
			if(intersections!=null){
				for(Endpoint i : intersections){
					g.setColor(Color.BLACK);
					g.fillRect(i.getX(), i.getY(), 10, 10);
					
				}
			}
		}
		
	}
}



