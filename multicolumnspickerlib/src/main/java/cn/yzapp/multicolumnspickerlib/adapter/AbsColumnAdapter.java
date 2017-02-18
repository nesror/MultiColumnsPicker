package cn.yzapp.multicolumnspickerlib.adapter;

import java.util.List;

import cn.yzapp.multicolumnspickerlib.Mapper;

/**
 * Created by Nestor on 2/17/17.
 */
public abstract class AbsColumnAdapter<Data> extends ColumnAdapter<Data> {
    final Mapper<Data> mMapper;

    protected AbsColumnAdapter(List<Data> dataList, Mapper<Data> mapper) {
        super(dataList);
        mMapper = mapper;
    }

    @Override
    public String showString(Data data) {
        return mMapper == null ? "" : mMapper.getString(data);
    }

    @Override
    public boolean isChecked(Data data) {
        return mMapper != null && mMapper.isChecked(data);
    }
}
