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
import com.seong.ll.SCB_Viewer.data.DirInfoData;
import com.seong.ll.SCB_Viewer.data.FileInfoData;
import com.seong.ll.SCB_Viewer.data.ImageInfoData;
import com.seong.ll.SCB_Viewer.dummy.DummyContent;
import com.seong.ll.SCB_Viewer.util.SCB_Const;

import java.io.File;
import java.util.ArrayList;

public class InsertActivity extends BaseActivity {

    private Context mContext = null;
    private Button mBtnInsert = null;
    private RecyclerView mInsertRecycler = null;
    private InsertRecyclerViewAdapter mInsertAdapter = null;

    private ArrayList<FileInfoData> mAllFilesList = null;   // 모든 파일리스트

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        mContext = this;
        mAllFilesList = new ArrayList<FileInfoData>();
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

        private void findThumbList()
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
                int imageNameCol = imageCursor.getColumnIndex(MediaStore.Files.FileColumns.TITLE); // 파일이름


                // 커서에서 이미지의 ID와 경로명을 가져와서 ThumbImageInfo 모델 클래스를 생성해서
                // 리스트에 더해준다.
                while (imageCursor.moveToNext())
                {
                    String name = imageCursor.getString(imageNameCol);
                    String path = imageCursor.getString(imageDataCol);
                    String fileCheckString = path.substring(0, path.lastIndexOf('/')); // FIX_DIR_PATH경로에 디렉토리가 아닌 이미지파일이 들어가있는지 확인하기 위해

                    if (!new File(path).exists() || fileCheckString.contains(FIX_PATH)) // 기본폴더 경로는 제외한다.
                    {
                        continue;
                    }
                    String directoryPath = new File(path).getParent(); // 디렉토리 경로
//                    if (dbDirAddedCheck(directoryPath)) //DB에 데이터가 있는지 체크
//                    {
//                        continue;
//                    }
                    putImageToParentDirectories(name, directoryPath, path); // 디렉토리에 이미지를 넣어준다.
                }
            }
        }

        /**
         * 해당 parentPath 폴더 경로에 사진삽입
         *
         * @param parentPath
         * @param path
         */
        private void putImageToParentDirectories(String imageName, String parentPath, String path) {

            DirInfoData dirData = isDirPath(parentPath); // 디렉토리 경로가 같을경우 dirData ,다른 경우 null
            if (dirData == null) {

                dirData = new DirData();
                dirData.setName(new File(parentPath).getName());    // 디렉토리 파일이름
                dirData.setPath(parentPath);                        // 디렉토리 경로
                dirData.setDate(0);                                 // 디렉토리 날짜
                dirData.setLastSeeDate(0);                          // 마지막으로 본 날짜
                dirData.setLock(false);                             // 디렉토리 잠금 여부
                dirData.setSee(false);                              // 디렉토리 본 여부
                dirData.setSavePosition(0);                         // 해당 디렉토리의 이어보기 포지션
                dirData.setCheck(false);

                dirData.addImage(imageName, path);
                dirData.setTotalPage(new File(parentPath).listFiles().length);
                mAllFilesList.add(dirData)
            }
        }

        /**
         * 가져온 이미지파일 디렉토리경로와 현재 디렉토리 경로와 같은지
         * @param fileParentPath    이미지파일의 부모경로(이미지파일의 부모경로는 보통 폴더)
         * @return
         */
        private DirInfoData isDirPath(String fileParentPath) {
            for (FileInfoData file : mAllFilesList) {
                if (file instanceof DirInfoData) {
                    DirInfoData dirData = (DirInfoData) file;
                    if (dirData.getPath().equalsIgnoreCase(fileParentPath)) {
                        return dirData;
                    }
                }
            }
            return null;
        }
    }


}
