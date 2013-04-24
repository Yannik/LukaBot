package com.bot.api.methods;

import java.io.PrintStream;
import java.util.ArrayList;


import com.bot.Loader;
import com.bot.accessors.Client;
import com.bot.accessors.Ground;
import com.bot.accessors.PhysicalObject;
import com.bot.accessors.RSObjectDef;

public class GameObjects
{
  private static ArrayList<ObjectDef> cached = new ArrayList();
  private static Client client = Loader.client;

  public static ObjectDef get(int i)
  {
    
      ObjectDef o = new ObjectDef();
      RSObjectDef d = client.getObjectCache(i);
      if (d != null) {
        o.setIndex(i);
        o.setName(d.getName());
        o.setActions(d.getActions());
        return o;
      }
   
    if (cached.size() == 0)
      loadDefinitions();
    try
    {
      return (ObjectDef)cached.get(i);
    } catch (Exception e) {
      loadDefinitions();
    }
    return (ObjectDef)cached.get(0);
  }

  public static void loadDefinitions()
  {
    cached.clear();
    int i = 0;
    while (i < 40000) {
      try {
        ObjectDef o = new ObjectDef();
        RSObjectDef d = client.getObjectCache(i);
        if (d != null) {
          o.setIndex(i);
          o.setName(d.getName());
          o.setActions(d.getActions());
          cached.add(o);
        }
        i++;
      } catch (ArrayIndexOutOfBoundsException e) {
        break;
      } catch (Exception e) {
        System.out.println("Exception while loading objects: " + 
          e.toString() + " @ index: " + i);
        i++;
      }
    }
    System.gc();
    System.out.println("Object definitions loaded: " + getCount());
  }

  public static int getCount()
  {
    return cached.size();
  }

  public static GameObject getObjectAt(int x, int y)
  {
    Client client = Loader.client;
    try {
   Ground ground =  client.getWorldController().getGroundArray()[client.getPlane()][x][y];
    if (ground != null) {
      PhysicalObject object1 = ground.getPhysicalObject1();
      PhysicalObject object2 = ground.getPhysicalObject2();
      PhysicalObject object3 = ground.getPhysicalObject3();
      PhysicalObject object4 = ground.getPhysicalObject4();
      if (object4 != null) {
        return new GameObject(object4,x,y);
      }
      PhysicalObject[] object5 = ground.getInteractivePhysicalObjects();
      for (PhysicalObject obj5 : object5) {
        if (obj5 != null) {
          return new GameObject(obj5,x,y);
        }
      }
      if (object1 != null) {
        return new GameObject(object1,x,y);
      }
      if (object2 != null) {
        return new GameObject(object2,x,y);
      }
      if (object3 != null) {
        return new GameObject(object3,x,y);
      }
    }
    } catch(NullPointerException|ArrayIndexOutOfBoundsException e){}
    return null;
  }

  public static GameObject[] getObjects(int range)
  {
    return getObjects(Players.getLocalPlayer().getLocation(), range);
  }

  public static GameObject[] getObjects(Tile tile, int range)
  {
    try
    {
      Client client = Loader.client;
      ArrayList out = new ArrayList();
      int X = tile.getX() - client.getBaseX();
      int Y = tile.getY() - client.getBaseY();
      int minX = X - range;
      int minY = Y - range;
      int maxX = X + range;
      int maxY = Y + range;
      for (int x = minX; x < maxX; x++) {
        for (int y = minY; y < maxY; y++) {
          GameObject o = getObjectAt(x, y);
          if (o != null) {
            out.add(o);
          }
        }
      }
      return (GameObject[])out.toArray(new GameObject[out.size()]);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  public static GameObject getNearest(int[] ids)
  {
    return getNearest(Players.getLocalPlayer().getLocation(), ids);
  }

  public static GameObject getNearest(String[] names)
  {
    return getNearest(Players.getLocalPlayer().getLocation(), names);
  }

  public static GameObject getNearest(Tile tile, int[] indexes)
  {
    GameObject nearest = null;
    double lastDistance = -1.0D;
    GameObject[] objects = getObjects();
    if (objects == null) {
      return null;
    }
    for (GameObject object : objects) {
      for (int index : indexes) {
        if (object.getID() == index) {
          double distance = object.getLocation().getDistance(tile);
          if ((lastDistance == -1.0D) || (lastDistance > distance)) {
            lastDistance = distance;
            nearest = object;
          }
        }
      }
    }
    return nearest;
  }

  public static GameObject getNearest(Tile tile, String[] names)
  {
    GameObject nearest = null;
    double lastDistance = -1.0D;
    GameObject[] objects = getObjects();
    if (objects == null) {
      return null;
    }
    for (GameObject object : objects) {
      for (String name : names) {
    	  if(object.getDef() != null ) {
        if (object.getDef().equals(name)) {
          double distance = object.getLocation().getDistance(tile);
          if ((lastDistance == -1.0D) || (lastDistance > distance)) {
            lastDistance = distance;
            nearest = object;
          }
        }
    	  }
      }
    }
    return nearest;
  }

  public static GameObject[] getObjects(Filter<GameObject> f)
  {

    ArrayList objects = new ArrayList();
    for (int x = 0; x < 104; x++) {
      for (int y = 0; y < 104; y++) {
        GameObject object = getObjectAt(x, y);
        if ((object != null) && 
          (f.accept(object))) {
          objects.add(object);
        }
      }
    }

    return (GameObject[])objects.toArray(new GameObject[objects.size()]);
  }

  public static GameObject[] getObjects()
  {

    ArrayList objects = new ArrayList();
    for (int x = 0; x < 104; x++) {
      for (int y = 0; y < 104; y++) {
        GameObject object = getObjectAt(x, y);
        if (object != null) {
          objects.add(object);
        }
      }
    }
    return (GameObject[])objects.toArray(new GameObject[objects.size()]);
  }
}