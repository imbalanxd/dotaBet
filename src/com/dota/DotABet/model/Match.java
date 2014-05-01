package com.dota.DotABet.model;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/19
 * Time: 10:54 PM
 * To change this template use File | Settings | File Templates.
 */
public class Match
{
	private String teamOne;
	private String teamTwo;
	private String winner;
	private String id;
	private String series;
	private ArrayList<ArrayList<String>> teamOneOdds; //Rares, Uncommons, Commons, Keys
	private ArrayList<ArrayList<String>> teamTwoOdds; //Rares, Uncommons, Commons, Keys
	private String time;
	private String items;
	private String bestOf;

	public String getTitle()
	{
		return getTeamOne() + " vs " + getTeamTwo();
	}

	public String getTeamOneImage()
	{
		return String.format("http://cdn.dota2lounge.com/img/teams/%s.jpg", teamOne);
	}

	public String getTeamTwoImage()
	{
		return String.format("http://cdn.dota2lounge.com/img/teams/%s.jpg", teamTwo);
	}

	public String getBestOf() {
		return bestOf;
	}

	public void setTeamOne(String teamOne) {
		this.teamOne = teamOne;
	}

	public void setTeamTwo(String teamTwo) {
		this.teamTwo = teamTwo;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSeries(String series) {
		this.series = series;
	}

//	public void setTeamOneOdds(double[] odds) {
//		this.teamOneOdds = odds;
//	}
//
//	public void setTeamTwoOdds(double[] odds) {
//		this.teamTwoOdds = odds;
//	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setBestOf(String bestOf) {
		this.bestOf = bestOf;
	}

	public String getTeamOne() {
		return teamOne;
	}

	public String getTeamTwo() {
		return teamTwo;
	}

	public String getId() {
		return id;
	}

	public String getSeries() {
		return series;
	}

//	public double[] getTeamOneOdds() {
//		return teamOneOdds;
//	}
//
//	public double[] getTeamTwoOdds() {
//		return teamTwoOdds;
//	}

	public String getTime() {
		return time;
	}
}
