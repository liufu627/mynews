package alfredfliu.app.mynews.util;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

import alfredfliu.app.mynews.base.BasePage;
import alfredfliu.app.mynews.ui.LeftMenuFragment;
import alfredfliu.app.mynews.ui.MainActivity;
import alfredfliu.app.mynews.ui.NewsType.DefaultNewsType;
import alfredfliu.app.mynews.ui.Pages.NewsPage;
import lombok.Getter;
import lombok.Setter;

public class Cache {

    @Getter
    @Setter
    public static Context Context;

    static MainActivity MainActivity;


    public static MainActivity getMainActivity() {
    return  MainActivity;
    }
    public static void setMainActivity(MainActivity value) {
        MainActivity = value;
    }


//    @Getter
//    @Setter
//    public static DefaultNewsType DefaultNewsType;

    @Getter
    @Setter
    public static NewsPage NewsPage;


    @Getter
    @Setter
    public static BasePage CurrentPage;

    @Getter
    @Setter
    public static BasePage CurrentNewsType;


    private static Map<String,Object> ObjectGlobal=new HashMap<>(100);
    private static Map<String,String> StringGlobal=new HashMap<>(100);

    public static void setNetCache(String url, String result) {
        StringGlobal.put(url,result);
    }
    public static String getNetCache(String url) {
        return StringGlobal.get(url);
    }

//    @Getter
//    @Setter
//    public  static  Boolean ImageScrolling;

    public static void setObjectCache(String key, Object value){
        ObjectGlobal.put(key,value);
    }
    public static Object getObjectCache(String key){
        return ObjectGlobal.get(key);
    }
}
