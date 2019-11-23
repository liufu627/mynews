package alfredfliu.app.mynews.VM;


import androidx.viewpager.widget.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import alfredfliu.app.mynews.data.MyCategory;
import alfredfliu.app.mynews.ui.NewsType.DefaultNewsTypeChildPage;
import alfredfliu.app.mynews.util.Cache;
import lombok.Getter;
import lombok.var;

public class DefaultNewsTypeVM extends BaseVM {


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

    public DefaultNewsTypeVM() {
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
            var view = new DefaultNewsTypeChildPage(Cache.getContext(), DefaultNewsTypeChildPage.ResID, null);
            insertList.put(bean, view);
            ListTabTitle.add(bean.getTitle());
        }

        // update map;
        map_Bean_View.clear();
        map_Bean_View.putAll(insertList);
    }
    public void Load(MyCategory.DataBean categoryBean) {
        this.categoryBean = categoryBean;
        var newsBeans = this.categoryBean.getChildren();

        UpdateNewBean(newsBeans); //get latest newsbeans
    }

    public void UpdateView() {
        setPage(viewPager.getCurrentItem());
//        for (MyCategory.DataBean.NewsBean beanKey : map_Bean_View.keySet()) {
//            DefaultNewsTypeChildPage view = map_Bean_View.get(beanKey);
//            view.SetBean(beanKey);
//            view.UpdateView();
//        }
    }


    public void setPage(int position) {
        MyCategory.DataBean.NewsBean keyBean = (MyCategory.DataBean.NewsBean) map_Bean_View.keySet().toArray()[position];
        var view = map_Bean_View.get(keyBean);
        view.SetBean(keyBean);
        view.UpdateView();
    }

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }
}
