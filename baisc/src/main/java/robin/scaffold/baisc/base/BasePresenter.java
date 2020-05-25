package robin.scaffold.baisc.base;

import android.content.Context;

public abstract class BasePresenter<V extends IBaseView> {

    public Context mContext;

    public BasePresenter() {

    }

    public BasePresenter(Context context) {
        this.mContext = context;
    }

    private V mView;

    public void attachView(V view) {
        mView = view;
    }


    public void detachView() {
        mView = null;
    }

    public V getView() {
        return mView;
    }
}
