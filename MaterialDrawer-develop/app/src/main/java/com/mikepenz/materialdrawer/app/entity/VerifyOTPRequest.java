package com.mikepenz.materialdrawer.app.entity;

public class VerifyOTPRequest {
      private String countryCode;
      private String mobileNumber;
      private String oneTimePassword;

      public String getCountryCode()
      {
            return countryCode;
      }

      public void setCountryCode(String countryCode)
      {
            this.countryCode = countryCode;
      }

      public String getMobileNumber()
      {
            return mobileNumber;
      }

      public void setMobileNumber(String mobileNumber)
      {
            this.mobileNumber = mobileNumber;
      }

      public String getOneTimePassword()
      {
            return oneTimePassword;
      }

      public void setOneTimePassword(String oneTimePassword)
      {
            this.oneTimePassword = oneTimePassword;
      }
}
