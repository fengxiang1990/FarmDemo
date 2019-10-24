package com.lantern.core.myapplication;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.bun.miitmdid.core.JLibrary;
import com.tencent.bugly.crashreport.CrashReport;

import java.lang.ref.WeakReference;
import java.util.List;

public class MyApplication extends Application {

    Handler handler = new Handler();

    @Override
    public void onCreate() {
        super.onCreate();
        JLibrary.InitEntry(this);


//        CrashReport.initCrashReport(getApplicationContext(), "b8dd8cde12", true);
//        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
//            @Override
//            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//
//            }
//
//            @Override
//            public void onActivityStarted(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityResumed(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityPaused(Activity activity) {
//
//            }
//
//            @Override
//            public void onActivityStopped(Activity activity) {
////                handler.postDelayed(new Runnable() {
////                    @Override
////                    public void run() {
////                        isActivityRunningTop(MyApplication.this, "MainActivity");
////                    }
////                }, 10000);
//                final WeakReference<Activity> weakReference = new WeakReference<>(activity);
//                System.gc();
//                handler.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        Activity activity1 = weakReference.get();
//                        Log.e("fxa","activity1->"+activity1);
//                    }
//                }, 5000);
//            }
//
//            @Override
//            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//
//            }
//
//            @Override
//            public void onActivityDestroyed(Activity activity) {
//
//            }
//        });
    }


    static boolean isActivityRunningTop(Context context, String name) {
        try {
            ActivityManager activityManager =
                    (ActivityManager) context.getSystemService(Service.ACTIVITY_SERVICE);
            if (activityManager == null) {
                return false;
            }
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                List<ActivityManager.AppTask> tasks = activityManager.getAppTasks();
                for (ActivityManager.AppTask task : tasks) {
                    ComponentName componentName = null;
                    ActivityManager.RecentTaskInfo taskInfo;
                    try {
                        Log.e("fxa", "getTaskInfo");
//                        task.finishAndRemoveTask();
                        taskInfo = task.getTaskInfo();
                        Log.e("fxa", "after getTaskInfo");
                        if (taskInfo != null) {
                            componentName = taskInfo.topActivity;
                        }
                    } catch (Exception e) {
                        Log.e("fxa", e.getMessage());
                    }

                    if (true) {
                        Log.e("fxa", "before throw null");
//                        throw new NullPointerException();

                    }
                    Log.e("fxa", "after throw null");
                    if (componentName != null) {
                        if (componentName.getClassName().startsWith(name)
                                || componentName.getClassName().endsWith(name)) {
                            return true;
                        }
                    }
                }
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
