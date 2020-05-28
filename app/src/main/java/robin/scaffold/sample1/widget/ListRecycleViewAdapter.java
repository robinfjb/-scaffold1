package robin.scaffold.sample1.widget;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.NotNull;

public class ListRecycleViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public ListRecycleViewAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {

    }
}
