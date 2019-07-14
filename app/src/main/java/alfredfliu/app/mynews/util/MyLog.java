package alfredfliu.app.mynews.util;

import android.util.Log;


import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MyLog {

    private static String TAG = Config.TAG;

    public static void d(String s, Object... args) {
        try {
            if (s.indexOf("%") >= 0)
                android.util.Log.d(TAG, String.format(s, args));
            else {
                List<String> list = new ArrayList<>();
                for (Object obj : args) {
                    list.add(obj.toString());
                }
                String result = StringUtils.join(list.toArray(), ",");
                android.util.Log.d(TAG, result);
            }
        }catch(Exception e) {

        }

    }
}
