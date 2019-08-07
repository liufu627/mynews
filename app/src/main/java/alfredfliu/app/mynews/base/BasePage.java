package alfredfliu.app.mynews.base;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import alfredfliu.app.mynews.util.Cache;

public class BasePage {

    protected final Context context;
    protected final ViewGroup parentView;
    protected View view;
    protected int resID;
    protected ViewHolder viewHolder;

    public BasePage(Context context, int resID, ViewGroup parentView) {
        this.context = context;
        this.resID = resID;
        this.parentView = parentView;

        Cache.getMainActivity().enableSlidingMenu(false);
    }

    public View getView() {
        if (view != null)
            return view;

        //view = LayoutInflater.from(context).inflate(resID, parentView, false);

        this.viewHolder =ViewHolder.Bind(context,null,parentView,resID,0 );
        view = this.viewHolder.getView();
        InitViewObject();
        return view;
    }
    public  void InitViewObject(){}

    public  void UpdateView()
    {}

}
