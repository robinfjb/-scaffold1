package robin.scaffold.baisc.rx;


import androidx.annotation.NonNull;

import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.HttpException;
import robin.scaffold.baisc.api.HttpConstants;
import robin.scaffold.baisc.api.bean.HttpStatus;


public abstract class BaseSubscriber<T> implements Observer<T> {

    private static final String TAG = "BaseSubscriber";

    @Override
    public void onSubscribe(@io.reactivex.rxjava3.annotations.NonNull Disposable d) {

    }

    @Override
    public void onNext(T t) {
        if(t != null) {
            onSuccess(t, HttpConstants.REPONSE_CODE_OK);
        } else {
            HttpStatus httpStatus = new HttpStatus();
            httpStatus.setHttpCode(HttpConstants.REPONSE_CODE_UNKNOW);
            httpStatus.setMessage("result object is null");
            onFail(httpStatus);
        }
    }

    @Override
    public void onComplete() {
    }

    @Override
    public void onError(Throwable e) {
        onComplete();
        onFail(e);
    }

    protected abstract void onSuccess(@NonNull T t, int code);

    protected abstract void onFail(@NonNull HttpStatus httpStatus);// 接口返回错误

    private void onFail(Throwable e) {// 各种原因失败
        HttpStatus httpStatus = new HttpStatus();
        if (e instanceof HttpException) {
            String errorBody = null;
            try {
                httpStatus.setHttpCode(((HttpException)e).code());
                errorBody = ((HttpException) e).response().errorBody().string();
            }catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                if(errorBody == null) errorBody = "";
                httpStatus.setMessage(errorBody);
            }
        } else {
            httpStatus.setHttpCode(HttpConstants.REPONSE_CODE_UNKNOW);
            httpStatus.setMessage(e.getMessage());
        }

        onFail(httpStatus);
    }

}
