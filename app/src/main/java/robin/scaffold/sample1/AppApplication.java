package robin.scaffold.sample1;

import me.jessyan.retrofiturlmanager.RetrofitUrlManager;
import robin.scaffold.baisc.BaseApplication;

public class AppApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        RetrofitUrlManager.getInstance().putDomain("test1", "https://api.test1.com");
        RetrofitUrlManager.getInstance().putDomain("test2", "https://api.test2.com");
    }
}
