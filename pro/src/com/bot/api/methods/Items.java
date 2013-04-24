package com.bot.api.methods;

import java.io.PrintStream;
import java.util.ArrayList;

import com.bot.Loader;
import com.bot.accessors.Client;
import com.bot.accessors.RSItemDef;


public class Items
{
  private static ArrayList<ItemDef> cached = new ArrayList();
  private static Client client = Loader.client;

  public static ItemDef get(int i)
  {
   
      ItemDef item = new ItemDef();
      RSItemDef def = client.getItemCache()[i];
      if (def != null) {
        item.setIndex(i);
        item.setName(def.getItemName());
        item.setActions(def.getActions());
        item.setGroundActions(def.getGroundActions());
        return item;
      }
      return null;
  }

  public static void loadDefinitions()
  {
    cached.clear();
    int i = 0;
    while (i < 20000) {
      try {
        ItemDef item = new ItemDef();
        RSItemDef def = client.getItemCache()[i];
        if (def != null) {
          item.setIndex(i);
          item.setName(def.getItemName());
          item.setActions(def.getActions());
          item.setGroundActions(def.getGroundActions());
          cached.add(item);
        }
        i++;
      } catch (ArrayIndexOutOfBoundsException e) {
        break;
      } catch (Exception e) {
        System.out.println("Exception while loading items: " + e.toString() + " @ index: " + i);
        i++;
      }
    }
    System.gc();
    System.out.println("Item definitions loaded: " + getCount());
  }

  public static int getCount()
  {
    return cached.size();
  }
}