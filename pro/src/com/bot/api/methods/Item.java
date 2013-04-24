package com.bot.api.methods;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import com.bot.Mouse;
import com.bot.Loader;
import com.bot.accessors.RSItemDef;
import com.bot.api.Utils;


public class Item
{
  private int index;
  private int stack;
  private int slot;
  private static Mouse mouse = new Mouse(Loader.applet);
  
  public Item(int index, int stack, int slot)
  {
    this.index = index;
    this.stack = stack;
    this.slot = slot;
    
  }

  public int getStackSize()
  {
    return this.stack;
  }

  public int getId()
  {
    return this.index;
  }

  public Point getScreenLocation()
  {
    int col = this.slot % 4;
    int row = this.slot / 4;
    int x = 580 + col * 42;
    int y = 228 + row * 36;
    return new Point(x, y);
  }

  public void drop()
  {
    interact("Drop");
  }


  public void interact(String action)
  {
    Point p = getScreenLocation();
    try {
		mouse.move(p.x, p.y);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    Utils.sleep(100);
    Menu.interact(action);
  }

  public int getSlot()
  {
    return this.slot;
  }


  public RSItemDef getDef()
  {
	for(RSItemDef d : Loader.client.getItemCache()) {
			if(d.getID() == getId()) {
				return d;
			}
		}
		return null;
  }
}