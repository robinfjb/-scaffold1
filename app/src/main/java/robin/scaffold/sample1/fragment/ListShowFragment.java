package robin.scaffold.sample1.fragment;

import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import robin.scaffold.baisc.base.BaseFragment;
import robin.scaffold.baisc.util.DisplayUtil;
import robin.scaffold.sample1.R;
import robin.scaffold.sample1.widget.ListRecycleViewAdapter;

public class ListShowFragment extends BaseFragment<ListShowPresenter> implements IListShowView{
    @BindView(R.id.smartRefreshLayout)
    SmartRefreshLayout mSmartRefreshLayout;

    @BindView(R.id.rv_list)
    RecyclerView recyclerView;

    private ListRecycleViewAdapter listRecycleViewAdapter;

    @Override
    protected int attachLayoutRes() {
        return R.layout.fragment_list;
    }

    @Override
    protected void initViews() {
        mSmartRefreshLayout.setOnRefreshListener(refreshlayout -> {
            Map<String, Object> parameters = new HashMap<>();
            parameters.put("key1","data1");
            parameters.put("key2","data2");
            mPresenter.queryList(parameters);
        });
        mSmartRefreshLayout.setHeaderHeight(DisplayUtil.dip2px(mContext, 30));
        mSmartRefreshLayout.setHeaderMaxDragRate(2.5f);
        mSmartRefreshLayout.setDisableContentWhenRefresh(true);

        listRecycleViewAdapter = new ListRecycleViewAdapter(0/*your recycleview item layout*/);
        recyclerView.setAdapter(listRecycleViewAdapter);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected ListShowPresenter initPresenter() {
        ListShowPresenter presenter = new ListShowPresenter();
        presenter.attachView(this);
        return presenter;
    }

    @Override
    protected void lazyInitData() {
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("key1","data1");
        parameters.put("key2","data2");
        mPresenter.queryList(parameters);
    }

    @Override
    public void showList(String data) {
        List<String> datas = new ArrayList<String>();
        datas.add(data);
        listRecycleViewAdapter.setDiffNewData(datas);
    }

    @Override
    public void onRequestStart() {

    }

    @Override
    public void onRequestCompleted() {
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishRefresh();
            mSmartRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void showError(String msg) {
        if (mSmartRefreshLayout != null) {
            mSmartRefreshLayout.finishRefresh();
            mSmartRefreshLayout.finishLoadMore();
        }
    }
}
