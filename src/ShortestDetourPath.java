import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ShortestDetourPath {
	
	//Set to True if you want true optimal path, False for the longer Detour route
	ShortestDetourPath(boolean findOptimalPath){
		
		//Detour #1: Partial routes
        PartialRoute A_C;
        PartialRoute C_D;
        PartialRoute D_B;
        
        //Detour #2: Partial routes
        PartialRoute C_A;
        PartialRoute A_B;
        PartialRoute B_D;
		
        //Instantiate all Destination Points 
		HashMap<Character, Point> pointHash = createPointHashMap();
		
		//Set the edges and weight to create a graph
		generateEdges(pointHash);
		
        // Run Dijkstra from A to calculate sub routes from A to C, and A to B
        DijkstraToolKit.computePaths(pointHash.get('A')); 
        
        //Assign partial route weights from Node A to be compared later
        A_C = new PartialRoute(pointHash.get('C').getMinimumDistance());
        A_B = new PartialRoute(pointHash.get('B').getMinimumDistance());
        
        A_C.setPath(DijkstraToolKit.getShortestPath(pointHash.get('C')));
        A_B.setPath(DijkstraToolKit.getShortestPath(pointHash.get('B')));
        
        //Reset all distances to Infinity
        removeAllDistances(pointHash);
        
        // Run Dijkstra from C to calculate sub routes from C to D, and C to D
        DijkstraToolKit.computePaths(pointHash.get('C')); 
        
        //Assign partial route weights from Node C to be compared later
        C_D = new PartialRoute(pointHash.get('D').getMinimumDistance());
        C_A = new PartialRoute(pointHash.get('A').getMinimumDistance());
        
        C_D.setPath(DijkstraToolKit.getShortestPath(pointHash.get('D')));
        C_A.setPath(DijkstraToolKit.getShortestPath(pointHash.get('A')));
        
        //Reset all distances to Infinity
        removeAllDistances(pointHash);
        
        // Run Dijkstra from C to calculate sub routes from D to B
        DijkstraToolKit.computePaths(pointHash.get('D')); 
        
        //Assign partial route weights from Node D to be compared later
        D_B = new PartialRoute(pointHash.get('B').getMinimumDistance());

        D_B.setPath(DijkstraToolKit.getShortestPath(pointHash.get('B')));
        
        removeAllDistances(pointHash);
        
        // Run Dijkstra from B to calculate sub routes from B to D
        DijkstraToolKit.computePaths(pointHash.get('B')); 
        
        //Assign partial route weights from Node B to be compared later
        B_D = new PartialRoute(pointHash.get('D').getMinimumDistance());
        
        //Add the optimal node path
        B_D.setPath(DijkstraToolKit.getShortestPath(pointHash.get('D')));
        
        //Sum up the detour paths to be compared
        double detourForA = DijkstraToolKit.computeFullDistance(A_C, C_D, D_B);
        double detourForB = DijkstraToolKit.computeFullDistance(C_A, A_B, B_D);
      
        
        if(findOptimalPath && (detourForA < detourForB)){
        	System.out.println("Start from A!");
        	
        	//Join partial paths from A to C, C to D, and D to B.
        	List<Point> route = new ArrayList<Point>(A_C.getPath());
        	List<Point> C_DLessEnd = C_D.getPath();
        	
        	//removal of common node between C and D
        	C_DLessEnd.remove(0);
        	route.addAll(C_DLessEnd);
        	List<Point> D_BLessEnd = D_B.getPath();
        	
        	//removal of common node between D and B
        	D_BLessEnd.remove(0);
        	route.addAll(D_BLessEnd);

        	System.out.println(route);
        	System.out.println("Total Distance: " + detourForA);
        }
        else{
        	System.out.println("Start from B!");
        	
        	//Join partial paths from C to A, A to B, and B to D.
        	List<Point> route = new ArrayList<Point>(C_A.getPath());
        	List<Point> A_BLessEnd = A_B.getPath();
        	
        	//removal of common node between A and B
        	A_BLessEnd.remove(0);
        	route.addAll(A_BLessEnd);
        	List<Point> B_DLessEnd = B_D.getPath();
        	
        	//removal of common node between B and D
        	B_DLessEnd.remove(0);
        	route.addAll(B_DLessEnd);

        	System.out.println(route);
        	System.out.println("Total Distance: " + detourForB);
        }
    }
	
	private void generateEdges(HashMap<Character, Point> pointHash) {
		//This can be done more automated in the future, when a GUI is finished
		pointHash.get('A').setAdjacencies(new Edge[]{ new Edge(pointHash.get('E'), DijkstraToolKit.computeEdges(pointHash.get('A'), pointHash.get('E'))), new Edge(pointHash.get('F'), DijkstraToolKit.computeEdges(pointHash.get('A'), pointHash.get('F'))) });
		pointHash.get('B').setAdjacencies(new Edge[]{ new Edge(pointHash.get('P'), DijkstraToolKit.computeEdges(pointHash.get('P'), pointHash.get('B'))), new Edge(pointHash.get('N'), DijkstraToolKit.computeEdges(pointHash.get('N'), pointHash.get('B'))),new Edge(pointHash.get('K'), DijkstraToolKit.computeEdges(pointHash.get('K'), pointHash.get('B'))),new Edge(pointHash.get('J'), DijkstraToolKit.computeEdges(pointHash.get('J'), pointHash.get('B'))) });
		pointHash.get('C').setAdjacencies(new Edge[]{ new Edge(pointHash.get('H'), DijkstraToolKit.computeEdges(pointHash.get('C'), pointHash.get('H'))), new Edge(pointHash.get('E'), DijkstraToolKit.computeEdges(pointHash.get('C'), pointHash.get('E'))) });
		pointHash.get('D').setAdjacencies(new Edge[]{ new Edge(pointHash.get('M'), DijkstraToolKit.computeEdges(pointHash.get('M'), pointHash.get('D'))), new Edge(pointHash.get('L'), DijkstraToolKit.computeEdges(pointHash.get('L'), pointHash.get('D'))),new Edge(pointHash.get('N'), DijkstraToolKit.computeEdges(pointHash.get('N'), pointHash.get('D'))) });
		pointHash.get('E').setAdjacencies(new Edge[]{ new Edge(pointHash.get('A'), DijkstraToolKit.computeEdges(pointHash.get('E'), pointHash.get('A'))), new Edge(pointHash.get('F'), DijkstraToolKit.computeEdges(pointHash.get('E'), pointHash.get('F'))),new Edge(pointHash.get('C'), DijkstraToolKit.computeEdges(pointHash.get('C'), pointHash.get('E'))), new Edge(pointHash.get('G'), DijkstraToolKit.computeEdges(pointHash.get('G'), pointHash.get('E')))});
		pointHash.get('F').setAdjacencies(new Edge[]{ new Edge(pointHash.get('A'), DijkstraToolKit.computeEdges(pointHash.get('A'), pointHash.get('F'))), new Edge(pointHash.get('E'), DijkstraToolKit.computeEdges(pointHash.get('F'), pointHash.get('E'))), new Edge(pointHash.get('G'), DijkstraToolKit.computeEdges(pointHash.get('G'), pointHash.get('F'))), new Edge(pointHash.get('M'), DijkstraToolKit.computeEdges(pointHash.get('M'), pointHash.get('F')))});
		pointHash.get('G').setAdjacencies(new Edge[]{ new Edge(pointHash.get('E'), DijkstraToolKit.computeEdges(pointHash.get('E'), pointHash.get('G'))), new Edge(pointHash.get('F'), DijkstraToolKit.computeEdges(pointHash.get('F'), pointHash.get('G'))), new Edge(pointHash.get('I'), DijkstraToolKit.computeEdges(pointHash.get('I'), pointHash.get('G'))), new Edge(pointHash.get('L'), DijkstraToolKit.computeEdges(pointHash.get('L'), pointHash.get('G'))), new Edge(pointHash.get('K'), DijkstraToolKit.computeEdges(pointHash.get('K'), pointHash.get('G'))) });
		pointHash.get('H').setAdjacencies(new Edge[]{ new Edge(pointHash.get('C'), DijkstraToolKit.computeEdges(pointHash.get('C'), pointHash.get('H'))), new Edge(pointHash.get('I'), DijkstraToolKit.computeEdges(pointHash.get('I'), pointHash.get('H'))) });
		pointHash.get('I').setAdjacencies(new Edge[]{ new Edge(pointHash.get('H'), DijkstraToolKit.computeEdges(pointHash.get('H'), pointHash.get('I'))), new Edge(pointHash.get('G'), DijkstraToolKit.computeEdges(pointHash.get('G'), pointHash.get('I'))),new Edge(pointHash.get('J'), DijkstraToolKit.computeEdges(pointHash.get('J'), pointHash.get('I'))) });
		pointHash.get('J').setAdjacencies(new Edge[]{ new Edge(pointHash.get('I'), DijkstraToolKit.computeEdges(pointHash.get('I'), pointHash.get('J'))), new Edge(pointHash.get('K'), DijkstraToolKit.computeEdges(pointHash.get('K'), pointHash.get('J'))),new Edge(pointHash.get('B'), DijkstraToolKit.computeEdges(pointHash.get('B'), pointHash.get('J')))});
		pointHash.get('K').setAdjacencies(new Edge[]{ new Edge(pointHash.get('G'), DijkstraToolKit.computeEdges(pointHash.get('K'), pointHash.get('G'))), new Edge(pointHash.get('L'), DijkstraToolKit.computeEdges(pointHash.get('K'), pointHash.get('L'))),new Edge(pointHash.get('B'), DijkstraToolKit.computeEdges(pointHash.get('K'), pointHash.get('B'))),new Edge(pointHash.get('J'), DijkstraToolKit.computeEdges(pointHash.get('K'), pointHash.get('J'))) });
		pointHash.get('L').setAdjacencies(new Edge[]{ new Edge(pointHash.get('D'), DijkstraToolKit.computeEdges(pointHash.get('D'), pointHash.get('L'))), new Edge(pointHash.get('K'), DijkstraToolKit.computeEdges(pointHash.get('K'), pointHash.get('L'))), new Edge(pointHash.get('N'), DijkstraToolKit.computeEdges(pointHash.get('N'), pointHash.get('L'))), new Edge(pointHash.get('G'), DijkstraToolKit.computeEdges(pointHash.get('G'), pointHash.get('L'))) });
		pointHash.get('M').setAdjacencies(new Edge[]{ new Edge(pointHash.get('F'), DijkstraToolKit.computeEdges(pointHash.get('F'), pointHash.get('M'))), new Edge(pointHash.get('D'), DijkstraToolKit.computeEdges(pointHash.get('D'), pointHash.get('M'))),new Edge(pointHash.get('O'), DijkstraToolKit.computeEdges(pointHash.get('O'), pointHash.get('M'))) });
		pointHash.get('N').setAdjacencies(new Edge[]{ new Edge(pointHash.get('L'), DijkstraToolKit.computeEdges(pointHash.get('L'), pointHash.get('N'))), new Edge(pointHash.get('D'), DijkstraToolKit.computeEdges(pointHash.get('D'), pointHash.get('N'))),new Edge(pointHash.get('B'), DijkstraToolKit.computeEdges(pointHash.get('B'), pointHash.get('N')))  });
		pointHash.get('O').setAdjacencies(new Edge[]{ new Edge(pointHash.get('M'), DijkstraToolKit.computeEdges(pointHash.get('O'), pointHash.get('M'))), new Edge(pointHash.get('P'), DijkstraToolKit.computeEdges(pointHash.get('O'), pointHash.get('P'))) });
		pointHash.get('P').setAdjacencies(new Edge[]{ new Edge(pointHash.get('O'), DijkstraToolKit.computeEdges(pointHash.get('P'), pointHash.get('O'))) ,new Edge(pointHash.get('B'), DijkstraToolKit.computeEdges(pointHash.get('P'), pointHash.get('B'))) });
	}

	private static void removeAllDistances(HashMap<Character, Point> pointHash){
		//Sets all distances back to infinity, to prepare for next Djisktra's calculation
		for(Point point : pointHash.values())
			point.removeDistances();
	}
	
	private static HashMap<Character, Point> createPointHashMap() {
		
		NodeSet nodes = new NodeSet();
		Iterator<Point> pointIterator = nodes.iterator();
		HashMap<Character, Point> hash = new HashMap<Character, Point>();
		
		//Starting each node at 'A'
		int asciiPOIs = 65;
		while(pointIterator.hasNext())
			hash.put(new Character((char)(asciiPOIs++)), pointIterator.next());
		
		return hash;
	}
}
