package robin.scaffold.baisc.base;

import com.trello.rxlifecycle4.LifecycleTransformer;

public interface IBaseView {
    /**
     * 请求开始
     */
    void onRequestStart();

    /**
     * 请求结束
     */
    void onRequestCompleted();

    /**
     * 显示错误信息
     *
     * @param msg
     */
    void showError(String msg);
}
