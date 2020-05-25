package robin.scaffold.baisc.webview;

import android.graphics.Bitmap;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;

import robin.scaffold.baisc.util.CommUrl;


public interface IWebClientDevelopCallback {
    boolean shouldOverrideUrlLoading(WebView view, CommUrl url);
    boolean onReceivedSslError(WebView view, SslErrorHandler handler, android.net.http.SslError error);
    boolean onReceivedError(WebView view, int errorCode, String description, String failingUrl);
    boolean onPageFinished(WebView view, final CommUrl url);
    boolean onPageStarted(WebView view, CommUrl url, Bitmap favicon);
    boolean onLoadResource(WebView view, CommUrl url);
}
