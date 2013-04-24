package com.bot;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;
import java.net.URL;
import java.net.URLConnection;

import com.bot.utils.JarUtils;


public class Injector {
	public static String[] hooks;
	public static URL url = null;
	public static BufferedReader br;
	public static URLConnection urlConnection;
	public static String strLine;

	public static void load() {
		try {
			File DatFile = new File("C:/Users/Luka/Desktop/07Bot/Bot/ds.dat");
			if ((DatFile.exists())){
				url = DatFile.toURI().toURL();
			}
			System.out.println(url);
			URLConnection urlConnection = url.openConnection();
			BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

			while ((strLine = br.readLine()) != null) {
				String[] hooks = strLine.split(" ");
				try {
					if (hooks[0].equals("getn")) {
						if(hooks.length == 5) {
							JarUtils.addGetterNonStatic(Loader.CLASSES.get(hooks[1]), hooks[2], hooks[3], hooks[4]);	
						} else if(hooks.length == 6) {
							JarUtils.addStaticFieldAccessor(Loader.CLASSES.get(hooks[1]), hooks[2], hooks[6], hooks[4], hooks[3], hooks[5]);
						}
					}
				} catch (ArrayIndexOutOfBoundsException e){
					e.printStackTrace();
				}

				if (hooks[0].equals("injectinter")) {
					JarUtils.injectInterface(Loader.CLASSES.get(hooks[2]), hooks[1]);

				}

				if (hooks[0].equals("get")) {
					JarUtils.addGetterMethod(Loader.CLASSES.get(hooks[1]), hooks[2], hooks[3], hooks[4]);
				}


				if (hooks[0].equals("getMethod")) {
					try{
						JarUtils.addMethodGetter(Loader.CLASSES.get("client"), Loader.CLASSES.get("ObjectDef"), "forID", "getObjectCache", "Lcom/bot/accessors/RSObjectDef;", "(I)");
					}catch (NullPointerException c) {}
				}

			}


		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		JarUtils.addGetterNonStatic(Loader.CLASSES.get("RSApplet"), "graphics", "getRealGraphics", "Ljava/awt/Graphics;");

	}
}

