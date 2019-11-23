package alfredfliu.app.mynews.ui.Adaper;

import androidx.viewpager.widget.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
    private final List<View> listView;
    List<String> titles;

    public MyPagerAdapter(List<?> listView) {
        this(listView,null);
    }

    public MyPagerAdapter(List<?> listView,List<String> titles) {
        this.listView = (List<View>) listView;
        this.titles = titles;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        if(titles!=null)
            return titles.get(position);
        return String.valueOf(position);
    }


    @Override
    public int getCount() {
        return listView.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View thisChild = listView.get(position);
        container.addView(thisChild);
        return thisChild;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==object;
    }
}
