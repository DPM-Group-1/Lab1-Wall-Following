package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class PController extends UltrasonicController {

  private static final int MOTOR_SPEED = 150;
  private static final int MAX_DELTA = 125;

  public PController() {
    LEFT_MOTOR.setSpeed(MOTOR_SPEED); // Initialize motor rolling forward
    RIGHT_MOTOR.setSpeed(MOTOR_SPEED);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  @Override
  public void processUSData(int distance) {
    filter(distance);

    int error = distance - BAND_CENTER;
    if (Math.abs(error) > BAND_WIDTH/2) { // Verify if within dead band.
      double delta = error * GAIN; // Calculate delta.
      if (delta > MAX_DELTA) {delta = MAX_DELTA;} // Make sure delta isn't too high.
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
