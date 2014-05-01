package com.dota.DotABet.webview;

import android.util.Log;
import com.dota.DotABet.application.D2LApplication;
import com.dota.DotABet.constants.Constants;
import com.dota.DotABet.model.Bet;
import com.dota.DotABet.model.BetHistory;
import com.dota.DotABet.parse.ParseUtility;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/15
 * Time: 3:43 PM
 * To change this template use File | Settings | File Templates.
 */
public class D2LJavascriptInterface
{
	//String betHistoryHtml;

	public void processBetHistory(String betData)
	{
		D2LApplication.getDatastore().onJSONDownloaded(Constants.Data.BET_HISTORY_DATA, betData);
	}
}
