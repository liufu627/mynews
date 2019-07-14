package alfredfliu.app.mynews.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {
    public  static final String TAG = "mynews";

    public static final String PACKAGE_FLASH = "ALFRED_NEWS";
    public static final String FLAG_FLASH = "FLAG_FLASH";

    public static boolean hadFlash(Context context, Boolean value) {
        SharedPreferences sp = context.getSharedPreferences(PACKAGE_FLASH, Context.MODE_PRIVATE);
        if (value == null) {
            return  sp.getBoolean(FLAG_FLASH,false);
        }
        sp.edit().putBoolean(FLAG_FLASH,value);

        return value;
    }
}
