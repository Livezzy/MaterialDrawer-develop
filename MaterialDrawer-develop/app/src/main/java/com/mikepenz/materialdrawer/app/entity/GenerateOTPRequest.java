package com.mikepenz.materialdrawer.app.entity;

public class GenerateOTPRequest {
      private String countryCode;
      private String mobileNumber;

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

      @Override
      public String toString() {
            return "countryCode = " + countryCode + ", mobileNumber = "
                  + mobileNumber + "]";
      }
}
