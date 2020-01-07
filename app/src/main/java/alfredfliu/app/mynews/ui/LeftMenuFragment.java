package alfredfliu.app.mynews.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.base.BasePage;
import alfredfliu.app.mynews.base.Gate;
import alfredfliu.app.mynews.base.LoadWay;
import alfredfliu.app.mynews.data.MyCategory;
import alfredfliu.app.mynews.net.DataCenter;
import alfredfliu.app.mynews.ui.Adaper.MyListViewAdapter;
import alfredfliu.app.mynews.util.Cache;
import lombok.Getter;
import lombok.Setter;
import lombok.var;

public class LeftMenuFragment extends FragmentBase {
    public static final String TAG =LeftMenuFragment.class.getName();
    List<String> menuStrList;
    private ListView listView;
    private  MyListViewAdapter MyListViewAdapter;
    private boolean needToLoadData = true;

    @Setter
    @Getter
    int CheckedPosition;
    private MyCategory MyCategory;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        CheckedPosition = 0;
        menuStrList = new ArrayList<String>();
        listView = new ListView(context);
        listView.setOnItemClickListener(new LeftMenuFragment.MyClickListener());
        MyListViewAdapter = new MyListViewAdapter(listView, menuStrList, R.layout.item_leftmenu,R.id.chk_menuitem);
        listView.setAdapter(MyListViewAdapter );
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 4 ) {
                    var popup=   Toast.makeText(context, "Can not access it.", Toast.LENGTH_SHORT);
                    popup.show();
                    getMainActivity().ToggleMenu();
                    return;
                }

                CheckedPosition = position;
                MyListViewAdapter.setCheckedIndex(position);
                MyListViewAdapter.notifyDataSetChanged();

                UpdateNews();
                //Cache.getMainActivity().ToggleMenu(true);
                //Cache.getMainActivity().mainContentFragment.UpdateView();
            }
        });
    }

    public  void UpdateNews() {
        BasePage obj = getMainActivity().mainContentFragment.getNewsPage().getChildNewsPage(CheckedPosition);
        obj.UpdateView(LoadWay.Load,obj.getClass(),MyCategory);
    }

    public void loadCategoryData() {

         DataCenter.Load_MyCategory(false,new Gate() {
            @Override
            public void run(Item param) {
                //true,url,content,obj
                var menuStrList2 =new ArrayList<String>();
                 MyCategory = (MyCategory)param.getObject();
                for (var data : MyCategory.getData()) {
                    menuStrList2.add(data.getTitle());
                }
                UpdateNewsTypeList(menuStrList2);

                UpdateNews();//show news on main area.
                //Cache.getMainActivity().mainContentFragment.UpdateView();
            }
        });
    }

    public  void UpdateNewsTypeList(List<String> menuStrList2 ) {
        if(menuStrList2 == null || menuStrList2.size()<1)
            return;
        menuStrList.clear();
        menuStrList.addAll(menuStrList2);

        MyListViewAdapter.notifyDataSetChanged();
    }

    public void doubleClick() {
        BasePage obj = getMainActivity().mainContentFragment.getNewsPage().getChildNewsPage(CheckedPosition);
        obj.DoubleClick();
    }

    class MyClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            MyListViewAdapter.setCheckedIndex(position);
            MyListViewAdapter.notifyDataSetChanged();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return  listView;
    }


}
