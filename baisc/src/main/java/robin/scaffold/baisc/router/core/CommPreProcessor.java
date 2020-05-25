package robin.scaffold.baisc.router.core;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

import robin.scaffold.baisc.router.base.IPreProcessInterface;
import robin.scaffold.baisc.router.robin.RobinRouterConfig;
import robin.scaffold.baisc.router.exception.RouterException;
import robin.scaffold.baisc.util.CommUrl;
import robin.scaffold.baisc.util.Parameters;
import robin.scaffold.baisc.util.UrlUtils;

public class CommPreProcessor implements IPreProcessInterface<RouterAction> {
    private Context mContext;
    public CommPreProcessor(Context context) {
        this.mContext = context;
    }

    @Override
    public RouterAction prePorcess(String url) throws RouterException {
        if(mContext == null) {
            throw new RouterException("context is null");
        }
        if(!checkUrl(url)) {
            throw new RouterException("illegal url");
        }
        //url转换
        //TODO

        RouterAction action = new RouterAction();
        CommUrl commUrl = new CommUrl(url);

        //url中start_class参数的补充
        commUrl.addOrIgnoreQuery(RouterConfig.EXTRA_START_CLASS, mContext.getClass().getName());

        //处理url上非业务参数
        //request code
        String paramRcode = commUrl.getQueryParameter(RouterConfig.EXTRA_REQUEST_CODE);
        if(!TextUtils.isEmpty(paramRcode)) {
            try {
                action.setRequestCode(Integer.parseInt(paramRcode));
            } catch (NumberFormatException e){}
        }
        commUrl.removeQuery(RouterConfig.EXTRA_REQUEST_CODE);
        // package name
        String paramPname = commUrl.getQueryParameter(RouterConfig.EXTRA_PARAM_PACKAGE);
        if(!TextUtils.isEmpty(paramPname)) {
            action.setPackageName(paramPname);
        } else {
            action.setPackageName(mContext.getPackageName());
        }
        commUrl.removeQuery(RouterConfig.EXTRA_PARAM_PACKAGE);
        // intent flag
        String paramFlag = commUrl.getQueryParameter(RouterConfig.EXTRA_INTENT_FLAG);
        if(!TextUtils.isEmpty(paramFlag)) {
            try {
                action.setIntentFlag(Integer.parseInt(paramFlag));
            } catch (NumberFormatException e){}
        }
        commUrl.removeQuery(RouterConfig.EXTRA_INTENT_FLAG);

        String finalLink = commUrl.getUrl(); //处理后的url
        Parameters params = UrlUtils.getParamsFromUrl(finalLink);
        action.setParameters(params);
        action.setPath(commUrl.getPath());
        action.setOriginalUrl(finalLink);
        return action;
    }

    /***
     * robin，并且host为robin.test，则认为有效
     */
    private boolean checkUrl(String url) {
        if (TextUtils.isEmpty(url))
            return false;

        Uri uri = Uri.parse(url);
        String scheme = uri.getScheme();
        String host = uri.getHost();
        if (uri == null || scheme == null || host == null)
            return false;

        return RobinRouterConfig.SCHEME.equalsIgnoreCase(scheme)
                && RobinRouterConfig.HOST.equalsIgnoreCase(host);
    }
}
