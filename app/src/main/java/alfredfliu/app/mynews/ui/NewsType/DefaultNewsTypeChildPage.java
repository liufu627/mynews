package alfredfliu.app.mynews.ui.NewsType;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;

import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

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

    BasePage Parent;

    private  Boolean loaded;
    private LinearLayout ll_loading;
    private int ll_loading_height;
    private TextView tx_dragDown;
    private ImageView img_loading;

    public DefaultNewsTypeChildPage(Context context, int resID, BasePage parentPage) {
        super(context, resID, null);
        loaded = false;

        this.Parent = parentPage;
        listView = new ArrayList<>();


        myPagerAdapter = new MyPagerAdapter(listView);
         newsListAdapter = null;
    }

    @Override
    public void InitViewObject() {
          headerView = View.inflate(context,R.layout.header_news_defaulttype,null);
        vp_Gallery = (TopImageViewPager) headerView.findViewById(R.id.gallery);
        ll_loading = (LinearLayout)headerView.findViewById(R.id.ll_loading);
        ll_loading.measure(0,0);
        ll_loading_height = ll_loading.getMeasuredHeight();
        ll_loading.setPadding(0,-ll_loading_height,0,0);
        tv_TopImageText = (TextView)headerView.findViewById(R.id.tv_TopImageText);
         ll_points =(LinearLayout)headerView.findViewById(R.id.ll_points);
         iv_red_point =(ImageView)headerView.findViewById(R.id.iv_red_point);
        tx_dragDown =(TextView)headerView.findViewById(R.id.tx_dragDown);
        img_loading =(ImageView)headerView.findViewById(R.id.img_loading);

        lv_newsItems = (RefreshListView)view.findViewById(R.id.lv_newsItems);
        lv_newsItems.setOnTouchListener(new View.OnTouchListener() {
            private int bottom;
            private int right;
            private int left;
            private int top;
            private int[] outLocation=new int[2];
            private int[] origial_outLocation =new int[2];
            private float downY;
            private float downX;


            @Override
            public boolean onTouch(View v, MotionEvent event) {
                var action =event.getAction();

                if(action == MotionEvent.ACTION_DOWN){
                    downX = event.getX();
                    downY = event.getY();
                    top = ll_loading.getTop();
                    left =ll_loading.getLeft();
                    right=ll_loading.getRight();
                    bottom=ll_loading.getBottom();

                    if(origial_outLocation ==null) {
                        origial_outLocation = new int[2];
                        vp_Gallery.getLocationOnScreen(origial_outLocation);
                        MyLog.D("ll_loading_height:",ll_loading_height,"meausre height:",ll_loading.getMeasuredHeight(),"height:", ll_points.getHeight());
                    }
                    MyLog.D("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

                }else if(action == MotionEvent.ACTION_MOVE){
                    var distance= (int)(event.getY() -downY);
                        vp_Gallery.getLocationOnScreen(outLocation);
                    MyLog.D("origial-y:",origial_outLocation[1],"y:",outLocation[1],"distance:",distance);
                    if(outLocation[1]>=origial_outLocation[1]
                    && distance>0){
                        MyLog.D( "top-padding:",-ll_loading_height+distance);
                        if( (-ll_loading_height+distance) >= ll_loading_height){
                            tx_dragDown.setText("手松刷新");
                            var animation= new RotateAnimation(0,180,RotateAnimation.RELATIVE_TO_SELF,0.5F,RotateAnimation.RELATIVE_TO_SELF ,0.5F);

                            //var animation2= new RotateAnimation
                            animation.setDuration(500);
                            animation.setFillAfter(true);
                            img_loading.startAnimation(animation);

                        }else{
                            tx_dragDown.setText("下拉刷新");
                        }
                        ll_loading.setPadding(0,-ll_loading_height+distance,0,0);
                    }
                }else if(action == MotionEvent.ACTION_UP) {
                    ll_loading.setPadding(0, -ll_loading_height, 0, 0);
                }
                return false;
            }
        });
        lv_newsItems.addHeaderView(headerView);

        vp_Gallery.setAdapter(myPagerAdapter);
        vp_Gallery.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
                RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams)iv_red_point.getLayoutParams();
                layoutParams.leftMargin =  DensityUtil.dip2px(context,20*i+20*v);

                //MyLog.D("onPageScrolled",layoutParams.leftMargin );
                iv_red_point.setLayoutParams(layoutParams);
            }
            @Override
            public void onPageSelected(int i) {
                tv_TopImageText.setText(newsData.getData().getTopnews().get(i).getTitle());
                RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams)iv_red_point.getLayoutParams();//new  RelativeLayout.LayoutParams(pointWidth,pointHeight);
                var widthDistince = DensityUtil.dip2px(context,i*20);
                layoutParams.leftMargin = widthDistince;
                MyLog.D("OnPageSelected",layoutParams.leftMargin );

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
        if( loaded) return;
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

                loaded = true;
            }
        });
    }

    public void SetBean(MyCategory.DataBean.NewsBean bean) {
        this.Bean =bean;
    }
}
