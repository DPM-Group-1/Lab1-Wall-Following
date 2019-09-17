package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class PController extends UltrasonicController {

  private static final int MOTOR_SPEED = 140; // Default motor angular speed (deg/s)
  private static final int MAX_DELTA = 65; // Half of the maximum angular speed differential between the two motors.

  public PController() {
    LEFT_MOTOR.setSpeed(MOTOR_SPEED); // Initialize motor rolling forward.
    RIGHT_MOTOR.setSpeed(MOTOR_SPEED);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  @Override
  public void processUSData(int distance) {
    filter(distance);
   differentialFilter(distance);

    int error = distance - BAND_CENTER;
    if (Math.abs(error) > BAND_WIDTH/2) { // Verify if distance is within dead band.
      double delta = error * GAIN; // Calculate delta. The delta will be 1/2 * difference between the angular speed of the two motors.
      if (delta > MAX_DELTA) {delta = MAX_DELTA;} // Make sure delta isn't too high, otherwise the motors could not keep up.
      if (delta < -MAX_DELTA) {delta = -MAX_DELTA;}
     LEFT_MOTOR.setSpeed((int) (MOTOR_SPEED - delta)); // Set the correct speed for each motors.
      RIGHT_MOTOR.setSpeed((int) (MOTOR_SPEED + delta));
    } else {
      LEFT_MOTOR.setSpeed(MOTOR_SPEED); // Go straight.
      RIGHT_MOTOR.setSpeed(MOTOR_SPEED);
    }
    
  }

  @Override
  public int readUSDistance() {
    return this.distance;
  }
}
