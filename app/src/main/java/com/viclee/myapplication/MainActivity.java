package com.viclee.myapplication;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String TAG = "viclee";
    private boolean isCurrentRunningForeground = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!isCurrentRunningForeground) {
            Log.v(TAG, ">>>>>>>>>>>>>>>>>>>切到前台 activity process");
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        isCurrentRunningForeground = isRunningForeground();
        if (!isCurrentRunningForeground) {
            Log.v(TAG, ">>>>>>>>>>>>>>>>>>>切到后台 activity process");
        }
    }

    public boolean isRunningForeground() {
        ActivityManager activityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> appProcessInfos = activityManager.getRunningAppProcesses();
        // 枚举进程
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : appProcessInfos) {
            if (appProcessInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                if (appProcessInfo.processName.equals(this.getApplicationInfo().processName)) {
                    Log.d(TAG, "MainActivity isRunningForeGround");
                    return true;
                }
            }
        }
        Log.d(TAG, "MainActivity isRunningBackGround");
        return false;
    }
//    public boolean isRunningForeground() {
//        String packageName = getPackageName();
//        String topActivityClassName = getTopActivityName(this);
//        Log.d(TAG, "packageName=" + packageName + ",topActivityClassName=" + topActivityClassName);
//        if (packageName != null && topActivityClassName != null && topActivityClassName.startsWith(packageName)) {
//            Log.d(TAG, "MainActivity isRunningForeGround");
//            return true;
//        } else {
//            Log.d(TAG, "MainActivity isRunningBackGround");
//            return false;
//        }
//    }
//
//    public String getTopActivityName(Context context) {
//        String topActivityClassName = null;
//        ActivityManager activityManager = (ActivityManager) (context.getSystemService(android.content.Context.ACTIVITY_SERVICE));
//        List<ActivityManager.RunningTaskInfo> runningTaskInfos = activityManager.getRunningTasks(1);
//        if (runningTaskInfos != null) {
//            ComponentName f = runningTaskInfos.get(0).topActivity;
//            topActivityClassName = f.getClassName();
//        }
//        //按下Home键盘后 topActivityClassName=com.android.launcher2.Launcher
//        return topActivityClassName;
//    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn) {
            startActivity(new Intent(this,Main2Activity.class));
        }
    }
}
