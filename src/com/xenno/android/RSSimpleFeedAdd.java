package com.xenno.android;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Intent;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;

public class RSSimpleFeedAdd extends Activity
{
	Activity activity = this;
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.feedadd);
		Intent intent = new Intent(this, RSSimple.class);

		Button add_button = (Button) findViewById(R.id.add);
		add_button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				boolean mExternalStorageAvailable = false;
				boolean mExternalStorageWriteable = false;
				String state = Environment.getExternalStorageState();

				if (Environment.MEDIA_MOUNTED.equals(state)) {
					// We can read and write the media
					mExternalStorageAvailable = mExternalStorageWriteable = true;
				} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
					// We can only read the media
					mExternalStorageAvailable = true;
					mExternalStorageWriteable = false;
				} else {
					// Something else is wrong. It may be one of many other states, but all we need
					//  to know is we can neither read nor write
					mExternalStorageAvailable = mExternalStorageWriteable = false;
				}

				if (mExternalStorageAvailable && mExternalStorageWriteable)
				{

					File file = new File(getExternalFilesDir(null), "RSSimpleFeeds.txt");
					RSSimpleFeedList feed_list = new RSSimpleFeedList(file);
					feed_list.getFeeds();
					TextView tv = (TextView) findViewById(R.id.new_feed);
					String feed = tv.getText().toString();
					feed_list.addFeed(tv.getText().toString());
					activity.finish();
/*
					try {
						OutputStream os = new FileOutputStream(file, true);
						TextView tv = (TextView) findViewById(R.id.new_feed);
						String feed = tv.getText().toString();
						byte[] buf = new String(feed + "\n").getBytes();
						// XXX: check URL and such here
						os.write(buf);
						os.close();
						activity.finish();
					} catch (FileNotFoundException e) {
						Log.w("ExternalStorage", "Can't find " + file, e);
					} catch (IOException e) {
						Log.w("ExternalStorage", "Error writing " + file, e);
					}
*/
				}
			}
		});
	}
}
