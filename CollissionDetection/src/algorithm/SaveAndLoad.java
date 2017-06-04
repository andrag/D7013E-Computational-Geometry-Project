package algorithm;

import java.awt.Color;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import datastructures.AVLTree;

public class SaveAndLoad {
	
	public static ArrayList<Endpoint> polygon1;
	public static ArrayList<Endpoint> polygon2;
	

	public static void saveEventQueue(AVLTree eventQueue){
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss");
			Date date = new Date();
			String filename = "Log_" + dateFormat.format(date) + ".ser";
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(eventQueue);
			oos.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static AVLTree loadEventQueue(String filename){
		AVLTree result = new AVLTree();
		try{
			FileInputStream fis = new FileInputStream(filename);
			ObjectInputStream ois = new ObjectInputStream(fis);
			result = (AVLTree) ois.readObject();
			ois.close();
			
		}
		catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void savePolygons(Endpoint startpoint1, Endpoint startpoint2)
	{
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss");
			Date date = new Date();
			String filename = "Polygons_" + dateFormat.format(date) + ".ser";
			FileOutputStream fos = new FileOutputStream(filename);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			
			
			Endpoint current = startpoint1;
			while(current.getNextSeg().getEnd() != startpoint1){
				oos.writeObject(current);
				current = current.getNextSeg().getEnd();
			}
			current = current.getNextSeg().getEnd();
			oos.writeObject(current);
			
			current = startpoint2;
			while(current.getNextSeg().getEnd() != startpoint2){
				oos.writeObject(current);
				current = current.getNextSeg().getEnd();
			}
			current = current.getNextSeg().getEnd();
			oos.writeObject(current);
			
			oos.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public static void loadPolygons(String loadFile)
	{
		polygon1 = new ArrayList<Endpoint>();
		polygon2 = new ArrayList<Endpoint>();
		Endpoint startpoint1;
		Endpoint startpoint2;
		
		ObjectInputStream ois = null;
		
		try{
			FileInputStream fis = new FileInputStream(loadFile);
			ois = new ObjectInputStream(fis);
			startpoint1 = (Endpoint) ois.readObject();
			Endpoint current = startpoint1;
			polygon1.add(startpoint1);
			
			while((current = (Endpoint) ois.readObject()) != null) //EOF!!
			{
				if(current.getBelonging() == 1)
				{
					polygon1.add(current);
				}
				else if(current.getBelonging() == 2)
				{
					polygon2.add(current);
				}
			}
			ois.close();
		}
		catch (EOFException e){
			try {
				ois.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
