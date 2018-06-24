package com.seong.ll.SCB_Viewer.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.seong.ll.SCB_Viewer.R;

import java.util.ArrayList;

public class PermissionActivity extends AppCompatActivity {
    private final String[] PERMISSIONS =
            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.READ_PHONE_STATE};
    private final int REQUEST_PERMISSION = 0;
    private Context mContext = null;
    private RelativeLayout mbtnPerConfirm = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_permission);
        mbtnPerConfirm = (RelativeLayout)findViewById(R.id.btn_per_confirm);
        mbtnPerConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPermission(mContext)) {
                    requestStoragePermission();
                } else {
                    moveActivity();
                }
            }
        });

        //권한이 허
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //6.0 이상에서만 권한 제거 가능
            if (checkPermission(mContext)) {
                moveActivity();
            }
        }
    }

    public static boolean checkPermission(Context context) {
        boolean flag = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //6.0 이상에서만 권한 제거 가능
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    &&ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
                flag = true;
            } else {
                flag = false;
            }
        }
        return flag;
    }

    private void moveActivity(){
        Toast.makeText(this, "권한 확인", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        setResult(RESULT_OK);
        finish();
    }

    private void requestStoragePermission() {
        ActivityCompat.requestPermissions(this,checkUnGrantedPermission(),REQUEST_PERMISSION);
    }

    public String[] checkUnGrantedPermission() {
        ArrayList<String> list = new ArrayList<>();

        for (String permisison : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permisison) != PackageManager.PERMISSION_GRANTED) {
                list.add(permisison);
            }
        }
        return list.toArray(new String[list.size()]);
    }

    private void permissionRetryOrExit(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(false);
        dialog.setMessage("권한 요청이 거부 되었습니다. 권한이 없는 경우 앱 사용시 문제가 발생할 수 있습니다.\n권한을 허용해주시기 바랍니다.");
        dialog.setPositiveButton("재시도", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                requestStoragePermission();
            }
        });
        dialog.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean isDenied = false;
        if (requestCode == REQUEST_PERMISSION) {
            for(int i=0;i<grantResults.length;i++){
                if(grantResults[i] != PackageManager.PERMISSION_GRANTED){
                    isDenied = true;
                }
            }

            if(isDenied){
                permissionRetryOrExit();
            }else{
                moveActivity();
            }
        }else{
            finish();
        }
    }
}
