package com.dota.DotABet.datastore;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import com.dota.DotABet.R;
import com.dota.DotABet.assets.AssetLoader;
import com.dota.DotABet.download.Downloader;
import com.dota.DotABet.download.JSONDownloader;
import com.dota.DotABet.fragment.FragmentDataListener;
import com.dota.DotABet.constants.Constants;
import com.dota.DotABet.assets.SpriteSheet;
import com.dota.DotABet.model.BetHistory;
import com.dota.DotABet.model.MatchHistory;
import com.dota.DotABet.model.User;
import com.dota.DotABet.parse.ParseUtility;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/20
 * Time: 11:07 AM
 * To change this template use File | Settings | File Templates.
 */
public class D2LDataStore implements JSONDownloader.JSONDownloaderListener
{
	private Context m_context;
	//Data values
	private BetHistory m_betHistory;
	private MatchHistory m_matchHistory;
	private User m_currentUser;
	//Data timestamps
	private HashMap<String, Long> m_dataTimeStamps;
	private int dataExpireTime = 20000;
	//Datalisteners
	private HashMap<Short, ArrayList<FragmentDataListener>> m_dataListeners;

	private SpriteSheet m_heroSpriteSheet;

	public D2LDataStore(Context _context)
	{
		m_context = _context;
		m_dataTimeStamps = new HashMap<String, Long>();
		m_dataListeners = new HashMap<Short, ArrayList<FragmentDataListener>>();

		m_heroSpriteSheet = new SpriteSheet(_context, "Heroes");
	}

	public BetHistory getBetHistory()
	{
		return m_betHistory;
	}

	public void setBetHistory(BetHistory _betHistory)
	{
		m_betHistory = _betHistory;
//		Intent intent = new Intent();
//		intent.setAction(Constants.Intents.BET_HISTORY_INTENT);
//		m_context.sendBroadcast(intent);
	}

	public void setMatchHistory(MatchHistory _matchHistory)
	{
		m_matchHistory = _matchHistory;
	}

	public void userLoggedIn(String _steamID)
	{
		//TODO add userpreferences here for steamID instead of always requesting it
		String requestURL = String.format(m_context.getString(R.string.steam_user_summary), m_context.getString(R.string.steam_api_key), _steamID);
		Downloader.downloadJSON(requestURL, Constants.Data.USER_LOGIN_DATA, this);
	}

	public void requestMatchData()
	{
		String data = AssetLoader.loadRawText(R.raw.all);
		onJSONDownloaded(Constants.Data.MATCH_DATA, data);
		//Downloader.downloadJSON("https://dl.dropboxusercontent.com/u/6250985/dotabet/all.json", Constants.Data.MATCH_DATA, this);
	}

	public void setCurrentUser(User _user)
	{
		m_currentUser = _user;
		Intent intent = new Intent();
		intent.setAction(Constants.Intents.ITEM_GRAPH_INTENT);
		m_context.sendBroadcast(intent);
	}

	public User getCurrentUser()
	{
		return m_currentUser;
	}


	@Override
	public void onJSONDownloaded(final short _id, String _data)
	{
		switch(_id)
		{
			case Constants.Data.USER_LOGIN_DATA:
				setCurrentUser(ParseUtility.parseUser(_data));
				break;
			case Constants.Data.BET_HISTORY_DATA:
				setBetHistory(ParseUtility.parseBet(_data));
				break;
			case Constants.Data.MATCH_DATA:
				setMatchHistory(ParseUtility.parseMatches(_data));
				break;
		}
		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				alertDataListeners(_id);
			}
		});
	}

	public Bitmap getHeroImage(String _name)
	{
		return m_heroSpriteSheet.getSprite(_name);
	}

	public void alertDataListeners(short _id)
	{
		ArrayList<FragmentDataListener> listeners = m_dataListeners.get(_id) == null? new ArrayList<FragmentDataListener>() : m_dataListeners.get(_id);
		for(FragmentDataListener listener : listeners)
		{
			listener.onDataComplete(_id);
		}
	}

	public void registerListener(FragmentDataListener _listener, short _id)
	{
		ArrayList<FragmentDataListener> listeners;
		if(m_dataListeners.containsKey(_id))
			listeners = m_dataListeners.get(_id);
		else
			listeners = new ArrayList<FragmentDataListener>();
		m_dataListeners.put(_id, listeners);
		if(!listeners.contains(_listener))
			listeners.add(_listener);
	}

	public void unregisterListener(FragmentDataListener _listener, short _id)
	{
		ArrayList<FragmentDataListener> listeners;
		if(m_dataListeners.containsKey(_id))
		{
			listeners = m_dataListeners.get(_id);
			listeners.remove(_listener);
		}
	}

}
