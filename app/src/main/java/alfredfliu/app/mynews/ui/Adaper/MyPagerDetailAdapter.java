package alfredfliu.app.mynews.ui.Adaper;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import alfredfliu.app.mynews.data.NewsData;

public class MyPagerDetailAdapter extends PagerAdapter {

    private final NewsData.DataBean newsTab;

    public MyPagerDetailAdapter(NewsData.DataBean newsTab ) {
        this.newsTab = newsTab;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return false;
    }
}
