package alfredfliu.app.mynews.ui;

import android.app.Activity;
import android.media.Image;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.ui.uiUtil.MyPagerAdapter;
import alfredfliu.app.mynews.ui.uiUtil.MyPagerChangeListener;
import alfredfliu.app.mynews.util.DensityUtil;
import alfredfliu.app.mynews.util.MyLog;

public class GuideActivity extends Activity {

    private ViewPager viewPager;
    private  ImageView redpoint;
    private LinearLayout ll_point_group;
    private ArrayList<ImageView> imageViewList;
    private ArrayList<Integer> idList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        //get member vars
        ll_point_group = (LinearLayout) findViewById(R.id.ll_point_group);
        viewPager = (ViewPager) findViewById(R.id.vp_guide);
        redpoint =(ImageView)findViewById(R.id.iv_red_point);

        //init and config for viewpage
        init_imageViewList();
        viewPager.setAdapter(new MyPagerAdapter(imageViewList));
        viewPager.addOnPageChangeListener(new MyPagerChangeListener(redpoint,ll_point_group));

        Integer dip = DensityUtil.dip2px(this,10);
        //config for points to indicate how many images can show
        for (Integer i : idList){
            ImageView pointView=new ImageView( this);
            pointView.setBackgroundResource(R.drawable.normalpoint);
            LinearLayout.LayoutParams layoutParam = new LinearLayout.LayoutParams(dip,dip);
            if( ll_point_group.getChildCount() !=0) {
                layoutParam.leftMargin = dip;
            }
            pointView.setLayoutParams(layoutParam);
            ll_point_group.addView(pointView);
        }

        redpoint.getViewTreeObserver().addOnGlobalLayoutListener( new MyGlobalLayoutListener() );
    }

    private void init_imageViewList() {
        idList = new ArrayList<>();
        idList.add(R.drawable.guide_1);
        idList.add(R.drawable.guide_2);
        idList.add(R.drawable.guide_3);
        imageViewList = new ArrayList<>();
        for (int i : idList){
            ImageView imageView=new ImageView(this);
            imageView.setBackgroundResource(i);
            imageViewList.add(imageView);
        }
    }

    private class MyGlobalLayoutListener implements ViewTreeObserver.OnGlobalLayoutListener {
        @Override
        public void onGlobalLayout() {
            //执行不只一次
            redpoint.getViewTreeObserver().removeGlobalOnLayoutListener(MyGlobalLayoutListener.this);

//            间距  = 第1个点距离左边的距离 - 第0个点距离左边的距离
           Integer leftmax = ll_point_group.getChildAt(1).getLeft() - ll_point_group.getChildAt(0).getLeft();
            MyLog.d( "leftmax==" + leftmax );
        }
    }
}
