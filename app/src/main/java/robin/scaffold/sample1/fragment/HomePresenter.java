package robin.scaffold.sample1.fragment;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

import java.util.Map;

import okhttp3.RequestBody;
import robin.scaffold.baisc.api.RequestHandler;
import robin.scaffold.baisc.api.RetrofitHelper;
import robin.scaffold.baisc.api.bean.HttpStatus;
import robin.scaffold.baisc.base.BasePresenter;
import robin.scaffold.baisc.rx.BaseSubscriber;
import robin.scaffold.sample1.api.ChildIApiService;

public class HomePresenter extends BasePresenter<IHomeView> {

    public void queryBanner(final Map<String, Object> parameters) {
        getView().onRequestStart();
        RetrofitHelper.getInstance().request(new BaseSubscriber<String>() {
            @Override
            protected void onSuccess(String result, int code) {
                getView().showBanner(result);
            }

            @Override
            protected void onFail(@NonNull HttpStatus httpStatus) {
                getView().showError(httpStatus.getMessage());
            }
        }, () -> {
            ChildIApiService apiService = RetrofitHelper.getInstance().createApiService(ChildIApiService.class);
            String requestParams = new Gson().toJson(parameters, Map.class);
            return apiService.getBanner(RequestBody.create(RequestHandler.MEDIA_TYPE, requestParams));
        });
    }
}
