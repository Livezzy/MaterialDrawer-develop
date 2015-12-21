package com.mikepenz.materialdrawer.app.entity;

import com.google.gson.annotations.SerializedName;

public class GenerateOTPResponse {
      private String status;
      @SerializedName("response")
      private GenerateOTPResponseData response;

      public String getStatus() {
            return status;
      }

      public void setStatus(String status) {
            this.status = status;
      }

      public GenerateOTPResponseData getResponse() {
            return response;
      }

      public void setResponse(GenerateOTPResponseData response) {
            this.response = response;
      }

}
