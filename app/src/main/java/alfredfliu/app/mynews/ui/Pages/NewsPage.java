package alfredfliu.app.mynews.ui.Pages;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.base.BasePage;
import alfredfliu.app.mynews.net.DataCenter;
import alfredfliu.app.mynews.ui.MainActivity;
import alfredfliu.app.mynews.ui.NewsType.DefaultNewsType;
import alfredfliu.app.mynews.ui.NewsType.InteractNewType;
import alfredfliu.app.mynews.ui.NewsType.PicNewsType;
import alfredfliu.app.mynews.ui.NewsType.SpecNewsType;
import alfredfliu.app.mynews.ui.NewsType.VoteNewType;
import alfredfliu.app.mynews.util.Cache;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.var;

public class NewsPage extends BasePage {
    //private NoScrollViewPager viewPager_NewsType;

    private List<BasePage> newsTypePages;

    FrameLayout fl_newsTypePages;

    @Getter
    @Setter(value = AccessLevel.PRIVATE)
    int CurrentIndex;

    @Getter
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

        fl_newsTypePages = (FrameLayout)view.findViewById(R.id.fl_newsTypePages);
        fl_newsTypePages.removeAllViews();
        fl_newsTypePages.addView(newsTypePages.get(0).getView());
        Cache.setCurrentNewsType(newsTypePages.get(0));
    }

    @Override
    public void UpdateView() {
        var context = Cache.getContext();
        MainActivity MainActivity =(MainActivity)context;
        var newsType = MainActivity.getLeftMenuFragment().getNewsType();

        newsType=newsType<0?0:newsType;
        newsType=newsType>4?4:newsType;

        //viewPager_NewsType.setCurrentItem(newsType);

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

        showPage(CurrentIndex);

    }

    private void showPage(int pageIndex) {
        CurrentIndex = pageIndex;
        fl_newsTypePages.removeAllViews();
        fl_newsTypePages.addView(newsTypePages.get(pageIndex).getView());
        Cache.setCurrentNewsType(newsTypePages.get(pageIndex));

        Cache.getMainActivity().enableSlidingMenu(pageIndex == 0);
    }
}
