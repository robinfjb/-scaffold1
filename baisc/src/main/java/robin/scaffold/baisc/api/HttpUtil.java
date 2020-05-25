package robin.scaffold.baisc.api;

import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

public class HttpUtil {
    public static String getDefaultUserAgent(Context context){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            return android.webkit.WebSettings.getDefaultUserAgent(context);

        return getDefaultUABelowJellyBeanMr1(context);
    }

    /**
     * 17以下使用反射获取
     * @param context
     * @return
     */
    private static String getDefaultUABelowJellyBeanMr1(Context context) {
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1){
            Constructor<WebSettings> constructorWebSettings=null;
            WebSettings settings=null;
            try {
                constructorWebSettings = WebSettings.class.getDeclaredConstructor(Context.class, android.webkit.WebView.class);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(constructorWebSettings!=null) {
                try {
                    constructorWebSettings.setAccessible(true);
                    settings = constructorWebSettings.newInstance(context, null);
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
                constructorWebSettings.setAccessible(false);
            }
            if(settings!=null)
                return settings.getUserAgentString();
        }
        return null;
    }
}
