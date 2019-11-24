package alfredfliu.app.mynews.ui;

import android.os.Bundle;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import alfredfliu.app.mynews.R;
import alfredfliu.app.mynews.base.BasePage;
import alfredfliu.app.mynews.ui.Adaper.MyPagerAdapter;
import alfredfliu.app.mynews.ui.NavTab.EMarketPage;
import alfredfliu.app.mynews.ui.NavTab.MainPage;
import alfredfliu.app.mynews.ui.NavTab.NewsPage;
import alfredfliu.app.mynews.ui.NavTab.SettingPage;
import alfredfliu.app.mynews.ui.NavTab.ShopCarPage;
import alfredfliu.app.mynews.ui.control.NoScrollViewPager;
import alfredfliu.app.mynews.util.Cache;
import lombok.Getter;
import lombok.var;

public class MainContentFragment extends FragmentBase {
    public static final String TAG = MainContentFragment.class.getName();

    ArrayList<View> viewList;
    NoScrollViewPager viewPager;
    RadioGroup radioGroup_bottom_main;

    View thisView;
    private ImageButton ib_leftmenu;
    private Button btn_shopcar_edit;

    @Getter
     List<BasePage> basePages;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        thisView = View.inflate(context,R.layout.fragment_main,null);
        viewPager = (NoScrollViewPager) thisView.findViewById(R.id.viewPager_Main);
        radioGroup_bottom_main = (RadioGroup) thisView.findViewById(R.id.radioGroup_bottom_main);

        basePages = new ArrayList<>();
        basePages.add(new MainPage(context,R.layout.page_main,null));
        basePages.add(new NewsPage(context,R.layout.page_news,null));
        basePages.add(new EMarketPage(context,R.layout.page_emarket,null));
        basePages.add(new ShopCarPage(context,R.layout.page_shopcar,null));
        basePages.add(new SettingPage(context,R.layout.page_setting,null));


        viewList =new ArrayList<>(5);
        for ( var page: basePages) {
            viewList.add(page.getView());
        }

        ib_leftmenu = (ImageButton)thisView.findViewById(R.id.ib_leftmenu);
         btn_shopcar_edit = (Button)thisView.findViewById(R.id.btn_shopcar_edit);

        ib_leftmenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Cache.getMainActivity().ToggleMenu();
            }
        } );

        //Cache.setCurrentPage( basePages.get(0) );
        viewPager.setTag(viewList.get(0));
        radioGroup_bottom_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                var index = group.indexOfChild(group.findViewById(checkedId));
                viewPager.setCurrentItem(index, false);
                viewPager.setTag(viewList.get(index));

                // show the  slidingmenu when news page is on, otherwise, hide it.
               // Cache.getMainActivity().enableSlidingMenu(false);

                var tv_Header = (TextView)thisView.findViewById(R.id.tv_Header);
                Cache.getMainActivity().enableSlidingMenu(false);
                ib_leftmenu.setVisibility(View.GONE);
                btn_shopcar_edit.setVisibility(View.GONE);
                switch (index) {

                    case 1:
                        tv_Header.setText("新闻");
                        ib_leftmenu.setVisibility(View.VISIBLE);
                        Cache.getMainActivity().leftMenuFragment.loadCategoryData();
                        break;
                    case 2:
                        tv_Header.setText("商城热卖");
                        break;
                    case 3:
                        tv_Header.setText("购物车");
                        btn_shopcar_edit.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        tv_Header.setText("设置中心");
                        break;
                    default:
                        tv_Header.setText("主页面");
                        break;
                }
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewPager.setAdapter(new MyPagerAdapter(viewList));
        var defaultRadio = radioGroup_bottom_main.getChildAt(0);
        radioGroup_bottom_main.check(defaultRadio.getId());

        return  thisView;
    }
}
