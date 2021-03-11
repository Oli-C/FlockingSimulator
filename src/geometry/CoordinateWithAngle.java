package geometry;

public class CoordinateWithAngle extends CartesianCoordinate {

    private double angle;   // Angle of cartesian

    public CoordinateWithAngle(double x, double y, double a){
        super(x,y);
        a = angle;
    }

    public double getAngle(){
        return angle;
    }

    public void setAngle(double a){
        this.angle = a;
    }
}
