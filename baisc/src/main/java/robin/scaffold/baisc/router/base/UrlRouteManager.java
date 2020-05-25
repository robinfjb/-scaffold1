package robin.scaffold.baisc.router.base;


import robin.scaffold.baisc.router.robin.RobinRouterConfig;

/**
 * router功能对外类
 */

public class UrlRouteManager {
    private static class SingletonHolder {
        private static final UrlRouteManager INSTANCE = new UrlRouteManager();
    }
    private UrlRouterCore core = new UrlRouterCore();
    private UrlRouteManager (){}
    public static final UrlRouteManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    /**
     * 注册
     * @param path
     * @param processor
     */
    public void registerProtocol(String path, int group, IProcessInterface processor) {
        core.registerProtocol(path, group, processor);
    }

    public void registerProtocol(String path, IProcessInterface processor) {
        core.registerProtocol(path, RobinRouterConfig.GROUP_DEFAULT, processor);
    }

    /**
     * 反注册
     * @param path
     * @param group
     */
    public void unRegisterProtocol(String path, int group){
        core.unRegisterProtocol(path, group);
    }

    /**
     * 反注册整个group
     * @param group
     */
    public void unRegisterProtocol(int group){
        core.unRegisterProtocol(group);
    }

    /**
     * 查找Processor
     * @param url
     * @param group
     * @return
     */
    public IProcessInterface seek(String url, int group) {
        return core.getTargetIProcessor(url, group);
    }

    public IProcessInterface seek(String url) {
        return core.getTargetIProcessor(url, RobinRouterConfig.GROUP_DEFAULT);
    }
}
