package alfredfliu.app.mynews.ui.control;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import alfredfliu.app.mynews.util.MyLog;

public class TopImageViewPager extends ViewPager {

    public TopImageViewPager(Context context) {
        super(context);
    }

    public TopImageViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    float downX=0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                downX= ev.getX();
                MyLog.D("DownX:",downX);
                break;
            case MotionEvent.ACTION_MOVE:
                float distance= ev.getX() -downX ;
                MyLog.D("DownX:",downX,"distance:",distance);
                if(getCurrentItem() == 0 && distance>0){//第一个元素，从左向右滑动，（不阻止父类截获事件，让父类处理该事件）
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else if((getCurrentItem() == (getAdapter().getCount()-1)) && distance<0) {//最后一个元素，从右向左滑动，（不阻止父类截获事件，不能中断事件传输，让其可以跳转下一个事件子类型）
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                else{//其它情况，（阻止父类截获事件，让本类处理该事件）
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_UP:break;
            default:break;
        }
        return super.dispatchTouchEvent(ev);
    }
}
