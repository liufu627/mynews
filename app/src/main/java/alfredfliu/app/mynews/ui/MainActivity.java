package alfredfliu.app.mynews.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.util.Cache;
import lombok.var;

public class MainActivity extends SlidingFragmentActivity {

    private int screeWidth;
    private int screeHeight;
     LeftMenuFragment leftMenuFragment;
     MainContentFragment mainContentFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.sample_leftmenu_fragment);

        InitSlidingMenu();
        initFragment();

        Cache.setContext(this);
        Cache.setMainActivity(this);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

    }

    private void InitSlidingMenu(){
        SlidingMenu slidingMenu = getSlidingMenu();
        //左部出菜单，从左向右滑出菜单

        slidingMenu.setMode(SlidingMenu.LEFT);
        //6.设置主页占据的宽度
        DisplayMetrics outmetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(outmetrics);
        screeWidth = outmetrics.widthPixels;
        slidingMenu.setBehindOffset((int) (screeWidth * 0.65));//离右边距离

        enableSlidingMenu(false);
    }
    public  void ToggleMenu() {
        if(!getSlidingMenu().isMenuShowing()) {
            leftMenuFragment.loadCategoryData();
        }

        getSlidingMenu().toggle();
    }

    public void enableSlidingMenu(boolean flag) {
        SlidingMenu slidingMenu = getSlidingMenu();
        if( flag)
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        else
            //全屏时，都可以从左向右滑出菜单
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
    }

    void initFragment() {
        var manager =getSupportFragmentManager();
        var tran =manager.beginTransaction();
        leftMenuFragment = new LeftMenuFragment();
        mainContentFragment=new MainContentFragment();
        tran.replace(R.id.fl_sample_content,mainContentFragment,MainContentFragment.TAG);
        tran.replace(R.id.fl_sample_leftmenu,leftMenuFragment,LeftMenuFragment.TAG);
        tran.commit();
    }
}
