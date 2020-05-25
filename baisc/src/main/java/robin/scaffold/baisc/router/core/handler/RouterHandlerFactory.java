package robin.scaffold.baisc.router.core.handler;


import androidx.annotation.NonNull;

import robin.scaffold.baisc.router.core.RouterAction;

public class RouterHandlerFactory {
    public IRouterHandler createHandler(@NonNull RouterAction action) {
        int type = action.getTargetType();
        switch (type) {
            case RouterAction.TYPE_ACTIVITY:
                return new ActivityHandler();
            case RouterAction.TYPE_FRAGMENT:
                return new FragmentHandler();
            case RouterAction.TYPE_SREVICE:
                return new ServiceHandler();
        }
        return null;
    }
}
