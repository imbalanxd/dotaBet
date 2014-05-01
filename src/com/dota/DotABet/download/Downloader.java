package com.dota.DotABet.download;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/21
 * Time: 12:58 AM
 * To change this template use File | Settings | File Templates.
 */
public class Downloader
{
	static HashMap<String, Object> waitingViews = new HashMap<String, Object>();
	static HashMap<String, Object> dataCache = new HashMap<String, Object>();

	static BitmapDownloader.BitmapDownloaderListener bitmapDownloaderListener =
			new BitmapDownloader.BitmapDownloaderListener(){
				public void onImageDownloaded(Bitmap bmp, String url){
					dataCache.put(url, bmp);
					for(ImageView view : ((ArrayList<ImageView>)(waitingViews.get(url))))
					{
						view.setImageBitmap(bmp);
					}
				}};

	static public ImageView downloadBitmap(String url, ImageView view)
	{
		if(dataCache.containsKey(url))
		{
			view.setImageBitmap((Bitmap)dataCache.get(url));
			return view;
		}
		else if(waitingViews.containsKey(url))
		{
			((ArrayList<ImageView>)(waitingViews.get(url))).add(view);
			return view;
		}
		else
		{
			ArrayList<ImageView> viewList = new ArrayList<ImageView>();
			viewList.add(view);
			waitingViews.put(url, viewList);
		}

		BitmapDownloader downloader = new BitmapDownloader(bitmapDownloaderListener);
		downloader.execute(url);
		return view;
	}

	static public void downloadJSON(String _url, JSONDownloader.JSONDownloaderListener _listener)
	{
		JSONDownloader downloader = new JSONDownloader( _listener);
		downloader.execute(_url);
	}

	static public void downloadJSON(String _url, short _id, JSONDownloader.JSONDownloaderListener _listener)
	{
		JSONDownloader downloader = new JSONDownloader(_id, _listener);
		downloader.execute(_url);
	}
}
