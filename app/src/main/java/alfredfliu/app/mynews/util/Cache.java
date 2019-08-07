package alfredfliu.app.mynews.util;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import alfredfliu.app.mynews.ui.MainActivity;
import lombok.Getter;
import lombok.Setter;

public class Cache {

    @Getter
    @Setter
    public static Context Context;

    @Getter
    @Setter
    public static MainActivity MainActivity;

    public static Map<String,Object> ObjectGlobal=new HashMap<>(100);
    public static Map<String,String> StringGlobal=new HashMap<>(100);

    public static void putString(Context context, String url, String result) {

        StringGlobal.put(url,result);
    }

    public static void put(String key,Object value){
        ObjectGlobal.put(key,value);
    }
}
