package com.mikepenz.materialdrawer.app.contracts;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.SoftReference;
import java.util.Hashtable;

public class UiUtils {

    public static void showDefaultAlertDialog(Context context, String title,
                                              String msg) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setMessage(msg != null ? msg : "")
                .setTitle(title != null ? title : "")
                .setPositiveButton(android.R.string.yes,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                            }
                        });

        AlertDialog alertDialog = builder.create();
//        alertDialog.getWindow().setBackgroundDrawable(context.getResources().getDrawable(android.R.drawable.dialog_holo_light_frame));
        alertDialog.show();
    }

    public static void showNoResultDialog(Context context) {
        showDefaultAlertDialog(context, "No Result", "No result from host");
    }

    public static void showNoInternetDialog(Context context) {
        showDefaultAlertDialog(context, "No Internet", "Please check internet!");
    }

    public static void showServerCommunicationErrorDialog(Context context) {
        showDefaultAlertDialog(context, "Error", "Error while communicating with server");
    }

    public static void showDummyToast(String msg, Context context) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    public static void showDummyToastNeedToImplement(Context context) {
        Toast.makeText(context, "Need to implement...", Toast.LENGTH_SHORT)
                .show();
    }

    public static void main(String[] args) {
        System.out.println("Hello");
    }

    public static void setCustomFont(View tv, Context ctx, AttributeSet attrs,
                                     int[] attributeSet, int fontId) {

        try {
            TypedArray a = ctx.obtainStyledAttributes(attrs, attributeSet);
            String customFont = a.getString(fontId);
            int style = attrs.getAttributeIntValue(
                    "http://schemas.android.com/apk/res/android",
                    "textStyle",
                    Typeface.NORMAL);
            Log.i("test", "customFont: " + customFont);
            setCustomFont(tv, ctx, customFont, style);
            a.recycle();

        } catch (Exception e) {

        }

    }

    public static void setDefaultFont(View view, Context context, String assets, int style) {
        setCustomFont(view, context, assets, style);
    }

    private static boolean setCustomFont(View view, Context ctx, String asset, int style) {
        Typeface tf = null;
        try {
            if (TextUtils.isEmpty(asset)) {
                String font = style == Typeface.BOLD ? Constants.defaultBoldFont : Constants.defaultNormalFont;

                tf = getFont(ctx, font);
            } else {
                tf = getFont(ctx, asset);
            }
            if (view instanceof TextView) {
                ((TextView) view).setTypeface(tf);
            } else if (view instanceof Button) {
                ((Button) view).setTypeface(tf);
            } else if (view instanceof EditText) {
                ((EditText) view).setTypeface(tf);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("test", "error type face " + e);
//			_logger.error("Could not get typeface: " + asset, e);
            return false;
        }

        return true;
    }

    private static final Hashtable<String, SoftReference<Typeface>> fontCache = new Hashtable<String, SoftReference<Typeface>>();

    public static Typeface getFont(Context context, String name) {
        synchronized (fontCache) {
            if (fontCache.get(name) != null) {
                SoftReference<Typeface> ref = fontCache.get(name);
                if (ref.get() != null) {
                    return ref.get();
                }
            }

            Typeface typeface = Typeface.createFromAsset(context.getAssets(),
                    "fonts/" + name);
            Log.i("test", "getFont tf: " + typeface);
            fontCache.put(name, new SoftReference<Typeface>(typeface));

            return typeface;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }
}
