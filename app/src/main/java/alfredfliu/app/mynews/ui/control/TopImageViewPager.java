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
    float downY=0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                downX= ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float distanceX= ev.getX() -downX ;
                float distanceY= ev.getY() -downY ;
                MyLog.D("TopImageViewPager dispatchTouchEvent :distanceX,",distanceX,";distanceY",distanceY);
                if(Math.abs(distanceY) > Math.abs(distanceX)){
                    getParent().requestDisallowInterceptTouchEvent(false);
                    break;
                }

                if(getCurrentItem() == 0 && distanceX>0){//第一个元素，从左向右滑动，（不阻止父类截获事件，让父类处理该事件）
                    getParent().requestDisallowInterceptTouchEvent(false);
                }else if((getCurrentItem() == (getAdapter().getCount()-1)) && distanceX<0) {//最后一个元素，从右向左滑动，（不阻止父类截获事件，不能中断事件传输，让其可以跳转下一个事件子类型）
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
