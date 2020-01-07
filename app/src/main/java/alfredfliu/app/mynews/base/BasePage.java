package alfredfliu.app.mynews.base;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import alfredfliu.app.mynews.ui.MainActivity;

public class BasePage implements Updatable {

    protected final Context context;
    protected final ViewGroup parentView;
    protected View view;
    protected int resID;
    protected ViewHolder viewHolder;

    protected  boolean loaded;


    public BasePage(Context context, int resID, ViewGroup parentView) {
        this.context = context;
        this.resID = resID;
        this.parentView = parentView;


    }

    /**
     * get resID view
     * @return
     */
    public View getView() {
        if (view != null)
            return view;

        //view = LayoutInflater.from(context).inflate(resID, parentView, false);

        this.viewHolder =ViewHolder.Bind(context,null,parentView,resID,0 );
        view = this.viewHolder.getView();
        InitViewObject();
        return view;
    }

    /**
     * init resID view
     * @return
     */
    public  void InitViewObject(){}


    public void UpdateView(LoadWay way,Class<?> classInfo,Object data)
    {}

    public void DoubleClick()
    {}

    public MainActivity getMainActivity(){
        return (MainActivity )getView().getContext();
    }

}
