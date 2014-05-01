package com.dota.DotABet.download;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/21
 * Time: 1:38 AM
 * To change this template use File | Settings | File Templates.
 */
public class BitmapDownloader extends AsyncTask<String, Integer, Bitmap>
{
	private String target;
	private boolean busy = false;
	private BitmapDownloaderListener listener;

	public BitmapDownloader(BitmapDownloaderListener _listener)
	{
		listener = _listener;
	}

	public boolean isBusy()
	{
		return busy;
	}

	@Override
	protected void onPreExecute()
	{
		super.onPreExecute();
		busy = true;
	}

	@Override
	protected Bitmap doInBackground(String... params)
	{
		try
		{
			target = params[0];
			URL url = new URL(target);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap bitmap = BitmapFactory.decodeStream(input);

			return bitmap;
		}
		catch (Exception e)
		{
//			e.printStackTrace();
//			Log.e("getBmpFromUrl error: ", e.getMessage().toString());
			return null;
		}
	}

	@Override
	protected void onPostExecute(Bitmap result)
	{
		listener.onImageDownloaded(result, target);
	}

	public interface BitmapDownloaderListener
	{
		public void onImageDownloaded(Bitmap bmp, String url);
	}
}
