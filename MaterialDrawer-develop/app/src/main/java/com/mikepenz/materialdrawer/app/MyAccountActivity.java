package com.mikepenz.materialdrawer.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.app.comm.ServiceRequest;
import com.mikepenz.materialdrawer.app.contracts.Constants;
import com.mikepenz.materialdrawer.app.entity.Location;
import com.mikepenz.materialdrawer.app.entity.TestModal;

import org.json.JSONObject;

public class MyAccountActivity extends BaseActivity {

    private static final String TAG = "MyAccountActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myaccountactivity);

        Log.i(TAG, "onCreate()");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //  setSupportActionBar(toolbar);
        showBackButton();
        setActonBarTitleBar("My Account");
        this.setupView();
//        this.volleyRequest();
        findViewById(R.id.myOrdersBtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyAccountActivity.this, MyOrdersActivity.class));
            }
        });
        findViewById(R.id.myAddressBtn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                startActivity(new Intent(MyAccountActivity.this, AddressActivity.class));
            }
        });
    }

    private void setupView() {
        ((TextView) findViewById(R.id.myorder_rightArrow)).setBackgroundDrawable(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_chevron_right).color(getResources().getColor(R.color.grey)).sizeDp(13));
        ((TextView) findViewById(R.id.myAddress_rightArrow)).setBackgroundDrawable(new IconicsDrawable(this).icon(GoogleMaterial.Icon.gmd_chevron_right).color(getResources().getColor(R.color.grey)).sizeDp(13));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return true;
        }
    }

    // TestModal modal =new TestModal(); modal.setI(33);
    // modal.setH("fuckkkkkkkk"); Location location2 = new Location();
    // location2.setCity("city"); location2.setLat("s");
    // location2.setLng("lng");
    // modal.setLocation(location2);
    private void volleyRequest() {
        JSONObject jsonObject = null;
        TestModal modal = new TestModal();
        modal.setI(33);
        modal.setH("fuckkkkkkkk");
        Location location2 = new Location();
        location2.setCity("city");
        location2.setLat("s");
        location2.setLng("lng");
        modal.setLocation(location2);
        String URL = Constants.BASE_URL + "/test/tc";
        Log.d(TAG, "url login" + URL);

        try {
            jsonObject = new JSONObject(new Gson().toJson(modal));
        } catch (Exception e) {
        }

        volleyRequest(URL, jsonObject);
    }

    private void volleyRequest(String URL, JSONObject model) {

        showDummyWaitDialog();
        RequestQueue mVolleyQueue = ServiceRequest.getRequestQueue();
        Log.d("JSONObject", model.toString());
        JsonObjectRequest req = new JsonObjectRequest(URL, model, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                stopProgressDialog();
                Log.d(TAG, response.toString());
                      /*  Gson gson = new Gson();
                        LoginResponse signUpResponse = gson.fromJson(response.toString(), LoginResponse.class);*//*
                        Log.d(TAG, "signUpRequest....: " + signUpResponse.isSuccess);*/
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                stopProgressDialog();
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                showOkDialog("Login Error!", "Server error!");
            }
        });

        mVolleyQueue.add(req);
    }

}