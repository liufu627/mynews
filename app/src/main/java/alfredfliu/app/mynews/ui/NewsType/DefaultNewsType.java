package alfredfliu.app.mynews.ui.NewsType;

import android.content.Context;
import androidx.viewpager.widget.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.viewpagerindicator.TabPageIndicator;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.base.BasePage;
import alfredfliu.app.mynews.data.MyCategory;
import alfredfliu.app.mynews.net.DataCenter;
import alfredfliu.app.mynews.ui.Adaper.MyPagerAdapter;
import alfredfliu.app.mynews.util.Cache;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.var;

public class DefaultNewsType extends BasePage {
    private TabPageIndicator tabGroup;
    private ViewPager viewPager;
    @Getter
    DefaultNewsTypeController DefaultNewsTypeController;
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
        DefaultNewsTypeController =new DefaultNewsTypeController();

        Cache.setDefaultNewsType(this);
    }

    @Override
    public void InitViewObject() {
        tabGroup = (TabPageIndicator) viewHolder.findViewById(R.id.tabpages);
        viewPager = (ViewPager) viewHolder.findViewById(R.id.vp_News);
        adapter = new MyPagerAdapter(listTabView, listTabTitle);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(0);
        isFirstPage = true;
        tabGroup.setViewPager(viewPager);

        DefaultNewsTypeController.setViewPager(viewPager);
        tabGroup.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {

                if (position == 0){
                    //Cache.getMainActivity().enableSlidingMenu(true);
                    isFirstPage = true;}
                else {
                    Cache.getMainActivity().enableSlidingMenu(false);
                    isFirstPage = false;
                    isLastPage = position == listTabTitle.size() - 1;
                }

               DefaultNewsTypeController.UpdateView();
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
        DefaultNewsTypeController.Load(DataCenter.getMyCategory().getData().get(0));

        listTabTitle.clear();
        listTabTitle.addAll(DefaultNewsTypeController.getListTabTitle());

        listTabView.clear();
        listTabView.addAll(DefaultNewsTypeController.getMap_index_View());

        DefaultNewsTypeController.UpdateView();
        adapter.notifyDataSetChanged();

        tabGroup.notifyDataSetChanged();
    }


    public class DefaultNewsTypeController {

        public List<View> getMap_index_View() {
            List<View> list = new ArrayList<>();
            var list_View = map_Bean_View.values();
            for (var item : list_View)
                list.add(item.getView());
            return list;
        }

        @Getter
        public ArrayList<String> ListTabTitle;

        private MyCategory.DataBean categoryBean;

        //ui
        ViewPager viewPager;

        public LinkedHashMap<MyCategory.DataBean.NewsBean, DefaultNewsTypeChildPage> map_Bean_View;
        //
        int position = 0;

        public DefaultNewsTypeController() {
            ListTabTitle = new ArrayList<>();
            map_Bean_View = new LinkedHashMap<>();
        }

        private MyCategory.DataBean.NewsBean IncludeId(List<MyCategory.DataBean.NewsBean> newsBeans, int id) {
            for (var bean : newsBeans) {
                if (bean.getId() == id)
                    return bean;
            }
            return null;
        }

        private void UpdateNewBean(List<MyCategory.DataBean.NewsBean> newsBeans) {
            if(newsBeans==null||newsBeans.size()<1)
                return;

            LinkedHashMap<MyCategory.DataBean.NewsBean, DefaultNewsTypeChildPage> insertList = new LinkedHashMap<>();
            ListTabTitle.clear();
            ArrayList<MyCategory.DataBean.NewsBean> keys = new ArrayList<>();
            var arr=map_Bean_View.keySet().toArray();

            if(arr.length>0) {
                //existed old news
                var keySet = map_Bean_View.keySet().toArray();
                //delete oldbean in newsbean from map and delete same id in newsBeans
                //delete oldbean not in newsbeans.
                for (var obj : keySet) {
                    var keyBean = (MyCategory.DataBean.NewsBean)obj;
                    var view = map_Bean_View.get(keyBean);
                    var foundbean = IncludeId(newsBeans, keyBean.getId());
                    if (foundbean != null) {
                        insertList.put(foundbean, view);
                        ListTabTitle.add(foundbean.getTitle());
                        newsBeans.remove(foundbean);
                    } else {
                        map_Bean_View.remove(keyBean);
                    }
                }
            }

            //add newsbean not in map.
            for (var bean : newsBeans) {
                var view = new DefaultNewsTypeChildPage(Cache.getContext(), DefaultNewsTypeChildPage.ResID, DefaultNewsType.this);
                insertList.put(bean, view);
                ListTabTitle.add(bean.getTitle());
            }

            // update map;
            map_Bean_View.clear();
            map_Bean_View.putAll(insertList);
        }

        public void setViewPager(ViewPager viewPager) {
            this.viewPager = viewPager;
        }

        public void Load(MyCategory.DataBean categoryBean) {
            this.categoryBean = categoryBean;
            var newsBeans = this.categoryBean.getChildren();

            UpdateNewBean(newsBeans); //get latest newsbeans
        }

        public void UpdateView() {
            int position = viewPager.getCurrentItem();
            MyCategory.DataBean.NewsBean keyBean = (MyCategory.DataBean.NewsBean) map_Bean_View.keySet().toArray()[position];
            var view = map_Bean_View.get(keyBean);
            view.SetBean(keyBean);
            view.UpdateView();
        }

    }
}