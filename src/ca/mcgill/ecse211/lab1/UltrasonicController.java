package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;

/**
 * Controller that controls the robot's movements based on ultrasonic data.
 */
public abstract class UltrasonicController {

  int distance;
  
  int previousDistance;
  
  int filterControl;
  
  /**
   * Perform an action based on the US data input.
   * 
   * @param distance the distance to the wall in cm
   */
  public abstract void processUSData(int distance);

  /**
   * Returns the distance between the US sensor and an obstacle in cm.
   * 
   * @return the distance between the US sensor and an obstacle in cm
   */
  public abstract int readUSDistance();
  
  /**
   * Rudimentary filter - toss out invalid samples corresponding to null signal.
   * @param distance distance in cm
   */
  void filter(int distance) {
    if (distance >= 255 && filterControl < FILTER_OUT) {
      // bad value, do not set the distance var, however do increment the filter value
      filterControl++;
    } else if (distance >= 255) {
      // Repeated large values, so there is nothing there: leave the distance alone
      this.distance = distance;
    } else {
      // distance went below 255: reset filter and leave distance alone.
      filterControl = 0;
      this.distance = distance;
    }
  }
  
  /**
   * Differential filter. Make sure that the value is not too different from the previous one.
   * @param distance distance in cm
   */
  void differentialFilter(int distance) {
    if (previousDistance - distance < -100) { // If the distance is suddenly too far...
      int temp = this.previousDistance; // Disregard value and use previous distance.
      this.previousDistance = this.distance;
      this.distance = temp;
    } else {
      this.previousDistance = this.distance; // Otherwise, just keep the value.
    }
  }
  
  
}
