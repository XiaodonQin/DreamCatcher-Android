package com.xiaodong.dream.catcher.demo.categories.home;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.xiaodong.dream.catcher.demo.Constants;
import com.xiaodong.dream.catcher.demo.MyFragment;
import com.xiaodong.dream.catcher.demo.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiaodong on 2015/8/21.
 */
public class MainHomeFragment extends MyFragment{
    private static String FRAGMENT_MAIN_HOME_TAG = "MainHomeFragment";

    private static Activity mActivity;
    private OnSetMainTitleListener onSetMainTitleListener;

    private RecyclerView mRecyclerView;
    private AppsRecyclerViewAdapter mRecyclerViewAdapter;

    private static List<AppContent> appContentList;

    private FloatingActionButton mFabButton;

    private View dialogView;
    private View mRootView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();

        onSetMainTitleListener = (OnSetMainTitleListener) mActivity;

        onSetMainTitleListener.onSetMainTitle(R.string.drawer_item_home);

        Constants constants = Constants.getInstance();

        appContentList = constants.appContentList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_home, null, false);

        if(mRootView.getId() == R.id.cardListView){
            mRecyclerView = (RecyclerView) mRootView;
        }else {
            mRecyclerView = (RecyclerView) mRootView.findViewById(R.id.cardListView);
        }

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerViewAdapter = new AppsRecyclerViewAdapter(MainHomeFragment.this);
        mRecyclerView.setAdapter(mRecyclerViewAdapter);

        generateAboutThisAppSection();

        // Fab Button
        mFabButton = (FloatingActionButton) mRootView.findViewById(R.id.fab_home);
        mFabButton.setImageDrawable(new IconicsDrawable(mActivity, GoogleMaterial.Icon.gmd_add).color(Color.WHITE).actionBar());
        mFabButton.setOnClickListener(fabClickListener);

        return mRootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mRecyclerViewAdapter.addContent(appContentList);
        //set animation
        LayoutAnimationController layoutAnimationController;

        Animation fadeIn = AnimationUtils.loadAnimation(mActivity, android.R.anim.slide_in_left);
        fadeIn.setDuration(500);
        layoutAnimationController = new LayoutAnimationController(fadeIn);

        mRecyclerView.setLayoutAnimation(layoutAnimationController);
        mRecyclerView.startLayoutAnimation();

        super.onViewCreated(view, savedInstanceState);
    }

    private void generateAboutThisAppSection(){
        //get the packageManager to load and read some values :D
        PackageManager pm = getActivity().getPackageManager();
        //get the packageName
        String packageName = getActivity().getPackageName();
        //Try to load the applicationInfo
        ApplicationInfo appInfo = null;
        PackageInfo packageInfo = null;
        try {
            appInfo = pm.getApplicationInfo(packageName, 0);
            packageInfo = pm.getPackageInfo(packageName, 0);
        } catch (Exception ex) {
        }

        //Set the Icon or hide it
        Drawable icon = null;
        if (appInfo != null) {
            icon = appInfo.loadIcon(pm);
        }

        //set the Version or hide it
        String versionName = null;
        Integer versionCode = null;
        if (packageInfo != null) {
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;
        }

        //add this cool thing to the headerView of our listView
        mRecyclerViewAdapter.setHeader(versionName, versionCode, icon);
    }

    /**
     * sample onClickListener with to-top as action
     */
    View.OnClickListener fabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
//            AlertDialog.Builder alert = new AlertDialog.Builder(mActivity);
//            alert.setMessage("FloatingActionButton (FAB)");
//            alert.create().show();
            setupDialog();

            AlertDialog.Builder builder =
                    new AlertDialog.Builder(mActivity, R.style.AppCompatAlertDialogStyle);
            builder.setTitle(getString(R.string.dialog_add_item_title));
            builder.setView(dialogView);
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (!isEmpty()) {

                        String appNameText = ((EditText) dialogView.findViewById(R.id.app_name_input))
                                .getText().toString().trim();
                        String appAuthorText = ((EditText) dialogView.findViewById(R.id.app_author_input))
                                .getText().toString().trim();
                        String appDescriptionText = ((EditText) dialogView.findViewById(R.id.app_description_input))
                                .getText().toString().trim();
                        String appVersionText = ((EditText) dialogView.findViewById(R.id.app_version_input))
                                .getText().toString().trim();
                        addItem(appNameText, appAuthorText, appDescriptionText, appVersionText);
                        Snackbar.make(mRootView.findViewById(R.id.fragment_home_layout),
                                getString(R.string.text_success), Snackbar.LENGTH_LONG)
                                .setAction(R.string.text_undo, onUndoClickListener)
                                .show();
                    }
                }
            });//second parameter used for onclicklistener
            builder.setNegativeButton("Cancel", null);
            //Show dialog and launch keyboard
            builder.show().getWindow().setSoftInputMode(WindowManager.LayoutParams
                    .SOFT_INPUT_STATE_ALWAYS_VISIBLE);


        }
    };

    private void setupDialog(){
        dialogView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_layout,null,false);

        final TextInputLayout appNameInputLayout = (TextInputLayout)dialogView.findViewById(R.id.app_name_input_layout);
        final TextInputLayout appAuthorInputLayout = (TextInputLayout)dialogView.findViewById(R.id.app_author_input_layout);
        final TextInputLayout appDescriptionInputLayout = (TextInputLayout)dialogView.findViewById(R.id.app_description_input_layout);
        final TextInputLayout appVersionInputLayout = (TextInputLayout)dialogView.findViewById(R.id.app_version_input_layout);


        appNameInputLayout.setErrorEnabled(true);
        appAuthorInputLayout.setErrorEnabled(true);
        appDescriptionInputLayout.setErrorEnabled(true);
        appVersionInputLayout.setErrorEnabled(true);


        EditText appNameInput = (EditText)dialogView.findViewById(R.id.app_name_input);
        EditText appAuthorInput = (EditText)dialogView.findViewById(R.id.app_author_input);
        EditText appDescriptionInput = (EditText)dialogView.findViewById(R.id.app_description_input);
        EditText appVersionInput = (EditText)dialogView.findViewById(R.id.app_version_input);


        appNameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence==null||charSequence.toString().equals("")){
                    appNameInputLayout.setError(getString(R.string.edittext_error));
                }else{
                    appNameInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        appAuthorInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence==null||charSequence.toString().equals("")){
                    appAuthorInputLayout.setError(getString(R.string.edittext_error));
                }else{
                    appAuthorInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        appDescriptionInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence==null||charSequence.toString().equals("")){
                    appDescriptionInputLayout.setError(getString(R.string.edittext_error));
                }else{
                    appDescriptionInputLayout.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void addItem(String appName, String appAuthor, String appDescription, String appVersion){
        AppContent appContent = new AppContent();
        appContent.setAppName(appName);
        appContent.setAuthor(appAuthor);
        appContent.setDescription(appDescription);
        appContent.setVersion(appVersion);

        if (mRecyclerViewAdapter != null)
            mRecyclerViewAdapter.addItem(appContent);

    }

    private void removeLastItem(){
        if (mRecyclerViewAdapter != null)
            mRecyclerViewAdapter.removeLastItem();
    }

    private void deleteItem(int appContentId){
        if (mRecyclerViewAdapter != null)
            mRecyclerViewAdapter.deleteItem(appContentId);
    }

    View.OnClickListener onUndoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            removeLastItem();
        }
    };

    private boolean isEmpty(){
        String appNameText = ((EditText)dialogView.findViewById(R.id.app_name_input))
                .getText().toString().trim();
        String appAuthorText = ((EditText)dialogView.findViewById(R.id.app_author_input))
                .getText().toString().trim();
        String appDescriptionText = ((EditText)dialogView.findViewById(R.id.app_description_input))
                .getText().toString().trim();

        if((appNameText==null||appNameText.equals(""))&&
                (appAuthorText==null||appAuthorText.equals(""))&&
                (appDescriptionText==null||appDescriptionText.equals(""))){
            Snackbar.make(mRootView.findViewById(R.id.fragment_home_layout),
                    getString(R.string.name_author_description_error),Snackbar.LENGTH_LONG).show();
            return true;
        }else if ((appNameText!=null && !appNameText.equals(""))&&
                (appAuthorText==null||appAuthorText.equals(""))&&
                (appDescriptionText==null||appDescriptionText.equals(""))){
            Snackbar.make(mRootView.findViewById(R.id.fragment_home_layout),
                    getString(R.string.author_description_error),Snackbar.LENGTH_LONG).show();
            return true;
        }else if ((appNameText==null||appNameText.equals(""))&&
                (appAuthorText!=null && !appAuthorText.equals(""))&&
                (appDescriptionText==null||appDescriptionText.equals(""))){
            Snackbar.make(mRootView.findViewById(R.id.fragment_home_layout),
                    getString(R.string.name_description_error),Snackbar.LENGTH_LONG).show();
            return true;
        }else if ((appNameText==null||appNameText.equals(""))&&
                (appAuthorText==null||appAuthorText.equals(""))&&
                (appDescriptionText!=null && !appDescriptionText.equals(""))){
            Snackbar.make(mRootView.findViewById(R.id.fragment_home_layout),
                    getString(R.string.name_author_error),Snackbar.LENGTH_LONG).show();
            return true;
        }else if(appNameText==null||appNameText.equals("")){
            Snackbar.make(mRootView.findViewById(R.id.fragment_home_layout),
                    getString(R.string.name_error),Snackbar.LENGTH_LONG).show();
            return true;
        }else if(appAuthorText==null||appAuthorText.equals("")){
            Snackbar.make(mRootView.findViewById(R.id.fragment_home_layout),
                    getString(R.string.author_error),Snackbar.LENGTH_LONG).show();
            return true;
        }else if(appDescriptionText==null||appDescriptionText.equals("")){
            Snackbar.make(mRootView.findViewById(R.id.fragment_home_layout),
                    getString(R.string.description_error),Snackbar.LENGTH_LONG).show();
            return true;
        }
        return false;
    }

    public void showDeleteItemDialog(final int appContentId){
        AlertDialog.Builder builder =
                new AlertDialog.Builder(mActivity, R.style.AppCompatAlertDialogStyle);
        builder.setTitle(getString(R.string.dialog_delete_item_title));
        builder.setMessage(getString(R.string.dialog_delete_item_message));
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteItem(appContentId);
            }
        });//second parameter used for onclicklistener
        builder.setNegativeButton("Cancel", null);
        //Show dialog and launch keyboard
        builder.show().getWindow().setSoftInputMode(WindowManager.LayoutParams
                .SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();

        if (onSetMainTitleListener != null)
            onSetMainTitleListener.onSetMainTitle(R.string.drawer_item_home);
    }
}
