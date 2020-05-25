package robin.scaffold.baisc.router.core.handler;

import android.content.Context;

import robin.scaffold.baisc.router.base.IResultCallback;
import robin.scaffold.baisc.router.core.RouterAction;
import robin.scaffold.baisc.router.exception.RouterException;


public interface IRouterHandler {
    boolean handle(Context context, RouterAction action, IResultCallback callback) throws RouterException;
}
