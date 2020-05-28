package robin.scaffold.sample1.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import robin.scaffold.baisc.base.BaseFragment;
import robin.scaffold.sample1.R;
import robin.scaffold.sample1.second.SecondActivity;
import robin.scaffold.sample1.widget.HomeBannerAdapter;

public class HomeFragment extends BaseFragment<HomePresenter> implements IHomeView{
    @BindView(R.id.banner)
    Banner mBanner;

    @BindView(R.id.btn)
    Button button;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initViews() {
        button.setOnClickListener(v -> {
            startActivity(new Intent(mContext, SecondActivity.class));
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected HomePresenter initPresenter() {
        HomePresenter presenter = new HomePresenter();
        presenter.attachView(this);
        return presenter;
    }

    @Override
    protected void lazyInitData() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("key1","data1");
        parameters.put("key2","data2");
        mPresenter.queryBanner(parameters);
    }

    @Override
    public void showBanner(String data) {
        List<String> banners = new ArrayList<String>();
        banners.add(data);
        mBanner.addBannerLifecycleObserver(this)//添加生命周期观察者
                .setAdapter(new HomeBannerAdapter(mContext, banners))
                .setIndicator(new CircleIndicator(mContext))
                .start();
    }

    @Override
    public void onRequestStart() {
    }

    @Override
    public void onRequestCompleted() {
    }

    @Override
    public void showError(String msg) {
    }
}
