package com.dj.abtesting;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.firebase.storage.FirebaseStorage;

import java.util.HashMap;
import java.util.concurrent.Executor;

public class ABTestingApp extends Application {

    private static ABTestingApp ourInstance;

    static ABTestingApp getInstance() {
        return ourInstance;
    }

    private Context context;

    private FirebaseAnalytics firebaseAnalytics;

    private FirebaseStorage firebaseStorage;

    private FirebaseRemoteConfig firebaseRemoteConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        ourInstance =  this;
        context = getApplicationContext();
        firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        firebaseStorage =  FirebaseStorage.getInstance();
        firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(3600)
                .setDeveloperModeEnabled(true)
                .build();
        firebaseRemoteConfig.setConfigSettingsAsync(configSettings);

        HashMap<String,Object> defaults = new HashMap<>();
        defaults.put("theme","iron_man");
        firebaseRemoteConfig.setDefaultsAsync(defaults);
        Task<Void> fetch = firebaseRemoteConfig.fetch(0);

     /*   Task<Void> voidTask = fetch.addOnSuccessListener(this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseRemoteConfig.activateFetched();

                getDataFromRemote();
            }
        });*/
    }

    private void getDataFromRemote() {
        String theme = (String) firebaseRemoteConfig.getString("theme");
        Log.d("Theme",theme);
    }

    public Context getContext() {
        return context;
    }

    public FirebaseAnalytics getFirebaseAnalytics() {
        return firebaseAnalytics;
    }

    public FirebaseStorage getFirebaseStorage() {
        return firebaseStorage;
    }

    public FirebaseRemoteConfig getFirebaseRemoteConfig() {
        return firebaseRemoteConfig;
    }
}
