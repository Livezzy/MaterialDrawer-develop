package com.mikepenz.materialdrawer.app.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.materialdrawer.app.BaseActivity;
import com.mikepenz.materialdrawer.app.R;
import com.mikepenz.materialdrawer.app.com.widget.CustomButton;
import com.mikepenz.materialdrawer.app.com.widget.CustomEditText;
import com.mikepenz.materialdrawer.app.contracts.Constants;
import com.mikepenz.materialdrawer.app.contracts.ServiceClient.UserServiceClient;
import com.mikepenz.materialdrawer.app.contracts.ValidateUtils;
import com.mikepenz.materialdrawer.app.entity.GenerateOTPRequest;
import com.mikepenz.materialdrawer.app.entity.VerifyOTPRequest;
import com.mikepenz.materialdrawer.app.handler.IGenerateOTPHandler;
import com.mikepenz.materialdrawer.app.handler.IVerifyOTPHandler;

import org.w3c.dom.Text;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener, IGenerateOTPHandler, IVerifyOTPHandler{

    private Toolbar toolbar;
    private LinearLayout linearLayoutChangePassword;
    private LinearLayout linearLayoutOTPVerification;
    private CustomEditText editTextEmailAddress;
    private CustomEditText editTextMobileNumber;
    private CustomEditText editTextNewPassword;
    private CustomEditText editTextConfirmPassword;
    private CustomEditText editTextEnterMobileNumber;
    private CustomEditText editTextEnterOTP;
    private CustomButton actionButton;
    private TextView textViewEyeNewPassword;
    private TextView textViewEyeConfirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget_password_activity);

        // Handle Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setActonBarTitleBar(getString(R.string.title_activity_forget_password));

        init();
    }

    private void init() {
        linearLayoutChangePassword = (LinearLayout) findViewById(R.id.linearLayoutChangePassword);
        linearLayoutOTPVerification = (LinearLayout) findViewById(R.id.linearLayoutOTPVerification);

        editTextEmailAddress = (CustomEditText) findViewById(R.id.editTextEmailAddress);
        editTextMobileNumber = (CustomEditText) findViewById(R.id.editTextMobileNumber);
        editTextNewPassword = (CustomEditText) findViewById(R.id.editTextNewPassword);
        editTextConfirmPassword = (CustomEditText) findViewById(R.id.editTextConfirmPassword);
        textViewEyeNewPassword = (TextView) findViewById(R.id.textViewEyeNewPassword);
        textViewEyeConfirmPassword = (TextView) findViewById(R.id.textViewEyeConfirmPassword);
        editTextEnterMobileNumber = (CustomEditText) findViewById(R.id.editTextEnterMobileNumber);
        editTextEnterOTP = (CustomEditText) findViewById(R.id.editTextEnterOTP);

        actionButton = (CustomButton) findViewById(R.id.actionButton);
        actionButton.setText("Generate OTP");
        actionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        switch (viewId){
            case R.id.actionButton:
                clickOnActionButton();
                break;
        }
    }

    private void clickOnActionButton() {
        if(actionButton.getText().equals("OK")) {
            //call change password web service and if success go to login screen
        } else {
            generateOTP();
        }
    }


    private void generateOTP() {
        String mobile_number = editTextEnterMobileNumber.getText().toString();
        if (!ValidateUtils.isStringValidated(mobile_number)) {
            showOkDialog("Phone Number error!", "Please provide phone number!");
            return;
        }

        if(editTextEnterOTP.getVisibility() == View.VISIBLE) {
            String otp = editTextEnterOTP.getText().toString();

            VerifyOTPRequest verifyOTPRequest = new VerifyOTPRequest();
            verifyOTPRequest.setCountryCode("91");
            verifyOTPRequest.setMobileNumber(mobile_number);
            verifyOTPRequest.setOneTimePassword(otp);

            UserServiceClient.verifyOTP(verifyOTPRequest, this);
        } else {
            GenerateOTPRequest generateOTPRequest = new GenerateOTPRequest();
            generateOTPRequest.setCountryCode("91");
            generateOTPRequest.setMobileNumber(mobile_number);

            UserServiceClient.generateOTP(generateOTPRequest, this);
        }
    }

    @Override
    public void onSuccessGenerateOTP() {
        editTextEnterOTP.setVisibility(View.VISIBLE);
        actionButton.setText("Verify");
    }

    @Override
    public void onErrorGenerateOTP() {
        editTextEnterOTP.setVisibility(View.GONE);
        actionButton.setText("Generate OTP");
    }

    @Override
    public void onSuccessVerifyOTP() {
        String mobile_number = editTextEnterMobileNumber.getText().toString();

        linearLayoutChangePassword.setVisibility(View.VISIBLE);
        linearLayoutOTPVerification.setVisibility(View.GONE);

        Intent receivedIntent = getIntent();
        String emailId = receivedIntent.getStringExtra("email");

        editTextEmailAddress.setText(emailId);
        editTextMobileNumber.setText(mobile_number);

        actionButton.setText("OK");
    }

    @Override
    public void onErrorVerifyOTP() {
        editTextEnterOTP.setVisibility(View.GONE);
        actionButton.setText("Generate OTP");
    }
}
