package robin.scaffold.baisc.router.core.handler;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;

import androidx.fragment.app.Fragment;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;

import robin.scaffold.baisc.router.base.IResultCallback;
import robin.scaffold.baisc.router.core.RouterAction;
import robin.scaffold.baisc.router.exception.RouterException;
import robin.scaffold.baisc.util.Parameters;


public class FragmentHandler implements IRouterHandler{
    @Override
    public boolean handle(Context context, RouterAction action, IResultCallback callback) throws RouterException {
        if(action == null) {
            throw new RouterException("action null in FragmentHandler:handle");
        }

        //set fragment
        final Fragment fragment = (Fragment) action.getTarget();
        if (fragment == null) {
            throw new RouterException("fragment null or invalid in FragmentHandler:handle");
        }
        Bundle bundle = new Bundle();

        //set param
        Parameters parameters = action.getParameters();

        if(parameters != null) {
            Set<String> keys = parameters.getParameterNames();
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = parameters.getParameter(key);
                bundle.putString(key, value);
            }
        }

        //set data
        Bundle extrabundle = action.getBundle();
        if(extrabundle != null) {
            Serializable sData = extrabundle.getSerializable("s_obj");
            if(sData != null)
                bundle.putSerializable("s_obj", sData);
            Parcelable pData = extrabundle.getParcelable("p_obj");
            if(pData != null)
                bundle.putParcelable("p_obj", pData);
        }
        fragment.setArguments(bundle);
        if(callback != null) {
            callback.onResult(fragment);
        }
        return true;
    }
}
