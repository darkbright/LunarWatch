package com.saramdl.lunarwatch;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AlarmService extends Service {
    public AlarmService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        update();

        return super.onStartCommand(intent, flags, startId);
    }

    private void update()
    {
        String time = getTime();
        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        ComponentName widget = new ComponentName(this, WatchAppWidget.class);
        int[] ids = manager.getAppWidgetIds(widget);
        final int N = ids.length;

        for (int i = 0; i < N; i++){
            int awID = ids[i];
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.watch_app_widget);
            views.setTextViewText(R.id.appwidget_text_bottom, time);
            manager.updateAppWidget(awID, views);
        }
    }

    public String getTime(){

        String time=null;
        Date date=new Date(System.currentTimeMillis());
        SimpleDateFormat CurTimeFormat=new SimpleDateFormat("HH:mm");
        time=CurTimeFormat.format(date);
        return time;
    }

}
