package com.mikepenz.materialdrawer.app.contracts;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.provider.Settings;
import android.widget.Toast;

import com.google.gson.Gson;
import com.mikepenz.materialdrawer.app.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class CommUtils {
    //	final static Logger _logger = Logger.getLogger(CommUtils.class);
    private static CharSequence upToCharacter = ".";


    public static String getAndroidDeviceId(Context context) {

        String android_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return android_id;
    }

    public static JSONObject toJsonObject(Object object) {
        if (object != null) {
            try {
                return new JSONObject(new Gson().toJson(object));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static String getRequestString(String jsonString) {

        if (jsonString == null || jsonString.length() == 0) {
            return "";
        }
        String s = jsonString.replace("}", "");
        String s1 = s.replace("{", "");
        String s2 = s1.replace(":", "=");
        String s3 = s2.replace(",", "&");
        String s4 = s3.replace("\"", "");

        return s4;
    }

    public static String replaceStringSpaceToUnderScore(String str) {
        if (str == null || str.length() == 0) {
            return "";
        }
        return str.replace(" ", "_");
    }

    public static String getThreeDigitCityName(String str) {

        String returnStr = "";
        try {
            if (str == null || str.length() == 0) {
                return "";
            }

            if (str.contains("New Delhi") || str.contains("new delhi")) {
                returnStr = (str.substring(3, str.length() - 1).trim())
                        .substring(0, 3);
            } else {
                returnStr = str.substring(0, 3);
            }

            returnStr = returnStr.substring(0, 1).toUpperCase(
                    Locale.getDefault())
                    + returnStr.substring(1).toLowerCase(Locale.getDefault());

        } catch (Exception e) {
//			_logger.error("error getThreeDigitCityName() ", e);
            e.printStackTrace();
        }

        return returnStr;
    }

    public static String formateDate(Date date) {
        if (date == null) {
            return "";
        }
        try {
            SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
            String strDate = formater.format(date);
            return strDate;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date.toString();
    }

    public static void openDefaultBrowser(Context context, String url) {
        if (!ValidateUtils.isStringValidated(url)) {
            return;
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        context.startActivity(browserIntent);
    }

    public static void shareOnOther(Context context, Uri imageUri, String text) {

        try {
            String appName = context.getString(R.string.app_name);
            String EMAIL_SUBJECT = "Check out this awesome app : " + appName;

            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("image/*");
            if (imageUri != null)
                share.putExtra(Intent.EXTRA_STREAM, imageUri);

            share.putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT);
            share.putExtra(Intent.EXTRA_TEXT, text);
            context.startActivity(Intent.createChooser(share, "Choose"));
        } catch (Exception e) {
//			_logger.error("error shareOnOther() ", e);
        }

    }

    public static void shareWhatssAPp(Activity context, String text, String url) {

        PackageManager pm = context.getPackageManager();
        try {

            Intent waIntent = new Intent(Intent.ACTION_SEND);
            waIntent.setType("text/plain");
            PackageInfo info = pm.getPackageInfo("com.whatsapp",
                    PackageManager.GET_META_DATA);
            // Check if package exists or not. If not then code
            // in catch block will be called
            waIntent.setPackage("com.whatsapp");
            if (text != null) {
                waIntent.putExtra(Intent.EXTRA_TEXT, text);
            }
            context.startActivity(Intent.createChooser(waIntent, "Share with"));

        } catch (NameNotFoundException e) {
            Toast.makeText(context, "WhatsApp not Installed",
                    Toast.LENGTH_SHORT).show();
        }

    }

    public static void shareOnWhatsApp(Activity context, Uri uri) {
        try {
            String imageCaption = "I created this awesome image using "
                    + context.getString(R.string.app_name) + ".\n"
                    + "You can download this app from Google Play: "
                    + Constants.MARKET_LINK + "" + context.getPackageName();

            Toast.makeText(context, "" + context.getString(R.string.app_name),
                    Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("image/*");
            intent.putExtra(Intent.EXTRA_STREAM, uri);
            intent.putExtra(Intent.EXTRA_TEXT, imageCaption);
            intent.putExtra(Intent.EXTRA_SUBJECT, imageCaption);
            intent.setPackage("com.whatsapp");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (intent != null) {
                context.startActivity(Intent.createChooser(intent,
                        "Share with : "));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void shareOnWhatsApp(Activity context, Uri uri, String text) {
        try {
            String imageCaption = "I created this awesome image using "
                    + context.getString(R.string.app_name)
                    + ".\n"
                    + "You can download this app from Google Play: https://play.google.com/store/apps/details?id="
                    + context.getPackageName();

            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
                android.text.ClipboardManager clipboard = (android.text.ClipboardManager) context
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                clipboard.setText(imageCaption);
            } else {
                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) context
                        .getSystemService(Context.CLIPBOARD_SERVICE);
                android.content.ClipData clip = android.content.ClipData
                        .newPlainText("sdjlfsdkfjs", imageCaption);
                clipboard.setPrimaryClip(clip);
            }

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setType("image/*");
            if (uri != null) {
                intent.putExtra(Intent.EXTRA_STREAM, uri);
            }
            intent.setPackage("com.whatsapp");
            if (intent != null) {
                intent.putExtra(Intent.EXTRA_TEXT, "");
                context.startActivity(Intent
                        .createChooser(intent, "Share with"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void allReviews(Activity context) {
        String uri = "";
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            uri = Constants.PLAY_LINK + context.getPackageName();
            intent.setData(Uri.parse(uri));
            context.startActivity(intent);
        } catch (Exception e) { // google play app is not installed
//			_logger.error("allReviews() error ", e);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            uri = Constants.MARKET_LINK + context.getPackageName();
            intent.setData(Uri.parse(uri));
            context.startActivity(intent);
        }
    }

    public static void call(Context context, String phoneNumber) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + phoneNumber));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
//			_logger.error("call() error ", e);
        }
    }

    public static void shareOnTwitter(Context context, String text, Uri uri) {
        try {
            Intent tweetIntent = new Intent(Intent.ACTION_SEND);
            tweetIntent.putExtra(Intent.EXTRA_TEXT, text);
            tweetIntent.setType("text/plain");
            tweetIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            PackageManager packManager = context.getPackageManager();
            List<ResolveInfo> resolvedInfoList = packManager
                    .queryIntentActivities(tweetIntent,
                            PackageManager.MATCH_DEFAULT_ONLY);

            boolean resolved = false;
            for (ResolveInfo resolveInfo : resolvedInfoList) {
                if (resolveInfo.activityInfo.packageName
                        .startsWith("com.twitter.android")) {
                    tweetIntent.setClassName(
                            resolveInfo.activityInfo.packageName,
                            resolveInfo.activityInfo.name);
                    resolved = true;
                    break;
                }
            }
            if (resolved) {
                context.startActivity(tweetIntent);
            } else {
                Intent i = new Intent();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                i.putExtra(Intent.EXTRA_TEXT, text);
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://twitter.com/intent/tweet?text="
                        + text));
                context.startActivity(i);
                Toast.makeText(context, "Twitter app isn't found",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
//			_logger.error("shareOnTwitter error", e);
        }
    }

    public static void shareOnGmail(Context context, String subject,
                                    String body, String url) {
        try {
            String[] recipients = new String[]{"sendme@me.com", ""};
            Intent testIntent = new Intent(Intent.ACTION_SEND);
            testIntent.setType("message/rfc822");
            if (subject != null) {
                testIntent.putExtra(Intent.EXTRA_SUBJECT,
                        subject);
            }
            StringBuffer bodyBuffer = new StringBuffer();
            if (body != null) {
                bodyBuffer.append(body);
            }
            if (url != null) {
                bodyBuffer.append(url);
            }
            if (bodyBuffer != null) {
                testIntent.putExtra(Intent.EXTRA_TEXT,
                        bodyBuffer.toString());
            }
            testIntent.putExtra(Intent.EXTRA_EMAIL, recipients);
            testIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            context.startActivity(testIntent);
        } catch (Exception e) {
//			_logger.error("shareOnGmail()", e);
        }
    }

    // private void shareOnFb() {
    // try {
    // FacebookDialog shareDialog = new FacebookDialog.ShareDialogBuilder(
    // this).setLink("https://developers.facebook.com/android")
    // .setPicture(uri.toString()).build();
    // uiHelper.trackPendingDialogCall(shareDialog.present());
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }

    public static void shareFb1(Activity context) {
        String fullUrl = "https://m.facebook.com/sharer.php?u=..";
        try {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setClassName("com.facebook.katana",
                    "com.facebook.katana.ShareLinkActivity");
            sharingIntent.putExtra(Intent.EXTRA_TEXT, "your title text");
            context.startActivity(sharingIntent);

        } catch (Exception e) {
            e.printStackTrace();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(fullUrl));
            context.startActivity(i);

        }
    }

    public static void shareFb(Activity context, String text, String urlToShare) {

        try {

            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            // String url = "http://www.amarujala.com/";
            if (urlToShare != null) {
                intent.putExtra(Intent.EXTRA_TEXT, urlToShare);
            }
            PackageManager packManager = context.getPackageManager();
            List<ResolveInfo> resolvedInfoList = packManager
                    .queryIntentActivities(intent,
                            PackageManager.MATCH_DEFAULT_ONLY);

            boolean resolved = false;
            for (ResolveInfo resolveInfo : resolvedInfoList) {
                if (resolveInfo.activityInfo.packageName
                        .startsWith("com.facebook.katana")) {
                    intent.setClassName(resolveInfo.activityInfo.packageName,
                            resolveInfo.activityInfo.name);
                    resolved = true;
                    break;
                }
            }
            if (resolved) {
                context.startActivity(intent);
            } else {
                Intent i = new Intent();
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                        | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                if (text != null) {
                    i.putExtra(Intent.EXTRA_TEXT, text);
                }
                i.setAction(Intent.ACTION_VIEW);
                i.setData(Uri
                        .parse("https://www.facebook.com/sharer/sharer.php?u="));
                context.startActivity(i);
                Toast.makeText(context, "Fb app isn't found", Toast.LENGTH_LONG)
                        .show();
            }
        } catch (Exception e) {
            e.printStackTrace();
//			_logger.error("shareOnTwitter error", e);
        }

    }

    public static String subString(String string) {
        if (string == null || string.length() == 0) {
            return "";
        }
        if (string.contains(upToCharacter)) {
            int indexOfDot = string.indexOf(".");
            if (indexOfDot != -1) {
                return string.substring(0, indexOfDot);
            }
        }
        return "";
    }
}
