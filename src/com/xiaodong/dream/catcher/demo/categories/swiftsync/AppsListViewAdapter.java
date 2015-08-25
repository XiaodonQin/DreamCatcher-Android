package com.xiaodong.dream.catcher.demo.categories.swiftsync;

import android.app.Activity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mikepenz.materialdrawer.util.UIUtils;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.categories.home.AppContent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiaodong on 2015/8/23.
 */
public class AppsListViewAdapter extends BaseAdapter{
    private static String TAG = "AppsListViewAdapter";

    private List<AppContent> appContentList = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private Activity mActivity;


    public AppsListViewAdapter(Activity mActivity) {
        this.mActivity = mActivity;
        this.layoutInflater = LayoutInflater.from(mActivity);
    }

    @Override
    public int getCount() {
        return appContentList == null ? 0 : appContentList.size();
    }

    @Override
    public Object getItem(int position) {
        return appContentList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addContent(List<AppContent> list){
        if(appContentList != null)
            appContentList.addAll(list);

        notifyDataSetChanged();
    }

    public void deleteContent(){
        if (appContentList != null)
            appContentList.clear();
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.list_item_home, null);
//            viewHolder = new ViewHolder(convertView);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        AppContent appContent = appContentList.get(position);

        viewHolder.appName.setText(appContent.getAppName());
        viewHolder.author.setText(appContent.getAuthor());
        viewHolder.description.setText(Html.fromHtml(appContent.getDescription()));

        return convertView;
    }

    public class ViewHolder{
        TextView appName;
        TextView author;
        TextView description;

        public ViewHolder(View itemView) {
            appName = (TextView) itemView.findViewById(R.id.libraryName);
            appName.setTextColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_title_openSource, R.color.about_libraries_title_openSource));
            author = (TextView) itemView.findViewById(R.id.libraryCreator);
            author.setTextColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_text_openSource, R.color.about_libraries_text_openSource));
            description = (TextView) itemView.findViewById(R.id.libraryDescription);
            description.setTextColor(UIUtils.getThemeColorFromAttrOrRes(itemView.getContext(), R.attr.about_libraries_text_openSource, R.color.about_libraries_text_openSource));
        }
    }
}
