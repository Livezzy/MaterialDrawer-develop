package com.mikepenz.materialdrawer.app.core;

import android.content.Context;
import android.util.Log;

import com.mikepenz.materialdrawer.app.contracts.Constants;
import com.mikepenz.materialdrawer.app.entity.User;
import com.mikepenz.materialdrawer.app.entity.UserAddress;

import java.util.List;

public class UserManager {
    final static String TAG = "UserManager";

    private static UserManager userManager;
    private User user;

    private UserManager() {

    }

    public static UserManager getInstance() {
//		_logger.info("getInstance () userManager: " + userManager);
        if (userManager == null) {
            userManager = new UserManager();
        }

        return userManager;
    }

    public boolean isUserLoggedIn(Context context) {

        String userId = PrefManager.getString(context,
                Constants.PREF_USER_ID_KEY);
//		_logger.info("isUserLoggedIn() acessToken: " + acessToken + " userId: "
//				+ userId);
        if (userId != null) {
            return true;
        }
        return false;

    }

    public void logoutUser(Context context) {
//		_logger.info("logoutUser()");
        PrefManager.saveString(context, Constants.PREF_USER_ID_KEY, null);
    }

    public User getUser() {
        return user;

    }

    public void saveUser(User user, Context context) {

        Log.i(TAG, "saveUser () user " + user);
        if (user == null) {
            return;
        }

        this.user = user;

        PrefManager.saveString(context, Constants.PREF_USER_ID_KEY,
                user.getEmailId());

    }

    public boolean isUserAddressExists() {
        if (this.user == null) {
            return false;
        }
        List<UserAddress> userAddress = this.user.getUserAddresss();
        if (userAddress == null || userAddress.size() == 0) {
            return false;
        }
        return true;
    }

    public UserAddress getDefaultUserAddress() {
        if (!this.isUserAddressExists()) {
            return null;
        }
        for (UserAddress address : this.user.getUserAddresss()) {
            if (address.isDefaultAddress()) {
                return address;
            }
        }
        return null;
    }
}
