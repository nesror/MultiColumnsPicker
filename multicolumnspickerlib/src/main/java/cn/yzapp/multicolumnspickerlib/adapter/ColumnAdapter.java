package cn.yzapp.multicolumnspickerlib.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Nestor on 2/17/17.
 */
public abstract class ColumnAdapter<Data> extends BaseAdapter {

    private List<Data> mDataList;

    public ColumnAdapter(List<Data> dataList) {
        mDataList = dataList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(provideItemLayout(), parent, false);
        }
        initView(position,convertView,parent);
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

    public abstract String showString(Data data);

    public abstract boolean isChecked(Data data);

    /**
     * @return 提供布局文件
     */
    public abstract int provideItemLayout();
}
