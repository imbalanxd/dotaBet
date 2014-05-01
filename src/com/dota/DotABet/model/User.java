package com.dota.DotABet.model;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/28
 * Time: 11:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class User
{
	private String steamid;
	private String personaname;
	private String avatarfull;

	public String getSteamID() {
		return steamid;
	}

	public String getName() {
		return personaname;
	}

	public String getImageURL() {
		return avatarfull;
	}

	public void setSteamID(String steamID) {
		this.steamid = steamID;
	}

	public void setName(String name) {
		this.personaname = name;
	}

	public void setImageURL(String imageURL) {
		this.avatarfull = imageURL;
	}
}
