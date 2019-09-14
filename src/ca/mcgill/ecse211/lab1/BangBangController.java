package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

public class BangBangController extends UltrasonicController {

  public BangBangController() {
    LEFT_MOTOR.setSpeed(MOTOR_HIGH); // Start robot moving forward
    RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
    LEFT_MOTOR.forward();
    RIGHT_MOTOR.forward();
  }

  @Override
  public void processUSData(int distance) {
    filter(distance);

    if (distance < (BAND_CENTER - BAND_WIDTH/2)) { // If the robot is too close, turn right.
      LEFT_MOTOR.setSpeed(MOTOR_HIGH);
      RIGHT_MOTOR.setSpeed(MOTOR_LOW);
    } else if (distance < (BAND_CENTER + BAND_WIDTH/2)) { // If the robot is in the dead band, move forward.
      LEFT_MOTOR.setSpeed(MOTOR_HIGH);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
    } else { // If the robot is too far, turn left.
      LEFT_MOTOR.setSpeed(MOTOR_LOW);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
    }
    
  }

  @Override
  public int readUSDistance() {
    return this.distance;
  }
}
