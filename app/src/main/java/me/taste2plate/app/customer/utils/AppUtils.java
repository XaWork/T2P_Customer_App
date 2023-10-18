package me.taste2plate.app.customer.utils;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.StringRes;
import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import me.taste2plate.app.customer.R;
import me.taste2plate.app.customer.ui.onboarding.OnBoardActivity;
import me.taste2plate.app.data.api.RegistrationData;
import me.taste2plate.app.models.AppDataResponse;
import me.taste2plate.app.models.Customer;
import me.taste2plate.app.models.address.Address;

/*
 *
 */
public class AppUtils {

    Context context;

    String token;
    String expiry;

    public static final String MY_PREFS_NAME = "StarterApp";

    public AppUtils(Context context) {
        this.context = context;
    }

    public static void alertLogout(Context context) {
        AlertDialog alert = new AlertDialog.Builder(context)
                .setTitle(context.getString(R.string.app_name))
                .setMessage(R.string.account_disable_msg)
                .setCancelable(false)
                .setPositiveButton(context.getString(R.string.alert_ok), (dialog, which) -> context.startActivity((new Intent(context, OnBoardActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)))).create();

        alert.show();

        Button buttonbackground = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground.setTextColor(Color.BLACK);

    }

    public static void showToast(Context context, @StringRes int text, boolean isLong) {
        showToast(context, context.getString(text), isLong);
    }

    public static void showToast(Context context, String text, boolean isLong) {
        Toast.makeText(context, text, isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT).show();
    }


    public void saveNewUser(Customer customer) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString("user", new Gson().toJson(customer, Customer.class));
        editor.apply();
    }

    public void saveUser(RegistrationData registrationData) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString("user_data", new Gson().toJson(registrationData, RegistrationData.class));
        Log.e("save user", "saveUser: " + registrationData);
        editor.apply();
    }


    public void saveDefaultAddress(Address address) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString("address", new Gson().toJson(address, Address.class));
        editor.apply();
    }

    public Address getDefaultAddress() {
        String appDataStr = getString("address", "");
        if (!TextUtils.isEmpty(appDataStr)) {
            return new Gson().fromJson(appDataStr, Address.class);
        }
        return null;
    }

    public void saveAppData(AppDataResponse appData) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString("appData", new Gson().toJson(appData, AppDataResponse.class));
        editor.apply();
    }

    public AppDataResponse getAppData() {
        String appDataStr = getString("appData", "");
        if (!TextUtils.isEmpty(appDataStr)) {
            return new Gson().fromJson(appDataStr, AppDataResponse.class);
        }
        return null;
    }

    public void putString(String key, String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString(key, value);
        editor.apply();
    }

    public String getString(String key, String value) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        return prefs.getString(key, value);
    }

    public void setTaste(String value) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString("taste", value);
        editor.apply();
        getTaste();
    }

    public String getTaste() {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        return prefs.getString("taste", "1");
    }

    public RegistrationData getUser() {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        String userGson = prefs.getString("user_data", "");
        if (TextUtils.isEmpty(userGson)) {
            return null;
        }
        return new Gson().fromJson(userGson, RegistrationData.class);
    }

    public String getCity() {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        String userGson = prefs.getString("currentCity", "");
        if (TextUtils.isEmpty(userGson)) {
            return "";
        }
        return userGson;
    }

    public Boolean addCityId(String cityid) {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        String userGson = prefs.getString("currentCity", "");

        if (TextUtils.isEmpty(userGson)) {
            userGson = cityid;
            editor.putString("currentCity", userGson);
            editor.apply();
            return true;
        } else return userGson.equalsIgnoreCase(cityid);
    }

    public void removeCity(String cityId) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString("currentCity", "");
        editor.apply();

    }

    public void clearCityData() {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.remove("currentCity");
        editor.apply();
    }

    public void setInstallFromPlayStore(Boolean isInstall) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putBoolean("is_install_from_playstore", isInstall);
        Log.e("apputils", "Ip Address is saved successfully" + isInstall);
        editor.apply();
        isInstallFromPlayStore();
    }

    public Boolean isInstallFromPlayStore() {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        Boolean isInstall = prefs.getBoolean("is_install_from_playstore", false);
        Log.e("apputils", "Is intall from play store" + isInstall);
        return isInstall;
    }

    public void saveIpAddress(String ip) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString("ip", ip);
        Log.e("apputils", "Ip Address is saved successfully" + ip);
        editor.apply();
        getIpAddress();
    }

    public String getIpAddress() {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        Log.e("apputils", "Ip Address is " + prefs.getString("ip", ""));
        return prefs.getString("ip", "");
    }
    public void saveAffiliate(String ip) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString("aff", ip);
        Log.e("apputils", "Ip Address is saved successfully" + ip);
        editor.apply();
        getAffiliate();
    }

    public String getAffiliate() {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        return prefs.getString("aff", "");
    }

    public void saveReferralInfo(String id, String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString("referral_id", id);
        editor.putString("referral_token", token);
        editor.apply();
        getReferralInfo();
    }

    public List<String> getReferralInfo() {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        List<String> infoList = new ArrayList<>();
        infoList.add(prefs.getString("referral_id", "64abd456600481c67e58853a"));
        infoList.add(prefs.getString("referral_token", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjoiNjRhYmQzNDRmMmIzYWRmMjUxNWUxMDU0IiwicHJvamVjdCI6IjY0YWJkMzZkZjJiM2FkZjI1MTVlMTA1OSIsImlhdCI6MTY4ODk4MjYxNH0.-4KoXpnK-6Kx4SmaN3yZTSKF0V1q-0695XaF69K3QQM"));

        Log.e("apputils", "Referral user info is " + infoList);
        return infoList;
    }



    public void saveToken(String token) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString("token", token);
        editor.apply();
    }

    public String getToken() {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        Log.e("apputils", prefs.getString("token", ""));
        return prefs.getString("token", "");
    }

    public void saveCartSession(String sessionId, String expiry) {
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.putString("cartSession", sessionId);
        editor.putString("expiry", expiry);
        editor.putBoolean("hasSession", true);
        editor.apply();
    }

    public String getCartSession() {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        return prefs.getString("cartSession", null);
    }

    public String getExpiry() {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        return prefs.getString("expiry", null);
    }

    public boolean isLoggedIn() {
        SharedPreferences prefs = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE);
        return prefs.getBoolean("loggedIn", false);
    }

    public void logOut() {
        String token = getToken();
        SharedPreferences.Editor editor = context.getSharedPreferences(MY_PREFS_NAME, context.MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        saveToken(token);
    }

    public static Date getCurrentDateTime() {
        Date currentDate = Calendar.getInstance().getTime();
        return currentDate;
    }

    public static String getFormattedDateString(Date date) {

        try {

            SimpleDateFormat spf = new SimpleDateFormat("EEE MMM d HH:mm:ss zzz yyyy");
            String dateString = spf.format(date);

            Date newDate = spf.parse(dateString);
            spf = new SimpleDateFormat("dd MMM yyyy HH:mm:ss");
            return spf.format(newDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String generateHash(String password) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-512");
            md.update(password.getBytes());
            byte byteData[] = md.digest();
            String base64 = Base64.encodeToString(byteData, Base64.NO_WRAP);
            return base64;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void showMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public static void openKeyboard(final Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
                }
            }
        }, 500);
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
    }


    public static HashMap<String, String> getData() {
        String data = "{\n" +
                "   \"Andhra Pradesh\":\"AP\",\n" +
                "   \"Arunachal Pradesh\":\"AR\",\n" +
                "   \"Assam\":\"AS\",\n" +
                "   \"Bihar\":\"BR\",\n" +
                "   \"Chhattisgarh\":\"CG\",\n" +
                "   \"Chandigarh\":\"Chandigarh\",\n" +
                "   \"Dadra and Nagar Haveli\":\"DN\",\n" +
                "   \"Daman and Diu\":\"DD\",\n" +
                "   \"Delhi\":\"DL\",\n" +
                "   \"Goa\":\"GA\",\n" +
                "   \"Gujarat\":\"GJ\",\n" +
                "   \"Haryana\":\"HR\",\n" +
                "   \"Himachal Pradesh\":\"HP\",\n" +
                "   \"Jammu and Kashmir\":\"JK\",\n" +
                "   \"Jharkhand\":\"JH\",\n" +
                "   \"Karnataka\":\"KA\",\n" +
                "   \"Kerala\":\"KL\",\n" +
                "   \"Madhya Pradesh\":\"MP\",\n" +
                "   \"Maharashtra\":\"MH\",\n" +
                "   \"Manipur\":\"MN\",\n" +
                "   \"Meghalaya\":\"ML\",\n" +
                "   \"Mizoram\":\"MZ\",\n" +
                "   \"Nagaland\":\"NL\",\n" +
                "   \"Orissa\":\"OR\",\n" +
                "   \"Punjab\":\"PB\",\n" +
                "   \"Pondicherry\":\"PY\",\n" +
                "   \"Rajasthan\":\"RJ\",\n" +
                "   \"Sikkim\":\"SK\",\n" +
                "   \"Tamil Nadu\":\"TN\",\n" +
                "   \"Tripura\":\"TR\",\n" +
                "   \"Uttar Pradesh\":\"UP\",\n" +
                "   \"Uttarakhand\":\"UK\",\n" +
                "   \"West Bengal\":\"WB\"\n" +
                "}";

        return new Gson().fromJson(
                data, new TypeToken<HashMap<String, String>>() {
                }.getType()
        );
    }


}