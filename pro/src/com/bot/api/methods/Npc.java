package com.bot.api.methods;

import java.awt.Point;
import java.awt.Rectangle;

import com.bot.Mouse;
import com.bot.Loader;
import com.bot.accessors.Client;
import com.bot.accessors.NPC;

import com.bot.api.Utils;
import com.bot.utils.Calculations;

public class Npc {
	
	private NPC npc;
	private Client client;
	private Mouse d= new Mouse(Loader.applet);
	public Npc(NPC npc) {
		this.npc = npc;
		this.client = Loader.client;

	}

	public int GetCombatLevel() {
		return npc.getEntityDef().getcombatLevel();
	}
	
	
	public int getX() {
		int i = (this.client.getBaseX())+ ((this.npc.getY()) >> 7);
		return i;
	}

	public int getY() {
		int i = (this.client.getBaseY())+ ((this.npc.getY()) >> 7);
		return i;
	}
	
	public String getName() {
		try{
		return npc.getEntityDef().getName();
		}catch (NullPointerException e) {}
		return null;
	}
	
	public int getAnimation() {
		return npc.getAnimimation();
	}
	
	public Tile getLocation() {
		return new Tile(getX(),getY());
	}
	
	

	public int getId() {
		return (int) npc.getEntityDef().getID();
	}
	
	
	public int getHeight(){
		return npc.getHeight();
	}
	  public void click()
	  {
		 Point t = Calculations.worldToScreen(getX(), getY(), (this.npc.getHeight()-20));
		  try {
			d.move(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  try {
			d.leftclick(t);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  Utils.sleep(1000, 1200);
		  
	  }
	  
	  public void interact(String action)
	  {
	    Point p = onScreenLocation();
	    try {
			d.move(p.x, p.y);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    Utils.sleep(100);
	    Menu.interact(action);
	  }
	  
	  
	  public void click(String phrase) {
		 if(ContainsAction(phrase)) { 
		
			  try {
				Mouse.move(onScreenLocation());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  try {
				Mouse.leftclick(onScreenLocation());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			  Utils.sleep(1000, 1200); 
		 }
		  
	  }
	  
	  public boolean ContainsAction(String phrase) {
		  
		  for (int i = 0; i < this.npc.getEntityDef().getActions().length; i++) {
		      if ((this.npc.getEntityDef().getActions()[i] != null) && (this.npc.getEntityDef().getActions()[i].contains(phrase))) {
		        return true;
		      }
		    }
		    return false;
		  }
	  
	  
	  public boolean isOnScreen() {

		  final Rectangle GAMESCREEN = new Rectangle(4, 4, 512, 334);
		  if(GAMESCREEN.contains(onScreenLocation())){
			  return true;
		  }
		return false;
		  
	  }
	  
	  public Point onScreenLocation() {
		  return Calculations.worldToScreen(this.npc.getX(), this.npc.getY(), (this.npc.getHeight()-50));
	  }
	  
}


