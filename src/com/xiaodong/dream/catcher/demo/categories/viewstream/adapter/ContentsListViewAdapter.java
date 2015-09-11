package com.xiaodong.dream.catcher.demo.categories.viewstream.adapter;

import android.graphics.Color;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.util.UIUtils;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.model.MediaCluster;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.model.MediaItem;
import com.xiaodong.dream.catcher.demo.categories.viewstream.MainViewstreamFragment;
import com.xiaodong.dream.catcher.demo.image.ImageFetcher;
import com.xiaodong.dream.catcher.demo.manager.LocalCacheManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiaodong on 2015/8/29.
 */
public class ContentsListViewAdapter extends BaseAdapter{
    private static String TAG = "ContentsListViewAdapter";

    private MainViewstreamFragment mContext;
    private LayoutInflater inflater;
    private ImageFetcher mThumbnailImageFetcher = null;

    private List<MediaCluster> mediaClusterList = new ArrayList<>();

    public ContentsListViewAdapter(MainViewstreamFragment fragment) {
        this.mContext = fragment;
        this.inflater = LayoutInflater.from(mContext.getActivity());

        LocalCacheManager localCacheManager = LocalCacheManager.getInstance();
        if (localCacheManager != null)
            mThumbnailImageFetcher = localCacheManager.getThumbnailImageFetcher();
    }

    @Override
    public int getCount() {
        return mediaClusterList == null ? 0:mediaClusterList.size();
    }

    @Override
    public MediaCluster getItem(int position) {
        return mediaClusterList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addContent(List<MediaCluster> clusterList){
        if (mediaClusterList != null){
            this.mediaClusterList.addAll(clusterList);
        }else {
            Log.e(TAG, ">>>mediaClusterList == null");
        }

        notifyDataSetChanged();

    }

    public void deleteContent(){
        if (mediaClusterList != null)
            mediaClusterList.clear();

        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_item_viewstream, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.categoryName);
            viewHolder.categoryName.setTextColor(UIUtils.getThemeColorFromAttrOrRes(convertView.getContext(), R.attr.about_libraries_title_openSource, R.color.about_libraries_title_openSource));
            viewHolder.categoryMore = (ImageView) convertView.findViewById(R.id.categoryMore);
            viewHolder.libraryDescriptionDivider = convertView.findViewById(R.id.libraryDescriptionDivider);
            viewHolder.libraryDescriptionDivider.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(convertView.getContext(), R.attr.about_libraries_dividerLight_openSource, R.color.about_libraries_dividerLight_openSource));
            viewHolder.horizontalScrollView = (HorizontalScrollView) convertView.findViewById(R.id.content_horizontal_scrollview);
            viewHolder.itemContentContainer = (LinearLayout) convertView.findViewById(R.id.item_content_container);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        MediaCluster mediaCluster = mediaClusterList.get(position);

        viewHolder.categoryName.setText(mediaCluster.getCategoryName());

        setupItemContents(viewHolder.itemContentContainer, mediaCluster.getMediaItemList());

        return convertView;
    }

    private void setupItemContents(LinearLayout container, List<MediaItem> dataList){

        if (dataList == null || dataList.size() <= 0){
            container.removeAllViews();
            return;
        }

        CardView cardView;
        ThumbnailViewHolder thumbnailViewHolder;

        int iDataSize = dataList.size();
        int childViewSize = container.getChildCount();

        // add view in container
        if (childViewSize < iDataSize){
            while (childViewSize < iDataSize){
                cardView = (CardView) inflater.inflate(R.layout.list_content_item_viewstream, null);

                thumbnailViewHolder = new ThumbnailViewHolder();
                thumbnailViewHolder.thumbnail = (ImageView) cardView.findViewById(R.id.imageView);
                thumbnailViewHolder.itemName = (TextView) cardView.findViewById(R.id.item_name);
                thumbnailViewHolder.itemName.setTextColor(UIUtils.getThemeColorFromAttrOrRes(cardView.getContext(), R.attr.about_libraries_text_openSource, R.color.about_libraries_text_openSource));

                cardView.setTag(thumbnailViewHolder);
                container.addView(cardView);
                childViewSize ++;
            }
        }else {
            while (childViewSize > iDataSize){
                container.removeViewAt(0);
                childViewSize --;
            }
        }

        MediaItem mediaItem;
        //set item image
        for (int i = 0; i < iDataSize; i++){
            cardView = (CardView) container.getChildAt(i);
            thumbnailViewHolder = (ThumbnailViewHolder) cardView.getTag();
            mediaItem = dataList.get(i);

            mThumbnailImageFetcher.loadImage(mediaItem.getThumbnailPath(), thumbnailViewHolder.thumbnail);
            thumbnailViewHolder.itemName.setText(mediaItem.getName());
        }
    }

    public class ViewHolder{
        TextView categoryName;
        ImageView categoryMore;
        View libraryDescriptionDivider;
        HorizontalScrollView horizontalScrollView;
        LinearLayout itemContentContainer;
    }

    public class ThumbnailViewHolder{
        ImageView thumbnail;
        ProgressBar progressBar;
        TextView itemName;
    }
}
