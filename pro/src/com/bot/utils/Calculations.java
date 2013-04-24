package com.bot.utils;

import java.awt.Point;
import java.awt.Rectangle;
import java.io.ObjectInputStream.GetField;

import com.bot.Loader;
import com.bot.accessors.Client;
import com.bot.accessors.RSInterface;
import com.bot.api.Utils;
import com.bot.api.methods.Players;
import com.bot.api.methods.Tile;



public class Calculations {


	  public static final int[] CURVESIN = new int[2048];
	    public static final int[] CURVECOS = new int[2048];
	    public static int[] SINE = new int[2048];

	    public static int[] COSINE = new int[2048];

	
	    static {
	        for (int i = 0; i < 2048; i++) {
	            CURVESIN[i] = (int)(65536D * Math.sin((double)i * 0.0030679614999999999D));
	            CURVECOS[i] = (int)(65536D * Math.cos((double)i * 0.0030679614999999999D));
	        }
	    }
	    
	  public static int tileHeight(int plane, int x, int y) {
		  final Client client = Loader.client;
	        final int[][][] groundHeights = client.getintGroundArray();
	        if(groundHeights == null)
	            return 0;
	        int x1 = x >> 7;
	        int y1 = y >> 7;
	        if(x1 < 0 || y1 < 0 || x1 > 103 || y1 > 103)
	            return 0;
	        int x2 = x & 0x7f;
	        int y2 = y & 0x7f;
	        int zIndex = plane;
	        if(zIndex > 3 && (client.getbyteGroundArray() [1][x1][y1] & 2) == 2)
	            zIndex++;
	        int i2 = (((-x2 + 128) * groundHeights[zIndex][x1][y1]) + (x2 * groundHeights[zIndex][x1 + 1][y1])) >> 7;
	        int j2 = ((groundHeights[zIndex][x1][1 + y1] * (128 - x2)) + (groundHeights[zIndex][1 + x1][1 + y1] * x2)) >> 7;
	        return ((i2 * (128 - y2)) - -(y2 * j2)) >> 7;
	    }

	    public static Point worldToScreen(int X, int Y, int height) {

	        if(X < 128 || Y < 128 || X > 13056 || Y > 13056) {
	            return new Point(-1, -1);
	        }
	        final Client client = Loader.client;
	        int Z = tileHeight(client.getPlane(), X, Y) - height;

	        X -= client.getCameraX();
	        Y -= client.getCameraY();
	        Z -= client.getCameraZ();

	        final int curveX = client.getxCameraCurve();
	        final int curveY = client.getyCameraCurve();
	        int curveCosX = CURVECOS[curveX];
	        int curveCosY = CURVECOS[curveY];

	        int curveSinX = CURVESIN[curveX];
	        int curveSinY = CURVESIN[curveY];

	        int tempCalculation = ((curveCosX * X) + (Y * curveSinX)) >> 16;
	        Y = ((Y * curveCosX) - (X * curveSinX)) >> 16;
	        X = tempCalculation;

	        tempCalculation = ((Z * curveCosY) - (Y * curveSinY)) >> 16;
	        Y = ((Z * curveSinY) - -(curveCosY * Y)) >> 16;
	        Z = tempCalculation;        

	        if(Y < 50) {
	            return new Point(-1, -1);
	        } else {
	            int calculatedScreenPosX = 256 + ((X << 9) / Y);
	            int calculatedScreenPosY = (Z << 9) / Y + 167;

	            return new Point(calculatedScreenPosX, calculatedScreenPosY);
	        }
	    }
	    
	    public static double distanceTo(Tile tile)
	    {
	      return distanceBetween(tile, Players.getLocalPlayer().getLocation());
	    }

	    public static double distanceBetween(Tile a, Tile b)
	    {
	      int x = b.getX() - a.getX();
	      int y = b.getY() - a.getY();
	      return Math.sqrt(x * x + y * y);
	    }
	    
	    public static double dist_tile(Tile src, Tile dst)
	    {
	      return Math.sqrt(Math.pow(src.getX() - dst.getX(), 2.0D) + Math.pow(src.getY() - dst.getY(), 2.0D));
	    }


	    public static Point tileToScreen(int x, int y)
	    {
	    final Client client = Loader.client;
	      x -= client.getBaseX();
	      y -= client.getBaseY();
	      return worldToScreen((x + 0.5D) * 128.0D, (y + 0.5D) * 128.0D, 0);
	    }


	    public static Point tileToScreen(Tile t) {
	        return tileToScreen(t.getX(), t.getY(), 0.5, 0.5, 0);
	    }

	    public static Point tileToScreen(int tileX, int tileY, double dX, double dY, int height) {
	        return worldToScreen((int)((tileX - Loader.client.getBaseX() + dX) * 128), (int)((tileY
	                - Loader.client.getBaseY() + dY) * 128), height);
	    }


	    public static Point worldToScreen(double X, double Y, int height)
	    {
		    final Client client = Loader.client;
	      try
	      {
	        if ((X < 128.0D) || (Y < 128.0D) || (X > 13056.0D) || (Y > 13056.0D)) {
	          return new Point(-1, -1);
	        }
	        int tileCalculation = tileHeight((int)X, (int)Y) - height;
	        X -= client.getCameraX();
	        tileCalculation -= client.getCameraZ();
	        int curvexsin = CURVESIN[client.getCameraX()];
	        int curvexcos = CURVECOS[client.getCameraX()];
	        Y -= client.getCameraY();
	        int curveysin = CURVESIN[client.getCameraY()];
	        int curveycos = CURVECOS[client.getCameraY()];
	        int calculation = curvexsin * (int)Y + (int)X * curvexcos >> 16;
	        Y = -(curvexsin * (int)X) + (int)Y * curvexcos >> 16;
	        X = calculation;
	        calculation = curveycos * tileCalculation - curveysin * (int)Y >> 16;
	        Y = curveysin * tileCalculation + (int)Y * curveycos >> 16;
	        tileCalculation = calculation;
	        int ScreenX = ((int)X << 9) / (int)Y + 256;
	        int ScreenY = (tileCalculation << 9) / (int)Y + 167;

	        return new Point(ScreenX, ScreenY); } catch (Exception e) {
	      }
	      return new Point(-1, -1);
	    }
	    
	    public static int tileHeight(int x, int y)
	    {
	        final Client client = Loader.client;
	      int[][][] ground = client.getintGroundArray();
	      int zidx = client.getPlane();
	      int x1 = x >> 7;
	      int y1 = y >> 7;
	      int x2 = x & 0x7F;
	      int y2 = 0x7F & y;

	      if ((x1 < 0) || (y1 < 0) || (x1 > 103) || (y1 > 103)) {
	        return 0;
	      }
	      if ((zidx < 3) && ((0x2 & client.getbyteGroundArray()[1][x1][y1]) == 2)) {
	        zidx++;
	      }
	      int i = ground[zidx][(1 + x1)][y1] * x2 + (128 + -x2) * ground[zidx][x1][y1] >> 7;
	      int j = ground[zidx][(1 + x1)][(1 + y1)] * x2 + ground[zidx][x1][(y1 + 1)] * (128 - x2) >> 7;

	      return j * y2 + (128 - y2) * i >> 7;
	    }

	   
	    public static int angleToTile(Tile t)
	    {
	      Tile me = Players.getLocalPlayer().getLocation();
	      int angle = (int)Math.toDegrees(Math.atan2(t.getY() - me.getY(), t.getX() - me.getX()));
	      return angle >= 0 ? angle : 360 + angle;
	    }

	    public static int tile_angle(Tile origin, Tile tile)
	    {
	      int degree = (int)Math.toDegrees(Math.atan2(tile.getY() - origin.getY(), tile.getX() - origin.getX()));
	      return degree >= 0 ? degree : 360 + degree;
	    }
	
	    public static final Point trans_tile_cam(Client client, int x, int y, int height)
	    {
	      if ((x < 128) || (y < 128) || (x > 13056) || (y > 13056)) {
	        return new Point(-1, -1);
	      }
	   
	      int z = tileHeight(client.getPlane(), x, y)-height;
	      x -= client.getCameraX();
	      z -= client.getCameraZ();
	      y -= client.getCameraY();

	      int pitch_sin = SINE[client.getCameraX()];
	      int pitch_cos = COSINE[client.getCameraX()];
	      int yaw_sin = SINE[client.getCameraZ()];
	      int yaw_cos = COSINE[client.getCameraZ()];

	      int _angle = y * yaw_sin + x * yaw_cos >> 16;

	      y = y * yaw_cos - x * yaw_sin >> 16;
	      x = _angle;
	      _angle = z * pitch_cos - y * pitch_sin >> 16;
	      y = z * pitch_sin + y * pitch_cos >> 16;
	      z = _angle;

	      if (y >= 50) {
	        return new Point(256 + (x << 9) / y, (_angle << 9) / y + 167);
	      }

	      return new Point(-1, -1);
	    }


	    public static Point trans_tile_screen(Client client, Tile tile, double dX, double dY, int height)
	    {
	      return trans_tile_cam(client, (int)((tile.getX() - client.getBaseX() + dX) * 128.0D), (int)((tile.getY() - client.getBaseY() + dY) * 128.0D), height);
	    }
	    
}