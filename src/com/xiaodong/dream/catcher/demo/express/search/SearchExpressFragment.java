/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/10.
 */
package com.xiaodong.dream.catcher.demo.express.search;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import com.xiaodong.dream.catcher.demo.R;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class SearchExpressFragment extends Fragment implements View.OnClickListener{
    private static String TAG = "SearchExpressFragment";

    private View mRootView;

    private EditText mInputExpressCodeEt;
    private EditText mInputExpressCompanyEt;
    private Button mSearchExpressButton;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, ">>onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(TAG, ">>onCreateView");

        mRootView = inflater.inflate(R.layout.express_search_layout, null);

        initView();

        return mRootView;
    }

    private void initView(){
        mInputExpressCodeEt = (EditText) mRootView.findViewById(R.id.input_express_code_et);
        mInputExpressCompanyEt = (EditText) mRootView.findViewById(R.id.input_express_company_et);
        mSearchExpressButton = (Button) mRootView.findViewById(R.id.express_search_button);

        mInputExpressCompanyEt.setOnClickListener(this);
        mSearchExpressButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.input_express_company_et:
                mInputExpressCompanyEt.setText("shunfeng");

                break;

            case R.id.express_search_button:
                if(checkIsInput()){
                    Intent mIntent = new Intent(getActivity(), SearchExpressResultActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString(SearchExpressResultActivity.SEARCH_TYPE, mInputExpressCompanyEt.getText().toString());
                    mBundle.putString(SearchExpressResultActivity.SEARCH_POSTID, mInputExpressCodeEt.getText().toString());
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);
                }
                break;
        }
    }

    private boolean checkIsInput(){
        if (mInputExpressCodeEt != null && mInputExpressCompanyEt != null
                && mInputExpressCodeEt.getText().toString() != null
                && mInputExpressCompanyEt.getText().toString() != null){
            return true;
        }else {
            return false;
        }
    }


}
