package turtles;
import drawing.Canvas;
import geometry.CoordinateWithAngle;
import tools.Utils;
import java.util.List;


public class FlockTurtle extends DynamicTurtle {

    public FlockTurtle(Canvas canvas, int xpos, int ypos) {
        super(canvas, xpos, ypos);
    }

    private double Alignment;                       // Alignment coefficient
    private double Cohesion;                        // Cohesion coefficient
    private double Separation;                      // Separation coefficient
    private double angleAlignment;                  // Alignment angle of Flock Turtle
    private CoordinateWithAngle localCentre;        // Local centre averages variable [local x, local y, local angle]

    /*******************************************************************************************************************
     *
     *                                     = Getters and Setters =
     *
     *******************************************************************************************************************/

    public double getCohesion() {
        return Cohesion;
    }

    public void setCohesion(double cohesion) {
        Cohesion = cohesion;
    }

    public double getAlignment() {
        return Alignment;
    }

    public void setAlignment(double alignment) {
        this.Alignment = alignment;
    }

    public double getSeperation() {
        return Separation;
    }

    public void setSeperation(double seperation) {
        Separation = seperation;
    }

    /*******************************************************************************************************************
     *
     *                                          = Methods =
     *
     *******************************************************************************************************************/

    // Method for making turtles flock with intensities of flocking parameteres
    public void Flock(List<FlockTurtle> Allturtles) {

        boolean flocking = false;

        // search radius for near turtles
        List<FlockTurtle> localTurtles = searchRadius(Allturtles, this.getRadius());

        if ((Separation != 0 || Cohesion != 0 || Alignment != 0) && localTurtles.size() != 0)
            flocking = true;

        if (flocking) {

            double flockingAngVel = 0;

            localCentre = localCentre(localTurtles);

            if (Alignment > 0) {

                alignAngle(localTurtles);

                flockingAngVel += (Alignment / 100) * (angleAlignment);
            }

            if (Cohesion > 0) {

                flockingAngVel += (Cohesion / 100) * relativeAngle(localCentre.getX(), localCentre.getY());
            }

            if (Separation > 0) {

                flockingAngVel += -1 * (Separation / 100) * (relativeAngle(localCentre.getX(), localCentre.getY()) - 180) ;
            }

            setAngVelocity(flockingAngVel * 20);

        } else if (Math.random() <= 0.04) {

            setAngVelocity(Utils.randNom(-4000, 4000));
        }
    }

    // Calculate angle of alignment for local turtles
    public void alignAngle(List<FlockTurtle> localTurtles) {

        angleAlignment = 0;

        if (localTurtles.size() > 0) {
            for (FlockTurtle T : localTurtles) {

                // Local turtle angle is greater than current turtle
                if (T.getAngle() > this.getAngle()) {

                    angleAlignment += T.getAngle() - this.getAngle();
                }

                // Local turtle angle is less than current turtle
                if (T.getAngle() < this.getAngle()) {

                    angleAlignment -= this.getAngle() - T.getAngle();
                }
            }
        }
    }
}