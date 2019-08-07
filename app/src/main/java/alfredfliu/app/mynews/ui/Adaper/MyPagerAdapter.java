package alfredfliu.app.mynews.ui.Adaper;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MyPagerAdapter extends PagerAdapter {
    private final List<View> imageViewArrayList;

    public MyPagerAdapter(List<View> imageViewArrayList) {
        this.imageViewArrayList = imageViewArrayList;
    }
    @Override
    public int getCount() {
        return imageViewArrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View thisChild = imageViewArrayList.get(position);
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
