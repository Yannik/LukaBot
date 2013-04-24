package com.bot;

import java.applet.Applet;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Random;

public class KeyBoard implements KeyListener {
	private static Applet app = Loader.applet;
	private static KeyListener keyListner;
	private static Applet applet;
	private static HashMap<Character, Character> specialChars;
	private static long pressTime;

	static
	{
		char[] spChars = { '~', '!', '@', '#', '%', '^', '&', '*', '(', ')', '_', '+', '{', '}', ':', '<', '>', '?', '"', '|' };
		char[] replace = { '`', '1', '2', '3', '5', '6', '7', '8', '9', '0', '-', '=', '[', ']', ';', ',', '.', '/', '\'', '\\' };
		specialChars = new HashMap(spChars.length);
		for (int x = 0; x < spChars.length; x++)
			specialChars.put(Character.valueOf(spChars[x]), Character.valueOf(replace[x]));
	}

	public KeyBoard() {
		applet = Loader.applet;
		this.keyListner =  applet.getKeyListeners()[0];
		applet.removeKeyListener(this.keyListner);
		applet.addKeyListener(this);    
	}

	private static KeyEvent[] createKeyClick(Component target, char c)
	{
		pressTime += 2L * getRandom();

		Character newChar = (Character)specialChars.get(Character.valueOf(c));
		int keyCode = Character.toUpperCase(newChar == null ? c : newChar.charValue());

		if ((Character.isLowerCase(c)) || ((!Character.isLetter(c)) && (newChar == null))) {
			KeyEvent pressed = new KeyEvent(target, 401, pressTime, 0, keyCode, c);
			KeyEvent typed = new KeyEvent(target, 400, pressTime, 0, 0, c);
			pressTime += getRandom();
			KeyEvent released = new KeyEvent(target, 402, pressTime, 0, keyCode, c);

			return new KeyEvent[] { pressed, typed, released };
		}
		return null;

	}

	private static long getRandom()
	{
		Random rand = new Random();
		return rand.nextInt(100) + 40;
	}

	public static void sendKeys(String s)
	{
		pressTime = System.currentTimeMillis();
		for (char c : s.toCharArray())
			for (KeyEvent ke : createKeyClick(app, c)) {
				try {
					Thread.sleep(5L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				keyListner.keyTyped(ke);
			}
	}

	public void pressKey(int keycode)
	{
		KeyEvent pressed = new KeyEvent(this.applet, 401, System.currentTimeMillis(), 0, 
				keycode, (char)keycode, 1);
		this.keyListner.keyPressed(pressed);
	}

	public void typeKey(int keycode)
	{
		KeyEvent typed = new KeyEvent(this.applet, 400, System.currentTimeMillis(), 0, 
				0, (char)keycode, 0);
		this.keyListner.keyTyped(typed);
	}

	public void releaseKey(int keycode)
	{
		KeyEvent released = new KeyEvent(this.applet, 402, System.currentTimeMillis(), 0, 
				keycode, (char)keycode, 1);
		this.keyListner.keyReleased(released);
	}



	@Override
	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}



	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}



	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

}


