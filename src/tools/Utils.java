package tools;

public class Utils {
	// Returns random int between min and max value
	public static int randNom(int min, int max){

		int range = (max - min) + 1;

		int random_number = (int)(Math.random() * range) + min;

		return random_number;
	}

	// Check and change so angle is always between -180 and 180 (360 range)
	public static double checkAngle(double angle){

		while(angle > 180.0) {
			angle -= 360.0;
		}

		while(angle < -179.0) {
			angle += 360.0;
		}

		return angle;
	}
}