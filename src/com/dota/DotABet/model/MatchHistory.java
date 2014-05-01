package com.dota.DotABet.model;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/04/21
 * Time: 2:52 PM
 * To change this template use File | Settings | File Templates.
 */
public class MatchHistory
{
	private Match[] matches;

	public MatchHistory()
	{

	}

	public MatchHistory (Match[] _matches)
	{
		setMatches(_matches);
	}

	public void setMatches(Match[] matches)
	{
		this.matches = matches;
	}

	public Match[] getMatches()
	{
		return matches;
	}
}
