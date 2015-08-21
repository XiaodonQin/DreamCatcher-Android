package com.xiaodong.dream.catcher.demo.categories.dreamcatcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.xiaodong.dream.catcher.demo.MyFragment;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.categories.dreamcatcher.douban.DoubanFragment;
import com.xiaodong.dream.catcher.demo.categories.dreamcatcher.express.ExpressFragment;
import com.xiaodong.dream.catcher.demo.categories.dreamcatcher.tencent.TencentFragment;
import com.xiaodong.dream.catcher.demo.categories.dreamcatcher.weibo.WeiboFragment;
import com.xiaodong.dream.catcher.demo.categories.dreamcatcher.weixin.WeixinFragment;

/**
 * Created by Xiaodong on 2015/8/19.
 */
public class MainDreamCatcherFragment extends MyFragment {

    private Activity mActivity;

    private OnSetMainTitleListener onSetMainTitleListener;

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

    private LayoutInflater mLayoutInflater;
    private View mRootView;
    private FragmentTabHost fragmentTabHost;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mActivity = getActivity();
        onSetMainTitleListener = (OnSetMainTitleListener) mActivity;

        onSetMainTitleListener.onSetMainTitle(R.string.drawer_item_dreamcatcher);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.main_dreamcatcher_layout, container, false);
        mLayoutInflater = inflater;

        initView(mRootView);

        return mRootView;
    }

    private void initView(View view) {
        fragmentTabHost = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        fragmentTabHost.setup(mActivity, getChildFragmentManager(), R.id.content_container_layout);
        fragmentTabHost.getTabWidget().setDividerDrawable(null);
        setupTabHost();
    }

    private void setupTabHost() {
        int count = fragmentArray.length;
        for (int i = 0; i < count; i++) {
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

    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.item_tab_view, null);

        ImageView imageView = (ImageView) view.findViewById(R.id.tab_icon);
        imageView.setImageResource(iconArray[index]);

        TextView textView = (TextView) view.findViewById(R.id.tab_text);
        textView.setText(nameArray[index]);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (onSetMainTitleListener != null)
            onSetMainTitleListener.onSetMainTitle(R.string.drawer_item_dreamcatcher);
    }
}
