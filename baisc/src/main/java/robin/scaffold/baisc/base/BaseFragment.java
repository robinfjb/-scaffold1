package robin.scaffold.baisc.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.trello.rxlifecycle4.components.support.RxFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment<P extends BasePresenter> extends RxFragment {
    protected Context mContext;
    /**
     * 当前Fragment是否可见
     */
    public boolean mIsVisible = false;
    /**
     * 是否与View建立起映射关系
     */
    public boolean mIsInitView = false;//
    /**
     * 是否是第一次加载数据
     */
    public boolean mIsFirstLoad = true;

    protected P mPresenter;

    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(attachLayoutRes(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        mPresenter = initPresenter();
        attachView();
        initStatusBar();
        initViews();
        initData();
        mIsInitView = true;
        lazyLoadData();
        return view;
    }

    protected abstract int attachLayoutRes();

    protected abstract void initViews();

    protected abstract void initData();

    protected abstract P initPresenter();

    protected abstract void attachView();

    protected abstract void lazyInitData();


    public void initStatusBar() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            mIsVisible = true;
            lazyLoadData();
        } else {
            mIsVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void lazyLoadData() {
        if (!mIsFirstLoad || !mIsVisible || !mIsInitView) {
            return;
        }
        lazyInitData();
        mIsFirstLoad = false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        clearData();
    }

    private void clearData() {
        //初始化 懒加载记录
        mIsInitView = false;
        mIsVisible = false;
        mIsFirstLoad = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }

        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
    }
}
