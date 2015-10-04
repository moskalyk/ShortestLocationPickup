
public class Point implements Comparable<Point> {
	private String name;
	private double x;
	private double y;
	private Edge[] adjacencies;
	private double minDistance = Double.POSITIVE_INFINITY;
	private Point previous = null;

	Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	Point(double x, double y, String name) {
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public int compareTo(Point other) {
		return Double.compare(minDistance, other.minDistance);
	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

	public String toString() {
		return this.name;
	}

	public void removeDistances() {
		this.previous = null;
		this.minDistance = Double.POSITIVE_INFINITY;
	}

	public void setAdjacencies(Edge[] adjacencies) {
		this.adjacencies = adjacencies;
	}

	public Edge[] getAdjacencies() {
		return this.adjacencies;
	}

	public void setMinimumDistance(double minDistance) {
		this.minDistance = minDistance;
	}

	public double getMinimumDistance() {
		return this.minDistance;
	}

	public void setPrevious(Point previous) {
		this.previous = previous;
	}

	public Point getPrevious() {
		return this.previous;
	}

}
