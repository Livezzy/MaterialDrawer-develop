package com.mikepenz.materialdrawer.app;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.WindowManager;

import com.mikepenz.materialdrawer.app.contracts.Constants;
import com.mikepenz.materialdrawer.app.core.PrefManager;
import com.mikepenz.materialdrawer.app.core.UserManager;

public class SplashActivity extends BaseActivity {

    private static final String TAG = "SplashActivity";
    private static final int _splashTime = 3 * 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();
        setContentView(R.layout.splashactivity);

        final boolean isAppStart = PrefManager.getBoolean(this, Constants.PREF_FIRST_TIME_APP_OPEN_ID_KEY);

        Log.i(TAG, "isFirstTime : " + isAppStart);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isAppStart) {
                    PrefManager.saveBoolean(SplashActivity.this, Constants.PREF_FIRST_TIME_APP_OPEN_ID_KEY, true);
                    startActivity(new Intent(SplashActivity.this,
                            SignUpActivity.class));
                    finish();
                    return;
                }

                startActivity(new Intent(SplashActivity.this,
                        HomeActivity.class));
                finish();

            }
        }, 2 * 1000);


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
