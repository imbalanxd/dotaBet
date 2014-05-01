package com.dota.DotABet.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.dota.DotABet.R;
import com.dota.DotABet.application.D2LApplication;
import com.dota.DotABet.model.Bet;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/30
 * Time: 11:37 AM
 * To change this template use File | Settings | File Templates.
 */
public class ItemGraph extends LinearLayout
{
	private int m_entries = 0;
	private float m_value = 0;
	private int m_totalWidth;
	private float m_maximum, m_minimum;
	private float m_screenMax, m_screenMin;

	public ItemGraph(Context context) {
		super(context);
		init();
	}

	public ItemGraph(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ItemGraph(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init()
	{
		this.setOrientation(LinearLayout.VERTICAL);
	}

	public void addEntries(Bet[] _bets)
	{
		if(m_entries != 0)
			return;
		for(int i = 1; i <= _bets.length; i++)
		{
			addEntry(_bets[_bets.length - i]);
		}
	}

	public void addEntry(Bet _bet)
	{
		m_entries++;
		m_value += _bet.net;
		this.addView(createBar(m_value, _bet.net), 0);
	}

	public void resetEntries()
	{
		m_entries = 0;
	}

	private void convertValue(float _value)
	{

	}

	public void setBounds(float _max, float _min)
	{
		m_maximum = _max;
		m_minimum = _min;
		createScreenBounds();
	}

	private void setCurrentValue(float _value)
	{
		m_value = _value;
	}

	@Override
	protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld)
	{
		super.onSizeChanged(xNew, yNew, xOld, yOld);
		m_totalWidth = xNew;
	}

	//Minimum values represented as negatives or 0
	//TODO ensure these values
	private void createScreenBounds()
	{
		m_screenMax = m_totalWidth/(m_maximum - m_minimum)*m_maximum;
		m_screenMin = m_totalWidth/(m_maximum - m_minimum)*m_minimum;
	}

	private float convertToScreen(float _value)
	{
		return m_totalWidth/(m_maximum - m_minimum)*_value;
	}

	private ImageView createBar(float _value, float _delta)
	{
		int screenValue = (int)convertToScreen(_value);
		ImageView bar = new ImageView(getContext());
		LayoutParams sizeParams = new LayoutParams((int)Math.abs(screenValue), 10);
		sizeParams.setMargins(((int)Math.abs(m_screenMin) + (int)Math.min(screenValue, 0)),0,0,0);


		bar.setLayoutParams(sizeParams);
		if(_delta > 0)
			bar.setImageDrawable(getResources().getDrawable(R.drawable.graph_bar_green));
		else if(_delta < 0)
			bar.setImageDrawable(getResources().getDrawable(R.drawable.graph_bar_red));
		else
			bar.setImageDrawable(getResources().getDrawable(R.drawable.graph_bar_grey));
		return bar;
	}
}
