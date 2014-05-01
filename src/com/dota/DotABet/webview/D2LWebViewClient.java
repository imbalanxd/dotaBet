package com.dota.DotABet.webview;

import android.content.res.Resources;
import android.util.Log;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.dota.DotABet.R;
import com.dota.DotABet.application.D2LApplication;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/15
 * Time: 3:39 PM
 * To change this template use File | Settings | File Templates.
 */
public class D2LWebViewClient extends WebViewClient
{


	private Resources resources;
	private D2LJavascriptInterface d2lInterface;

	public D2LWebViewClient(Resources _resources)
	{
		super();
		resources =	_resources;
		d2lInterface = new D2LJavascriptInterface();
	}

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
		return handlePageLoad((D2LWebView)view, url);
    }

	private boolean handlePageLoad(D2LWebView view, String url)
	{
		if(resources != null && !url.startsWith(getString(R.string.steam_base_url)))
			view.conceal();
		view.loadUrl(url);
		return true;
	}

    @Override
    public void onPageFinished(WebView view, String url)
    {
        //super.onPageFinished(view, url);
		processResponse((D2LWebView)view, url);
    }

	private void processResponse(D2LWebView view, String url)
	{
		switch(D2LWebView.processResponse(url))
		{
			case D2LWebView.LOGIN_PAGE:
				Log.i("D2L", "process URL: Login Page");
				break;
			case D2LWebView.LOGIN_COMPLETE:

				//TODO clean up steam id extraction
				String cookies = CookieManager.getInstance().getCookie(url)+";";
				D2LApplication.getDatastore().userLoggedIn(cookies.substring(cookies.indexOf(" id=")+4, cookies.indexOf(";", cookies.indexOf(" id="))));

				Log.i("D2L", "process URL: Login Complete ");
				requestBetHistory(view);
				break;
			case D2LWebView.BET_HISTORY:
				Log.i("D2L", "process URL: Bet History");
				scrubBetHistory(view);
				break;
			default:
				Log.i("D2L", "process URL: Unknown " + url);
				break;
		}
	}

	private boolean isLoginComplete(WebView view, String url)
	{
		if(url.equals(getString(R.string.d2l_base_url)))
			return true;
		return false;
	}

	public void requestBetHistory(D2LWebView view)
	{
		view.loadUrl(getString(R.string.d2l_bet_history_url));
	}

	private void scrubBetHistory(D2LWebView view)
	{
		view.addJavascriptInterface(d2lInterface, "d2l");
		view.loadUrl("javascript:var bets = []; var betElements = document.getElementsByTagName('tr');for(var i=0; i< betElements.length/3; i++){var bet = {};var matchElement = betElements[i*3];bet.match = {};bet.match.teamOne = matchElement.getElementsByTagName('a')[1].innerHTML;bet.match.teamTwo = matchElement.getElementsByTagName('a')[3].innerHTML; /*bet.match.odds = [0.5,0.5,0.5,0.5];*/bet.match.date = matchElement.getElementsByTagName('td')[6].innerHTML;/*bet.match.bestOf = 3;*/bet.match.series = matchElement.getElementsByTagName('td')[5].innerHTML;bet.match.id = matchElement.getElementsByTagName('a')[1].href.substring(matchElement.getElementsByTagName('a')[1].href.indexOf(\"m=\")+2);bet.won = (matchElement.getElementsByTagName('span')[0].innerHTML == \"won\");bet.lost = (matchElement.getElementsByTagName('span')[0].innerHTML == \"lost\");bet.placed= [];var placedElements = betElements[i*3 + 1].getElementsByClassName('item');for(var j=0; j< placedElements.length; j++){var item = {};item.name = placedElements[j].getElementsByTagName('img')[0].alt;item.type = placedElements[j].getElementsByTagName('div')[1].innerHTML.replace(/&nbsp;/g,\"\");item.code = placedElements[j].getElementsByTagName('img')[0].src;item.hero =placedElements[j].getElementsByClassName('hero')[0].innerHTML;item.piece = placedElements[j].getElementsByClassName('type')[0].innerHTML;bet.placed.push(item)};bet.winnings= [];var winningsElements = betElements[i*3 + 2].getElementsByClassName('item');for(var k=0; k< winningsElements.length; k++){var item = {};item.name = winningsElements[k].getElementsByTagName('img')[0].alt;item.type = winningsElements[k].getElementsByTagName('div')[1].innerHTML.replace(/&nbsp;/g,\"\");item.code = winningsElements[k].getElementsByTagName('img')[0].src;item.hero =winningsElements[k].getElementsByClassName('hero')[0].innerHTML;item.piece = winningsElements[k].getElementsByClassName('type')[0].innerHTML;bet.winnings.push(item)};bets.push(bet)};window.d2l.processBetHistory(JSON.stringify(bets))");
		//view.loadUrl("javascript:window.d2l.processBetHistory('<head>'+document.getElementsByTagName('html')[0].innerHTML+'</head>');");
	}

	private String getString(int _id)
	{
		return resources.getString(_id);
	}
}
