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

    @Getter
    @Setter
    public static MainActivity MainActivity;

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


    public static Map<String,Object> ObjectGlobal=new HashMap<>(100);
    public static Map<String,String> StringGlobal=new HashMap<>(100);

    public static void putString(Context context, String url, String result) {

        StringGlobal.put(url,result);
    }

    public static void put(String key,Object value){
        ObjectGlobal.put(key,value);
    }
}
