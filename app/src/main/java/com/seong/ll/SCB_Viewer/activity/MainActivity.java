package com.seong.ll.SCB_Viewer.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.seong.ll.SCB_Viewer.adapter.FolderRecyclerViewAdapter;
import com.seong.ll.SCB_Viewer.R;
import com.seong.ll.SCB_Viewer.dummy.DummyContent;
import com.seong.ll.SCB_Viewer.util.SCB_Const;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar mToolbar = null;
    private FloatingActionButton mMainMenuFab = null;  // 메뉴 버튼
    private RecyclerView mFolderRecycler = null;        // Recycle 뷰
    private FolderRecyclerViewAdapter mFolderAdapter = null;
    private boolean isOpenFab = false;                  // 플로팅버튼 오픈 여부
    private FrameLayout mFlFabOpen = null;             // 플로팅 버튼 오픈 후 배경 컴컴하게

    private LinearLayout mContentAddFab = null;
    private LinearLayout mFolderEditFab = null;
    private LinearLayout mKeepViewFab = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //백그라운드로 넘길시 화면이 보이지 않게 된다.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        mToolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mMainMenuFab = (FloatingActionButton) findViewById(R.id.main_menu_fab);
        mFolderRecycler = (RecyclerView) findViewById(R.id.folder_recycler);
        mFlFabOpen = (FrameLayout)findViewById(R.id.folder_open_fab_fl);

        mContentAddFab =  (LinearLayout)findViewById(R.id.open_add_ll);
        mFolderEditFab = (LinearLayout) findViewById(R.id.open_edit_ll);
        mKeepViewFab = (LinearLayout) findViewById(R.id.open_keep_view_ll);

        mFlFabOpen.setOnClickListener(this);
        mMainMenuFab.setOnClickListener(this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, SCB_Const.FOLDER_COLUMN_COUNT);
        mFolderRecycler.setLayoutManager(gridLayoutManager);
        mFolderAdapter = new FolderRecyclerViewAdapter(this, DummyContent.ITEMS);
        mFolderRecycler.setAdapter(mFolderAdapter);
        mFolderRecycler.addOnScrollListener(new HidingScrollListener() {
            @Override
            public void onHide() {
                hideViews();
            }

            @Override
            public void onShow() {
                showViews();
            }
        });
    }

    /**
     * 툴바를 숨긴다.
     */
    private void hideViews() {
        mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mMainMenuFab.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mMainMenuFab.animate().translationY(mMainMenuFab.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    /**
     * 툴바를 보여준다.
     */
    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        mMainMenuFab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }
                               
    /**
     * 플로팅 버튼 On/Off 애니메이션 및 동작 설정
     */
    private void fabOnOffAnimation() {
        Animation rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);    // 플로팅 버튼 회전
        Animation rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);  // 플로팅 버튼 회전
        Animation visible_alpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.visible_alpha);      // 페이지 투명도
        Animation gone_alpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.gone_alpha);            // 페이지 투명도

        Animation fab_open= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);            // 페이지 투명도
        Animation fab_close= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);            // 페이지 투명도



        if (isOpenFab) {
            isOpenFab = false;
            mMainMenuFab.startAnimation(rotate_backward);   // 메뉴버튼 X로

            mContentAddFab.startAnimation(fab_close);       //
            mFolderEditFab.startAnimation(fab_close);
            mKeepViewFab.startAnimation(fab_close);

            mFlFabOpen.startAnimation(gone_alpha);
            mFlFabOpen.setVisibility(View.GONE);
        } else {
            isOpenFab = true;
            mMainMenuFab.startAnimation(rotate_forward);    // 메뉴버튼 +로

            mContentAddFab.startAnimation(fab_open);
            mFolderEditFab.startAnimation(fab_open);
            mKeepViewFab.startAnimation(fab_open);

            mFlFabOpen.startAnimation(visible_alpha);
            mFlFabOpen.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
//        if (hasFocus == true) {
//            Toast.makeText(MainActivity.this, "화면보여짐", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(MainActivity.this, "화면안보임", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_menu_fab:
                fabOnOffAnimation();
                break;
            case R.id.folder_open_fab_fl:
                fabOnOffAnimation();
                break;
        }
    }
}
