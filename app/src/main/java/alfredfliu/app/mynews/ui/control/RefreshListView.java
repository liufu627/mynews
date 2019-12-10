package alfredfliu.app.mynews.ui.control;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class RefreshListView extends ListView {
    public RefreshListView(Context context) {
        super(context);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);


    }


    public interface  Refresh{
        public abstract void HeaderRefreshing();
        public abstract void HeaderRefreshed();

        public abstract void FooterRefreshing();
        public abstract void FooterRefreshed();
    }

}
