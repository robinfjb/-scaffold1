package robin.scaffold.baisc;

/**
 * 负责子工程向父工程传输用。子工程实现此接口，返回对象，父工程直接使用。为单向管道。
 */
public interface IChildPipe {
    /**
     * 用户id
     * @return
     */
    String getUserId();

    /**
     * device id
     * @return
     */
    String getDeviceId();

    /**
     * app id 如2_1
     * @return
     */
    String getAppId();

    /**
     * 获取app版本号,如3.0.0
     * @return
     */
    String getAppVersion();

    /**
     * 获取市场信息,如tencent
     * @return
     */
    String getMarket();

    /**
     * 获取系统版本号，如7.0
     * @return
     */
    String getOsVersion();

    /**
     * 获取model，如MI 6
     * @return
     */
    String getModel();

    /**
     * 获取user agent,如 Dalvik/2.1.0 (Linux; U; Android 7.0; FRD-AL00 Build/HUAWEIFRD-AL00)
     */
    String getUserAgent();

    /**
     * 获取包名
     * @return
     */
    String getPackageName();

    /**
     * 手机号
     */
    String getPhone();

    /**
     * IMEI
     * @return
     */
    String getImei();

    /**
     * Idfa
     * @return
     */
    String getIdfa();
}
