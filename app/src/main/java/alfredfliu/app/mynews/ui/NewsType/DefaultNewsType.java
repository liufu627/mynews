package alfredfliu.app.mynews.ui.NewsType;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.viewpagerindicator.TabPageIndicator;
import java.util.ArrayList;
import java.util.List;
import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.base.BasePage;
import alfredfliu.app.mynews.net.DataCenter;
import alfredfliu.app.mynews.ui.Adaper.MyTabPagerAdapter;
import alfredfliu.app.mynews.util.Cache;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.var;

public class DefaultNewsType extends BasePage {
    private TabPageIndicator tabGroup;
    private ImageButton ib_tab_news;
    private ViewPager viewPager;

    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    boolean isLastPage = false;

    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    boolean isFirstPage = false;

    List<String> tabs;
    MyTabPagerAdapter adapter;

    public DefaultNewsType(Context context, int resID, ViewGroup parentView) {
        super(context, resID, parentView);
        tabs = new ArrayList<>();
    }

    @Override
    public void InitViewObject() {
        tabGroup = (TabPageIndicator) viewHolder.findViewById(R.id.tabpages);
//        ib_tab_news=   (ImageButton) viewHolder.findViewById(R.id.ib_tab_news);
        viewPager = (ViewPager) viewHolder.findViewById(R.id.vp_News);
        adapter = new MyTabPagerAdapter(context, tabs,true);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        isFirstPage = true;
        tabGroup.setViewPager(viewPager);
        tabGroup.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0)
                    isFirstPage = true;
                else {
                    isFirstPage = false;
                    if (position == tabs.size() - 1)
                        isLastPage = true;
                    else
                        isLastPage = false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void UpdateView() {
        List<String> tmpTabs = new ArrayList<>();
        var newsBeans = DataCenter.getMyCategory().getData().get(0).getChildren();
        for (var bean : newsBeans) {
            tmpTabs.add(bean.getTitle());
        }


        tabs.clear();
        tabs.addAll(tmpTabs);
        adapter.notifyDataSetChanged();

        tabGroup.notifyDataSetChanged();
    }
}