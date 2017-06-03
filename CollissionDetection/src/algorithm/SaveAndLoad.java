/*package algorithm;

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
	
}*/
