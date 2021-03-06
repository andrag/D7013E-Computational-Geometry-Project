package main;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import algorithm.*;
import datastructures.*;


public class Main {
	
	public static void main(String[] args){
		StatusTreeSet.statusTreeSet();
	}

	/*public static void main(String[] args) {
		//Construct the GUI on the AWT Event Dispatch Thread (EDT)
		//testa insert och search i StatusTree och AVLTree
		AVLTree eventQueueTest = new AVLTree();
		StatusTree status = new StatusTree();

		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createGUI();
			}
		});
		
		//========================================Fel 7 - Nullpointer - Mer avancerad polygon===========================================================
		//2015-05-17
		
		//Polygon A
		//Side 1
		/*Endpoint a1 = new Endpoint(89, 400-185);
		Endpoint a2 = new Endpoint(41, 400-287);
		Edge a1a2 = new Edge(a1, a2);
		a1.setNextSeg(a1a2);
		a2.setPrevSeg(a1a2);
		a1.setBelonging(Endpoint.POLYGON1);
		a2.setBelonging(Endpoint.POLYGON1);

		//Side 2
		Endpoint a3 = new Endpoint(123, 400-254);
		Edge a2a3 = new Edge(a2, a3);
		a2.setNextSeg(a2a3);
		a3.setPrevSeg(a2a3);
		a3.setBelonging(Endpoint.POLYGON1);

		//Side 3
		Endpoint a4 = new Endpoint(98, 400-319);
		Edge a3a4 = new Edge(a3, a4);
		a3.setNextSeg(a3a4);
		a4.setPrevSeg(a3a4);
		a4.setBelonging(Endpoint.POLYGON1);

		//Side 4
		Endpoint a5 = new Endpoint(198, 400-273);
		Edge a4a5 = new Edge(a4, a5);
		a4.setNextSeg(a4a5);
		a5.setPrevSeg(a4a5);
		a5.setBelonging(Endpoint.POLYGON1);
		
		//Side 5
		Edge a5a1 = new Edge(a5, a1);
		a5.setNextSeg(a5a1);
		a1.setPrevSeg(a5a1);
		
		
		//Polygon B
		Endpoint b1 = new Endpoint(29, 400-247);
		Endpoint b2 = new Endpoint(157, 400-341);
		Edge b1b2 = new Edge(b1, b2);
		b1.setNextSeg(b1b2);
		b2.setPrevSeg(b1b2);
		b1.setBelonging(Endpoint.POLYGON2);
		b2.setBelonging(Endpoint.POLYGON2);

		//Side 2
		Endpoint b3 = new Endpoint(46, 400-222);
		Edge b2b3 = new Edge(b2, b3);
		b2.setNextSeg(b2b3);
		b3.setPrevSeg(b2b3);
		b3.setBelonging(Endpoint.POLYGON2);

		//Side 3
		Edge b3b1 = new Edge(b3, b1);
		b3.setNextSeg(b3b1);
		b1.setPrevSeg(b3b1);
		
		
		int count = 0;
		CollisionDetection detect = new CollisionDetection(a1, b1);
		ArrayList<Endpoint>intersections = new ArrayList<Endpoint>();
		intersections = detect.getIntersections();
		if(intersections != null){
			for(Endpoint i : intersections){
				System.out.println(i.getX()+", " + i.getY());
				count++;
				System.out.println(count);	
			}
			
		}
		else{
			System.out.println("Intersection array is empty.");
		}

		*/
		
		/*=========================================Fel 4 - Nullpointer ==========================================================
		 * 2015-05-14
		 */
		/*Endpoint a1 = new Endpoint(105, 400-268);
		Endpoint a2 = new Endpoint(300, 400-277);
		Edge a1a2 = new Edge(a1, a2);
		a1.setNextSeg(a1a2);
		a2.setPrevSeg(a1a2);
		a1.setBelonging(Endpoint.POLYGON1);
		a2.setBelonging(Endpoint.POLYGON1);

		//Side 2
		Endpoint a3 = new Endpoint(293, 400-102);
		Edge a2a3 = new Edge(a2, a3);
		a2.setNextSeg(a2a3);
		a3.setPrevSeg(a2a3);
		a3.setBelonging(Endpoint.POLYGON1);

		//Side 3
		Endpoint a4 = new Endpoint(102, 400-102);
		Edge a3a4 = new Edge(a3, a4);
		a3.setNextSeg(a3a4);
		a4.setPrevSeg(a3a4);
		a4.setBelonging(Endpoint.POLYGON1);

		//Side 4
		Edge a4a1 = new Edge(a4, a1);
		a4.setNextSeg(a4a1);
		a1.setPrevSeg(a4a1);

		//Polygon B

		//Side 1
		Endpoint b1 = new Endpoint(151,400-208);
		Endpoint b2 = new Endpoint(231, 400-209);
		Edge b1b2 = new Edge(b1, b2);
		b1.setNextSeg(b1b2);
		b2.setPrevSeg(b1b2);
		b1.setBelonging(Endpoint.POLYGON2);
		b2.setBelonging(Endpoint.POLYGON2);

		//Side 2
		Endpoint b3 = new Endpoint(236, 400-150);
		Edge b2b3 = new Edge(b2, b3);
		b2.setNextSeg(b2b3);
		b3.setPrevSeg(b2b3);
		b3.setBelonging(Endpoint.POLYGON2);

		//Side 3
		Endpoint b4 = new Endpoint(152, 400-150);
		Edge b3b4 = new Edge(b3, b4);
		b3.setNextSeg(b3b4);
		b4.setPrevSeg(b3b4);
		b4.setBelonging(Endpoint.POLYGON2);

		//Side 4
		Edge b4b1 = new Edge(b4, b1);
		b4.setNextSeg(b4b1);
		b1.setPrevSeg(b4b1);
		
		int count = 0;
		CollisionDetection detect = new CollisionDetection(a1, b1);
		ArrayList<Endpoint>intersections = new ArrayList<Endpoint>();
		intersections = detect.getIntersections();
		if(intersections != null){
			for(Endpoint i : intersections){
				System.out.println(i.getX()+", " + i.getY());
				count++;
				System.out.println(count);	
			}
			
		}
		else{
			System.out.println("Intersection array is empty.");
		}
		*/
		
		
		
		/*=========================================CollisionDetection fel p� delete -> f�r m�nga korsningspunkter==
		 * 
		 */
		/*Endpoint a1 = new Endpoint(169, 400-315);
		Endpoint a2 = new Endpoint(217, 400-143);
		Edge a1a2 = new Edge(a1, a2);
		a1.setNextSeg(a1a2);
		a2.setPrevSeg(a1a2);
		a1.setBelonging(Endpoint.POLYGON1);
		a2.setBelonging(Endpoint.POLYGON1);

		//Side 2
		Endpoint a3 = new Endpoint(50, 400-101);
		Edge a2a3 = new Edge(a2, a3);
		a2.setNextSeg(a2a3);
		a3.setPrevSeg(a2a3);
		a3.setBelonging(Endpoint.POLYGON1);

		//Side 3
		Endpoint a4 = new Endpoint(63, 400-281);
		Edge a3a4 = new Edge(a3, a4);
		a3.setNextSeg(a3a4);
		a4.setPrevSeg(a3a4);
		a4.setBelonging(Endpoint.POLYGON1);

		//Side 4
		Edge a4a1 = new Edge(a4, a1);
		a4.setNextSeg(a4a1);
		a1.setPrevSeg(a4a1);

		//Polygon B

		//Side 1
		Endpoint b1 = new Endpoint(156,400-299);
		Endpoint b2 = new Endpoint(289, 400-299);
		Edge b1b2 = new Edge(b1, b2);
		b1.setNextSeg(b1b2);
		b2.setPrevSeg(b1b2);
		b1.setBelonging(Endpoint.POLYGON2);
		b2.setBelonging(Endpoint.POLYGON2);

		//Side 2
		Endpoint b3 = new Endpoint(307, 400-110);
		Edge b2b3 = new Edge(b2, b3);
		b2.setNextSeg(b2b3);
		b3.setPrevSeg(b2b3);
		b3.setBelonging(Endpoint.POLYGON2);

		//Side 3
		Endpoint b4 = new Endpoint(128, 400-94);
		Edge b3b4 = new Edge(b3, b4);
		b3.setNextSeg(b3b4);
		b4.setPrevSeg(b3b4);
		b4.setBelonging(Endpoint.POLYGON2);

		//Side 4
		Edge b4b1 = new Edge(b4, b1);
		b4.setNextSeg(b4b1);
		b1.setPrevSeg(b4b1);
		
		int count = 0;
		CollisionDetection detect = new CollisionDetection(a1, b1);
		ArrayList<Endpoint>intersections = new ArrayList<Endpoint>();
		intersections = detect.getIntersections();
		if(intersections != null){
			for(Endpoint i : intersections){
				System.out.println(i.getX()+", " + i.getY());
				count++;
				System.out.println(count);	
			}
			
		}
		else{
			System.out.println("Intersection array is empty.");
		}*/

		

		/*=========================================CollisionDetection fel p� belonging f�r intersections===========
		 * 
		 */

		//Side 1
		/*
		Endpoint a1 = new Endpoint(196, 400-327);
		Endpoint a2 = new Endpoint(222, 400-151);
		Edge a1a2 = new Edge(a1, a2);
		a1.setNextSeg(a1a2);
		a2.setPrevSeg(a1a2);
		a1.setBelonging(Endpoint.POLYGON1);
		a2.setBelonging(Endpoint.POLYGON1);

		//Side 2
		Endpoint a3 = new Endpoint(51, 400-134);
		Edge a2a3 = new Edge(a2, a3);
		a2.setNextSeg(a2a3);
		a3.setPrevSeg(a2a3);
		a3.setBelonging(Endpoint.POLYGON1);

		//Side 3
		Endpoint a4 = new Endpoint(55, 400-303);
		Edge a3a4 = new Edge(a3, a4);
		a3.setNextSeg(a3a4);
		a4.setPrevSeg(a3a4);
		a4.setBelonging(Endpoint.POLYGON1);

		//Side 4
		Edge a4a1 = new Edge(a4, a1);
		a4.setNextSeg(a4a1);
		a1.setPrevSeg(a4a1);

		//Polygon B

		//Side 1
		Endpoint b1 = new Endpoint(184,400-314);
		Endpoint b2 = new Endpoint(301, 400-310);
		Edge b1b2 = new Edge(b1, b2);
		b1.setNextSeg(b1b2);
		b2.setPrevSeg(b1b2);
		b1.setBelonging(Endpoint.POLYGON2);
		b2.setBelonging(Endpoint.POLYGON2);

		//Side 2
		Endpoint b3 = new Endpoint(309, 400-87);
		Edge b2b3 = new Edge(b2, b3);
		b2.setNextSeg(b2b3);
		b3.setPrevSeg(b2b3);
		b3.setBelonging(Endpoint.POLYGON2);

		//Side 3
		Endpoint b4 = new Endpoint(162, 400-83);
		Edge b3b4 = new Edge(b3, b4);
		b3.setNextSeg(b3b4);
		b4.setPrevSeg(b3b4);
		b4.setBelonging(Endpoint.POLYGON2);

		//Side 4
		Edge b4b1 = new Edge(b4, b1);
		b4.setNextSeg(b4b1);
		b1.setPrevSeg(b4b1);

		int count = 0;
		CollisionDetection detect = new CollisionDetection(a1, b1);
		ArrayList<Endpoint>intersections = new ArrayList<Endpoint>();
		intersections = detect.getIntersections();
		if(intersections != null){
			for(Endpoint i : intersections){
				System.out.println(i.getX()+", " + i.getY());
				count++;
				System.out.println(count);	
			}
			
		}
		else{
			System.out.println("Intersection array is empty.");
		}

		/*=========================================CollisionDetection: yet more probs===============================
		 * Datum: 10/3 kl 20:04
		 */

		//Side 1
		/*
		Endpoint a1 = new Endpoint(184, 400-321);
		Endpoint a2 = new Endpoint(268, 400-312);
		Edge a1a2 = new Edge(a1, a2);
		a1.setNextSeg(a1a2);
		a2.setPrevSeg(a1a2);
		a1.setBelonging(Endpoint.POLYGON1);
		a2.setBelonging(Endpoint.POLYGON1);

		//Side 2
		Endpoint a3 = new Endpoint(267, 400-238);
		Edge a2a3 = new Edge(a2, a3);
		a2.setNextSeg(a2a3);
		a3.setPrevSeg(a2a3);
		a3.setBelonging(Endpoint.POLYGON1);

		//Side 3
		Endpoint a4 = new Endpoint(186, 400-235);
		Edge a3a4 = new Edge(a3, a4);
		a3.setNextSeg(a3a4);
		a4.setPrevSeg(a3a4);
		a4.setBelonging(Endpoint.POLYGON1);

		//Side 4
		Edge a4a1 = new Edge(a4, a1);
		a4.setNextSeg(a4a1);
		a1.setPrevSeg(a4a1);

		//Polygon B

		//Side 1
		Endpoint b1 = new Endpoint(75,400-275);
		Endpoint b2 = new Endpoint(219, 400-271);
		Edge b1b2 = new Edge(b1, b2);
		b1.setNextSeg(b1b2);
		b2.setPrevSeg(b1b2);
		b1.setBelonging(Endpoint.POLYGON2);
		b2.setBelonging(Endpoint.POLYGON2);

		//Side 2
		Endpoint b3 = new Endpoint(222, 400-108);
		Edge b2b3 = new Edge(b2, b3);
		b2.setNextSeg(b2b3);
		b3.setPrevSeg(b2b3);
		b3.setBelonging(Endpoint.POLYGON2);

		//Side 3
		Endpoint b4 = new Endpoint(94, 400-120);
		Edge b3b4 = new Edge(b3, b4);
		b3.setNextSeg(b3b4);
		b4.setPrevSeg(b3b4);
		b4.setBelonging(Endpoint.POLYGON2);

		//Side 4
		Edge b4b1 = new Edge(b4, b1);
		b4.setNextSeg(b4b1);
		b1.setPrevSeg(b4b1);

		CollisionDetection detect = new CollisionDetection(a1, b1);
		ArrayList<Endpoint>intersections = new ArrayList<Endpoint>();
		intersections = detect.getIntersections();
		if(intersections != null){
			for(Endpoint i : intersections){
				System.out.println(i.getX()+", " + i.getY());}
		}
		else{
			System.out.println("Intersection array is empty.");
		}*/

		/* ========================================CollisionDetection: delete root===================================
		 * Datum: 10/3 kl 13:25
		 * L�sning: Fixade findLargest s� att den aldrig returnerar null om den inte m�ste. Lade till en else return null ist�llet f�r
		 * rekursiv returnering av null.
		 */

		//Side 1
		/*
		Endpoint a1 = new Endpoint(149, 400-313);
		Endpoint a2 = new Endpoint(179, 400-307);
		Edge a1a2 = new Edge(a1, a2);
		a1.setNextSeg(a1a2);
		a2.setPrevSeg(a1a2);
		a1.setBelonging(Endpoint.POLYGON1);
		a2.setBelonging(Endpoint.POLYGON1);

		//Side 2
		Endpoint a3 = new Endpoint(200, 400-140);
		Edge a2a3 = new Edge(a2, a3);
		a2.setNextSeg(a2a3);
		a3.setPrevSeg(a2a3);
		a3.setBelonging(Endpoint.POLYGON1);

		//Side 3
		Endpoint a4 = new Endpoint(159, 400-147);
		Edge a3a4 = new Edge(a3, a4);
		a3.setNextSeg(a3a4);
		a4.setPrevSeg(a3a4);
		a4.setBelonging(Endpoint.POLYGON1);

		//Side 4
		Edge a4a1 = new Edge(a4, a1);
		a4.setNextSeg(a4a1);
		a1.setPrevSeg(a4a1);

		//Polygon B

		//Side 1
		Endpoint b1 = new Endpoint(84,400-287);
		Endpoint b2 = new Endpoint(276, 400-294);
		Edge b1b2 = new Edge(b1, b2);
		b1.setNextSeg(b1b2);
		b2.setPrevSeg(b1b2);
		b1.setBelonging(Endpoint.POLYGON2);
		b2.setBelonging(Endpoint.POLYGON2);

		//Side 2
		Endpoint b3 = new Endpoint(296, 400-151);
		Edge b2b3 = new Edge(b2, b3);
		b2.setNextSeg(b2b3);
		b3.setPrevSeg(b2b3);
		b3.setBelonging(Endpoint.POLYGON2);

		//Side 3
		Endpoint b4 = new Endpoint(36, 400-179);
		Edge b3b4 = new Edge(b3, b4);
		b3.setNextSeg(b3b4);
		b4.setPrevSeg(b3b4);
		b4.setBelonging(Endpoint.POLYGON2);

		//Side 4
		Edge b4b1 = new Edge(b4, b1);
		b4.setNextSeg(b4b1);
		b1.setPrevSeg(b4b1);

		CollisionDetection detect = new CollisionDetection(a1, b1);
		ArrayList<Endpoint>intersections = new ArrayList<Endpoint>();
		intersections = detect.getIntersections();
		if(intersections != null){
			for(Endpoint i : intersections){
			System.out.println(i.getX()+", " + i.getY());}
		}
		else{
			System.out.println("Intersection array is empty.");
		}
		 */

		/* =======================================CollisionDetection fixed input test 4==============================
		 * Datum: 9/3 kl 16:33
		 */

		//Side A
		/*Endpoint a1 = new Endpoint(172, 400-315);
		Endpoint a2 = new Endpoint(350, 400-317);
		Edge a1a2 = new Edge(a1, a2);
		a1.setNextSeg(a1a2);
		a2.setPrevSeg(a1a2);
		a1.setBelonging(Endpoint.POLYGON1);
		a2.setBelonging(Endpoint.POLYGON1);

		//Side B
		Endpoint a3 = new Endpoint(314, 400-172);
		Edge a2a3 = new Edge(a2, a3);
		a2.setNextSeg(a2a3);
		a3.setPrevSeg(a2a3);
		a3.setBelonging(Endpoint.POLYGON1);

		//Side C
		Edge a3a1 = new Edge(a3, a1);
		a3.setNextSeg(a3a1);
		a1.setPrevSeg(a3a1);


		//Side D
		Endpoint b1 = new Endpoint(73, 400-270);
		Endpoint b2 = new Endpoint(299, 400-283);
		Edge b1b2 = new Edge(b1, b2);
		b1.setNextSeg(b1b2);
		b2.setPrevSeg(b1b2);
		b1.setBelonging(Endpoint.POLYGON2);
		b2.setBelonging(Endpoint.POLYGON2);

		//Side E
		Endpoint b3 = new Endpoint(143, 400-93);
		Edge b2b3 = new Edge(b2, b3);
		b2.setNextSeg(b2b3);
		b3.setPrevSeg(b2b3);
		b3.setBelonging(Endpoint.POLYGON2);

		//Side F
		Endpoint b4 = new Endpoint(32, 400-182);
		Edge b3b4 = new Edge(b3, b4);
		b3.setNextSeg(b3b4);
		b4.setPrevSeg(b3b4);
		b4.setBelonging(Endpoint.POLYGON2);

		//Side G
		Edge b4b1 = new Edge(b4, b1);
		b4.setNextSeg(b4b1);
		b1.setPrevSeg(b4b1);

		CollisionDetection detect = new CollisionDetection(a1, b1);
		ArrayList<Endpoint>intersections = new ArrayList<Endpoint>();
		intersections = detect.getIntersections();
		if(intersections != null){
			for(Endpoint i : intersections){
			System.out.println(i.getX()+", " + i.getY());}
		}
		else{
			System.out.println("Intersection array is empty.");
		}

		/* ======================================Collision Detection med tv� trianglar som bildar stj�rna============
		 * Datum 9/3
		 * Exemplet funkar kl 16:18 9/3
		 * Felet var: F�rs�kte hitta element i crossings isUpperTo-array n�r det skulle vara p:s array. Rad 769 i CollisionDetection.
		 */

		//Side A
		/*
		Endpoint a1 = new Endpoint(189, 400-285);
		Endpoint a2 = new Endpoint(299, 400-151);
		Edge a1a2 = new Edge(a1, a2);
		a1.setNextSeg(a1a2);
		a2.setPrevSeg(a1a2);
		a1.setBelonging(Endpoint.POLYGON1);
		a2.setBelonging(Endpoint.POLYGON1);

		//Side B
		Endpoint a3 = new Endpoint(86, 400-195);
		Edge a2a3 = new Edge(a2, a3);
		a2.setNextSeg(a2a3);
		a3.setPrevSeg(a2a3);
		a3.setBelonging(Endpoint.POLYGON1);

		//Side C
		Edge a3a1 = new Edge(a3, a1);
		a3.setNextSeg(a3a1);
		a1.setPrevSeg(a3a1);

		//Next polygon

		//Side D
		Endpoint b1 = new Endpoint(84, 400-249);
		Endpoint b2  = new Endpoint(303, 400-250);
		Edge b1b2 = new Edge(b1, b2);
		b1.setNextSeg(b1b2);
		b2.setPrevSeg(b1b2);
		b1.setBelonging(Endpoint.POLYGON2);
		b2.setBelonging(Endpoint.POLYGON2);

		//Side E
		Endpoint b3 = new Endpoint(162, 400-109);
		Edge b2b3 = new Edge(b2, b3);
		b2.setNextSeg(b2b3);
		b3.setPrevSeg(b2b3);
		b3.setBelonging(Endpoint.POLYGON2);

		//Side F
		Edge b3b1 = new Edge(b3, b1);
		b3.setNextSeg(b3b1);
		b1.setPrevSeg(b3b1);


		CollisionDetection detect = new CollisionDetection(a1, b1);
		ArrayList<Endpoint>intersections = new ArrayList<Endpoint>();
		intersections = detect.getIntersections();
		if(intersections != null){
			for(Endpoint i : intersections){
			System.out.println(i.getX()+", " + i.getY());}
		}
		else{
			System.out.println("Intersection array is empty.");
		}


		 */

		//======================================Testa CollisionDetection med ny fixerad input========================

		/* Datum: 9/3
		 * Detta caset funkar.
		 */

		//Poly 1, Edge 1
		/*Endpoint p1 = new Endpoint(153, 400-318);
				Endpoint p2 = new Endpoint(218, 400-152);
				Edge p1p2 = new Edge(p1, p2);
				p1.setNextSeg(p1p2);
				p2.setPrevSeg(p1p2);
				p1.setBelonging(Endpoint.POLYGON1);
				p2.setBelonging(Endpoint.POLYGON1);

				//Poly 1, Edge 2
				Endpoint p3 = new Endpoint(61, 400-94);
				Edge p2p3 = new Edge(p2, p3);
				p2.setNextSeg(p2p3);
				p3.setPrevSeg(p2p3);
				p3.setBelonging(Endpoint.POLYGON1);

				//Poly 1, Edge 3
				Endpoint p4 = new Endpoint(40, 400-274);
				Edge p3p4 = new Edge(p3, p4);
				p3.setNextSeg(p3p4);
				p4.setPrevSeg(p3p4);
				p4.setBelonging(Endpoint.POLYGON1);

				//Poly 1, Edge 4
				Edge p4p1 = new Edge(p4, p1);
				p4.setNextSeg(p4p1);
				p1.setPrevSeg(p4p1);

				//Poly 2, Edge 1
				Endpoint p5 = new Endpoint(154, 400-304);
				Endpoint p6 = new Endpoint(239, 400-311);
				Edge p5p6 = new Edge(p5, p6);
				p5.setNextSeg(p5p6);
				p6.setPrevSeg(p5p6);
				p5.setBelonging(Endpoint.POLYGON2);
				p6.setBelonging(Endpoint.POLYGON2);

				//Poly 2, Edge 2
				Endpoint p7 = new Endpoint(270, 400-84);
				Edge p6p7 = new Edge(p6, p7);
				p6.setNextSeg(p6p7);
				p7.setPrevSeg(p6p7);
				p6.setBelonging(Endpoint.POLYGON2);
				p7.setBelonging(Endpoint.POLYGON2);

				//Poly 2, Edge 3
				Endpoint p8 = new Endpoint(156, 400-80);
				Edge p7p8 = new Edge(p7, p8);
				p7.setNextSeg(p7p8);
				p8.setPrevSeg(p7p8);
				p7.setBelonging(Endpoint.POLYGON2);
				p8.setBelonging(Endpoint.POLYGON2);

				//Poly 2, Edge 4
				Edge p8p5 = new Edge(p8, p5);
				p8.setNextSeg(p8p5);
				p5.setPrevSeg(p8p5);
				p8.setBelonging(Endpoint.POLYGON2);
				p5.setBelonging(Endpoint.POLYGON2);

				CollisionDetection detect = new CollisionDetection(p1, p5);
				ArrayList<Endpoint>intersections = new ArrayList<Endpoint>();
				intersections = detect.getIntersections();
				if(intersections != null){
					for(Endpoint i : intersections){
						
					System.out.println(i.getX()+", " + i.getY());}
				}
				else{
					System.out.println("Intersection array is empty.");
				}

		 */


		//======================================Testa CollisionDetection med en fixerad input=========================

		//Poly 1, Edge 1
		/*Endpoint p1 = new Endpoint(135, 400-294);
		Endpoint p2 = new Endpoint(258, 400-138);
		Edge p1p2 = new Edge(p1, p2);
		p1.setNextSeg(p1p2);
		p2.setPrevSeg(p1p2);
		p1.setBelonging(Endpoint.POLYGON1);
		p2.setBelonging(Endpoint.POLYGON1);

		//Poly 1, Edge 2
		Endpoint p3 = new Endpoint(79, 400-71);
		Edge p2p3 = new Edge(p2, p3);
		p2.setNextSeg(p2p3);
		p3.setPrevSeg(p2p3);
		p3.setBelonging(Endpoint.POLYGON1);

		//Poly 1, Edge 3
		Endpoint p4 = new Endpoint(46, 400-243);
		Edge p3p4 = new Edge(p3, p4);
		p3.setNextSeg(p3p4);
		p4.setPrevSeg(p3p4);
		p4.setBelonging(Endpoint.POLYGON1);

		//Poly 1, Edge 4
		Edge p4p1 = new Edge(p4, p1);
		p4.setNextSeg(p4p1);
		p1.setPrevSeg(p4p1);

		//Poly 2, Edge 1
		Endpoint p5 = new Endpoint(139, 400-277);
		Endpoint p6 = new Endpoint(252, 400-289);
		Edge p5p6 = new Edge(p5, p6);
		p5.setNextSeg(p5p6);
		p6.setPrevSeg(p5p6);
		p5.setBelonging(Endpoint.POLYGON2);
		p6.setBelonging(Endpoint.POLYGON2);

		//Poly 2, Edge 2
		Endpoint p7 = new Endpoint(306, 400-83);
		Edge p6p7 = new Edge(p6, p7);
		p6.setNextSeg(p6p7);
		p7.setPrevSeg(p6p7);
		p6.setBelonging(Endpoint.POLYGON2);
		p7.setBelonging(Endpoint.POLYGON2);

		//Poly 2, Edge 3
		Endpoint p8 = new Endpoint(160, 400-50);
		Edge p7p8 = new Edge(p7, p8);
		p7.setNextSeg(p7p8);
		p8.setPrevSeg(p7p8);
		p7.setBelonging(Endpoint.POLYGON2);
		p8.setBelonging(Endpoint.POLYGON2);

		//Poly 2, Edge 4
		Edge p8p5 = new Edge(p8, p5);
		p8.setNextSeg(p8p5);
		p5.setPrevSeg(p8p5);
		p8.setBelonging(Endpoint.POLYGON2);
		p5.setBelonging(Endpoint.POLYGON2);

		CollisionDetection detect = new CollisionDetection(p1, p5);
		ArrayList<Endpoint>intersections = new ArrayList<Endpoint>();
		intersections = detect.getIntersections();
		if(intersections != null){
			for(Endpoint i : intersections){
				
			System.out.println(i.getX()+", " + i.getY());}
		}
		else{
			System.out.println("Intersection array is empty.");
		}

		 */

		//======================================Testa currentXCoord i klassen Edge igen===============================
		/* Test case: 	Upper: (124, 325)
		 * 				Lower1: (22, 277)
		 * 				Lower2: (206, 180)
		 * 				Sweep_y: 124
		 * 
		 * Results derived from live tests say that lower1 gives currentX = 0 and lower2 gives currentX = 124.
		 * 
		 * Results: lower1 gives 0 which I can't explain. Calculations seems correct.
		 * 
		 * Fixat: Avrundningsfel i currentXCoord. Castar divisionen av k till division med doubles nu!
		 * Tidpunkt: 21:19 26/2 - 15
		 */
		/*
		Endpoint p1 = new Endpoint(124, 400-325);
		Endpoint p2 = new Endpoint(22, 400-277);
		Edge p1p2 = new Edge(p1, p2);

		Endpoint p3 = new Endpoint(206, 180);
		Edge p1p3 = new Edge(p1, p3);
		//p1.addUpperTo(p1p3);

		int sweep_y = 325;
		int currentX1 = p1p2.currentXCoord(sweep_y);
		int currentX2 = p1p3.currentXCoord(sweep_y);

		System.out.println("Current x1: "+currentX1);
		System.out.println("Current x2: "+currentX2);
		 */


		/*======================================Testa currentXCoord i klassen Edge====================================
		 * Test cases:
		 *  1. Horisontell linje: Fungerar. Har antagit det b�st att returnera upperns x. Sedan f�r lower j�mf�ras med linjer osv.
		 *  2. Vertikal linje: Fungerar.
		 *  3. Vinklad: Verkar fungera.
		 *  Test utf�rt: 9/2 kl 19:29
		 */


		//Test 1: Horisontell linje
		/*
		Endpoint p1 = new Endpoint(200, 100);
		Endpoint p2 = new Endpoint(100,  200);
		Edge p1p2 = new Edge(p1, p2);

		//Testa f�r sweep_y = p1p2.getY()
		System.out.println(p1p2.currentXCoord(400 - 150));//Funkar inte, tror att sweepline lies outside
		//Testa f�r sweep_y = utanf�r. Borde ge null?
		System.out.println(p1p2.currentXCoord(400-101));



		 */

		/*======================================Testa balansering av status============================================
		 * Problem: Statusen skapar obalanserade tr�d. G�r det sv�rt att hitta grannar.
		 * N�dl�sning: L�tsas att vi har en fungerande status och k�r traverseInOrder ist�llet :/
		 * Test 1: Anv�nd de punkter och kanter som skapade ett obalanserat s�ktr�d fr�n b�rjan. L�gg till och se om det inte l�ser sig.
		 * Resultat: Det verkar fungera. Kolla igen i programmet om det kan vara n�got annat fel.
		 * Datum: 6/2 kl 15:37
		 */

		//Simulate the first part of the program.

		//Insert
		/*Endpoint p1 = new Endpoint(120, 400-320);
		Endpoint p2 = new Endpoint(41,  400-268);
		Edge p1p2 = new Edge(p1, p2);
		status.insert(p1p2);

		//Insert
		Endpoint p3 = new Endpoint(120, 400-320);
		Endpoint p4 = new Endpoint(196, 400-205);
		Edge p3p4 = new Edge(p3,p4);
		status.insert(p3p4);

		//Insert
		Endpoint p5 = new Endpoint(205, 400-317);
		Endpoint p6 = new Endpoint(128, 400-289);
		Edge p5p6 = new Edge(p5, p6);
		status.insert(p5p6);

		//Insert
		Endpoint p7 = new Endpoint(205, 400-317);
		Endpoint p8 = new Endpoint(315, 400-120);
		Edge p7p8 = new Edge(p7, p8);
		status.insert(p7p8);

		System.out.println("Four segments inserted. Traverse.");
		status.traverseInOrder();

		System.out.println("\nIntersection point --> crop and delete");
		//Intersection! Crop the left and right
		Endpoint intersection = new Endpoint(138, 400-293);
		Edge cropped1 = new Edge(intersection, p6);
		Edge cropped2 = new Edge(intersection, p4);

		//Now delete p3p4 and p5p6
		status.delete(p3p4);
		status.delete(p5p6);
		status.traverseInOrder();

		System.out.println("\nInsert cropped segments");
		//Insert cropped1 and cropped2
		status.insert(cropped1);
		status.insert(cropped2);
		status.traverseInOrder();

		System.out.println("\nLower Point reached. \nDelete and traverse again.");
		//Lower point of cropped1 reached. Delete cropped1.
		status.delete(cropped1);
		status.traverseInOrder();

		//Insert
		Endpoint p9 = new Endpoint(220, 400-81);
		Edge p6p9 = new Edge(p6, p9);
		status.insert(p6p9);

		System.out.println("\nLast Edge inserted. Traverse.\n");

		status.traverseInOrder();
		 */

		//===========================================Test av isCrossingWith med horisontella och vertikala linjer=======
		/* Test utf�rt 3/2 kl 15:30
		 * Resultat: Exception vid horisontell linje. Metoden returnerar null eftersom den inte hittar y1==y2 intersection
		 * L�sning: Kolla om det beh�vs fler villkor kring horisontella linjer... Nope. Bygg en det-metod fr�n wikipedia ist�llet.
		 */




		//Horizontal segment
		/*
		Endpoint p1 = new Endpoint(25, 25);//Upper
		Endpoint p2 = new Endpoint(30, 40);//Lower
		Edge p1p2 = new Edge(p1,p2);
		//Detta segment korsar det horisontella p1p2-segmentet
		Endpoint p3 = new Endpoint(50, 10);//Upper
		Endpoint p4 = new Endpoint(50, 60);//Lower 
		Edge p3p4 = new Edge(p3,p4);


		//Printen nedan �r ett test av isCrossing som inte fungerar. G�r ett test med ny det-metod ist�llet. 
		//System.out.println("Linjerna korsar i: "+p1p2.isCrossingWith(p3p4).getX()+", "+p1p2.isCrossingWith(p3p4).getRealY());

		//===================================Test av doIntersect i Edge 4/2=======================================================
		/* Test 1: Horisontellt mot lutande. 
		 * Resultat: Intersection funnen!
		 * 
		 * Test 2: Samma fast korsande punkt utanf�r segmentens endpoints.
		 * Resultat: Returnerar null--> ok!
		 * 
		 * Test 3: Ett vertikalt segment och ett horisontellt
		 * Resultat: Fungerar!
		 * 
		 * Test 4: Som ovan fast utanf�r segmentens endpoints
		 * Resultat: Fungerar, returnerar null --> ok!
		 * 
		 * Test 5: Ett verikalt och ett lutande segment korsar
		 * Resultat: Funkar!
		 * 
		 * Test 6: Som ovan fast utanf�r segmentens endpoints
		 * Resultat: Funkar, retirnerar null --> ok!
		 */

		/*

		Endpoint crossing = p1p2.doIntersect(p3p4);
		System.out.println("Testar om p1p2 och p3p4 korsar varandra. G�r det det?");
		System.out.println(crossing!=null);*/
		//System.out.println("Korsningspunkten ligger i ("+crossing.getX()+", "+crossing.getRealY()+")");

		/*
		//Endpoint p5 = new Endpoint(100, 20);//Upper
		Endpoint p5 = new Endpoint(25, 25);//Upper test 3
		//Endpoint p6 = new Endpoint(100, 100);//Lower: This made a degenerate case. The lines p5p6 is almost identical to p3p4
		Endpoint p6 = new Endpoint(100, 70);
		Edge p5p6 = new Edge(p5,p6);*/




		//==============================================================================================================
		/* Test av findNeighbour och find rightmost/leftmost segments vid m�nga segments hos en upper
		 * Testdatum: 3/2 kl 10:20
		 * Test:
		 * 	1. Segmentet har tv� neighbours --> Funkar!
		 *  2. Segmentet har inga neighbours --> Metoderna returnerar null -- ok!
		 *  3. En upper har flera segment - hitta leftmost & rightmost
		 *  Resultat: St�tte p� ett degenerate case med linje p3p4 och p5p6 som 
		 *  l�g s� n�ra varandra att det var sv�rt att avg�ra vilken sida de var p�.
		 *  F�rmodligen har programmet r�tt och mina antaganden felaktiga. 
		 *  L�sning: Testa igen med tydligare input.
		 *  
		 *  Resultat: Ny Nullpointer i goLeft som kallas fr�n status.insert
		 *  
		 *  L�sning: Bytte ut en check i rotation(ENDAST I GOLEFT-metoden som bara anv�nds p� ETT ST�LLE)
		 *  fr�n att j�mf�ra uppers mot segment i isToRightOrLeft till att anv�nda lower. Detta f�r att 
		 *  alla uppers ligger p� samma plats i just detta case, vilket alltid g�r att metoden returnerar 0.
		 *  Resultat: Funkar 3/2 kl 15:09!
		 */
		/*
		Endpoint p1 = new Endpoint(25, 25);//Upper
		Endpoint p2 = new Endpoint(28, 50);//Lower
		Edge p1p2 = new Edge(p1,p2);

		//Mittersta segmentet har tv� neighbours
		//Endpoint p3 = new Endpoint(60, 30);//Upper
		Endpoint p3 = new Endpoint(25, 25);//Upper test 3
		Endpoint p4 = new Endpoint(65, 60);//Lower 
		Edge p3p4 = new Edge(p3,p4);

		//Endpoint p5 = new Endpoint(100, 20);//Upper
		Endpoint p5 = new Endpoint(25, 25);//Upper test 3
		//Endpoint p6 = new Endpoint(100, 100);//Lower: This made a degenerate case. The lines p5p6 is almost identical to p3p4
		Endpoint p6 = new Endpoint(100, 70);
		Edge p5p6 = new Edge(p5,p6);

		//Test 1 & 2
		status.insert(p1p2);
		status.insert(p3p4);
		status.insert(p5p6);

		if(status.findLeftNeighbour(p1p2)==p3p4){
			System.out.println("Left funkar");
		}
		if(status.findRightNeihbour(p5p6)==p3p4){
			System.out.println("Right funkar");
		}

		//Test 3: Hitta left- och rightmost segment f�r en gemensam upper.
		//Simulera att alla har samma upper:
		p1.addUpperTo(p3p4);
		p1.addUpperTo(p5p6);
		Edge left = p1.getUpperTo().get(0);
		Edge right = p1.getUpperTo().get(0);
		for(Edge e : p1.getUpperTo()){
			if(e.getLower().isToRightOrLeftOf(left)<0){//May throw exception. May only be used with two uppers at the same place... Is this always the case?
				left = e;
			}
			if(e.getLower().isToRightOrLeftOf(right)>0){//If e is to the right of right -> update right
				right = e;
			}
		}
		System.out.println("Left segment is: ("+left.getUpper().getX()+", "+left.getUpper().getRealY()+") ("+left.getLower().getX()+", "+left.getLower().getRealY()+")");
		System.out.println("Right segment is: ("+right.getUpper().getX()+", "+right.getUpper().getRealY()+") ("+right.getLower().getX()+", "+right.getLower().getRealY()+")");

		status.traverseInOrder();

		//Ovan test avsl�jar fel i status.insert. Ett h�gersegment erk�nns inte som h�ger om ett annat.

		//Case: p6 erk�nns inte ligga till h�ger om p3p4. Testa metoden Endpoint.isToRightOrLeftOf(Edge)
		System.out.println("Is Endpoint.isToRightOrLeftOf(Edge) working?");
		System.out.println(p6.isToRightOrLeftOf(p3p4)>0);//P�st�ende: p6 ligger till h�ger, ska returnera true
		System.out.println(p6.isToRightOrLeftOf(p3p4));
		System.out.println(p4.isToRightOrLeftOf(p1p2));

		System.out.println("Is Endpoint.testNewSideMethod(Edge) working?");
		System.out.println(p6.testNewSideMethod(p3p4)>0);//P�st�ende: p6 ligger till h�ger, ska returnera true
		System.out.println(p6.testNewSideMethod(p3p4));
		System.out.println(p4.testNewSideMethod(p1p2));

		 */


		//==============================================================================================================
		//Test av deletion av segments med en viss lower
		//Resultat 2/2 kl 19:20 -- Funkar!!
		/*Endpoint p1 = new Endpoint(25, 25);//Upper
		Endpoint p2 = new Endpoint(28, 50);//Lower
		Edge p1p2 = new Edge(p1,p2);


		Endpoint p3 = new Endpoint(50, 50);//Upper
		Endpoint p4 = new Endpoint(110, 200);//Lower to all except p1p2

		Edge p3p4 = new Edge(p3,p4);

		Endpoint p5 = new Endpoint(100, 50);//Upper
		Endpoint p6 = new Endpoint(110, 200);//Lower to all except p1p2
		Edge p5p6 = new Edge(p5,p6);

		Endpoint p7 = new Endpoint(130, 50);//Upper
		Endpoint p8 = new Endpoint(110, 200);//Lower to all except p1p2
		Edge p7p8 = new Edge(p7,p8);

		status.insert(p1p2);
		status.insert(p3p4);
		status.insert(p5p6);
		status.insert(p7p8);

		status.traverseInOrder();

		ArrayList<Edge> segments = new ArrayList<Edge>();

		status.deleteWithLower(p4, segments);
		for(Edge e : segments){
			status.delete(e);
		}

		System.out.println("============================\n=========================");

		status.traverseInOrder();
		 */
		//===============================================================================================================
		//Test av insert med isToRightOrLeftOf fr�n Endpoint ist�llet f�r Edge. Punkt mot segment ist�llet f�r segment mot segment.
		//Resultat: It's wooorking! It's wooooooorkiiiiing B-)
		/*
		Edge p1p2 = new Edge(p1, p2);

		//Crosses with p1p2
		Endpoint p3 = new Endpoint(50, 25);
		Endpoint p4 = new Endpoint(10, 40);

		Edge p3p4 = new Edge(p3,p4);

		Endpoint p5 = new Endpoint(100, 25);//Upper
		Endpoint p6 = new Endpoint(110, 75);//Lower
		Edge p5p6 = new Edge(p5,p6);

		Endpoint p7 = new Endpoint(130, 15);//Upper
		Endpoint p8 = new Endpoint(135, 200);//Lower
		Edge p7p8 = new Edge(p7,p8);

		//Priority in queue and status: (p7p8, p1p2), p3p4, p5p6


		status.insert(p3p4);
		status.insert(p1p2);
		status.insert(p5p6);
		status.insert(p7p8);

		status.traverseInOrder();
		 */
		//================================================================================================================
		/* Test av isToRightOrLeftOf i Edge, anv�nds i status
		 * Syfte: Segment s�tts in i status. Segmentet delar upper med ett annat segment. S�tt in dem i r�ttordning.
		 * Kan bli fel om segmenten korsar varandra. L�sning d�: Endpoints isToRightOrLeft 
		 * Resultat: Placerar segmenten fr�n v�nster till h�ger enligt x. 
		 * Funkar �ven f�r segment med uppers p� samma plats.
		 * OBS! Inget gjort f�r hela segment som ligger p� samma plats. 
		 * Tid: 2/2 kl 18:05
		 */

		/*Test 1: Ett antal segment + tv� som har coincided uppers
		 * Simulerar att ena uppern h�ller b�da segmenten
		 */

		/*Endpoint p1 = new Endpoint(25, 25);//Upper
		Endpoint p2 = new Endpoint(50, 50);//Lower
		Edge p1p2 = new Edge(p1, p2);

		Endpoint p3 = new Endpoint(100, 25);//Upper coinciding with upper
		Endpoint p4 = new Endpoint(75, 50);//Lower

		Edge p3p4 = new Edge(p3,p4);

		Endpoint p5 = new Endpoint(100, 25);//Upper coinciding with upper
		Endpoint p6 = new Endpoint(110, 75);
		Edge p5p6 = new Edge(p5,p6);

		Endpoint p7 = new Endpoint(130, 15);//Upper
		Endpoint p8 = new Endpoint(135, 200);//Lower
		Edge p7p8 = new Edge(p7,p8);

		//Priority in queue and status: p7p8, p1p2, p3p4, p5p6

		//Simulate the queues adding of segment p5p6 to p3p4:s upper:
		p3p4.getUpper().getUpperTo().add(p5p6);

		status.insert(p1p2);
		for(Edge e : p3p4.getUpper().getUpperTo()){//Should loop through all segments an upper has :P
			status.insert(e);
		}
		status.insert(p7p8);

		status.traverseInOrder();*/

		//Test 2: Korsande segment i statusen
		//Resultat: Funkar men v�ljer att pr�va med motsvarande metod f�r en upper point mot en hel linje. K�nns funky annars.
		/*Endpoint p1 = new Endpoint(25, 25);//Upper
		Endpoint p2 = new Endpoint(50, 50);//Lower
		Edge p1p2 = new Edge(p1, p2);

		//Crosses with p1p2
		Endpoint p3 = new Endpoint(50, 25);
		Endpoint p4 = new Endpoint(10, 40);

		Edge p3p4 = new Edge(p3,p4);

		Endpoint p5 = new Endpoint(100, 25);//Upper
		Endpoint p6 = new Endpoint(110, 75);//Lower
		Edge p5p6 = new Edge(p5,p6);

		Endpoint p7 = new Endpoint(130, 15);//Upper
		Endpoint p8 = new Endpoint(135, 200);//Lower
		Edge p7p8 = new Edge(p7,p8);

		//Priority in queue and status: (p7p8, p1p2), p3p4, p5p6


		status.insert(p3p4);
		status.insert(p1p2);
		status.insert(p5p6);
		status.insert(p7p8);

		status.traverseInOrder();
		 */


		//=================================================================================================================
		//Test av gammal metod f�r att hitta om en insatt eventpoint krockar med en lower eller ligger i ett segment
		//findIntersectingSegments i StatusTree
		/*Tests:
		 * 1. New point lies inside segment
		 * 2. New point lies on a lower point
		 * 3. Both of the above
		 * Test result: Works for interior. Only tested a point against a line.
		 * Date: 2/2 17:15
		 */

		//Test 1: Point p3 is on line p1p2 and is detected as intersection.
		/*Endpoint p1 = new Endpoint(50, 50);//Upper
		Endpoint p2 = new Endpoint(100, 100);//Lower
		Edge p1p2 = new Edge(p1, p2);

		Endpoint p3 = new Endpoint(75,75);//Should lie on the line

		ArrayList<Edge> intersectionLower = new ArrayList<Edge>();
		ArrayList<Edge> intersectionInterior = new ArrayList<Edge>();

		status.insert(p1p2);
		//Checks if p3 is on the line p1p2
		status.findIntersectingSegments(p3, intersectionLower, intersectionInterior);

		if(intersectionLower.size()!=0){
			System.out.println("Lower array has something.");
		}
		if(intersectionInterior.size()!=0){
			System.out.println("Interior array has something.");
			System.out.println(intersectionInterior.get(0).getUpper().getX()+", "+ intersectionInterior.get(0).getUpper().getY());
			System.out.println(intersectionInterior.get(0).getLower().getX()+", "+ intersectionInterior.get(0).getLower().getY());
		}*/

		//Test 2: Point p3 is on line p1p2s lower and is detected as intersection.
		//Result: Works for a point on an edges lower. 
		//Date: 2/2 17:15
		/*Endpoint p1 = new Endpoint(50, 50);//Upper
				Endpoint p2 = new Endpoint(100, 100);//Lower
				Edge p1p2 = new Edge(p1, p2);

				Endpoint p3 = new Endpoint(100,100);

				ArrayList<Edge> intersectionLower = new ArrayList<Edge>();
				ArrayList<Edge> intersectionInterior = new ArrayList<Edge>();

				status.insert(p1p2);
				//Checks if p3 is on the line p1p2
				status.findIntersectingSegments(p3, intersectionLower, intersectionInterior);

				if(intersectionLower.size()!=0){
					System.out.println("Lower array has something.");
					System.out.println(intersectionLower.get(0).getUpper().getX()+", "+ intersectionLower.get(0).getUpper().getY());
					System.out.println(intersectionLower.get(0).getLower().getX()+", "+ intersectionLower.get(0).getLower().getY());
				}
				if(intersectionInterior.size()!=0){
					System.out.println("Interior array has something.");
				}		

		 */


		//==================================================================================================================
		/*Test of new conditions in insert method of eventQueue. Datum: 2/2 - 15
		 * The method shall/have:
		 * 1. Prioritize the eventpoints that are uppers before lowers when they coinside
		 * 2. Have been cleaned up with private methods for goLeft and goRight - test if this works
		 * Test case: Insert coinsiding uppers, lowers and uppers and lowers. Print the result.
		 * Result: 
		 */

		//Test 1: 2 edges with 2 uppers at same coordinates
		//Result: Works after assigning upper/lower booleans in every endpoint from setUpperAndLower in Edge.
		/*Endpoint p1 = new Endpoint(50, 50);
		Endpoint p2 = new Endpoint(25, 75);
		Edge p1p2 = new Edge(p1, p2);

		Endpoint p3 = new Endpoint(50,50);//Coinsiding upper
		Endpoint p4 = new Endpoint(75, 75);
		Edge p3p4 = new Edge(p3,p4);

		eventQueueTest.insert(p1); eventQueueTest.insert(p2); eventQueueTest.insert(p3); eventQueueTest.insert(p4);
		eventQueueTest.traverseInOrder();
		System.out.println("Is uppers isUpperTo array bigger than 1 now?");
		System.out.println(p1.getUpperTo().size()>1);
		for(Edge e : p1.getUpperTo()){
			System.out.println(e.getUpper().getX());
		}
		 */
		//Test 2: Upper and lower endpoints coinside
		//Result: Works 2/2 - 15! lower has a lower position in the tree than uppers at same coords.
		/*
		Endpoint p1 = new Endpoint(50, 50);//Lower
		Endpoint p2 = new Endpoint(25, 25);
		Edge p1p2 = new Edge(p1, p2);

		Endpoint p3 = new Endpoint(50,50);//Upper coinciding with upper
		Endpoint p4 = new Endpoint(75, 75);
		Edge p3p4 = new Edge(p3,p4);

		eventQueueTest.insert(p1); eventQueueTest.insert(p2); eventQueueTest.insert(p3); eventQueueTest.insert(p4);
		eventQueueTest.traverseInOrder();

		 */






		//==================================================================================================================
		//Testa isCrossingWith(Edge segment) i klassen Edge
		/*Endpoint p1 = new Endpoint(50, 300);
		Endpoint p2 = new Endpoint(150, 200);
		Edge p1p2 = new Edge(p1, p2);

		Endpoint p3 = new Endpoint(150, 300);
		Endpoint p4 = new Endpoint(50, 200);
		Edge p3p4 = new Edge(p3, p4);

		Endpoint testNewCode = p1p2.intersection(p1p2.getUpper().getX(), p1p2.getUpper().getY(), p1p2.getLower().getX(), p1p2.getLower().getY(), p3p4.getUpper().getX(), p3p4.getUpper().getY(), p3p4.getLower().getX(), p3p4.getLower().getY());
		System.out.println(testNewCode.getX()+", "+testNewCode.getRealY());//=========== Seem to work now. Mixed realY and Y. Do
																			//=========== not know Y :D
		 */

		//Testet ovan funkade f�r tv� lutande segment den 27/1 kl 16:48
		//Fixa s� att det g�ller r�tt intervall f�r segmenten, inte hela linjerna.
		//Fixa cases d� ett eller b�da segmenten �r vertikala.

		//================================== Upprepar testet ovan f�r segment som korsar varandra OMM de r�knas som linjer==
		/*Endpoint p1 = new Endpoint(125, 225);
		Endpoint p2 = new Endpoint(150, 200);
		Edge p1p2 = new Edge(p1, p2);

		Endpoint p3 = new Endpoint(75, 225);
		Endpoint p4 = new Endpoint(50, 200);
		Edge p3p4 = new Edge(p3, p4);

		//Endpoint crossing = p1p2.isCrossingWith(p3p4);
		//System.out.println("Linjesegmenten korsar varandra : "+ crossing==null);

		Endpoint testNewCode = p1p2.intersection(p1p2.getUpper().getX(), p1p2.getUpper().getY(), p1p2.getLower().getX(), p1p2.getLower().getRealY(), p3p4.getUpper().getX(), p3p4.getUpper().getRealY(), p3p4.getLower().getX(), p3p4.getLower().getRealY());
		if(testNewCode==null){
			System.out.println("It's null, yayy!");
		}
		else System.out.println("Crap.... Something is wrong."); //============================Test of intersection between segments
																			//				worked with null return for intersection outside segments intervals
																			//				27/1 17:18
		 */
		//==================================================================================================================
		//Testa statustr�dets insert och search med nya isToRightOrLeft i Edge!
		/*
		Endpoint p1 = new Endpoint(50, 350);
		Endpoint p2 = new Endpoint(60, 300);
		Edge p1p2 = new Edge(p1, p2);

		Endpoint p3 = new Endpoint(60, 300);//Sammanfaller med p2.
		Endpoint p4 = new Endpoint(100, 350);
		Edge p3p4 = new Edge(p3,p4);

		Endpoint p5 = new Endpoint(300, 300);
		Endpoint p6 = new Endpoint(290, 350);
		Edge p5p6 = new Edge(p5, p6);

		eventQueueTest.insert(p1);
		eventQueueTest.insert(p2);
		eventQueueTest.insert(p3);
		eventQueueTest.insert(p4);
		eventQueueTest.insert(p5);
		eventQueueTest.insert(p6);

		//Hoppa �ver eventQueuen s� l�nge

		status.insert(p1p2);
		status.insert(p3p4);
		status.insert(p5p6);
		status.traverseInOrder();

		//======================================================>Statusens insert �r ok 23/1 kl 16:20 (Koden ovan)

		StatusNode node = status.search(p5p6);
		System.out.println("S�kningen lyckades.....?");
		System.out.println(node.getSegment()==p5p6);

		//=====================================================> Status search fungerar 23/1 kl 16:21 (Koden ovan)

		 */





		//==================================================================================================================
		//Testa StatusTree. Funkar specialCompareTo? Sortera segment som har samma upper. Upper och intersections som blir nya uppers.
		//Kolla �ven search

		//specialCompareTo anv�nds inte l�ngre. Inte currentX heller, den funkade inte. 

		//Testa med korsande segment och l�t sweep_y minska.

		/*Endpoint p0 = new Endpoint(10, 250);
		Endpoint p1 = new Endpoint(390, 350);
		Endpoint p2 = new Endpoint(390, 250);
		Endpoint p3 = new Endpoint(10, 350);


		Edge p0p1 = new Edge(p0, p1);
		Edge p2p3 = new Edge(p2, p3);


		int sweep_y = p0.getRealY();
		//int sweep_y = p1.getRealY();

		status.insert(p0p1, sweep_y);
		status.insert(p2p3, sweep_y);

		status.traverseInOrder();


		//Testa currentXCoord och compareSides med ovan punkter och kanter
		System.out.println("Test 1 av currentXCoord.");
		System.out.println("Punkt p0 har x: "+p0.getX()+" och y: "+p0.getRealY());
		System.out.println("currentXCoord s�ger att p0:s x �r: "+p0p1.currentXCoord(p0.getRealY()));
		System.out.println("\n=======================================================================================");

		System.out.println("Test 2 av currentXCoord.");
		System.out.println("Punkt p3 har x: "+p3.getX()+" och y: "+p3.getRealY());
		System.out.println("currentXCoord s�ger att p3:s x �r: "+p2p3.currentXCoord(p3.getRealY()));

		//Ovan currentXCoord funkar inte. G�r om insert och search till att bli determinantbaserat
		//Testa ny "isSmallerThan"-metod f�r att avg�ra v�gar i statustr�det:

		System.out.println("Ligger punkten p2 till h�ger om linjen p0p1? Svaret ska vara ja.");
		System.out.println(p2.isToRightOrLeftOf(p0p1));

		System.out.println("Ligger punkten p3 till v�nster eller h�ger om linjen p0p1?");
		System.out.println(p3.isToRightOrLeftOf(p0p1));

		Endpoint onLine = new Endpoint(200, 300); //Denna ska ge resultat 0

		System.out.println("Hur �r det med punkten d�r linjerna korsar varandra d�?");
		System.out.println(onLine.isToRightOrLeftOf(p0p1));
		System.out.println(onLine.isToRightOrLeftOf(p2p3));


		ArrayList<Edge> intersectionLower = new ArrayList<Edge>();
		ArrayList<Edge> intersectionInterior = new ArrayList<Edge>();
		status.findIntersectingSegments(onLine, intersectionLower, intersectionInterior);

		if(intersectionInterior.isEmpty()){
			System.out.println("Inga intersections :/");
		}

		//Testa nya j�mf�relsemetoden f�r statusens insert och search: isToRightOrLeftOf(Edge segment) i Edge.
		//Funkar endast d� linjer inte korsar varandra. Det g�r de inte i statusen!!!!!!!!!!! Linjer ers�tts av nya s� snart 
		//ett intersection event anl�nder.

		Endpoint p4 = new Endpoint(50, 150);
		Endpoint p5 = new Endpoint(60, 70);
		Edge p4p5 = new Edge(p4, p5);

		Endpoint p6 = new Endpoint(40, 70);
		Endpoint p7 = new Endpoint(80, 150);
		Edge p6p7 = new Edge(p6, p7);

		System.out.println("Testa nya j�mf�relsemetoden f�r statusen. Ska kunna hantera segment med samma startpunkt.");
		System.out.println("Vilken sida ligger p4p5 om p6p7?");
		System.out.println(p4p5.isToRightOrLeftOf(p6p7));
		System.out.println("Om allt �r r�tt ska svaret vara -1.");


		 */







		//==================================================================================================================
		//Test av AVL-tr�det - Fungerar insert med flera segments i en upper? Insert? Yes!
		/*
		Endpoint p0 = new Endpoint(300, 100);
		Endpoint p1 = new Endpoint(200, 200);
		Endpoint p2 = new Endpoint(100, 300);
		Endpoint p3 = new Endpoint(250, 300);
		Endpoint p4 = new Endpoint(300, 300);

		Edge p0p1 = new Edge(p0, p1);
		Edge p1p2 = new Edge(p1, p2);
		Edge p1p3 = new Edge(p1, p3);
		Edge p1p4 = new Edge(p1, p4);

		eventQueueTest.insert(p0);
		eventQueueTest.insert(p1);//Should keep all segments below now.
		eventQueueTest.insert(p2);
		eventQueueTest.insert(p3);
		eventQueueTest.insert(p4);

		System.out.println("p0 is upper to: ");
		for(Edge u : p0.getUpperTo()){
			System.out.println("Upper: "+u.getUpper().getX()+", "+u.getUpper().getRealY());
			System.out.println("Lower: "+u.getLower().getX()+", "+u.getLower().getRealY());
		}
		System.out.println("========================================================================\n");
		System.out.println("p1 is upper to: ");
		for(Edge u : p1.getUpperTo()){
			System.out.println("Upper: "+u.getUpper().getX()+", "+u.getUpper().getRealY());
			System.out.println("Lower: "+u.getLower().getX()+", "+u.getLower().getRealY());
		}
		System.out.println("========================================================================\n");
		System.out.println("p2 is upper to: ");
		for(Edge u : p2.getUpperTo()){
			System.out.println("Upper: "+u.getUpper().getX()+", "+u.getUpper().getRealY());
			System.out.println("Lower: "+u.getLower().getX()+", "+u.getLower().getRealY());
		}
		System.out.println("========================================================================\n");
		System.out.println("p3 is upper to: ");
		for(Edge u : p3.getUpperTo()){
			System.out.println("Upper: "+u.getUpper().getX()+", "+u.getUpper().getRealY());
			System.out.println("Lower: "+u.getLower().getX()+", "+u.getLower().getRealY());
		}
		System.out.println("========================================================================\n");
		System.out.println("p4 is upper to: ");
		for(Edge u : p4.getUpperTo()){
			System.out.println("Upper: "+u.getUpper().getX()+", "+u.getUpper().getRealY());
			System.out.println("Lower: "+u.getLower().getX()+", "+u.getLower().getRealY());
		}



		 */



		//==================================================================================================================
		//Test av determinant-metod f�r att kolla vilken sida en linje ligger av en annan
		/*
		Endpoint p1 = new Endpoint(200, 200);
		Endpoint p2 = new Endpoint(100, 300);
		Edge p1p2 = new Edge(p1, p2);

		Endpoint p3 = new Endpoint(300, 300);
		Edge p1p3 = new Edge(p1, p3);

		System.out.println("p1p2.compareSides(p3) = "+p1p2.compareSides(p3));

		 */


		//==================================================================================================================
		//Test av intersectionmetod med en vertikal, en horisontell och ett par linjer till
		/*
		Endpoint p1 = new Endpoint(100, 200);
		Endpoint p2 = new Endpoint(300, 100);
		Edge p1p2 = new Edge(p1, p2);//Horisontell linje - kolla s� att horisontell funkar i status insert och checkar i status.

		Endpoint p3 = new Endpoint(200, 300);
		Endpoint p4 = new Endpoint(200, 100);
		Edge p3p4 = new Edge(p3, p4);//Vertical linje

		Endpoint p6 = new Endpoint(200, 250);

		Endpoint p5 = new Endpoint(300, 350);
		Edge p3p5 = new Edge(p3, p5);

		Edge p6p5 = new Edge(p6, p5);

		eventQueueTest.insert(p1);
		eventQueueTest.insert(p2);
		eventQueueTest.insert(p3);
		eventQueueTest.insert(p4);

		status.insert(p1p2, p1p2.getUpper().getRealY());
		status.insert(p3p4, p3p4.getUpper().getRealY());
		//status.insert(p3p5, p3p5.getUpper().getRealY());

		ArrayList<Edge> intersectionLower = new ArrayList<Edge>();
		ArrayList<Edge> intersectionInterior = new ArrayList<Edge>();

		status.findIntersectingSegments(p6, intersectionLower, intersectionInterior);

		if(intersectionInterior.isEmpty()){
			System.out.println("No interior intersections found");
		}
		else{
			System.out.println("Interior intersections found. Whyyy?"+"The point is: ");

		}
		if(intersectionLower.isEmpty()){
			System.out.println("No intersections with lower points found. That's odd.");
		}
		else System.out.println("Intersections with lower point found. As it should be.");



		//==================================================================================================================
		//Test av b�da BST:erna
		 * 
		 * 
		 */

		//Bygg en l�nkad lista av Endpoints och Edges

		//En polygon/*
		/*Endpoint p1 = new Endpoint(50, 50);//N�st h�gsta punkten = p1
		Endpoint p2 = new Endpoint(60, 40);//H�gsta punkten = p2
		Edge p1p2 = new Edge(p1, p2);
		p1.setNextSeg(p1p2);
		p2.setPrevSeg(p1p2);

		Endpoint p3 = new Endpoint(70, 70);
		Endpoint p4 = new Endpoint(20, 100);//L�gsta punkten = p4
		Edge p2p3 = new Edge(p2, p3);
		p2.setNextSeg(p2p3);
		p3.setPrevSeg(p2p3);
		Edge p3p4 = new Edge(p3, p4);
		p3.setNextSeg(p3p4);
		p4.setPrevSeg(p3p4);
		Edge p4p1 = new Edge(p4, p1);
		p4.setNextSeg(p4p1);
		p1.setPrevSeg(p4p1);


		eventQueueTest.insert(p1);
		eventQueueTest.insert(p2);
		eventQueueTest.insert(p3);
		eventQueueTest.insert(p4);

		//G�r status utan sweep_y. G�r endast massor d� det �r en upper point. Intersection point kommer inte beh�vas.
		//G�r om alla intersection points till nya uppers. Typ.


		//L�gg till polygon 2 och testa rebalance
		Endpoint p5 = new Endpoint(55, 55);
		Endpoint p6 = new Endpoint(57, 120);
		Endpoint p7 = new Endpoint(65, 30);
		Endpoint p8 = new Endpoint(58, 28);

		Edge p5p6 = new Edge(p5, p6);
		Edge p6p7 = new Edge(p6, p7);
		Edge p7p8 = new Edge(p7, p8);
		Edge p8p5 = new Edge(p8, p5);

		p5.setNextSeg(p5p6);
		p5.setPrevSeg(p7p8);
		p6.setNextSeg(p6p7);
		p6.setPrevSeg(p5p6);
		p7.setNextSeg(p7p8);
		p7.setPrevSeg(p6p7);
		p8.setNextSeg(p8p5);
		p8.setPrevSeg(p7p8);


		//Printa hur tr�det ser ut
		System.out.println("Insert a polygons endpoints");
		eventQueueTest.traverseInOrder();*/



		/*
		 * Punkter utan edges
		 */
		/*Endpoint p9 = new Endpoint(13, 18);
		Endpoint p10 = new Endpoint(12, 17);


		eventQueueTest.insert(p5);
		eventQueueTest.insert(p6);
		eventQueueTest.insert(p7);
		eventQueueTest.insert(p8);
		eventQueueTest.insert(p9);
		eventQueueTest.insert(p10);

		Endpoint p0 = new Endpoint(52, 390);
		Endpoint p11 = new Endpoint(10, 10);

		Endpoint collission = new Endpoint(10, 10);
		eventQueueTest.insert(collission);//Blir ej insatt alls. Kolla i collission-funktionerna om hur detta ska hanteras.
		/*
		 * Olika s�tt:
		 * 1. S�tt den krockande eventpointen i en ny nod som baserat p� �t vilket h�ll den �r p� v�g och om det �r en lower
		 * eller upper osv.
		 * 
		 * 2. Banka samman alla punkter i en och samma punkt, �verf�r egenskaper som lower och segment osv till den befintliga
		 * punkten som d� kan vara b�de lower, upper och intersection.
		 */

		/*eventQueueTest.insert(p11);
		eventQueueTest.insert(p0);
		System.out.println("Insert another polygons event points and rebalance");
		eventQueueTest.traverseInOrder();*/



	/*}

	private static void createGUI(){
		JFrame frame = new JFrame("Computational geometry project");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//Painting panel
		final Gui paintArea = new Gui(); 
		frame.add(paintArea);

		//Controls
		/*JPanel controlPanel = new JPanel();
		frame.add(controlPanel, BorderLayout.SOUTH);*/
		
		
/*
		frame.pack();//Unnecessary when we have components?
		frame.setVisible(true);
	}*/
	
	
	
}



