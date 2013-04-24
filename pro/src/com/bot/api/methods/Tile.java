package com.bot.api.methods;

import java.awt.Point;

import com.bot.Mouse;
import com.bot.api.Utils;
import com.bot.utils.Calculations;

public class Tile {
	
	int x;
	int y;
	
	public Tile(int x, int y){ 
		this.x = x;
		this.y = y;
	}

	
	  public int getX()
	  {
	    return this.x;
	  }

	  public int getY()
	  {
	    return this.y;
	  }

	  
	  public int distanceTo(Tile other)
	  {
	    return (int)Math.sqrt(Math.pow(other.getX() - this.x, 2.0D) + Math.pow(other.getY() - this.y, 2.0D));
	  }


	  public double getDistance(Tile t)
	  {
	    return Calculations.distanceTo(t);
	  }
	  

	  
}
