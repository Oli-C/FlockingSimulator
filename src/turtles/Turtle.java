package turtles;
import drawing.Canvas;
import geometry.CartesianCoordinate;
import tools.Utils;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Turtle {

	protected Canvas myCanvas;

	CartesianCoordinate current = new CartesianCoordinate(0.0, 0.0);	// Current turtles (x, y) position
	private boolean drawing;	// Boolean to determine if drawing turtles when moving
	private double angle = 0;	// Degrees counterclockwise from the x-axis giving direction of turtles

	public Turtle(Canvas myCanvas, int xpos, int ypos) {
		this.myCanvas = myCanvas;
		current.setxPosition(xpos);
		current.setyPosition(ypos);
	}

	/*******************************************************************************************************************
	 *
	 *                                     = Getters and Setters =
	 *
	 *******************************************************************************************************************/


	public double getPositionX() {
		return current.getX();
	}

	public double getPositionY() {
		return current.getY();
	}

	public double getAngle(){
		return Utils.checkAngle(Math.toDegrees(this.angle));
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}


	/*******************************************************************************************************************
	 *
	 *                                          = Methods =
	 *
	 *******************************************************************************************************************/


	 // The turtles is moved in its current direction for the given number of pixels.
	 // If the pen is down when the robot moves, a line will be drawn on the floor.
	 // @param step is the the number of pixels to move.

	// Move turtles position
	public void move(double step) {

		CartesianCoordinate nextPos = new CartesianCoordinate(0, 0);

		double X = this.getPositionX();
		double Y = this.getPositionY();

		// Adds to x, y position of the turtles
		nextPos.setxPosition(X += (step * sin(angle)));	// Would be normally cos, but is inverted
		nextPos.setyPosition(Y += (step * cos(angle)));	 // due to how the canvas axis work

		if (drawing) {

			// Draws line on canvas
			myCanvas.drawLineBetweenPoints(current, nextPos);
		}
		current = nextPos;
	}

	// Change angle of turtles
	public void turn(double i) {

		// Adds to angle with integer degrees
		angle += Math.toRadians(i);

	}

	// Places pen up for drawing on canvas
	public void putPenUp() {
		drawing = false;
	}

	// Places pen down for drawing on canvas
	public void putPenDown() {
		drawing = true;
	}

	// Draws turtles
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

	}

	// Undraws turtles (Could remove method as clearCanvas used for removing turtles in game loop)
	public void undraw() {

		int turtle_lines = 2;

		for (int i = 0; i < turtle_lines; i++) {

			myCanvas.removeMostRecentLine();

		}
		myCanvas.repaint();
	}

	// Wraps turtles position to other side of the screen if near screen edge
	public void wrapPosition(int xMax, int yMax){

		if(getPositionX() > xMax){

			current.setxPosition(-10);

		}

		if(getPositionX() < -10){

			current.setxPosition(xMax);

		}

		if(getPositionY() > yMax + 10){

			current.setyPosition(0);

		}

		if(getPositionY() < 0){

			current.setyPosition(yMax + 10);

		}

	}

	// Wraps turtles position to other side of the screen if within range
	public void flipPosition(int xMax, int yMax){

		if(getPositionX() >= xMax + 10){

			this.turn(45);

		}

		if(getPositionX() <= -10){

			this.turn(45);

		}

		if(getPositionY() >= yMax + 10){

			this.turn(45);

		}

		if(getPositionY() <= -10){

			this.turn(45);

		}

	}

}