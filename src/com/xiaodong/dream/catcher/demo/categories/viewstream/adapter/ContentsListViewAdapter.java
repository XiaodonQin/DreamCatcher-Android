package com.xiaodong.dream.catcher.demo.categories.viewstream.adapter;

import android.graphics.Color;
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

    private List<MediaCluster> mediaClusterList = new ArrayList<>();

    public ContentsListViewAdapter(MainViewstreamFragment fragment) {
        this.mContext = fragment;
        this.inflater = LayoutInflater.from(mContext.getActivity());
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null){
            convertView = inflater.inflate(R.layout.list_item_viewstream, parent, false);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();

        }

        MediaCluster mediaCluster = mediaClusterList.get(position);

        viewHolder.categoryName.setText(mediaCluster.getCategoryName());

        setupItemContents(viewHolder.itemContentContainer, mediaCluster.getMediaItemList());

        return convertView;
    }

    private void setupItemContents(LinearLayout container, List<MediaItem> mediaItemList){

        if (container.getChildCount() > 0)
            container.removeAllViews();

        for (MediaItem mediaItem : mediaItemList){
            View view = inflater.inflate(R.layout.list_content_item_swiftmedia, null);
            CardView cardView = (CardView) view;
            ThumbnailViewHolder thumbnailViewHolder = new ThumbnailViewHolder();

            cardView.setCardBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(cardView.getContext(), R.attr.about_libraries_card, R.color.about_libraries_card));

            thumbnailViewHolder.progressBar = (ProgressBar) cardView.findViewById(R.id.image_progress_bar);
            thumbnailViewHolder.thumbnail = (ImageView) cardView.findViewById(R.id.imageView);
            thumbnailViewHolder.itemName = (TextView) cardView.findViewById(R.id.item_name);
            thumbnailViewHolder.itemName.setTextColor(UIUtils.getThemeColorFromAttrOrRes(cardView.getContext(), R.attr.about_libraries_text_openSource, R.color.about_libraries_text_openSource));

            cardView.setTag(thumbnailViewHolder);
            container.addView(cardView);

            String mImageUrl = mediaItem.getThumbnailPath();

            if (mImageUrl != null){
                LocalCacheManager localCacheManager = LocalCacheManager.getInstance();
                ImageFetcher mThumbnailImageFetcher = null;
                if (localCacheManager != null)
                    mThumbnailImageFetcher = localCacheManager.getThumbnailImageFetcher();

                if (mThumbnailImageFetcher != null)
                    mThumbnailImageFetcher.loadImage(mImageUrl, thumbnailViewHolder.thumbnail);
            }

            thumbnailViewHolder.itemName.setText(mediaItem.getName());
        }

    }

    public class ViewHolder{
        CardView card;

        TextView categoryName;
        ImageView categoryMore;
        View libraryDescriptionDivider;
        HorizontalScrollView horizontalScrollView;
        LinearLayout itemContentContainer;

        public ViewHolder(View itemView) {
            card = (CardView) itemView;
            card.setCardBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_card, R.color.about_libraries_card));

            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
            categoryName.setTextColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_title_openSource, R.color.about_libraries_title_openSource));
            categoryMore = (ImageView) itemView.findViewById(R.id.categoryMore);
            categoryMore.setImageDrawable(new IconicsDrawable(mContext.getActivity(), GoogleMaterial.Icon.gmd_navigate_next).color(Color.GRAY).actionBarSize());
            libraryDescriptionDivider = itemView.findViewById(R.id.libraryDescriptionDivider);
            libraryDescriptionDivider.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_dividerLight_openSource, R.color.about_libraries_dividerLight_openSource));
            horizontalScrollView = (HorizontalScrollView) itemView.findViewById(R.id.content_horizontal_scrollview);
            itemContentContainer = (LinearLayout) itemView.findViewById(R.id.item_content_container);
        }
    }

    public class ThumbnailViewHolder{
        CardView cardView;
        ImageView thumbnail;
        ProgressBar progressBar;
        TextView itemName;
    }
}
