import java.util.ArrayList;

public class Point implements Comparable<Point>{
	public String name;
	public double x;
	public double y;
	public Edge[] adjacencies;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Point previous = null;
	public ArrayList<Edge> list = new ArrayList<Edge>();
	
	Point(String name){
		this.name = name;
	}
	
	Point( double x, double y){

		this.x = x;
		this.y = y;
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
	public void removeDistances(){
		this.previous = null;
		this.minDistance = Double.POSITIVE_INFINITY;
	}
	public void addAdjacency(Edge e){
		this.list.add(e);
	}

}
