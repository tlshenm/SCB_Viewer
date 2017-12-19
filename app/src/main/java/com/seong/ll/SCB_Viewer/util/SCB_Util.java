package com.seong.ll.SCB_Viewer.util;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * Created by P093862 on 2017-11-20.
 */

public class SCB_Util {
    public static int SCREEN_WIDTH = 480;
    public static int SCREEN_HEIGHT = 800;

    /**
     * 소프트키를 미포함한 화면 전체해상도를 가져온다.
     * @param context
     */
    public static void setScreenInfo(Context context){
        DisplayMetrics dmath=context.getResources().getDisplayMetrics();	// 화면의 가로,세로 길이를 구할 때 사용합니다.
        SCREEN_WIDTH=dmath.widthPixels;
        SCREEN_HEIGHT=dmath.heightPixels;
    }
}
