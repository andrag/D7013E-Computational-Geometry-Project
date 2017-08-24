package algorithm;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import datastructures.AVLTree;


/**
 * Class for saving and loading polygons to/from text files
 * Used for recreating known problems during debugging.
 */

public class SaveAndLoad {

	public static ArrayList<Integer> polygon1;
	public static ArrayList<Integer> polygon2;


	public static void savePolygonsAsText(Endpoint startpoint1, Endpoint startpoint2)
	{
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH-mm-ss");
			Date date = new Date();
			String filename = "Polygons_" + dateFormat.format(date) + ".txt";
			FileWriter writer = new FileWriter(filename);


			Endpoint current = startpoint1;
			while(current.getNextSeg().getEnd() != startpoint1){
				String strToWrite = current.getX() + "," + current.getY() + ",";
				writer.write(strToWrite);
				current = current.getNextSeg().getEnd();
			}

			String strToWrite = current.getX() + "," + current.getY() + ":";
			writer.write(strToWrite);

			current = startpoint2;
			while(current.getNextSeg().getEnd() != startpoint2){
				strToWrite = current.getX() + "," + current.getY() + ",";
				writer.write(strToWrite);
				current = current.getNextSeg().getEnd();
			}

			strToWrite = current.getX() + "," + current.getY();
			writer.write(strToWrite);

			writer.close();
		}
		catch (IOException e){
			e.printStackTrace();
		}
	}


	public static void loadPolygonsFromTxt(String filename)
	{
		polygon1 = new ArrayList<Integer>();
		polygon2 = new ArrayList<Integer>();

		BufferedReader in = null;
		try{
			FileReader reader = new FileReader(filename);
			StringBuilder result = new StringBuilder();

			in = new BufferedReader(reader);
			
			String read = null;
			while((read = in.readLine()) != null)
			{
				result.append(read);
			}
			
			String resultStr = result.toString();
			String[] firstDivide = resultStr.split(":");
			String[] polStr1 = firstDivide[0].split(",");
			String[] polStr2 = firstDivide[1].split(",");
			
			for(String s : polStr1)
			{
				polygon1.add(Integer.parseInt(s));
			}
			for(String s : polStr2)
			{
				polygon2.add(Integer.parseInt(s));
			}
		}
		catch (IOException e) {
			System.out.println("There was a problem: " + e);
			e.printStackTrace();
		} finally {
			try {
				in.close();
			} catch (Exception e) {}	
		}
	}
}