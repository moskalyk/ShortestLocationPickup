import java.util.ArrayList;

public class Point implements Comparable<Point>{
	public String name;
	public double x;
	public double y;
	public Edge[] adjacencies;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Point previous = null;
	
	Point(String name){
		this.name = name;
	}
	
	Point(String name, double x, double y){
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public int compareTo(Point other){
		return Double.compare(minDistance, other.minDistance);
	}
	
	public double getX(){
		return this.x;
	}
	
	public double getY(){
		return this.y;
	}
	public String toString(){
		return this.name;
	}
	public void removeEdges(){
		this.previous = null;
		this.adjacencies = null;
		this.minDistance = Double.POSITIVE_INFINITY;
	}

}
