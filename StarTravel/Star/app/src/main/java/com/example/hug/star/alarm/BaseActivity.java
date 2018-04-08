package com.example.hug.star.alarm;

import java.lang.reflect.Field;

import com.example.hug.star.alarm.preferences.AlarmPreferencesActivity;
import com.example.hug.star.alarm.service.AlarmServiceBroadcastReciever;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.example.hug.star.R;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.widget.Toast;

public abstract class BaseActivity  extends ActionBarActivity implements android.view.View.OnClickListener{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		try {
	        ViewConfiguration config = ViewConfiguration.get(this);	        
	        Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
	        if(menuKeyField != null) {
	            menuKeyField.setAccessible(true);
	            menuKeyField.setBoolean(config, false);
	        }
	    } catch (Exception ex) {
	        // Ignore
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu, menu);
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String url = null;
		Intent intent = null;
		switch (item.getItemId()) {
		case R.id.menu_item_new:
			Intent newAlarmIntent = new Intent(this, AlarmPreferencesActivity.class);
			startActivity(newAlarmIntent);
			break;
		case R.id.menu_item_introduce:
			url = "http://www.facebook.com";
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url));
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(this, "Couldn't go facebbok", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.menu_item_website:
			url = "http://www.google.com";
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url));
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(this, "Couldn't launch the website", Toast.LENGTH_LONG).show();
			}
			break;
		case R.id.menu_item_report:

			url = "https://facebook.com";
			intent = new Intent(Intent.ACTION_VIEW);
			intent.setData(Uri.parse(url));
			try {
				startActivity(intent);
			} catch (ActivityNotFoundException e) {
				Toast.makeText(this, "Couldn't go facebook", Toast.LENGTH_LONG).show();
			}
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	protected void callMathAlarmScheduleService() {
		Intent mathAlarmServiceIntent = new Intent(this, AlarmServiceBroadcastReciever.class);
		sendBroadcast(mathAlarmServiceIntent, null);
	}
}
