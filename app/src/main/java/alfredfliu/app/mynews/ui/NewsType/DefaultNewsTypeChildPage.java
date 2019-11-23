package alfredfliu.app.mynews.ui.NewsType;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.base.BasePage;
import alfredfliu.app.mynews.base.Gate;
import alfredfliu.app.mynews.data.MyCategory;
import alfredfliu.app.mynews.data.NewsData;
import alfredfliu.app.mynews.net.DataCenter;
import alfredfliu.app.mynews.ui.Adaper.MyPagerAdapter;
import alfredfliu.app.mynews.ui.Adaper.NewsListAdapter;
import alfredfliu.app.mynews.ui.control.RefreshListView;
import alfredfliu.app.mynews.ui.control.TopImageViewPager;
import alfredfliu.app.mynews.util.Cache;
import alfredfliu.app.mynews.util.Config;
import alfredfliu.app.mynews.util.DensityUtil;
import alfredfliu.app.mynews.util.MyLog;
import lombok.Getter;
import lombok.var;

public class DefaultNewsTypeChildPage extends BasePage {
    public static final int ResID =R.layout.page_news_defaulttype_content;
    private NewsListAdapter newsListAdapter;

    MyPagerAdapter myPagerAdapter;

    //sub default news pic
    TopImageViewPager vp_Gallery;
    List<ImageView> listView;
    TextView tv_TopImageText;
    private LinearLayout ll_points;
    private ImageView iv_red_point;

    RefreshListView lv_newsItems;

    @Getter
    MyCategory.DataBean.NewsBean Bean;
    private alfredfliu.app.mynews.data.NewsData newsData;
    private View headerView;


    public DefaultNewsTypeChildPage(Context context, int resID, ViewGroup parentView) {
        super(context, resID, parentView);

        listView = new ArrayList<>();
        myPagerAdapter = new MyPagerAdapter(listView);
         newsListAdapter = null;
    }

    @Override
    public void InitViewObject() {
          headerView = View.inflate(context,R.layout.header_news_defaulttype,null);
        vp_Gallery = (TopImageViewPager) headerView.findViewById(R.id.gallery);
        tv_TopImageText = (TextView)headerView.findViewById(R.id.tv_TopImageText);
         ll_points =(LinearLayout)headerView.findViewById(R.id.ll_points);
         iv_red_point =(ImageView)headerView.findViewById(R.id.iv_red_point);

        lv_newsItems = (RefreshListView)view.findViewById(R.id.lv_newsItems);
        lv_newsItems.addHeaderView(headerView);

        //lv_newsItems.setAdapter(newsListAdapter);
        vp_Gallery.setAdapter(myPagerAdapter);
//        vp_Gallery.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction() == MotionEvent.ACTION_UP)
//                    Cache.setImageScrolling(false);
//                return false;
//            }
//        });
        vp_Gallery.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

               // Cache.setImageScrolling(true);
                var pointWidth = DensityUtil.dip2px(context,10);
                var pointHeight= DensityUtil.dip2px(context,10);
                RelativeLayout.LayoutParams layoutParams= new  RelativeLayout.LayoutParams(pointWidth,pointHeight);
                //(LinearLayout.LayoutParams) iv_red_point.getLayoutParams();
                layoutParams.leftMargin =  DensityUtil.dip2px(context,20*i+20*v);

                //MyLog.D("onPageScrolled",layoutParams.leftMargin );
                iv_red_point.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageSelected(int i) {
                var pointWidth = DensityUtil.dip2px(context,10);
                var pointHeight= DensityUtil.dip2px(context,10);
                tv_TopImageText.setText(newsData.getData().getTopnews().get(i).getTitle());
                RelativeLayout.LayoutParams layoutParams= new  RelativeLayout.LayoutParams(pointWidth,pointHeight);
                var widthDistince = DensityUtil.dip2px(context,i*20);
                layoutParams.leftMargin = widthDistince;
                MyLog.D("OnPageSelected",layoutParams.leftMargin );
                Cache.getMainActivity().enableSlidingMenu(i==0?true:false);
                iv_red_point.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }


/*
* need call SetBean first.
 */
    @Override
    public void UpdateView() {
        DataCenter.Load_News(Bean, true, new Gate() {
            @Override
            public void run(Gate.Item param) {
                var newsData= (NewsData) param.getObject();
                if( newsData == null)
                    return;
                DefaultNewsTypeChildPage.this.newsData = newsData;

                var topnews = newsData.getData().getTopnews();
                if(topnews.size()<1) {
                    return;
                }
                listView.clear();
                if(ll_points.getChildCount()>0)
                    ll_points.removeAllViews();
                for (var i=0;i<topnews.size();i++){
                    ImageView imageView =new ImageView(Cache.getContext());
                    var url =topnews.get(i).getTopimage();
                    listView.add(imageView);
                    Glide.with(Cache.getContext()).load(Config.RootUrl+url).into(imageView);

                    var pointWidth = DensityUtil.dip2px(context,10);
                    var pointHeight= DensityUtil.dip2px(context,10);
                    ImageView point = new ImageView(Cache.getContext());
                    point.setImageResource(R.drawable.normalpoint);
                    LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(pointWidth,pointHeight);
                    if(i!=0)
                    layoutParams.leftMargin = pointWidth;
                    point.setLayoutParams(layoutParams);

                    ll_points.addView(point,layoutParams);
                }


                myPagerAdapter.notifyDataSetChanged();
                tv_TopImageText.setText(newsData.getData().getTopnews().get(0).getTitle());

                if( newsListAdapter == null ) {
                    newsListAdapter = new NewsListAdapter(context,newsData, R.layout.item_news_defaulttype);
                    lv_newsItems.setAdapter(newsListAdapter );
                    
                }
                newsListAdapter.setData(newsData);
                newsListAdapter.notifyDataSetChanged();
                vp_Gallery.setCurrentItem(0);
            }
        });
    }

    public void SetBean(MyCategory.DataBean.NewsBean bean) {
        this.Bean =bean;
    }
}
