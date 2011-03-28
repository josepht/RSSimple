package com.xenno.android;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

import android.os.Environment;
import android.util.Log;

public class RSSimpleFeedList {

	ArrayList<String> feeds;
	File file;

	public RSSimpleFeedList(File file) {
		feeds = new ArrayList<String>();
		this.file = file;
	}

	public String[] array() {
		String[] ar = new String[feeds.size()];
		feeds.toArray(ar);
		return (ar);
	}

	public void getFeeds() {
		String state = Environment.getExternalStorageState();
		feeds.clear();

		if (Environment.MEDIA_MOUNTED.equals(state) && file != null) {
			try {
				InputStream is = new FileInputStream(file);
				byte[] buf = new byte[is.available()];
				is.read(buf);
				String[] lines = new String(buf).split("\\n");

				for (int i = 0; i < lines.length; i++) {
					if (lines[i].length() > 0)
						feeds.add(lines[i]);
				} 

				is.close();
			} catch (FileNotFoundException e) {
				Log.w("ExternalStorage", "Can't find " + file, e);
			} catch (IOException e) {
				Log.w("ExternalStorage", "Error writing " + file, e);
			}
		}
	}

	public void saveFeeds() {
		boolean available = false;
		boolean writeable = false;
		String state = Environment.getExternalStorageState();

		if (Environment.MEDIA_MOUNTED.equals(state)) {
			available = writeable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
			available = true;
			writeable = false;
		} else {
			available = writeable = false;
		}

		if (available && writeable && file != null)
		{
			try {
				OutputStream os = new FileOutputStream(file, false);

				String feed_list = new String("");
				for (int i = 0; i < feeds.size(); i++)
					feed_list += feeds.get(i) + "\n";
				if (feed_list.length() > 0) {
					byte[] buf = feed_list.getBytes();
					os.write(buf);
				}
				os.close();
			} catch (FileNotFoundException e) {
				Log.w("RSSimple/ExternalStorage", "Can't find " + file, e);
			} catch (IOException e) {
				Log.w("RSSimple/ExternalStorage", "Error writing " + file, e);
			}
		}
	}

	public void removeFeed(String feedURL) {
		feeds.remove(feedURL);
		saveFeeds();
	}

	public void removeAllFeeds() {
		feeds.clear();
		saveFeeds();
	}

	public void addFeed(String feedURL) {
		try {
			URL url = new URL(feedURL);;
			feeds.add(feedURL);
			saveFeeds();
		} catch (MalformedURLException e) {
			Log.e("RSSimple", feedURL + " is not a valid URL");
		}
	}
}
