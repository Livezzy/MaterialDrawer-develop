package com.mikepenz.materialdrawer.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.app.comm.ServiceRequest;
import com.mikepenz.materialdrawer.app.contracts.CommUtils;
import com.mikepenz.materialdrawer.app.contracts.Constants;
import com.mikepenz.materialdrawer.app.contracts.ValidateUtils;
import com.mikepenz.materialdrawer.app.core.UserManager;
import com.mikepenz.materialdrawer.app.entity.LoginResponse;
import com.mikepenz.materialdrawer.app.ui.ForgetPasswordActivity;

import org.json.JSONObject;

public class LoginActivity extends BaseActivity {
    private static final String TAG = "LoginActivity";

    private EditText _emailView;
    private EditText _passwordView;
    private String _email;
    private String _password;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "LoginActivity....................");
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getSupportActionBar().hide();
        setContentView(R.layout.loginactivity);

        init();
    }

    private void init() {

        _emailView = ((EditText) findViewById(R.id.emailAddress));
        _passwordView = ((EditText) findViewById(R.id.password));
        final int opacity40 = 95;
        findViewById(R.id.person).setBackgroundDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_account).color(getResources()
                .getColor(R.color.black)).alpha(opacity40));

        findViewById(R.id.lockicon).setBackgroundDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_lock_outline).color(getResources()
                .getColor(R.color.black)).alpha(opacity40));

        findViewById(R.id.eye).setBackgroundDrawable(new IconicsDrawable(this, GoogleMaterial.Icon.gmd_eye).color(getResources()
                .getColor(R.color.black)).alpha(opacity40));

        findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        });

        findViewById(R.id.forgetPassword).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!isNetworkAvailable()) {
                    showNoInternetDialogWarning();
                    return;
                }
                _email = _emailView.getText().toString();
                if (!ValidateUtils.isEmailValidated(_email)) {
                    showOkDialog("Email error!", "Please provide valid email!");
                    return;
                }

                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                intent.putExtra("email_id", _email);
                startActivity(intent);
            }
        });

        findViewById(R.id.signUpNow).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                if (_isProceedToCheckOut) {
                    intent.putExtra(Constants.BUNDLE_KEY_PROCEED_TO_CHECKOUT, "login first");
                }
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.loginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isNetworkAvailable()) {
                    showNoInternetDialogWarning();
                    return;
                }

                Request request = new Request();
                _email = _emailView.getText().toString();
                if (!ValidateUtils.isEmailValidated(_email)) {
                    showOkDialog("Email error!", "Please provide valid email!");
                    return;
                }

                _password = _passwordView.getText().toString();
                if (!ValidateUtils.isStringValidated(_password)) {
                    showOkDialog("Password error!", "Please provide password!");
                    return;
                }

                request.emailId = _emailView.getText().toString();
                request.password = _passwordView.getText().toString();
                String URL = Constants.BASE_URL + "/user/login?" + CommUtils.getRequestString(new Gson().toJson(request));
                Log.d(TAG, "url login" + URL);
                volleyRequest(URL);
            }
        });

    }

    private void volleyRequest(String URL) {


        showDummyWaitDialog();

        RequestQueue mVolleyQueue = ServiceRequest.getRequestQueue();

        JsonObjectRequest req = new JsonObjectRequest(URL,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        stopProgressDialog();
                        Log.d(TAG, response.toString());
                        Gson gson = new Gson();
                        LoginResponse signUpResponse = gson.fromJson(response.toString(), LoginResponse.class);
                        Log.d(TAG, "signUpRequest....: " + signUpResponse.isSuccess);
                        if (signUpResponse.isSuccess) {
                            UserManager.getInstance().saveUser(signUpResponse.getUser(), LoginActivity.this);
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            if (_isProceedToCheckOut) {
                                new Intent(LoginActivity.this, CheckOutActivity.class);
                            }
                            startActivity(intent);
                            finish();
                        } else {
                            showOkDialog("Login Error!", signUpResponse.description);
                        }
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

    //internal request class
    class Request {
        public String emailId;
        public String password;
    }
}
