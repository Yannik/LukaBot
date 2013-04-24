package com.bot.accessors;

public interface Ground extends Node {
	
	com.bot.accessors.PhysicalObject getPhysicalObject1();
	
	com.bot.accessors.PhysicalObject getPhysicalObject2();
	
	com.bot.accessors.PhysicalObject getPhysicalObject3();	
	
	com.bot.accessors.PhysicalObject getPhysicalObject4();
	
	com.bot.accessors.PhysicalObject[] getInteractivePhysicalObjects();
	
}
