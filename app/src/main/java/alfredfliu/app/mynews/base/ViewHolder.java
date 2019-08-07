package alfredfliu.app.mynews.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ViewHolder {
    SparseArray<View>  items;
    View contentView;
    int position;
    Context context;

    private ViewHolder( Context context,ViewGroup parent,int resourceID ) {
        this.contentView = LayoutInflater.from(context).inflate(resourceID,parent,false);
        this.context = context;
        this.contentView.setTag(this);
        items = new SparseArray<>();
    }

    public  static ViewHolder Bind( Context context, View contentView, ViewGroup parent,
                                    int layoutRes, int position){
        ViewHolder obj = null;
        if( contentView == null )
            obj =new ViewHolder(context,parent,layoutRes);
        else
            obj = (ViewHolder) contentView.getTag();

        obj.position = position;
        return  obj;
    }


    public View getView() {
        return contentView;
    }

    public View findViewById(int resID){
        View childView= items.get(resID);
        if(childView==null) {
            childView = contentView.findViewById(resID);
            items.put(resID,childView);
        }
        return childView;
    }
}
