package geometry;

public class LineSegment {
	
	private CartesianCoordinate start_point;
    private CartesianCoordinate end_point;
    
    public LineSegment(CartesianCoordinate startpoint, CartesianCoordinate endpoint){
    	
    	start_point = startpoint;
    	end_point = endpoint;
    }

    public CartesianCoordinate getStart() {
		return start_point;
	}

	public void setStart(CartesianCoordinate start_point) {
		this.start_point = start_point;
	}

	public CartesianCoordinate getEnd() {
		return end_point;
	}

	public void setEnd(CartesianCoordinate end_point) {
		this.end_point = end_point;
	}
    
    public String PrintLineSeg(){
	   
	   return ("Start of line:" + getStart() + "End of Line:" + getEnd());
    }
    
}