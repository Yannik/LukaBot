package com.bot.accessors;

public interface PlayerAccessor {
	
	com.bot.accessors.EntityDef getEntityDef();
	
	String getName();

	int getCombatLevel();
	
	int getSkullIcon();
	
	boolean canBeVisible();
	
	int getInteractingNPC();
	
	int getCurrentHp();
	
	int getmaxHealth();
	
	int getAnimimation();
	
	int getHeight();
	
	int getX();
	
	int getY();
	
	String getActions();
}
