package cn.yzapp.multicolumnspickerlib;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.ArrayList;
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
    private List<ListView> mListViewList;

    private OnSelected<T> mOnSelected;
    private OnAdapterProvide<T> mOnAdapterProvide;
    private List<OnAdapterProvide<T>> mOnAdapterProvideList;

    private int mDivisionColour;
    private int mPageCount;

    public MultiColumnsPicker(Context context) {
        super(context);
    }

    public MultiColumnsPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
        setAttrs(context, attrs);
    }

    public MultiColumnsPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setAttrs(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MultiColumnsPicker(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setAttrs(context, attrs);
    }

    private void setAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.multicolomns_attrs, 0, 0);
        try {
            int divisionColour = typedArray.getColor(R.styleable.multicolomns_attrs_multicolomns_divisionColour, 0);
            setDivisionColour(divisionColour);
            int pageCount = typedArray.getInt(R.styleable.multicolomns_attrs_multicolomns_pageCount, 1);
            setPageCount(pageCount);
        } finally {
            typedArray.recycle();
        }
    }

    /**
     * 设置分割线颜色(也可通过multicolomns_divisionColour设置)
     *
     * @param colour 分割线颜色
     */
    public void setDivisionColour(int colour) {
        mDivisionColour = colour;
    }

    /**
     * 设置页数(也可通过multicolomns_pageCount设置)
     *
     * @param count 总页数
     */
    public void setPageCount(int count) {
        if (mData == null) {
            mPageCount = count;
            mData = new SparseArray<>(count);
            initView();
        }
    }

    /**
     * 设置选中,显示内容的字段
     *
     * @param mapper 映射器
     */
    public void setMapper(Mapper<T> mapper) {
        mMapper = mapper;
    }

    /**
     * 设置选择监听器
     *
     * @param onSelected 中项的回调
     */
    public void setOnSelected(OnSelected<T> onSelected) {
        mOnSelected = onSelected;
    }

    /**
     * 配置自定义适配器
     *
     * @param onAdapterProvide 适配器回调
     */
    public void setAdapter(OnAdapterProvide<T> onAdapterProvide) {
        mOnAdapterProvide = onAdapterProvide;
    }

    /**
     * 配置某页的自定义适配器
     *
     * @param page             页数
     * @param onAdapterProvide 适配器回调
     */
    public void setAdapter(int page, OnAdapterProvide<T> onAdapterProvide) {
        if (mOnAdapterProvideList == null) {
            mOnAdapterProvideList = new ArrayList<>(mPageCount);
        }
        if (mOnAdapterProvideList.size() == 0) {
            for (int i = 0; i < mPageCount; i++) {
                mOnAdapterProvideList.add(null);
            }
        }
        mOnAdapterProvideList.add(page, onAdapterProvide);
    }

    /**
     * 设置显示内容
     *
     * @param page 需要显示的页
     * @param data 内容
     */
    public void setContent(final int page, final List<T> data) {
        checkConfig(page);

        mData.put(page, data);

        initializeListView(page, data);
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

    private void initView() {
        mListViewList = new ArrayList<>(mPageCount);
        for (int i = 0; i < mPageCount; i++) {
            final ListView listView = new ListView(getContext());
            listView.setLayoutParams(
                    new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            addDivisionView(mPageCount, i);
            addView(listView);
            mListViewList.add(listView);
        }
    }

    private void addDivisionView(int count, int position) {
        if (mDivisionColour == 0) {
            return;
        }
        final View divisionView = new View(getContext());
        divisionView.setLayoutParams(
                new LayoutParams(UiUtil.dip2px(getContext(), 1), ViewGroup.LayoutParams.MATCH_PARENT));
        divisionView.setBackgroundColor(mDivisionColour);
        if (position != 0 && position != count) {
            addView(divisionView);
        }
    }

    private void initializeListView(final int page, final List<T> data) {
        final ListView listView = mListViewList.get(page);

        final ColumnAdapter adapter;

        if (mOnAdapterProvideList != null && mOnAdapterProvideList.get(page) != null) {
            adapter = mOnAdapterProvideList.get(page).provideAdapter(this, data);
        } else if (mOnAdapterProvide != null) {
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

    private void checkConfig(int page) {
        if (mData == null) {
            throw new InstantiationError("没有设置总页数");
        }
        if (mMapper == null) {
            throw new InstantiationError("没有设置Mapper");
        }
        if (page >= getChildCount()) {
            throw new ArrayIndexOutOfBoundsException("设置内容页大于总页数");
        }
    }

}
