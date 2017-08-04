package com.saramdl.lunarwatch;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

    private void update() {
        String time = getTime();

        Calendar now = Calendar.getInstance();
        SimpleDateFormat frm = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);

        LunarCalendar cal = new LunarCalendar();
        cal.GYear = now.get(Calendar.YEAR);
        cal.GMonth = now.get(Calendar.MONTH) + 1;
        cal.GDay = now.get(Calendar.DAY_OF_MONTH);
        cal.GHour = now.get(Calendar.HOUR_OF_DAY);
        cal.GMinute = now.get(Calendar.MINUTE);
        cal.convert();

        String chungan = new String();
        chungan = cal.CHour.charAt(0) + " " + cal.CDay.charAt(0) + " "
                + cal.CMonth.charAt(0) + " " +cal.CYear.charAt(0);
        String zizi = new String();
        zizi = cal.CHour.charAt(1) + " " + cal.CDay.charAt(1) + " "
                + cal.CMonth.charAt(1) + " " +cal.CYear.charAt(1);

        AppWidgetManager manager = AppWidgetManager.getInstance(this);
        ComponentName widget = new ComponentName(this, WatchAppWidget.class);

        for (int appWidgetId : manager.getAppWidgetIds(widget)) {
            RemoteViews views = new RemoteViews(getPackageName(), R.layout.watch_app_widget);
            views.setTextViewText(R.id.appwidget_text_top, chungan);
            views.setTextViewText(R.id.appwidget_text_bottom, zizi);
            views.setTextViewText(R.id.textView, time);
            manager.updateAppWidget(appWidgetId, views);
        }
    }

    public String getTime() {
        String time = null;
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH:mm");
        time = CurTimeFormat.format(date);

        return time;
    }

}
