package com.bot.api.methods;

import com.bot.Loader;
import com.bot.accessors.Client;
import com.bot.accessors.PlayerAccessor;


public class Player {

	private PlayerAccessor player;
	private Client client;

	public Player(PlayerAccessor p) {
		client = Loader.client;
		this.player = p;

	}

	public int getAnimation() {
		try{
		return player.getAnimimation();
		}catch(NullPointerException | ArrayIndexOutOfBoundsException e) {}
		return 0;
	}

	public int getCombatLevel() {
		return player.getCombatLevel();
	}

	public String getName() {
		return player.getName();
	}

	public int getHealth()
	{
		return this.player.getCurrentHp();
	}


	public int getX() {
		int i = (this.client.getBaseX()+ (this.client.getLocalPlayer().getX() >> 7));
		return i;
	}

	public int getY() {
		int i2 = (this.client.getBaseY()+ (this.client.getLocalPlayer().getY() >> 7)); 
		return i2;
	}
	
	public Tile getLocation() {
		return new Tile(getX(),getY());
	}
	
	public boolean isInCombat() {
		return player.getInteractingNPC() != -1;
	}



}
