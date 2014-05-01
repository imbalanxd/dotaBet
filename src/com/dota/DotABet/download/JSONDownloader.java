package com.dota.DotABet.download;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: Imbalanxd
 * Date: 2014/03/28
 * Time: 11:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class JSONDownloader  extends AsyncTask<String, Integer, String>
{
	JSONDownloaderListener m_listener;
	short m_id;

	public JSONDownloader(JSONDownloaderListener _listener)
	{
		m_listener = _listener;
	}

	public JSONDownloader(short _id, JSONDownloaderListener _listener)
	{
		m_listener = _listener;
		m_id = _id;
	}

	@Override
	protected String doInBackground(String... params)
	{
		try
		{
			URL url = new URL(params[0]);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			StringBuilder sb = new StringBuilder();

			String line = null;
			while ((line = reader.readLine()) != null)
				sb.append(line + "\n");
			input.close();
			return sb.toString();
		}
		catch (Exception e)
		{
//			e.printStackTrace();
//			Log.e("getBmpFromUrl error: ", e.getMessage().toString());
			return null;
		}
	}

	@Override
	protected void onPostExecute(String result)
	{
		m_listener.onJSONDownloaded(m_id, result);
	}

	public interface JSONDownloaderListener
	{
		public void onJSONDownloaded(short _id, String data);
	}
}
