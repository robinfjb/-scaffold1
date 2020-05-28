package robin.scaffold.sample1.second;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import robin.scaffold.baisc.api.RequestHandler;
import robin.scaffold.baisc.api.RetrofitHelper;
import robin.scaffold.baisc.api.bean.HttpStatus;
import robin.scaffold.baisc.base.BasePresenter;
import robin.scaffold.baisc.rx.BaseSubscriber;
import robin.scaffold.sample1.api.ChildIApiService;
import robin.scaffold.sample1.second.ISecondView;

public class SecondPresenter extends BasePresenter<ISecondView> {
    public Context mContext;

    public SecondPresenter(Context context) {
        mContext = context;
    }

    public void doSomeThing() {
        getView().onRequestStart();
        RetrofitHelper.getInstance().request(new BaseSubscriber<String>() {
            @Override
            protected void onSuccess(String result, int code) {
                if (TextUtils.isEmpty(result)) {
                    return;
                }
                getView().onSomeThing(result);
            }

            @Override
            protected void onFail(@NonNull HttpStatus httpStatus) {
                getView().showError(httpStatus.getMessage());
            }

        }, () -> {
            ChildIApiService apiService = RetrofitHelper.getInstance().createApiService(ChildIApiService.class);
            return apiService.weather();
        });


    }
}
