package com.mikepenz.materialdrawer.app.com.widget;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

import com.mikepenz.materialdrawer.app.R;
import com.mikepenz.materialdrawer.app.contracts.Constants;
import com.mikepenz.materialdrawer.app.contracts.UiUtils;

public class CustomEditText extends EditText {

	public CustomEditText(Context context) {
		super(context);
		init(context, null, 0);
	}

	public CustomEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs, 0);
	}

	public CustomEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs, defStyle);
	}

	@Override
	public void setTypeface(Typeface tf, int style) {
		if (Constants.isXmlInEditMode) {
			return;
		}
		Typeface typeface = null;
		if (style == Typeface.BOLD) {
			typeface = UiUtils.getFont(getContext(), Constants.defaultBoldFont);
		} else {
			typeface = UiUtils.getFont(getContext(),
					Constants.defaultNormalFont);
		}
//		Log.i("test", "setTypeface tf: " + typeface + " style:" + style);
		if (typeface != null) {
			super.setTypeface(typeface);
		}
	}

	private void init(Context context, AttributeSet attrs, int defStyle) {
		if (Constants.isXmlInEditMode) {
			return;
		}
//		Log.i("test",
//				"init() Context context, AttributeSet attrs, int defStyle "
//						+ context + " : " + attrs + " : " + defStyle);
		if (attrs != null) {
			UiUtils.setCustomFont(
					this,
					context,
					attrs,
					R.styleable.com_fart_connecting_you_widgit_FontableTextView,
					R.styleable.com_fart_connecting_you_widgit_FontableTextView_font);
		}
	}
}
