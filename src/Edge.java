

public class Edge {
	private final Point target;
	private final double weight;

	public Edge(Point target, double weight) {
		this.target = target;
		this.weight = weight;
	}
	
	public Point getTarget(){
		return this.target;
	}
	public double getWeight(){
		return this.weight;
	}
}
