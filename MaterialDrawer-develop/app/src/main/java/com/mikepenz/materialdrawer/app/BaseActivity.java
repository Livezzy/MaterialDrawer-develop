package com.mikepenz.materialdrawer.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.app.contracts.Constants;
import com.mikepenz.materialdrawer.app.contracts.TypefaceSpan;
import com.mikepenz.materialdrawer.app.contracts.UiUtils;
import com.mikepenz.materialdrawer.app.contracts.ValidateUtils;

public class BaseActivity extends AppCompatActivity {

    private static final int menuItemIconSize = 18;

    private ProgressDialog _progressDialog;
    public boolean _isProceedToCheckOut;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        Intent i = getIntent();
        if (i != null) {
            String str = i.getStringExtra(Constants.BUNDLE_KEY_PROCEED_TO_CHECKOUT);
            if (ValidateUtils.isStringValidated(str)) {
                _isProceedToCheckOut = true;
            }
        }
    }

    public void setActonBarTitleBar(String titleBar) {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Log.i("test", "error setActonBarTitleBar() proivde action bar");
        }

        SpannableString s = new SpannableString(titleBar);
        s.setSpan(new TypefaceSpan(this, Constants.defaultBoldFont), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        final ForegroundColorSpan fcs = new ForegroundColorSpan(getResources().getColor(R.color.white));
        s.setSpan(fcs, 0, s.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        actionBar.setTitle(s);
    }

    public void showBackButton() {
        IconicsDrawable iconicsDrawable = new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_arrow_back)
                .color(Color.WHITE).sizeDp(menuItemIconSize);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(iconicsDrawable);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public void updateMenuIconButton(Menu menu) {
        MenuItem searchItem = menu.findItem(R.id.search);
        if (searchItem == null) {
            return;

        }
        IconicsDrawable iconicsDrawable = new IconicsDrawable(this)
                .icon(GoogleMaterial.Icon.gmd_search)
                .color(Color.WHITE).sizeDp(menuItemIconSize);
        searchItem.setIcon(iconicsDrawable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.menu_bar
        )));
    }


    public boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }

    public void showNoInternetDialogWarning() {
        UiUtils.showDefaultAlertDialog(this, "",
                getString(R.string.internet_errorMsg));
    }

    public void showProgressDialog(String resTitle, String msg) {
        _progressDialog = new ProgressDialog(this);
        if (resTitle != null) {
            _progressDialog.setTitle(resTitle);
        }
        if (msg != null) {
            _progressDialog.setMessage(msg);
        }
        _progressDialog.show();
    }

    public void showProgressDialogDefault() {
        showProgressDialog(null, getString(R.string.please_wait_msg));
    }

    public void stopProgressDialog() {
        if (_progressDialog != null && _progressDialog.isShowing()) {
            _progressDialog.cancel();
        }
    }

    public void showOkDialog(String title, String msg) {
        UiUtils.showDefaultAlertDialog(this, title, msg);
    }

    public void showDummyWaitDialog() {
        showProgressDialogDefault();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                stopProgressDialog();
            }
        }, 3 * 1000);
    }

    public void navigateToHomeScreen() {
        startActivity(new Intent(this, HomeActivity.class));
    }
}
