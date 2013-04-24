package com.bot.api.methods;

import com.bot.KeyBoard;
import com.bot.Loader;
import com.bot.accessors.Client;
import com.bot.utils.Calculations;

public class Camera
{
  private static Client c = Loader.client;
  private static KeyBoard k = new KeyBoard();

  public static void turnTo(Npc c, boolean pitchUp)
  {
    int angle = getCharacterAngle(c);
    setRotation(angle);
    setPitch(false);
  }

  public static void turnTo(Npc c) {
    turnTo(c, false);
  }

  private static int getCharacterAngle(Npc n) {
    return getTileAngle(n.getLocation());
  }



  public static void turnTo(Tile tile, boolean pitchUp)
  {
    int angle = getTileAngle(tile);
    setRotation(angle);
    setPitch(pitchUp);
  }

  public static void turnTo(Tile tile) {
    turnTo(tile, false);
  }




  /*public static void turnTo(GroundItem item, boolean pitchUp)
  {
    int angle = getTileAngle(item.getLocation());
    setRotation(angle);
    setPitch(pitchUp);
  }

  public static void turnTo(GroundItem item) {
    turnTo(item, false);
  }*/
  
  private static int getObjectAngle(GameObject o) {
	    return getTileAngle(o.getLocation());
	  }

  public static void turnTo(GameObject o, boolean pitchUp)
  {
    int angle = getObjectAngle(o);
    setRotation(angle);
  }

  public static void turnTo(GameObject o) {
    turnTo(o, false);
  }
  
  
  public static int getZoom()
  {
    return 0;
  }

  public static int getX()
  {
    return c.getCameraX();
  }

  public static int getY()
  {
    return c.getCameraY();
  }

  public static int getZ()
  {
    return c.getCameraZ();
  }

  public static int getCurveX()
  {
    return c.getxCameraCurve();
  }

  public static int getCurveY()
  {
    return c.getyCameraCurve() ; 
  }

  public static int getAngle()
  {
    double mapAngle = getCurveX();
    mapAngle /= 2040.0D;
    mapAngle *= 360.0D;
    return (int)mapAngle;
  }

  private static int getTileAngle(Tile t)
  {
    int a = (Calculations.angleToTile(t) - 90) % 360;
    return a < 0 ? a + 360 : a;
  }

  public static void setRotation(int degrees)
  {
    char left = '%';
    char right = '\'';
    char whichDir = left;
    int start = getAngle();
    if (start < 180)
      start += 360;
    if (degrees < 180)
      degrees += 360;
    if (degrees > start) {
      if (degrees - 180 < start)
        whichDir = right;
    } else if ((start > degrees) && (start - 180 >= degrees))
      whichDir = right;
    degrees %= 360;
    k.pressKey(whichDir);
    int timeWaited = 0;
    while ((getAngle() > degrees + 5) || (getAngle() < degrees - 5)) {
      sleep(10);
      timeWaited += 10; if (timeWaited > 500) {
        int time = timeWaited - 500;
        if (time == 0)
          k.pressKey(whichDir);
        else if (time % 40 == 0)
          k.pressKey(whichDir);
      }
    }
    k.releaseKey(whichDir);
  }

  public static void setCompass(char direction)
  {
    switch (direction)
    {
    case 'n':
      setRotation(359);
      break;
    case 'e':
      setRotation(89);
      break;
    case 's':
      setRotation(179);
      break;
    case 'w':
      setRotation(269);
      break;
    default:
      setRotation(359);
    }
  }

  public static boolean setPitch(boolean up)
  {
    try
    {
      char key = up ? '&' : '(';
      k.pressKey(key);
      sleep(nextInt(1000, 1500));
      k.releaseKey(key);
    } catch (Exception e) {
      return false;
    }
    return true;
  }

  public static boolean setAltitude(double altPercent)
  {
    int alt = (int)(altPercent / 100.0D * -1237.0D - 1226.0D);
    int curAlt = getZ();
    int lastAlt = 0;
    if (curAlt == alt)
      return true;
    if (curAlt > alt) {
      k.pressKey(38);
      for (long start = System.currentTimeMillis(); (curAlt > alt) && (System.currentTimeMillis() - start < 30L); curAlt = getZ()) {
        if (lastAlt != curAlt)
          start = System.currentTimeMillis();
        lastAlt = curAlt;
        sleep(1);
      }

      k.releaseKey(38);
      return true;
    }
    k.pressKey(40);
    for (long start = System.currentTimeMillis(); (curAlt < alt) && (System.currentTimeMillis() - start < 30L); curAlt = getZ()) {
      if (lastAlt != curAlt)
        start = System.currentTimeMillis();
      lastAlt = curAlt;
      sleep(1);
    }

    k.releaseKey(40);
    return true;
  }

  private static int nextInt(int min, int max)
  {
    return (int)(Math.random() * (max - min)) + min;
  }

  private static void sleep(int i)
  {
    try
    {
      Thread.sleep(i);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}