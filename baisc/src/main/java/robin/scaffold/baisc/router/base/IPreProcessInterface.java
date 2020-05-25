package robin.scaffold.baisc.router.base;

import robin.scaffold.baisc.router.exception.RouterException;

/**
 * 负责url的预处理
 * @param <T>
 */
public interface IPreProcessInterface<T> {
    T prePorcess(String url) throws RouterException;
}
