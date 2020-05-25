package robin.scaffold.baisc.router.robin;


import robin.scaffold.baisc.router.core.RouterConfig;


public interface RobinRouterConfig extends RouterConfig {
    String SCHEME = "robin";
    String HOST = "robin.test";
    String MY_PACKAGE_NAME = "robin.scaffold.app";

    int GROUP_DEFAULT = 0x01;
    int GROUP_HOME = 0x02;
    int GROUP_LOGIN = 0x03;
}
