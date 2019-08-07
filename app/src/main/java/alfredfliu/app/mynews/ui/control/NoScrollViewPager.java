package alfredfliu.app.mynews.ui.control;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import alfredfliu.app.mynews.util.MyLog;
import lombok.var;

public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            MyLog.D("NoScrollViewPager onTouchEvent move,return false;");
            return false;
        }

        return super.onTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_MOVE) {
            MyLog.D("NoScrollViewPager onInterceptTouchEvent move,return false;");
            return false;
        }
        return super.onInterceptTouchEvent(ev);
    }
}
