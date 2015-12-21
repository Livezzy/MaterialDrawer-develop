package com.mikepenz.materialdrawer.app.contracts;


public class ValidateUtils {

	public static boolean isStringValidated(String str) {
		if (str == null || str.length() == 0) {
			return false;
		}
		return true;
	}

	public static boolean isEmailValidated(String email) {

		if (email != null && email.length() > 0
				&& (email.matches(Constants.emailPattern))) {
			return true;
		}
		return false;
	}
}
