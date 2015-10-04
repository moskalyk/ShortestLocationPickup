import java.util.List;

public class PartialRoute {
	private Double distance;
	private List<Point> path;
	
	PartialRoute(Double distance){
		this.distance = distance;
	}
	
	public void setPath(List<Point> path){
		this.path = path;
	}
	
	public Double getDistance(){
		return this.distance;
	}
	
	public List<Point> getPath(){
		return this.path;
	}
}
