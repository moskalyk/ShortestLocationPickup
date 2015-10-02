import java.util.List;

public class PartialRoute {
	public Double distance;
	public List<Point> path;
	
	PartialRoute(Double distance){
		this.distance = distance;
	}
	
	public void addPath(List<Point> path){
		this.path = path;
	}
	
	public Double getDistance(){
		return this.distance;
	}
	
	public List<Point> getPath(){
		return this.path;
	}
}
