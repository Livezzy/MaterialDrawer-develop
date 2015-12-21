package com.mikepenz.materialdrawer.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.app.contracts.CommUtils;
import com.mikepenz.materialdrawer.app.contracts.Constants;
import com.mikepenz.materialdrawer.app.contracts.ServiceClient.UserServiceClient;
import com.mikepenz.materialdrawer.app.contracts.UiUtils;
import com.mikepenz.materialdrawer.app.contracts.ValidateUtils;
import com.mikepenz.materialdrawer.app.core.UserManager;
import com.mikepenz.materialdrawer.app.entity.GenerateOTPRequest;
import com.mikepenz.materialdrawer.app.entity.SignUpResponse;
import com.mikepenz.materialdrawer.app.entity.VerifyOTPRequest;
import com.mikepenz.materialdrawer.app.handler.IGenerateOTPHandler;
import com.mikepenz.materialdrawer.app.handler.IVerifyOTPHandler;

import org.json.JSONObject;

public class SignUpActivity extends BaseActivity implements IGenerateOTPHandler, IVerifyOTPHandler
{
      private static final String TAG = "SignUpActivity";

      private EditText _firstNameView;
      private EditText _emailView;
      private EditText _primaryPhoneNumberView;
      private EditText _passwordView;
      private EditText _otpView;
      private EditText _promoCodeView;


      private String _firstName;
      private String _email;
      private String _primaryPhoneNumber;
      private String _password;
      private String _promoCode;

      @Override
      public void onCreate(Bundle savedInstanceState) {
            Log.i(TAG, "LoginActivity....................");
            super.onCreate(savedInstanceState);
            //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
            this.getSupportActionBar().hide();
            setContentView(R.layout.signupactivity);

            init();
      }

      private void init() {

            _firstNameView = (EditText) findViewById(R.id.firstName);
            _emailView = ((EditText) findViewById(R.id.emailAddress));
            _primaryPhoneNumberView = (EditText) findViewById(R.id.mobileNumber);
            _passwordView = ((EditText) findViewById(R.id.password));
            _otpView = ((EditText) findViewById(R.id.otp));
            _promoCodeView = (EditText) findViewById(R.id.promoCode);

            findViewById(R.id.skip).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                        startActivity(new Intent(SignUpActivity.this, HomeActivity.class));
                        finish();
                  }
            });


            findViewById(R.id.sign_up).setOnClickListener(new View.OnClickListener() {

                  @Override
                  public void onClick(View view) {

                        UiUtils.hideKeyboard(SignUpActivity.this);
                        if (!isNetworkAvailable()) {
                              showNoInternetDialogWarning();
                              return;
                        }

                        _firstName = _firstNameView.getText().toString();
                        if (!ValidateUtils.isStringValidated(_firstName)) {
                              showOkDialog("Password error!", "Please provide name!");
                              return;
                        }

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


                        _primaryPhoneNumber = _primaryPhoneNumberView.getText().toString();
                        if (!ValidateUtils.isStringValidated(_primaryPhoneNumber)) {
                              showOkDialog("Phone Number error!", "Please provide phone number!");
                              return;
                        }

                        verifyOTP();
                  }
            });

            findViewById(R.id.loginNow).setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                        if (_isProceedToCheckOut) {
                              intent.putExtra(Constants.BUNDLE_KEY_PROCEED_TO_CHECKOUT, "login first");
                        }
                        startActivity(intent);
                        finish();
                  }
            });
      }

      private void verifyOTP() {
            if (isNetworkAvailable()) {
                  String otp = _otpView.getText().toString();
                  if(otp.equals("")) {
                        GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
                        generateOTPRequest.setCountryCode("91");
                        generateOTPRequest.setMobileNumber(_primaryPhoneNumber);
                        UserServiceClient.generateOTP(generateOTPRequest, this);
                  } else {
                        VerifyOTPRequest verifyOTPRequest = new VerifyOTPRequest();
                        verifyOTPRequest.setCountryCode("91");
                        verifyOTPRequest.setMobileNumber(_primaryPhoneNumber);
                        verifyOTPRequest.setOneTimePassword(otp);
                        UserServiceClient.verifyOTP(verifyOTPRequest, this);
                  }
            } else {
                  showNoInternetDialogWarning();
            }
      }

      private void volleyRequest(String URL) {
            RequestQueue mVolleyQueue = Volley.newRequestQueue(this);

            showDummyWaitDialog();

            JsonObjectRequest req = new JsonObjectRequest(URL,
                  new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                              stopProgressDialog();
                              Log.d(TAG, response.toString());
                              Gson gson = new Gson();
                              SignUpResponse signUpResponse = gson.fromJson(response.toString(), SignUpResponse.class);
                              Log.d(TAG, "signUpRequest....: " + signUpResponse.isSuccess);
                              if (signUpResponse.isSuccess) {
                                    UserManager.getInstance().saveUser(signUpResponse.getUser(), SignUpActivity.this);

                                    Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                                    if (_isProceedToCheckOut) {
                                          new Intent(SignUpActivity.this, CheckOutActivity.class);
                                    }
                                    startActivity(intent);
                                    finish();

                              } else {
                                    showOkDialog("Sign Up Error!", " " + signUpResponse.description);
                              }
                        }
                  }, new Response.ErrorListener() {
                  @Override
                  public void onErrorResponse(VolleyError error) {
                        VolleyLog.d(TAG, "Error: " + error.getMessage());
                        stopProgressDialog();
                        showOkDialog("Sign Up Error!", "Server error!");
                  }
            });

            mVolleyQueue.add(req);
      }

      @Override
      public void onSuccessGenerateOTP()
      {
            _otpView.setVisibility(View.VISIBLE);
            _otpView.requestFocus();
      }

      @Override
      public void onErrorGenerateOTP()
      {
            //_otpView.setVisibility(View.INVISIBLE);

      }

      @Override
      public void onSuccessVerifyOTP()
      {
            _promoCode = _promoCodeView.getText().toString();

            Request request = new Request();
            request.name = _firstName;
            request.emailId = _email;
            request.mobileNumber = _primaryPhoneNumber;
            request.password = _password;
            request.referralCode = _promoCode;
            String URL = Constants.BASE_URL + "/user/signUp?" + CommUtils.getRequestString(new Gson().toJson(request));
            Log.d(TAG, "url signup" + URL);
            volleyRequest(URL);
      }

      @Override
      public void onErrorVerifyOTP()
      {
            Toast.makeText(this, "Invalid OTP", Toast.LENGTH_LONG).show();
            _otpView.setText("");
            _otpView.requestFocus();
      }

      class Request {
            private String name;
            private String emailId;
            private String mobileNumber;
            private String password;
            private String referralCode;

      }


}
