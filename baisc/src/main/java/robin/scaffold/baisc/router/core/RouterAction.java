package robin.scaffold.baisc.router.core;

import android.os.Bundle;

import robin.scaffold.baisc.util.Parameters;

public class RouterAction<T> {
    public static final int TYPE_ACTIVITY = 1;
    public static final int TYPE_FRAGMENT = 2;
    public static final int TYPE_SREVICE = 3;

    private int requestCode;
    private String packageName;
    private String originalUrl;
    private int targetType;
    private Bundle bundle;//供大对象使用
    private T target;
    private String path;
    private Parameters parameters;
    private int intentFlag;

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public int getTargetType() {
        return targetType;
    }

    public void setTargetType(int targetType) {
        this.targetType = targetType;
    }

    public T getTarget() {
        return target;
    }

    public void setTarget(T target) {
        this.target = target;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters mParameters) {
        this.parameters = mParameters;
    }

    public Bundle getBundle() {
        return bundle;
    }

    public void setBundle(Bundle mBundle) {
        this.bundle = mBundle;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public int getIntentFlag() {
        return intentFlag;
    }

    public void setIntentFlag(int intentFlag) {
        this.intentFlag = intentFlag;
    }
}
