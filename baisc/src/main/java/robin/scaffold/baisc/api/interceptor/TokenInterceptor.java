package robin.scaffold.baisc.api.interceptor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import robin.scaffold.baisc.api.HttpConstants;



public class TokenInterceptor implements Interceptor {
    private static final String TAG = TokenInterceptor.class.getSimpleName();
    private boolean isDoingLogout = false;
    private static List<String> blackList = new ArrayList<String>();
    static {
        blackList.add("/auth_server/auth/logout");
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        //黑名单的url不处理
        String path = request.url().encodedPath();
        if(path == null || blackList.contains(path)) {
            return response;
        }
        if (isAccessTokenExpired(response)) {
            if(isDoingLogout) {
                //处于正在登出状态，直接忽略以下操作
                return response;
            }
           //TODO


        }
        return response;
    }



    /**
     * 根据Response，判断access Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isAccessTokenExpired(Response response) {
        if (response != null && response.code() == HttpConstants.REPONSE_CODE_UNAUTHORIZATION ) {
            return true;
        }
        return false;
    }

    /**
     * 根据Response，判断refresh Token是否失效
     *
     * @param response
     * @return
     */
    private boolean isRefreshTokenExpired(retrofit2.Response response) {
        if (response == null || response.code() != HttpConstants.REPONSE_CODE_OK) {
            return true;
        }
        if(response.body() == null)
            return true;
        return false;
    }






}
