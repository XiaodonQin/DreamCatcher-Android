/**
 * Copyright (c) 2012 Conversant Solutions. All rights reserved.
 *
 * Created on 2015/8/10.
 */
package com.xiaodong.dream.catcher.demo.express.search;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.xiaodong.dream.catcher.demo.R;

/**
 * TODO
 *
 * @author Xiaodong
 */
public class SearchExpressFragment extends Fragment implements View.OnClickListener{
    private static String TAG = "SearchExpressFragment";

    private View mRootView;

    private Activity mContext;

    private EditText mInputExpressCodeEt;
    private EditText mInputExpressCompanyEt;
    private Button mSearchExpressButton;

    private OnSearchRecordListener onSearchRecordListener;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.express_search_layout, null);

        initView();

        return mRootView;
    }

    private void initView(){
        mInputExpressCodeEt = (EditText) mRootView.findViewById(R.id.input_express_code_et);
        mInputExpressCompanyEt = (EditText) mRootView.findViewById(R.id.input_express_company_et);
        mSearchExpressButton = (Button) mRootView.findViewById(R.id.express_search_button);

        mInputExpressCompanyEt.setText(R.string.express_company_sfsy);
        mSearchExpressButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.express_search_button:
                if(checkIsInput()){
                    InputMethodManager in = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(mInputExpressCodeEt.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                    String postId = mInputExpressCodeEt.getText().toString();
                    String type = "shunfeng";

                    onSearchRecordListener.onRecord(postId, type);

                    Intent mIntent = new Intent(getActivity(), SearchExpressResultActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString(SearchExpressResultActivity.SEARCH_TYPE, "shunfeng");
                    mBundle.putString(SearchExpressResultActivity.SEARCH_POSTID, postId);
                    mIntent.putExtras(mBundle);
                    startActivity(mIntent);
                }else {
                    Toast.makeText(getActivity(), R.string.prompt_input_express_code_company, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private boolean checkIsInput(){
        if (mInputExpressCodeEt != null && mInputExpressCompanyEt != null
                && !mInputExpressCodeEt.getText().toString().equals("")
                && !mInputExpressCompanyEt.getText().toString().equals("")){
            return true;
        }else {
            return false;
        }
    }


}
