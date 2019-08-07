package alfredfliu.app.mynews.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
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
import alfredfliu.app.mynews.ui.Pages.EMarketPage;
import alfredfliu.app.mynews.ui.Pages.MainPage;
import alfredfliu.app.mynews.ui.Pages.NewsPage;
import alfredfliu.app.mynews.ui.Pages.SettingPage;
import alfredfliu.app.mynews.ui.Pages.ShopCarPage;
import alfredfliu.app.mynews.ui.control.NoScrollViewPager;
import alfredfliu.app.mynews.util.Cache;
import lombok.var;

public class MainContentFragment extends FragmentBase {
    public static final String TAG = MainContentFragment.class.getName();

    ArrayList<View> viewArrayList;
    NoScrollViewPager viewPager;
    RadioGroup radioGroup_bottom_main;

    View thisView;
    private ImageButton ib_leftmenu;
    private Button btn_shopcar_edit;

    private List<BasePage> basePages;
    private  BasePage currentPage;

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


        viewArrayList =new ArrayList<>(5);
        for ( var page: basePages) {
            viewArrayList.add(page.getView());
        }

        ib_leftmenu = (ImageButton)thisView.findViewById(R.id.ib_leftmenu);
         btn_shopcar_edit = (Button)thisView.findViewById(R.id.btn_shopcar_edit);

        ib_leftmenu.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Cache.getMainActivity().ToggleMenu();
            }
        } );

        radioGroup_bottom_main.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                var index = group.indexOfChild(group.findViewById(checkedId));
                viewPager.setCurrentItem(index, false);

                // show the  slidingmenu when news page is on, otherwise, hide it.
               // Cache.getMainActivity().enableSlidingMenu(false);


                var tv_Header = (TextView)thisView.findViewById(R.id.tv_Header);
                currentPage = basePages.get(index);
                switch (index) {
                    case 0:
                        tv_Header.setText("主页面");
                        ib_leftmenu.setVisibility(View.GONE);
                        btn_shopcar_edit.setVisibility(View.GONE);

                        break;

                    case 1:
                        tv_Header.setText("新闻");
                        ib_leftmenu.setVisibility(View.VISIBLE);
                        btn_shopcar_edit.setVisibility(View.GONE);
                        Cache.getMainActivity().leftMenuFragment.loadCategoryData();
                        break;
                    case 2:
                        tv_Header.setText("商城热卖");
                        ib_leftmenu.setVisibility(View.GONE);
                        btn_shopcar_edit.setVisibility(View.GONE);
                        break;
                    case 3:
                        tv_Header.setText("购物车");
                        ib_leftmenu.setVisibility(View.GONE);
                        btn_shopcar_edit.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        tv_Header.setText("设置中心");
                        ib_leftmenu.setVisibility(View.GONE);
                        btn_shopcar_edit.setVisibility(View.GONE);
                        break;
                    default:
                        tv_Header.setText("主页面");
                        ib_leftmenu.setVisibility(View.GONE);
                        btn_shopcar_edit.setVisibility(View.GONE);
                        break;
                }
            }
        });
    }

    public  void UpdateView( )
    {
        currentPage.UpdateView();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        viewPager.setAdapter(new MyPagerAdapter(new ArrayList<View>(viewArrayList)));
        var defaultRadio = radioGroup_bottom_main.getChildAt(0);
        radioGroup_bottom_main.check(defaultRadio.getId());

        return  thisView;
    }
}
