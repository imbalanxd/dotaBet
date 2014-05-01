package com.dota.DotABet.model;

import com.dota.DotABet.R;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/19
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Item
{
	private static int itemNo = -1;

	public final static int ITEM_RARE = 1;
	public final static int ITEM_UNCOMMON = 2;
	public final static int ITEM_COMMON = 3;
	public final static int ITEM_KEY = 4;
	public static int [] ITEM_COLOUR = {};

	private int identifier;
	private String name;
	private String type;
	private String code;
	private String hero;
	private String piece;
	private Match match;

	public Item()
	{
		itemNo++;
		identifier = itemNo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setHero(String hero) {
		this.hero = hero;
	}

	public void setPiece(String piece) {
		this.piece = piece;
	}

	public void setMatch(Match match) {
		this.match = match;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getCode() {
		return code;
	}

	public String getHero() {
		return hero;
	}

	public String getPiece() {
		return piece;
	}

	public Match getMatch() {
		return match;
	}

	public int getColour()
	{
		if(getType().equals("Rare"))
			return R.color.item_rare;
		else if(getType().equals("Uncommon"))
			return R.color.item_uncommon;
		else if(getType().equals("Common"))
			return R.color.item_common;
		else
			return R.color.item_key;
	}

	public int getIdentifier() {
		return identifier;
	}

	public boolean equals(Item _item)
	{
		if(_item == null)
			return false;
		else if(this.code.equalsIgnoreCase(_item.getCode()))
			return true;
		return false;
	}
}
