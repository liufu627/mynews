package alfredfliu.app.mynews.ui.Adaper;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.base.ViewHolder;
import alfredfliu.app.mynews.util.Config;
import lombok.Getter;
import lombok.Setter;

public class NewsListAdapter extends BaseAdapter {
    @Getter
    @Setter
    alfredfliu.app.mynews.data.NewsData Data;

    Context context;


    int itemResourceId;

    public NewsListAdapter(Context context,alfredfliu.app.mynews.data.NewsData data,int itemResourceId) {
        this.context = context;
        this.Data = data;
        this.itemResourceId = itemResourceId;
    }

    @Override
    public int getCount() {
        if (Data==null ) return 0;
        return Data.getData().getNews().size();
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
        ViewHolder viewHolder = ViewHolder.Bind(context, contentView, viewGroup, itemResourceId, i);

        View view = viewHolder.getView();
        ImageView img_left = (ImageView)view.findViewById(R.id.img_left);
        Glide.with(context).load(Config.RootUrl + Data.getData().getNews().get(i).getListimage()).into(img_left);

        TextView txt_newsTitle = (TextView)view.findViewById(R.id.txt_newsTitle);
        txt_newsTitle.setText(Data.getData().getNews().get(i).getTitle());

        TextView txt_newsDate = (TextView)view.findViewById(R.id.txt_newsDate);
        txt_newsDate .setText(Data.getData().getNews().get(i).getPubdate());

        return view;
    }

}
