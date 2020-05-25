package robin.scaffold.baisc.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @param <T>
 * @author new7
 * adapter父类
 */
public abstract class BasePAdapter<T> extends BaseAdapter {

    protected List<T> list = new ArrayList<>();
    protected T bean;
    protected LayoutInflater inflater;
    protected Context ct;

    public BasePAdapter(Context ct) {
        this.ct = ct;
        inflater = LayoutInflater.from(ct);
    }

    public BasePAdapter(Context ct, List<T> list) {
        this.ct = ct;
        this.list = list;
        inflater = LayoutInflater.from(ct);
    }

    public void setData(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void addData(T t) {
        this.list.add(t);
        notifyDataSetChanged();
    }

    public void addData(List<T> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return list;
    }

    public void removeData(List<T> list) {
        this.list.removeAll(list);
        notifyDataSetChanged();
    }

    public void clearAdapter() {
        if (null != list && list.size() > 0) {
            list.clear();
            notifyDataSetChanged();
        }
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public T getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
