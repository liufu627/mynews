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
    //type:0
    private List<View> viewList;

    //type:1
    ArrayList<TabDetailPager> tabDetailPagers;
    List<String> titles;

    public MyTabPagerAdapter(Context contex,  List<?> list,boolean hasTitle) {
        this.contex = contex;
        if(hasTitle) {
            this.tabDetailPagers = null;
            this.titles = (List<String>) list;
        }else {
            this.viewList = (List<View>) list;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if(titles!=null)
            return titles.get(position);
        return String.valueOf(position);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public int getCount() {
        if(titles!=null)
            return titles.size();
        return viewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
        //super.destroyItem(container, position, object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=null;
        if(titles==null) {
            view = viewList.get(position);
            container.addView(view);
            return  view;
        }

        //titles !=null
        view = LayoutInflater.from(contex).inflate(R.layout.detail,container,false);
        //var gallery = (ViewPager)view.findViewById(R.id.gallery);

        //gallery.setAdapter(new MyPagerDetailAdapter());


        TextView textView = (TextView) view.findViewById(R.id.textView);
        textView.setText(titles.get(position));
        container.addView(view);;
        return  view;
        //return super.instantiateItem(container, position);
    }
}
