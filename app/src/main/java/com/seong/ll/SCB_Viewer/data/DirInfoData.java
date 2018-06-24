package com.seong.ll.SCB_Viewer.data;

import java.util.ArrayList;

public class DirInfoData {

    private int dirId;
    private String name;                      // 이름
    private String path;                      // 경로
    private String representativeImagePath;     // 대표이미지 경로
    private long date;                        // 날짜
    private boolean isCheck;                  // 선택여부
    private boolean isLock;                   // 디렉토리 잠금 여부
    private boolean isSee;                    // 디렉토리 본 여부
    private long lastSeeDate;                 // 마지막으로 본 날짜
    private int savePosition;                 //저장된 페이지

    private int mTotalPage = 0;                     // 총페이지

    private ArrayList<ImageInfoData> imageInfoList = null;  //이미지 목록

    public DirInfoData() {
        imageInfoList = new ArrayList<ImageInfoData>();
    }

    public ArrayList<ImageInfoData> getImageInfoList() {
        return imageInfoList;
    }


    public void setImageInfoList(ArrayList<ImageInfoData> imageInfoList) {
        this.imageInfoList = imageInfoList;
    }

    public int getDirId() {
        return dirId;
    }

    public void setDirId(int dirId) {
        this.dirId = dirId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRepresentativeImagePath() {
        return representativeImagePath;
    }

    public void setRepresentativeImagePath(String representativeImagePath) {
        this.representativeImagePath = representativeImagePath;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public boolean isLock() {
        return isLock;
    }

    public void setLock(boolean lock) {
        isLock = lock;
    }

    public boolean isSee() {
        return isSee;
    }

    public void setSee(boolean see) {
        isSee = see;
    }

    public long getLastSeeDate() {
        return lastSeeDate;
    }

    public void setLastSeeDate(long lastSeeDate) {
        this.lastSeeDate = lastSeeDate;
    }

    public int getSavePosition() {
        return savePosition;
    }

    public void setSavePosition(int savePosition) {
        this.savePosition = savePosition;
    }

    public int getmTotalPage() {
        return mTotalPage;
    }

    public void setmTotalPage(int mTotalPage) {
        this.mTotalPage = mTotalPage;
    }
}
