package alfredfliu.app.mynews.ui;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingActivity;

import alfredfliu.app.mynews.R;

public class MainActivity extends SlidingActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
    }
}
