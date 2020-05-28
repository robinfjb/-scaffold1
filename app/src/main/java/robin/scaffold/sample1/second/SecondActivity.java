package robin.scaffold.sample1.second;

import android.view.View;
import android.widget.TextView;

import com.gyf.immersionbar.ImmersionBar;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import robin.scaffold.baisc.base.BaseActivity;
import robin.scaffold.sample1.R;
import robin.scaffold.sample1.R2;

public class SecondActivity extends BaseActivity<SecondPresenter> implements ISecondView {
    @BindView(R2.id.tv_response_data)
    TextView tvResponseData;

    @Override
    protected int attachLayoutRes() {
        return R.layout.activity_second;
    }

    @Override
    protected void initViews() {
        titleManagerBuidler.setTitleTxt("Second Page");
        ImmersionBar.with(this).navigationBarWithKitkatEnable(false)
                .statusBarDarkFont(true, 0.2f)
                .navigationBarColor(android.R.color.white).init();
    }

    @Override
    protected void initData() {
        //init your data
    }

    @Override
    protected SecondPresenter initPresenter() {
        SecondPresenter presenter = new SecondPresenter(context);
        presenter.attachView(this);
        return presenter;
    }

    public void requestTest(View v) {
        mPresenter.doSomeThing();
    }

    @Override
    public void onSomeThing(String action) {
        tvResponseData.setText(action);
    }
}
