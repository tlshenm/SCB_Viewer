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

	public boolean isCheckedState() {
		return checkedState;
	}

	public void setCheckedState(boolean checkedState) {
		this.checkedState = checkedState;
	}

}
