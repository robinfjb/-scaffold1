package robin.scaffold.baisc;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public abstract class BaseApplication extends Application {
    public static Application sApplication;
    public static IChildPipe iChildPipe;

    public BaseApplication() {
        //Application真正的初始化时
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApplication = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        // TODO Auto-generated method stub
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public static Context getContext() {
        return sApplication.getApplicationContext();
    }
}
