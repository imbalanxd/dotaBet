package com.dota.DotABet.model;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/19
 * Time: 10:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class Bet
{
	private Match match;
	private boolean won;
	private boolean lost;
	private ArrayList<Item> placed;
	private ArrayList<Item> winnings;
	public int net;

	public void setMatch(Match match)
	{
		this.match = match;
	}

	public void setWon(boolean won)
	{
		this.won = won;
	}

	public void setLost(boolean won)
	{
		this.won = won;
	}

	public void setPlaced(ArrayList<Item> placed)
	{
		this.placed = placed;
	}

	public void setWinnings(ArrayList<Item> winnings)
	{
		this.winnings = winnings;
	}

	public Match getMatch()
	{
		return match;
	}

	public boolean isWon()
	{
		return won;
	}

	public boolean isLost()
	{
		return lost;
	}

	public boolean isClosed()
	{
		return !won && !lost;
	}

	public Item getPlaced(int index)
	{
		if(index >= placed.size())
			return null;
		return placed.get(index);
	}

	public ArrayList<Item> getPlaced()
	{
		if(placed == null)
			placed = new ArrayList<Item>();
		return placed;
	}

	public Item getWinnings(int index)
	{
		if(index >= winnings.size())
			return null;
		return winnings.get(index);
	}

	public ArrayList<Item> getWinnings()
	{
		if(winnings == null)
			winnings = new ArrayList<Item>();
		return winnings;
	}

	public int getNet() {
		return net;
	}

	public void setNet(int net) {
		this.net = net;
	}
}
