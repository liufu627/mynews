package alfredfliu.app.mynews.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {
    public static final String TAG = "mynews";

    public static final String PACKAGE_FLASH = "ALFRED_NEWS";
    public static final String FLAG_FLASH = "FLAG_FLASH";
    public static final String FLAG_CLICKEDENTERBUTTON = "FLAG_CLICKEDENTERBUTTON ";


    public static boolean hadFlash(Context context, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences(PACKAGE_FLASH, Context.MODE_PRIVATE);
        if (value == null) {
            boolean result = sp.getBoolean(FLAG_FLASH, false);
            MyLog.Df("Config.hadFlash: %s", result);
            return result;
        }
        MyLog.Df("Config.hadFlash: %s", value);

        sp.edit().putBoolean(FLAG_FLASH, value).commit();

        return value;
    }

    public static boolean clickedEnterButton(Context context, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences(PACKAGE_FLASH, Context.MODE_PRIVATE);
        if (value == null) {
            boolean result = sp.getBoolean(FLAG_CLICKEDENTERBUTTON, false);
            MyLog.Df("Config.hadFlash: %s", result);
            return result;
        }
        MyLog.Df("Config.clickedEnterButton: %s", value);

        sp.edit().putBoolean(FLAG_CLICKEDENTERBUTTON, value).commit();

        return value;
    }


    public static String HostUrl = "http://192.168.1.107:8002";
    public static String WebAppName = "web_home";
    public static String RootUrl = HostUrl +"/" + WebAppName;
    public static String Url = RootUrl + "/static/api/news/categories.json";

    public static void GetUrlFromFile() {
    }

}
