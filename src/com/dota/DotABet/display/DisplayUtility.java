package com.dota.DotABet.display;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import com.dota.DotABet.application.D2LApplication;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/04/28
 * Time: 3:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class DisplayUtility
{
	public static float convertDpToPixel(float dp)
	{
		Resources resources = D2LApplication.getContext().getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float px = dp * (metrics.densityDpi / 160f);
		return px;
	}

	public static float convertPixelsToDp(float px)
	{
		Resources resources = D2LApplication.getContext().getResources();
		DisplayMetrics metrics = resources.getDisplayMetrics();
		float dp = px / (metrics.densityDpi / 160f);
		return dp;
	}
}
