package testpackage;

import java.util.TreeMap;

import algorithm.Edge;
import algorithm.Endpoint;

public class TreeMapTest {

	/* 1. Skapa en TreeMap f�r punkter
	 * 2. Sortera punkterna efter y-led (Event queue. Kolla in hur jag gjort innan)
	 * 		2.1 G�r en ny klass f�r punkter?????
	 * 		2.2 Eller bara g�r en bra compareTo-metod f�r detta
	 * 
	 * 3. G�r en TreeMap f�r linjer
	 * 4. Se till att alla linjer kan j�mf�ras och sorteras beroende p� nuvarande sweep_y
	 * 5. Testa med n�got exempel 
	 * 
	 */
	
	
	private TreeMap<Integer, Endpoint> eventQueue;
	private TreeMap<Integer, Edge> treeMap;
	
	public TreeMapTest(){
		
	}
	
	
}
