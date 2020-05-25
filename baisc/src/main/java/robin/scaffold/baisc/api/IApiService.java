package robin.scaffold.baisc.api;


import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface IApiService {
    /**
     * 上传日志
     */
    @POST("common-api/app/log")
    Observable<String> uploadErrorLog(@Body RequestBody params);


    @POST("auth_server/auth/logout")
    retrofit2.Call<ResponseBody> logout(@Query("access_token") String access_token);
}
