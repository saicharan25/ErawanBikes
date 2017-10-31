package erawanbikes.com.sample.Login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import erawanbikes.com.sample.R;

public class Helper {
    public static String errorMsg = "Something went wrong ! Try Again !";
    private static String[] PERMISSIONS_STORAGE = new String[]{"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private static final Pattern VALID_PH_NUMBER = Pattern.compile("^[789]\\d{9}$");
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$");

    //  public static final String YOUTUBE_API_KEY = "AIzaSyDd8TD6BQXsU-Jxr1eHMLdLMzKk_PUWCjI";
    public static Intent s1;
    public static Intent s2;


    public static void openURL(Activity c, String url) {
        try {
            if (!url.startsWith("https")) {
                url = "https://" + url;
            }
            Toast.makeText(c, "Opening " + url, Toast.LENGTH_LONG).show();
            Intent i = new Intent("android.intent.action.VIEW");
            i.setData(Uri.parse(url));
            c.startActivity(i);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   /* public static void setToolbarHeader(Context context, View view, String profpicstr, String coverpicstr, String fullnamestr, String friendCount) {
        try {
            ImageView cover = (ImageView) view.findViewById(C0716R.id.cover);
            CircleImageView profile = (CircleImageView) view.findViewById(C0716R.id.profile);
            TextView titlename = (TextView) view.findViewById(C0716R.id.title);
            TextView followers = (TextView) view.findViewById(C0716R.id.followers);
            if (!(coverpicstr == null || coverpicstr.trim().length() == 0)) {
                Glide.with(context).load(getImageUrl(coverpicstr, "coverpic")).into(cover);
            }
            if (!(profpicstr == null || profpicstr.trim().length() == 0)) {
                Glide.with(context).load(getImageUrl(profpicstr, "profilepic")).into(profile);
            }
            if (!(fullnamestr == null || fullnamestr.trim().length() == 0)) {
                titlename.setText(fullnamestr);
            }
            if (friendCount != null) {
                followers.setText(friendCount + " Followers");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/


    public static int dpToPx(Context c, int sizeInDp) {
        return (int) ((((float) sizeInDp) * c.getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static boolean validate(String input) {
        if (input == null || input.trim().length() == 0) {
            return false;
        }
        input = input.trim();
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(input);
        Matcher matcher2 = VALID_PH_NUMBER.matcher(input);
        if (matcher.find() || matcher2.find()) {
            return true;
        }
        return false;
    }

    public static String getImageUrl(String input, String tag) {
        return "https://cdn.stumagz.com/images/" + input + tag;
    }

    public static boolean store_locally(String key, String value, Context context) {
        if (key == null || value == null) {
            return false;
        }
        Editor editor = context.getSharedPreferences("stumagz", 0).edit();
        editor.putString(key, value);
        editor.apply();
        return true;
    }

    public static String getString(List al) {
        Set<String> hs = new HashSet();
        hs.addAll(al);
        al.clear();
        al.addAll(hs);
        return al.toString().replaceAll("[\\[.\\].\\s+]", "");
    }

    public static List getList(String str) {
        try {
            return Arrays.asList(str.split("\\s*,\\s*"));
        } catch (Exception e) {
            return new ArrayList();
        }
    }

    public static File storeImage(Context c, Bitmap image) {
        String TAG = "uprof";
        File pictureFile = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/img.jpeg");
        if (pictureFile == null) {
            Log.d(TAG, "Error creating media file, check storage permissions: ");
        } else {
            try {
                FileOutputStream fos = new FileOutputStream(pictureFile);
                image.compress(CompressFormat.PNG, 90, fos);
                fos.close();
            } catch (FileNotFoundException e) {
                Log.d(TAG, "File not found: " + e.getMessage());
            } catch (IOException e2) {
                Log.d(TAG, "Error accessing file: " + e2.getMessage());
            }
        }
        return pictureFile;
    }

    public static boolean store_locally(HashMap<String, String> map, Context context) {
        if (map == null) {
            return false;
        }
        Editor editor = context.getSharedPreferences("stumagz", 0).edit();
        for (Entry<String, String> s : map.entrySet()) {
            editor.putString((String) s.getKey(), (String) s.getValue());
        }
        editor.apply();
        return true;
    }


    public static boolean checkNow(Context con){

        try{
            ConnectivityManager connectivityManager = (ConnectivityManager) con.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            NetworkInfo mobileInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if(wifiInfo.isConnected() || mobileInfo.isConnected())
            {
                return true;
            }
        }
        catch(Exception e){
            System.out.println("CheckConnectivity Exception: " + e.getMessage());

        }

        return false;
    }

    public static boolean validate_mobile(String input) {
        if (input == null || input.trim().length() == 0) {
            return false;
        }
        return VALID_PH_NUMBER.matcher(input.trim()).find();
    }

    public static boolean validate_email(String input) {
        if (input == null || input.trim().length() == 0) {
            return false;
        }
        return VALID_EMAIL_ADDRESS_REGEX.matcher(input.trim()).find();
    }
    public static boolean validate_password(String input) {
        if (input == null || input.trim().length() == 0) {
            return false;
        }
        return PASSWORD_PATTERN.matcher(input.trim()).find();
    }
    public static boolean isValidField(String msg, View v, Activity act) {
        EditText et = (EditText) v;
        if (!et.getText().toString().isEmpty() && !et.getText().toString().equalsIgnoreCase("null")) {
            return true;
        }
        et.setError(msg);
        return false;
    }


    public static void showSnackBar(View v, String messgage) {
        try {
            Snackbar.make(v, (CharSequence) messgage, 0).setAction((CharSequence) "Done", null).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public static void verifyStoragePermissions(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, 1);
        }
    }


    public static void storeLocally(String key, String value, Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key, value).apply();

    }

    public static String getLocalValue(Context context, String key) {
        return context.getSharedPreferences(String.valueOf(R.string.app_name), Context.MODE_PRIVATE).getString(key, "");

    }
    public static boolean isUserLoggedIn(Context c) {

        Log.d("Tag","Helpertoken: "+ Helper.getLocalValue(c, "token").toString().trim().length());
        return Helper.getLocalValue(c, "token").toString().trim().length() != 0;

    }

    public static String getAuthenticationHeader(Context context) {

        String token = (String) getLocalValue(context, "token");
        return "Bearer " + token;



    }

}
