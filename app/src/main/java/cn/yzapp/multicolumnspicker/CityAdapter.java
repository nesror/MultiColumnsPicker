package cn.yzapp.multicolumnspicker;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cn.yzapp.multicolumnspickerlib.Mapper;
import cn.yzapp.multicolumnspickerlib.adapter.AbsColumnAdapter;

/**
 * Created by Nestor on 2/17/18.
 */
public class CityAdapter<Data> extends AbsColumnAdapter<Data> {
    CityAdapter(List<Data> list, Mapper<Data> mapper) {
        super(R.layout.city_item, list, mapper);
    }

    @Override
    protected void initView(int position, View convertView, ViewGroup parent) {
        TextView textView = (TextView) convertView.findViewById(cn.yzapp.multicolumnspickerlib.R.id.text);
        textView.setText(showString(getItem(position)));
        if (isChecked(getItem(position))) {
            textView.setBackgroundColor(0xffeeeeee);
        } else {
            textView.setBackgroundColor(0xffffffff);
        }
    }
}
