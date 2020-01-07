package alfredfliu.app.mynews.net;



import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import alfredfliu.app.mynews.base.Gate;
import alfredfliu.app.mynews.base.Promise;
import alfredfliu.app.mynews.data.MyCategory;
import alfredfliu.app.mynews.data.NewsData;
import alfredfliu.app.mynews.util.Cache;
import alfredfliu.app.mynews.util.Config;
import lombok.Getter;
import lombok.Setter;
import lombok.var;

public class DataCenter {

    @Getter
    @Setter
    Object CurrentItem;



    /*
    *
    *@param runnable return new object[]{ url,string content,title list
    *
     */
    public static void Load_MyCategory( boolean forced,final Gate runnable) {
        final var url = Config.Url;

        LoadData(url, forced, runnable, MyCategory.class);
    }

    /*
    * Get NewsData
     */
        public static  void Load_News(MyCategory.DataBean.NewsBean newsBean, boolean forced, final Gate runnable) {

            final var url = Config.RootUrl + newsBean.getUrl();

            LoadData(url, forced, runnable, NewsData.class);

        }

    public static void LoadMore(alfredfliu.app.mynews.data.NewsData newsData,boolean forced,final Gate  runnable) {

        final var url=Config.RootUrl + newsData.getData().getMore();

        LoadData(url,forced,runnable,newsData.getClass());
    }

    public  static void  LoadData(final String url, boolean forced, final Gate runnable, final Class<?> classInfo){
        Object objtemp = Cache.getObjectCache(url);
        //1. in Cache
        if (!forced && objtemp != null) {
            runnable.run(new Gate.Item(true,url,Cache.getNetCache(url),objtemp));
            return;
        }

        //2. not in Cache,get from net
        NetHelper.GetRemoteData(url,new Promise<String, String>() {
            @Override
            public String pass(String... args) {
                var url = args[0];
                var content = args[1];
                var obj = new Gson().fromJson(content, classInfo);

                Cache.setObjectCache(url, obj);//save in cache
                runnable.run(new Gate.Item(true,url,content,obj));
                return null;
            }

            @Override
            public String fail(String... args) {
                runnable.run(new Gate.Item(false,url,"",null));
                return null;
            }
        });
    }
}
