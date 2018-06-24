package com.seong.ll.SCB_Viewer.activity;

import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;

import com.seong.ll.SCB_Viewer.R;
import com.seong.ll.SCB_Viewer.adapter.InsertRecyclerViewAdapter;
import com.seong.ll.SCB_Viewer.data.ImageInfoData;
import com.seong.ll.SCB_Viewer.dummy.DummyContent;
import com.seong.ll.SCB_Viewer.util.SCB_Const;

import java.util.ArrayList;

public class InsertActivity extends BaseActivity {

    private Context mContext = null;
    private Button mBtnInsert = null;
    private RecyclerView mInsertRecycler = null;
    private InsertRecyclerViewAdapter mInsertAdapter = null;

    private ArrayList<ImageInfoData> mImageInfoList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        mContext = this;
        mImageInfoList = new ArrayList<ImageInfoData>();
        mBtnInsert = (Button) findViewById(R.id.btn_insert);
        mInsertRecycler = (RecyclerView) findViewById(R.id.insert_recycler);
        mInsertAdapter = new InsertRecyclerViewAdapter(this, DummyContent.ITEMS);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, SCB_Const.LIST_COLUMN_COUNT);
        mInsertRecycler.setLayoutManager(gridLayoutManager);

        mInsertRecycler.setAdapter(mInsertAdapter);

        mInsertRecycler.addOnScrollListener(new HidingScrollListener() {
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
        FrameLayout.LayoutParams continueViewBtnFL = (FrameLayout.LayoutParams) mBtnInsert.getLayoutParams();

        int btnBottomMargin = continueViewBtnFL.bottomMargin;
        mBtnInsert.animate().translationY(mBtnInsert.getHeight() + btnBottomMargin).setInterpolator(new AccelerateInterpolator(2)).start();
    }

    /**
     * 툴바를 보여준다.
     */
    private void showViews() {
        mToolbar.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2));
        mBtnInsert.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    private class FindStorageSavedImage extends AsyncTask<Void, Void, Void> {

        private Cursor imageCursor = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog(mContext);
        }

        @Override
        protected synchronized Void doInBackground(Void... params) {

            findThumbList();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {


            hideProgressDialog();
        }

        private long findThumbList()
        {
            long returnValue = 0;

            // Select 하고자 하는 컬럼
            String[] projection = { MediaStore.Images.Media._ID, MediaStore.Images.Media.DATA };

            // 쿼리 수행
            imageCursor = managedQuery(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, MediaStore.Images.Media.DATE_ADDED + " desc ");

            if (imageCursor != null && imageCursor.getCount() > 0)
            {
                // 컬럼 인덱스
                int imageIDCol = imageCursor.getColumnIndex(MediaStore.Images.Media._ID);
                int imageDataCol = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
                String imageNameCol = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE)); // 파일이름


                // 커서에서 이미지의 ID와 경로명을 가져와서 ThumbImageInfo 모델 클래스를 생성해서
                // 리스트에 더해준다.
                while (imageCursor.moveToNext())
                {
                    String path = imageCursor.getString(imageDataCol);

                    ImageInfoData mImageListData = new ImageInfoData();
                    mImageListData.setPath(path);
                    mImageListData.setCheckedState(false);

                    mImageInfoList.add(mImageListData);
                    returnValue++;
                }
            }

            return returnValue;
        }
    }


}
