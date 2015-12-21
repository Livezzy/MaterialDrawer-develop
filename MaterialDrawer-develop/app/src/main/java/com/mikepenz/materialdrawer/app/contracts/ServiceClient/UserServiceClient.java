package com.mikepenz.materialdrawer.app.contracts.ServiceClient;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.mikepenz.materialdrawer.app.BaseActivity;
import com.mikepenz.materialdrawer.app.SignUpActivity;
import com.mikepenz.materialdrawer.app.comm.ServiceRequest;
import com.mikepenz.materialdrawer.app.contracts.CommUtils;
import com.mikepenz.materialdrawer.app.contracts.Constants;
import com.mikepenz.materialdrawer.app.entity.GenerateOTPRequest;
import com.mikepenz.materialdrawer.app.entity.GenerateOTPResponse;
import com.mikepenz.materialdrawer.app.entity.VerifyOTPRequest;
import com.mikepenz.materialdrawer.app.entity.VerifyOTPResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UserServiceClient {
      private static final String TAG = "Generate OTP";
      public static void generateOTP(GenerateOTPRequest request, final BaseActivity baseActivity) {
            String url = "http://sendotp.msg91.com/api/generateOTP";
            JSONObject jsonObject = CommUtils.toJsonObject(request);
            if (jsonObject != null)
            {
                  baseActivity.showProgressDialog(null, "Sending OTP...");
                  Log.d("volleyRequest", request.toString());
                  JsonObjectRequest req = new JsonObjectRequest(url, jsonObject,
                        new Response.Listener<JSONObject>()
                        {
                              @Override
                              public void onResponse(JSONObject response)
                              {
                                    baseActivity.stopProgressDialog();
                                    Log.d(TAG, " volleyRequest " + response.toString());
                                    GenerateOTPResponse sendOTPResponse = (GenerateOTPResponse) new Gson().fromJson(response.toString(), GenerateOTPResponse.class);
                                    Log.d(TAG, "volleyRequest....: " + sendOTPResponse);
                                    ((SignUpActivity) baseActivity).onSuccessGenerateOTP();
                              }
                        }, new Response.ErrorListener()
                  {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                              baseActivity.stopProgressDialog();
                              VolleyLog.d(TAG, "volleyRequestCat: error " + error);
                              ((SignUpActivity) baseActivity).onErrorGenerateOTP();
                        }
                  })
                  {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError
                        {
                              HashMap<String, String> headers = new HashMap<String, String>();
                              headers.put("Content-Type", "application/json");
                              headers.put("Application-Key", Constants.OTP_APPLICATION_KEY);

                              return headers;
                        }
                  };
                  ServiceRequest.getRequestQueue().getCache().clear();
                  ServiceRequest.getRequestQueue().add(req);
            }
      }

      public static void verifyOTP(VerifyOTPRequest request, final BaseActivity baseActivity) {
            String url = "http://sendotp.msg91.com/api/verifyOTP";
            JSONObject jsonObject = CommUtils.toJsonObject(request);

            if (jsonObject != null)
            {
                  baseActivity.showProgressDialog(null, "Verifying OTP...");
                  Log.d("volleyRequest", request.toString());
                  JsonObjectRequest req = new JsonObjectRequest(url, jsonObject,
                        new Response.Listener<JSONObject>()
                        {
                              @Override
                              public void onResponse(JSONObject response)
                              {
                                    baseActivity.stopProgressDialog();
                                    Log.d(TAG, " volleyRequest " + response.toString());
                                    VerifyOTPResponse verifyOTPResponse = (VerifyOTPResponse) new Gson().fromJson(response.toString(), VerifyOTPResponse.class);
                                    Log.d(TAG, "volleyRequest....: " + verifyOTPResponse);
                                    ((SignUpActivity) baseActivity).onSuccessVerifyOTP();
                              }
                        }, new Response.ErrorListener()
                  {
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                              baseActivity.stopProgressDialog();
                              VolleyLog.d(TAG, "volleyRequestCat: error " + error);
                              ((SignUpActivity) baseActivity).onErrorVerifyOTP();
                        }
                  })
                  {
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError
                        {
                              HashMap<String, String> headers = new HashMap<String, String>();
                              headers.put("Content-Type", "application/json; charset=utf-8");
                              headers.put("Application-Key", Constants.OTP_APPLICATION_KEY);
                              return headers;
                        }
                  };
                  ServiceRequest.getRequestQueue().add(req);
            }
      }
}
