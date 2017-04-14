package testpackage;

import java.util.TreeMap;

import algorithm.Edge;
import algorithm.Endpoint;

public class TreeMapTest {

	/* 1. Skapa en TreeMap för punkter
	 * 2. Sortera punkterna efter y-led (Event queue. Kolla in hur jag gjort innan)
	 * 		2.1 Gör en ny klass för punkter?????
	 * 		2.2 Eller bara gör en bra compareTo-metod för detta
	 * 
	 * 3. Gör en TreeMap för linjer
	 * 4. Se till att alla linjer kan jämföras och sorteras beroende på nuvarande sweep_y
	 * 5. Testa med något exempel 
	 * 
	 */
	
	
	private TreeMap<Integer, Endpoint> eventQueue;
	private TreeMap<Integer, Edge> treeMap;
	
	public TreeMapTest(){
		
	}
	
	
}
