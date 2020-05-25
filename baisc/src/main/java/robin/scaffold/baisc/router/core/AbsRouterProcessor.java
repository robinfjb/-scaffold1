package robin.scaffold.baisc.router.core;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.Serializable;

import robin.scaffold.baisc.router.base.IProcessInterface;
import robin.scaffold.baisc.router.base.IResultCallback;
import robin.scaffold.baisc.router.core.handler.IRouterHandler;
import robin.scaffold.baisc.router.core.handler.RouterHandlerFactory;
import robin.scaffold.baisc.router.exception.RouterException;

/**
 * router处理抽象类，定义了处理流程
 */
public abstract class AbsRouterProcessor implements IProcessInterface<RouterAction> {
    protected static final String TAG = "AbsRouterProcessor";
    //跳转源
    protected Context mContext;
    /**
     * 是否成功处理
     */
    protected boolean success;

    protected Bundle bundle;
    protected RouterAction mAction;

    @Nullable
    public RouterAction getAction() {
        return mAction;
    }


    //====================================================
    // 对外接口方法
    //====================================================
    /***
     * 用于自定义scheme跳转。在处理上，open = preprocess生成intent + 用生成intent启动activity
     */
    @Override
    public boolean execute(Context context, String url, IRouterHandler handler, IResultCallback callback) {
        mContext = context;
        RouterAction action = processUrl(url);
        return processResult(action, handler, callback);
    }

    @Override
    public void withSerializableObj(Serializable obj) {
        if(bundle == null)
            bundle = new Bundle();
        bundle.putSerializable("s_obj",obj);
    }

    @Override
    public void withParcelableObj(Parcelable obj) {
        if(bundle == null)
            bundle = new Bundle();
        bundle.putParcelable("p_obj",obj);
    }

    protected RouterAction preprocess(String url) {
        CommPreProcessor preProcessor = new CommPreProcessor(mContext);
        try {
            mAction = preProcessor.prePorcess(url);
        } catch (RouterException e) {
            Log.e(TAG, e.getMessage());
        }
        return mAction;
    }

    protected void process(RouterAction action) {
        mapping(mContext, action);
        action.setBundle(bundle);
    }

    protected boolean handleRouteAction(RouterAction action, IRouterHandler handler, IResultCallback callback) {
        if(action == null) {
            return false;
        }
        if(handler == null)//如果没有指定处理器，用系统自带的
            handler = new RouterHandlerFactory().createHandler(action);
        if(handler == null) {
            return false;
        }
        try {
            return handler.handle(mContext, action, callback);
        } catch (RouterException e) {
            e.printStackTrace();
        }
        return false;
    }

    private RouterAction processUrl(String url) {
        RouterAction action = preprocess(url);
        process(action);
        return action;
    }

    private boolean processResult(RouterAction action, IRouterHandler handler, IResultCallback callback) {
        if (action == null)
            return success;
        success = handleRouteAction(action, handler, callback);
        return success;
    }

    abstract protected void mapping(Context context, RouterAction action);
}
