package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;
import lejos.utility.Timer;
import lejos.utility.TimerListener;

/**
 * Samples the US sensor and invokes the selected controller on each cycle.
 * 
 * Control of the wall follower is applied periodically by the UltrasonicPoller thread. The poller uses a timer that sends an
 * interrupt every SININTERVAL, determining the sampling frequency.
 * 
 */
public class UltrasonicPoller implements TimerListener {

  private UltrasonicController controller;
  private float[] usData;

  public UltrasonicPoller() throws InterruptedException {
    usData = new float[US_SENSOR.sampleSize()];
    controller = Main.selectedController;
    Timer samplerTimer = new Timer(SINTERVAL, this);
    samplerTimer.start();
  }

  /*
   * Sensors now return floats using a uniform protocol. Need to convert US result to an integer
   * [0,255] (non-Javadoc)
   * 
   * @see java.lang.Thread#run()
   */
  public void timedOut() { // Called every time the timer times out.
    int distance;
    US_SENSOR.getDistanceMode().fetchSample(usData, 0); // acquire distance data in meters
    distance = (int) (usData[0] * 100.0); // extract from buffer, convert to cm, cast to int
    controller.processUSData(distance); // now take action depending on value
  }

}
