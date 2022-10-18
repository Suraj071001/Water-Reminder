package com.example.android.waterreminder;

import android.app.IntentService;
import android.content.Intent;

public class MyIntentService extends IntentService {


    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String action = intent.getAction();
        ReminderTask.executeTask(this,action);
    }

}