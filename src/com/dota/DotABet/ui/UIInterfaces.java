package com.dota.DotABet.ui;

import android.view.View;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/04/22
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class UIInterfaces
{
	public interface ItemSelectedListener
	{
		public void onItemSelected(Object _item, ItemDeselectListener _listener);
		public Object getItemSelected();
		public int getDeselectIdentifier();
	}

	public interface ItemDeselectListener
	{
		public void deselectItem();
		public int getIdentifier();
	}

	public interface ScrollListener
	{
		public void onScroll(String _identifier, int _amount);
	}
}
