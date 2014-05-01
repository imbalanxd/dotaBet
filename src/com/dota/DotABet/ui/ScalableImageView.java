package com.dota.DotABet.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ScalableImageView extends ImageView
{
	public boolean isMeasured = true;

	public ScalableImageView(Context context) {
		super(context);
	}

	public ScalableImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScalableImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		try
		{
			Drawable drawable = getDrawable();

			if (drawable == null)
			{
				setMeasuredDimension(0, 0);
			}
			else
			{
				int height = MeasureSpec.getSize(heightMeasureSpec);
				int width = height * drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight();
				setMeasuredDimension(width, height);
			}
		}
		catch (Exception e)
		{
			isMeasured = false;
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		}
	}
}
