package com.bot.api.methods;

import com.bot.Loader;
import com.bot.accessors.Client;
import com.bot.accessors.PlayerAccessor;


public class Players {
	private static Client client;
	
	public Players() {
		client = Loader.client;
	}
	
	public static Player getLocalPlayer() {
	try{
		return new Player( Loader.client.getLocalPlayer());
	}catch(NullPointerException e) {}
	return null;
	}
	
	

}
