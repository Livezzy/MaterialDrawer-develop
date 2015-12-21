package com.mikepenz.materialdrawer.app.entity;

import com.google.gson.annotations.SerializedName;

public class VerifyOTPResponse {
      private String status;
      @SerializedName("response")
      private VerifyOTPResponseData response;

      public String getStatus()
      {
            return status;
      }

      public void setStatus(String status)
      {
            this.status = status;
      }

      public VerifyOTPResponseData getResponse()
      {
            return response;
      }

      public void setResponse(VerifyOTPResponseData response)
      {
            this.response = response;
      }
}
