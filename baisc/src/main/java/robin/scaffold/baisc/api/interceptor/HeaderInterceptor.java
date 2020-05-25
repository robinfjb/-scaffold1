package robin.scaffold.baisc.api.interceptor;

import android.os.Build;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


public class HeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Content-Type", "application/json;charset=UTF-8");
        if (Build.VERSION.SDK != null && Build.VERSION.SDK_INT > 13) {
            builder.addHeader("Connection", "close");
        }


        return chain.proceed(builder.build());
    }
}
