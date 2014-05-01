package com.dota.DotABet.application;

import android.app.Application;
import android.content.Context;
import com.dota.DotABet.datastore.D2LDataStore;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/16
 * Time: 2:05 PM
 * To change this template use File | Settings | File Templates.
 */
public class D2LApplication extends Application
{
	private static Context m_Context;
	private static D2LDataStore m_datastore;

	@Override
	public void onCreate()
	{
		super.onCreate();
		m_Context = this;
		getDatastore();
	}

	public static Context getContext()
	{
		return m_Context;
	}

	public static D2LDataStore getDatastore()
	{
		if(m_datastore == null)
			m_datastore = new D2LDataStore(m_Context);
		return m_datastore;
	}
}
