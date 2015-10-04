import java.util.ArrayList;
import java.util.Iterator;

public class NodeSet implements Iterable<Point> {
	
	private ArrayList<Point> pointList = new ArrayList<Point>();

	NodeSet() {
		// Instantiate all Driver points
		pointList.add(new Point(3.0, 3.0, "A"));
		pointList.add(new Point(18.0, 11.0, "B"));
		pointList.add(new Point(4.0, 8.0, "C"));
		pointList.add(new Point(14.0, 7.0, "D"));

		// Instantiate all junction points
		pointList.add(new Point(4.0, 6.0, "E"));
		pointList.add(new Point(9.0, 5.0, "F"));
		pointList.add(new Point(9.0, 9.0, "G"));
		pointList.add(new Point(5.0, 11.0, "H"));
		pointList.add(new Point(9.0, 12.0, "I"));
		pointList.add(new Point(13.0, 13.0, "J"));
		pointList.add(new Point(15.0, 11.0, "K"));
		pointList.add(new Point(13.0, 9.0, "L"));
		pointList.add(new Point(14.0, 5.0, "M"));
		pointList.add(new Point(16.0, 9.0, "N"));
		pointList.add(new Point(17.0, 4.0, "O"));
		pointList.add(new Point(19.0, 8.0, "P"));
	}

	NodeSet(ArrayList<Point> pointList) {
		this.pointList = pointList;
	}

	@Override
	public Iterator<Point> iterator() {
		return pointList.iterator();
	}
}
