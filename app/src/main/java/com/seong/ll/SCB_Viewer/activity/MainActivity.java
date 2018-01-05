package com.seong.ll.SCB_Viewer.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.seong.ll.SCB_Viewer.adapter.FolderRecyclerViewAdapter;
import com.seong.ll.SCB_Viewer.R;
import com.seong.ll.SCB_Viewer.dummy.DummyContent;
import com.seong.ll.SCB_Viewer.util.SCB_Const;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private FloatingActionButton mMainMenuFab = null;  // 메뉴 버튼
    private RecyclerView mFolderRecycler = null;        // Recycle 뷰
    private FolderRecyclerViewAdapter mFolderAdapter = null;
    private boolean isOpenFab = false;                  // 플로팅버튼 오픈 여부
    private boolean isProcessAni = false;               // 애니메이션 실행중 여부
    private FrameLayout mFlFabOpen = null;              // 플로팅 버튼 오픈 후 배경 컴컴하게


    private LinearLayout mContentAddFab = null;
    private LinearLayout mFolderEditFab = null;
    private LinearLayout mKeepViewFab = null;
    private Button mContinueViewBtn = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        mBinding =  DataBindingUtil.setContentView(this, R.layout.activity_main);
        setContentView(R.layout.activity_main);

        //백그라운드로 넘길시 화면이 보이지 않게 된다.
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        initView();
    }

    private void initView() {
        setToolbarRegister(this,R.id.main_toolbar);
        setToolbarState(TOOLBAR_STATE.NORMAL);
        mMainMenuFab = (FloatingActionButton) findViewById(R.id.main_menu_fab);
        mFolderRecycler = (RecyclerView) findViewById(R.id.folder_recycler);
        mFlFabOpen = (FrameLayout)findViewById(R.id.folder_open_fab_fl);

        mContentAddFab =  (LinearLayout)findViewById(R.id.open_add_ll);
        mFolderEditFab = (LinearLayout) findViewById(R.id.open_edit_ll);
//        mKeepViewFab = (LinearLayout) findViewById(R.id.open_keep_view_ll);

        mContinueViewBtn = (Button)findViewById(R.id.bottom_btn);

        mFolderAdapter = new FolderRecyclerViewAdapter(this, DummyContent.ITEMS);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, SCB_Const.FOLDER_COLUMN_COUNT);
        mFolderRecycler.setLayoutManager(gridLayoutManager);

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
        mFlFabOpen.setOnClickListener(this);
        mMainMenuFab.setOnClickListener(this);
        mContentAddFab.setOnClickListener(this);
        mFolderEditFab.setOnClickListener(this);
        mContinueViewBtn.setOnClickListener(this);
    }


    /**
     * 툴바를 숨긴다.
     */
    private void hideViews() {
        mToolbar.animate().translationY(-mToolbar.getHeight()).setInterpolator(new AccelerateInterpolator(2));
        FrameLayout.LayoutParams mainMenuFabFL = (FrameLayout.LayoutParams) mMainMenuFab.getLayoutParams();
        FrameLayout.LayoutParams continueViewBtnFL = (FrameLayout.LayoutParams) mContinueViewBtn.getLayoutParams();


        int fabBottomMargin = mainMenuFabFL.bottomMargin;
        int btnBottomMargin = continueViewBtnFL.bottomMargin;

        mMainMenuFab.animate().translationY(mMainMenuFab.getHeight() + fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
        mContinueViewBtn.animate().translationY(mContinueViewBtn.getHeight() + btnBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    /**
     * 툴바를 보여준다.
     */
    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        mMainMenuFab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
        mContinueViewBtn.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }
                               
    /**
     * 플로팅 버튼 On/Off 애니메이션 및 동작 설정
     */
    private void fabOnOffAnimation(boolean isOpen) {
        isProcessAni = true;
        Animation rotate_forward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_forward);    // 플로팅 버튼 회전
        Animation rotate_backward = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotate_backward);  // 플로팅 버튼 회전
        Animation visible_alpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.visible_alpha);      // 페이지 투명도
        Animation gone_alpha = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.gone_alpha);            // 페이지 투명도
        Animation fab_open= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);                 // 플로팅 버튼 투명도
        Animation fab_close= AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);               // 플로팅 버튼 투명도

        if (isOpen) {
            isOpenFab = false;
            mMainMenuFab.startAnimation(rotate_backward);   // 메뉴버튼 X로

            mContentAddFab.startAnimation(fab_close);       //
            mFolderEditFab.startAnimation(fab_close);
//            mKeepViewFab.startAnimation(fab_close);

            mFlFabOpen.startAnimation(gone_alpha);
            mFlFabOpen.setVisibility(View.GONE);
        } else {
            isOpenFab = true;
            mMainMenuFab.startAnimation(rotate_forward);    // 메뉴버튼 +로

            mContentAddFab.startAnimation(fab_open);
            mFolderEditFab.startAnimation(fab_open);
//            mKeepViewFab.startAnimation(fab_open);

            mFlFabOpen.startAnimation(visible_alpha);
            mFlFabOpen.setVisibility(View.VISIBLE);
        }

        // mFlFabOpen 클릭이벤트가 연속으로 눌리면 UI동작이 이상해지는 오류로인한 딜레이 적용
        new Handler().postDelayed(new Runnable() {
            public void run() {
                isProcessAni =false;
            }
        }, 250);

    }


    private void showSortPopUp() {
        // 저장된 정렬방식 조회

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        final CharSequence[] items = getResources().getStringArray(R.array.folder_sort_array);
        dialog.setTitle(getResources().getString(R.string.sort));

        dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        //Dao 연결    CREATE,     폴더 만들어진 순
                        break;
                    case 1:
                        //ABC,        // 가나다 순
                        break;
                    case 2:
                        //ABC_REVERSE // 가나다 역순
                        break;
                }
                dialog.cancel();
            }
        });
        dialog.show();
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
        switch (id) {
            case R.id.action_settings:
                Intent intent = new Intent(this,SettingActivity.class);
                startActivity(intent);
                break;
            case R.id.action_sort:
                    showSortPopUp();
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_menu_fab:
                fabOnOffAnimation(isOpenFab);
                break;
            case R.id.bottom_btn:

                break;
            case R.id.open_add_ll:
                // 추가하기
                break;
            case R.id.open_edit_ll:
                //폴더편집
                break;
            case R.id.folder_open_fab_fl:
                if(isProcessAni) {
                    return;
                }
                fabOnOffAnimation(isOpenFab);
                break;

        }
    }

}
