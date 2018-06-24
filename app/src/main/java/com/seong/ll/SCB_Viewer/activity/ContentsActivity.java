package com.seong.ll.SCB_Viewer.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import com.seong.ll.SCB_Viewer.R;
import com.seong.ll.SCB_Viewer.adapter.ContentRecyclerViewAdapter;
import com.seong.ll.SCB_Viewer.dummy.DummyContent;
import com.seong.ll.SCB_Viewer.util.SCB_Const;

/**
 * Created by P093862 on 2017-11-21.
 */

public class ContentsActivity extends BaseActivity {
    private FloatingActionButton mContentAddFab = null;  // Contents 추가 버튼
    private RecyclerView mContentRecycler = null;        // Recycle 뷰
    private ContentRecyclerViewAdapter mContentAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        setToolbarRegister(this,R.id.content_toolbar);
        setToolbarState(TOOLBAR_STATE.BACK);

        mContentAddFab = (FloatingActionButton) findViewById(R.id.content_add_fab);
        mContentRecycler = (RecyclerView) findViewById(R.id.content_recycler);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, SCB_Const.LIST_COLUMN_COUNT);
        mContentRecycler.setLayoutManager(gridLayoutManager);
        mContentAdapter = new ContentRecyclerViewAdapter(this,DummyContent.ITEMS);
        mContentRecycler.setAdapter(mContentAdapter);
        mContentRecycler.addOnScrollListener(new HidingScrollListener() {
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

        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mContentAddFab.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mContentAddFab.animate().translationY(mContentAddFab.getHeight()+fabBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    /**
     * 툴바를 보여준다.
     */
    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        mContentAddFab.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_right);
    }
}
