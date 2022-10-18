package com.example.android.waterreminder;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.util.concurrent.TimeUnit;

public class ReminderJob {
    private static final int TIME_INTERVAL_MINUTES = 1000;
    private static final int TIME_INTERVAL_SECONDS = (int) (TimeUnit.MINUTES.toMillis(TIME_INTERVAL_MINUTES));
    private static final int SYNC_FLEXTIME_SECONDS = TIME_INTERVAL_SECONDS;

    private static final String REMINDER_JOB_TAG = "reminder_job_tag";
    public static final int JOB_ID = 1001;
    private boolean mInitialised;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void jobNotification(final Context context){
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);

        ComponentName componentName = new ComponentName(context,MyJobIntent.class);
        JobInfo jobInfo = new JobInfo.Builder(JOB_ID,componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setPeriodic(JobInfo.getMinPeriodMillis(),JobInfo.getMinFlexMillis())
                .setRequiresCharging(true)
                .setPersisted(true)
                .build();
        int result = jobScheduler.schedule(jobInfo);

        if(result == JobScheduler.RESULT_SUCCESS){
            Log.d("tag", "jobNotification: "+result);

        }
    }
}
