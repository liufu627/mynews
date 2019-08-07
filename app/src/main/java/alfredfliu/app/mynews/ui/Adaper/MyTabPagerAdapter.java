package alfredfliu.app.mynews.ui.Adaper;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.ui.TabDetailPager;

public class MyTabPagerAdapter extends PagerAdapter {

    private final Context contex;
    ArrayList<TabDetailPager> tabDetailPagers;
    List<String> titles;

    public MyTabPagerAdapter(Context contex,  List<String> titles) {
        this.contex = contex;
        this.tabDetailPagers = null;
        this.titles = titles;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        return titles.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
        //super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = LayoutInflater.from(contex).inflate(R.layout.detail,container,false);

        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(titles.get(position));
        container.addView(view);;
        return  view;
        //return super.instantiateItem(container, position);
    }
}
