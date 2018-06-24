package com.seong.ll.SCB_Viewer.util;

import com.seong.ll.SCB_Viewer.R;

/**
 * 상수모음
 * Created by P093862 on 2017-11-20.
 */

public class SCB_Const {
    public static final int LIST_COLUMN_COUNT = 4;                   // List Col 개수

    /**
     * 폴더 정렬
     */
    public enum FOLDER_SORT{
        CREATE,   // 폴더 만들어진 순
        ABC,        // 가나다 순
        ABC_REVERSE // 가나다 역순
    }

    public enum CONTENT_SORT{
        NAME,       // 가나다 순
        CONTINUE,   // 이어보기 순
        NEW         // NEW
    }

    public enum DISPLAY_MODE{
        ADD,         // 추가
        NAME_EDIT,   // 이름변경
        DELETE       // 삭제
    }

}
