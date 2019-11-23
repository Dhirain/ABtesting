package com.dj.abtesting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import pl.droidsonroids.gif.GifImageView;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private String userId;
    private boolean isActivityVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final GifImageView  gifImageView = findViewById(R.id.gifimage);
        ABTestingApp.getInstance().getFirebaseRemoteConfig().fetchAndActivate()
                .addOnCompleteListener(this, new OnCompleteListener<Boolean>() {
                    @Override
                    public void onComplete(@NonNull Task<Boolean> task) {
                        if (task.isSuccessful()) {
                            boolean updated = task.getResult();
                            Log.d(TAG, "Config params updated: " + updated);
                            Toast.makeText(SplashActivity.this, "Fetch and activate succeeded", Toast.LENGTH_SHORT).show();
                            String theme =  getDataFromRemote();
                            if(theme.equalsIgnoreCase("thor")){
                                gifImageView.setImageResource(R.drawable.thorfull);
                            }
                            else if(theme.equalsIgnoreCase("iron_man")){
                                gifImageView.setImageResource(R.drawable.ironfull);
                            }
                            else{
                                gifImageView.setImageResource(R.drawable.ironfull);
                            }

                        } else {
                            Toast.makeText(SplashActivity.this, "Fetch failed",
                                    Toast.LENGTH_SHORT).show();
                            gifImageView.setImageResource(R.drawable.ironfull);
                        }
                    }
                });
    }

    private String getDataFromRemote() {
        String theme = (String) ABTestingApp.getInstance().getFirebaseRemoteConfig().getString("theme");
        Log.d("Theme",theme);
        Toast.makeText(SplashActivity.this, "Theme: " + theme, Toast.LENGTH_SHORT).show();
        SharedPreferenceManager.singleton().save("theme",theme);
        return  SharedPreferenceManager.singleton().getString("theme");
    }


    @Override
    protected void onResume() {
        super.onResume();
        isActivityVisible = true;
        delayedHide(6000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActivityVisible = false;
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            }
        }, delayMillis);
    }

}