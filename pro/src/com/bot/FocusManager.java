package com.bot;


import java.awt.Component;
import java.awt.event.FocusEvent;


public class FocusManager {

	public static void giveFocus(Component target) {
		FocusEvent fe = new FocusEvent(target, FocusEvent.FOCUS_GAINED, false);
		target.dispatchEvent(fe);
	}

	public static void loseFocus(Component target) {
		FocusEvent fe = new FocusEvent(target, FocusEvent.FOCUS_LOST, true);
		target.dispatchEvent(fe);
	}

	public static void readyForInput(Component target) {
		try{
			if (!target.hasFocus())
				giveFocus(target);

		}catch(NullPointerException E ) {}

	}
}
