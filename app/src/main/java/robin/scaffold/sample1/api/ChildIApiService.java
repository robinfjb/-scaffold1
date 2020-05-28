package robin.scaffold.sample1.api;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import robin.scaffold.baisc.api.IApiService;

import static me.jessyan.retrofiturlmanager.RetrofitUrlManager.DOMAIN_NAME_HEADER;

public interface ChildIApiService extends IApiService {
    /**
     * 测试接口
     */
    @Headers({DOMAIN_NAME_HEADER + "test1"})
    @POST("/url1")
    Observable<String> getBanner(@Body RequestBody params);

    @Headers({DOMAIN_NAME_HEADER + "test2"})
    @POST("/url2")
    Observable<String> getList(@Body RequestBody params);

    @GET("/data/cityinfo/101010100.html")
    Observable<String> weather();
}
