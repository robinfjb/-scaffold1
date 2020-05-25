package robin.scaffold.baisc.security.jni;

import android.content.Context;

public class JniManager {
    static {
        //对应着c的文件名，以及后面的配置名以及so的库名称
        System.loadLibrary("robinencrypt");
    }
    //c/c++中要对应实现的方法，必须声明native
    public native String getPublicKey(Context context);

    public native String getSecret(Context context);

    public native String getDesKey(Context context);





}
