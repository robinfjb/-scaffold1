package robin.scaffold.baisc.router.core;

import android.content.Context;
import android.os.Parcelable;

import androidx.annotation.Nullable;

import java.io.Serializable;

import robin.scaffold.baisc.router.base.IProcessInterface;
import robin.scaffold.baisc.router.base.IResultCallback;
import robin.scaffold.baisc.router.base.UrlRouteManager;
import robin.scaffold.baisc.router.core.handler.IRouterHandler;
import robin.scaffold.baisc.router.exception.RouterException;
import robin.scaffold.baisc.util.CommUrl;


public class RouterExcuter {
    private Serializable mSerializable;
    private Parcelable mParcelable;
    private int mRequestCode;
    public RouterExcuter(){}

    public RouterExcuter withSerializableObj(Serializable obj) {
        mSerializable = obj;
        return this;
    }

    public RouterExcuter withParcelableObj(Parcelable obj) {
        mParcelable = obj;
        return this;
    }

    public RouterExcuter withRequestCode(int requestCode) {
        mRequestCode = requestCode;
        return this;
    }

    public void execute(Context context, String url, int group, @Nullable IRouterHandler handler, @Nullable IResultCallback callback) throws RouterException {
        IProcessInterface<RouterAction> processor = UrlRouteManager.getInstance().seek(url, group);
        if(processor == null) {
            throw new RouterException("cannot find processor, have register it?");
        }
        if(mSerializable != null) {
            processor.withSerializableObj(mSerializable);
        }
        if(mParcelable != null) {
            processor.withParcelableObj(mParcelable);
        }
        CommUrl commUrl = new CommUrl(url);
        if(mRequestCode > 0) {
            commUrl.addOrReplaceQuery(RouterConfig.EXTRA_REQUEST_CODE, String.valueOf(mRequestCode));
        }
        boolean success = processor.execute(context, commUrl.getUrl(), handler, callback);
        if(!success) {
            throw new RouterException("execute failed");
        }
    }
}
