package alfredfliu.app.mynews.ui.control;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import alfredfliu.app.mynews.ui.NewsType.DefaultNewsType;
import alfredfliu.app.mynews.ui.Pages.NewsPage;
import alfredfliu.app.mynews.util.Cache;
import alfredfliu.app.mynews.util.MyLog;
import lombok.var;

public class NoScrollViewPager extends ViewPager {


    private float downX;
    private float downY;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(Cache.getCurrentPage() == null  || Cache.getCurrentNewsType() == null)
            return super.onTouchEvent(ev);
//        if(! (Cache.getCurrentPage() instanceof NewsPage ))
//            return super.onTouchEvent(ev);
//        if(! (Cache.getCurrentNewsType() instanceof DefaultNewsType ))
//            return super.onTouchEvent(ev);

         NewsPage newsPage=null;
        if( Cache.getCurrentPage() instanceof  NewsPage )
            newsPage = (NewsPage)Cache.getCurrentPage();

        DefaultNewsType defaultNewsType = null;
        if( Cache.getCurrentNewsType() instanceof  DefaultNewsType )
            defaultNewsType = (DefaultNewsType)Cache.getCurrentNewsType();
        var x=ev.getX();
        var y=ev.getY();

        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            MyLog.D("NoScrollViewPager onTouchEvent move,return false;");
            if ( newsPage!=null && defaultNewsType!=null &&  defaultNewsType.isFirstPage() && (x - downX) > 0 && !Cache.getMainActivity().getSlidingMenu().isMenuShowing())
                Cache.getMainActivity().ToggleMenu(false);
            return false;
        }else if(ev.getAction() == MotionEvent.ACTION_DOWN){
            if(newsPage!=null &&  defaultNewsType!=null && defaultNewsType.isFirstPage() &&Cache.getMainActivity().getSlidingMenu().isMenuShowing())
                Cache.getMainActivity().ToggleMenu(false);
            downX=x;
            downY =y;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if(Cache.getCurrentPage() == null  || Cache.getCurrentNewsType() == null)
            return super.onInterceptTouchEvent(ev);
//        if(! (Cache.getCurrentPage() instanceof NewsPage ))
//            return super.onInterceptTouchEvent(ev);
//        if(! (Cache.getCurrentNewsType() instanceof DefaultNewsType ))
//            return super.onInterceptTouchEvent(ev);
        NewsPage newsPage=null;
        if( Cache.getCurrentPage() instanceof  NewsPage )
            newsPage = (NewsPage)Cache.getCurrentPage();

        DefaultNewsType defaultNewsType = null;
        if( Cache.getCurrentNewsType() instanceof  DefaultNewsType )
            defaultNewsType = (DefaultNewsType)Cache.getCurrentNewsType();
        var x=ev.getX();
        var y=ev.getY();
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            MyLog.D("NoScrollViewPager onInterceptTouchEvent move,return false;");
                if (newsPage!=null &&  defaultNewsType!= null && defaultNewsType.isFirstPage() && (x - downX) > 0 && !Cache.getMainActivity().getSlidingMenu().isMenuShowing())
                    Cache.getMainActivity().ToggleMenu(false);
                return false;

        }else if(ev.getAction() == MotionEvent.ACTION_DOWN){
            if(newsPage!=null && defaultNewsType!= null &&  defaultNewsType.isFirstPage() &&Cache.getMainActivity().getSlidingMenu().isMenuShowing())
                Cache.getMainActivity().ToggleMenu(false);
            downX=x;
            downY =y;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
