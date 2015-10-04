import java.util.ArrayList;
import java.util.Iterator;

public class NodeSet implements Iterable<Point>{
	//
	private ArrayList<Point> pointList = new ArrayList<Point>();
	NodeSet(){
		//Instantiate all Driver points
		pointList.add(new Point("A", 3.0, 3.0));
		pointList.add(new Point("B", 18.0, 11.0));
		pointList.add(new Point("C", 4.0, 8.0));
		pointList.add(new Point("D", 14.0, 7.0));
		
		//Instantiate all junction points
		pointList.add(new Point("E", 4.0, 6.0));
		pointList.add(new Point("F", 9.0, 5.0));
		pointList.add(new Point("G", 9.0, 9.0));
		pointList.add(new Point("H", 5.0, 11.0));
		pointList.add(new Point("I", 9.0, 12.0));
		pointList.add(new Point("J", 13.0, 13.0));
		pointList.add(new Point("K", 15.0, 11.0));
		pointList.add(new Point("L", 13.0, 9.0));
		pointList.add(new Point("M", 14.0, 5.0));
		pointList.add(new Point("N", 16.0, 9.0));
		pointList.add(new Point("O", 17.0, 4.0));
		pointList.add(new Point("P", 19.0, 8.0));
	}
	NodeSet(ArrayList<Point> pointList){
		this.pointList = pointList;
	}
	
	
	@Override
	public Iterator<Point> iterator() {
		return pointList.iterator();
	}
}
