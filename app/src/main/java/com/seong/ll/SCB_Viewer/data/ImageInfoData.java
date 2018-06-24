package com.seong.ll.SCB_Viewer.data;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ImageInfoData extends FileInfoData implements Serializable {
	/**
	 * 공통코드 정보 클래스 
	 */
	private static final long serialVersionUID = 1L;

	private String id;
	private String path;
	private Bitmap bitmapImg;
	private byte[] bitmapStr;
	private boolean checkedState;

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

	public byte[] getBitmapStr() {
		return bitmapStr;
	}

	public void setBitmapStr(byte[] bitmapStr) {
		this.bitmapStr = bitmapStr;
	}

	public boolean isCheckedState() {
		return checkedState;
	}

	public void setCheckedState(boolean checkedState) {
		this.checkedState = checkedState;
	}

	public Bitmap getBitmapImg() {
		return bitmapImg;
	}

	public void setBitmapImg(Bitmap bitmapImg) {
		this.bitmapImg = bitmapImg;
	}
}
