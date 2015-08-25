package com.xiaodong.dream.catcher.demo.categories.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.materialdrawer.util.UIUtils;
import com.xiaodong.dream.catcher.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiaodong on 2015/8/22.
 */
public class AppsRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    private String mVersionName;
    private Integer mVersionCode;
    private Drawable mIcon;

    private List<AppContent> appContentList = new ArrayList<>();

    private boolean header = false;

    public AppsRecyclerViewAdapter() {
        super();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == TYPE_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_header_home, viewGroup, false);
            return new HeaderViewHolder(view);
        }

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_home, viewGroup, false);

        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {

        Context context = viewHolder.itemView.getContext();

        if (viewHolder instanceof HeaderViewHolder) {
            HeaderViewHolder headerViewHolder = (HeaderViewHolder) viewHolder;

            if (mIcon != null){
                headerViewHolder.aboutIcon.setImageDrawable(mIcon);
            }
            headerViewHolder.aboutVersion.setText(context.getString(R.string.version) + " " + mVersionName + " (" + mVersionCode + ")");

            // Reset aboutSpecial fields
            headerViewHolder.aboutSpecial1.setText("A");
            headerViewHolder.aboutSpecial2.setText("B");
            headerViewHolder.aboutSpecial3.setText("C");

            headerViewHolder.aboutAppDescription.setText(Html.fromHtml(context.getResources().getString(R.string.about_description_text)));
        } else if (viewHolder instanceof ItemViewHolder){
            ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;

            AppContent appContent = appContentList.get(i);

            itemViewHolder.libraryName.setText(appContent.getAppName());
            itemViewHolder.libraryCreator.setText(appContent.getAuthor());
            itemViewHolder.libraryDescription.setText(Html.fromHtml(appContent.getDescription()));
            itemViewHolder.libraryVersion.setText(appContent.getVersion());
        }
    }

    public void setHeader(String versionName, Integer versionCode, Drawable icon) {
        this.header = true;
        appContentList.add(0, null);
        this.mVersionName = versionName;
        this.mVersionCode = versionCode;
        this.mIcon = icon;

        notifyItemInserted(0);
    }

    public void addContent(List<AppContent> appContentList){
        if (appContentList != null)
            this.appContentList.addAll(appContentList);

    }

    public void addItem(AppContent appContent){
        if (appContentList != null)
            this.appContentList.add(appContent);

        notifyDataSetChanged();
    }

    public void removeLastItem(){
        if (appContentList != null)
            this.appContentList.remove(appContentList.size() - 1);

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return appContentList == null? 0:appContentList.size();
    }

    public AppContent getItem(int pos) {
        return appContentList.get(pos);
    }

    public long getItemId(int pos) {
        return pos;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0 && header) {
            return TYPE_HEADER;
        }

        return TYPE_ITEM;
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        ImageView aboutIcon;
        TextView aboutAppName;
        View aboutSpecialContainer;
        Button aboutSpecial1;
        Button aboutSpecial2;
        Button aboutSpecial3;
        TextView aboutVersion;
        View aboutDivider;
        TextView aboutAppDescription;

        public HeaderViewHolder(View headerView) {
            super(headerView);

            //get the about this app views
            aboutIcon = (ImageView) headerView.findViewById(R.id.aboutIcon);
            aboutAppName = (TextView) headerView.findViewById(R.id.aboutName);
            aboutAppName.setTextColor(UIUtils.getThemeColorFromAttrOrRes(headerView.getContext(), R.attr.about_libraries_title_openSource, R.color.about_libraries_title_openSource));
            aboutSpecialContainer = headerView.findViewById(R.id.aboutSpecialContainer);
            aboutSpecial1 = (Button) headerView.findViewById(R.id.aboutSpecial1);
            aboutSpecial2 = (Button) headerView.findViewById(R.id.aboutSpecial2);
            aboutSpecial3 = (Button) headerView.findViewById(R.id.aboutSpecial3);
            aboutVersion = (TextView) headerView.findViewById(R.id.aboutVersion);
            aboutVersion.setTextColor(UIUtils.getThemeColorFromAttrOrRes(headerView.getContext(), R.attr.about_libraries_text_openSource, R.color.about_libraries_text_openSource));
            aboutDivider = headerView.findViewById(R.id.aboutDivider);
            aboutDivider.setBackgroundColor(UIUtils.getThemeColorFromAttrOrRes(headerView.getContext(), R.attr.about_libraries_dividerDark_openSource, R.color.about_libraries_dividerDark_openSource));
            aboutAppDescription = (TextView) headerView.findViewById(R.id.aboutDescription);
            aboutAppDescription.setTextColor(UIUtils.getThemeColorFromAttrOrRes(headerView.getContext(), R.attr.about_libraries_text_openSource, R.color.about_libraries_text_openSource));
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


}
