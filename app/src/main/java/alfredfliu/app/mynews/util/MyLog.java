package alfredfliu.app.mynews.util;

import android.util.Log;


import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyLog {

    private static String TAG = Config.TAG;

    public static void Df(String format,Object... args) {
        android.util.Log.d(TAG, String.format(format, args));
    }
    public static void D(Object... args) {
        List<String> list = new ArrayList<>();
        for (Object obj : args) {
            list.add(obj.toString());
        }
        String result = StringUtils.join(list.toArray(), ",");
        android.util.Log.d(TAG, result);
    }
}
