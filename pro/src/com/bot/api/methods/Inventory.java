package com.bot.api.methods;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import com.bot.Loader;
import com.bot.api.Utils;

public class Inventory
{
  private static Widget inventory;

  public static Widget get()
  {
    if (inventory == null) {
      return Inventory.inventory = new Widget(Loader.client.getInterfaceCache()[3214]);
    }
    return inventory;
  }

  public static int getCount()
  {
    int c = 0;
    for (int item : get().getItems()) {
      if (item != -1) {
        c++;
      }
    }
    return c;
  }

  public static int getCount(int[] id)
  {
    int c = 0;
    for (int i = 0; i < get().getItems().length; i++) {
      int[] arrayOfInt = id; int j = id.length; for (int i1 = 0; i1 < j; i1++) { int index = arrayOfInt[i1];
        if (get().getItems()[i1] == index) {
          c += get().getStackSizes()[i1];
        }
      }
    }
    return c;
  }

  public static int getCount(String[] names)
  {
    int c = 0;
    for (Item i : getItems()) {
      String[] arrayOfString = names; int m = names.length; for (int k = 0; k < m; k++) { String name = arrayOfString[k];
        if (i.getDef().getItemName().contains(name)) {
          c += i.getStackSize();
        }
      }
    }
    return c;
  }

  public static boolean isFull()
  {
    return getCount() == 28;
  }

  public static Item[] getItems()
  {
    Widget inv = get();
    ArrayList arr = new ArrayList();
    int[] items = inv.getItems();
    int[] stacks = inv.getStackSizes();
    for (int i = 0; i < items.length; i++) {
      if (items[i] != -1) {
        arr.add(new Item(items[i], stacks[i], i));
      }
    }
    return (Item[])arr.toArray(new Item[arr.size()]);
  }

  public static Item[] getItems(int[] ids)
  {
    ArrayList items = new ArrayList();
    int[] arrayOfInt = ids; int j = ids.length; for (int i = 0; i < j; i++) { int id = arrayOfInt[i];
      for (Item i1 : getItems()) {
        if ((i1 != null) && (i1.getId() == id)) {
          items.add(i1);
        }
      }
    }
    return (Item[])items.toArray(new Item[items.size()]);
  }

  public static Item[] getItems(String[] names)
  {
    ArrayList items = new ArrayList();
    String[] arrayOfString = names; int j = names.length; for (int i = 0; i < j; i++) { String name = arrayOfString[i];
      for (Item i1 : getItems()) {
        if ((i1 != null) && (i1.getDef().getItemName().contains(name))) {
          items.add(i1);
        }
      }
    }
    return (Item[])items.toArray(new Item[items.size()]);
  }

  public static Item getItem(int id)
  {
    for (Item i : getItems()) {
      if ((i != null) && (i.getId() == id)) {
        return i;
      }
    }
    return null;
  }

  public static Item getItem(String name)
  {
	  try{
    for (Item i : getItems()) {
      if ((i != null) && (i.getDef().getItemName().contains(name))) {
        return i;
      }
    }
	  }catch (NullPointerException e){}
    return null;
  }

  public static boolean contains(int id)
  {
    return getCount(new int[] { id }) > 0;
  }

  public static boolean contains(String name)
  {
    return getCount(new String[] { name }) > 0;
  }

  public static void dropItem(int id, int amount)
  {
    for (int i2 = 0; i2 < amount; i2++) {
      Item i = getItem(id);
      if (i != null) {
        i.interact("drop");
        Utils.sleep(1000);
      }
    }
  }

  public static void dropAllOfItem(int id)
  {
    dropItem(id, 28);
  }

  public static void dropAllExcept(int[] ids)
  {
    List keep = Arrays.asList(new int[][] { ids });
    for (Item i : getItems())
      if ((i != null) && 
        (!keep.contains(Integer.valueOf(i.getId()))))
        dropAllOfItem(i.getId());
  }


}