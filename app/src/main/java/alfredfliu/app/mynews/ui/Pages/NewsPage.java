package alfredfliu.app.mynews.ui.Pages;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.base.BasePage;
import alfredfliu.app.mynews.net.DataCenter;
import alfredfliu.app.mynews.ui.Adaper.MyTabPagerAdapter;
import alfredfliu.app.mynews.ui.MainActivity;
import alfredfliu.app.mynews.ui.NewsType.DefaultNewsType;
import alfredfliu.app.mynews.ui.NewsType.InteractNewType;
import alfredfliu.app.mynews.ui.NewsType.PicNewsType;
import alfredfliu.app.mynews.ui.NewsType.SpecNewsType;
import alfredfliu.app.mynews.ui.NewsType.VoteNewType;
import alfredfliu.app.mynews.ui.control.NoScrollViewPager;
import alfredfliu.app.mynews.util.Cache;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.var;

public class NewsPage extends BasePage {
    private NoScrollViewPager viewPager_NewsType;

    private List<BasePage> newsTypePages;

    MyTabPagerAdapter myTabPagerAdapter;

    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    int CurrentIndex;

    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    BasePage CurrentPage;

    List<View> viewList;
    public NewsPage(Context context, int resID, ViewGroup parentView) {
        super(context, resID, parentView);

        Cache.setNewsPage(this);

    }
    public void setCurrentPage(int index)
    {
        if(index == CurrentIndex)
            return;
        if(index<0)
            return;
        if(index>newsTypePages.size()-1)
            index = newsTypePages.size()-1;

        Cache.setCurrentNewsType(newsTypePages.get(index));
    }


    @Override
    public void InitViewObject() {
        newsTypePages =new ArrayList<>();
        newsTypePages.add(new DefaultNewsType(context,R.layout.page_news_defaulttype,parentView));
        newsTypePages.add(new SpecNewsType(context,R.layout.page_news_spctype,parentView));
        newsTypePages.add(new PicNewsType(context,R.layout.page_news_pictype,parentView));
        newsTypePages.add(new InteractNewType(context,R.layout.page_news_interacttype,parentView));
        newsTypePages.add(new VoteNewType(context,R.layout.page_news_votetype,parentView));

        viewList = new ArrayList<>();
        for ( var page: newsTypePages) {
            viewList.add(page.getView());
        }

        viewPager_NewsType = (NoScrollViewPager)view.findViewById(R.id.viewPager_NewsType);

        myTabPagerAdapter = new MyTabPagerAdapter(context,viewList,false);
        viewPager_NewsType.setAdapter(myTabPagerAdapter);

        viewPager_NewsType.setCurrentItem(0);

        Cache.setCurrentNewsType(newsTypePages.get(0));
    }

    @Override
    public void UpdateView() {
        var newsType = Cache.getMainActivity().getLeftMenuFragment().getNewsType();

        newsType=newsType<0?0:newsType;
        newsType=newsType>4?4:newsType;

        viewPager_NewsType.setCurrentItem(newsType);

        if(newsType == 4 ) {
         var popup=   Toast.makeText(context, "Can not access it.", Toast.LENGTH_SHORT);
         popup.show();
        }

        List<String> tmpTabs = new ArrayList<>();
        var data =DataCenter.getMyCategory();

        if( data == null )  return;

        var newsBeans = data.getData().get(0).getChildren();
        for (var bean : newsBeans) {
            tmpTabs.add(bean.getTitle());
        }

        Cache.getCurrentNewsType().UpdateView();

        myTabPagerAdapter.notifyDataSetChanged();
    }
}
