package alfredfliu.app.mynews.ui.NewsType;

import android.content.Context;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.base.BasePage;
import alfredfliu.app.mynews.base.Gate;
import alfredfliu.app.mynews.base.LoadWay;
import alfredfliu.app.mynews.data.MyCategory;
import alfredfliu.app.mynews.data.NewsData;
import alfredfliu.app.mynews.net.DataCenter;
import alfredfliu.app.mynews.ui.NewsDetailActivity;
import alfredfliu.app.mynews.ui.control.RefreshListView;
import alfredfliu.app.mynews.util.Config;
import alfredfliu.app.mynews.util.MyLog;
import lombok.var;

public class DefaultNewsTypeChildPage extends BasePage {

    public DefaultNewsTypeChildPage(Context context, int resID, BasePage parentPage, String title) {
        super(context, resID, null);
        loaded = false;
        this.title = title;

        this.Parent = parentPage;
    }

    @Override
    public void InitViewObject() {
        lv_newsItems = (RefreshListView) view.findViewById(R.id.lv_newsItems);
        lv_newsItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DefaultNewsTypeChildPage.this.context, NewsDetailActivity.class);
                String moreUrl = lv_newsItems.getUrl(position - 1);
                if (moreUrl == null || moreUrl.length() < 1)
                    return;
                intent.putExtra("url", Config.RootUrl + moreUrl);
                DefaultNewsTypeChildPage.this.context.startActivity(intent);
            }
        });
    }


    /*
     * need call SetBean first.
     */
    @Override
    public void UpdateView(final LoadWay way, Class<?> classInfo, Object data) {
        if (way == LoadWay.Load && loaded) return;
        if (data == null || !(data instanceof MyCategory.DataBean.NewsBean))
            return;

        MyCategory.DataBean.NewsBean bean = (MyCategory.DataBean.NewsBean) data;

        DataCenter.Load_News(bean, true, new Gate() {
            @Override
            public void run(Gate.Item param) {
                var newsData = (NewsData) param.getObject();
                if (newsData == null)
                    return;

                lv_newsItems.UpdateView(way, lv_newsItems.getClass(), newsData);
                loaded = true;
            }
        });
    }

    @Override
    public void DoubleClick() {
        super.DoubleClick();
        MyLog.D("DoubleClick",title);
        lv_newsItems.setSelection(0);
    }

    public static final int ResID = R.layout.page_news_defaulttype_content;
    private final String title;
    BasePage Parent;
    private Boolean loaded;
    private RefreshListView lv_newsItems;
}
