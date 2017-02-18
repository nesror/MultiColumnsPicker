package cn.yzapp.multicolumnspickerlib;

/**
 * Created by Nestor on 2/17/17.
 * <p>
 * 映射显示字符串
 */
public interface Mapper<T> {
    String getString(T t);

    boolean isChecked(T t);

    void setChecked(T t,boolean isChecked);
}
