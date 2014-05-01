package com.dota.DotABet.constants;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/20
 * Time: 3:09 PM
 * To change this template use File | Settings | File Templates.
 */
public class Constants
{
	public static class Intents
	{
		public static final String BET_HISTORY_INTENT = "com.dota.DotABet.betHistory";
		public static final String USER_LOGIN_INTENT = "com.dota.DotABet.userLogin";
		public static final String ITEM_GRAPH_INTENT = "com.dota.DotABet.itemGraph";

		public static final String [] FILTER_ARRAY = {BET_HISTORY_INTENT, USER_LOGIN_INTENT, ITEM_GRAPH_INTENT};
	}

	public static class Fragments
	{
		public static final short BET_HISTORY_FRAGMENT = 0;
		public static final short USER_LOGIN_FRAGMENT = 1;
		public static final short ITEM_GRAPH_FRAGMENT = 2;
	}

	public static class Data
	{
		public static final short USER_LOGIN_DATA = 0;
		public static final short BET_HISTORY_DATA = 1;
		public static final short MATCH_DATA = 2;
	}
}
