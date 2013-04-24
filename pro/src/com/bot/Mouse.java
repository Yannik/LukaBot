package com.bot;

import java.applet.Applet;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.util.Random;

import com.bot.api.Utils;

public class Mouse {
	private static Applet client;
	private static double mouseSpeed = 1.8;
	private static Utils util = new Utils();
	public static boolean mouseMoving = false;
	public static Point mousePosition = new Point(100, 0);


	public Mouse(Applet client){
		this.client = client;
	}

	public static void rightClick(int x, int y) throws InterruptedException{
		move(new Point(x, y));
		long lagTime = System.currentTimeMillis();
		client.dispatchEvent(new MouseEvent(client, MouseEvent.MOUSE_PRESSED, lagTime, MouseEvent.BUTTON3_MASK, x, y, 1, false, 1));
		lagTime += getRandom();
		client.dispatchEvent(new MouseEvent(client, MouseEvent.MOUSE_RELEASED, lagTime, MouseEvent.BUTTON3_MASK, x, y, 1, false, 1));
		client.dispatchEvent(new MouseEvent(client, MouseEvent.MOUSE_CLICKED, lagTime, MouseEvent.BUTTON3_MASK, x, y, 1, false, 1));
		util.sleep(1000, 2000);
	}

	public static void rightClick() throws InterruptedException{
		long lagTime = System.currentTimeMillis();
		client.dispatchEvent(new MouseEvent(client, MouseEvent.MOUSE_PRESSED, lagTime, MouseEvent.BUTTON3_MASK, mousePosition.x, mousePosition.y, 1, false, 1));
		lagTime += getRandom();
		client.dispatchEvent(new MouseEvent(client, MouseEvent.MOUSE_RELEASED, lagTime, MouseEvent.BUTTON3_MASK, mousePosition.x,  mousePosition.y , 1, false, 1));
		client.dispatchEvent(new MouseEvent(client, MouseEvent.MOUSE_CLICKED, lagTime, MouseEvent.BUTTON3_MASK, mousePosition.x,  mousePosition.y, 1, false, 1));
		util.sleep(1000, 2000);
	}

	private static int getRandom() throws InterruptedException{
		Random rand = new Random();
		return rand.nextInt(50) + 40;
	}

	private static int getRandom(int range) throws InterruptedException{
		Random rand = new Random();
		return rand.nextInt(range) + 1;
	}

	private static void sleep(int time) throws InterruptedException{
		Thread.sleep(time);
	}


	public static void leftclick(int x, int y) throws InterruptedException{
		move(new Point(x, y));
		long lagTime = System.currentTimeMillis();
		client.dispatchEvent(new MouseEvent(client, MouseEvent.MOUSE_PRESSED, lagTime, MouseEvent.BUTTON1_MASK, x, y, 1, false, 1));
		lagTime += getRandom();
		client.dispatchEvent(new MouseEvent(client, MouseEvent.MOUSE_RELEASED, lagTime, MouseEvent.BUTTON1_MASK, x, y, 1, false, 1));
		client.dispatchEvent(new MouseEvent(client, MouseEvent.MOUSE_CLICKED, lagTime, MouseEvent.BUTTON1_MASK, x, y, 1, false, 1));
		util.sleep(1000, 2000);
	}

	public static void leftclick(Point x) throws InterruptedException{
		leftclick(x.x, x.y);
	}



	public static void move(Point destination) throws InterruptedException{
		FocusManager.readyForInput(	client);
		moveSpeedRandom(destination);
	}

	public static void move(int x, int y) throws InterruptedException{
		FocusManager.readyForInput(	client);
		moveSpeedRandom(new Point(x,y));
	}


	private static void moveSpeedRandom(Point Destination) throws InterruptedException{
		Point origin = mousePosition;		
		mouseMoving = true;
		Point Difference;

		int whichWay = 1;

		switch(whichWay){
		case 0: //Do nothing
		break;
		case 1: //Randomly go past the destination point and back to the correct point.
			Point newDest = new Point((int)(Destination.getX() + getRandom(20)), (int)(Destination.getY()  +  getRandom(20)));
			Difference = new Point((int)(newDest.getX() - mousePosition.getX()),(int)(newDest.y - mousePosition.getY()));

			for(double Current = 0; Current < 1; Current+= (mouseSpeed/Math.sqrt(Math.pow(Difference.getX(),2) + Math.pow(Difference.getY(),2)))){
				mousePosition = new Point((int)origin.getX() + (int)(Difference.getX() * Current), (int)origin.getY() + (int)(Difference.getY() * Current));
				silentMove(mousePosition.x, mousePosition.y);
				sleep(5);
			}

			mousePosition = new Point(newDest.x, newDest.y);
			origin = mousePosition;
			sleep(100);
			break;
		case 2: //Do nothing
			break;
		}

		Difference = new Point((int)(Destination.getX() - mousePosition.getX()),(int)(Destination.getY() - mousePosition.getY()));

		for(double Current = 0; Current < 1; Current+= (mouseSpeed/Math.sqrt(Math.pow(Difference.getX(),2) + Math.pow(Difference.getY(),2)))){
			mousePosition = new Point((int)origin.getX() + (int)(Difference.getX() * Current), (int)origin.getY() + (int)(Difference.getY() * Current));
			silentMove(mousePosition.x, mousePosition.y);
			sleep(5);
		}

		mousePosition = new Point(Destination.x, Destination.y);
		sleep(50);
		mouseMoving = false;
	}

	private void moveSpeed(Point Destination) throws InterruptedException{
		Point origin = mousePosition;		
		mouseMoving = true;
		Point Difference;

		Difference = new Point((int)(Destination.getX() - mousePosition.getX()),(int)(Destination.getY() - mousePosition.getY()));

		for(double Current = 0; Current < 1; Current+= (mouseSpeed/Math.sqrt(Math.pow(Difference.getX(),2) + Math.pow(Difference.getY(),2)))){
			mousePosition = new Point((int)origin.getX() + (int)(Difference.getX() * Current), (int)origin.getY() + (int)(Difference.getY() * Current));
			silentMove(mousePosition.x, mousePosition.y);
			sleep(5);
		}

		mousePosition = new Point(Destination.x, Destination.y);
		sleep(50);
		mouseMoving = false;
	}

	private static void silentMove(int x, int y) throws InterruptedException{
		client.dispatchEvent(new MouseEvent(client, MouseEvent.MOUSE_MOVED, System.currentTimeMillis(), 0, x, y, 1, false, 0));
	}


	public double getMouseSpeed() throws InterruptedException{
		return mouseSpeed;
	}

	public void setMouseSpeed(double mouseSpeed) throws InterruptedException{
		this.mouseSpeed = mouseSpeed;
	}

	public boolean isMouseMoving() {
		return mouseMoving;
	}

	public static void click(boolean left) {
		try {
			click(mousePosition.x , mousePosition.y, left ? 1 : 3);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void click(int x, int y, boolean left) throws InterruptedException {
		click(x, y, left ? 1 : 3);
	}

	public static void click(int x, int y, int button) throws InterruptedException {
		if(x != mousePosition.x && y != mousePosition.y) {
			move(new Point(x, y));
		}
		long lagTime = System.currentTimeMillis();
		client.dispatchEvent(new MouseEvent(client, MouseEvent.MOUSE_PRESSED, lagTime, MouseEvent.BUTTON1_MASK, x, y, 1, false, 1));
		lagTime += getRandom();
		client.dispatchEvent(new MouseEvent(client, MouseEvent.MOUSE_RELEASED, lagTime, MouseEvent.BUTTON1_MASK, x, y, 1, false, 1));
		client.dispatchEvent(new MouseEvent(client, MouseEvent.MOUSE_CLICKED, lagTime, MouseEvent.BUTTON1_MASK, x, y, 1, false, 1));
		util.sleep(100, 200);

	}

	public Point getMousePosition() {
		return mousePosition;
	}

	public void setMouseMoving(boolean mouseMoving) {
		this.mouseMoving = mouseMoving;
	}

	public void setMousePosition(Point mousePosition) {
		this.mousePosition = mousePosition;
	}
}