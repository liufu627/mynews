package alfredfliu.app.mynews;

import android.app.Application;

import alfredfliu.app.mynews.net.VolleyManager;

public class MyNewsApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        VolleyManager.init(this);
    }
}
