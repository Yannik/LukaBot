package com.bot.api.methods;


import java.awt.Point;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.bot.Mouse;
import com.bot.Loader;
import com.bot.accessors.Client;
import com.bot.api.Utils;


public class Menu
{
	private static Mouse mouse = new Mouse(Loader.applet);
	private static Client client = Loader.client;
	public static int x0 = 4;
	public static int y0 = 4;
	public static int x1 = 519;
	public static int y1 = 168;
	public static int x2 = 17;
	public static int y2 = 338;
	public static int x3 = 519;
	public static int y3 = 0;
	static Mouse  fd = new Mouse(Loader.applet);

	public static Point getMenuLocation() {
		if (client == null) {
			client = Loader.client;
		}
		int x = client.getMenuOffsetX();
		int y = client.getMenuOffsetY();
		int screenArea = client.getMenuScreenArea();
		if (screenArea == 0) {
			x += x0;
			y += y0;
		} else if (screenArea == 1) {
			x += x1;
			y += y1;
		} else if (screenArea == 2) {
			x += x2;
			y += y2;
		} else if (screenArea == 3) {
			x += x3;
			y += y3;
		}
		return new Point(x, y);
	}

	public static Point getPoint(int i) {
		if (client == null) {
			client = Loader.client;
		}
		Point loc = getMenuLocation();
		return new Point(loc.x + client.getMenuWidth() / 2, loc.y + 25 + 15 * i);
	}

	public static void interact(String action) {
		if (client == null) {
			client = Loader.client;
		}
		String[] menuitems = getMenuItems();
		for (int x = 0; x < menuitems.length; x++)
			if (menuitems[x].contains(action)) {
				interact(x);
				break;
			}
	}

	public static void interact(int i)
	{
		Mouse p = new Mouse(Loader.applet);
		if (client == null) {
			client = Loader.client;
		}

		if (!isOpen()) {
			try {
				p.rightClick();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Utils.sleep(50);
			if (isOpen()) {
				interact(i);
				return;
			}
			return;
		}
		Point pd = getPoint(i);
		try {
			fd.move(pd.x, pd.y);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Utils.sleep(200);
		try {
			p.click(pd.x, pd.y, true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static boolean isOpen() {
		if (client == null) {
			client = Loader.client;
		}
		return client.isMenuOpen();
	}


	public static int getMenuActionRow() {
		if (client == null) {
			client = Loader.client;
		}
		return client.getMenuActionRow();
	}

	public static String getCurrentAction() {
		if (client == null) {
			client = Loader.client;
		}
		return getMenuItems()[0];
	}

	public static String[] getMenuItems() {
		if (client == null) {
			client = Loader.client;
		}
		Pattern TAG = Pattern.compile("@{1}[a-z|A-Z|0-9]{3}@{1}");
		String[] arr = getMenuActionNames();
		ArrayList menu = new ArrayList();
		for (int i = getMenuActionRow() - 1; i >= 0; i--) {
			menu.add(TAG.matcher(arr[i]).replaceAll(""));
		}
		return (String[])menu.toArray(new String[menu.size()]);
	}

	public static String[] getMenuActionNames() {
		return client.getMenuActionNames();
	}

}