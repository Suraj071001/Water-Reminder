package com.example.android.waterreminder;

import android.annotation.SuppressLint;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

public class MyJobIntent extends JobService {

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("tag", "run: notification running");
                ReminderTask.executeTask(MyJobIntent.this,ReminderTask.ACTION_INCREMENT_CHARGING_COUNT);
            }
        }).start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
