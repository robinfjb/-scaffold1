package robin.scaffold.baisc.api;


import android.util.Log;

import androidx.annotation.Nullable;

import java.io.File;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.schedulers.Schedulers;
import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.BufferedSource;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import robin.scaffold.baisc.BaseApplication;
import robin.scaffold.baisc.api.converter.StringConverterFactory;
import robin.scaffold.baisc.api.interceptor.HeaderInterceptor;
import robin.scaffold.baisc.api.interceptor.TokenInterceptor;



public class RetrofitHelper {
    private static final String TAG = "RetrofitHelper";
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8");
    //设缓存有效期为7天
    private static final long CACHE_STALE_SEC = 60 * 60 * 24 * 7;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    private static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //(假如请求了服务器并在a时刻返回响应结果，则在max-age规定的秒数内，浏览器将不会发送对应的请求到服务器，数据由缓存直接返回)
    static final String CACHE_CONTROL_NETWORK = "Cache-Control: public, max-age=3600";

    private static RetrofitHelper sRetrofitHelper;
    private static OkHttpClient mOkHttpClient;
    private static Retrofit sRetrofit;
    private static final String BASE_URL = "http://www.weather.com.cn";

    final ObservableTransformer mSchedulersTransformer = new ObservableTransformer() {

        @Override
        public @NonNull ObservableSource apply(@NonNull Observable upstream) {
            return upstream.subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread());
        }
    };

    private <T> ObservableTransformer<T, T> applySchedulers() {
        return (ObservableTransformer<T, T>) mSchedulersTransformer;
    }

    public static RetrofitHelper getInstance() {
        if (sRetrofitHelper == null) {
            synchronized (RetrofitHelper.class) {
                if (sRetrofitHelper == null) {
                    initOkHttpClient();
                    initRetrofit();
                    sRetrofitHelper = new RetrofitHelper();
                }
            }
        }
        return sRetrofitHelper;
    }

    public void request(Observer<String> observer, RequestHandler handler) {
        Observable observable = handler.request();
        if (observable != null) {
            observable.compose(this.<String>applySchedulers()).subscribe(observer);
        } else {
            HttpException exception = new HttpException(Response.error(HttpConstants.REPONSE_CODE_NOT_FOUND, new ResponseBody() {
                @Nullable
                @Override
                public MediaType contentType() {
                    return null;
                }

                @Override
                public long contentLength() {
                    return 0;
                }

                @Override
                public BufferedSource source() {
                    return null;
                }
            }));
            observer.onError(exception);
        }
    }


    public <T> T createApiService(Class<T> clazz) {
        return getRetrofit().create(clazz);
    }

    public Retrofit getRetrofit() {
        return sRetrofit;
    }

    protected static void initRetrofit() {
        if (sRetrofit == null) {
            sRetrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(mOkHttpClient)
                    .addConverterFactory(new StringConverterFactory())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();
        }
    }

    /**
     * 初始化OKHttpClient
     * 设置缓存
     * 设置超时时间
     * 设置打印日志
     * 设置UA拦截器
     */
    protected static void initOkHttpClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.e("retrofit", message + "--");
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    File cacheFile = new File(BaseApplication.getContext().getCacheDir(), "HttpCache");
                    Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
                    mOkHttpClient = RetrofitUrlManager.getInstance().with(new OkHttpClient.Builder())
                            .retryOnConnectionFailure(false) //原来是false
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .writeTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(30, TimeUnit.SECONDS)
                            .cache(cache)
                            .addInterceptor(new HeaderInterceptor())
                            .addInterceptor(new TokenInterceptor())
                            .addInterceptor(logging).build();
                }
            }
        }
    }
}
