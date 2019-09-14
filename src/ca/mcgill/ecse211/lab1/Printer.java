package ca.mcgill.ecse211.lab1;

import static ca.mcgill.ecse211.lab1.Resources.*;
import lejos.utility.Timer;
import lejos.utility.TimerListener;

/**
 * The printer thread prints status information in the background. Since the thread sleeps for 
 * 200 ms each time through the loop, screen updating is limited to 5 Hz.
 */
public class Printer implements TimerListener {

  private UltrasonicController controller;

  public Printer() {
    controller = Main.selectedController;
    Timer printerTimer = new Timer(50, this);
    printerTimer.start();
  }

  public void timedOut() {
    TEXT_LCD.clear();
    // print header
    TEXT_LCD.drawString("Controller Type is... ", 0, 0);
    
    if (controller instanceof BangBangController) {
      TEXT_LCD.drawString("BangBang", 0, 1);
    } else if (controller instanceof PController) {
      TEXT_LCD.drawString("P type", 0, 1);
    }
    
    // print last US reading
    TEXT_LCD.drawString("US Distance: " + controller.readUSDistance(), 0, 2);
  }

  /**
   * Prints the main menu.
   * 
   * Note that this is a static method.
   */
  public static void printMainMenu() {
    TEXT_LCD.clear(); // the screen at initialization
    TEXT_LCD.drawString("left = bangbang", 0, 0);
    TEXT_LCD.drawString("right = p type", 0, 1);
  }
}
