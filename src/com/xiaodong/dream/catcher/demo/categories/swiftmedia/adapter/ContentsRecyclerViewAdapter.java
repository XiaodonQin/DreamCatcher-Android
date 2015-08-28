package com.xiaodong.dream.catcher.demo.categories.swiftmedia.adapter;

import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.materialdrawer.util.UIUtils;
import com.viewpagerindicator.CirclePageIndicator;
import com.xiaodong.dream.catcher.demo.views.CustomViewPager;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.categories.home.AppContent;
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
    private List<AppContent> appContentList = new ArrayList<>();

    private MainSwiftMediaFragment mContext;

    private PosterViewPagerAdapter mPosterViewPagerAdapter;

    private ViewPager mViewPager;
    private Handler mMainHandler;


    public ContentsRecyclerViewAdapter(MainSwiftMediaFragment context) {
        super();
        this.mContext = context;
        mPosterViewPagerAdapter = new PosterViewPagerAdapter(context.getChildFragmentManager());
        mMainHandler = new Handler();
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

            final AppContent appContent = appContentList.get(position);

            itemViewHolder.libraryName.setText(appContent.getAppName());
            itemViewHolder.libraryCreator.setText(appContent.getAuthor());
            itemViewHolder.libraryDescription.setText(Html.fromHtml(appContent.getDescription()));
            itemViewHolder.libraryVersion.setText(appContent.getVersion());

            itemViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
//                    mContext.showDeleteItemDialog(i);

                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return appContentList == null? 0:appContentList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void setHeader(List<PosterItem> posterItems){
        this.header = true;
        appContentList.add(0, null);
        if (posterItemList != null){
            posterItemList.clear();
            posterItemList.addAll(posterItems);
        }

        notifyItemInserted(0);

    }

    public void addContent(List<AppContent> appContentList){
        if (appContentList != null)
            this.appContentList.addAll(appContentList);

    }

    public void deleteContent(){
        posterItemList.clear();
        appContentList.clear();
        notifyDataSetChanged();
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

        TextView libraryName;
        TextView libraryCreator;
        View libraryDescriptionDivider;
        TextView libraryDescription;

        View libraryBottomDivider;
        View libraryBottomContainer;

        TextView libraryVersion;
        TextView libraryLicense;

        public ItemViewHolder(View itemView) {
            super(itemView);
            card = (CardView) itemView;
            card.setCardBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_card, R.color.about_libraries_card));

            libraryName = (TextView) itemView.findViewById(R.id.libraryName);
            libraryName.setTextColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_title_openSource, R.color.about_libraries_title_openSource));
            libraryCreator = (TextView) itemView.findViewById(R.id.libraryCreator);
            libraryCreator.setTextColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_text_openSource, R.color.about_libraries_text_openSource));
            libraryDescriptionDivider = itemView.findViewById(R.id.libraryDescriptionDivider);
            libraryDescriptionDivider.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_dividerLight_openSource, R.color.about_libraries_dividerLight_openSource));
            libraryDescription = (TextView) itemView.findViewById(R.id.libraryDescription);
            libraryDescription.setTextColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_text_openSource, R.color.about_libraries_text_openSource));

            libraryBottomDivider = itemView.findViewById(R.id.libraryBottomDivider);
            libraryBottomDivider.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_dividerLight_openSource, R.color.about_libraries_dividerLight_openSource));
            libraryBottomContainer = itemView.findViewById(R.id.libraryBottomContainer);

            libraryVersion = (TextView) itemView.findViewById(R.id.libraryVersion);
            libraryVersion.setTextColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_text_openSource, R.color.about_libraries_text_openSource));
            libraryLicense = (TextView) itemView.findViewById(R.id.libraryLicense);
            libraryLicense.setTextColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_text_openSource, R.color.about_libraries_text_openSource));
        }
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
