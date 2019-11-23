package com.dj.abtesting;

import android.os.Bundle;

public class Firebaselogging {
    private static final Firebaselogging ourInstance = new Firebaselogging();

    static Firebaselogging getInstance() {
        return ourInstance;
    }

    private Firebaselogging() {
    }

    public void firebaseLog(String accountName){
        Bundle params = new Bundle();
        params.putString("account_name", accountName);
        ABTestingApp.getInstance().getFirebaseAnalytics().logEvent(accountName, params);
    }
}
