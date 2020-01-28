package com.seong.ll.SCB_Viewer.data;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ZipInfoData extends FileInfoData implements Serializable {
	/**
	 * 공통코드 정보 클래스 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String path;
	private String representativeImagePath;   // 대표이미지 경로
	private long date;                        // 날짜
	private boolean isCheck;                  // 선택여부
	private boolean isLock;                   // 디렉토리 잠금 여부
	private boolean isSee;                    // 디렉토리 본 여부
	private long lastSeeDate;                 // 마지막으로 본 날짜
	private int savePosition;                 // 저장된 페이지
	private int totalPage = 0;                // 총페이지
	private boolean isAdded;                  // 추가여부

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public boolean isAdded() {
		return isAdded;
	}

	public void setAdded(boolean added) {
		isAdded = added;
	}
}
