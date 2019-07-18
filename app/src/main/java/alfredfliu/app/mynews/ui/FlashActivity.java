package alfredfliu.app.mynews.ui;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.util.Config;
import alfredfliu.app.mynews.util.MyLog;

public class FlashActivity extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash);

        if (Config.hadFlash(FlashActivity.this, null)) {
            if (!Config.clickedEnterButton(FlashActivity.this, null)) {
                Intent intent = new Intent(FlashActivity.this, GuideActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(FlashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }
        RelativeLayout imageView =(RelativeLayout)findViewById(R.id.rl_flash);
        RotateAnimation rotateAnimation=new RotateAnimation(0,360,RotateAnimation.RELATIVE_TO_SELF ,0.5f,RotateAnimation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setFillAfter(true);

        AlphaAnimation alphaAnimation=new AlphaAnimation(0,1f);
        alphaAnimation.setFillAfter(true);

        ScaleAnimation scaleAnimation=new ScaleAnimation(0,1,0,1,ScaleAnimation.RELATIVE_TO_SELF,0.5f,ScaleAnimation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setFillAfter(true);

        AnimationSet set = new AnimationSet(false);
        set.addAnimation(alphaAnimation);
        set.addAnimation(rotateAnimation);
        set.addAnimation(scaleAnimation);

        set.setDuration(2000);
        imageView.startAnimation(set);

        set.setAnimationListener(new MyAnimationListener());

    }

    private class MyAnimationListener implements Animation.AnimationListener {
        @Override
        public void onAnimationStart(Animation animation) {

        }

        @Override
        public void onAnimationEnd(Animation animation) {
            if (!Config.clickedEnterButton(FlashActivity.this, null)) {
                Intent intent = new Intent(FlashActivity.this, GuideActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(FlashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
            Config.hadFlash(FlashActivity.this, true);
        }

        @Override
        public void onAnimationRepeat(Animation animation) {

        }
    }
}
