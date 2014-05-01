package com.dota.DotABet.webview;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.webkit.WebView;
import com.dota.DotABet.R;
import com.dota.DotABet.application.D2LApplication;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/15
 * Time: 3:30 PM
 * To change this template use File | Settings | File Templates.
 */
public class D2LWebView extends WebView
{
	public static final int LOGIN_COMPLETE = 0;
	public static final int LOGIN_PAGE = 1;
	public static final int BET_HISTORY = 2;

	private D2LWebViewClient d2lWebViewClient;

    public D2LWebView(Context context)
    {
        super(context);
		init();
    }

    public D2LWebView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
		init();
    }

    public D2LWebView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
		init();
    }

    private void init()
    {
        getSettings().setJavaScriptEnabled(true);
		//getSettings().setLoadsImagesAutomatically(false);
		d2lWebViewClient = new D2LWebViewClient(getResources());
		setWebViewClient(d2lWebViewClient);
		loadLogin();
    }

    private void loadLogin()
    {
        loadUrl(getResources().getString(R.string.steam_login_url));
    }

	public void conceal()
	{
		this.setVisibility(View.GONE);
	}

	public static int processResponse(String url)
	{
		if(url.equals(D2LApplication.getContext().getString(R.string.d2l_base_url)))	//Login has completed
			return LOGIN_COMPLETE;
		else if(url.equals(D2LApplication.getContext().getString(R.string.steam_login_url)))
			return LOGIN_PAGE;
		else if(url.equals(D2LApplication.getContext().getString(R.string.d2l_bet_history_url)))
			return BET_HISTORY;
		else
			return -1;
	}
}
