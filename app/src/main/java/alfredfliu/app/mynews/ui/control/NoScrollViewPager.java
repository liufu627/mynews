package alfredfliu.app.mynews.ui.control;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {
    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        var viewCount = getAdapter().getCount();
//        //if(viewCount==5 && )
//        MyLog.D(" onInterceptTouchEvent getAdapter().getCount()", getAdapter().getCount());
        return false;
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//         getParent().requestDisallowInterceptTouchEvent(true);
//        return super.dispatchTouchEvent(ev);
//    }
}
