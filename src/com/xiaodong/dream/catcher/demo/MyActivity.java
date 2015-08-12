package com.xiaodong.dream.catcher.demo;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.xiaodong.dream.catcher.demo.douban.DoubanFragment;
import com.xiaodong.dream.catcher.demo.express.ExpressFragment;
import com.xiaodong.dream.catcher.demo.manager.ClientManager;
import com.xiaodong.dream.catcher.demo.tencent.TencentFragment;
import com.xiaodong.dream.catcher.demo.weibo.WeiboFragment;
import com.xiaodong.dream.catcher.demo.weixin.WeixinFragment;

public class MyActivity extends FragmentActivity implements MyFragment.OnSetMainTitleListener{

    private FragmentTabHost fragmentTabHost;
    private LayoutInflater layoutInflater;

    private TextView mainTitle;
    private ImageView backBtn;
    private ImageView menuBtn;


    private Class fragmentArray[] = {
            WeixinFragment.class, WeiboFragment.class,
            TencentFragment.class, DoubanFragment.class,
            ExpressFragment.class
    };
    private int nameArray[] = {
            R.string.weixin_title, R.string.weibo_title,
            R.string.tencent_title, R.string.douban_title,
            R.string.express_title
    };
    private int iconArray[] = {
            R.drawable.logo_weixin, R.drawable.logo_sina,
            R.drawable.logo_tencent, R.drawable.logo_douban,
            R.drawable.logo_express
    };
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        //init client manager
        ClientManager.init(this.getApplicationContext());

        initView();
    }

    private void initView(){
        layoutInflater = LayoutInflater.from(this);

        mainTitle = (TextView) findViewById(R.id.title);
        backBtn = (ImageView) findViewById(R.id.back_btn);
        menuBtn = (ImageView) findViewById(R.id.menu_btn);

        menuBtn.setVisibility(View.VISIBLE);

        fragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.content_container_layout);

        setupTabHost();

    }

    private void setupTabHost(){
        int count = fragmentArray.length;
        for(int i = 0; i < count; i++){
            //为每一个Tab按钮设置图标、文字和内容
            TabHost.TabSpec tabSpec = fragmentTabHost.newTabSpec(getResources().getString(nameArray[i]))
                    .setIndicator(getTabItemView(i));
            //将Tab按钮添加进Tab选项卡中
            fragmentTabHost.addTab(tabSpec, fragmentArray[i], null);
            //设置Tab按钮的背景
            fragmentTabHost.getTabWidget().getChildAt(i).setBackgroundResource(R.drawable.tab_bg);
        }

        fragmentTabHost.setCurrentTab(0);
    }

    private View getTabItemView(int index){
        View view = layoutInflater.inflate(R.layout.item_tab_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageResource(iconArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.tab_text);
        textView.setText(nameArray[index]);

        return view;
    }

    @Override
    public void onSetMainTitle(int resourceId) {
        if(mainTitle != null){
            mainTitle.setText(resourceId);
        }
    }

    @Override
    public void onSetMainTitle(String string) {

    }
}
