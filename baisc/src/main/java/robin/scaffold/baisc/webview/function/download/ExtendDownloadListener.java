package robin.scaffold.baisc.webview.function.download;

import android.webkit.DownloadListener;



public interface ExtendDownloadListener extends DownloadListener {
    boolean cancelDownload();
}
