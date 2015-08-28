package com.xiaodong.dream.catcher.demo.categories.swiftmedia.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiaodong.dream.catcher.demo.categories.swiftmedia.factory.SimpleImageFragmentFactory;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.model.PosterItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiaodong on 2015/8/26.
 */
public class PosterViewPagerAdapter extends FragmentPagerAdapter{

    private List<PosterItem> posterItemList = new ArrayList<>();

    public PosterViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return SimpleImageFragmentFactory.newInstance(posterItemList.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getCount() {
        return posterItemList == null ? 0 : posterItemList.size();
    }

    public void addPosterItems(List<PosterItem> posterItems){
        if (posterItemList != null){
            posterItemList.clear();
            posterItemList.addAll(posterItems);
        }

        notifyDataSetChanged();
    }
}
