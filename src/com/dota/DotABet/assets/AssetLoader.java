package com.dota.DotABet.assets;

import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import com.dota.DotABet.application.D2LApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/04/14
 * Time: 11:29 PM
 * To change this template use File | Settings | File Templates.
 */
public class AssetLoader
{
	private static AssetManager m_manager;

	private static Resources m_resources;

	private static void setupManager()
	{
		if(m_manager == null)
			m_manager = D2LApplication.getContext().getAssets();
	}

	private static void setupResources()
	{
		if(m_resources == null)
			m_resources = D2LApplication.getContext().getResources();
	}

	public static Bitmap loadImage(String _fileName)
	{
		setupManager();
		Bitmap bitmap = null;
		try
		{
			InputStream imageStream = m_manager.open(_fileName);
			bitmap = BitmapFactory.decodeStream(imageStream);
		}
		catch(IOException ex)
		{
			Log.i("D2L", "Loading Asset " + _fileName + " failed: " + ex.toString());
		}
		return bitmap;
	}

	public static String loadText(String _fileName)
	{
		setupManager();
		try
		{
			StringBuilder buf=new StringBuilder();
			InputStream mapStream = m_manager.open(_fileName);
			BufferedReader in= new BufferedReader(new InputStreamReader(mapStream));
			String str;

			while ((str=in.readLine()) != null)
			{
				buf.append(str);
			}

			in.close();
			return buf.toString();
		}
		catch(IOException ex)
		{
			Log.i("D2L", "Loading Asset " + _fileName + " failed: " + ex.toString());
			return null;
		}
	}

	public static String loadRawText(int resId)
	{
		setupResources();
		try {
			InputStream in_s = m_resources.openRawResource(resId);

			byte[] b = new byte[in_s.available()];
			in_s.read(b);
			return new String(b);
		} catch (Exception e)
		{
			// e.printStackTrace();
			return null;
		}
	}
}
