/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/12.
 */
package com.xiaodong.dream.catcher.demo.express.search;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.xiaodong.dream.catcher.demo.R;
import com.xiaodong.dream.catcher.demo.sdk.model.LogisticsItem;
import com.xiaodong.dream.catcher.demo.utils.Utils;
import com.xiaodong.dream.catcher.demo.sdk.model.ExpressInfo;

import java.util.List;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class SearchExpressResultActivity extends FragmentActivity implements AdapterView.OnItemClickListener {
    private static String TAG = "SearchExpressResultActivity";

    public static String SEARCH_TYPE = "search_type";
    public static String SEARCH_POSTID = "search_postid";

    private String searchType;
    private String searchPostId;

    private ImageView mBackButton;
    private ImageView mExpressCompanyThumbnail;
    private TextView mExpressCompanyName;
    private TextView mExpressCode;
    private ListView mSearchExpressResultListView;
    private ProgressDialog mProgressDialog;

    private SearchExpressResultAdapter searchExpressResultAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.search_express_result_layout);

        getIntentExtras();

        initView();

        showProgressDialog();

        new SearchExpressTask(this) {
            @Override
            protected void onPostExecute(ExpressInfo expressInfo) {
                super.onPostExecute(expressInfo);
                Log.i(TAG, ">>Search Result");
                if (expressInfo != null) {
                    Log.i(TAG, expressInfo.toString());
                    if (searchExpressResultAdapter != null) {
                        List<LogisticsItem> itemList = Utils.parseLogisticsToList(expressInfo.getData());
                        searchExpressResultAdapter.addData(itemList);
                    }
                } else {
                    Log.e(TAG, "expressInfo is null");
                }

                hideProgressDialog();


            }
        }.execute(searchType, searchPostId);

    }

    private void getIntentExtras() {
        Intent mIntent = getIntent();
        Bundle bundle = mIntent.getExtras();

        if (bundle != null) {
            searchType = bundle.getString(SEARCH_TYPE);
            searchPostId = bundle.getString(SEARCH_POSTID);
        }
    }

    private void initView() {
        mBackButton = (ImageView) findViewById(R.id.back_btn);
        mBackButton.setVisibility(View.VISIBLE);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ((TextView) findViewById(R.id.title)).setText(R.string.express_search_result_title);

        mExpressCompanyThumbnail = (ImageView) findViewById(R.id.icon_express_company);
        mExpressCompanyName = (TextView) findViewById(R.id.express_company);
        mExpressCode = (TextView) findViewById(R.id.express_code);

        Utils.setExpressHeaderInSearchResult(this, searchType, searchPostId, mExpressCompanyThumbnail, mExpressCompanyName, mExpressCode);

        mSearchExpressResultListView = (ListView) findViewById(R.id.express_search_listview);
        searchExpressResultAdapter = new SearchExpressResultAdapter(this);

        mSearchExpressResultListView.setAdapter(searchExpressResultAdapter);
        mSearchExpressResultListView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private void showProgressDialog() {
        mProgressDialog = ProgressDialog.show(this, null, getResources().getString(R.string.loading));
        mProgressDialog.setCancelable(false);
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null) {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.dismiss();
            }
        }
    }
}
