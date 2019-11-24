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

    public static MyCategory getMyCategory() {
        MyCategory objtemp = (MyCategory) Cache.getObjectCache(Config.Url);
        return objtemp;
    }

    /*
    *
    *@param runnable return new object[]{ url,string content,title list
    *
     */
    public static void Load_MyCategory( boolean forced,final Gate runnable) {
        final List<String> menuStrList = new ArrayList<>();

        MyCategory objtemp = getMyCategory();
        //1. in Cache
        if (!forced && objtemp != null) {
            menuStrList.clear();
            for (var data : objtemp.getData()) {
                menuStrList.add(data.getTitle());
            }
            runnable.run(new Gate.Item(true,Config.Url,Cache.getNetCache(Config.Url),menuStrList));
            return;
        }

        //2. not in Cache,get from net
        NetHelper.GetCatgories(new Promise<String, String>() {
            @Override
            public String pass(String... args) {
                var url = args[0];
                var arg = args[1];
                var obj = (MyCategory) new Gson().fromJson(arg, MyCategory.class);
                menuStrList.clear();
                for (var data : obj.getData()) {
                    menuStrList.add(data.getTitle());
                }

                Cache.setObjectCache(url, obj);//save in cache
                runnable.run(new Gate.Item(true,url,arg,menuStrList));
                return null;
            }

            @Override
            public String fail(String... args) {
                runnable.run(new Gate.Item(false,Config.Url,"",null));
                return null;
            }
        });
    }

    public static void Load_NewsList(List<MyCategory.DataBean.NewsBean> newsBeans, boolean forced, final Gate runnable) {
    }

    /*
    * Get NewsData
     */
        public static  void Load_News(MyCategory.DataBean.NewsBean newsBean, boolean forced, final Gate runnable) {

        final var url=Config.RootUrl + newsBean.getUrl();
            NewsData objtemp = (NewsData) Cache.getObjectCache(url);
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
                var obj = (NewsData) new Gson().fromJson(content, NewsData.class);

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
