package com.dota.DotABet.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import com.dota.DotABet.R;
import com.dota.DotABet.application.D2LApplication;
import com.dota.DotABet.constants.Constants;
import com.dota.DotABet.model.Bet;
import com.dota.DotABet.model.BetHistory;
import com.dota.DotABet.ui.ItemGraph;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/04/04
 * Time: 10:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class ItemGraphFragment extends BaseFragment
{
	private ItemGraph m_graph;
	private boolean m_graphReady = false;
	private BetHistory m_betHistory;

	@Override
	protected void registerFragment()
	{
		m_fragID = Constants.Fragments.ITEM_GRAPH_FRAGMENT;
		registerForData(Constants.Data.BET_HISTORY_DATA);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_item_graph, container, false);

		m_graph = (ItemGraph)view.findViewById(R.id.itemGraph);
		m_graph.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
			@Override
			public void onGlobalLayout() {
				m_graphReady = true;
				initItemViews();
			}
		});

		initItemViews();
		return view;
	}

	@Override
	public void onResume()
	{
		super.onResume();
		initItemViews();
	}

	private void initItemViews()
	{
		m_betHistory = D2LApplication.getDatastore().getBetHistory();
		if(m_betHistory == null || !m_graphReady)
			return;
		m_graph.setBounds(m_betHistory.getItemsMax(), m_betHistory.getItemsMin());
		m_graph.addEntries(m_betHistory.getBets());
	}

	@Override
	public void onDataComplete(short _id)
	{
		switch(_id)
		{
			case Constants.Data.BET_HISTORY_DATA:
				initItemViews();
				break;
		}
	}
}
