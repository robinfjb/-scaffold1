package robin.scaffold.baisc.webview.function.permission;

import android.content.Context;

public interface PermissionCallbackListener {
    void onRequestPermissionsResult(Context context, int requestCode, String[] permissions, int[] grantResults);
}
