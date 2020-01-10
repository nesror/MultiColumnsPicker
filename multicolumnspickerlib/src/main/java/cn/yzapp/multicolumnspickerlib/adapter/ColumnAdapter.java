package cn.yzapp.multicolumnspickerlib.adapter;

import androidx.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Nestor on 2/17/17.
 */
public abstract class ColumnAdapter<Data> extends BaseAdapter {

    private final int mLayoutRes;
    private final List<Data> mDataList;

    ColumnAdapter(@LayoutRes int layoutRes, List<Data> dataList) {
        mLayoutRes = layoutRes;
        mDataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(mLayoutRes, parent, false);
        }
        initView(position, convertView, parent);
        return convertView;
    }

    protected abstract void initView(int position, View convertView, ViewGroup parent);

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Data getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    /**
     * 得到需要显示的文字
     * @param data 数据
     * @return 需要显示的文字
     */
    public abstract String showString(Data data);

    /**
     * 判断是否为选中的item
     * @param data 数据
     * @return 是否选中
     */
    public abstract boolean isChecked(Data data);

}
