import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
	public static void main(String[] args)
    {
        //Instantiate all Destination Points 
        Point A = new Point("A", 3.0, 3.0);
        Point B = new Point("B", 18.0, 11.0);
        Point C = new Point("C", 4.0, 8.0);
        Point D = new Point("D", 14.0, 7.0);
        
        //Instantiate all junction points
        Point E = new Point("E", 4.0, 6.0);
        Point F = new Point("F", 9.0, 5.0);
        Point G = new Point("G", 9.0, 9.0);
        Point H = new Point("H", 5.0, 11.0);
        Point I = new Point("I", 9.0, 12.0);
        Point J = new Point("J", 13.0, 13.0);
        Point K = new Point("K", 15.0, 11.0);
        Point L = new Point("L", 13.0, 9.0);
        Point M = new Point("M", 14.0, 5.0);
        Point N = new Point("N", 16.0, 9.0);
        Point O = new Point("O", 17.0, 4.0);
        Point P = new Point("P", 19.0, 8.0);
        System.out.println(computeEdges(A, E));
        System.out.println(computeEdges(E, C));
        System.out.println(computeEdges(C, E));
        System.out.println(computeEdges(E, G));
        System.out.println(computeEdges(G, L));
        System.out.println(computeEdges(L, D));
        System.out.println(computeEdges(D, N));
        System.out.println(computeEdges(N, B));
        
        System.out.println("--------");
        System.out.println(computeEdges(C, E));
        System.out.println(computeEdges(E, A));
        System.out.println(computeEdges(A, E));
        System.out.println(computeEdges(E, G));
        System.out.println(computeEdges(G, K));
        System.out.println(computeEdges(K, B));
        System.out.println(computeEdges(B, N));
        System.out.println(computeEdges(N, D));
        
        // set the edges and weight
        A.adjacencies = new Edge[]{ new Edge(E, computeEdges(A, E)), new Edge(F, computeEdges(A, F)) };
        B.adjacencies = new Edge[]{ new Edge(P, computeEdges(P, B)),new Edge(N, computeEdges(N, B)),new Edge(K, computeEdges(K, B)),new Edge(J, computeEdges(J, B)) };
        C.adjacencies = new Edge[]{ new Edge(H, computeEdges(C, H)),new Edge(E, computeEdges(C, E)) };
        D.adjacencies = new Edge[]{ new Edge(M, computeEdges(M, D)),new Edge(L, computeEdges(L, D)),new Edge(N, computeEdges(N, D)) };
        E.adjacencies = new Edge[]{ new Edge(A, computeEdges(E, A)), new Edge(F, computeEdges(E, F)),new Edge(C, computeEdges(C, E)), new Edge(G, computeEdges(G, E))};
        F.adjacencies = new Edge[]{ new Edge(A, computeEdges(A, F)), new Edge(E, computeEdges(F, E)), new Edge(G, computeEdges(G, F)), new Edge(M, computeEdges(M, F))};
        G.adjacencies = new Edge[]{ new Edge(E, computeEdges(E, G)), new Edge(F, computeEdges(F, G)), new Edge(I, computeEdges(I, G)), new Edge(L, computeEdges(L, G)), new Edge(K, computeEdges(K, G)) };
        H.adjacencies = new Edge[]{ new Edge(C, computeEdges(C, H)), new Edge(I, computeEdges(I, H)) };
        I.adjacencies = new Edge[]{ new Edge(H, computeEdges(H, I)),new Edge(G, computeEdges(G, I)),new Edge(J, computeEdges(J, I)) };
        J.adjacencies = new Edge[]{ new Edge(I, computeEdges(I, J)), new Edge(K, computeEdges(K, J)),new Edge(B, computeEdges(B, J))};
        K.adjacencies = new Edge[]{ new Edge(G, computeEdges(K, G)),new Edge(L, computeEdges(K, L)),new Edge(B, computeEdges(K, B)),new Edge(J, computeEdges(K, J)) };
        L.adjacencies = new Edge[]{ new Edge(D, computeEdges(D, L)), new Edge(K, computeEdges(K, L)), new Edge(N, computeEdges(N, L)), new Edge(G, computeEdges(G, L)) };
        M.adjacencies = new Edge[]{ new Edge(F, computeEdges(F, M)),new Edge(D, computeEdges(D, M)),new Edge(O, computeEdges(O, M)) };
        N.adjacencies = new Edge[]{ new Edge(L, computeEdges(L, N)),new Edge(D, computeEdges(D, N)),new Edge(B, computeEdges(B, N))  };
        O.adjacencies = new Edge[]{ new Edge(M, computeEdges(O, M)),new Edge(P, computeEdges(O, P)) };
        P.adjacencies = new Edge[]{ new Edge(O, computeEdges(P, O)) ,new Edge(B, computeEdges(P, B)) };
        
        PartialRoute A_C;
        PartialRoute C_D;
        PartialRoute D_B;
        
        PartialRoute C_A;
        PartialRoute A_B;
        PartialRoute B_D;
        
        // run Dijkstra
        computePaths(A); 
        A_C = new PartialRoute(C.minDistance);
        A_B = new PartialRoute(B.minDistance);
        
        A_C.addPath(getShortestPath(C));
        A_B.addPath(getShortestPath(B));
        
        A.removeDistances();
        B.removeDistances();
        C.removeDistances();
        D.removeDistances();
        E.removeDistances();
        F.removeDistances();
        G.removeDistances();
        H.removeDistances();
        I.removeDistances();
        J.removeDistances();
        K.removeDistances();
        L.removeDistances();
        M.removeDistances();
        N.removeDistances();
        O.removeDistances();
        P.removeDistances();
        
        // run Dijkstra
        computePaths(C); 
        C_D = new PartialRoute(D.minDistance);
        C_A = new PartialRoute(A.minDistance);
        
        C_D.addPath(getShortestPath(D));
        C_A.addPath(getShortestPath(A));
        
        A.removeDistances();
        B.removeDistances();
        C.removeDistances();
        D.removeDistances();
        E.removeDistances();
        F.removeDistances();
        G.removeDistances();
        H.removeDistances();
        I.removeDistances();
        J.removeDistances();
        K.removeDistances();
        L.removeDistances();
        M.removeDistances();
        N.removeDistances();
        O.removeDistances();
        P.removeDistances();
        
        // run Dijkstra
        computePaths(D); 
        D_B = new PartialRoute(B.minDistance);

        D_B.addPath(getShortestPath(B));
        
        A.removeDistances();
        B.removeDistances();
        C.removeDistances();
        D.removeDistances();
        E.removeDistances();
        F.removeDistances();
        G.removeDistances();
        H.removeDistances();
        I.removeDistances();
        J.removeDistances();
        K.removeDistances();
        L.removeDistances();
        M.removeDistances();
        N.removeDistances();
        O.removeDistances();
        P.removeDistances();
        
     // run Dijkstra
        computePaths(B); 
        B_D = new PartialRoute(D.minDistance);

        B_D.addPath(getShortestPath(D));
        double path1 = computeFullDistance(A_C, C_D, D_B);
        System.out.println("~~~~~~~~~~~~");
        System.out.println(A_C.getDistance());
        System.out.println(C_D.getDistance());
        System.out.println(C_D.getPath());
        System.out.println(D_B.getDistance());
        double path2 = computeFullDistance(C_A, A_B, B_D);
        if((path1 < path2)){
        	System.out.println("Start from A!");
        	List<Point> route = new ArrayList<Point>(A_C.getPath());
        	List<Point> C_DLessEnd = C_D.getPath();
        	C_DLessEnd.remove(0);
        	route.addAll(C_DLessEnd);
        	List<Point> D_BLessEnd = D_B.getPath();
        	D_BLessEnd.remove(0);
        	route.addAll(D_BLessEnd);

        	System.out.println(route);
        	System.out.println("Total Distance: "+path1);
        }
        else{
        	System.out.println("Start from B!");
        	List<Point> route = new ArrayList<Point>(C_A.getPath());
        	
        	List<Point> A_BLessEnd = A_B.getPath();
        	A_BLessEnd.remove(0);
        	route.addAll(A_BLessEnd);
        	List<Point> B_DLessEnd = B_D.getPath();
        	B_DLessEnd.remove(0);
        	route.addAll(B_DLessEnd);

        	System.out.println(route);
        	System.out.println("Total Distance: "+path2);
        }
    }
	
	public static double computeFullDistance(PartialRoute a, PartialRoute b, PartialRoute c ){
		return a.getDistance() + b.getDistance() + c.getDistance();
	}
	
	public static Double computeEdges(Point p1, Point p2){
		return Math.sqrt(Math.pow(Math.abs(p1.getX() - p2.getX()), 2) + Math.pow(Math.abs(p1.getY() - p2.getY()), 2));
	}
	
	public static void computePaths(Point source) {
		source.minDistance = 0.;
		PriorityQueue<Point> pointQueue = new PriorityQueue<Point>();
		pointQueue.add(source);
		while (!pointQueue.isEmpty()) {
			Point u = pointQueue.poll();
			if(u.adjacencies!=null){
				for (Edge e : u.adjacencies) {
	
					Point target = e.target;
					double weight = e.weight;
					double distanceThroughP = weight + u.minDistance;
	
					if (distanceThroughP < target.minDistance) {
						pointQueue.remove(target);
	
						target.minDistance = distanceThroughP;
						target.previous = u;
						pointQueue.add(target);
					}
				}
			}
		}
	}
	
	public static List<Point> getShortestPath(Point target){
		List<Point> path = new ArrayList<Point>();
		
		for(Point point = target; point != null; point = point.previous)
			path.add(point);
		
		Collections.reverse(path);
		return path;
		
	}
}
