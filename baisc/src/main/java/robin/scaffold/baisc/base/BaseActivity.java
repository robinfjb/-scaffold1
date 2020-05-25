package robin.scaffold.baisc.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.gyf.immersionbar.ImmersionBar;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import robin.scaffold.baisc.BaseApplication;
import robin.scaffold.baisc.R;
import robin.scaffold.baisc.rx.RxBus;
import robin.scaffold.baisc.rx.event.ConnectionChangeEvent;
import robin.scaffold.baisc.util.DisplayUtil;
import rx.Subscription;
import rx.functions.Action1;


public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity implements IBaseView{

    protected P mPresenter;
    private Unbinder mUnbinder;
    private Handler mBaseHandler = new Handler();
    private Subscription mRxSub;
    protected TitleManager titleManager;
    protected Activity context;
    protected ImmersionBar mImmersionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(attachLayoutRes());
        //最新屏幕适配
        DisplayUtil.setCustomDensity(this, getApplication());

        mUnbinder = ButterKnife.bind(this);
        titleManager = new TitleManager(this);  //新添加标题栏
        mPresenter = initPresenter();

        attachView();
        initViews();
        initData();
    }

    protected abstract int attachLayoutRes();

    protected abstract void initViews();

    protected abstract void initData();

    protected abstract P initPresenter();

    protected abstract void attachView();


    @Override
    protected void onDestroy() {
        super.onDestroy();

        mBaseHandler.removeCallbacksAndMessages(null);
        if (mPresenter != null) {
            mPresenter.detachView();
        }

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }

    protected boolean checkPermission(String... permissions) {
        if (permissions == null || permissions.length < 1) {
            return true;
        }
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        }
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }


    /**
     * 没有授权alert
     */
    public void showNoPermissionDialog(final String permissionName, String permissionDesc) {
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle(permissionName)
                .setMessage(permissionDesc)
                .setPositiveButton("去设置", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startAppSettings();
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }


    /**
     * 启动当前应用设置详情页面
     */
    public void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setData(Uri.parse("package:" + getPackageName()));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        startActivity(intent);
    }


    @Override
    public void onRequestCompleted() {
    }


    @Override
    public void onRequestStart() {
    }


    @Override
    public void showError(String msg) {
    }
}
