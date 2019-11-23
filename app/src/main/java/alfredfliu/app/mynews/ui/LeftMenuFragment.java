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
import alfredfliu.app.mynews.base.Gate;
import alfredfliu.app.mynews.net.DataCenter;
import alfredfliu.app.mynews.ui.Adaper.MyListViewAdapter;
import alfredfliu.app.mynews.util.Cache;
import lombok.Getter;
import lombok.Setter;
import lombok.var;

public class LeftMenuFragment extends FragmentBase {
    public static final String TAG =LeftMenuFragment.class.getName();
    List<String> menuStrList;
    private int checkedId;
    private ListView listView;
    private  MyListViewAdapter MyListViewAdapter;
    private boolean needToLoadData = true;

    @Setter
    @Getter
    int NewsType;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        NewsType = 0;
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
                    Cache.getMainActivity().ToggleMenu(true);
                    return;
                }

                NewsType = position;
                MyListViewAdapter.setCheckedIndex(position);
                MyListViewAdapter.notifyDataSetChanged();

                Cache.getNewsPage().setCurrentPage(position);
                Cache.getMainActivity().ToggleMenu(true);
                Cache.getMainActivity().mainContentFragment.UpdateView();
            }
        });
        loadCategoryData();
    }
    public void loadCategoryData() {

         DataCenter.Load_MyCategory(false,new Gate() {
            @Override
            public void run(Item param) {
                var menuStrList2 = (List<String>) param.getObject();
                UpdateView(menuStrList2);
                Cache.getMainActivity().mainContentFragment.UpdateView();
            }
        });
    }

    public  void UpdateView(List<String> menuStrList2 ) {
        if(menuStrList2 == null || menuStrList2.size()<1)
            return;
        menuStrList.clear();
        menuStrList.addAll(menuStrList2);

        MyListViewAdapter.notifyDataSetChanged();
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
