package com.seong.ll.SCB_Viewer.data;

import java.io.Serializable;

public class FileInfoData implements Serializable {
    public enum FILE_TYPE {
        IMAGE, ZIP
    }
    private FILE_TYPE fileType;

    public FILE_TYPE getFileType() {
        return fileType;
    }

    public void setFileType(FILE_TYPE fileType) {
        this.fileType = fileType;
    }

}
