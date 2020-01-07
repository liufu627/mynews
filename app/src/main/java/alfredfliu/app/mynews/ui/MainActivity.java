package alfredfliu.app.mynews.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.util.Cache;
import alfredfliu.app.mynews.util.MyLog;
import lombok.Getter;
import lombok.var;
public class MainActivity extends SlidingFragmentActivity {

    private int screeWidth;
    private int screeHeight;
    @Getter
     LeftMenuFragment leftMenuFragment;

     MainContentFragment mainContentFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);//设置没有标题
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.sample_leftmenu_fragment);

        InitSlidingMenu();
        initFragment();
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION );

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
        getSlidingMenu().toggle();
    }

    public void enableSlidingMenu(boolean flag) {
        SlidingMenu slidingMenu = getSlidingMenu();
        if( flag) {
            MyLog.D("SlidingMenu Enable");
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//全屏时，都可以从左向右滑出菜单
        }
        else {
            MyLog.D("SlidingMenu Disable");
            slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        }
    }

    void initFragment() {
        var manager =getSupportFragmentManager();
        var tran =manager.beginTransaction();
        leftMenuFragment = new LeftMenuFragment();
        mainContentFragment=new MainContentFragment();
        //Cache.setMainContentFragment(mainContentFragment);
        tran.replace(R.id.fl_sample_content,mainContentFragment,MainContentFragment.TAG);
        tran.replace(R.id.fl_sample_leftmenu,leftMenuFragment,LeftMenuFragment.TAG);
        //tran.add(mainContentFragment,MainContentFragment.TAG);
        //tran.add(leftMenuFragment,LeftMenuFragment.TAG);
        tran.commit();
    }
}
