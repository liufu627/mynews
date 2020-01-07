package alfredfliu.app.mynews.ui;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentBase  extends Fragment {
   protected Activity context;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
    }

    public MainActivity getMainActivity(){
        return (MainActivity)context;
    }
}
