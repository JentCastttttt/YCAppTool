package com.yc.toollib.crash;


import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.yc.toollib.tool.ToolAppManager;
import com.yc.toolutils.AppLogUtils;

/**
 * <pre>
 *     @author yangchong
 *     email  : yangchong211@163.com
 *     time  : 2015/03/22
 *     desc  : 生命周期管理类
 *     revise:
 * </pre>
 */
public class LifecycleCallback implements Application.ActivityLifecycleCallbacks{

    public static LifecycleCallback getInstance() {
        return HolderClass.INSTANCE;
    }


    private final static class HolderClass {
        private final static LifecycleCallback INSTANCE = new LifecycleCallback();
    }


    private LifecycleCallback() {}

    /**
     * 必须在 Application 的 onCreate 方法中调用
     */
    public void init(Application application) {
        application.registerActivityLifecycleCallbacks(this);
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        AppLogUtils.e("Activity生命周期","onActivityCreated");
        ToolAppManager.getAppManager().addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        AppLogUtils.e("Activity生命周期","onActivityStarted");
    }

    @Override
    public void onActivityResumed(Activity activity) {
        AppLogUtils.e("Activity生命周期","onActivityResumed");
    }

    @Override
    public void onActivityPaused(Activity activity) {
        AppLogUtils.e("Activity生命周期","onActivityPaused");
    }

    @Override
    public void onActivityStopped(Activity activity) {
        AppLogUtils.e("Activity生命周期","onActivityStopped");
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        AppLogUtils.e("Activity生命周期","onActivitySaveInstanceState");
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        AppLogUtils.e("Activity生命周期","onActivityDestroyed");
        //将当前Activity移除到容器
        ToolAppManager.getAppManager().removeActivity(activity);
    }

}
