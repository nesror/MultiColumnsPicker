package cn.yzapp.multicolumnspickerlib.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.yzapp.multicolumnspickerlib.R;
import cn.yzapp.multicolumnspickerlib.Mapper;

/**
 * Created by Nestor on 2/17/17.
 */
public class SimpleColumnAdapter<Data> extends AbsColumnAdapter<Data> {

    public SimpleColumnAdapter(List<Data> dataList, Mapper<Data> mapper) {
        super(R.layout.multicolomns_item, dataList, mapper);
    }

    @Override
    protected void initView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) convertView.findViewById(R.id.text);
        textView.setText(showString(getItem(position)));
        if (isChecked(getItem(position))) {
            textView.setBackgroundColor(0xffdddddd);
        } else {
            textView.setBackgroundColor(0xffffffff);
        }
    }

    @Override
    public String showString(Data data) {
        return mMapper.getString(data);
    }

    @Override
    public boolean isChecked(Data data) {
        return mMapper.isChecked(data);
    }
}
