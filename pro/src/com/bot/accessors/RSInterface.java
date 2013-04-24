package com.bot.accessors;

public interface RSInterface {
	
		  public abstract String getMessage();

		  int getId();

		  public abstract String getSpellName();

		  public abstract int getWidth();

		  public abstract int getHeight();

		  public abstract int getParent();

		  public abstract String[] getActions();

		  public abstract String getTooltip();

		  public abstract String getPopup();

		  public abstract String getSelectedActionName();

		  public abstract boolean isInventory();

		  public abstract int[] getStackSize();

		  public abstract int[] getItems();

		  public abstract int[] getChildren();

		  public abstract int[] getChildX();

		  public abstract int[] getChildY();

		  public abstract int[] getSpriteX();

		  public abstract int[] getSpriteY();
		  
		  

		
}
