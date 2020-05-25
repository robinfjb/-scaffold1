package robin.scaffold.baisc.webview.function.scheme;

import android.content.Context;


public interface ISchemeHandler {
    void registerToWhiteList(String scheme);
    void unRegisterToWhiteList(String scheme);
    boolean handleScheme(Context context, String url);
}
