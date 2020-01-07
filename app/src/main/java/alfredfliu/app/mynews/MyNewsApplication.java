package alfredfliu.app.mynews;

import android.app.Application;

import com.android.volley.Cache;
import com.android.volley.toolbox.Volley;

import org.xutils.x;

import alfredfliu.app.mynews.net.VolleyManager;

public class MyNewsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        VolleyManager.init(this);

        x.Ext.setDebug(true);
        x.Ext.init(this);
        alfredfliu.app.mynews.util.Cache.setApplicationContext(this);
    }
}
