package geometry;

public class CartesianCoordinate {

    private double xPosition;   // X-coordinate of cartesian
    private double yPosition;   // Y-coordinate of cartesian

    public CartesianCoordinate(double x, double y) {

        xPosition = x;
        yPosition = y;

    }

    // Get X point
    public double getX() {
        return xPosition;
    }

    // Get Y point
    public double getY() {
        return yPosition;
    }

    // Set X point
    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    // Set Y point
    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }
}