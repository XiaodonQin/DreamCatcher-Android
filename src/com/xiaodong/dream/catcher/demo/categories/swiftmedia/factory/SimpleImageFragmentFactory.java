package com.xiaodong.dream.catcher.demo.categories.swiftmedia.factory;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.model.PosterItem;
import com.xiaodong.dream.catcher.demo.image.ImageFetcher;
import com.xiaodong.dream.catcher.demo.manager.LocalCacheManager;

import java.util.List;

/**
 * Created by Xiaodong on 2015/8/26.
 */
public class SimpleImageFragmentFactory {
    private static String POSTER_ITEM = "poster_item";

    public static ImageFragment newInstance(PosterItem posterItem){
        ImageFragment mImageFragment = new ImageFragment();

        Bundle mArgs = new Bundle();
        mArgs.putSerializable(POSTER_ITEM, posterItem);
        mImageFragment.setArguments(mArgs);

        return mImageFragment;
    }


    public static class ImageFragment extends Fragment{

        public ImageFragment() {
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View mRootView = inflater.inflate(R.layout.simple_image, container, false);
            ImageView mImageView = (ImageView) mRootView.findViewById(R.id.imageView);

            Bundle mArgs = getArguments();
            PosterItem mPosterItem = (PosterItem) mArgs.getSerializable(POSTER_ITEM);
            if (mPosterItem == null){
                return mRootView;
            }
            String mImageUrl = mPosterItem.getPostPath();

            if (mImageUrl != null){
                LocalCacheManager localCacheManager = LocalCacheManager.getInstance();
                ImageFetcher mPosterImageFetcher = null;
                if (localCacheManager != null)
                    mPosterImageFetcher = localCacheManager.getPosterImageFetcher();

                if (mPosterImageFetcher != null)
                    mPosterImageFetcher.loadImage(mImageUrl, mImageView);

            }

            return mRootView;
        }
    }

}
