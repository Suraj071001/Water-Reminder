package com.example.android.waterreminder;

import android.content.Context;

import com.example.android.waterreminder.utilities.NotificationUtils;
import com.example.android.waterreminder.utilities.PreferencesUtilities;

public class ReminderTask {
    public static final String INCREMENT_WATER_COUNT = "increment_water_count";
    public static final String ACTION_CANCEL_NOTIFICATION = "cancel_notification";
    public static final String ACTION_INCREMENT_CHARGING_COUNT = "increment_charging_count";

    public static void executeTask(Context context,String action){
        if (INCREMENT_WATER_COUNT.equals(action)){
            incrementWaterCount(context);
        }else if(ACTION_CANCEL_NOTIFICATION.equals(action)){
            NotificationUtils.clearNotification(context);
        }else if(ACTION_INCREMENT_CHARGING_COUNT.equals(action)){
            incrementChargingCount(context);
        }
    }

    private static void incrementChargingCount(Context context) {
        PreferencesUtilities.incrementChargingReminderCount(context);
        NotificationUtils.createNotification(context);
    }

    private static void incrementWaterCount(Context context){
        PreferencesUtilities.incrementWaterCount(context);
        NotificationUtils.clearNotification(context);
    }

}
