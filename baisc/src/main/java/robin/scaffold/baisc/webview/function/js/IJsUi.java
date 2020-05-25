package robin.scaffold.baisc.webview.function.js;

import android.webkit.ValueCallback;


public interface IJsUi {
    void loadJs(String script, ValueCallback<String> callback);
    void onAddJavaObjects();
}
