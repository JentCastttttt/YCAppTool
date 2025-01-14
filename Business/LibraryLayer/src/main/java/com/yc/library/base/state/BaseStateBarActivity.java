package com.yc.library.base.state;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.yc.ycstatelib.StateLayoutManager;
import com.yc.library.R;


/**
 * <pre>
 *     @author      杨充
 *     blog         https://www.jianshu.com/p/53017c3fc75d
 *     time         2015/08/22
 *     desc
 *     revise       使用自己的开源库，可以自由切换5种不同状态
 *                  且与Activity，Fragment让View状态的切换和Activity彻底分离开
 *                  欢迎star和反馈
 *     GitHub       https://github.com/yangchong211
 * </pre>
 */
public abstract class BaseStateBarActivity extends AppCompatActivity{

    protected StateLayoutManager statusLayoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.base_state_bar_view);
        initStatusLayout();
        initBaseView();
        initView();
        initListener();
        initData();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        initLeakCanary();             //测试内存泄漏，正式一定要隐藏
    }


    /** 状态切换 **/
    public void initStatusLayout(){
        statusLayoutManager = StateLayoutManager.newBuilder(this)
                .contentView(getLayoutId())
                .emptyDataView(R.layout.view_custom_empty_data)
                .errorView(R.layout.view_custom_data_error)
                .loadingView(R.layout.view_custom_loading_data)
                .netWorkErrorView(R.layout.view_custom_network_error)
                .build();
    }

    protected abstract int getLayoutId();

    /** 初始化View的代码写在这个方法中 */
    public abstract void initView();

    /** 初始化监听器的代码写在这个方法中 */
    public abstract void initListener();

    /** 初始数据的代码写在这个方法中，用于从服务器获取数据 */
    public abstract void initData();

    /**
     * 获取到子布局
     */
    private void initBaseView() {
        LinearLayout flMain = findViewById(R.id.ll_main);
        flMain.addView(statusLayoutManager.getRootLayout());
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    /**
     * 用来检测所有Activity的内存泄漏
     */
    private void initLeakCanary() {
        /*RefWatcher refWatcher = BaseApplication.getRefWatcher(this);
        refWatcher.watch(this);*/
    }


}
