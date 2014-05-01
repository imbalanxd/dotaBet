package com.dota.DotABet.fragment;

import android.app.Fragment;
import android.view.View;
import com.dota.DotABet.application.D2LApplication;
import com.dota.DotABet.datastore.D2LDataStore;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/20
 * Time: 2:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class BaseFragment extends Fragment implements FragmentDataListener
{
	protected View m_view;
	protected short m_fragID;
	protected D2LDataStore m_datastore = D2LApplication.getDatastore();

	@Override
	public void onResume()
	{
		super.onResume();
		registerFragment();
	}

	protected void registerFragment()
	{

	}

	protected void registerForData(short ... params)
	{
		for(short id : params)
			D2LApplication.getDatastore().registerListener(this, id);
	}

	@Override
	public void onDataComplete(short _id)
	{

	}
}