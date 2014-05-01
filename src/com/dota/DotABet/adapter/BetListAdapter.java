package com.dota.DotABet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.dota.DotABet.R;
import com.dota.DotABet.application.D2LApplication;
import com.dota.DotABet.download.Downloader;
import com.dota.DotABet.model.Bet;
import com.dota.DotABet.ui.ItemHolder;
import com.dota.DotABet.ui.UIInterfaces.ItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/20
 * Time: 12:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class BetListAdapter extends BaseExpandableListAdapter
{
	private final ArrayList<Bet> bets;
	private LayoutInflater inflater;
	private ItemSelectedListener listener;

	public BetListAdapter(ArrayList<Bet> _bets)
	{
		inflater = (LayoutInflater) D2LApplication.getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		bets = _bets;
	}

	public BetListAdapter(ArrayList<Bet> _bets, ItemSelectedListener _listener)
	{
		inflater = (LayoutInflater) D2LApplication.getContext()
			.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		bets = _bets;
		listener = _listener;
	}

	@Override
	public int getGroupCount()
	{
		return bets.size();
	}

	@Override
	public int getChildrenCount(int groupPosition)
	{
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition)
	{
		return bets.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition)
	{
		return bets.get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public boolean hasStableIds() {
		return false;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent)
	{
		Bet bet = (Bet)getGroup(groupPosition);
		convertView = inflater.inflate(R.layout.listrow_bet, null);
		TextView teamText = (TextView)convertView.findViewById(R.id.teamsText);
		teamText.setText(bet.getMatch().getTitle());
		ImageView teamOneImage = (ImageView)convertView.findViewById(R.id.teamOneImage);
		Downloader.downloadBitmap(bet.getMatch().getTeamOneImage(),teamOneImage);
		ImageView teamTwoImage = (ImageView)convertView.findViewById(R.id.teamTwoImage);
		Downloader.downloadBitmap(bet.getMatch().getTeamTwoImage(),teamTwoImage);
		return convertView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
	{
		Bet bet = (Bet)getGroup(groupPosition);
		convertView = inflater.inflate(R.layout.listrow_bet_expand, null);
		TextView dateText = (TextView)convertView.findViewById(R.id.dateText);
		dateText.setText(bet.getMatch().getTime());
		ItemHolder itemsPlaced = (ItemHolder)convertView.findViewById(R.id.itemsPlaced);
		itemsPlaced.setOnItemSelectedListener(listener);
		itemsPlaced.identifier = Integer.parseInt(groupPosition + "1");
		for(int i = 0; i < bet.getPlaced().size(); i++)
			itemsPlaced.addItem(bet.getPlaced(i));

		ItemHolder itemsWon = (ItemHolder)convertView.findViewById(R.id.itemsWon);
		itemsWon.setOnItemSelectedListener(listener);
		itemsWon.identifier = Integer.parseInt(groupPosition + "2");
		for(int i = 0; i < bet.getWinnings().size(); i++)
			itemsWon.addItem(bet.getWinnings(i));

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition)
	{
		return true;
	}
}
