package com.bot.api.methods;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.List;



import com.bot.Mouse;
import com.bot.Loader;
import com.bot.accessors.RSInterface;


public class Widget
{
  private RSInterface accessor;
  private Mouse mouse = new Mouse(Loader.applet);

  public Widget(RSInterface accessor)
  {
    this.accessor = accessor;
  }

  public void click() {
    int x = getX() + getWidth();
    int y = getY() + getHeight();
    try {
		mouse.leftclick(x, y);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }


  
  public int getId() {
    if (this.accessor == null)
      return -1;
    return this.accessor.getId();
  }

  public Widget getParent() {
    if (this.accessor == null)
      return null;
    if (getId() == getParentId())
      return this;
    return Widgets.get(getParentId());
  }

  public String[] getActions() {
    if (this.accessor == null)
      return null;
    return this.accessor.getActions();
  }

  public String getSelectedAction() {
    if (this.accessor == null)
      return null;
    return this.accessor.getSelectedActionName();
  }

  public String getTooltip() {
    if (this.accessor == null)
      return null;
    return this.accessor.getTooltip();
  }

  public int getWidth() {
    if (this.accessor == null)
      return 0;
    return this.accessor.getWidth();
  }

  public int getHeight() {
    if (this.accessor == null)
      return 0;
    return this.accessor.getHeight();
  }

  public Point getPoint(Region region) {
    if (this.accessor == null)
      return null;
    return new Point(region.getX() + getX(), region.getY() + getY());
  }

  public int getX() {
    if (this.accessor == null)
      return -1;
    int[] ids = getParent().getChildrenIds();
    if (ids != null) {
      for (int i = 0; i < ids.length; i++) {
        if (ids[i] == getId()) {
        	System.out.println(getParent().getX());
          return getParent().getChildX()[i] + getParent().getX();
        }
      }
    }
    return 0;
  }

  public int[] getChildX() {
    return this.accessor.getChildX();
  }

  public int getY() {
    if (this.accessor == null)
      return -1;
    int[] ids = getParent().getChildrenIds();
    if (ids != null) {
      for (int i = 0; i < ids.length; i++) {
        if (ids[i] == getId()) {
        	System.out.println(getParent().getY());
          return getParent().getChildY()[i] + getParent().getY();
        }
      }
    }
    return 0;
  }

  public int[] getChildY() {
    return this.accessor.getChildY();
  }

  public int[] getChildrenIds() {
    return this.accessor.getChildren();
  }

  public int getParentId() {
    if (this.accessor == null)
      return -1;
    return Widgets.getParentId(getId());
  }

  public Widget[] getChildren() {
    if (this.accessor == null)
      return null;
    int[] ids = this.accessor.getChildren();
    if (ids == null)
      return null;
    Widget[] ret = new Widget[ids.length];
    for (int i = 0; i < ids.length; i++)
      ret[i] = Widgets.get(ids[i]);
    return ret;
  }

  public String getText() {
    if (this.accessor == null)
      return null;
    return this.accessor.getMessage();
  }

  public Rectangle getArea(Region region) {
    Point p = getPoint(region);
    return new Rectangle(p.x, p.y, getWidth(), getHeight());
  }

  public void draw(Graphics g) {
    g.setColor(Color.GREEN);
    g.drawRect(getX(), getY(), getWidth(), getHeight());
  }

  public Widget getChild(int child) {
    return getChildren()[child];
  }

  public int[] getItemIDArray() {
      return accessor.getItems();
  }



  
  public int[] getItems() {
	  int[] items = new int[this.accessor.getItems().length];
      for (int i = 0; i < items.length; i++) {
        items[i] = (this.accessor.getItems()[i] - 1);
      }
      return items;
	  }

	  public int[] getStackSizes() {
		  return this.accessor.getStackSize();
	  }
	  
	  
  
  public boolean hasInventory() {
    return this.accessor.isInventory();
  }
}