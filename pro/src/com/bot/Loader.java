package com.bot;

import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.GraphicsEnvironment;
import java.awt.MenuBar;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.JarFile;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.xml.soap.Node;

import org.objectweb.asm.tree.ClassNode;


import com.bot.accessors.Client;
import com.bot.accessors.NPC;
import com.bot.accessors.Player;
import com.bot.accessors.PlayerAccessor;
import com.bot.api.Utils;
import com.bot.api.methods.Camera;
import com.bot.api.methods.GroundItem;
import com.bot.api.methods.GroundItems;
import com.bot.api.methods.Inventory;
import com.bot.api.methods.Item;
import com.bot.api.methods.Npc;
import com.bot.api.methods.Npcs;
import com.bot.api.methods.Players;
import com.bot.utils.JarUtils;
public class Loader {
	public boolean poo;
	private JMenu ps;
	public static Applet applet;
	public static Client client;
	public static HashMap<String, ClassNode> CLASSES = new HashMap<>();
	public static int fieldsHooked = 0;

	public Loader() throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		CLASSES = JarUtils.parseJar(new JarFile(""));
		Injector.load();
		final JFrame frame = new JFrame("LukaBot");	
		frame.setVisible(true);
		frame.setResizable(true);
		final File gamepack = new File("");	
		JarUtils.dumpClasses(CLASSES, gamepack.toPath());
		URLClassLoader cl = new URLClassLoader(new URL[] { gamepack.toURI().toURL() });
		client = (Client) cl.loadClass("client").newInstance();
		applet = (Applet) client;
		applet.init();
		applet.start();
		applet.setLocation(frame.getWidth()/2 , frame.getHeight()/2 );
		frame.setLayout(new BorderLayout());
		frame.add(applet, BorderLayout.CENTER);
		applet.setLocation(frame.getWidth()/2 , frame.getHeight()/2 );
		frame.pack();
		final JMenuBar menuBar = new JMenuBar();
		final JButton button = new JButton("Print NPC names");
		ps = new JMenu("Debug");	
		JMenuItem npc = new JMenuItem("NPC");
		JMenuItem wid  = new JMenuItem("Widget Explorer");
		JMenuItem gItems = new JMenuItem("GroundItems");
		JMenuItem Invent = new JMenuItem("Inventory");
		ps.add(Invent);
		ps.add(npc);
		ps.add(gItems);
		ps.add(wid);
		frame.setSize(765,555);
		menuBar.add(ps);
		frame.setJMenuBar(menuBar);


	}


	public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException {
		new Loader();
	}



}
