package com.dota.DotABet.ui;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/04/05
 * Time: 12:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class GraphBar extends Drawable
{
	private final Paint m_paint;
	private final RectF m_rect;

	public GraphBar()
	{
		m_paint = new Paint();
		m_rect = new RectF();
	}


	@Override
	public void draw(Canvas canvas)
	{

	}

	@Override
	public void setAlpha(int alpha) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public int getOpacity() {
		return 0;  //To change body of implemented methods use File | Settings | File Templates.
	}
}
