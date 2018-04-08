package com.example.hug.star.alarm.service;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import com.example.hug.star.alarm.Alarm;
import com.example.hug.star.alarm.alert.AlarmAlertBroadcastReciever;
import com.example.hug.star.alarm.database.Database;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AlarmService extends Service {

	@Override
	public IBinder onBind(Intent intent) {

		return null;
	}

	@Override
	public void onCreate() {
		Log.d(this.getClass().getSimpleName(),"onCreate()");
		super.onCreate();		
	}

	private Alarm getNext(){
		Set<Alarm> alarmQueue = new TreeSet<Alarm>(new Comparator<Alarm>() {
			@Override
			public int compare(Alarm alarm1, Alarm alarm2) {
				int result = 0;
				long kq = alarm1.getAlarmTime().getTimeInMillis() - alarm2.getAlarmTime().getTimeInMillis();
				if(kq>0){
					return 1;
				}else if (kq < 0){
					return -1;
				}
				return result;
			}
		});
				
		Database.init(getApplicationContext());
		List<Alarm> alarms = Database.getAll();
		
		for(Alarm alarm : alarms){
			if(alarm.getAlarmActive())
				alarmQueue.add(alarm);
		}
		if(alarmQueue.iterator().hasNext()){
			return alarmQueue.iterator().next();
		}else{
			return null;
		}
	}

	@Override
	public void onDestroy() {
		Database.deactivate();
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d(this.getClass().getSimpleName(),"onStartCommand()");
		Alarm alarm = getNext();
		if(null != alarm){
			alarm.schedule(getApplicationContext());
			Log.d(this.getClass().getSimpleName(), alarm.getTimeUntilNextAlarmMessage());
			
		}else{
			Intent myIntent = new Intent(getApplicationContext(), AlarmAlertBroadcastReciever.class);
			myIntent.putExtra("alarm", new Alarm());
			
			PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, myIntent,PendingIntent.FLAG_CANCEL_CURRENT);			
			AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
			
			alarmManager.cancel(pendingIntent);
		}
		return START_NOT_STICKY;
	}

}
