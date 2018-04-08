package com.example.hug.star.alarm.alert;

import com.example.hug.star.alarm.Alarm;
import com.example.hug.star.alarm.service.AlarmServiceBroadcastReciever;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class AlarmAlertBroadcastReciever extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent mathAlarmServiceIntent = new Intent(
				context,
				AlarmServiceBroadcastReciever.class);
		context.sendBroadcast(mathAlarmServiceIntent, null);
		
		//StaticWakeLock.lockOn(context);
		Bundle bundle = intent.getExtras();
		final Alarm alarm = (Alarm) bundle.getSerializable("alarm");

		Intent intent1;

		intent1 = new Intent(context, AlarmAlertActivity.class);

		intent1.putExtra("alarm", alarm);

		intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		context.startActivity(intent1);
	}

}
