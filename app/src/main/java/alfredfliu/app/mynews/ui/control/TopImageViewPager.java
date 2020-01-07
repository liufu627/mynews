package alfredfliu.app.mynews.ui.control;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.os.Message;
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
    int cycleStep=0;
    final MyHandler handler=new MyHandler();
    public class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int index = (TopImageViewPager.this.getCurrentItem() + 1) % TopImageViewPager.this.getAdapter().getCount();
            TopImageViewPager.this.setCurrentItem(index, true);
            MyLog.D("handleMessage",cycleStep);

            handler.postDelayed(new MyRunnable(), cycleStep);
        }
    }
    public class MyRunnable implements Runnable{
        @Override
        public void run() {
            MyLog.D("sendEmptyMessage",cycleStep);
            handler.sendEmptyMessage(0);
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                downX= ev.getX();
                downY = ev.getY();
                handler.removeCallbacksAndMessages(null);
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
            case MotionEvent.ACTION_UP: {
                handler.postDelayed(new MyRunnable(),cycleStep);
                break;
            }
            default:break;
        }
        return super.dispatchTouchEvent(ev);
    }

    public void startCyclePic(int cycleStep) {
        if( cycleStep<500 || ( this.getAdapter()!=null && this.getAdapter().getCount() <2 ))
            return;
        this.cycleStep  =cycleStep;

        handler.removeCallbacksAndMessages(null);
        handler.postDelayed(new MyRunnable(),cycleStep);
    }
}
