package com.bot.api.methods;

import java.awt.Point;
import java.awt.Rectangle;

import com.bot.Mouse;
import com.bot.Loader;
import com.bot.accessors.RSItem;
import com.bot.accessors.RSItemDef;
import com.bot.api.Utils;
import com.bot.utils.Calculations;


public class GroundItem
{
  private RSItem item = null;
  private Tile tile = null;
  private int id = 0;
  private Mouse d = new Mouse(Loader.applet);

  public GroundItem(Tile tile, int id, RSItem node)
  {
    this.tile = tile;
    this.id = id;
    this.item = node;
  }

  public int getID()
  {
    return this.id;
  }

  public RSItem get()
  {
    return this.item;
  }

  public Tile getLocation()
  {
    return new Tile(getX(),getY());
  }

  public int getX()
  {
    return this.tile.getX();
  }

  public int getY()
  {
    return this.tile.getY();
  }

  public double getDistance()
  {
    return Calculations.distanceTo(getLocation());
  }

  public Point getLocationOnScreen()
  {
    return Calculations.tileToScreen(getLocation());
  }

  
  public boolean isOnScreen() {

	  final Rectangle GAMESCREEN = new Rectangle(4, 4, 512, 334);
	  if(GAMESCREEN.contains(getLocationOnScreen())){
		  return true;
	  }
	return false;
	  
  }
  

  public void interact(String action)
  {
    Point p = getLocationOnScreen();
    try {
		d.move(p.x, p.y);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    Utils.sleep(200);
    Menu.interact(action + " " + getDef().getItemName());
  }

  public RSItemDef getDef()
  {
	for(RSItemDef d : Loader.client.getItemCache()) {
			if(d.getID() == getID()) {
				return d;
			}
		}
		return null;
  }

  public void take()
  {
    interact("Take");
  }
}