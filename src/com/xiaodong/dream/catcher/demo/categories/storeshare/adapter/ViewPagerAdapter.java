package com.xiaodong.dream.catcher.demo.categories.storeshare.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.categories.storeshare.fragment.DocumentsFragment;
import com.xiaodong.dream.catcher.demo.categories.storeshare.fragment.MusicFragment;
import com.xiaodong.dream.catcher.demo.categories.storeshare.fragment.PhotosFragment;
import com.xiaodong.dream.catcher.demo.categories.storeshare.fragment.VideosFragment;

/**
 * Created by Xiaodong on 2015/8/24.
 */
public class ViewPagerAdapter extends FragmentPagerAdapter{

    private Activity mContext;

    public ViewPagerAdapter(FragmentManager fm, Activity context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new PhotosFragment();
            case 1:
                return new MusicFragment();
            case 2:
                return new VideosFragment();
            case 3:
                return new DocumentsFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

        @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getResources().getString(R.string.tab_storeshare_photos);
            case 1:
                return mContext.getResources().getString(R.string.tab_storeshare_music);
            case 2:
                return mContext.getResources().getString(R.string.tab_storeshare_videos);
            case 3:
                return mContext.getResources().getString(R.string.tab_storeshare_documents);
        }
        return null;
    }
}
