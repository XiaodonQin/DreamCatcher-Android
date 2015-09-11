package com.xiaodong.dream.catcher.demo.categories.swiftmedia.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.util.UIUtils;
import com.viewpagerindicator.CirclePageIndicator;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.model.MediaCluster;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.model.MediaItem;
import com.xiaodong.dream.catcher.demo.image.ImageFetcher;
import com.xiaodong.dream.catcher.demo.manager.LocalCacheManager;
import com.xiaodong.dream.catcher.demo.views.CustomViewPager;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.MainSwiftMediaFragment;
import com.xiaodong.dream.catcher.demo.categories.swiftmedia.model.PosterItem;
import com.xiaodong.dream.catcher.demo.image.ImageUtils;
import com.xiaodong.dream.catcher.demo.listener.OnPostAndRemoveRunnableListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiaodong on 2015/8/26.
 */
public class ContentsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements OnPostAndRemoveRunnableListener{
    private static String TAG = "ContentsRecyclerViewAdapter";

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private static final int POSTER_AUTO_SCROLL_TIME = 10000;

    private boolean header = false;

    private List<PosterItem> posterItemList = new ArrayList<>();
    private List<MediaCluster> mediaClusterList = new ArrayList<>();

    private MainSwiftMediaFragment mContext;
    private ImageFetcher mThumbnailImageFetcher = null;

    private PosterViewPagerAdapter mPosterViewPagerAdapter;

    private ViewPager mViewPager;
    private Handler mMainHandler;

    private LayoutInflater inflater;


    public ContentsRecyclerViewAdapter(MainSwiftMediaFragment context) {
        super();
        this.mContext = context;
        mPosterViewPagerAdapter = new PosterViewPagerAdapter(context.getChildFragmentManager());
        inflater = LayoutInflater.from(mContext.getActivity());
        mMainHandler = new Handler();

        LocalCacheManager localCacheManager = LocalCacheManager.getInstance();
        if (localCacheManager != null)
            mThumbnailImageFetcher = localCacheManager.getThumbnailImageFetcher();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && header) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_header_swiftmedia, parent, false);
            return new HeaderViewHolder(view);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_swiftmedia, parent, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        Context context = viewHolder.itemView.getContext();

        if (viewHolder instanceof HeaderViewHolder) {
            final HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;

            headerViewHolder.viewPager.setAdapter(mPosterViewPagerAdapter);
            mViewPager = headerViewHolder.viewPager;

            mPosterViewPagerAdapter.addPosterItems(posterItemList);

            Display display = mContext.getActivity().getWindowManager().getDefaultDisplay();
            int width = 0;
            if (ImageUtils.hasIcecreamSandwich()){
                Point size = new Point();
                display.getSize(size);
                width = size.x;
            }else{
                width = display.getWidth();
            }
//            width*9/16
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, width * 328 / 600, Gravity.CENTER);
            headerViewHolder.viewPager.setLayoutParams(layoutParams);

            headerViewHolder.viewPager.setOnTouchListener(onTouchListener);
            headerViewHolder.circlePageIndicator.setViewPager(headerViewHolder.viewPager);
            headerViewHolder.circlePageIndicator.setVisibility(View.VISIBLE);
            headerViewHolder.circlePageIndicator.setOnPageChangeListener(onPageChangeListener);

//            mMainHandler.postDelayed(mViewPagerScrollRunnable, POSTER_AUTO_SCROLL_TIME);

        } else if (viewHolder instanceof ItemViewHolder){
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;

            final MediaCluster mediaCluster = mediaClusterList.get(position);

            Log.i(TAG, ">>MediaCluster: " + mediaCluster.toString());

            itemViewHolder.categoryName.setText(mediaCluster.getCategoryName());
            itemViewHolder.categoryMore.setOnClickListener(onClickListener);
            itemViewHolder.categoryMore.setTag(mediaCluster);
            itemViewHolder.horizontalScrollView.setOnTouchListener(onTouchListener);

            setupItemContents(itemViewHolder.itemContentContainer, mediaCluster.getMediaItemList());
        }
    }

    @Override
    public int getItemCount() {
        return mediaClusterList == null? 0:mediaClusterList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setHeader(List<PosterItem> posterItems){
        this.header = true;
        mediaClusterList.add(0, null);
        if (posterItemList != null){
            posterItemList.clear();
            posterItemList.addAll(posterItems);
        }

        notifyItemInserted(0);

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
        posterItemList.clear();
        mediaClusterList.clear();
        notifyDataSetChanged();
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
                cardView = (CardView) inflater.inflate(R.layout.list_content_item_swiftmedia, null);

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

    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        //finger press start (x1, y1) to (x2, y2)
        float x1 = 0;
        float x2 = 0;
        float y1 = 0;
        float y2 = 0;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN) {
                //when the finger press down
                x1 = event.getX();
                y1 = event.getY();

                mContext.setSwipeRefreshEnabled(false);
            }
            if(event.getAction() == MotionEvent.ACTION_MOVE) {
                //when the finger slide
                x2 = event.getX();
                y2 = event.getY();
                if(y1 - y2 > 75) {//Slide to the up
                    mContext.setSwipeRefreshEnabled(true);
                } else if(y2 - y1 > 75) {//Slide to the down
                    mContext.setSwipeRefreshEnabled(true);
                } else if(x1 - x2 > 25) {//Slide to the left
                    mContext.setSwipeRefreshEnabled(false);
                } else if(x2 - x1 > 25) {//Slide to the right
                    mContext.setSwipeRefreshEnabled(false);
                }
            }
            if (event.getAction() == MotionEvent.ACTION_UP){
                //when the finger left
                mContext.setSwipeRefreshEnabled(true);
            }

            v.getParent().requestDisallowInterceptTouchEvent(true);
            return false;
        }
    };

    Runnable mViewPagerScrollRunnable = new Runnable() {
        @Override
        public void run() {
            if (mViewPager != null && posterItemList != null){
                int currentIndex = mViewPager.getCurrentItem();
                int posterCount = posterItemList.size();
                if (currentIndex < posterCount - 1){
                    mViewPager.setCurrentItem(++currentIndex, true);
                }else {
                    mViewPager.setCurrentItem(0, true);
                }

                mMainHandler.postDelayed(this, POSTER_AUTO_SCROLL_TIME);

            }
        }
    };


    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        CustomViewPager viewPager;
        CirclePageIndicator circlePageIndicator;

        public HeaderViewHolder(View headerView) {
            super(headerView);

            viewPager = (CustomViewPager) headerView.findViewById(R.id.viewpager);
            circlePageIndicator = (CirclePageIndicator) headerView.findViewById(R.id.viewpagerindicator);

        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        CardView card;

        TextView categoryName;
        ImageView categoryMore;
        View libraryDescriptionDivider;
        HorizontalScrollView horizontalScrollView;
        LinearLayout itemContentContainer;

        public ItemViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView;
            card.setCardBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_card, R.color.about_libraries_card));

            categoryName = (TextView) itemView.findViewById(R.id.categoryName);
            categoryName.setTextColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_title_openSource, R.color.about_libraries_title_openSource));
            categoryMore = (ImageView) itemView.findViewById(R.id.categoryMore);
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

    @Override
    public void onPostRunnable() {

        if (mMainHandler != null && mViewPagerScrollRunnable != null)
            mMainHandler.postDelayed(mViewPagerScrollRunnable, POSTER_AUTO_SCROLL_TIME);

    }

    @Override
    public void onRemoveRunnable() {
        if (mMainHandler != null && mViewPagerScrollRunnable != null)
            mMainHandler.removeCallbacks(mViewPagerScrollRunnable);

    }
}
