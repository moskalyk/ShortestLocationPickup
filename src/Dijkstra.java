import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class Dijkstra {
	public static void main(String[] args)
    {
        // mark all the vertices 
        Point A = new Point("A", 1.0, 2.0);
        Point B = new Point("B", 6.0, 10.0);
        Point C = new Point("C", 2.0, 6.0);
        Point D = new Point("D", 4.0, 4.0);
        
        // set the edges and weight
        A.adjacencies = new Edge[]{ new Edge(C, computeEdges(A, C)) };
        C.adjacencies = new Edge[]{ new Edge(D, computeEdges(C, D)) };
        D.adjacencies = new Edge[]{ new Edge(B, computeEdges(D, B)) };

        computePaths(A); // run Dijkstra
        Double path1 = B.minDistance;
        System.out.println("Distance to " + B + ": " + path1);
        List<Point> pathA = getShortestPath(B);
        System.out.println("Path From A " + pathA);
        
        A.removeEdges();
        B.removeEdges();
        C.removeEdges();
        D.removeEdges();
        
        C.adjacencies = new Edge[]{ new Edge(A, computeEdges(A, C)) };
        A.adjacencies = new Edge[]{ new Edge(B, computeEdges(A, B)) };
        B.adjacencies = new Edge[]{ new Edge(D, computeEdges(D, B)) };
        
        computePaths(C); // run Dijkstra
        Double path2 = D.minDistance;
        System.out.println("Distance to " + D + ": " + path2);
        List<Point> pathD = getShortestPath(D);
        System.out.println("Path From C " + pathD);
        
        if(path1<path2){
        	System.out.println("Start from A!");
        }
        else{
        	System.out.println("Start from B!");
        }
    }
	
	public static Double computeEdges(Point p1, Point p2){
		return Math.sqrt(Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY()));
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
