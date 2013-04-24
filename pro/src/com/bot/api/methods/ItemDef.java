package com.bot.api.methods;


public class ItemDef
{
  private int index;
  private String name;
  private String[] actions;
  private String[] groundActions;

  public int getIndex()
  {
    return this.index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getName()
  {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String[] getActions()
  {
    return this.actions;
  }

  public void setActions(String[] actions) {
    this.actions = actions;
  }

  public String[] getGroundActions()
  {
    return this.groundActions;
  }

  public void setGroundActions(String[] groundActions) {
    this.groundActions = groundActions;
  }

  public boolean hasAction(String action)
  {
    for (String s : this.actions) {
      if (s.equalsIgnoreCase(action)) {
        return true;
      }
    }
    return false;
  }

  public boolean nameContains(String name) {
    return getName().toLowerCase().contains(name.toLowerCase());
  }
}