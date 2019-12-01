package alfredfliu.app.mynews.util;

import android.content.Context;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import alfredfliu.app.mynews.base.BasePage;
import alfredfliu.app.mynews.ui.MainActivity;
import alfredfliu.app.mynews.ui.NavTab.NewsPage;
import alfredfliu.app.mynews.ui.NewsType.DefaultNewsType;
import lombok.Getter;
import lombok.Setter;

public class Cache {

    @Getter
    @Setter
    public static Context Context;

    static MainActivity MainActivity;

//    @Setter
//    @Getter
//    static MainContentFragment MainContentFragment;

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


//    @Getter
//    @Setter
//    public static BasePage CurrentPage;

    @Getter
    @Setter
    public static DefaultNewsType DefaultNewsType;


    private static Map<String,Object> ObjectGlobal=new HashMap<>(100);
    private static Map<String,String> StringGlobal=new HashMap<>(100);
    private static LinkedHashMap<Date,String> readTimeQueue=new LinkedHashMap<Date,String>();

    public static void setNetCache(String url, String result) {
        if(result ==null || result.length() ==0)
            return;
        StringGlobal.put(url,result);
        Config.PutUrlContent(Cache.getContext(),url,result);
    }
    public static String getNetCache(String url) {
        String value =StringGlobal.get(url);
        if(value==null || value.length()==0){
            value = Config.GetUrlContent(Cache.getContext(),url);
            if(value!=null&&value.length()>0)
                StringGlobal.put(url,value);
        }
        return value;
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
