package com.mikepenz.materialdrawer.app.entity;



public class UserProfile
{
    private String name;
    private String password;
    private String mobileNumber;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword ()
    {
        return password;
    }

    public void setPassword (String password)
    {
        this.password = password;
    }

    @Override
    public String toString()
    {
        return "UserProfile [name = "+name+", password = "+password+", mobileNumber = "+mobileNumber+"]";
    }
}
			
			