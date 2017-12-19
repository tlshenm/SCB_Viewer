package com.seong.ll.SCB_Viewer.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;

import com.seong.ll.SCB_Viewer.R;


/**
 * Created by P092613 on 2017-11-17.
 */

public abstract class  BaseActivity extends AppCompatActivity {

    protected ProgressDialog mProgressDialog = null;
    protected Toolbar mToolbar = null;                                      // 상단 툴바
    protected DrawerLayout mDrawerLayout = null;                            // DrawerLayout
    protected ActionBarDrawerToggle mActionBarDrawerToggle = null;          // 툴바 ActionDrawerToggle

    protected abstract Context getCurrentContext();


    /**
     * 프로그래스를 보여준다.
     */
    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getCurrentContext());
            mProgressDialog.setMessage(getString(R.string.progress_message));
            mProgressDialog.setIndeterminate(true);
            mProgressDialog.setCancelable(false);
        }
        mProgressDialog.show();
    }

    /**
     * 프로그래스를 숨긴다.
     */
    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /**
     * 좌측 메뉴 설정
     *
     * @param activity          : 액티비티
     * @param drawerLayoutResId : DrawerLayout 리소스 ID
     * @param toolbarResId      : Toolber 리소스 ID
     */
    protected void setToolbarLeftMenu(Activity activity, int drawerLayoutResId, int toolbarResId) {
        // toolbar 객체화
        mToolbar = (Toolbar) activity.findViewById(toolbarResId);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // DrawerLayout 설정
        mDrawerLayout = (DrawerLayout) activity.findViewById(drawerLayoutResId);

        // 상단 툴바를 이용하여 좌측 메뉴 열기 / 닫기 설정
        mActionBarDrawerToggle = new ActionBarDrawerToggle(activity, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // Drawer Toggle Object Made
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle); // Drawer Listener set to the Drawer toggle
        mActionBarDrawerToggle.syncState();
    }

    /**
     * 좌측메뉴, 좌측슬라이드 보이고 노출되도록 설정
     */
    protected void setToolbarVisibleLeftMenu() {
        if (mToolbar != null && mDrawerLayout != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            // 상단 툴바를 이용하여 좌측 메뉴 열기 / 닫기 설정
            mActionBarDrawerToggle = new ActionBarDrawerToggle((Activity) getCurrentContext(), mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            // Drawer Toggle Object Made
            mDrawerLayout.setDrawerListener(mActionBarDrawerToggle); // Drawer Listener set to the Drawer toggle
            mActionBarDrawerToggle.syncState();
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    /**
     * 좌측메뉴, 좌측슬라이드 보이고 노출되지않도록 설정
     */
    protected void setToolbarGoneLeftMenu() {
        if (mToolbar != null && mDrawerLayout != null) {
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            getSupportActionBar().setDisplayShowHomeEnabled(false);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }
    }


    /**
     * 좌측 Back 버튼 설정
     *
     * @param activity     : 액티비티
     * @param toolbarResId : 툴바 리소스
     */
    protected void setToolbarLeftBack(Activity activity, int toolbarResId) {
        // toolbar 객체화
        mToolbar = (Toolbar) activity.findViewById(toolbarResId);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 툴바 타이틀
     *
     * @param title
     */
    protected void setToolbarTitle(String title) {
        if (mToolbar != null) {
            getSupportActionBar().setTitle(title);
        }
    }


    /**
     * 툴바 서브타이틀
     *
     * @param title
     */
    protected void setToolbarSubTitle(String title) {
        if (mToolbar != null) {
            getSupportActionBar().setSubtitle(title);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}
