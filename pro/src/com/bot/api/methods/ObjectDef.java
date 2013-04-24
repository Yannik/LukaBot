package com.bot.api.methods;

public class ObjectDef
{
  private int index;
  private String name;
  private String[] actions;

  public int getIndex()
  {
    return this.index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public String getName() {
    return this.name == null ? "null" : this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String[] getActions() {
    return this.actions;
  }

  public void setActions(String[] actions) {
    this.actions = actions;
  }

  public boolean nameContains(String name) {
    return getName().toLowerCase().contains(name.toLowerCase());
  }

  public boolean hasAction(String action) {
    for (String s : getActions()) {
      if (s.equalsIgnoreCase(action)) {
        return true;
      }
    }
    return false;
  }
}