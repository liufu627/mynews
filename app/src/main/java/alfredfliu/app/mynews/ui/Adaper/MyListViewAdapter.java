package alfredfliu.app.mynews.ui.Adaper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import alfredfliu.app.mynews.base.ViewHolder;
import lombok.Getter;
import lombok.Setter;

public class MyListViewAdapter extends BaseAdapter {

    Context context;
    List<String> menuStrList;

    @Getter
    @Setter
    int checkedId;

    int itemResourceId;
    int checkedItemResouceId;

    public MyListViewAdapter(ListView listView,List<String> srcList,int itemResourceId,int checkedItemResouceId) {
        this.context = listView.getContext();
        menuStrList = srcList;
        checkedId = 0;

        this.itemResourceId = itemResourceId;
        this.checkedItemResouceId = checkedItemResouceId;
        listView.setDivider(null);
    }

    @Override
    public int getCount() {
        return menuStrList.size();
    }
        @Override
    public Object getItem(int i) {
     return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View contentView, ViewGroup viewGroup) {
        ViewHolder viewHolder=ViewHolder.Bind(context,contentView,viewGroup,itemResourceId,i);
        if( checkedItemResouceId >=0) {
            TextView chk_menuitem = (TextView)viewHolder.findViewById( checkedItemResouceId);
            chk_menuitem.setText(menuStrList.get(i));
            chk_menuitem.setEnabled(checkedId == i);
        }
        return viewHolder.getView();
    }
}
