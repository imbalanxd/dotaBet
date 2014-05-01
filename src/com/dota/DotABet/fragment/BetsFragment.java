package com.dota.DotABet.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ImageView;
import android.widget.TextView;
import com.dota.DotABet.R;
import com.dota.DotABet.adapter.BetListAdapter;
import com.dota.DotABet.application.D2LApplication;
import com.dota.DotABet.download.Downloader;
import com.dota.DotABet.model.Bet;
import com.dota.DotABet.model.Item;
import com.dota.DotABet.ui.Drawer;
import com.dota.DotABet.ui.ScalableImageView;
import com.dota.DotABet.ui.UIInterfaces.ItemDeselectListener;
import com.dota.DotABet.ui.UIInterfaces.ItemSelectedListener;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/20
 * Time: 2:37 PM
 * To change this template use File | Settings | File Templates.
 */
public class BetsFragment extends BaseFragment implements ItemSelectedListener
{
	ExpandableListView betList;

	Item itemSelected;
	ItemDeselectListener deselectListener;
	private Drawer itemDrawer;
	private TextView itemName;
	private ImageView typeImage;
	private TextView typeName;
	private ScalableImageView itemImage;
	private ImageView heroImage;
	private TextView heroName;

	private int originalListHeight;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_bet_history, container, false);

		betList = (ExpandableListView)view.findViewById(R.id.betList);
		betList.setAdapter(
				new BetListAdapter(new ArrayList<Bet>(Arrays.asList(D2LApplication.getDatastore().getBetHistory().getBets())), this)
		);
		betList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Log.i("D2L", "fragment triggered");
			}
		});
		betList.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			@Override
			public void onGroupCollapse(int groupPosition)
			{
				if(deselectListener != null && groupPosition == deselectListener.getIdentifier())
					closeDrawer();
			}
		});

		itemDrawer = (Drawer)view.findViewById(R.id.itemDrawer);

		itemName = (TextView)view.findViewById(R.id.itemName);
		itemImage = (ScalableImageView)view.findViewById(R.id.itemImage);

		heroImage = (ImageView)view.findViewById(R.id.heroImage);
		heroName = (TextView)view.findViewById(R.id.heroName);

		typeName = (TextView)view.findViewById(R.id.typeName);
		typeImage = (ImageView)view.findViewById(R.id.typeImage);

		return view;
	}

	@Override
	protected void registerFragment()
	{

	}

	private void createBetList()
	{
		//betList = new ArrayList<Bet>;
	}

	public void closeDrawer()
	{
		ViewGroup.LayoutParams params = betList.getLayoutParams();
		params.height = originalListHeight;
		betList.setLayoutParams(params);
		itemDrawer.hide();
		itemSelected = null;
	}

	public void openDrawer()
	{
		itemName.setText(itemSelected.getName());
		Downloader.downloadBitmap(itemSelected.getCode(), itemImage);

		typeName.setText(itemSelected.getType()+" Item");
		typeImage.setBackgroundColor(getResources().getColor(itemSelected.getColour()));

		heroImage.setImageBitmap(m_datastore.getHeroImage(itemSelected.getHero()));
		heroName.setText(itemSelected.getHero());

		if(itemDrawer.show())
		{
			originalListHeight = betList.getHeight();
			new Handler().postDelayed(new Runnable() {
				public void run() {
					ViewGroup.LayoutParams params = betList.getLayoutParams();
					params.height = originalListHeight - itemDrawer.getHeight();
					betList.setLayoutParams(params);
				}
			}, itemDrawer.getDuration());
		}
	}

	@Override
	public void onItemSelected(Object _item, ItemDeselectListener _listener)
	{
		if(deselectListener != null)
			deselectListener.deselectItem();
		deselectListener = _listener;
		if(((Item)_item).equals(itemSelected))
		{
			closeDrawer();
		}
		else
		{
			itemSelected = (Item)_item;
			openDrawer();
		}
	}

	@Override
	public Object getItemSelected()
	{
		return itemSelected;
	}

	@Override
	public int getDeselectIdentifier()
	{
		if(deselectListener == null)
			return -1;
		else
			return deselectListener.getIdentifier();
	}
}
