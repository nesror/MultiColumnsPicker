package cn.yzapp.multicolumnspickerlib;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

import cn.yzapp.multicolumnspickerlib.adapter.ColumnAdapter;
import cn.yzapp.multicolumnspickerlib.adapter.SimpleColumnAdapter;
import cn.yzapp.multicolumnspickerlib.listener.OnSelected;

/**
 * Created by Nestor on 2/17/17.
 */
public class MultiColumnsPicker<T> extends LinearLayout implements Mapper<T> {
    private Mapper<T> mMapper;

    private SparseArray<List<T>> mData;

    private OnSelected<T> mOnSelected;
    private OnAdapterProvide<T> mOnAdapterProvide;

    public MultiColumnsPicker(Context context) {
        super(context);
    }

    public MultiColumnsPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiColumnsPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MultiColumnsPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setPageCount(int count) {
        mData = new SparseArray<>(count);

        for (int i = count; i > 0; i--) {
            final ListView listView = new ListView(getContext());
            listView.setLayoutParams(
                    new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            addView(listView);
        }

    }

    /**
     * 配置监听器
     */
    public void setOnSelected(OnSelected<T> onSelected) {
        mOnSelected = onSelected;
    }

    public void setMapper(Mapper<T> mapper) {
        mMapper = mapper;
    }

    /**
     * 配置适配器
     *
     * @param onAdapterProvide 适配器回调
     */
    public void setAdapter(OnAdapterProvide<T> onAdapterProvide) {
        mOnAdapterProvide = onAdapterProvide;
    }

    /**
     * 配置某页的适配器
     *
     * @param page             页数
     * @param onAdapterProvide 适配器回调
     */
    public void setAdapter(int page, OnAdapterProvide<T> onAdapterProvide) {
        // TODO: 2017/2/18
    }

    /**
     * 设置内容
     */
    public void setContent(final int page, final List<T> data) {
        if (mData == null) {
            throw new InstantiationError("没有设置总页数");
        }
        if (mMapper == null) {
            throw new InstantiationError("没有设置Mapper");
        }
        if (page >= getChildCount()) {
            throw new ArrayIndexOutOfBoundsException("设置内容页大于总页数");
        }
        mData.put(page, data);

        final ListView listView = (ListView) getChildAt(page);

        final ColumnAdapter adapter;
        if (mOnAdapterProvide != null) {
            adapter = mOnAdapterProvide.provideAdapter(this, data);
        } else {
            adapter = new SimpleColumnAdapter<>(data, MultiColumnsPicker.this);
        }
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                for (T t : data) {
                    setChecked(t, false);
                }
                setChecked(data.get(position), true);
                mOnSelected.onSelected(page, data.get(position));
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public String getString(T t) {
        return mMapper.getString(t);
    }

    @Override
    public boolean isChecked(T t) {
        return mMapper.isChecked(t);
    }

    @Override
    public void setChecked(T t, boolean checked) {
        mMapper.setChecked(t, checked);
    }

    public interface OnAdapterProvide<V> {
        ColumnAdapter<V> provideAdapter(Mapper<V> mapper, List<V> data);
    }

}
