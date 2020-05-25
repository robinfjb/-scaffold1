package robin.scaffold.baisc.util;

import robin.scaffold.baisc.BaseApplication;

public class UserAgentContants {
    public static final String USER_AGENT = BaseApplication.iChildPipe.getUserAgent() + "("
            + "\"uid\"" + ":" + "\"" + BaseApplication.iChildPipe.getUserId() + "\""
            + ","
            + "\"dev\"" + ":" + "{\"deviceid\"" + ":" + "\"" + BaseApplication.iChildPipe.getDeviceId() + "\"" + ","
            + "\"os\"" + ":" + "\"" + BaseApplication.iChildPipe.getOsVersion() + "\"" + ","
            + "\"model\"" + ":" + "\"" + BaseApplication.iChildPipe.getModel() + "\"}"
            + ","
            + "\"app\"" + ":" + "{\"appid\"" + ":" + "\"" + BaseApplication.iChildPipe.getAppId() + "\"" + ","
            + "\"version\"" + ":" + "\"" + BaseApplication.iChildPipe.getAppVersion()+ "\"" + ","
            + "\"market\"" + ":" + "\"" + BaseApplication.iChildPipe.getMarket() + "\"}"
            + ")";
}
