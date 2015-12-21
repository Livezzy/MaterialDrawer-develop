package com.mikepenz.materialdrawer.app.com.widget;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;

public class BaseFrameLayout extends FrameLayout {

	@Override
	public void addView(View child) {
		super.addView(child);
	}

	public BaseFrameLayout(Context context) {
		super(context);
	}
}
