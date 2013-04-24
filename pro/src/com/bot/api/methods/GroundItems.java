package com.bot.api.methods;

import java.util.ArrayList;

import com.bot.Loader;
import com.bot.accessors.Client;
import com.bot.accessors.Node;
import com.bot.accessors.NodeList;
import com.bot.accessors.RSItem;
import com.bot.accessors.RSItemDef;
import com.bot.utils.Calculations;


public class GroundItems
{
  private static Client client = Loader.client;

  public static GroundItem[] getGroundItems(int range) {
    ArrayList out = new ArrayList();
    try {
      int minX = Players.getLocalPlayer().getX() - range;
      int minY = Players.getLocalPlayer().getY() - range;
      int maxX = Players.getLocalPlayer().getX() + range;
      int maxY = Players.getLocalPlayer().getY() + range;
      for (int x = minX; x < maxX; x++) {
        for (int y = minY; y < maxY; y++) {
          GroundItem[] o = getGroundItemsAt(x, y);
          if (o != null) {
            for (GroundItem item : o)
              if (item != null)
                out.add(item);
          }
        }
      }
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return (GroundItem[])out.toArray(new GroundItem[out.size()]);
  }

  public static GroundItem[] getGroundItemsAt(int x, int y) {
    try {
      int basex = Loader.client.getBaseX();
      int basey = Loader.client.getBaseY();
      NodeList nl = Loader.client.getObjects()[Loader.client.getPlane()][x][y];
      ArrayList list = new ArrayList();
      if (nl == null) {
        return null;
      }
      Node holder = nl.getHead();
      Node curNode = holder.getNext();
      while ((curNode != null) && (curNode != holder) && (curNode != nl.getHead())) {
        RSItem node = (RSItem)curNode;
        list.add(new GroundItem(new Tile(basex + x, basey + y), node.getItemID(), node));
        curNode = curNode.getNext();
      }
      return (GroundItem[])list.toArray(new GroundItem[list.size()]);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static GroundItem getNearest(Tile tile, int[] ids) {
    GroundItem[] array = getAll();
    if ((array != null) && (array.length > 0)) {
      GroundItem nearest = array[0];
      for (GroundItem g : array) {
        for (int id : ids) {
          if (g.getID() == id) {
            double nextDistance = Calculations.distanceBetween(tile, g.getLocation());
            double nearestDistance = Calculations.distanceBetween(tile, nearest.getLocation());
            if (nextDistance < nearestDistance) {
              nearest = g;
            }
          }
        }
      }
      for (int id : ids) {
        if (nearest.getID() == id) {
          return nearest;
        }
      }
    }
    return null;
  }

  public static GroundItem getNearest(int[] ids) {
    return getNearest(Players.getLocalPlayer().getLocation(), ids);
  }

  public static GroundItem getNearest(Tile tile, String[] names) {
    GroundItem[] array = getAll();
    if ((array != null) && (array.length > 0)) {
      GroundItem nearest = array[0];
      for (GroundItem g : array) {
        for (String name : names) {
          RSItemDef def = g.getDef();
          if (def != null) {
            try {
              if (def.equals(name)) {
                double nextDistance = Calculations.distanceBetween(tile, g.getLocation());
                double nearestDistance = Calculations.distanceBetween(tile, nearest.getLocation());
                if (nextDistance < nearestDistance)
                  nearest = g;
              }
            }
            catch (Exception e) {
              e.printStackTrace();
            }
          }
        }
      }

      for (String name : names) {
        if (nearest.getDef().equals(name)) {
          return nearest;
        }
      }
    }
    return null;
  }

  public static GroundItem getNearest(String[] names) {
    return getNearest(Players.getLocalPlayer().getLocation(), names);
  }

  public static GroundItem[] getAll() {
    ArrayList items = new ArrayList();
    for (int x = 0; x < 104; x++) {
      for (int y = 0; y < 104; y++) {
        GroundItem[] o = getGroundItemsAt(x, y);
        if (o != null) {
          for (GroundItem i : o) {
            if (i != null) {
              items.add(i);
            }
          }
        }
      }
    }
    return (GroundItem[])items.toArray(new GroundItem[items.size()]);
  }
}