package shop.portonics.com.shop.model;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by imran on 11/22/2016.
 */

public class PrefManager {

    SharedPreferences pref;
    SharedPreferences.Editor editor;

    Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "Shop-Welcome";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PrefManager(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void setIsFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirsTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }
}
