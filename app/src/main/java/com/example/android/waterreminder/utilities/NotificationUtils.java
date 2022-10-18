package com.example.android.waterreminder.utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.example.android.waterreminder.MainActivity;
import com.example.android.waterreminder.MyIntentService;
import com.example.android.waterreminder.R;
import com.example.android.waterreminder.ReminderTask;

public class NotificationUtils {

    public static final int PENDING_INTENT_ID = 1001;
    public static final int DRINK_WATER_ID = 2002;
    public static final int IGNORE_WATER_ID =3003;

    private static final String NOTIFICATION_CHANNEL_ID = "notification_channel_id";

    public static void clearNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void createNotification(Context context){
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Drink Water",NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context,R.color.purple_200))
                .setSmallIcon(R.drawable.ic_cancel_black_24px)
                .setLargeIcon(getImage(context))
                .setContentTitle(context.getString(R.string.charging_reminder_notification_title))
                .setContentText(context.getText(R.string.charging_reminder_notification_body))
                .setStyle(new NotificationCompat.BigTextStyle().bigText(context.getText(R.string.charging_reminder_notification_body)))
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentIntent(context))
                .addAction(incrementWaterCountWithNotification(context))
                .addAction(cancelNotification(context))
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT<Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }
        notificationManager.notify(PENDING_INTENT_ID,notificationBuilder.build());
    }
    private static NotificationCompat.Action incrementWaterCountWithNotification(Context context){
        Intent i_did_it_intent = new Intent(context, MyIntentService.class);
        i_did_it_intent.setAction(ReminderTask.INCREMENT_WATER_COUNT);

        PendingIntent pendingIntent = PendingIntent.getService(context,DRINK_WATER_ID,i_did_it_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action(R.drawable.ic_drink_grey_120px,"i did it",pendingIntent);
    }

    private static NotificationCompat.Action cancelNotification(Context context){
        Intent no_thanks_intent = new Intent(context,MyIntentService.class);
        no_thanks_intent.setAction(ReminderTask.ACTION_CANCEL_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getService(context,IGNORE_WATER_ID,no_thanks_intent,PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Action(R.drawable.ic_cancel_black_24px,"no thanks",pendingIntent);
    }

    private static PendingIntent contentIntent(Context context){
        Intent intent = new Intent(context, MainActivity.class);

        return PendingIntent.getActivity(context,PENDING_INTENT_ID,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }
    public static Bitmap getImage(Context context){
        return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_drink_grey_120px);
    }
}
