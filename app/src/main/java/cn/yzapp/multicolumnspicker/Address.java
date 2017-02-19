package cn.yzapp.multicolumnspicker;

/**
 * Created by Nestor on 2/17/17.
 */
public class Address {
    public static final int PROVINCE = 0;
    public static final int CITY = 1;

    int id;
    String name;
    boolean checked;

    public Address(int id, String name) {
        this.id = id;
        this.name = name;
    }
}
