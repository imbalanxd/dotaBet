package com.dota.DotABet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.dota.DotABet.R;
import com.dota.DotABet.application.D2LApplication;
import com.dota.DotABet.download.Downloader;
import com.dota.DotABet.model.BetHistory;
import com.dota.DotABet.model.User;
import com.dota.DotABet.constants.Constants;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/28
 * Time: 10:23 PM
 * To change this template use File | Settings | File Templates.
 */
public class HomeFragment extends BaseFragment
{
	private User m_user;
	private BetHistory m_betHistory;

	//UI Elements
	ImageView profileImage;
	TextView profileName;
	TextView profileBetsMade;
	TextView profileBetsWon;
	TextView profileItemsWon;
	TextView profileItemsLost;

	@Override
	protected void registerFragment()
	{
		m_fragID = Constants.Fragments.USER_LOGIN_FRAGMENT;
		registerForData(Constants.Data.USER_LOGIN_DATA, Constants.Data.BET_HISTORY_DATA);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		m_view = inflater.inflate(R.layout.fragment_home, container, false);
		profileImage = (ImageView)m_view.findViewById(R.id.profileImage);
		profileName = (TextView)m_view.findViewById(R.id.profileName);
		profileBetsMade = (TextView)m_view.findViewById(R.id.profileBetsMade);
		profileBetsWon = (TextView)m_view.findViewById(R.id.profileBetsWon);
		profileItemsWon = (TextView)m_view.findViewById(R.id.profileItemsWon);
		profileItemsLost = (TextView)m_view.findViewById(R.id.profileItemsLost);
		initBetViews();
		initUserViews();
		return m_view;
	}

	private void initBetViews()
	{
		m_betHistory = D2LApplication.getDatastore().getBetHistory();
		if(m_betHistory == null)
			return;
		profileBetsMade.setText(String.format(getString(R.string.home_total_bets), m_betHistory.getBetsTotal()));
		profileBetsWon.setText(String.format(getString(R.string.home_total_won), m_betHistory.getBetsWon()));
		profileItemsWon.setText(String.format(getString(R.string.home_items_won), m_betHistory.getItemsWon()));
		profileItemsLost.setText(String.format(getString(R.string.home_items_lost), m_betHistory.getItemsLost()));
	}

	private void initUserViews()
	{
		m_user = D2LApplication.getDatastore().getCurrentUser();
		if(m_user == null)
			return;
		profileName.setText(m_user.getName());
		Downloader.downloadBitmap(m_user.getImageURL(), profileImage);
	}

	@Override
	public void onDataComplete(short _id)
	{
		switch(_id)
		{
			case Constants.Data.USER_LOGIN_DATA:
				initUserViews();
				break;
			case Constants.Data.BET_HISTORY_DATA:
				initBetViews();
				break;
		}
	}
}
