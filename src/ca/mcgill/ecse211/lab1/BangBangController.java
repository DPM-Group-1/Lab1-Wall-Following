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

    if (distance < (Resources.BAND_CENTER - Resources.BAND_WIDTH/2)) {
      LEFT_MOTOR.setSpeed(MOTOR_HIGH);
      RIGHT_MOTOR.setSpeed(MOTOR_LOW);
    } else if (distance < (Resources.BAND_CENTER + Resources.BAND_WIDTH/2)) {
      LEFT_MOTOR.setSpeed(MOTOR_HIGH);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
    } else {
      LEFT_MOTOR.setSpeed(MOTOR_LOW);
      RIGHT_MOTOR.setSpeed(MOTOR_HIGH);
    }
    
  }

  @Override
  public int readUSDistance() {
    return this.distance;
  }
}
