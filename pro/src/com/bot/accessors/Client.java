package com.bot.accessors;

import java.awt.Graphics;

import javax.swing.JPanel;

import org.w3c.dom.NodeList;



public interface Client {
	
	com.bot.accessors.NPC[] getNPCArray();
	
	com.bot.accessors.PlayerAccessor[] getPlayerArray();
	
	com.bot.accessors.PlayerAccessor getLocalPlayer();

	int getCameraY();
	
	int getCameraZ();
	
	int getCameraX();

	int getyCameraCurve();
	
	int getxCameraCurve();
	
	byte [][][] getbyteGroundArray();
	
	public Graphics getRealGraphics();
	
	com.bot.accessors.RSObjectDef[] getCache();
	
	com.bot.accessors.RSObjectDef getObjectCache(int paramInt);

	JPanel getGame();

	int getitemSelected();
	
	String getselectedItemName();
	
	int getCameraZoom();
	
	int getPlane();
	
	int [][][] getintGroundArray();
		
	boolean isloggedIn();
	
	 int[] getnpcIndices();
	 
	 int[] getplayerIndices();
	 
	int getmenuScreenArea();
	
	com.bot.accessors.RSItem[] getItems();
	
	com.bot.accessors.RSItemDef[] getItemCache();
	
	com.bot.accessors.RSInterface[] getInterfaceCache();
	
	com.bot.accessors.NodeList[][][] getObjects();
	
	int getBaseX();
	
	int getBaseY();

	com.bot.accessors.WorldController getWorldController();
	
	int getopenInterfaceID();
	
	  public abstract int getMenuScreenArea();

	  public abstract int getMenuOffsetX();

	  public abstract int getMenuOffsetY();

	  public abstract int getMenuWidth();

	  public abstract int getMenuHeight();
	  
	  public abstract int getMenuActionRow();
	  
	  public abstract String[] getMenuActionNames();

	  public abstract boolean isMenuOpen();
	  
	  public abstract int[] menuAction1();

	  public abstract int[] menuAction2();

	  public abstract int[] menuAction3();

	  public abstract int[] menuActionId();


}
