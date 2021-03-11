package turtles;

import drawing.Canvas;
import geometry.CoordinateWithAngle;
import tools.Utils;

import java.util.List;

public class PredatorTurtle extends DynamicTurtle {

    private double Cohesion = 0.5;                    // Cohesion coefficient used for hunting
    private CoordinateWithAngle localCentre;    // Local centre averages variable [local x, local y, local angle]
    private boolean drawingRadius;              // Boolean to determine when to draw the radius
    private int radius = 100;                    // Radius of turtles
    private int eatingRadius = 10;              // Radius where birds are consumed

    public PredatorTurtle(Canvas canvas, int xpos, int ypos) {
        super(canvas, xpos, ypos);
    }

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

    public boolean isDrawingRadius() {
        return drawingRadius;
    }

    public void setDrawingRadius(boolean drawingRadius) {
        this.drawingRadius = drawingRadius;
    }

    /*******************************************************************************************************************
     *
     *                                          = Methods =
     *
     *******************************************************************************************************************/

    // Scan whole list of turts, for each turt check if displacemnt close, remove if close enough
    public void eatTurts(List<FlockTurtle> eatingTurts, int eatingRadius) {


        synchronized (eatingTurts) {
            for (int i = 0; i <= eatingTurts.size() - 1; i++) {

                double xPart = Math.pow((eatingTurts.get(i).getPositionX() - this.getPositionX()), 2);
                double yPart = Math.pow((eatingTurts.get(i).getPositionY() - this.getPositionY()), 2);
                double xyPart = Math.sqrt(xPart + yPart);

                if (xyPart < eatingRadius) {
                    eatingTurts.remove(eatingTurts.get(i));

                }
            }
        }
    }

    @Override
    public void draw() {

        // Draws equilateral triangle

        putPenDown();
        turn(-150);
        move(20);
        turn(-120);
        move(20);
        turn(-120);
        move(20);
        turn(30);
        move(-30);
        move(30);
        putPenUp();

        // Draw radius around turtles if enabled
        if (drawingRadius) {

            turn(-90);
            move(radius);
            turn(90);

            for (int i = 0; i < 10; i++) {
                move(18 * (2 * Math.PI * radius) / 360);
                turn(18);
                putPenDown();
                move(18 * (2 * Math.PI * radius) / 360);
                turn(18);
                putPenUp();
            }

            turn(-90);
            move(-radius);
            turn(90);

        }
    }

    // Function for predator to hunt turtles within radius of predator
    public void Hunt(List<FlockTurtle> Allturtles) {

        boolean hunting = false;

        eatTurts(Allturtles, this.eatingRadius);

        // search radius for near turtles
        List<FlockTurtle> localTurtles = searchRadius(Allturtles, this.getRadius());

        if (localTurtles.size() != 0)
            hunting = true;

        if (hunting) {

            double flockingAngVel = 0;

            localCentre = localCentre(localTurtles);

            flockingAngVel += Cohesion * relativeAngle(localCentre.getX(), localCentre.getY());


            setAngVelocity(flockingAngVel * 200);

        } else if (Math.random() <= 0.04) {

            setAngVelocity(Utils.randNom(-4000, 4000));
        }
    }
}
