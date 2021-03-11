package turtles;
import drawing.Canvas;
import geometry.CoordinateWithAngle;
import tools.Utils;
import java.util.ArrayList;
import java.util.List;

public class DynamicTurtle extends Turtle {

    private int speed = 100;            // Speed of dynamic turtles
    private int radius = 70;            // Radius of dynamic turtles
    private boolean drawingRadius;      // Boolean for drawing radius or not
    private double angVelocity = 0;     // Angular velocity of dynamic turtles

    public DynamicTurtle(Canvas canvas, int xpos, int ypos) {
        super(canvas, xpos, ypos);
    }

    /*******************************************************************************************************************
     *
     *                                     = Getters and Setters =
     *
     *******************************************************************************************************************/

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setDrawingRadius(boolean drawingRadius) {
        this.drawingRadius = drawingRadius;
    }

    public void setVelocity(int velocity) {
        this.speed = velocity;
    }

    public void setAngVelocity(double angVelocity) {
        this.angVelocity = angVelocity;
    }

    /*******************************************************************************************************************
     *
     *                                          = Methods =
     *
     *******************************************************************************************************************/

    @Override
    public void draw() {


        turn(201.625);
        move(7);
        putPenDown();
        move(-7);
        turn(-43.25);
        move(7);
        putPenUp();
        move(-7);
        turn(-158.375);


        // Draw radius around turtles if enabled
        if (drawingRadius) {

            move(-5.773492);

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

            move(5.773492);

        }
    }

    // Update turtles position and angle with respect to time and wrap/flip position if required
    public void update(double time, boolean WrapOrFlip) {

        // Moves with regard to time
        move(speed * (time / 1000));

        // Turns by angular velocity
        turn(angVelocity * (time / 1000));

        // Wrapping = true, Flip = false
        if (!WrapOrFlip) {
            this.flipPosition(myCanvas.getWidth(), myCanvas.getHeight());
        } else if (WrapOrFlip) {
            this.wrapPosition(myCanvas.getWidth(), myCanvas.getHeight());
        }
    }

    // Finds local average centre and angle of turtles and returns averages [xPos, yPos, Angle]
    public CoordinateWithAngle localCentre(List<FlockTurtle> localTurtles) {

        double xposAverage = 0;     // Average x-pos of local turtles
        double yposAverage = 0;     // "     " for y-pos
        double angAverage = 0;      // Average angle

        if (localTurtles.size() > 0) {
            for (FlockTurtle T : localTurtles) {
                if (T != this) {
                    xposAverage += T.getPositionX();
                    yposAverage += T.getPositionY();
                    angAverage += Utils.checkAngle(T.getAngle());
                }
            }
            // Calculate averages
            xposAverage = (xposAverage) / localTurtles.size();
            yposAverage = (yposAverage) / localTurtles.size();
            angAverage = angAverage / localTurtles.size();
        }
        return new CoordinateWithAngle(xposAverage, yposAverage, angAverage);
    }

    // Search local radius and create list of local turtles
    public List<FlockTurtle> searchRadius(List<FlockTurtle> allTurtles, int searchingRadius) {

        // Create empty list to fill with nearTurtles
        List<FlockTurtle> localTurtles = new ArrayList<FlockTurtle>();

        for (FlockTurtle Turt : allTurtles) {
            if (Turt != this) {

                double xPart = Math.pow((Turt.getPositionX() - this.getPositionX()), 2);
                double yPart = Math.pow((Turt.getPositionY() - this.getPositionY()), 2);
                double xyPart = Math.sqrt(xPart + yPart);

                if (xyPart < searchingRadius) { // https://stackoverflow.com/questions/481144/equation-for-testing-if-a-point-is-inside-a-circle
                    localTurtles.add(Turt);
                }
            }
        }
        return localTurtles;
    }

    // Find relative turning angle (x pos, y pos) when given coordinates
    public double relativeAngle(double xDisplacement, double yDisplacement) {

        double turningAngle = 0;

        // assumes turtles bearing is at 0,0
        if (xDisplacement > 0 && yDisplacement > 0)  // Top-right quadrant
            turningAngle = 90 - Math.toDegrees(Math.atan2(yDisplacement, xDisplacement));

        else if (xDisplacement > 0 && yDisplacement < 0)  // Bottom-right quadrant
            turningAngle = 90 - Math.toDegrees(Math.atan2(yDisplacement, xDisplacement));

        else if (xDisplacement < 0 && yDisplacement > 0)   // Top-left quadrant
            turningAngle = 90 - Math.toDegrees(Math.atan2(yDisplacement, xDisplacement));

        else if (xDisplacement < 0 && yDisplacement < 0)   // Bottom-left quadrant
            turningAngle = -270 - Math.toDegrees(Math.atan2(yDisplacement, xDisplacement));

        if (this.getAngle() < 180)
            turningAngle = turningAngle - this.getAngle();
        else if (this.getAngle() >= 180)
            turningAngle = turningAngle - this.getAngle();


        return turningAngle;
    }
}