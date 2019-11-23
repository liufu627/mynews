package alfredfliu.app.mynews.ui.NewsType;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import com.viewpagerindicator.TabPageIndicator;
import java.util.ArrayList;
import java.util.List;

import alfredfliu.app.mynews.VM.DefaultNewsTypeVM;
import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.base.BasePage;
import alfredfliu.app.mynews.net.DataCenter;
import alfredfliu.app.mynews.ui.Adaper.MyPagerAdapter;
import alfredfliu.app.mynews.util.Cache;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

public class DefaultNewsType extends BasePage {
    private TabPageIndicator tabGroup;
    private ImageButton ib_tab_news;
    private ViewPager viewPager;
    DefaultNewsTypeVM defaultNewsTypeController;
    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    boolean isLastPage = false;

    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    boolean isFirstPage = false;

    List<String> listTabTitle;
    List<View> listTabView;
    MyPagerAdapter adapter;

    public DefaultNewsType(Context context, int resID, ViewGroup parentView) {
        super(context, resID, parentView);
        listTabTitle = new ArrayList<>();
        listTabView = new ArrayList<>();
        defaultNewsTypeController =new DefaultNewsTypeVM();
    }

    @Override
    public void InitViewObject() {
        tabGroup = (TabPageIndicator) viewHolder.findViewById(R.id.tabpages);
//        ib_tab_news=   (ImageButton) viewHolder.findViewById(R.id.ib_tab_news);
        viewPager = (ViewPager) viewHolder.findViewById(R.id.vp_News);
        adapter = new MyPagerAdapter(listTabView, listTabTitle);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        isFirstPage = true;
        tabGroup.setViewPager(viewPager);

        defaultNewsTypeController.setViewPager(viewPager);
        tabGroup.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0){
                    Cache.getMainActivity().enableSlidingMenu(true);
                    isFirstPage = true;}
                else {
                    Cache.getMainActivity().enableSlidingMenu(false);
                    isFirstPage = false;
                    isLastPage = position == listTabTitle.size() - 1;
                }

                defaultNewsTypeController.setPage(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    @Override
    public void UpdateView() {

        //news:DataCenter.getMyCategory().getData().get(0)
        //special news:DataCenter.getMyCategory().getData().get(1)
        if(DataCenter.getMyCategory()== null || DataCenter.getMyCategory().getData()==null || DataCenter.getMyCategory().getData().size()<1)
            return;
        defaultNewsTypeController.Load(DataCenter.getMyCategory().getData().get(0));

        listTabTitle.clear();
        listTabTitle.addAll(defaultNewsTypeController.getListTabTitle());

        listTabView.clear();
        listTabView.addAll(defaultNewsTypeController.getMap_index_View());

        defaultNewsTypeController.UpdateView();
        adapter.notifyDataSetChanged();

        tabGroup.notifyDataSetChanged();
    }
}