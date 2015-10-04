import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;

public class DijkstraToolKit {

	public static double computeFullDistance(PartialRoute a, PartialRoute b, PartialRoute c ){
		return a.getDistance() + b.getDistance() + c.getDistance();
	}
	
	public static Double computeEdges(Point p1, Point p2){
		return Math.sqrt(Math.pow(Math.abs(p1.getX() - p2.getX()), 2) + Math.pow(Math.abs(p1.getY() - p2.getY()), 2));
	}
	
	public static void computePaths(Point source) {
		source.setMinimumDistance(0.0);
		PriorityQueue<Point> pointQueue = new PriorityQueue<Point>();
		pointQueue.add(source);
		while (!pointQueue.isEmpty()) {
			Point u = pointQueue.poll();
			if(u.getAdjacencies()!=null){
				for (Edge e : u.getAdjacencies()) {
	
					Point target = e.getTarget();
					double weight = e.getWeight();
					double distanceThroughP = weight + u.getMinimumDistance();
	
					if (distanceThroughP < target.getMinimumDistance()) {
						pointQueue.remove(target);
	
						target.setMinimumDistance(distanceThroughP);
						target.setPrevious(u);
						pointQueue.add(target);
					}
				}
			}
		}
	}
	
	public static List<Point> getShortestPath(Point target){
		List<Point> path = new ArrayList<Point>();
		
		for(Point point = target; point != null; point = point.getPrevious())
			path.add(point);
		
		Collections.reverse(path);
		return path;
		
	}
}
