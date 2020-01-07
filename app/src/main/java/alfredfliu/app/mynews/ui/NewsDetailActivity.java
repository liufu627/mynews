package alfredfliu.app.mynews.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.Nullable;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.util.DensityUtil;
import alfredfliu.app.mynews.util.MyLog;
import lombok.var;

public class NewsDetailActivity extends Activity implements View.OnClickListener {
    private ImageButton btnBack;
    private WebView webView;
    private ImageButton btnTextSize;
    private ImageButton btnShare;
    private WebSettings setting;
    private int tempSize = 2;
    private int realSize = 2;
    private RadioGroup rg_textsize;

    public NewsDetailActivity() {


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetail);

        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnTextSize = (ImageButton) findViewById(R.id.btnTextSize);
        btnShare = (ImageButton) findViewById(R.id.btnShare);
        webView = (WebView) findViewById(R.id.webView);
        //webView.js
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION );
        webView.loadUrl(this.getIntent().getStringExtra("url"));

        setting = webView.getSettings();

        btnBack.setOnClickListener(this);
        btnTextSize.setOnClickListener(this);
        btnShare.setOnClickListener(this);


        //this.se
    }

    @Override
    public void onClick(View v) {
        if (v == btnTextSize) {
            showTextSizeDlg();
        }else if(v==btnBack)
            finish();
        else
        {
        }
    }

    private Dialog getDialog(Dialog arg) {
      Dialog dlg =new Dialog(this,R.style.Theme_AppCompat_DayNight);
        View view = View.inflate(this,R.layout.dlg_textsize,null);
       rg_textsize = (RadioGroup )view.findViewById(R.id.rg_textsize);
        rg_textsize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                var index = group.indexOfChild(group.findViewById(checkedId));
                changeTextSize(index);
            }
        });

        dlg.setContentView(view);
        Window window = dlg.getWindow();
        //设置弹出位置
        window.setGravity(Gravity.BOTTOM);
        dlg.setCancelable(true);
        int matchParent = ViewGroup.LayoutParams.MATCH_PARENT;//父布局的宽度

        Window dialogWindow = dlg.getWindow();
        //dialogWindow.setGravity(Gravity.BOTTOM | Gravity.LEFT);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//        lp.width = matchParent;
//        lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
//        lp.x = matchParent;
//        lp.y = 0;  //设置出现的高度，距离顶部
        lp.alpha=1f;
        //lp.verticalMargin=0.02f;
        //lp.horizontalMargin=0.2f;
        window.setAttributes(lp);
        dlg.setCanceledOnTouchOutside(true);
        //dlg.getCurrentFocus();

        //设置弹出动画
        //     window.setWindowAnimations(R.style.main_menu_animStyle);
        //int padding = DensityUtil.dip2px(this,2);
        //setBackgroundDrawable(Color.TRANSPARENT);
        //window.getDecorView()..setBackgroundColor(Color.TRANSPARENT);
        //window.getDecorView().setPadding( padding,padding,padding,padding);

        //设置对话框大小
        //window.alwaysReadCloseOnTouchAttr
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //window.addFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH);

        dlg.show();


        return dlg;

    }

    private void showTextSizeDlg() {

        //DialogFragment dialogFragment=this.getWindowManager().di

//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        View view = View.inflate(this,R.layout.dlg_textsize,null);
//        builder.setView(view);
//        Dialog dlg =builder.show();

        getDialog(null);
//
//        builder.setView(view);
//
//        //WindowManager manager = getWindowManager();
       // AlertDialog dlg = builder.show();
        //builder.setCancelable()
//
//        dlg.getWindow().getDecorView().setPadding(0,0,0,0);
//        dlg.getWindow().getDecorView().setMinimumWidth(getResources().getDisplayMetrics().widthPixels);
//        dlg.getWindow().getDecorView().setBackgroundColor(Color.GREEN);
//
//        WindowManager.LayoutParams lps = dlg.getWindow().getAttributes();
//        lps.verticalMargin = 0;
//        dlg.getWindow().setAttributes(lps);
//
//        dlg.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);

//        dlg.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN| WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION|WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION
//        |WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );
//
//        Window window = dlg.getWindow();
//        var attr = window.getAttributes();
//        Point point =new Point(0,0);
//        getWindowManager().getDefaultDisplay().getSize(point);
//        attr.width =point.x;
//        attr.alpha = 0.5f;
//
//        //attr.height = point.y;
//
//        Point activitySize = new Point(0,0);
//        this.getWindowManager().getDefaultDisplay().getSize(activitySize);
//        MyLog.D("Actiivy width",activitySize.x,"Height",activitySize.y,"dlg width",point.x, "height",point.y);
//        window.setAttributes(attr);
//        window.setGravity(Gravity.BOTTOM);
//
//        dlg.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.MATCH_PARENT);
        //builder.setSingleChoiceItems()
    }

    private void showTextSizeDlg2() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("设置文字大小");
        String[] items = {"超大字体", "大字体", "正常字体", "小字体", "超小字体"};
        builder.setSingleChoiceItems(items, realSize, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tempSize = which;
                changeTextSize(tempSize);
            }
        });
        builder.setNegativeButton("关闭", null);
//        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//                realSize = tempSize;
//                changeTextSize(realSize);
//
//            }
//        });
        AlertDialog dlg = builder.show();
        dlg.getWindow().setGravity(Gravity.BOTTOM);

    }

    private void changeTextSize(int realSize) {
        switch (realSize){
            case 0://超大字体
                setting.setTextZoom(200);
                break;
            case 1://大字体
                setting.setTextZoom(150);
                break;
            case 2://正常字体
                setting.setTextZoom(100);
                break;
            case 3://小字体
                setting.setTextZoom(75);
                break;
            case 4://超小字体
                setting.setTextZoom(50);
                break;
        }
    }
}
