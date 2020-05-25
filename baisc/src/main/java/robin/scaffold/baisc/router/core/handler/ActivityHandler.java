package robin.scaffold.baisc.router.core.handler;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import robin.scaffold.baisc.router.base.IResultCallback;
import robin.scaffold.baisc.router.core.RouterAction;
import robin.scaffold.baisc.router.robin.RobinRouterConfig;
import robin.scaffold.baisc.router.exception.RouterException;
import robin.scaffold.baisc.util.Parameters;

public class ActivityHandler implements IRouterHandler {

    public boolean handle(final Context context, RouterAction action, final IResultCallback callback) throws RouterException {
        if(action == null) {
            throw new RouterException("action null in ActivityHandler:handle");
        }

        //set intent
        final Intent intent = (Intent) action.getTarget();
        if (intent == null || !queryActivityIntent(context, intent)) {
            throw new RouterException("intent null or invalid in ActivityHandler:handle");
        }

        //set param
        Parameters parameters = action.getParameters();
        if(parameters != null) {
            Set<String> keys = parameters.getParameterNames();
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = parameters.getParameter(key);
                intent.putExtra(key, value);
            }
        }

        //set data
        Bundle bundle = action.getBundle();
        if(bundle != null) {
            intent.putExtras(bundle);
        }

        //set flags
        int flag = action.getIntentFlag();
        if(flag > 0) {
            intent.addFlags(flag);
        }
        String packageName = action.getPackageName();
        if(TextUtils.isEmpty(packageName))
            packageName = intent.getPackage();
        if (!RobinRouterConfig.MY_PACKAGE_NAME.equals(packageName) || !(context instanceof Activity)) {
            //外部应用跳转进入，增加FLAG_ACTIVITY_NEW_TASK，使得能够返回首页
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }

        //set request code
        final int requestCode = action.getRequestCode();

        // Navigation in main looper.
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                 if (context instanceof Activity) {
                     if(requestCode > 0) {
                         ((Activity) context).startActivityForResult(intent, requestCode);
                      } else {
                         context.startActivity(intent);
                     }
                } else {
                    context.startActivity(intent);
                }
                if (null != callback) { // Navigation over.
                    callback.onResult(true);
                }
            }
        });
        return true;
    }

    /**
     * 判断是否有Intent对应的Activity
     * @param context
     * @param intent
     * @return
     */
    private boolean queryActivityIntent(Context context, Intent intent ) {
        final PackageManager packageManager = context.getPackageManager();
        final List<ResolveInfo> queryIntentActivities = packageManager.queryIntentActivities(
                intent, 0);
        return queryIntentActivities != null && queryIntentActivities.size() > 0;
    }
}
