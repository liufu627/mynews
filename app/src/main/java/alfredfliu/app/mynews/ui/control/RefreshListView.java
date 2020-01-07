package alfredfliu.app.mynews.ui.control;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.base.Gate;
import alfredfliu.app.mynews.base.LoadWay;
import alfredfliu.app.mynews.base.Updatable;
import alfredfliu.app.mynews.data.MyCategory;
import alfredfliu.app.mynews.data.NewsData;
import alfredfliu.app.mynews.net.DataCenter;
import alfredfliu.app.mynews.ui.Adaper.MyPagerAdapter;
import alfredfliu.app.mynews.ui.Adaper.NewsListAdapter;
import alfredfliu.app.mynews.ui.MainActivity;
import alfredfliu.app.mynews.util.Cache;
import alfredfliu.app.mynews.util.Config;
import alfredfliu.app.mynews.util.DensityUtil;
import alfredfliu.app.mynews.util.MyLog;
import lombok.Getter;
import lombok.var;

public class RefreshListView extends ListView implements Updatable {


    private final View footerView;
    @Getter
    MyCategory.DataBean.NewsBean Bean;
    private alfredfliu.app.mynews.data.NewsData newsData;

    private ArrayList<ImageView> listView;
    private int bottom;
    private int right;
    private int left;
    private int top;
    private int[] outLocation = new int[2];
    private int[] origial_outLocation = new int[2];
    private float downY = -1;
    private float downX;

    private NewsListAdapter newsListAdapter;


    private View headerView;
    private LinearLayout ll_loading;
    private ImageView img_loading;
    private TextView tx_dragDown;
    TopImageViewPager vp_Gallery;
    TextView tv_TopImageText;
    private LinearLayout ll_points;
    private ImageView iv_red_point;
    private int ll_loading_height;
    MyPagerAdapter myPagerAdapter;

    boolean loadMore;
    boolean draggingDownAnimation;
    private boolean loaded;


    public RefreshListView(final Context context) {
        this(context,null,0);
    }
    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs,0,0);
    }
    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        listView = new ArrayList<ImageView>();
        headerView = View.inflate(getContext(), R.layout.news_defaulttype_header, null);
        vp_Gallery = (TopImageViewPager) headerView.findViewById(R.id.gallery);
        ll_loading = (LinearLayout) headerView.findViewById(R.id.ll_loading);
        tv_TopImageText = (TextView) headerView.findViewById(R.id.tv_TopImageText);
        ll_points = (LinearLayout) headerView.findViewById(R.id.ll_points);
        iv_red_point = (ImageView) headerView.findViewById(R.id.iv_red_point);
        tx_dragDown = (TextView) headerView.findViewById(R.id.tx_dragDown);
        img_loading = (ImageView) headerView.findViewById(R.id.img_loading);


        ll_loading.measure(0, 0);
        ll_loading_height = ll_loading.getMeasuredHeight();
        ll_loading.setPadding(0, -ll_loading_height, 0, 0);
        myPagerAdapter = new MyPagerAdapter(listView);

        vp_Gallery.setAdapter(myPagerAdapter);

        addHeaderView(headerView);

        footerView= View.inflate( context,R.layout.news_defaulttype_footer,null);

        addFooterView(footerView);

        setOnScrollListener(new OnScrollListener(){
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState ==OnScrollListener.SCROLL_STATE_IDLE||scrollState ==OnScrollListener.SCROLL_STATE_FLING){
                    //并且是最后一条可见
                    if(getLastVisiblePosition()>=getCount()-1) {
                        //1.显示加载更多布局
                        footerView.setPadding(8, 8, 8, 8);

                        if (!loadMore) {
                            loadMore = true;
                            DataCenter.LoadMore(newsData, false, new Gate() {
                                @Override
                                public void run(Item arg) {
                                    NewsData newsData = (NewsData) arg.getObject();
                                    UpdateView(LoadWay.LoadMore, null, newsData);
                                }
                            });
                        }
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            }
        });

        vp_Gallery.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)iv_red_point.getLayoutParams();
                layoutParams.leftMargin =  DensityUtil.dip2px(getContext(),20*i+20*v);

                //MyLog.D("onPageScrolled",layoutParams.leftMargin );
                iv_red_point.setLayoutParams(layoutParams);
            }
            @Override
            public void onPageSelected(int i) {
                tv_TopImageText.setText(newsData.getData().getTopnews().get(i).getTitle());
                RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams)iv_red_point.getLayoutParams();//new  RelativeLayout.LayoutParams(pointWidth,pointHeight);
                var widthDistince = DensityUtil.dip2px(getContext(),i*20);
                layoutParams.leftMargin = widthDistince;
                MyLog.D("OnPageSelected",layoutParams.leftMargin );

                iv_red_point.setLayoutParams(layoutParams);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    private MainActivity getMainActivity(){
        return (MainActivity) this.getContext();
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        var action = ev.getAction();

        if (action == MotionEvent.ACTION_DOWN) {
            downX = ev.getX();
            downY = ev.getY();
            top = ll_loading.getTop();
            left = ll_loading.getLeft();
            right = ll_loading.getRight();
            bottom = ll_loading.getBottom();

            if (origial_outLocation == null) {
                origial_outLocation = new int[2];
                vp_Gallery.getLocationOnScreen(origial_outLocation);
                MyLog.D("ll_loading_height:", ll_loading_height, "meausre height:", ll_loading.getMeasuredHeight(), "height:", ll_points.getHeight());
            }
            //MyLog.D("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

        } else if (action == MotionEvent.ACTION_MOVE) {
            if (downY == -1) {
                downY = ev.getY();
            }
            var distance = (int) (ev.getY() - downY);
            vp_Gallery.getLocationOnScreen(outLocation);
            //MyLog.D("origial-y:", origial_outLocation[1], "y:", outLocation[1], "distance:", distance);
            //MyLog.D("distance",distance,"ll_loading_height",ll_loading_height);
            if (outLocation[1] >= origial_outLocation[1] && distance > 0) {
                //MyLog.D("top-padding:", -ll_loading_height + distance);
                if ((-ll_loading_height + distance) > ll_loading_height) {//(-ll_loading_height + distance)

                    tx_dragDown.setText("手松刷新");
                    //MyLog.D("手松刷新");
                    loadMore = true;
                    if(!draggingDownAnimation) {
                        var animation = new RotateAnimation(0, 180, RotateAnimation.RELATIVE_TO_SELF, 0.5F, RotateAnimation.RELATIVE_TO_SELF, 0.5F);

                        //var animation2= new RotateAnimation
                        animation.setDuration(500);
                        animation.setFillAfter(true);
                        img_loading.startAnimation(animation);
                        draggingDownAnimation = true;
                    }

                } else {
                    tx_dragDown.setText("下拉刷新");
                    //MyLog.D("下拉刷新");
                   // DataCenter.LoadMore();
                    loadMore = false;
                }
                ll_loading.setPadding(0, -ll_loading_height + distance, 0, 0);
            }else{
                tx_dragDown.setText("下拉刷新");
            }
        } else if (action == MotionEvent.ACTION_UP) {
            draggingDownAnimation = false;
            downY = -1;
            ll_loading.setPadding(0, -ll_loading_height, 0, 0);

            if(loadMore){
                MyLog.D("正在加载更多。。。");
                DataCenter.LoadMore(newsData, false, new Gate() {
                    @Override
                    public void run(Item arg) {

                       NewsData newsData= (NewsData) arg.getObject();
                       UpdateView(LoadWay.LoadMore,null,newsData);

                    }
                });
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void UpdateView(LoadWay way,Class classInfo,Object data) {
        if (way == LoadWay.Load || way == LoadWay.Reload) {
            if (newsData != null || (way == LoadWay.Load && loaded))
                return;
            loaded = true;

            newsData = (NewsData) data;
            listView.clear();
            if (ll_points.getChildCount() > 0)
                ll_points.removeAllViews();
            var topnews = newsData.getData().getTopnews();
            if (topnews.size() < 1) {
                return;
            }
            for (var i = 0; i < topnews.size(); i++) {
                ImageView imageView = new ImageView(RefreshListView.this.getContext());
                var url = topnews.get(i).getTopimage();
                listView.add(imageView);
                Glide.with(RefreshListView.this.getContext()).load(Config.RootUrl + url).into(imageView);

                var pointWidth = DensityUtil.dip2px(getContext(), 10);
                var pointHeight = DensityUtil.dip2px(getContext(), 10);
                ImageView point = new ImageView(RefreshListView.this.getContext());
                point.setImageResource(R.drawable.normalpoint);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(pointWidth, pointHeight);
                if (i != 0)
                    layoutParams.leftMargin = pointWidth;
                point.setLayoutParams(layoutParams);

                ll_points.addView(point, layoutParams);
            }

            myPagerAdapter.notifyDataSetChanged();
            tv_TopImageText.setText(newsData.getData().getTopnews().get(0).getTitle());

            if (newsListAdapter == null) {
                newsListAdapter = new NewsListAdapter(getContext(), newsData, R.layout.item_news_defaulttype);
                this.setAdapter(newsListAdapter);
            }
            newsListAdapter.setData(newsData);
            newsListAdapter.notifyDataSetChanged();
            vp_Gallery.setCurrentItem(0);

        } else if (way == LoadWay.LoadMore) {
            try {
                NewsData newsDataMore = (NewsData) data;

                if (newsDataMore.getData() == null || newsDataMore.getData().getNews() == null)
                    return;

                for (var item : newsDataMore.getData().getNews()) {
                    newsData.getData().getNews().add(item);
                }
                newsData.getData().setMore(newsDataMore.getData().getMore());

                newsListAdapter.notifyDataSetChanged();
            } finally {
                loadMore = false;
                MyLog.D("已加载更多");
            }
        }
    }

    public String getUrl(int position) {
        if(position<0) return null;
        if(position> newsData.getData().getNews().size()-1)
            return null;

        return newsData.getData().getNews().get(position).getUrl();
    }
}
