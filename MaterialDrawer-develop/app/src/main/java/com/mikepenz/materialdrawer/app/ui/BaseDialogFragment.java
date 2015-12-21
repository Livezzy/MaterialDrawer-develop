package com.mikepenz.materialdrawer.app.ui;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.mikepenz.materialdrawer.app.R;
import com.mikepenz.materialdrawer.app.contracts.UiUtils;


public class BaseDialogFragment extends DialogFragment {
	private ProgressDialog _progressDialog;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		return super.onCreateDialog(savedInstanceState);
	}

	public  void replaceFragment(FragmentActivity context,
			Fragment fragment, int id, String name) {
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		if (name != null) {
			fragmentManager.beginTransaction().replace(id, fragment, name)
					.commit();
		} else {
			fragmentManager.beginTransaction().replace(id, fragment).commit();
		}
	}

	public void addFragment(FragmentActivity context, Fragment fragment,
			int id, String name) {
		FragmentManager fragmentManager = context.getSupportFragmentManager();
		if (name != null) {
			fragmentManager.beginTransaction().add(id, fragment, name).commit();
		} else {
			fragmentManager.beginTransaction().replace(id, fragment).commit();
		}
	}


	public void showNoInternetDialogWarning() {
		UiUtils.showDefaultAlertDialog(getActivity(), "",
				getString(R.string.internet_errorMsg));
	}

	public void showProgressDialog(String resTitle, String msg) {
		_progressDialog = new ProgressDialog(getActivity());
		if (resTitle != null) {
			_progressDialog.setTitle(resTitle);
		}
		if (msg != null) {
			_progressDialog.setMessage(msg);
		}
		_progressDialog.show();
	}

	public void showProgressDialogDefault() {
		showProgressDialog(null, getString(R.string.please_wait_msg));
	}

	public void stopProgressDialog() {
		if (_progressDialog != null && _progressDialog.isShowing()) {
			_progressDialog.cancel();
		}
	}

	public void showOkDialog(String title, String msg) {
		UiUtils.showDefaultAlertDialog(getActivity(), title, msg);
	}

	public void showDummyWaitDialog() {
		showProgressDialogDefault();
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				stopProgressDialog();
			}
		}, 3 * 1000);
	}
}
