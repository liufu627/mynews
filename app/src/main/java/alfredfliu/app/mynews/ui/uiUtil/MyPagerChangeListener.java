package alfredfliu.app.mynews.ui.uiUtil;

import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import alfredfliu.app.mynews.util.DensityUtil;
import alfredfliu.app.mynews.util.MyLog;

public class MyPagerChangeListener implements ViewPager.OnPageChangeListener{

    private ImageView redpoint;
    private LinearLayout ll_point_group;
    public MyPagerChangeListener( ImageView redpoint,LinearLayout ll_point_group) {
        this.redpoint = redpoint;
        this.ll_point_group = ll_point_group;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        //MyLog.d("onPageScrolled: position:%d, positionOffset:%f,positionOffsetPixels:%d ",position, positionOffset, positionOffsetPixels );
        RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) redpoint.getLayoutParams();

        float leftDistinct= position * 20 + positionOffset* 20;
        params.leftMargin = DensityUtil.dip2px( redpoint.getContext(),Math.round(leftDistinct));
        redpoint.setLayoutParams(params);
        //MyLog.d("redpoint leftDistinct:%f,leftMargin :%d", leftDistinct,params.leftMargin );
    }

    @Override
    public void onPageSelected(int position) {
       RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) redpoint.getLayoutParams();
       params.leftMargin = DensityUtil.dip2px( redpoint.getContext(),20*position);
        redpoint.setLayoutParams(params);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
