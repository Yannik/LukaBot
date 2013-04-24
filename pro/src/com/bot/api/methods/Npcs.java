package com.bot.api.methods;

import java.awt.List;
import java.util.ArrayList;

import com.bot.Loader;
import com.bot.accessors.Client;
import com.bot.accessors.NPC;


public class Npcs {
	
	public static Client client;

	public Npcs() {
	client = Loader.client;
		
	}
	
	  public static ArrayList<Npc> getAll()
	  {
	    ArrayList<Npc> npcs = new ArrayList<Npc>();
	    try { 
	    for (NPC npc : Loader.client.getNPCArray()) {     
	      if (npc != null) {
	        npcs.add(new Npc(npc));
	      }
	    }
	    } catch (NullPointerException e) {}
	    return npcs;
	  }
	  
	 public static Npc getNearest(int[] ids) {
		 int previousdistance = 0;
		 Npc nearest = null;
		  ArrayList<Npc> npcs = getAll();
		  for(Npc p : npcs) {
			  if(p.getLocation().distanceTo(new Tile(client.getLocalPlayer().getX(),client.getLocalPlayer().getY())) < previousdistance) {
				  for(int t : ids) {
				  if(p.getId() == t) {
					  previousdistance = p.getLocation().distanceTo(new Tile(client.getLocalPlayer().getX(),client.getLocalPlayer().getY()));
					  nearest = p;
				  }
			  }
			  }
		  }
		  return nearest;
	 }
	 
			 public static Npc getNearest(int ids) {
				 int previousdistance = 0;
				 Npc nearest = null;
				  ArrayList<Npc> npcs = getAll();
				  for(Npc p : npcs) {
					  if(p.getLocation().distanceTo(new Tile(client.getLocalPlayer().getX(),client.getLocalPlayer().getY())) < previousdistance) {
						  if(p.getId() == ids) {
							  previousdistance = p.getLocation().distanceTo(new Tile(client.getLocalPlayer().getX(),client.getLocalPlayer().getY()));
							  nearest = p;
						  }
					  
					  }
				  }
				  return nearest;
			 }
	 
	 }
	  
	


