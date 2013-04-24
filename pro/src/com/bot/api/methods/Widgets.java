package com.bot.api.methods;

import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import com.bot.Loader;
import com.bot.accessors.Client;
import com.bot.accessors.RSInterface;


public class Widgets{

private static Client client = Loader.client;

  public static HashMap<Integer, Integer> parents = new HashMap();


  public static Widget getOpenWidget()
  {
    int index = client.getopenInterfaceID();
    return index == -1 ? null : getWidget(index);
  }


  public static Widget[] getParentWidgets()
  {
    Map topWidgets = new HashMap();
    if (client.getInterfaceCache() != null) {
      for (RSInterface iface : client.getInterfaceCache())
        if (iface != null)
        {
          if ((topWidgets.get(Integer.valueOf(iface.getId())) == null) && (iface.getParent() == iface.getId()))
            topWidgets.put(Integer.valueOf(iface.getId()), new Widget(iface));
        }
    }
    topWidgets = new TreeMap(topWidgets);
    return (Widget[])topWidgets.values().toArray(new Widget[topWidgets.size()]);
  }

  public static Widget getParentWidget(int id)
  {
    for (Widget iface : getParentWidgets())
      if (iface != null)
      {
        if (iface.getId() == id)
          return iface;
      }
    return null;
  }

  public static Widget getWidget(int parent, int child)
  {
    return getParentWidget(parent).getChild(child);
  }

  public static Widget getWidget(int id)
  {
    RSInterface accessor = client.getInterfaceCache()[id];
    if (accessor == null)
      return null;
    Widget iface = new Widget(client.getInterfaceCache()[id]);
    if (iface.getId() == iface.getParentId())
      return iface;
    System.out.println(iface.getParentId());
    return getWidget(iface.getParentId(), id);
  }

  public static Widget get(int index) {
    if (client.getInterfaceCache() == null || (client.getInterfaceCache()[index] == null)) {
      return null;
    }
    return new Widget(client.getInterfaceCache()[index]);
  }

  public static int getParentId(int childId) {
    if (parents.isEmpty()) {
      for (Widget face : getParentWidgets()) {
        if ((face != null) && 
          (face.getChildren() != null)) {
          for (Widget child : face.getChildren()) {
            if (child != null) {
              parents.put(Integer.valueOf(child.getId()), Integer.valueOf(face.getId()));
            }
          }
        }
      }
    }

    if (parents.get(Integer.valueOf(childId)) == null) {
      return childId;
    }
    return ((Integer)parents.get(Integer.valueOf(childId))).intValue();
  }
  
  
}