package org.alexsmith;

import org.alexsmith.RokuDriver.RokuDriver;
import java.util.concurrent.TimeUnit;

public class Main {
  public static void main(String[] args) {

    RokuDriver.setDeviceIp("192.168.50.232");

    try {
      RokuDriver.startRokuWebDriver();
    } catch(Exception e) {
      e.printStackTrace();
    }

    String sessionId = RokuDriver.startSession();

    RokuDriver.launchApp(sessionId);
//    Awaitility
//            .await()
//            .pollDelay(5, TimeUnit.SECONDS);
    System.out.println(RokuDriver.getSource(sessionId));

//    RokuDriver.getSource(Session.startSession());

  }
}
