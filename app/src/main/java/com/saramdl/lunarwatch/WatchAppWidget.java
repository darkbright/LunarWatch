package com.saramdl.lunarwatch;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Implementation of App Widget functionality.
 */
public class WatchAppWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

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

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.watch_app_widget);
        views.setTextViewText(R.id.appwidget_text_top, chungan);
        views.setTextViewText(R.id.appwidget_text_bottom, zizi);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
            setTime(context);
        }
    }

    public void setTime(Context context) {
        PendingIntent service = null;
        final AlarmManager m = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        final Intent si = new Intent(context, AlarmService.class);
        if (service == null)
        {
            service = PendingIntent.getService(context, 0, si, PendingIntent.FLAG_CANCEL_CURRENT);
        }

        long now = System.currentTimeMillis();
        Date date = new Date(now);

        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new  SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault());
        String strDate = calendar.get(Calendar.YEAR) + "-" + calendar.get(Calendar.MONTH)
                + "-" + calendar.get(Calendar.DAY_OF_MONTH)
                + " " + calendar.get(Calendar.HOUR_OF_DAY)
                + ":" + calendar.get(Calendar.MINUTE) + 1;
        try {
            date = dateFormat.parse(strDate);
        } catch (Exception ex) {
            date = new Date(now);
        }

        m.setInexactRepeating(AlarmManager.RTC, date.getTime(), 60 * 1000, service); // 1분마다
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

