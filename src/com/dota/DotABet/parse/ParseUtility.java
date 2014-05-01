package com.dota.DotABet.parse;

import com.dota.DotABet.model.*;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/20
 * Time: 12:48 AM
 * To change this template use File | Settings | File Templates.
 */
public class ParseUtility
{
	public static BetHistory parseBet(String json)
	{
		try
		{
			Gson gson = new Gson();
			Bet[] bets = gson.fromJson(json, Bet[].class);
			return new BetHistory(bets);
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public static User parseUser(String json)
	{
		json = json.substring(json.indexOf("["), json.indexOf("]") + 1).trim();
		try
		{
			Gson gson = new Gson();
			User[] user = gson.fromJson(json, User[].class);
			return user[0];
		}
		catch(Exception e)
		{
			return null;
		}
	}

	public static MatchHistory parseMatches(String json)
	{
		try
		{
			Gson gson = new Gson();
			Match[] matches = gson.fromJson(json, Match[].class);
			return new MatchHistory(matches);
		}
		catch(Exception e)
		{
			return null;
		}
	}
}
