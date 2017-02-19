package cn.yzapp.multicolumnspicker;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nestor on 2/17/19.
 */
public class AddressFactory {
    private static List<Address> mProvinceList;
    private static List<Address> mCityList;

    public static List<Address> getAddressList(int type) {
        switch (type) {
            default:
            case Address.PROVINCE:
                return getProvinceList();
            case Address.CITY:
                return getCityList();
        }

    }

    private static List<Address> getProvinceList() {
        mProvinceList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mProvinceList.add(new Address(i, "省" + i));
        }
        mProvinceList.get(0).checked = true;
        return mProvinceList;
    }

    private static List<Address> getCityList() {
        String checkedProvinceName = "";
        for (Address address : mProvinceList) {
            if (address.checked) {
                checkedProvinceName = address.name;
                break;
            }
        }
        mCityList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mCityList.add(new Address(i, checkedProvinceName + "市" + i));
        }
        mCityList.get(0).checked = true;
        return mCityList;
    }


}
