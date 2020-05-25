package robin.scaffold.baisc.webview.function.camera;

import android.net.Uri;

import androidx.annotation.Nullable;


public interface ICamerCallback {
    void onResult(@Nullable Uri[] paths);
}
