package com.dota.DotABet.activity;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import com.dota.DotABet.R;
import com.dota.DotABet.application.D2LApplication;
import com.dota.DotABet.assets.SpriteSheet;
import com.dota.DotABet.datastore.D2LDataStore;
import com.dota.DotABet.fragment.BaseFragment;
import com.dota.DotABet.fragment.BetsFragment;
import com.dota.DotABet.fragment.HomeFragment;
import com.dota.DotABet.fragment.ItemGraphFragment;
import com.dota.DotABet.ui.Drawer;
import com.dota.DotABet.webview.D2LWebView;
import com.dota.DotABet.constants.Constants;

public class MainActivity extends Activity implements View.OnClickListener
{
	D2LIntentReceiver receiver;

	D2LWebView d2lWebView;
	LinearLayout fragmentContainer;
	BaseFragment currentFragment;

	View headerBar;
	ImageButton homeButton, betsButton, itemsButton, steamButton;

	Drawer loginDrawer;

	D2LDataStore dataStore = D2LApplication.getDatastore();
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main);

		d2lWebView = (D2LWebView)this.findViewById(R.id.mainWebview);
		fragmentContainer = (LinearLayout)this.findViewById(R.id.fragmentContainer);

		headerBar = findViewById(R.id.headerBar);
		homeButton = (ImageButton)findViewById(R.id.headerHome);
		homeButton.setOnClickListener(this);
		betsButton = (ImageButton)findViewById(R.id.headerBets);
		betsButton.setOnClickListener(this);
		itemsButton = (ImageButton)findViewById(R.id.headerItems);
		itemsButton.setOnClickListener(this);
		steamButton = (ImageButton)findViewById(R.id.headerSteam);
		steamButton.setOnClickListener(this);
		loginDrawer = (Drawer)findViewById(R.id.loginDrawer);
		dataStore.requestMatchData();
		registerReceiver();
    }

	private void registerReceiver()
	{
		receiver = new D2LIntentReceiver();
		IntentFilter filter = new IntentFilter();
		for(String intent : Constants.Intents.FILTER_ARRAY)
			filter.addAction(intent);
		this.registerReceiver(receiver, filter);
	}

	private void showFragment(short _id)
	{
		BaseFragment fragment;

		switch(_id)
		{
			case Constants.Fragments.BET_HISTORY_FRAGMENT:
				if(currentFragment instanceof BetsFragment)
					return;
				fragment = new BetsFragment();
				break;
			case Constants.Fragments.ITEM_GRAPH_FRAGMENT:
				if(currentFragment instanceof ItemGraphFragment)
					return;
				fragment = new ItemGraphFragment();
				break;
			case Constants.Fragments.USER_LOGIN_FRAGMENT:
				if(currentFragment instanceof HomeFragment)
					return;
				fragment = new HomeFragment();
				break;
			default:
				fragment = new BaseFragment();
				break;
		}
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		if(currentFragment != null)
			ft.remove(currentFragment);
		ft.add(R.id.fragmentContainer, fragment);
		ft.commit();
		currentFragment = fragment;
	}

	@Override
	public void onClick(View v)
	{
		if(v == homeButton)
		{
			showFragment(Constants.Fragments.USER_LOGIN_FRAGMENT);
		}
		else if(v == betsButton)
		{
			showFragment(Constants.Fragments.BET_HISTORY_FRAGMENT);
		}
		else if(v == itemsButton)
		{
			showFragment(Constants.Fragments.ITEM_GRAPH_FRAGMENT);
		}
		else if(v == steamButton)
		{
			loginDrawer.toggle();
		}
	}

	class D2LIntentReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			String action = intent.getAction();
			if(Constants.Intents.BET_HISTORY_INTENT.equals(action))
			{

				showFragment(Constants.Fragments.BET_HISTORY_FRAGMENT);
			}
			else if(Constants.Intents.USER_LOGIN_INTENT.equals(action))
			{
				if(currentFragment instanceof HomeFragment)
					return;
				showFragment(Constants.Fragments.USER_LOGIN_FRAGMENT);
			}
			else if(Constants.Intents.ITEM_GRAPH_INTENT.equals(action))
			{
				if(currentFragment instanceof ItemGraphFragment)
					return;
				showFragment(Constants.Fragments.ITEM_GRAPH_FRAGMENT);
			}
		}
	}
}
