package com.bot.api.methods;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;



import com.bot.Mouse;
import com.bot.Loader;
import com.bot.accessors.PhysicalObject;
import com.bot.accessors.RSItemDef;
import com.bot.accessors.RSObjectDef;
import com.bot.api.Utils;
import com.bot.utils.Calculations;

public class GameObject
{
  protected PhysicalObject accessor = null;
  private int uid;
  private  RSObjectDef d;
private int x;
Mouse  fd = new Mouse(Loader.applet);
private int y;
  public GameObject(PhysicalObject accessor, int x, int y)
  {
    this.accessor = accessor;
    this.uid = accessor.getUID();
    this.x = x;
    this.y = y;
  }

  public int getX()
  {
    return x+ Loader.client.getBaseX();
  }

  public int getY()
  {
    return y + Loader.client.getBaseY();
  }

  public int getID()
  {
    return this.uid >> 14 & 0x7FFF ;
  }

  public int getUID() {
    return this.uid;
  }

  public Tile getLocation()
  {
    return new Tile( getX(),  getY());
  }

  public Point getLocationOnScreen(int height)
  {
    return Calculations.tileToScreen(getLocation());
  }

  public Point getLocationOnScreen()
  {
    return getLocationOnScreen(0);
  }

  public double getDistance()
  {
    return com.bot.utils.Calculations.distanceTo(getLocation());
  }

  public boolean isOnScreen() {

	  final Rectangle GAMESCREEN = new Rectangle(4, 4, 512, 334);
	  if(GAMESCREEN.contains(getLocationOnScreen())){
		  return true;
	  }
	return false;
	  
  }
  

  public RSObjectDef getDef()
  {
	try{
		if(Loader.client.getObjectCache(getID()) != null) {
		return Loader.client.getObjectCache(getID());
		}
	} catch(NullPointerException | ArrayIndexOutOfBoundsException e) {
	
	}
	return null;

  }

  public void interact(String action)
  {
    Point p = getLocationOnScreen();

		try {
			fd.move(p.x, p.y);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			fd.rightClick();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
    Utils.sleep(200);
    Menu.interact(action + " " + getDef().getName());
  }




}